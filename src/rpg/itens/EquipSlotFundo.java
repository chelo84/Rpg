package rpg.itens;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//Criação de um painel de fundo de slot de equipamento
public class EquipSlotFundo extends JPanel {
    
    public EquipSlotFundo() {
        //Modifica a cor de fundo do painel
        this.setBackground(Color.decode("#1a1d1f"));
        //Adiciona uma borda ao painel
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
    }
    
    public EquipSlotFundo(MigLayout layout) {
        this();
        
        this.setLayout(layout);
    }
}
