package rpg.GUI.util;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class MeuTextField extends JTextField {
    private int limite;
    
    public MeuTextField() {
        //Inicializa o atribute limite com o valor 999999999
        this.limite = 999999999;
    }
    
    public MeuTextField(String texto, int limite) {
        //Chamada ao construtor de JButton com o texto recebido por parâmetro como parâmetro
        super(texto);
        
        //Inicializa o atributo limite com o valor de maxChar que foi recebido como parâmetro do construtor
        this.limite = limite;
        
        //Cria uma borda fina preta para o painel
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public MeuTextField(String texto) {
        //Chamada ao construtor default(padrão) de MeuTextField
        this();
        
        //Modifica o texto para o texto recebido como parâmetro
        this.setText(texto);
        
        //Cria uma borda fina preta para o painel
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public MeuTextField(String texto, int limite, int tamanho) {
        //Chamada ao construtor que recebe o texto e o limite como parâmetros
        this(texto, limite);
        
        //Modifica o número de colunas do field
        this.setColumns(tamanho);
    }
    
    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {

        @Override
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limite) {
                super.insertString(offset, str, attr);
            }
        }       

    }
}
