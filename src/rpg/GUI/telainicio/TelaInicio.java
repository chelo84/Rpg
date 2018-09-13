package rpg.GUI.telainicio;

import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telacriarpersonagem.TelaCriarPersonagem;
import rpg.GUI.telaprincipal.TelaPrincipal;
import rpg.GUI.util.MeuLabelSublinhado;
import rpg.GUI.util.MeuTimer;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;
import rpg.personagem.Stats;

public class TelaInicio {
    private JFrame frame;
    private JList minhaLista = null;
    private JScrollPane listScroller;
    private JButton removerPersonagem = new MeuBotao();
    private JPanel painel;
            
    public TelaInicio() throws IOException {
        frame = new JFrame();
        frame.setTitle("RPG");
        frame.setPreferredSize(new Dimension(350, 450));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createComponents(frame.getContentPane());
        
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        MeuTimer.timer.scheduleAtFixedRate(new RemoverBotaoTask(), 0, 1000/10);
    }
    
    public void createComponents(Container container) throws IOException {
        painel = new MeuPanel(new MigLayout("fill, align center","",""));
        
        JLabel label = new MeuLabelSublinhado("Escolha o personagem");
        painel.add(label, "align center, wrap");
        
        Personagens personagens = new Personagens();
        Vector<String> nomes = new Vector<>();
        
        //Verifica se existem personagens criados
        if(personagens.size() > 0) {
        
        //Se sim, gera uma lista com os nomes de todos os personagens
        for(int i = 0; i < personagens.size(); i++) {
            nomes.add(personagens.get(i).getNome());
        }
        
        //Então é gerado um JList com os nomes que podem ser selecionados
        minhaLista = new JList(nomes);
        minhaLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        minhaLista.setSelectedIndex(0);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) minhaLista.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        minhaLista.setBackground(Color.decode("#5D6D7E"));
        minhaLista.setForeground(Color.WHITE);

        listScroller = new JScrollPane(minhaLista);
        listScroller.setPreferredSize(new Dimension(150, 250));
        listScroller.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painel.add(listScroller, "align center, wrap");
        
        //Caso não exista personagem criado é apresentado um painel escrito "Nenhum personagem criado"
        } else {
            JPanel painelListaVazia = new MeuPanel2(new MigLayout("fill"));
            painelListaVazia.setPreferredSize(new Dimension(150, 250));
            
            JLabel listaVaziaLabel = new JLabel("Nenhum personagem criado");
            listaVaziaLabel.setForeground(Color.WHITE);
            painelListaVazia.add(listaVaziaLabel, "align center");
            
            painel.add(painelListaVazia, "wrap, align center");
        }
        
        //É criado um botão para remover o personagem selecionado
        removerPersonagem = new MeuBotao("Remover personagem");
        removerPersonagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(new JOptionPane(), "Deseja realmente remover este personagem?",
                                                          "", JOptionPane.YES_NO_OPTION);
                
                if(reply == JOptionPane.YES_OPTION) {
                    Personagens personagens = null;
                    try {
                        personagens = new Personagens();
                    } catch (IOException ex) {
                        Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    Personagem personagem = personagens.get(minhaLista.getSelectedValue().toString());
                
                    personagens.remove(personagem.getNome());
                    nomes.remove(personagem.getNome());
                
                    try {
                        personagens.atualizarInfoPersonagem();
                    } catch (IOException ex) {
                        Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    painel.repaint();
                    painel.revalidate();
                }
            }
        });
        painel.add(removerPersonagem, "align center, wrap");
        
        //É criado um botão que abre um frame
        //para a criação de um novo personagem
        JButton criarPersonagem = new MeuBotao("Criar personagem");
        criarPersonagem.addActionListener(new ActionListener() {
            private JFrame frame;
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    TelaInicio.this.frame.dispose();
                    new TelaCriarPersonagem(this.frame);
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private ActionListener init(JFrame frame) {
                this.frame = frame;
                
                return this;
            }
        }.init(this.frame));
        painel.add(criarPersonagem, "split 2, align center");
        
        //Aqui é criado um botão que abre o jogo com o personagem selecionado
        JButton entrar = new MeuBotao("Entrar");
        //Caso a lista NÃO esteja vazia, o botão estará habilitado, e desabilitado caso contrário
        entrar.setEnabled((minhaLista != null) ? true : false);
        entrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Personagens personagens = null;
                try {
                    personagens = new Personagens();
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Personagem personagem = personagens.get(minhaLista.getSelectedValue().toString());
                
                frame.dispose();
                try {
                    try {
                        Stats.level = personagem.getLevel();
                        Stats.forca = personagem.getForca();
                        Stats.vitalidade = personagem.getVitalidade();
                        Stats.agilidade = personagem.getAgilidade();
                        Stats.sorte = personagem.getSorte();
                        Stats.inteligencia = personagem.getInteligencia();
                        Stats.ataque = personagem.getAtaque();
                        Stats.danoMagico = personagem.getDanoMagico();
                        Stats.defesa = personagem.getDefesa();
                        
                        TelaPrincipal tela = new TelaPrincipal(personagem, personagens);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        painel.add(entrar);
        
        container.add(painel);
    }
    
    //Essa é uma classe que verifica se o botão remover foi clicado,
    //caso o botão remover seja clicado, a lista com nomes de personagens 
    //será atualizada
    private class RemoverBotaoTask extends TimerTask {
        @Override
        public void run() {
            try {
                if(minhaLista.getSelectedValue() != null) {
                    removerPersonagem.setEnabled(true);
                }

            } catch (NullPointerException ex) {
                removerPersonagem.setEnabled(false);
            } catch (ArrayIndexOutOfBoundsException ex) {
                removerPersonagem.setEnabled(false);
            }
        }
    }
}
