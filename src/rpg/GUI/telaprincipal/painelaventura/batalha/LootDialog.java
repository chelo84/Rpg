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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.telaprincipal.painelinventario.PainelInventario;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.itens.Item;
import rpg.monstro.InvMonstroSlots;
import rpg.monstro.InventarioMonstro;
import rpg.monstro.Monstro;
import rpg.personagem.Inventario;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

//Criação da tela de loot do monstro
public class LootDialog extends JDialog {
    private Personagem personagem;
    private Personagens personagens;
    private Monstro monstro;
    private JPanel painelLoot;
    
    public LootDialog(JFrame frame, Personagem personagem, Personagens personagens, Monstro monstro) {
        super(frame, "", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.personagem = personagem;
        this.personagens = personagens;
        this.monstro = monstro;
        
        this.setTitle("Usar Item");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        painelLoot = new MeuPanel(new MigLayout("fillx, wrap 5"));
        paintLoot();
        
        this.add(painelLoot);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void paintLoot() {
        painelLoot.removeAll();
        
        //Pinta cada slot de inventário do monstro
        for(int i = 0; i < InvMonstroSlots.values().length; i++) {
            try {
                Item item = monstro.getDrop().getItem(i);
                Integer qtd = monstro.getDrop().getQtd(i);

                JPanel slot;
                JLabel imgSlot;

                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    JPopupMenu popup = new JPopupMenu();
            
                    JMenuItem pegarTudo = new JMenuItem("Pegar tudo");
                    pegarTudo.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Inventario inventarioPersonagem = LootDialog.this.personagem.getInventario();
                            InventarioMonstro dropMonstro = LootDialog.this.monstro.getDrop();
                            
                            //Enquanto o inventário do monstro for maior ou igual a 1
                            while(dropMonstro.size() >= 1) {
                                //É pegado o primeiro item do inventário do monstro e a quantidade
                                Item itemFor = dropMonstro.getItem(0);
                                int quantidade = dropMonstro.getQtd(itemFor.getId());
                                
                                try {
                                    //Se for possível adicionar o item e quantidade ao inventário do personagem
                                    if(inventarioPersonagem.add(itemFor, quantidade)) {
                                        //O item é removido do inventário do monstro
                                        dropMonstro.remove(itemFor.getId(), qtd);
                                    } else {     
                                        //O inventário do personagem está cheio
                                        JOptionPane.showMessageDialog(new JOptionPane(),
                                                                      "Inventario cheio.",
                                                                      "Erro", JOptionPane.ERROR_MESSAGE);
                                        
                                        LootDialog.this.repaintPainel();
                                        return;
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(LootDialog.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                            try {
                                LootDialog.this.repaintPainel();
                            } catch (IOException ex) {
                                Logger.getLogger(LootDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    popup.add(pegarTudo);
    
                    JMenuItem pegar = new JMenuItem("Pegar");
                    pegar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Inventario inventarioPersonagem = LootDialog.this.personagem.getInventario();
                            InventarioMonstro dropMonstro = LootDialog.this.monstro.getDrop();
                    
                            try {
                                //Se for possível adicionar o item e quantidade ao inventário do personagem
                                if(inventarioPersonagem.add(item, qtd)) {
                                    //O item é removido do inventário do monstro
                                    dropMonstro.remove(item.getId(), qtd);
                                } else {     
                                    //O inventário do personagem está cheio
                                    JOptionPane.showMessageDialog(new JOptionPane(), "Inventario cheio.", "Erro", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(LootDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            try {
                                LootDialog.this.repaintPainel();
                            } catch (IOException ex) {
                                Logger.getLogger(LootDialog.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }                
                    });
                    popup.add(pegar);

                    slot.setComponentPopupMenu(popup);
                
                    if(qtd > 1) {
                        //É criado um JLabel com a quantidade de item existente
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
        

                    painelLoot.add(slot);
                } else throw new IndexOutOfBoundsException();
            //Se não existir item
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelLoot.add(slot);
            }
        }
    }
        
    //Repinta a tela de loot e atualiza as informações do personagem    
    public void repaintPainel() throws IOException {
        this.paintLoot();
        this.repaint();
        this.revalidate();
        
        this.personagens.atualizarInfoPersonagem();
    }
}
