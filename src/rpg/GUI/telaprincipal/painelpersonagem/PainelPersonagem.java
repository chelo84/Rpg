package rpg.GUI.telaprincipal.painelpersonagem;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.PainelPrincipal;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuTimer;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class PainelPersonagem extends MeuPanel {
    private Personagem personagem;
    private Personagens personagens;
    private JLabel levelLabel = new MeuLabel();
    private JLabel ataqueLabel = new MeuLabel();
    private JLabel danoMagicoLabel = new MeuLabel();
    private JLabel defesaLabel = new MeuLabel();
    private JLabel criticoLabel = new MeuLabel();
    private JLabel evasaoLabel = new MeuLabel();
    private JProgressBar barraVida = new JProgressBar();
    private JProgressBar barraMana = new JProgressBar();
    private JProgressBar barraXp = new JProgressBar();
    private JTextField valorSPField = new JTextField();
    private JButton botaoConfirmar = new MeuBotao();
    private int statPointsDps = 0;
    
    public PainelPersonagem(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }
    
    public void createComponents(Container container) throws IOException, BadLocationException {
        container.setLayout(new MigLayout("fillx"));
        JPanel painelPersonagem = new MeuPanel(new MigLayout("fillx", "", ""));
        
        //É criado um label azul clicável para voltar à tela inicial
        JLabel voltarLabel = new LabelVoltar("Voltar", this, new PainelPrincipal(this.personagem,
                                                                                 this.personagens));
        container.add(voltarLabel, "align left, wrap");
        
        container.add(painelPersonagem, "align center, w 230:230:230");
        
        JPanel painel = new MeuPanel(new MigLayout("fillx", "", ""));
        painel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel nomePersonagem = new MeuLabel("<html><font color=\"black\"><u>"+ personagem.getNome());
        painel.add(nomePersonagem, "align center, wrap");
        
        //É criado timertask que cria um objeto que atualiza a barra de vida conforme a vida atual do personagem
        MeuTimer.timer.scheduleAtFixedRate(new InfoPersonagem(), 0, 1000/10);
        
        barraVida.setForeground(Color.GREEN);
        barraVida.setStringPainted(true);
        painel.add(barraVida, "growx, height 20:20:20, split 2");
        
        barraMana.setForeground(Color.decode("#2E75FA"));
        barraMana.setStringPainted(true);
        painel.add(barraMana, "growx, height 20:20:20, wrap");
        JLabel barraXpLabel = new MeuLabel("Xp: ");
        painel.add(barraXpLabel, "split 2");
        
        //É criado timertask que cria um objeto que atualiza a barra de xp conforme a xp atual do personagem
        MeuTimer.timer.scheduleAtFixedRate(new barraXp(), 0, 1000/10);

        barraXp.setForeground(Color.YELLOW);
        barraXp.setStringPainted(true);
        painel.add(barraXp, "gapleft 14px, height 20:20:20, growx, wrap");
        
        JButton botaoCurar = new JButton("Restaurar");
        botaoCurar.setForeground(Color.WHITE);
        botaoCurar.setBackground(Color.GREEN);
        botaoCurar.setFocusable(false);
        botaoCurar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                PainelPersonagem.this.personagem.curarVida(PainelPersonagem.this.personagem.getVidaMax());
                PainelPersonagem.this.personagem.setManaAtual(PainelPersonagem.this.personagem.getManaMax());
                
                try {
                    PainelPersonagem.this.personagens.atualizarInfoPersonagem();
                } catch (IOException ex) {
                    Logger.getLogger(PainelPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        painel.add(botaoCurar, "growx, wrap");
        
        //Mostra os status do personagem
        levelLabel = new MeuLabel("Level: "+ this.personagem.getLevel());
        painel.add(levelLabel, "wrap");
        ataqueLabel = new MeuLabel("Ataque: "+ this.personagem.getAtaque());
        painel.add(ataqueLabel, "wrap");
        danoMagicoLabel = new MeuLabel("Dano Mágico: "+ this.personagem.getDanoMagico());
        painel.add(danoMagicoLabel, "wrap");
        defesaLabel = new MeuLabel("Defesa: "+ this.personagem.getDefesa());
        painel.add(defesaLabel, "wrap");
        criticoLabel = new MeuLabel("Critico: "+ this.personagem.getChanceCrit());
        painel.add(criticoLabel, "wrap");
        evasaoLabel = new MeuLabel("Evasao: "+ this.personagem.getChanceEvasao());
        painel.add(evasaoLabel, "wrap");
        
        //Mostra os pontos de status livres
        JLabel pontosStatLabel = new MeuLabel("Pontos de status: ");
        painel.add(pontosStatLabel, "gaptop 5px, split 2");
        
        valorSPField = new JTextField(""+ personagem.getPontosStat(), 2);
        valorSPField.setDisabledTextColor(Color.BLACK);
        valorSPField.setEnabled(false);
        valorSPField.setHorizontalAlignment(JTextField.CENTER);
        
        painel.add(valorSPField, "wrap");
        
        //Informações referentes a força
        JLabel forLabel = new MeuLabel("Forca: ");
        forLabel.setToolTipText("<html>1 pt = +<font color=\"red\">1</font> ataque");
        JLabel valorForLabel = new MeuLabel(""+ personagem.getForca(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosForBotao = new MeuBotao(" < ");
        menosForBotao.setBorder(null);
        menosForBotao.addActionListener(new BotaoMenosStat(valorForLabel, valorSPField));
        JButton maisForBotao = new MeuBotao(" > ");
        maisForBotao.setBorder(null);  
        maisForBotao.addActionListener(new BotaoMaisStat(valorForLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("forca", maisForBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(this.personagem, "forca", menosForBotao, valorForLabel), 0, 1000/10);
        
        
        painel.add(forLabel, "split 4");
        painel.add(menosForBotao, "shrink 1000");
        painel.add(valorForLabel);
        painel.add(maisForBotao, "wrap");
        
        //Informações referentes a vitalidade
        JLabel vitLabel = new MeuLabel("Vitalidade: ");
        vitLabel.setToolTipText("<html>1 pt = +<font color=\"red\">3</font> vida máxima");
        JLabel valorVitLabel = new MeuLabel(""+ personagem.getVitalidade(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosVitBotao = new MeuBotao(" < ");
        menosVitBotao.setBorder(null);
        menosVitBotao.addActionListener(new BotaoMenosStat(valorVitLabel, valorSPField));
        JButton maisVitBotao = new MeuBotao(" > ");
        maisVitBotao.setBorder(null);  
        maisVitBotao.addActionListener(new BotaoMaisStat(valorVitLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("vitalidade", maisVitBotao, valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(this.personagem, "vitalidade", menosVitBotao, valorVitLabel), 0, 1000/10);

        painel.add(vitLabel, "split 4");
        painel.add(menosVitBotao, "shrink 1000");
        painel.add(valorVitLabel);
        painel.add(maisVitBotao, "wrap");
        
        //Informações referentes a agilidade
        JLabel agiLabel = new MeuLabel("Agilidade: ");
        agiLabel.setToolTipText("<html>2 pts = +<font color=\"red\">1</font>% chance de evasão");
        JLabel valorAgiLabel = new MeuLabel(""+ personagem.getAgilidade(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosAgiBotao = new MeuBotao(" < ");
        menosAgiBotao.setBorder(null);
        menosAgiBotao.addActionListener(new BotaoMenosStat(valorAgiLabel, valorSPField));
        JButton maisAgiBotao = new MeuBotao(" > ");
        maisAgiBotao.setBorder(null);  
        maisAgiBotao.addActionListener(new BotaoMaisStat(valorAgiLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("agilidade", maisAgiBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(this.personagem, "agilidade", menosAgiBotao, valorAgiLabel), 0, 1000/10);

        painel.add(agiLabel, "split 4");
        painel.add(menosAgiBotao, "shrink 1000");
        painel.add(valorAgiLabel);
        painel.add(maisAgiBotao, "wrap");
        
        //Informações referentes a sorte
        JLabel sorLabel = new MeuLabel("Sorte: ");
        sorLabel.setToolTipText("<html>1 pt = +<font color=\"red\">2.50</font>% chance de crítico");
        JLabel valorSorLabel = new MeuLabel(""+ personagem.getSorte(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosSorBotao = new MeuBotao(" < ");
        menosSorBotao.setBorder(null);
        menosSorBotao.addActionListener(new BotaoMenosStat(valorSorLabel, valorSPField));
        JButton maisSorBotao = new MeuBotao(" > ");
        maisSorBotao.setBorder(null);  
        maisSorBotao.addActionListener(new BotaoMaisStat(valorSorLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("sorte", maisSorBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(this.personagem, "sorte", menosSorBotao, valorSorLabel), 0, 1000/10);

        painel.add(sorLabel, "split 4");
        painel.add(menosSorBotao, "shrink 1000");
        painel.add(valorSorLabel);
        painel.add(maisSorBotao, "wrap");
        
        //Informações referentes a inteligencia
        JLabel intLabel = new MeuLabel("Inteligência: ");
        intLabel.setToolTipText("<html>1 pt = +<font color=\"red\">1</font> dano mágico");
        JLabel valorIntLabel = new MeuLabel(""+ personagem.getInteligencia(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosIntBotao = new MeuBotao(" < ");
        menosIntBotao.setBorder(null);
        menosIntBotao.addActionListener(new BotaoMenosStat(valorIntLabel, valorSPField));
        JButton maisIntBotao = new MeuBotao(" > ");
        maisIntBotao.setBorder(null);  
        maisIntBotao.addActionListener(new BotaoMaisStat(valorIntLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("inteligencia", maisIntBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(this.personagem, "inteligencia", menosIntBotao, valorIntLabel), 0, 1000/10);

        painel.add(intLabel, "split 4");
        painel.add(menosIntBotao, "shrink 1000");
        painel.add(valorIntLabel);
        painel.add(maisIntBotao, "wrap");
        
        //É criado um botão que reseta os valores para antes da primeira modificação(Valor status disponiveis)
        JButton botaoReset = new MeuBotao("Resetar");
        botaoReset.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botaoReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                valorForLabel.setText(""+ PainelPersonagem.this.personagem.getForca());
                valorVitLabel.setText(""+ PainelPersonagem.this.personagem.getVitalidade());
                valorAgiLabel.setText(""+ PainelPersonagem.this.personagem.getAgilidade());
                valorSorLabel.setText(""+ PainelPersonagem.this.personagem.getSorte());
                valorIntLabel.setText(""+ PainelPersonagem.this.personagem.getInteligencia());
                
                PainelPersonagem.this.statPointsDps = 0;
                
                valorSPField.setText(""+ PainelPersonagem.this.personagem.getPontosStat());
            }
        });
        
        botaoConfirmar = new MeuBotao("<html><u>Confirmar");
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Status antes da confirmação
                int forcaAtual = PainelPersonagem.this.personagem.getForca();
                int vitalidadeAtual = PainelPersonagem.this.personagem.getVitalidade();
                int agilidadeAtual = PainelPersonagem.this.personagem.getAgilidade();
                int sorteAtual = PainelPersonagem.this.personagem.getSorte();
                int inteligenciaAtual = PainelPersonagem.this.personagem.getInteligencia();
                
                //Status depois da confirmação
                int forcaNova = Integer.parseInt(valorForLabel.getText());
                int vitalidadeNova = Integer.parseInt(valorVitLabel.getText());
                int agilidadeNova = Integer.parseInt(valorAgiLabel.getText());
                int sorteNova = Integer.parseInt(valorSorLabel.getText());
                int inteligenciaNova = Integer.parseInt(valorIntLabel.getText());
                
                //Atualiza os status para depois da confirmação
                if(forcaNova > forcaAtual) PainelPersonagem.this.personagem.addForca(forcaNova - forcaAtual);
                if(vitalidadeNova > vitalidadeAtual) PainelPersonagem.this.personagem.addVitalidade(vitalidadeNova - vitalidadeAtual);
                if(agilidadeNova > agilidadeAtual) PainelPersonagem.this.personagem.addAgilidade(agilidadeNova - agilidadeAtual);
                if(sorteNova > sorteAtual) PainelPersonagem.this.personagem.addSorte(sorteNova - sorteAtual);
                if(inteligenciaNova > inteligenciaAtual) PainelPersonagem.this.personagem.addInteligencia(inteligenciaNova - inteligenciaAtual);
                
                PainelPersonagem.this.personagem.setPontosStat(Integer.parseInt(PainelPersonagem.this.valorSPField.getText()));
                
                PainelPersonagem.this.statPointsDps = 0;
                try {
                    PainelPersonagem.this.personagens.atualizarInfoPersonagem();
                } catch (IOException ex) {
                    Logger.getLogger(PainelPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        botaoConfirmar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        painel.add(botaoReset, "gaptop 5px, width 65, h 22, split 2");
        painel.add(botaoConfirmar, "gaptop 5px, gapleft 10px, wrap, w 65, h 22");
        
        JButton botaoMagias = new MeuBotao("Magias");
        botaoMagias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PainelPersonagem.this);
                try {
                    JDialog dialog = new MagiasPersonagemDialog(frame, personagem, personagens);
                } catch (IOException ex) {
                    Logger.getLogger(PainelPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        botaoMagias.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ImageIcon icone = new ImageIcon(new ImageIcon("elementos\\outros\\SpellBook.jpg").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT));
        botaoMagias.setIcon(icone);
        painel.add(botaoMagias, "growx, h 22");
        
        painelPersonagem.add(painel, "align center");
    }
    
    private class InfoPersonagem extends TimerTask {
        @Override
        public void run() {
            PainelPersonagem.this.levelLabel.setText("Level: "+ PainelPersonagem.this.personagem.getLevel());
            PainelPersonagem.this.ataqueLabel.setText("Ataque: "+ PainelPersonagem.this.personagem.getAtaque());
            PainelPersonagem.this.danoMagicoLabel.setText("Dano Mágico: "+ PainelPersonagem.this.personagem.getDanoMagico());
            PainelPersonagem.this.defesaLabel.setText("Defesa: "+ PainelPersonagem.this.personagem.getDefesa());
            
            DecimalFormat f = new DecimalFormat("###0.00", new DecimalFormatSymbols(Locale.ROOT));
            PainelPersonagem.this.criticoLabel.setText("Critico: "+ f.format(PainelPersonagem.this.personagem.getChanceCrit()*100) +"%");
            PainelPersonagem.this.evasaoLabel.setText("Evasao: "+ f.format(PainelPersonagem.this.personagem.getChanceEvasao()*100) +"%");
            PainelPersonagem.this.valorSPField.setText((""+ (personagem.getPontosStat() - statPointsDps)));
            
            if(PainelPersonagem.this.personagem.getPontosStat() >= 1) {
                PainelPersonagem.this.botaoConfirmar.setEnabled(true);
            } else {
                PainelPersonagem.this.botaoConfirmar.setEnabled(false);
            }
        
            int vidaAtual = PainelPersonagem.this.personagem.getVidaAtual();
            int vidaMax = PainelPersonagem.this.personagem.getVidaMax();
            JProgressBar barraVida = PainelPersonagem.this.barraVida;
            
            
            barraVida.setValue(vidaAtual);
            barraVida.setMaximum(vidaMax);
            barraVida.setString(vidaAtual+"/"+ vidaMax);
            
            int manaAtual = PainelPersonagem.this.personagem.getManaAtual();
            int manaMax = PainelPersonagem.this.personagem.getManaMax();
            JProgressBar barraMana = PainelPersonagem.this.barraMana;
            
            barraMana.setValue(manaAtual);
            barraMana.setMaximum(manaMax);
            barraMana.setString(manaAtual+"/"+ manaMax);
            
            PainelPersonagem.this.repaint();
            PainelPersonagem.this.revalidate();
        }
    }
    
    private class barraXp extends TimerTask {
        @Override
        public void run() {
            int xpAtual = PainelPersonagem.this.personagem.getXpAtual();
            int xpNec = PainelPersonagem.this.personagem.getXpNecessaria();
            JProgressBar barraXp = PainelPersonagem.this.barraXp;
            
            double pCento = (double) barraXp.getValue() / (double) barraXp.getMaximum() * 100;
            DecimalFormat f = new DecimalFormat("###0.00", new DecimalFormatSymbols(Locale.ROOT));
            barraXp.setToolTipText(f.format(pCento) +
                                   "% do nível "+ PainelPersonagem.this.personagem.getLevel());
            
            barraXp.setValue(xpAtual);
            barraXp.setMaximum(xpNec);
            barraXp.setString(xpAtual +"/"+ xpNec);
            
            PainelPersonagem.this.repaint();
            PainelPersonagem.this.revalidate();
        }
    }
    
    private class BotaoMaisStat implements ActionListener {
        private JLabel forNumLabel;
        private JTextField pontosStatField;
        
        private BotaoMaisStat(JLabel forNumLabel, JTextField pontosStatField) {
            this.forNumLabel = forNumLabel;
            this.pontosStatField = pontosStatField;
        }
        
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            int pts = Integer.parseInt(this.pontosStatField.getText());
            int forNum = Integer.parseInt(this.forNumLabel.getText());
            
            forNum++;
            pts--;
            
            this.pontosStatField.setText(""+ pts);
            this.forNumLabel.setText(""+ forNum);
            PainelPersonagem.this.statPointsDps++;
        }
    }
    
    private class BotaoMenosStat implements ActionListener {
        private JLabel forNumLabel;
        private JTextField pontosStatField;
        
        private BotaoMenosStat(JLabel forNumLabel, JTextField pontosStatField) {
            this.forNumLabel = forNumLabel;
            this.pontosStatField = pontosStatField;
        }
        
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            int pts = Integer.parseInt(this.pontosStatField.getText());
            int forNum = Integer.parseInt(this.forNumLabel.getText());
            
            forNum--;
            pts++;
            
            this.pontosStatField.setText(""+ pts);
            this.forNumLabel.setText(""+ forNum);
            PainelPersonagem.this.statPointsDps--;
        }
    }
}
