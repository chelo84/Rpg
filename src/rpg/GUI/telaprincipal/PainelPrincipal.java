package rpg.GUI.telaprincipal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telainicio.TelaInicio;
import rpg.GUI.telaprincipal.painelarmazem.PainelArmazem;
import rpg.GUI.telaprincipal.painelaventura.PainelAventura;
import rpg.GUI.telaprincipal.painelinventario.PainelInventario;
import rpg.GUI.telaprincipal.painelloja.PainelLoja;
import rpg.GUI.telaprincipal.painelpersonagem.PainelPersonagem;
import rpg.GUI.telaprincipal.painelquests.PainelQuests;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuLabelClicavel;
import rpg.GUI.util.MeuPanel;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class PainelPrincipal extends MeuPanel {

    private final Personagem personagem;
    private final Personagens personagens;
    
    public PainelPrincipal(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }

    private void createComponents(Container container) {
        container.setLayout(new MigLayout("fillx"));
        
        JPanel painel = new MeuPanel(new MigLayout("fill"));
        //É criado um painel para os botões
        JPanel painelBotoes = new MeuPanel(new MigLayout("fillx", "[]", "10px[]15px[]-5px"));
        //Cria uma borda fina preta para o painel dos botões
        painelBotoes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //É criado um label azul clicável para voltar à tela inicial
        JLabel voltarTelaInicio = new MeuLabelClicavel("Voltar à tela inicial");
        voltarTelaInicio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {  
                try {
                    Component component = (Component) e.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component);

                    frame.dispose();
                    TelaInicio inicio = new TelaInicio();
                } catch (IOException ex) {
                    Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        container.add(voltarTelaInicio, "growx, aligny top, alignx left, wrap");
        
        //É criado um botão que abre a tela de aventura
        JButton botaoAventura = new MeuBotao("Aventura");
        botaoAventura.setPreferredSize(new Dimension(200, 0));
        botaoAventura.addActionListener((ActionEvent ae) -> {
            try {
                PainelAventura painelAventura = new PainelAventura(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();
                
                painel1.remove(container);
                painel1.add(painelAventura);
                
                painel1.repaint();
                painel1.revalidate();
            }catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        painelBotoes.add(botaoAventura, "align center, wrap");
        
        //É criado um botão que abre a tela do personagem
        JButton botaoPersonagem = new MeuBotao("Personagem");
        botaoPersonagem.addActionListener((ActionEvent ae) -> {
            PainelPersonagem painelPersonagem;
            try {
                painelPersonagem = new PainelPersonagem(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();

                painel1.remove(PainelPrincipal.this);
                painel1.add(painelPersonagem);

                painel1.repaint();
                painel1.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        botaoPersonagem.setPreferredSize(new Dimension(200, 0));
        painelBotoes.add(botaoPersonagem, "growx, align center, wrap");
        
        //É criado um botão que abre a tela de inventário
        JButton botaoInventario = new MeuBotao("Inventário");
        botaoInventario.addActionListener((ActionEvent ae) -> {
            try {
                PainelInventario painelInventario = new PainelInventario(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();
                
                painel1.remove(PainelPrincipal.this);
                painel1.add(painelInventario);
                
                painelInventario.repaintPainel();
                
                painel1.repaint();
                painel1.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
        botaoInventario.setPreferredSize(new Dimension(200, 0));
        painelBotoes.add(botaoInventario, "align center, wrap");
        
        //É criado um botão que abre a tela de loja
        JButton botaoLoja = new MeuBotao("Loja");
        botaoLoja.addActionListener((ActionEvent ae) -> {
            try {
                PainelLoja painelLoja = new PainelLoja(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();
                
                painel1.remove(PainelPrincipal.this);
                painel1.add(painelLoja);
                
                painelLoja.repaintPainel();
                
                painel1.repaint();
                painel1.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });           
        botaoLoja.setPreferredSize(new Dimension(200, 0));
        painelBotoes.add(botaoLoja, "align center, wrap");
        
        //É criado um botão que abre a tela de quests
        JButton botaoQuest = new MeuBotao("Quests");
        botaoQuest.addActionListener((ActionEvent ae) -> {
            try {
                PainelQuests painelQuests = new PainelQuests(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();
            
                painel1.remove(PainelPrincipal.this);
                painel1.add(painelQuests);
            
                painelQuests.repaintPainel();
                painel1.repaint();
                painel1.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        botaoQuest.setPreferredSize(new Dimension(200, 0));
        painelBotoes.add(botaoQuest, "align center, wrap");     

        //É criado um botão que abre a tela de armazém
        JButton botaoArmazem = new MeuBotao("Armazém");
        botaoArmazem.addActionListener((ActionEvent ae) -> {
            try {
                PainelArmazem painelArmazem = new PainelArmazem(personagem, personagens);
                Component component1 = (Component) ae.getSource();
                JFrame frame = (JFrame) SwingUtilities.getRoot(component1);
                JPanel painel1 = (JPanel) frame.getContentPane();
                
                painel1.remove(PainelPrincipal.this);
                painel1.add(painelArmazem);
            
                painelArmazem.repaintPainel();            
                painel1.repaint();
                painel1.revalidate();
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadLocationException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        botaoArmazem.setPreferredSize(new Dimension(200, 0));
        painelBotoes.add(botaoArmazem, "align center, wrap");
        
        //O painel de botões é adicionado ao painel principal
        painel.add(painelBotoes, "span, aligny top, alignx center, wrap");
        container.add(painel, "align center, gaptop 40px");
    }
}
