package rpg.GUI.telacriarpersonagem;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import rpg.GUI.telainicio.TelaInicio;
import rpg.GUI.telaprincipal.painelpersonagem.CheckBotaoMaisStat;
import rpg.GUI.telaprincipal.painelpersonagem.CheckBotaoMenosStat;
import rpg.GUI.util.MeuBotao;
import rpg.GUI.util.MeuLabel;
import rpg.GUI.util.MeuLabelClicavel;
import rpg.GUI.util.MeuLabelSublinhado;
import rpg.GUI.util.MeuPanel;
import rpg.GUI.util.MeuTextField;
import rpg.GUI.util.MeuTimer;
import rpg.personagem.Personagem;
import rpg.personagem.Personagens;

public class TelaCriarPersonagem {
    private JFrame frame;
    private JTextField valorSPField = new JTextField();
    private JButton botaoCriar = new MeuBotao();
    private int statPointsDps = 0;
    
    public TelaCriarPersonagem(JFrame frame) throws IOException {
        this.frame = frame;
        frame.getContentPane().removeAll();
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        
        createComponents(this.frame.getContentPane());
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void createComponents(Container container) throws IOException {
        JPanel painel = new MeuPanel(new MigLayout("fillx", "", ""));
        
        //Um label clicável que redireciona para a tela anterior
        JLabel voltarLabel = new MeuLabelClicavel("Voltar");
        voltarLabel.addMouseListener(new MouseAdapter()  
        {  
            @Override
            public void mouseClicked(MouseEvent e)  
            {  
               frame.dispose();
                try {
                    TelaInicio inicio = new TelaInicio();
                } catch (IOException ex) {
                    Logger.getLogger(TelaCriarPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }  
        }); 
        painel.add(voltarLabel, "wrap");

        JLabel novoPersonagemLabel = new MeuLabelSublinhado("Criação de um novo personagem");
        painel.add(novoPersonagemLabel, "wrap, align center");
        
        //Aqui é feita a entrada do nome do personagem
        JLabel nomeLabel = new MeuLabel("Nome: ");
        painel.add(nomeLabel, "gaptop 15px, align center, split 2");
        JTextField nomeField = new MeuTextField("", 15, 15);
        painel.add(nomeField, "wrap");
        
        //Aqui é feita a distribuição do ponto do nível 1 do personagem
        JLabel pontosStatLabel = new MeuLabel("Pontos de status: ");
        painel.add(pontosStatLabel, "gaptop 20px, align center, split 2");
        
        Personagem personagem = new NovoPersonagem();
        
        //Este JTextField mostra quantos pontos de stats o personagem tem para distribuir
        valorSPField = new JTextField(""+ personagem.getPontosStat(), 2);
        valorSPField.setDisabledTextColor(Color.BLACK);
        valorSPField.setEnabled(false);
        valorSPField.setHorizontalAlignment(JTextField.CENTER);        
        
        painel.add(valorSPField, "align center, wrap");
        
        //Informações referentes a força
        JLabel forLabel = new MeuLabel("Forca: ");
        forLabel.setToolTipText("<html>1 pt = +<font color=\"red\">1</font> ataque");
        JLabel valorForLabel = new MeuLabel(""+ personagem.getForca(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosForBotao = new MeuBotao(" < ");
        menosForBotao.setBorder(null);
        menosForBotao.addActionListener(new BotaoMenosStat(valorForLabel, valorSPField));
        JButton maisForBotao = new MeuBotao(" > ");
        maisForBotao.setBorder(null);  
        maisForBotao.addActionListener(new BotaoMaisStat(valorForLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("forca", maisForBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(personagem, "forca", menosForBotao, valorForLabel), 0, 1000/10);
        
        
        painel.add(forLabel, "split 4, align center");
        painel.add(menosForBotao, "shrink 1000");
        painel.add(valorForLabel);
        painel.add(maisForBotao, "wrap");
        
        //Informações referentes a vitalidade
        JLabel vitLabel = new MeuLabel("Vitalidade: ");
        vitLabel.setToolTipText("<html>1 pt = +<font color=\"red\">3</font> vida");
        JLabel valorVitLabel = new MeuLabel(""+ personagem.getVitalidade(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosVitBotao = new MeuBotao(" < ");
        menosVitBotao.setBorder(null);
        menosVitBotao.addActionListener(new BotaoMenosStat(valorVitLabel, valorSPField));
        JButton maisVitBotao = new MeuBotao(" > ");
        maisVitBotao.setBorder(null);  
        maisVitBotao.addActionListener(new BotaoMaisStat(valorVitLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("vitalidade", maisVitBotao, valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(personagem, "vitalidade", menosVitBotao, valorVitLabel), 0, 1000/10);

        painel.add(vitLabel, "split 4, align center");
        painel.add(menosVitBotao, "shrink 1000");
        painel.add(valorVitLabel);
        painel.add(maisVitBotao, "wrap");
        
        //Informações referentes a agilidade
        JLabel agiLabel = new MeuLabel("Agilidade: ");
        agiLabel.setToolTipText("<html>2 pts = +<font color=\"red\">1</font>% chance de evasão");
        JLabel valorAgiLabel = new MeuLabel(""+ personagem.getAgilidade(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosAgiBotao = new MeuBotao(" < ");
        menosAgiBotao.setBorder(null);
        menosAgiBotao.addActionListener(new BotaoMenosStat(valorAgiLabel, valorSPField));
        JButton maisAgiBotao = new MeuBotao(" > ");
        maisAgiBotao.setBorder(null);  
        maisAgiBotao.addActionListener(new BotaoMaisStat(valorAgiLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("agilidade", maisAgiBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(personagem, "agilidade", menosAgiBotao, valorAgiLabel), 0, 1000/10);

        painel.add(agiLabel, "split 4, align center");
        painel.add(menosAgiBotao, "shrink 1000");
        painel.add(valorAgiLabel);
        painel.add(maisAgiBotao, "wrap");
        
        //Informações referentes a sorte
        JLabel sorLabel = new MeuLabel("Sorte: ");
        sorLabel.setToolTipText("<html>1 pt = +<font color=\"red\">2.50</font>% chance de critíco");
        JLabel valorSorLabel = new MeuLabel(""+ personagem.getSorte(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosSorBotao = new MeuBotao(" < ");
        menosSorBotao.setBorder(null);
        menosSorBotao.addActionListener(new BotaoMenosStat(valorSorLabel, valorSPField));
        JButton maisSorBotao = new MeuBotao(" > ");
        maisSorBotao.setBorder(null);  
        maisSorBotao.addActionListener(new BotaoMaisStat(valorSorLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("sorte", maisSorBotao,valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(personagem, "sorte", menosSorBotao, valorSorLabel), 0, 1000/10);

        painel.add(sorLabel, "split 4, align center");
        painel.add(menosSorBotao, "shrink 1000");
        painel.add(valorSorLabel);
        painel.add(maisSorBotao, "wrap");
        
        //Informações referentes a inteligencia
        JLabel intLabel = new MeuLabel("Inteligência: ");
        intLabel.setToolTipText("<html>1 pt = +<font color=\"red\">1</font> dano mágico");
        JLabel valorIntLabel = new MeuLabel(""+ personagem.getInteligencia(), 2);
        
        //Botões que aumentam ou diminuem o stat em questão em 1
        JButton menosIntBotao = new MeuBotao(" < ");
        menosIntBotao.setBorder(null);
        menosIntBotao.addActionListener(new BotaoMenosStat(valorIntLabel, valorSPField));
        JButton maisIntBotao = new MeuBotao(" > ");
        maisIntBotao.setBorder(null);  
        maisIntBotao.addActionListener(new BotaoMaisStat(valorIntLabel, valorSPField));
        
        //É criado timertask que chama um objeto que verifica se os pontos de stats não chegaram a 0
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMaisStat("inteligencia", maisIntBotao, valorSPField), 0, 1000/10);
        
        MeuTimer.timer.scheduleAtFixedRate(new CheckBotaoMenosStat(personagem, "inteligencia", menosIntBotao, valorIntLabel), 0, 1000/10);

        painel.add(intLabel, "split 4, align center");
        painel.add(menosIntBotao, "shrink 1000");
        painel.add(valorIntLabel);
        painel.add(maisIntBotao, "wrap");
        
        //Botão que faz a criação do personagem
        botaoCriar = new MeuBotao("Criar Personagem");
        botaoCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Personagens personagens = null;
                
                //Seta a força/vitalidade/agilidade e sorte do personagem conforme escolhido pelo mesmo
                try {
                    personagem.setForca(Integer.parseInt(valorForLabel.getText()));
                    personagem.setVitalidade(Integer.parseInt(valorVitLabel.getText()));
                    personagem.setAgilidade(Integer.parseInt(valorAgiLabel.getText()));
                    personagem.setSorte(Integer.parseInt(valorSorLabel.getText()));
                    personagem.setInteligencia(Integer.parseInt(valorIntLabel.getText()));
                    personagem.setPontosStat(0);
                    personagens = new Personagens();
                } catch (IOException ex) {
                    Logger.getLogger(TelaCriarPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                //Seta o nome desejado do personagem
                personagem.setNome(nomeField.getText());
                try {
                    if(personagens.add(personagem)) {
                        JOptionPane.showMessageDialog(new JOptionPane(), "Personagem criado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        
                        frame.dispose();
                        
                        TelaInicio inicio = new TelaInicio();
                        
                    } else {
                        JOptionPane.showMessageDialog(new JOptionPane(), "Nome já está sendo usado ou está em branco", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TelaCriarPersonagem.class.getName()).log(Level.SEVERE, null, ex);
                }
           } 
        });

        //Criado um timertask que cria um objeto que verifica o valor dos stats points do personagem
        //no momento exato
        MeuTimer.timer.scheduleAtFixedRate(new TaskStats(), 0, 1000/10);
        
        painel.add(botaoCriar, "gaptop 15px, align center, , wrap");
        
        container.add(painel);
    }
    
    private class BotaoMaisStat implements ActionListener {
        private JLabel forNumLabel;
        private JTextField pontosStatField;
        
        private BotaoMaisStat(JLabel forNumLabel, JTextField pontosStatField) {
            this.forNumLabel = forNumLabel;
            this.pontosStatField = pontosStatField;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            int pts = Integer.parseInt(this.pontosStatField.getText());
            int forNum = Integer.parseInt(this.forNumLabel.getText());
            
            forNum++;
            pts--;
            
            this.pontosStatField.setText(""+ pts);
            this.forNumLabel.setText(""+ forNum);
            TelaCriarPersonagem.this.statPointsDps++;
        }
    }
    
    private class BotaoMenosStat implements ActionListener {
        private JLabel forNumLabel;
        private JTextField pontosStatField;
        
        private BotaoMenosStat(JLabel forNumLabel, JTextField pontosStatField) {
            this.forNumLabel = forNumLabel;
            this.pontosStatField = pontosStatField;
        }
        
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            int pts = Integer.parseInt(this.pontosStatField.getText());
            int forNum = Integer.parseInt(this.forNumLabel.getText());
            
            forNum--;
            pts++;
            
            this.pontosStatField.setText(""+ pts);
            this.forNumLabel.setText(""+ forNum);
            TelaCriarPersonagem.this.statPointsDps--;
        }
    }
    
    private class TaskStats extends TimerTask {
        @Override
        public void run() {
            JButton botaoCriar = TelaCriarPersonagem.this.botaoCriar;
            int valorSP = Integer.parseInt(TelaCriarPersonagem.this.valorSPField.getText());
                
            botaoCriar.setEnabled((valorSP < 1));
        }
    }
}
