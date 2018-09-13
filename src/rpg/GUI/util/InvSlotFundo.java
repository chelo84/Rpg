package rpg.GUI.util;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//Criação de um painel de fundo de slot de inventário
public class InvSlotFundo extends JPanel {
    
    public InvSlotFundo() {
        //Modifica a cor de fundo do painel
        this.setBackground(Color.decode("#1a1d1f"));
        //Adiciona uma borda ao painel
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
    }
    
    public InvSlotFundo(MigLayout layout) {
        this();
        
        this.setLayout(layout);
    }    
}
