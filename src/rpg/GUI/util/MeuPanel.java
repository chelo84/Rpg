package rpg.GUI.util;


import java.awt.Color;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//Criação de um painel customizado
public class MeuPanel extends JPanel {
    
    public MeuPanel() {
        //Modifica a cor de fundo do painel
        this.setBackground(Color.decode("#85929E"));
    }
    
    public MeuPanel(MigLayout layout) {
        //Chamada ao construtor default(padrão) que modifica a cor de fundo do painel
        this();
        
        //Modifica o layout do painel
        this.setLayout(layout);
    }
    
}
