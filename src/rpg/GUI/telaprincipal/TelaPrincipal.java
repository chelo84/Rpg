package rpg.GUI.telaprincipal;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.text.BadLocationException;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.util.MeuPanel;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class TelaPrincipal extends JFrame {
    private final Personagem personagem;
    private final Personagens personagens;

    public TelaPrincipal(Personagem personagem, Personagens personagens) throws IOException, BadLocationException {
        this.personagem = personagem;
        this.personagens = personagens;
        
        this.setTitle("RPG");
        this.setPreferredSize(new Dimension(450, 525));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createComponents(this.getContentPane());
        
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void createComponents(Container container) throws IOException, BadLocationException {
        JPanel painel = new MeuPanel(new MigLayout("fillx"));
        JProgressBar barraVida = new JProgressBar();
        barraVida.setBackground(Color.GREEN);
        painel.add(barraVida, "wrap");
        
        JProgressBar barraXp = new JProgressBar();
        barraXp.setBackground(Color.YELLOW);
        painel.add(barraXp, "wrap");
        
        
        PainelPrincipal painelPrincipal = new PainelPrincipal(this.personagem, this.personagens); 
        container.add(painelPrincipal);
    }    
}
