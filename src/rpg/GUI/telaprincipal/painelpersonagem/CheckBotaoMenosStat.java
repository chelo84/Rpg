package rpg.GUI.telaprincipal.painelpersonagem;

import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import rpg.personagem.Personagem;

public class CheckBotaoMenosStat extends TimerTask {
        private Integer stat;
        private JButton botao;
        private JLabel label;
        private Personagem personagem;
        private String statString;
                
        public CheckBotaoMenosStat(Personagem personagem, String statString, JButton botao, JLabel label) {
            this.personagem = personagem;
            this.statString = statString;
            this.botao = botao;
            this.label = label;
        }
        
        @Override
        public void run() {
            //Verifica qual stat será afetado
            switch(statString) {
                case "forca" : this.stat = this.personagem.getForca(); break;
                case "vitalidade" : this.stat = this.personagem.getVitalidade(); break;
                case "agilidade" : this.stat = this.personagem.getAgilidade(); break;
                case "sorte" : this.stat = this.personagem.getSorte(); break;
                case "inteligencia" : this.stat = this.personagem.getInteligencia(); break;
            }
            
            int statAtual = Integer.parseInt(this.label.getText());
            if(statAtual <= this.stat) {
                this.botao.setEnabled(false);
            } else {
                this.botao.setEnabled(true);
            }
        }
    }
