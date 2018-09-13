package rpg.GUI.telaprincipal.painelpersonagem;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuPanel2;
import rpg.magia.Magia;
import rpg.personagem.MagiasPersonagem;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class MagiasPersonagemDialog extends JDialog {
    private final Personagem personagem;
    private final Personagens personagens;
    
    public MagiasPersonagemDialog(JFrame frame, Personagem personagem, Personagens personagens) throws IOException {
        super(frame, "", Dialog.ModalityType.APPLICATION_MODAL);
        
        this.personagem = personagem;
        this.personagens = personagens;
        
        this.setTitle("Magias Aprendidas");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(300, 300));
        createComponents(this.getContentPane());
        
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createComponents(Container container) throws IOException {
        pintarMagias();
    }
    
    public void pintarMagias() throws IOException {
        JPanel painelMagias = new MeuPanel(new MigLayout("fillx"));
        JScrollPane scrollPane = new JScrollPane(painelMagias);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(300,300));
        
        MagiasPersonagem magias = this.personagem.getMagias();
        
        if(magias.size() < 1) {
            painelMagias.add(new JSeparator(), "growx, wrap");
            JPanel painel = new MeuPanel(new MigLayout("fillx"));
            JLabel listaVazia = new MeuLabel(this.personagem.getNome() +" ainda não aprendeu nenhuma magia");
            painel.add(listaVazia, "gaptop 100px, grow, align center, wrap");
            painelMagias.add(painel, "grow, gapbottom 100px, align center, wrap");
            painelMagias.add(new JSeparator(), "growx, wrap");
        }
        
        for(int i = 0;i < magias.size();i++) {
            Magia magia = magias.get(i);
            JPanel painel = new MeuPanel(new MigLayout("fillx"));
        
            painel.add(new JSeparator(), "growx, wrap");
        
            JPanel painelMagia = new MeuPanel2(new MigLayout(""));
        
            JPanel painelImgMagia = new JPanel(new MigLayout());
            painelImgMagia.setOpaque(false);
            ImageIcon icone = new ImageIcon(magia.getImg());
            Image img = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            painelImgMagia.add(imgLabel);
            
            painelMagia.add(painelImgMagia, "");
            
            JPanel painelInfo = new MeuPanel(new MigLayout("fillx", "", ""));
            painelInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            JLabel nomeLabel = new MeuLabel("Nome: "+ magia.getNome());
            painelInfo.add(nomeLabel, "wrap");
            
            JLabel levelLabel = new MeuLabel("Level: "+ magia.getLevelNecessario());
            painelInfo.add(levelLabel, "wrap");
            
            JLabel tipoLabel = new MeuLabel("Tipo: "+ magia.getTipo());
            painelInfo.add(tipoLabel, "wrap");
            
            JLabel valorLabel = null;
            switch(magia.getTipo()) {
                case "dano" : valorLabel = new MeuLabel("<html>Dano: "+ magia.getValorEfeito()
                                                      + " + ( <font color=\"purple\">"+ (int) (personagem.getDanoMagico()*magia.getValorEfeitoRatio()) 
                                                      + "</font> )</html>");
                              break;
                            
                case "cura" : valorLabel = new MeuLabel("<html>Cura: <font color=\"green\">"+ magia.getValorEfeito() 
                                                      + "</font> + ( <font color=\"purple\">"+ (int) (personagem.getDanoMagico()*magia.getValorEfeitoRatio())
                                                      + "</font> )</html>");
                              break;
            }
            painelInfo.add(valorLabel, "wrap");
            
            JLabel custoLabel = new MeuLabel("<html>Custo: <font color=\"blue\">"+ magia.getCusto() +"</font></html>");
        
            painelInfo.add(custoLabel, "wrap");
    
            painelMagia.add(painelInfo, "wrap, growx, spanx, push");
        
            painel.add(painelMagia, "growx, span, align center");
    
            painel.add(new JSeparator(), "growx, spanx, wrap");
    
            painelMagias.add(painel, "growx, spanx, wrap");
        }
        
        this.add(scrollPane);
    }
}
