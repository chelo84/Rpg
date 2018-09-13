/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.GUI.util;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author W7
 */
public class MagiaFundo extends JPanel {
    
    public MagiaFundo() {
        //Modifica a cor de fundo do painel
        this.setBackground(Color.decode("#1a1d1f"));
        //Adiciona uma borda ao painel
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter()));
    }
    
    public MagiaFundo(MigLayout layout) {
        this();
        
        this.setLayout(layout);
    }    
}
