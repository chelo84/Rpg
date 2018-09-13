package rpg.GUI.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//Criação de um label clicável que serve para voltar à tela anterior
public class LabelVoltar extends JLabel {
    private JPanel painelAtual;
    private JPanel painelApos;
    private Thread thread = new Thread();
    
    public LabelVoltar(JPanel painelAtual, JPanel painelApos) {
        this.painelAtual = painelAtual;
        this.painelApos = painelApos;
        
        //Modifica a cor da fonte para azul
        this.setForeground(Color.BLUE);

        
        //Sublinha o texto
        Font font = this.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.setFont(font.deriveFont(attributes));
        
        //Modifica o cursor do mouse quando o mouse está em cima do label
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        
        this.addMouseListener(new MeuMouseAdapter());
        
    }
    
    public LabelVoltar(String text, JPanel painelAtual, JPanel painelApos) {
        this(painelAtual, painelApos);
        
        //Modifica o texto para o texto recebido como parâmetro
        this.setText(text);
    }    

    public LabelVoltar(String text, JPanel painelAtual, JPanel painelApos, Thread thread) {
        this(text, painelAtual, painelApos);
        
        //Adiciona a thread
        this.thread = thread;
    }
    
    private class MeuMouseAdapter extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {  
            //Suspende a thread
            thread.suspend();
    
            Component component = (Component) e.getSource();
            JFrame frame = (JFrame) SwingUtilities.getRoot(component);
            JPanel painel = (JPanel) frame.getContentPane();

            painel.remove(painelAtual);
            painel.add(painelApos);
                    
            painel.repaint();
            painel.revalidate();
        }  
    }
}
