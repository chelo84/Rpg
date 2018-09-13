package rpg.GUI.telaprincipal.painelaventura.batalha;

import rpg.infomagiausada.InfoMagiaUsada;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import rpg.GUI.telaprincipal.painelaventura.Area;
import rpg.magia.Magia;
import rpg.monstro.Monstro;
import rpg.personagem.Personagem;

//Criação da batalha
public class Batalha {
    private Personagem personagem;
    private Monstro monstro;
    
    Batalha(Personagem personagem, Area area) {
        this.personagem = personagem;
        
        Monstro[] monstros = area.getMonstros();
        
        int dadoMonstro = ThreadLocalRandom.current().nextInt(monstros.length-1);
        double dadoChefe = ThreadLocalRandom.current().nextDouble();
        
        if(dadoChefe <= 0.050) {
            for(Monstro m : monstros) {
                if(m.getTipo().trim().toLowerCase().equals("chefe")) {
                    this.monstro = m;
                }
            }
        } else {
            this.monstro = monstros[dadoMonstro];
        }
    }
    
    //Vira a rodada da batalha
    public boolean ataqueBasico(JTextPane text) throws BadLocationException {
        DanoFisico danoCausadoMonstro = this.monstro.causarDanoFisico(this.personagem);
        DanoFisico danoCausadoPersonagem = this.personagem.causarDanoFisico(this.monstro);
        StyledDocument doc = text.getStyledDocument();
        
        //É criado os estilos de texto (negrito, cor azul, vermelho e magenta)
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(
        StyleContext.DEFAULT_STYLE);
        Style boldStyle = doc.addStyle("bold", defaultStyle);
        StyleConstants.setBold(boldStyle, true);
        Style blueStyle = doc.addStyle("blue", boldStyle);
        StyleConstants.setForeground(blueStyle, Color.BLUE);
        Style redStyle = doc.addStyle("red", boldStyle);
        StyleConstants.setForeground(redStyle, Color.RED);
        Style magentaStyle = doc.addStyle("magenta", boldStyle);
        StyleConstants.setForeground(magentaStyle, Color.MAGENTA);
        
        String nomePersonagem = this.personagem.getNome();
        String nomeMonstro = this.monstro.getNome();
        
        int lengthDanoCausadoMonstro = String.valueOf(danoCausadoMonstro).length();
        int lengthDanoCausadoPersonagem = String.valueOf(danoCausadoPersonagem).length();
        int indexInicio = doc.getLength();
        String textoDanoCausadoPersonagem = "";
        
        if(danoCausadoPersonagem.getDano() > 0) {
            //Dano é critico
            if(danoCausadoPersonagem.isCrit() == true) {
                textoDanoCausadoPersonagem = "[CRITICO]"+ nomePersonagem +" causou "+ danoCausadoPersonagem +
                                             " de dano em "+ nomeMonstro +"\n";
                
                doc.insertString(doc.getLength(), textoDanoCausadoPersonagem, boldStyle);
                doc.setCharacterAttributes(indexInicio += 9, nomePersonagem.length(), blueStyle, false);
                doc.setCharacterAttributes(indexInicio += (8 + nomePersonagem.length()), lengthDanoCausadoPersonagem, magentaStyle, false);
                doc.setCharacterAttributes(indexInicio += (12 + lengthDanoCausadoPersonagem), nomeMonstro.length(), redStyle, false);
            } else {
                //Insere o estilo nos caracteres que precisam
                textoDanoCausadoPersonagem = nomePersonagem +" causou "+ danoCausadoPersonagem +
                                             " de dano em "+ nomeMonstro +"\n";
                
                doc.insertString(doc.getLength(), textoDanoCausadoPersonagem, boldStyle);
                doc.setCharacterAttributes(indexInicio, nomePersonagem.length(), blueStyle, false);
                doc.setCharacterAttributes(indexInicio += (8 + nomePersonagem.length()), lengthDanoCausadoPersonagem, magentaStyle, false);
                doc.setCharacterAttributes(indexInicio += (12 + lengthDanoCausadoPersonagem), nomeMonstro.length(), redStyle, false);
            }
            
        } else {
            //Personagem erra
            //Insere o estilo nos caracteres que precisam
            textoDanoCausadoPersonagem = nomePersonagem +" errou\n";
            doc.insertString(doc.getLength(), textoDanoCausadoPersonagem, boldStyle);
            doc.setCharacterAttributes(indexInicio, nomePersonagem.length(), blueStyle, false);
            doc.setCharacterAttributes(indexInicio += nomePersonagem.length(), 6, magentaStyle, false);
        }
        
        indexInicio = doc.getLength();
        String textoDanoCausadoMonstro = "";
        
        if(danoCausadoMonstro.getDano() > 0) {
            //Insere o estilo nos caracteres que precisam
            textoDanoCausadoMonstro = nomeMonstro +" causou "+ danoCausadoMonstro +" de dano em "+ nomePersonagem +"\n";
            
            doc.insertString(doc.getLength(), textoDanoCausadoMonstro, boldStyle);
            doc.setCharacterAttributes(indexInicio, nomeMonstro.length(), redStyle, false);
            doc.setCharacterAttributes(indexInicio += (8 + nomeMonstro.length()), lengthDanoCausadoMonstro, magentaStyle, false);
            doc.setCharacterAttributes(indexInicio += (12 + lengthDanoCausadoMonstro), nomePersonagem.length(), blueStyle, false);
            
        } else {
            //Monstro erra
            //Insere o estilo nos caracteres que precisam
            textoDanoCausadoMonstro = nomeMonstro +" errou\n";
            
            doc.insertString(doc.getLength(), textoDanoCausadoMonstro, boldStyle);
            doc.setCharacterAttributes(indexInicio, nomeMonstro.length(), redStyle, false);
            doc.setCharacterAttributes(indexInicio += nomeMonstro.length(), 6, magentaStyle, false);
        }
        
        text.setCaretPosition(text.getDocument().getLength());
        return danoCausadoPersonagem.isCrit();
    }
    
    public boolean tentarFugir(JTextPane text) throws BadLocationException {
        StyledDocument doc = text.getStyledDocument();
        
        //É criado os estilos de texto (negrito, cor azul, vermelho e magenta)
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(
        StyleContext.DEFAULT_STYLE);
        Style boldStyle = doc.addStyle("bold", defaultStyle);
        StyleConstants.setBold(boldStyle, true);
        Style blueStyle = doc.addStyle("blue", boldStyle);
        StyleConstants.setForeground(blueStyle, Color.BLUE);
        Style redStyle = doc.addStyle("red", boldStyle);
        StyleConstants.setForeground(redStyle, Color.RED);
        Style magentaStyle = doc.addStyle("magenta", boldStyle);
        StyleConstants.setForeground(magentaStyle, Color.MAGENTA);
        
        //Dado é rodado para saber se foge ou não
        double dado = ThreadLocalRandom.current().nextDouble();
        boolean tentativa = dado < (0.10 + (personagem.getAgilidade() * 0.03));
        
        if(tentativa == true) {
            doc.insertString(doc.getLength(), this.personagem.getNome(), blueStyle);
            doc.insertString(doc.getLength(), " teve êxito em fugir.\n", boldStyle);
            
        } else {
            DanoFisico danoCausadoMonstro = this.monstro.causarDanoFisico(this.personagem);
            String texto = this.personagem.getNome() +" falhou em fugir\n";
            int indexInicio = doc.getLength();
            doc.insertString(doc.getLength(), texto, boldStyle);
            
            doc.setCharacterAttributes(indexInicio, this.personagem.getNome().length(), blueStyle, false);
            
            texto = this.monstro.getNome() +" causou "+ danoCausadoMonstro +" de dano em "+ this.personagem.getNome() +"\n";
            indexInicio = doc.getLength();
            doc.insertString(doc.getLength(), texto, boldStyle);
            
            doc.setCharacterAttributes(indexInicio, this.monstro.getNome().length(), redStyle, false);
            doc.setCharacterAttributes(indexInicio+this.monstro.getNome().length()+8, String.valueOf(danoCausadoMonstro).length(), magentaStyle, false);
            doc.setCharacterAttributes(indexInicio+this.monstro.getNome().length()+20+String.valueOf(danoCausadoMonstro).length(), this.personagem.getNome().length(), blueStyle, false);
        }
        
        text.setCaretPosition(text.getDocument().getLength());
        return tentativa;
    }
    
    //Retorna o monstro
    public Monstro getMonstro() {
        return this.monstro;
    }

    void fimBatalhaLog(JTextPane text, int xp) throws BadLocationException {
        String fimBatalha = "\n["+ personagem.getNome() +" derrotou "+ monstro.getNome() +" e conseguiu "+ xp +" de XP]\n";
        
        StyledDocument doc = text.getStyledDocument();
        
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(
        StyleContext.DEFAULT_STYLE);
        Style boldStyle = doc.addStyle("bold", defaultStyle);
        StyleConstants.setBold(boldStyle, true);
        Style blueStyle = doc.addStyle("blue", boldStyle);
        StyleConstants.setForeground(blueStyle, Color.BLUE);
        Style redStyle = doc.addStyle("red", boldStyle);
        StyleConstants.setForeground(redStyle, Color.RED);
        Style yellowStyle = doc.addStyle("magenta", boldStyle);
        StyleConstants.setForeground(yellowStyle, Color.decode("#c18607"));
        
        String nomePersonagem = personagem.getNome();
        String nomeMonstro = monstro.getNome();
        int tamanhoXp = new String(""+ xp).length();
        
        int indexInicio = doc.getLength()+2;
        
        doc.insertString(doc.getLength(), fimBatalha, boldStyle);
        
        doc.setCharacterAttributes(indexInicio , nomePersonagem.length(), blueStyle, false);
        doc.setCharacterAttributes(indexInicio+nomePersonagem.length()+10, nomeMonstro.length(), redStyle, false);
        doc.setCharacterAttributes(indexInicio+nomePersonagem.length()+10+nomeMonstro.length()+13, tamanhoXp, yellowStyle, false);
        
        text.setCaretPosition(text.getDocument().getLength());
    }

    public boolean usarMagia(JTextPane text, Magia magia) throws BadLocationException {
        InfoMagiaUsada info = this.personagem.usarMagia(magia, monstro);
        if(info.isSucesso()) {
            DanoFisico danoCausadoMonstro = this.monstro.causarDanoFisico(this.personagem);

            StyledDocument doc = text.getStyledDocument();
            Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(
            StyleContext.DEFAULT_STYLE);
            Style boldStyle = doc.addStyle("bold", defaultStyle);
            StyleConstants.setBold(boldStyle, true);
            Style blueStyle = doc.addStyle("blue", boldStyle);
            StyleConstants.setForeground(blueStyle, Color.BLUE);
            Style redStyle = doc.addStyle("red", boldStyle);
            StyleConstants.setForeground(redStyle, Color.RED);
            Style magentaStyle = doc.addStyle("magenta", boldStyle);
            StyleConstants.setForeground(magentaStyle, Color.MAGENTA);
            Style greenStyle = doc.addStyle("green", boldStyle);
            StyleConstants.setForeground(greenStyle, Color.GREEN);
            Style cyanStyle = doc.addStyle("cyan", boldStyle);
            StyleConstants.setForeground(cyanStyle, Color.CYAN);

            String nomePersonagem = this.personagem.getNome();
            String nomeMonstro = this.monstro.getNome();

            int indexInicio;

            String textoInfoMagia = "";
            String magiaTipo = magia.getTipo().trim().toLowerCase();
            switch(magiaTipo) {
                case "dano" : {
                    textoInfoMagia = nomePersonagem
                            + " causou "+ info.getDanoMagia() 
                            + " em "+ nomeMonstro
                            + " com a magia "+ magia.getNome() +"\n";

                    indexInicio = doc.getLength();

                    doc.insertString(doc.getLength(), textoInfoMagia, boldStyle);

                    doc.setCharacterAttributes(indexInicio,
                                               nomePersonagem.length(),
                                               blueStyle, false);
                    doc.setCharacterAttributes(indexInicio += 8+nomePersonagem.length(), 
                                               String.valueOf(info.getDanoMagia()).length(),
                                               magentaStyle, false);
                    doc.setCharacterAttributes(indexInicio += 4+String.valueOf(info.getDanoMagia()).length(),
                                               nomeMonstro.length(),
                                               redStyle, false);
                    doc.setCharacterAttributes(indexInicio += 13+nomeMonstro.length(),
                                               magia.getNome().length(),
                                               cyanStyle, false);
                    
                    break;
                }
                case "cura" : {
                    textoInfoMagia = this.personagem.getNome()
                            + " se curou "+ info.getCuraMagia() +" com "
                            + magia.getNome() +"\n";
                    
                    indexInicio = doc.getLength();

                    doc.insertString(doc.getLength(), textoInfoMagia, boldStyle);
                    doc.setCharacterAttributes(indexInicio,
                                               nomePersonagem.length(),
                                               blueStyle, false);
                    doc.setCharacterAttributes(indexInicio += 10+nomePersonagem.length(),
                                               String.valueOf(info.getCuraMagia()).length(),
                                               greenStyle, false);
                    doc.setCharacterAttributes(indexInicio += +5+String.valueOf(info.getCuraMagia()).length(),
                                               magia.getNome().length(),
                                               cyanStyle, false);
                    break;
                }
            }

            String textoDanoCausadoMonstro = "";

            if(danoCausadoMonstro.getDano() > 0) {
                //Insere o estilo nos caracteres que precisam
                textoDanoCausadoMonstro = nomeMonstro +" causou "+ danoCausadoMonstro +" de dano em "+ nomePersonagem +"\n";

                indexInicio = doc.getLength();
                doc.insertString(doc.getLength(), textoDanoCausadoMonstro, boldStyle);

                doc.setCharacterAttributes(indexInicio, nomeMonstro.length(), redStyle, false);
                doc.setCharacterAttributes(indexInicio += (8 + nomeMonstro.length()), String.valueOf(danoCausadoMonstro.getDano()).length(), magentaStyle, false);
                doc.setCharacterAttributes(indexInicio += (12 + String.valueOf(danoCausadoMonstro.getDano()).length()), nomePersonagem.length(), blueStyle, false);

            } else {
                //Monstro erra
                //Insere o estilo nos caracteres que precisam
                textoDanoCausadoMonstro = nomeMonstro +" errou\n";

                indexInicio = doc.getLength();
                doc.insertString(doc.getLength(), textoDanoCausadoMonstro, boldStyle);

                doc.setCharacterAttributes(indexInicio, nomeMonstro.length(), redStyle, false);
                doc.setCharacterAttributes(indexInicio += nomeMonstro.length(), 6, magentaStyle, false);
            }

            text.setCaretPosition(text.getDocument().getLength());
            
            return true;
        } else {
            JOptionPane.showMessageDialog(new JOptionPane(), Batalha.this.personagem.getNome()
                    + " não possui mana suficiente para usar esta magia", "Mana insuficiente", JOptionPane.WARNING_MESSAGE);                
            
            return false;
        }
    }
}
