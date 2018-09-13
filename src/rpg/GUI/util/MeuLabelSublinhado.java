package rpg.GUI.util;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

public class MeuLabelSublinhado extends MeuLabel {
    
    public MeuLabelSublinhado() {
        //Sublinha o texto
        Font font = this.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.setFont(font.deriveFont(attributes));
    }

    public MeuLabelSublinhado(String text) {
        this();
        
        //Modifica o texto para o texto recebido como parâmetro
        this.setText(text);
    }
}
