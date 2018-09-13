package rpg.GUI.util;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//Criação de um painel customizado
public class MeuPanel2 extends JPanel {
    
    public MeuPanel2() {
        //Modifica a cor de fundo do painel
        this.setBackground(Color.decode("#5D6D7E"));
        
        //Cria uma borda fina preta para o painel
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public MeuPanel2(MigLayout layout) {
        //Chamada ao construtor default(padrão) que modifica a cor de fundo do painel e borda
        this();
        
        //Modifica o layout do painel
        this.setLayout(layout);
    }
}
