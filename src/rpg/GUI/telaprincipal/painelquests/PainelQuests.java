package rpg.GUI.telaprincipal.painelquests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.PainelPrincipal;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuTimer;
import rpg.itens.Item;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;
import rpg.quest.Quest;
import rpg.quest.Quests;

public class PainelQuests extends MeuPanel {
    private Personagem personagem;
    private Personagens personagens;
    private JPanel painel;
    private final JLabel exclamacao;
    
    public PainelQuests(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        exclamacao = new MeuLabel("<html><b><font color=\"yellow\"> ! </font></b></html>");
        painel = new MeuPanel(new MigLayout());
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }

    private void createComponents(Container container) throws IOException, BadLocationException {
        container.setLayout(new MigLayout("fillx"));
        
        //É criado um label azul clicável para voltar à tela anterior
        JLabel voltarLabel = new LabelVoltar("Voltar", this, new PainelPrincipal(this.personagem,
                                                                                  this.personagens));
        container.add(voltarLabel, "align left, wrap");
        
        ArrayList<Quest> quests = new Quests();
        
        //Pinta cada quest
        for(Quest quest : quests) {
            JPanel painelDentro = new MeuPanel(new MigLayout("fillx"));
            painelDentro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            //Pega o item necessitado pela quest e quantidade
            Item item = quest.getItem();
            int itemQtd = quest.getItemQtd();
            
            ImageIcon icone = new ImageIcon(item.getImg());
            JPanel painelItemImg = new InvSlotFundo(new MigLayout(""));
            JLabel itemImg = new JLabel(icone);
            painelItemImg.add(itemImg);
            painelDentro.add(painelItemImg);
            
            JPanel painelItemInfo = new MeuPanel(new MigLayout());
            JLabel labelItem = new MeuLabel("Item: ");
            painelItemInfo.add(labelItem, "split 2");
            JLabel labelNomeItem = new MeuLabel(item.getNome());
            painelItemInfo.add(labelNomeItem, "wrap");
            
            JLabel labelQtd = new MeuLabel("Quantidade: ");
            painelItemInfo.add(labelQtd, "split 2");
            
            JLabel labelQtdItem = new MeuLabel();
            painelItemInfo.add(labelQtdItem, "wrap");
            
            JLabel labelRecompensa = new MeuLabel("Recompensa: ");
            painelItemInfo.add(labelRecompensa, "split 2");
            JLabel labelRecompensaValor = new MeuLabel(quest.getXpRecompensa() +"xp");
            painelItemInfo.add(labelRecompensaValor, "wrap");
            
            painelDentro.add(painelItemInfo, "wrap, growx");
            
            JButton botaoRecompensa = new MeuBotao("Pegar");
            botaoRecompensa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    Personagem personagem = PainelQuests.this.personagem;
                    if(personagem.getInventario().remove(item.getId(), itemQtd)) {
                        //Defere a recompensa de xp ao personagem 
                        personagem.addXp(quest.getXpRecompensa());
                        
                        String xpRecebidaString = "<html><font color=\"blue\">"+ personagem.getNome() +"</font>"+
                                                  " recebeu <font color=\"yellow\">"+ quest.getXpRecompensa() +"</font>"+
                                                  "xp</html>";
                        JOptionPane.showMessageDialog(new JOptionPane(),
                                                      xpRecebidaString,
                                                      "Quest concluída", JOptionPane.INFORMATION_MESSAGE);                
                        
                        try {
                            PainelQuests.this.personagens.atualizarInfoPersonagem();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelQuests.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String itemInsuficienteString = "<html><font color=\"blue\">"+ personagem.getNome() +"</font>"+
                                                        " não possui itens suficientes";    
                        JOptionPane.showMessageDialog(new JOptionPane(),
                                                      itemInsuficienteString,
                                                      "Itens insuficientes", JOptionPane.ERROR_MESSAGE);                
                    }
                }
            });
            painelDentro.add(botaoRecompensa, "span, growx");
            
            boolean b = false;
            //É criado um botão que expande as informações da quest
            JButton botaoExpandir = new MeuBotao(quest.getItem().getNome());
            botaoExpandir.setIcon(icone);
            botaoExpandir.setLayout(new BorderLayout());
            botaoExpandir.setHorizontalAlignment(SwingConstants.LEFT);
            JLabel labelExpandirRecolher = new MeuLabel("+");
            botaoExpandir.add(labelExpandirRecolher, BorderLayout.EAST);
            botaoExpandir.addActionListener(new ActionListener() {
            private boolean auxBoolean;
                
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int i = painel.getComponentZOrder(botaoExpandir);
                    final String mais = "\\+";
                    final String menos = "\\-";
                    
                    if(auxBoolean == false) {
                        //Expande o painel com informações da quest e modifica o labelExpandirRecolher para '-'
                        labelExpandirRecolher.setText(labelExpandirRecolher.getText().replaceFirst(mais, menos));
                        painel.add(painelDentro, "pad -4 0 0 0, wrap, width 250", ++i);
                        auxBoolean = true;
                    } else {
                        //Recolhe o painel com informações da quest e modifica o labelExpandirRecolher para '+'
                        labelExpandirRecolher.setText(labelExpandirRecolher.getText().replaceFirst(menos, mais));
                        painel.remove(++i);
                        auxBoolean = false;
                    }
                    
                    PainelQuests.this.repaintPainel();
                }
                
                public ActionListener init(boolean b) {
                    this.auxBoolean = b;
                    
                    return this;
                }
            }.init(b));
            TimerTask timerTask = new TimerTask() {
                
                @Override
                public void run() {
                        int qtdPossuida = 0;
                        try {
                            qtdPossuida = PainelQuests.this.personagem.getInventario().getQtd(item.getId());
                            if(qtdPossuida >= itemQtd) {
                                botaoExpandir.add(exclamacao, BorderLayout.WEST);
                            } else {
                                botaoExpandir.remove(exclamacao);
                            }
                        } catch (Exception ex) {
                            botaoExpandir.remove(exclamacao);
                            painel.repaint();
                            painel.revalidate();
                        }
                    
                        painel.repaint();
                        painel.revalidate();
                        labelQtdItem.setText(qtdPossuida +"/"+ itemQtd);
                    }
            };
            MeuTimer.timer.scheduleAtFixedRate(timerTask, 0, 1000/10);
            painel.add(botaoExpandir, "width 250, wrap");
        }
        
        JScrollPane scrollPane = new JScrollPane(painel);
        scrollPane.setPreferredSize(new Dimension(283, 350));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        container.add(scrollPane, "align center");
        
    }
    
    //Repinta a tela de armazém e atualiza as informações do personagem
    public void repaintPainel() {
        this.painel.repaint();
        this.painel.revalidate();
    }
}
