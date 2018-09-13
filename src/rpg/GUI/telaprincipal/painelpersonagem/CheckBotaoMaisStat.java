package rpg.GUI.telaprincipal.painelpersonagem;

import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import rpg.personagem.Personagem;

public class CheckBotaoMaisStat extends TimerTask {
        private final JButton botao;
        private final JTextField field;
        
        public CheckBotaoMaisStat(String stat, JButton botao, JTextField field) {
            
            this.botao = botao;
            this.field = field;
        }
        
        @Override
        public void run() {
            try {
                int pts = Integer.parseInt(field.getText());
                
                if(pts < 1) {
                    this.botao.setEnabled(false);
                } else {
                    this.botao.setEnabled(true);
                }
            } catch (NumberFormatException ex) {
                return;
            }
        }
    }
