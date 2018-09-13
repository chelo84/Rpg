package rpg.GUI.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JLabel;

//Criação de um JLabel clicável
public class MeuLabelClicavel extends JLabel {
    
    public MeuLabelClicavel() {
        //Modifica a cor da fonte para azul
        this.setForeground(Color.BLUE);
        
        //Sublinha o texto
        Font font = this.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.setFont(font.deriveFont(attributes));
        
        //Modifica o cursor do mouse quando o mouse está em cima do label
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    public MeuLabelClicavel(String texto) {
        this();
        
        //Modifica o texto para o texto recebido como parâmetro
        this.setText(texto);
    }
}
