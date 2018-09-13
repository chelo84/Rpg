package rpg.GUI.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JLabel;

//Criação de um label com cor branca
public class MeuLabel extends JLabel {
    
    public MeuLabel() {
        //Modifica a cor da fonte para branco
        this.setForeground(Color.WHITE);
    }

    public MeuLabel(String texto) {
        //Chamada ao construtor default(padrão) que modifica a cor do texto para branco
        this();
        
        //Modifica o texto do label para o texto do parâmetro
        this.setText(texto);
    }
    
    public MeuLabel(String texto, int n) {
        //Chamada ao construtor de MeuLabel que possui o texto como parâmetro
        this(texto);
        
        //Modifica o alinhamento horizontal do texto
        this.setHorizontalAlignment(n);
    }
}

