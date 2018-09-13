package rpg.GUI.telaprincipal.painelaventura.batalha;

import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.telaprincipal.painelinventario.PainelInventario;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.itens.Item;
import rpg.personagem.InvSlots;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

//Criação da tela de usar item
public class UsarItemDialog extends JDialog {
    private Personagem personagem;
    private Personagens personagens;
    private JPanel painelInventario;
    
    public UsarItemDialog(JFrame frame, Personagem personagem, Personagens personagens) {
        super(frame, "", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.personagem = personagem;
        this.personagens = personagens;
        this.painelInventario = new MeuPanel(new MigLayout("fillx, wrap 5", "", ""));
        
        this.setTitle("Usar Item");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paintInventario();
        
        this.add(painelInventario);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    //Pinta o painel de inventário
    public void paintInventario() {
        painelInventario.removeAll();
                
        int itensNaoUsaveis = 0;
        //Pinta cada slot de inventário
        for(int i = 0; i < InvSlots.values().length; i++) {
            try {
                Item item = personagem.getInventario().getItem(i);
                Integer qtd = personagem.getInventario().getQtd(i);

                JPanel slot;
                JLabel imgSlot;
                //Se o item não for nulo, ou seja, existir um item naquele slot
                //E o item for usável
                if(item != null && item.isUsavel()) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    JPopupMenu popup = new JPopupMenu();
            
                    JMenuItem usar = new JMenuItem("Usar");
                    usar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            UsarItemDialog.this.personagem.usarItem(item);
                            personagem.getInventario().remove(item.getId());
                    
                            try {
                                UsarItemDialog.this.repaintPainel();
                            } catch (IOException ex) {
                                Logger.getLogger(UsarItemDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    popup.add(usar);
            
                    JMenuItem usarCinco = new JMenuItem("Usar 5");
                    usarCinco.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            final int USARCINCOVEZES = 5;
                            //Tenta usar o item cinco vezes
                            for(int i = 0; i < USARCINCOVEZES; i++) {
                                //Se o item existir no inventário do personagem
                                if(personagem.getInventario().getItem(item.getId()) != null) {
                                    UsarItemDialog.this.personagem.usarItem(item);
                                    
                                    personagem.getInventario().remove(item.getId());
                                }
                            }
        
                            try {
                                UsarItemDialog.this.repaintPainel();
                            } catch (IOException ex) {
                                Logger.getLogger(UsarItemDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    popup.add(usarCinco);
        
                    slot.setComponentPopupMenu(popup);
                    
                    if(qtd > 1) {
                        JLabel labelQtd = new MeuLabel(""+ qtd);
                        slot.add(labelQtd, "pos 0 65%");
                    }
                    
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
    
    
                    painelInventario.add(slot);
    
                } else {
                    itensNaoUsaveis++;
                }
    
            //Se não existir item
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelInventario.add(slot);
            }
        }
        
        //É gerado slots vazios caso o item exista porém não seja usável
        while(itensNaoUsaveis > 0) {
            JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
            JLabel imgSlot = new JLabel(PainelInventario.IMG);
            slot.add(imgSlot);
            painelInventario.add(slot);
            
            itensNaoUsaveis--;
        }
    }
    
    //Repinta a tela de usar item e atualiza as informações do personagem
    public void repaintPainel() throws IOException {
        this.paintInventario();
        this.repaint();
        this.revalidate();
        
        this.personagens.atualizarInfoPersonagem();
    }
}
