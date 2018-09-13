package rpg.GUI.util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

//Criação de um botão customizado
public class MeuBotao extends JButton {
    public MeuBotao() {
        
    }
    
    public MeuBotao(String text) {
        //Chamada ao construtor de JButton com o texto recebido por parâmetro como parâmetro
        super(text);
        
        //Modifica a cor da fonte para branco
        this.setForeground(Color.WHITE);
        
        //Modifica a cor do botão quando selecionado
        UIManager.put("Button.select", Color.decode("#536271").brighter());
        
        //Modifica a cor do botão
        this.setBackground(Color.decode("#5D6D7E"));
        //Remove o retângulo em volta do texto dentro do botão
        this.setFocusPainted(false);
        //Modifica a borda do botão para uma borda fina preta
        this.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 17, 5, 17)));
        //Adiciona um Mouse Listener para quando o mouse está em cima do botão
        this.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent m) {
             MeuBotao.this.setBackground(Color.decode("#536271"));
          }
          
          @Override
          public void mouseExited(MouseEvent m) {
             MeuBotao.this.setBackground(Color.decode("#5d6d7e"));
          }
        });
    }
}