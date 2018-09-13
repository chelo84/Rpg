package rpg.GUI.telaprincipal.painelaventura;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.PainelPrincipal;
import rpg.GUI.telaprincipal.painelaventura.batalha.PainelBatalha;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuPanel;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class PainelAventura extends MeuPanel {
    private Personagem personagem;
    private Personagens personagens;
    
    public PainelAventura(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }
    
    public void createComponents(Container container) throws IOException, BadLocationException {
        container.setLayout(new MigLayout("fillx"));
        
        JPanel painel = new MeuPanel(new MigLayout("fillx", "", ""));
        //É criado um label azul clicável para voltar à tela anterior
        LabelVoltar voltarLabel = new LabelVoltar("Voltar", this, new PainelPrincipal(this.personagem, this.personagens));
        
        container.add(voltarLabel, "growx, aligny top, alignx left, wrap");

        
        JPanel panelBotoes = new MeuPanel(new MigLayout("fillx, align center", "", "10px[]10px[]10px"));
        panelBotoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JButton botaoBueiro = new MeuBotao("Bueiro");
        botaoBueiro.addActionListener((ActionEvent ae) -> {
                try {
                    String area = botaoBueiro.getText();
                    area = area.replace(" ", "_");
                    PainelBatalha painelBueiro = new PainelBatalha(PainelAventura.this.personagem, PainelAventura.this.personagens, area);
                    
                    Component component1 = (Component) ae.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                    JPanel painel1 = (JPanel) frame.getContentPane();
                    
                    painel1.remove(container);
                    painel1.add(painelBueiro);
                    
                    painel1.repaint();
                    painel1.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        panelBotoes.add(botaoBueiro, "alignx center, spanx, wrap, width 175");
        
        JButton botaoFortTroll = new MeuBotao("Fortaleza dos Trolls");
        botaoFortTroll.addActionListener((ActionEvent ae) -> {
                try {
                    String area = botaoFortTroll.getText();
                    area = area.replace(" ", "_");
                    PainelBatalha painelFortTroll = new PainelBatalha(PainelAventura.this.personagem,
                                                                      PainelAventura.this.personagens, area);
                    
                    Component component1 = (Component) ae.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                    JPanel painel1 = (JPanel) frame.getContentPane();
                    
                    painel1.remove(container);
                    painel1.add(painelFortTroll);
                    
                    painel1.repaint();
                    painel1.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        panelBotoes.add(botaoFortTroll, "alignx center, spanx, wrap, width 175");
        
        JButton botaoFortOrc = new MeuBotao("Fortaleza dos Ogros");
        botaoFortOrc.addActionListener((ActionEvent ae) -> {
                try {
                    String area = botaoFortOrc.getText();
                    area = area.replace(" ", "_");
                    PainelBatalha painelFortOrc = new PainelBatalha(PainelAventura.this.personagem,
                                                                      PainelAventura.this.personagens, area);
                    
                    Component component1 = (Component) ae.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                    JPanel painel1 = (JPanel) frame.getContentPane();
                    
                    painel1.remove(container);
                    painel1.add(painelFortOrc);
                    
                    painel1.repaint();
                    painel1.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        panelBotoes.add(botaoFortOrc, "alignx center, spanx, wrap, width 175");
        
        JButton botaoFloresta = new MeuBotao("Floresta");
        botaoFloresta.addActionListener((ActionEvent ae) -> {
                try {
                    String area = botaoFloresta.getText();
                    area = area.replace(" ", "_");
                    PainelBatalha painelFloresta = new PainelBatalha(PainelAventura.this.personagem,
                                                                      PainelAventura.this.personagens, area);
                    
                    Component component1 = (Component) ae.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                    JPanel painel1 = (JPanel) frame.getContentPane();
                    
                    painel1.remove(container);
                    painel1.add(painelFloresta);
                    
                    painel1.repaint();
                    painel1.revalidate();
                } catch (IOException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadLocationException ex) {
                    Logger.getLogger(PainelAventura.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        panelBotoes.add(botaoFloresta, "alignx center, spanx, wrap, width 175");
        
        JButton botaoVoltar = new MeuBotao("<<");
        botaoVoltar.setEnabled(false);
        botaoVoltar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelBotoes.add(botaoVoltar, "gaptop 5px, align left, width 25, height 25");
        
        JButton botaoProx = new MeuBotao(">>");
        botaoProx.setEnabled(false);
        botaoProx.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelBotoes.add(botaoProx, "align right, width 25, height 25");
        
        painel.add(panelBotoes, "align center, wrap");
        
        container.add(painel, "align center");
    }
}
