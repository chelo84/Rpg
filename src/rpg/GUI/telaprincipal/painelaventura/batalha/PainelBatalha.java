package rpg.GUI.telaprincipal.painelaventura.batalha;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.painelaventura.Area;
import rpg.GUI.telaprincipal.painelaventura.Areas;
import rpg.GUI.telaprincipal.painelaventura.PainelAventura;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.util.LabelVoltar;
import static rpg.GUI.util.Listas.MAGIASJOGO;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import rpg.GUI.util.MeuTimer;
import rpg.magia.Magia;
import rpg.monstro.Monstro;
import rpg.personagem.MagiasPersonagem;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

//Criação da tela de batalha
public final class PainelBatalha extends MeuPanel {
    private final Personagem personagem;
    private final Personagens personagens;
    private final Area area;
    private Batalha batalha;
    private JLabel voltarLabel = new JLabel();
    private JButton botaoFugir = new MeuBotao();
    private JButton botaoUsarItem = new MeuBotao();
    private JButton botaoAtacar = new MeuBotao();
    private JButton botaoMagia = new MeuBotao();
    private JButton botaoLoot = new MeuBotao();
    private JTextPane text;
    private final JProgressBar barraVidaPersonagem = new JProgressBar();
    private final JProgressBar barraManaPersonagem = new JProgressBar();
    private final JProgressBar barraXpPersonagem = new JProgressBar();
    private final JProgressBar barraVidaMonstro = new JProgressBar();
    private final Thread thread;
    
    public PainelBatalha(Personagem personagem, Personagens personagens, String area) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        Areas areas = new Areas();
        
        this.area = areas.get(area);
        
        thread = new JogoThread();
        thread.start();
        
        this.setBatalha();
        
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }

    private void createComponents(Container container) throws BadLocationException, IOException {
        container.setLayout(new MigLayout("fillx"));
        
        JPanel painel = new MeuPanel(new MigLayout("fillx", "", ""));
        //É criado um label azul clicável para voltar à tela anterior
        voltarLabel = new LabelVoltar("Voltar", this, new PainelAventura(this.personagem, this.personagens), thread);
        voltarLabel.setVisible(false);
        container.add(voltarLabel, "growx, aligny top, alignx left, wrap");
        
        JPanel painelBatalha = new MeuPanel(new MigLayout("fillx"));
        painelBatalha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JPanel painelPersonagem = new MeuPanel(new MigLayout("fillx"));
        
        JLabel nomePersonagem = new MeuLabel(this.personagem.getNome());
        painelPersonagem.add(nomePersonagem, "align center, wrap");
        
        JPanel painelPersonagemImg = new InvSlotFundo(new MigLayout("fill"));
        JLabel personagemImg = new JLabel(new ImageIcon("elementos\\outros\\Personagem.jpg"));
        painelPersonagemImg.add(personagemImg, "align center, grow, span, id pimg");
        painelPersonagem.add(painelPersonagemImg, "width 80:80:80, h 80:80:80, wrap");
        
        //É criado um timertask que cria um timertask que atualiza a barra de vida e xp do personagem
        MeuTimer.timer.scheduleAtFixedRate(new vidaManaXpPersonagem(), 0, 1000/10);
        
        barraVidaPersonagem.setForeground(Color.GREEN);
        barraVidaPersonagem.setStringPainted(true);
        painelPersonagem.add(barraVidaPersonagem, "width 70, height 20:20:20, wrap");
        
        barraManaPersonagem.setForeground(Color.decode("#2E75FA"));
        barraManaPersonagem.setStringPainted(true);
        painelPersonagem.add(barraManaPersonagem, "width 70, height 20:20:20, wrap");
        
        barraXpPersonagem.setForeground(Color.YELLOW);
        barraXpPersonagem.setStringPainted(true);
        painelPersonagem.add(barraXpPersonagem, "width 70, height 20:20:20");
        
        painelBatalha.add(painelPersonagem, "align left");
        
        JLabel vs = new MeuLabel("<html><font size=\"30\">vs");
        painelBatalha.add(vs, "align center");
        
        JPanel painelMonstro = new MeuPanel(new MigLayout("fillx"));
        
        Monstro monstro = this.batalha.getMonstro();
        
        JLabel nomeMonstro = new MeuLabel(monstro.getNome());
        painelMonstro.add(nomeMonstro, "align center, wrap");
        
        JPanel painelMonstroImg = new InvSlotFundo(new MigLayout("fill"));
        JLabel monstroImg = new JLabel(new ImageIcon(monstro.getImg()));
        painelMonstroImg.add(monstroImg, "align center, grow, id monstroimg");
        painelMonstro.add(painelMonstroImg, "align center, width 80:80:80, h 80:80:80, wrap");
                
        //É criado um timertask que cria um timertask que atualiza a barra de vida do monstro
        MeuTimer.timer.scheduleAtFixedRate(new vidaMonstro(), 0, 1000/10);
        
        barraVidaMonstro.setForeground(Color.GREEN);
        barraVidaMonstro.setStringPainted(true);
        painelMonstro.add(barraVidaMonstro, "align center, height 20:20:20, width 70, wrap");
        //painelMonstro.setOpaque(true);
        painelBatalha.add(painelMonstro, "align right");
        
        painel.add(painelBatalha, "wrap, growx");
        
        text = new JTextPane();
        text.setContentType("text/html");
        
        botaoFugir = new MeuBotao("<html><b>Fugir");
        botaoFugir.addActionListener((ActionEvent ae) -> {
            try {
                if(batalha.tentarFugir(text)) {
                    voltarLabel.setVisible(true);
                    
                    botaoAtacar.setEnabled(false);
                    botaoFugir.setEnabled(false);
                    botaoUsarItem.setEnabled(false);
                    botaoLoot.setEnabled(false);
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painel.add(botaoFugir, "h 30:30:30, width 30, alignx center, split 5");
        
        botaoUsarItem = new MeuBotao("<html><b>Usar item");
        botaoUsarItem.addActionListener(new ActionListener() {
            Personagem personagem;
            Personagens personagens;
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PainelBatalha.this);
                JDialog dialog = new UsarItemDialog(frame, personagem, personagens);
            }
            
            public ActionListener init(Personagem personagem, Personagens personagens) {
                this.personagem = personagem;
                this.personagens = personagens;
                
                return this;
            }
        }.init(this.personagem, this.personagens));
        painel.add(botaoUsarItem, "h 30:30:30, gapleft 10px");
        
        botaoAtacar = new MeuBotao("<html><b>Atacar");
        botaoAtacar.addActionListener((ActionEvent ae) -> {
            try {
                boolean crit = PainelBatalha.this.batalha.ataqueBasico(text);
                ImageIcon icone;
                
                if(crit) {
                    final String animacaoCrit = "elementos\\magias\\animacoes\\Critical_Hit_Effect.gif";
                    icone = new ImageIcon(Toolkit.getDefaultToolkit().createImage(animacaoCrit));
                } else {
                    final String animacaoHit = "elementos\\magias\\animacoes\\Yellow_Electricity_Effect.gif";
                    icone = new ImageIcon(Toolkit.getDefaultToolkit().createImage(animacaoHit));
                }
                
                JLabel animacao = new JLabel(icone);
                painelMonstroImg.add(animacao, "pos monstroimg.x monstroimg.y monstroimg.x2 monstroimg.y2", 0);
                //Roda animação do ataque por 1 s
                Thread thread1 = new Thread(() -> {
                    long time = System.currentTimeMillis();
                    while(System.currentTimeMillis() - time < 300);
                    painelMonstroImg.remove(animacao);
                });
                thread1.start();
            }catch (BadLocationException ex) {
                Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painel.add(botaoAtacar, "h 30:30:30, gapleft 10px, width 30");
        
        botaoMagia = new MeuBotao("<html><b><font color=\"purple\">Magia");
        botaoMagia.setEnabled(false);

        painel.add(botaoMagia, "h 30:30:30, gapleft 10px");
        
        botaoLoot = new MeuBotao("<html><b>Loot");
        botaoLoot.setEnabled(false);
        botaoLoot.setVisible(false);
        botaoLoot.addActionListener((ActionEvent ae) -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PainelBatalha.this);
            JDialog dialog = new LootDialog(frame, personagem, personagens, batalha.getMonstro());
        });
        painel.add(botaoLoot, "h 30:30:30, gapleft 10px, width 30, wrap");
        
        text.setEditable(false);
        
        JPanel painelMagias = new MeuPanel2(new MigLayout("filly"));
        JScrollPane scrollPane = new JScrollPane(painelMagias); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().setBounds(1, 1, 1, 1);
        painelMagias.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ArrayList<JPanel> paineisMagia = new ArrayList<>();
        MagiasPersonagem magias = personagem.getMagias();
        
        final int TAMANHOBORDER = 3;
        for(int i = 0; i < magias.size(); i++) {
            JPanel painelDestaMagia = new MeuPanel(new MigLayout("fill"));
            painelDestaMagia.setBorder(BorderFactory.createLineBorder(Color.WHITE, TAMANHOBORDER));
            Magia magia = magias.get(i);
            painelDestaMagia.setName(magia.getId());
            painelDestaMagia.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent me) {
                    LineBorder border = (LineBorder) painelDestaMagia.getBorder();
                    
                    if(border.getLineColor().equals(Color.WHITE)) {
                        
                        for(int i = 0; i < paineisMagia.size(); i++) {
                            JPanel panel = paineisMagia.get(i);
                            if(panel.equals(painelDestaMagia)) {
                                panel.setBorder(BorderFactory.createLineBorder(Color.RED, TAMANHOBORDER));
                                botaoMagia.setEnabled(true);
                            } else {
                                panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, TAMANHOBORDER));
                            }
                        }
                        
                    } else if(border.getLineColor().equals(Color.RED)) {
                        painelDestaMagia.setBorder(BorderFactory.createLineBorder(Color.WHITE, TAMANHOBORDER));
                        botaoMagia.setEnabled(false);
                    }
                    
                    painelDestaMagia.repaint();
                    painelDestaMagia.revalidate();
                }
            });
            
            
            if(i == 0) {
                ImageIcon icone = new ImageIcon(magia.getImg());
                Image image = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                icone = new ImageIcon(image);
                
                JLabel label = new JLabel(icone);

                painelDestaMagia.add(label);
                painelDestaMagia.setBorder(BorderFactory.createLineBorder(Color.RED, TAMANHOBORDER));
                botaoMagia.setEnabled(true);
                painelDestaMagia.setToolTipText(magia.toString());
                
                painelMagias.add(painelDestaMagia, "");
                JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
                separator.setForeground(Color.BLACK);
                separator.setBackground(Color.BLACK);
                painelMagias.add(separator, "growy, spany");
                
            } else {
                JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
                separator.setForeground(Color.BLACK);
                separator.setBackground(Color.BLACK);
                ImageIcon icone = new ImageIcon(magia.getImg());
                Image image = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                icone = new ImageIcon(image);
                
                JLabel label = new JLabel(icone);
                
                painelDestaMagia.add(label);
                painelDestaMagia.setToolTipText(magia.toString());
                painelMagias.add(painelDestaMagia, "");
                painelMagias.add(separator, "growy, spany");
            }
            
            
            
            paineisMagia.add(painelDestaMagia);
        }
        painel.add(scrollPane, "grow, wrap");
        
        botaoMagia.addActionListener((ActionEvent ae) -> {
            try {
                Magia magia = null;
                for(JPanel p : paineisMagia) {
                    LineBorder borda = (LineBorder) p.getBorder();
                    if(borda.getLineColor().equals(Color.RED)) {
                        magia = MAGIASJOGO.get(p.getName());
                    }
                }
            
                if(batalha.usarMagia(text, magia)) {
                    ImageIcon icone;
                    
                    icone = new ImageIcon(Toolkit.getDefaultToolkit().createImage(magia.getAnimacao()));
                    
                    JLabel animacao = new JLabel(icone);
                    switch(magia.getAlvo().trim().toLowerCase()) {
                        case "inimigo" : painelMonstroImg.add(animacao, "pos monstroimg.x monstroimg.y monstroimg.x2 monstroimg.y2", 0); break;
                        case "personagem" : painelPersonagemImg.add(animacao, "pos pimg.x pimg.y pimg.x2 pimg.y2", 0); break;
                    }
                    //Roda animação do ataque por 1 s
                    Thread thread1 = new Thread(() -> {
                        long time = System.currentTimeMillis();
                        while(System.currentTimeMillis() - time < 300);
                        painelMonstroImg.remove(animacao);
                        painelPersonagemImg.remove(animacao);
                    });
                    thread1.start();
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JScrollPane sp = new JScrollPane(text);
        painel.add(sp, "h 115:115:115, growx");  
        
        //É criado um timertask e um timer para verificar se o monstro morreu
        //Se o monstro morrer a xp será adicionada ao personagem
        TimerTask task = new TimerTask() {
          @Override
          public void run() {
            if (PainelBatalha.this.batalha.getMonstro().getVidaAtual() <= 0) {
                try {
                    batalha.fimBatalhaLog(text, batalha.getMonstro().getXp());
                } catch (BadLocationException ex) {
                    Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
                }

                personagem.addXp(batalha.getMonstro().getXp());
                
                
                botaoLoot.setEnabled(true);
                botaoLoot.setVisible(true);
                
                botaoAtacar.setEnabled(false);
                botaoFugir.setEnabled(false);
                
                voltarLabel.setVisible(true);
                
                try {
                    PainelBatalha.this.personagens.atualizarInfoPersonagem();
                } catch (IOException ex) {
                    Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                this.cancel();
            }
          }
        };
        MeuTimer.timer.scheduleAtFixedRate(task, 0, 1000/10);
        

        
        container.add(painel, "push, align center, grow, gaptop");
    }
    
    public void setBatalha() {
        this.batalha = new Batalha(this.personagem, this.area);
    }
    
    private class vidaManaXpPersonagem extends TimerTask {
        @Override
        public void run() {
            int vidaAtual = PainelBatalha.this.personagem.getVidaAtual();
            int vidaMax = PainelBatalha.this.personagem.getVidaMax();
            JProgressBar barraVida = PainelBatalha.this.barraVidaPersonagem;
            
            barraVida.setValue(vidaAtual);
            barraVida.setMaximum(vidaMax);
            barraVida.setString(vidaAtual +"/"+ vidaMax);
            
            int manaAtual = PainelBatalha.this.personagem.getManaAtual();
            int manaMax = PainelBatalha.this.personagem.getManaMax();
            JProgressBar barraMana = PainelBatalha.this.barraManaPersonagem;
            
            barraMana.setValue(manaAtual);
            barraMana.setMaximum(manaMax);
            barraMana.setString(manaAtual +"/"+ manaMax);
            
            int xpAtual = PainelBatalha.this.personagem.getXpAtual();
            int xpNecessaria = PainelBatalha.this.personagem.getXpNecessaria();
            JProgressBar barraXp = PainelBatalha.this.barraXpPersonagem;
            
            
            double pCento = (double) barraXp.getValue() / (double) barraXp.getMaximum() * 100;
            DecimalFormat f = new DecimalFormat("###0.00", new DecimalFormatSymbols(Locale.ROOT));
            barraXp.setToolTipText(f.format(pCento) +
                                   "% do nível "+ PainelBatalha.this.personagem.getLevel());
            
            barraXp.setValue(xpAtual);
            barraXp.setMaximum(xpNecessaria);
            barraXp.setString(xpAtual +"/"+ xpNecessaria);
            
            PainelBatalha.this.repaint();
            PainelBatalha.this.revalidate();
        }
    } 
    
    private class vidaMonstro extends TimerTask {
        @Override
        public void run() {
            int vidaAtual = PainelBatalha.this.batalha.getMonstro().getVidaAtual();
            int vidaMax = PainelBatalha.this.batalha.getMonstro().getVida();
            JProgressBar barraVida = PainelBatalha.this.barraVidaMonstro;
            
            barraVida.setValue(vidaAtual);
            barraVida.setMaximum(vidaMax);
            barraVida.setString(vidaAtual+"/"+ vidaMax);
            
            PainelBatalha.this.repaint();
            PainelBatalha.this.revalidate();
        }
    } 
    
    private class JogoThread extends Thread {
        
        @Override
        public void run() {
            boolean b = true;
            
            while(b) {
                if(PainelBatalha.this.personagem.getVidaAtual() <= 0) {
                    JOptionPane.showMessageDialog(new JOptionPane(), personagem.getNome() 
                            + " morreu e perdeu 10% de xp!",
                            "Personagem morto",
                            JOptionPane.INFORMATION_MESSAGE);                
                    
                    PainelBatalha.this.voltarLabel.setVisible(true);
                    
                    PainelBatalha.this.botaoAtacar.setEnabled(false);
                    PainelBatalha.this.botaoFugir.setEnabled(false);
                    PainelBatalha.this.botaoUsarItem.setEnabled(false);
                    PainelBatalha.this.botaoLoot.setEnabled(false);
                    
                    PainelBatalha.this.personagem.addXp(-(PainelBatalha.this.personagem.getXpAtual()/10));
                    
                    PainelBatalha.this.personagem.curarVida(PainelBatalha.this.personagem.getVidaMax());
                    
                    try {
                        PainelBatalha.this.personagens.atualizarInfoPersonagem();
                    } catch (IOException ex) {
                        Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    break;
                }
                
                try {
                    Thread.sleep(1000/10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PainelBatalha.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            Thread.currentThread().interrupt();
        }
    }
}
