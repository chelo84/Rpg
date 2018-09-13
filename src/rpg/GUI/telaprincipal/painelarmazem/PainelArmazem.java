package rpg.GUI.telaprincipal.painelarmazem;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.PainelPrincipal;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import rpg.itens.Item;
import rpg.personagem.Armazem;
import rpg.personagem.ArmazemSlots;
import rpg.personagem.InvSlots;
import rpg.personagem.Inventario;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;
import static rpg.GUI.telaprincipal.painelinventario.PainelInventario.IMG;

public class PainelArmazem extends MeuPanel {
    private final Personagem personagem;
    private final Personagens personagens;
    private final JPanel painelArmazem = new MeuPanel2(new MigLayout("fillx, wrap 5", "", ""));
    private final JPanel painelInventario = new MeuPanel2(new MigLayout("fillx, wrap 5", "", ""));
    
    public PainelArmazem(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }
    
    public void createComponents(Container container) throws IOException, BadLocationException {
        this.setLayout(new MigLayout("fillx", "", ""));
        
        JLabel voltarLabel = new LabelVoltar("Voltar", this, new PainelPrincipal(this.personagem,
                                                                                 this.personagens));
        this.add(voltarLabel, "wrap");
        
        this.paintArmazem();
                
        this.add(painelArmazem, "align center, wrap");
        
        this.add(new JSeparator(), "gaptop 15px, gapbottom 15px, growx, wrap");
        
        this.paintInventario();
        
        this.add(painelInventario, "align center");
    }

    //Pinta o painel de armazém
    private void paintArmazem() {
        painelArmazem.removeAll();

        //Pinta cada slot de armazém
        for(int i = 0; i < ArmazemSlots.values().length; i++) {
            try {
                Item item = personagem.getArmazem().getItem(i);
                Integer qtd = personagem.getArmazem().getQtd(i);

                JPanel slot = null;
                JLabel imgSlot = null;
                
                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    
                    JPopupMenu popup = new JPopupMenu();

                    JMenuItem retirar = new JMenuItem("Retirar");
                    retirar.addActionListener((ActionEvent ae) -> {
                        Armazem armazem = PainelArmazem.this.personagem.getArmazem();
                        Inventario inventario = PainelArmazem.this.personagem.getInventario();
                        
                        int quantidade = 0;
                        try {
                            if(qtd > 1) {
                                String input = JOptionPane.showInputDialog("Quantidade \"1~"+ qtd +"\"");
                                
                                //Se o usuário apertar "X" ou cancelar será lançado um NullPointerException
                                if(input == null) throw new NullPointerException();
                                
                                quantidade = Integer.parseInt(input);
                                
                                //Se a quantidade for menor que 1 ou maior que a quantidade existente
                                //é lançada uma exceção NumberFormatException
                                if(quantidade < 1 || quantidade > qtd) throw new NumberFormatException();
                                
                                //Se for possível adicionar o item e quantidade ao inventário do personagem
                                if(inventario.add(item, quantidade)) {
                                    //O item é removido do armazém
                                    armazem.remove(item.getId(), quantidade);
                                } else {
                                    //O inventário está cheio
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                            "Inventario cheio.",
                                            "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                //Se for possivel adicionar o item com 1 unidade
                                if(inventario.add(item, 1)) {
                                    //É removido o item do armazém
                                    armazem.remove(item.getId());
                                } else {
                                    //O inventário está cheio
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                            "Inventario cheio.",
                                            "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            
                            PainelArmazem.this.repaintPainel();
                            
                        } catch (NumberFormatException ex) {
                            //O valor inserido na entrada é menor que 1 ou maior que a quantidade existente
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                    "Insira um valor entre 1 e "+ qtd,
                                    "Valor invalido",
                                    JOptionPane.INFORMATION_MESSAGE);
                            
                        //Foi apertado "X" ou Cancelar
                        } catch (NullPointerException ex) {
                            //Dialog de input simplesmente é fechado

                        } catch (IOException ex) {
                            Logger.getLogger(PainelArmazem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    popup.add(retirar);

                    JMenuItem retirarTudo = new JMenuItem("Retirar tudo");
                    retirarTudo.addActionListener((ActionEvent ae) -> {
                        Armazem armazem = PainelArmazem.this.personagem.getArmazem();
                        Inventario inventario = PainelArmazem.this.personagem.getInventario();
                        
                        try {
                            //Se for possível adicionar o item e quantidade ao inventário do personagem
                            if(inventario.add(item, qtd)) {
                                //O item é removido do armazém
                                armazem.remove(item.getId(), qtd);
                            } else {
                                //O inventário está cheio
                                JOptionPane.showMessageDialog(new JOptionPane(),
                                        "Inventario cheio.",
                                        "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                            PainelArmazem.this.repaintPainel();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelArmazem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    popup.add(retirarTudo);
            
                    slot.setComponentPopupMenu(popup);
                    
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  

                    if(qtd > 1) {
                        //É criado um JLabel com a quantidade de item existente
                        JLabel labelQtd = new MeuLabel(""+ qtd);
                        slot.add(labelQtd, "pos 0 65%");
                    }
    
                    painelArmazem.add(slot);
                } else throw new IndexOutOfBoundsException();
            
            //Se não existir item
            } catch (IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(IMG);
                slot.add(imgSlot);
                painelArmazem.add(slot);
            }
        }
    }
    
    //Pinta o painel de inventário
    private void paintInventario() {
        painelInventario.removeAll();

        //Pinta cada slot de inventário
        for(int i = 0; i < InvSlots.values().length; i++) {
            try {
                Item item = personagem.getInventario().getItem(i);
                Integer qtd = personagem.getInventario().getQtd(i);

                JPanel slot = null;
                JLabel imgSlot = null;
                
                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    imgSlot = null;
                    JPopupMenu popup = new JPopupMenu();

                    JMenuItem depositar = new JMenuItem("Depositar");
                    depositar.addActionListener((ActionEvent ae) -> {
                        Armazem armazem = PainelArmazem.this.personagem.getArmazem();
                        Inventario inventario = PainelArmazem.this.personagem.getInventario();
                        
                        int quantidade = 0;
                        try {
                            //Se a quantidade de itens existentes for maior que 1
                            if(qtd > 1) {
                                String input = JOptionPane.showInputDialog("Quantidade \"1~"+ qtd +"\"");
                                
                                //Se o usuário apertar "X" ou cancelar será lançado um NullPointerException
                                if(input == null) throw new NullPointerException();
                                
                                quantidade = Integer.parseInt(input);                
                                
                                //Se a quantidade for menor que 1 ou maior que a quantidade existente
                                //é lançada uma exceção NumberFormatException
                                if(quantidade < 1 || quantidade > qtd) throw new NumberFormatException();
                                
                                //Se for possível adicionar o item e quantidade ao armazém
                                if(armazem.add(item, quantidade)) {
                                    //O item é removido do inventário do personagem
                                    inventario.remove(item.getId(), quantidade);
                                } else {
                                    //O armazém está cheio
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                            "Armazem cheio.",
                                            "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                //Se for possivel adicionar o item com 1 unidade
                                if(armazem.add(item, 1)) {
                                    //É removido o item do inventário do personagem
                                    inventario.remove(item.getId());
                                } else {
                                    //O armazém está cheio
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                            "Armazem cheio.",
                                            "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            
                            PainelArmazem.this.repaintPainel();
                            
                        } catch (NumberFormatException ex) {
                            //O valor inserido na entrada é menor que 1 ou maior que a quantidade existente
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                    "Insira um valor entre 1 e "+ qtd,
                                    "Valor invalido",
                                    JOptionPane.INFORMATION_MESSAGE);
                            
                        //Foi apertado "X" ou Cancelar
                        } catch (NullPointerException ex) {
                            
                        } catch (IOException ex) {
                            Logger.getLogger(PainelArmazem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    popup.add(depositar);
        
                    JMenuItem depositarTudo = new JMenuItem("Depositar tudo");
                    depositarTudo.addActionListener((ActionEvent ae) -> {
                        Armazem armazem = PainelArmazem.this.personagem.getArmazem();
                        Inventario inventario = PainelArmazem.this.personagem.getInventario();
                        
                        try {
                            //Se for possível adicionar o item e quantidade ao armazém
                            if(armazem.add(item, qtd)) {
                                //É removido o item do inventário do personagem
                                inventario.remove(item.getId(), qtd);
                            } else {
                                //O armazém está cheio
                                JOptionPane.showMessageDialog(new JOptionPane(),
                                        "Armazem cheio.",
                                        "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                            
                            PainelArmazem.this.repaintPainel();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelArmazem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    popup.add(depositarTudo);
    
                    slot.setComponentPopupMenu(popup);
                    
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
    
                    if(qtd > 1) {
                        //É criado um JLabel com a quantidade de item existente
                        JLabel labelQtd = new MeuLabel(""+ qtd);
                        slot.add(labelQtd, "pos 0 65%");
                    }
        
                    painelInventario.add(slot);
                } else throw new IndexOutOfBoundsException();
        
            //Se não existir item
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(IMG);
                slot.add(imgSlot);
                painelInventario.add(slot);
            }
        }
    }
        
    //Repinta a tela de armazém e atualiza as informações do personagem
    public void repaintPainel() throws IOException {
        this.paintArmazem();
        this.paintInventario();
        this.repaint();
        this.revalidate();
        
        this.personagens.atualizarInfoPersonagem();
    }
}
