package rpg.GUI.telaprincipal.painelloja;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telaprincipal.PainelPrincipal;
import rpg.GUI.util.InvSlotFundo;
import rpg.GUI.telaprincipal.painelinventario.PainelInventario;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import rpg.itens.Item;
import rpg.loja.Loja;
import rpg.personagem.InvSlots;
import rpg.personagem.Inventario;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class PainelLoja extends MeuPanel {
    private final int TAMANHOPADRAOLOJA = 15;
    private Personagem personagem;
    private Personagens personagens;
    private Loja loja;
    private JPanel painelEquips = new MeuPanel2(new MigLayout("fillx, wrap 5"));
    private JPanel painelUsaveis = new MeuPanel2(new MigLayout("fillx, wrap 5"));
    private JPanel painelMagias = new MeuPanel2(new MigLayout("fillx, wrap 5"));
    private JPanel painelInventario = new MeuPanel2(new MigLayout("fillx, wrap 5"));
    private JScrollPane scrollEquips;
    private JScrollPane scrollUsaveis;
    private JScrollPane scrollMagias;
    
    public PainelLoja(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        this.loja = new Loja();
        
        this.createComponents(getInstance());
    }
    
    public final JPanel getInstance() {
        return this;
    }
    
    public void createComponents(Container container) throws IOException, BadLocationException {
        container.setLayout(new MigLayout("fillx", "", ""));
        
        JLabel voltarLabel = new LabelVoltar("Voltar", this, new PainelPrincipal(this.personagem,
                                                                                 this.personagens));
        container.add(voltarLabel, "wrap");
        
        JTabbedPane tabbedPanel = new JTabbedPane();
        
        paintLoja();
        scrollEquips = new JScrollPane(painelEquips);
        scrollEquips.setPreferredSize(new Dimension(290, 172));
        scrollEquips.getVerticalScrollBar().setUnitIncrement(10);
        tabbedPanel.addTab("Equips", scrollEquips);
        
        scrollUsaveis = new JScrollPane(painelUsaveis);
        scrollUsaveis.setPreferredSize(new Dimension(290, 172));
        scrollUsaveis.getVerticalScrollBar().setUnitIncrement(10);
        tabbedPanel.addTab("Usaveis", scrollUsaveis);
        
        scrollMagias = new JScrollPane(painelMagias);
        scrollMagias.setPreferredSize(new Dimension(290, 172));
        scrollMagias.getVerticalScrollBar().setUnitIncrement(10);
        tabbedPanel.addTab("Magias", scrollMagias);
        
        container.add(tabbedPanel, "align center, wrap");
        
        container.add(new JSeparator(), "gaptop 15px, gapbottom 15px, growx, wrap");
        
        this.paintInventario();
        this.add(painelInventario, "alignx center");
    }
    
    public void paintLoja() {
        this.paintEquips();
        this.paintUsaveis();
        this.paintMagias();
    }

    //Pinta o painel que contém itens equipáveis
    private void paintEquips() {
        this.painelEquips.removeAll();
        
        //É pego o número de itens deste tipo que a loja possui
        int tamanho = this.loja.getEquips().size();
        //Caso o valor seja menor que o tamanho padrão, é atribuido o valor de tamanho padrão
        //Caso não, o tamanho é mantido
        tamanho = (tamanho >= TAMANHOPADRAOLOJA) ? tamanho : TAMANHOPADRAOLOJA;
        
        //Pinta cada slot de itens deste tipo
        for(int i = 0; i < tamanho; i++) {
            try {
                Item item = loja.getEquips().get(i);

                JPanel slot;
                JLabel imgSlot;

                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    JPopupMenu popup = new JPopupMenu();
                    
                    JMenuItem comprar = new JMenuItem("Comprar");
                    comprar.addActionListener(new ComprarListener(item));
                    popup.add(comprar);
                    slot.setComponentPopupMenu(popup);
            
                    JLabel labelPreco = new MeuLabel(""+ item.getPrecoCompra());
                    labelPreco.setForeground(Color.YELLOW);
                    //Posiciona o preço no canto inferior esquerdo
                    slot.add(labelPreco, "pos 0 65%");
                    
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
                
                    painelEquips.add(slot);
                    
                //Se não existir item
                } else {
                    throw new IndexOutOfBoundsException();
                }
                
            //Caso esta exceção seja lançada significa que não existe item no slot
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelEquips.add(slot);
            }
        }
    }
    
    //Pinta o painel que contém itens usáveis
    private void paintUsaveis() {
        this.painelUsaveis.removeAll();
        
        //É pego o número de itens deste tipo que a loja possui
        int tamanho = this.loja.getUsaveis().size();
        //Caso o valor seja menor que o tamanho padrão, é atribuido o valor de tamanho padrão
        //Caso não, o tamanho é mantido
        tamanho = (tamanho >= TAMANHOPADRAOLOJA) ? tamanho : TAMANHOPADRAOLOJA;
        
        //Pinta cada slot de itens deste tipo
        for(int i = 0; i < tamanho; i++) {
            try {
                Item item = loja.getUsaveis().get(i);

                JPanel slot;
                JLabel imgSlot;

                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    JPopupMenu popup = new JPopupMenu();
            
                    JMenuItem comprar = new JMenuItem("Comprar");
                    comprar.addActionListener(new ComprarListener(item));
                    popup.add(comprar);
                    slot.setComponentPopupMenu(popup);
            
                    JLabel labelPreco = new MeuLabel(""+ item.getPrecoCompra());
                    labelPreco.setForeground(Color.YELLOW);
                    //Posiciona o preço no canto inferior esquerdo
                    slot.add(labelPreco, "pos 0 65%");
            
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
            
                    painelUsaveis.add(slot);

                //Se não existir item
                } else {
                    throw new IndexOutOfBoundsException();
                }

            //Caso esta exceção seja lançada significa que não existe item no slot
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelUsaveis.add(slot);
            }
        }
    }
    
    //Pinta o painel que contém itens de magias
    private void paintMagias() {
        this.painelMagias.removeAll();
        
        //É pego o número de itens deste tipo que a loja possui
        int tamanho = this.loja.getMagias().size();
        //Caso o valor seja menor que o tamanho padrão, é atribuido o valor de tamanho padrão
        //Caso não, o tamanho é mantido
        tamanho = (tamanho >= TAMANHOPADRAOLOJA) ? tamanho : TAMANHOPADRAOLOJA;
        
        //Pinta cada slot de itens deste tipo
        for(int i = 0; i < tamanho; i++) {
            try {
                Item item = loja.getMagias().get(i);

                JPanel slot;
                JLabel imgSlot;

                //Se o item não for nulo, ou seja, existir um item naquele slot
                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    JPopupMenu popup = new JPopupMenu();
            
                    JMenuItem comprar = new JMenuItem("Comprar");
                    comprar.addActionListener(new ComprarListener(item));
                    popup.add(comprar);
                    slot.setComponentPopupMenu(popup);
            
                    JLabel labelPreco = new MeuLabel(""+ item.getPrecoCompra());
                    labelPreco.setForeground(Color.YELLOW);
                    //Posiciona o preço no canto inferior esquerdo
                    slot.add(labelPreco, "pos 0 65%");
            
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
            
                    painelMagias.add(slot);

                //Se não existir item
                } else {
                    throw new IndexOutOfBoundsException();
                }

            //Caso esta exceção seja lançada significa que não existe item no slot
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelMagias.add(slot);
            }
        }
    }

    public void paintInventario() {
        painelInventario.removeAll();

        for(int i = 0; i < InvSlots.values().length; i++) {
            try {
                Item item = personagem.getInventario().getItem(i);
                Integer qtd = personagem.getInventario().getQtd(i);

                JPanel slot = null;
                JLabel imgSlot = null;

                if(item != null) {
                    slot = new InvSlotFundo(new MigLayout("", "", ""));
                    imgSlot = null;
                    JPopupMenu popup = new JPopupMenu();
            
                    JMenuItem vender = new JMenuItem("Vender");
                    vender.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Inventario inventario = PainelLoja.this.personagem.getInventario();
                            int quantidade = 0;
                            try {
                                if(qtd > 1) {
                                    String input = JOptionPane.showInputDialog("Quantidade \"1~"+ qtd +"\"", qtd);
    
                                    if(input == null) throw new NullPointerException();

                                    quantidade = Integer.parseInt(input);
    
                                    if(quantidade < 1 || quantidade > qtd) throw new NumberFormatException();
                                } else {
                                    quantidade = 1;
                                }
                            } catch (NullPointerException ex) {
                                return;
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(new JOptionPane(),
                                                              "Insira um valor entre 1 e "+ qtd,
                                                              "Valor invalido", JOptionPane.INFORMATION_MESSAGE);                
                                return;
                            }
                            int precoFinal = (item.getPrecoVenda() * quantidade);
                            
                            //String mostrada de confirmação de venda do item
                            String venderConfirm = "<html>Vender <font color=\"blue\">"+ item.getNome() +"</font>x"+
                                                   quantidade +" por <font color=\"green\">"+ precoFinal +
                                                   "</font> ouro(s)?";
                            //É pedido uma confirmação do usuário para a venda
                            int reply = JOptionPane.showConfirmDialog(new JOptionPane(),
                                                                      venderConfirm,
                                                                      "", JOptionPane.YES_NO_OPTION);
                            //Se confirmada
                            if(reply == JOptionPane.YES_OPTION) {
                                try {
                                    //Se for possível adicionar a quantidade de ouro ao inventário do personagem
                                    if(inventario.add("ouro", precoFinal)) {
                                        //Se for possivel remover a quantidade de itens do inventário do personagem
                                        if(inventario.remove(item.getId(), quantidade)) {
                                            PainelLoja.this.repaintPainel();
                                        } else {
                                            //Senão, é removida a quantidade de ouro que foi adicionada
                                            inventario.remove("ouro", precoFinal);
                                            //E isso ocorreu pela falta de item
                                            JOptionPane.showMessageDialog(new JOptionPane(), "Item insuficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(new JOptionPane(), "Inventario cheio.", "Erro", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            } 
                        }
                    });
                    popup.add(vender);
            
                    //Se o preço de venda do item for menor que 1
                    if(item.getPrecoVenda() < 1) {
                        //Não é possível vender o item em questão
                        vender.setEnabled(false);
                    }
        
                    slot.setComponentPopupMenu(popup);
                    
                    if(qtd > 1) {
                        JLabel labelQtd = new MeuLabel(""+ qtd);
                        slot.add(labelQtd, "pos 0 65%");
                    }
                
                    JLabel labelPreco = new MeuLabel(""+ item.getPrecoVenda());
                    labelPreco.setForeground(Color.YELLOW);
                    //Dependendo do preço a posição do JLabel é modificada para melhor se encaixar no painel
                    if(item.getPrecoVenda() < 10) slot.add(labelPreco, "pos 80% 65%");
                    else if(item.getPrecoVenda() >= 10 && item.getPrecoVenda() < 100) slot.add(labelPreco, "pos 65% 65%");
                    else if(item.getPrecoVenda() >= 100) slot.add(labelPreco, "pos 50% 65%");
        
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel(icone);
                    
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    slot.add(imgSlot);  
    
                    painelInventario.add(slot);
    
                } else throw new IndexOutOfBoundsException();    
            //Se não existir item
            }catch(IndexOutOfBoundsException ex) {
                //É criado um slot sem imagem/sem item
                JPanel slot = new InvSlotFundo(new MigLayout("", "", ""));
                JLabel imgSlot = new JLabel(PainelInventario.IMG);
                slot.add(imgSlot);
                painelInventario.add(slot);
            }
        }
    }

    //Repinta a tela de armazém e atualiza as informações do personagem
    public void repaintPainel() throws IOException {
        this.paintEquips();
        this.paintUsaveis();
        this.paintMagias();
        this.paintInventario();
        this.repaint();
        this.revalidate();
        
        this.personagens.atualizarInfoPersonagem();
    }
    
    //ActionListener para compra de item
    private class ComprarListener implements ActionListener {
        private Item item;
        
        public ComprarListener(Item item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            Inventario inventario = PainelLoja.this.personagem.getInventario();
            int quantidade = 0;
            try {
                //Se a quantidade máxima do item for maior que 1
                if(item.getQtdMax() > 1) {
                    //Quantidade máxima que se pode comprar com o ouro atual do personagem
                    int maxItem = inventario.getQtd("ouro") / item.getPrecoCompra();
                    String input = JOptionPane.showInputDialog("Quantidade \"1~"+ maxItem +"\"", ""+ maxItem);

                    //Se o usuário apertar "X" ou cancelar será lançado um NullPointerException
                    if(input == null) throw new NullPointerException();

                    quantidade = Integer.parseInt(input);

                    //Se a quantidade for menor que 1 é lançada uma exceção NumberFormatException
                    if(quantidade < 1) throw new NumberFormatException();
                } else {
                    //Senão a quantidade é 1(um)
                    quantidade = 1;
                }
            } catch (NullPointerException ex) {
                //Dialog de input simplesmente é fechado
                return;
            } catch (NumberFormatException ex) {
                //O valor inserido na entrada é menor que 1 ou maior que a quantidade máxima do item
                JOptionPane.showMessageDialog(new JOptionPane(),
                                              "Insira um valor entre 1 e "+ item.getQtdMax(), "Valor invalido",
                                              JOptionPane.INFORMATION_MESSAGE);                
                return;
            
            } catch (ArrayIndexOutOfBoundsException ex) {
                //O personagem não possui ouro suficiente para a compra do item
                JOptionPane.showMessageDialog(new JOptionPane(), "Ouro insuficiente.", "Erro", JOptionPane.ERROR_MESSAGE);                
                return;
            }
            
            int precoFinal = (item.getPrecoCompra() * quantidade);
            
            //String mostrada de confirmação de venda do item
            String comprarConfirm = "<html>Comprar <font color=\"blue\">"+ item.getNome() +"</font>x"+
                                    quantidade +" por <font color=\"#FB0404\">"+
                                    precoFinal +"</font> ouro(s)?</html>";
            //É pedido uma confirmação do usuário para a venda
            int reply = JOptionPane.showConfirmDialog(new JOptionPane(),
                                                      comprarConfirm,
                                                      "", JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION) {
                try {
                    //Se for possível remover a quantidade de ouro do inventário do personagem
                    if(inventario.remove("ouro", precoFinal)) {
                        //Se for possivel adicionar a quantidade de itens ao inventário do personagem
                        if(inventario.add(item, quantidade)) {
                            PainelLoja.this.repaintPainel();
                        } else {
                            //Senão, é adicionada a quantidade de ouro que foi removida
                            inventario.add("ouro", precoFinal);
                            JOptionPane.showMessageDialog(new JOptionPane(), "Inventario cheio.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(new JOptionPane(), "Ouro insuficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                //Foi apertado "X" ou Cancelar
                } catch (NullPointerException ex) {
                    //Dialog de input simplesmente é fechado

                } catch (NumberFormatException ex) {
                    //O valor inserido na entrada é menor que 1 ou maior que a quantidade máxima do item
                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                  "Insira um valor entre 1 e "+ item.getQtdMax(),
                                                  "Valor invalido", JOptionPane.INFORMATION_MESSAGE);                
                    
                } catch (IOException ex) {
                    Logger.getLogger(PainelLoja.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
