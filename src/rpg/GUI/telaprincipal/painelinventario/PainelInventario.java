package rpg.GUI.telaprincipal.painelinventario;

import rpg.itens.EquipSlotFundo;
import rpg.GUI.util.InvSlotFundo;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.Icon;
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
import rpg.GUI.util.EmptyIcon;
import rpg.GUI.util.LabelVoltar;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import rpg.itens.Item;
import rpg.itens.Itens;
import rpg.personagem.EquipSlots;
import rpg.personagem.InvSlots;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

//Criaçao da tela de inventário do personagem
public final class PainelInventario extends MeuPanel {
    private final Personagem personagem;
    private final Personagens personagens;
    private final HashMap<EquipSlots, JPanel> paineisEquips = new HashMap<>();
    public static final Icon IMG = new EmptyIcon(32, 32);
    private final JPanel painelInventario = new MeuPanel2(new MigLayout("fillx, wrap 5", "", ""));
    private final JPanel painelEquip = new MeuPanel2(new MigLayout("fillx", "[][][]", "[][][][]"));
    
    public PainelInventario(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
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
        
        this.paintEquipamento();
                
        this.add(painelEquip, "align center, wrap");
        
        this.add(new JSeparator(), "gaptop 15px, gapbottom 15px, growx, wrap");
        
        this.paintInventario();
        
        this.add(painelInventario, "align center");

    }
    
    public void paintEquipamento() throws IOException {
        this.painelEquip.removeAll();
        
        //Pega o equipamento do personagem
        HashMap<EquipSlots, Item> equipamento = personagem.getEquipamento();
        painelEquip.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        //Pinta cada slot de equipamento
        for(EquipSlots slotEquip : EquipSlots.values()) {
            JPanel painel = new EquipSlotFundo(new MigLayout("", "", ""));
            ImageIcon icone = new ImageIcon();
            JLabel imgItem = null;
            try {
                icone = new ImageIcon(equipamento.get(slotEquip).getImg());
                Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                icone = new ImageIcon(img);
                imgItem = new JLabel(icone);
                //É inserido um tooltip com as informações do item
                imgItem.setToolTipText(equipamento.get(slotEquip).toString());
                
                imgItem.addMouseListener(new MouseAdapter() {
                    private JLabel imgItem;
                    @Override
                    public void mouseClicked(MouseEvent event) {
                        if(event.getClickCount() == 2) {
                            try {
                                if(!personagem.desequiparItem(slotEquip)) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "Inventário Cheio","Inventário cheio",
                                                                  JOptionPane.ERROR_MESSAGE);                
                                } else {
                                    painel.removeAll();
                                    //Remove o popupmenu
                                    painel.setComponentPopupMenu(new JPopupMenu());
                                    //Adiciona uma imagem vazia
                                    imgItem = new JLabel(IMG);
                                    painel.add(imgItem);
                                }
                        
                                //É atualizado ataque e defesa do personagem
                                personagem.atualizarPersonagem();
                                PainelInventario.this.repaintPainel();
                            } catch (IOException ex) {
                                Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                    public MouseAdapter init(JLabel imgItem) {
                        this.imgItem = imgItem;
                        
                        return this;
                    }
                }.init(imgItem));
                
            } catch(NullPointerException ex) {
                Itens itens = new Itens();
                //Caso não exista um item neste slot, uma imagem de item acinzentada
                //ficará no lugar da imagem do item, de acordo com o slot
                switch(slotEquip.toString()) {
                    case "ELMO" : icone = new ImageIcon(itens.get("elmo_de_couro").getImg()); break;
                    case "ARMA" : icone = new ImageIcon(itens.get("adaga").getImg()); break;
                    case "ARMADURA" : icone = new ImageIcon(itens.get("armadura_de_couro").getImg()); break;
                    case "ESCUDO" : icone = new ImageIcon(itens.get("escudo_de_madeira").getImg()); break;
                    case "CALCAS" : icone = new ImageIcon(itens.get("calcas_de_couro").getImg()); break;
                    case "BOTAS" : icone = new ImageIcon(itens.get("botas_de_couro").getImg()); break;
                }
                
                Image imagem = icone.getImage();
                //É adicionado um filtro cinza à imagem de item
                ImageFilter filter = new GrayFilter(true, 15);
                ImageProducer producer = new FilteredImageSource(imagem.getSource(), filter);
                Image imagemCinza = Toolkit.getDefaultToolkit().createImage(producer);
                Icon iconeCinza = new ImageIcon(imagemCinza);
                
                imgItem = new JLabel(iconeCinza);
            }
            painel.add(imgItem);
            
            JPopupMenu popup = new JPopupMenu();
            
            JMenuItem menuItem = new JMenuItem("Desequipar");
            menuItem.addActionListener(new ActionListener() {
                private JLabel imgItem;
                
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if(!personagem.desequiparItem(slotEquip)) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "Inventário Cheio","Inventário cheio",
                                                          JOptionPane.ERROR_MESSAGE);                
                        } else {
                            painel.removeAll();
                            //Remove o popupmenu
                            painel.setComponentPopupMenu(new JPopupMenu());
                            //Adiciona uma imagem vazia
                            imgItem = new JLabel(IMG);
                            painel.add(imgItem);
                        }
                    
                        //É atualizado ataque e defesa do personagem
                        personagem.atualizarPersonagem();
                        PainelInventario.this.repaintPainel();
                    } catch (IOException ex) {
                        Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                public ActionListener init(JLabel imgItem) {
                    this.imgItem = imgItem;
                    
                    return this;
                }
            }.init(imgItem));
            
            popup.add(menuItem);
            //Se existir item equipado, o popup menu é adicionado
            if(equipamento.get(slotEquip) != null) painel.setComponentPopupMenu(popup);
            paineisEquips.put(slotEquip, painel);   

            //É pintado no seu local fixo, dependendo do slot do item
            if(slotEquip.equals(EquipSlots.valueOf("ELMO"))) {
                painelEquip.add(painel, "spanx 3, alignx center, wrap");
            } else if(slotEquip.equals(EquipSlots.valueOf("ESCUDO"))) {
                painelEquip.add(painel, "wrap");
            } else if(slotEquip.equals(EquipSlots.valueOf("CALCAS")) || slotEquip.equals(EquipSlots.valueOf("BOTAS"))) {
               painelEquip.add(painel, "spanx 3, alignx center, wrap");
            } else {
                painelEquip.add(painel);
            }
        }
    }
    
    //Pinta o painel de inventário
    public void paintInventario() {
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
                    JPopupMenu popup = new JPopupMenu();
                    
                    JMenuItem equipar = new JMenuItem("Equipar");
                    equipar.addActionListener((ActionEvent ae) -> {
                        try {
                    if(!personagem.equiparItem(item)) {
                        if(PainelInventario.this.personagem.getLevel() < item.getLevelNecessario()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"red\">Level</font> insuficiente!",
                                                          "Level insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getForca() < item.getForcaNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"red\">Força</font> insuficiente!",
                                                          "Força insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getInteligencia() < item.getInteligenciaNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"purple\">Inteligência</font> insuficiente!",
                                                          "Inteligência insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getVitalidade() < item.getVitalidadeNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Vitalidade</font> insuficiente!",
                                                          "Inteligência insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getSorte() < item.getSorteNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Sorte</font> insuficiente!",
                                                          "Sorte insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getAgilidade() < item.getAgilidadeNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Agilidade</font> insuficiente!",
                                                          "Agilidade insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        }
                    }
                        
                            personagem.atualizarPersonagem();
                            PainelInventario.this.repaintPainel();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    if(item.isEquipavel()) popup.add(equipar);
            
                    JMenuItem usar = new JMenuItem("Usar");
                    usar.addActionListener((ActionEvent ae) -> {
                        PainelInventario.this.personagem.usarItem(item);
                        personagem.getInventario().remove(item.getId());
                        
                        try {
                            PainelInventario.this.repaintPainel();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    if(item.isUsavel()) popup.add(usar);
            
                    JMenuItem aprender = new JMenuItem("Aprender");
                    aprender.addActionListener((ActionEvent ae) -> {
                        try {
                            if(PainelInventario.this.personagem.usarItem(item)) {
                                    personagem.getInventario().remove(item.getId());
                                
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"blue\">"+ personagem.getNome() +"</font>"+
                                                              " aprendeu a magia <font color=\"purple\">"+ item.getNome(),
                                                                  "Magia aprendida com sucesso",
                                                              JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                if(PainelInventario.this.personagem.getLevel() < item.getLevelNecessario()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"red\">Level</font> insuficiente!",
                                                                  "Level insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else if(PainelInventario.this.personagem.getForca() < item.getForcaNecessaria()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"red\">Força</font> insuficiente!",
                                                                  "Força insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else if(PainelInventario.this.personagem.getInteligencia() < item.getInteligenciaNecessaria()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"purple\">Inteligência</font> insuficiente!",
                                                                  "Inteligência insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else if(PainelInventario.this.personagem.getVitalidade() < item.getVitalidadeNecessaria()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"green\">Vitalidade</font> insuficiente!",
                                                                  "Inteligência insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else if(PainelInventario.this.personagem.getSorte() < item.getSorteNecessaria()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"green\">Sorte</font> insuficiente!",
                                                                  "Sorte insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else if(PainelInventario.this.personagem.getAgilidade() < item.getAgilidadeNecessaria()) {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"green\">Agilidade</font> insuficiente!",
                                                                  "Agilidade insuficiente!",
                                                                  JOptionPane.WARNING_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(new JOptionPane(),
                                                                  "<html><font color=\"blue\">"+ personagem.getNome() +"</font>"+
                                                                  " já possui a magia <font color=\"purple\">"+ item.getNome(),
                                                                  "Já possui esta magia!",
                                                              JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            
                            PainelInventario.this.repaintPainel();
                        } catch (IOException ex) {
                            Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    if(item.getTipo().equals("magia")) popup.add(aprender);
                    
                    JMenuItem remover = new JMenuItem("Remover");
                    remover.addActionListener((ActionEvent ae) -> {
                        int quantidade = 0;
                        try {
                            if(qtd > 1) {
                                String input = JOptionPane.showInputDialog("Quantidade \"1~"+ qtd +"\"", ""+ qtd);
                                
                                //Se o usuário apertar "X" ou cancelar será lançado um NullPointerException
                                if(input == null) throw new NullPointerException();
                                
                                quantidade = Integer.parseInt(input);
                                
                                if(quantidade < 1 || quantidade > qtd) throw new NumberFormatException();
                                
                                personagem.getInventario().remove(item.getId(), quantidade);
                            } else {
                                int reply = JOptionPane.showConfirmDialog(new JOptionPane(), "Tem certeza?", "", JOptionPane.YES_NO_OPTION);
                                if(reply == JOptionPane.YES_OPTION) {
                                    personagem.getInventario().remove(item.getId());
                                }
                            }
                            
                            PainelInventario.this.repaintPainel();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(new JOptionPane(), "Insira um valor entre 1 e "+ qtd, "Valor invalido", JOptionPane.INFORMATION_MESSAGE);
                            
                        } catch (NullPointerException ex) {
                            //Dialog de input simplesmente é fechado
                        } catch (IOException ex) {
                            Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    popup.add(remover);
                    
                    
                    slot.setComponentPopupMenu(popup);
                    
                    if(qtd > 1) {
                        JLabel labelQtd = new MeuLabel(""+ qtd);
                        slot.add(labelQtd, "pos 0 65%");
                    }
                    
                    ImageIcon icone = new ImageIcon(item.getImg());
                    Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
                    icone = new ImageIcon(img);
                    imgSlot = new JLabel((icone));
                    //É inserido um tooltip com as informações do item
                    imgSlot.setToolTipText(item.toString());
                    
                    if (item.isEquipavel()) imgSlot.addMouseListener(new EquiparMouseAdapter(item));
                    else if(item.getTipo().trim().toLowerCase().equals("usavel")) imgSlot.addMouseListener(new UsarMouseAdapter(item));
                    
                    slot.add(imgSlot);  
        
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
        this.paintEquipamento();
        this.paintInventario();
        this.repaint();
        this.revalidate();
        
        this.personagens.atualizarInfoPersonagem();
    }
    
    private class EquiparMouseAdapter extends MouseAdapter {
        private final Item item;
        
        public EquiparMouseAdapter(Item item) {
            this.item = item;
        }
        
        
        @Override
        public void mouseClicked(MouseEvent event) {
            if(event.getClickCount() == 2) {
                try {
                    if(!personagem.equiparItem(item)) {
                        if(PainelInventario.this.personagem.getLevel() < item.getLevelNecessario()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"red\">Level</font> insuficiente!",
                                                          "Level insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getForca() < item.getForcaNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"red\">Força</font> insuficiente!",
                                                          "Força insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getInteligencia() < item.getInteligenciaNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"purple\">Inteligência</font> insuficiente!",
                                                          "Inteligência insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getVitalidade() < item.getVitalidadeNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Vitalidade</font> insuficiente!",
                                                          "Inteligência insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getSorte() < item.getSorteNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Sorte</font> insuficiente!",
                                                          "Sorte insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        } else if(PainelInventario.this.personagem.getAgilidade() < item.getAgilidadeNecessaria()) {
                            JOptionPane.showMessageDialog(new JOptionPane(),
                                                          "<html><font color=\"green\">Agilidade</font> insuficiente!",
                                                          "Agilidade insuficiente!",
                                                          JOptionPane.WARNING_MESSAGE);
                        }
                    }

                    personagem.atualizarPersonagem();
                    PainelInventario.this.repaintPainel();
                } catch (IOException ex) {
                    Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private class UsarMouseAdapter extends MouseAdapter {
        private final Item item;
        
        public UsarMouseAdapter(Item item) {
            this.item = item;
        }
        
        @Override
        public void mouseClicked(MouseEvent event) {
            if(event.getClickCount() == 2) {
                PainelInventario.this.personagem.usarItem(item);
                personagem.getInventario().remove(item.getId());
        
                try {
                    PainelInventario.this.repaintPainel();
                } catch (IOException ex) {
                    Logger.getLogger(PainelInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
