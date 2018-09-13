package rpg.magia;

import rpg.GUI.util.MeuLabel;
import rpg.personagem.Stats;

public class Magia {
    private String id;
    private String nome;
    private int custo;
    private String alvo;
    private String tipo;
    private int valorEfeito;
    private double valorEfeitoRatio;
    private int levelNecessario;
    private String img;
    private String animacao;

    public int getLevelNecessario() {
        return levelNecessario;
    }

    public void setLevelNecessario(int levelNecessario) {
        this.levelNecessario = levelNecessario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public String getAlvo() {
        return alvo;
    }

    public void setAlvo(String alvo) {
        this.alvo = alvo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getValorEfeito() {
        return valorEfeito;
    }

    public void setValorEfeito(int valorEfeito) {
        this.valorEfeito = valorEfeito;
    }

    public double getValorEfeitoRatio() {
        return valorEfeitoRatio;
    }

    public void setValorEfeitoRatio(double valorEfeitoRatio) {
        this.valorEfeitoRatio = valorEfeitoRatio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        /*ImageIcon icone = new ImageIcon(img);
        Image imagem = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);*/
        
        this.img = img;
    }

    public String getAnimacao() {
        return animacao;
    }

    public void setAnimacao(String animacao) {
        /*ImageIcon icone = new ImageIcon(animacao);
        Image imagem = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);*/
        
        this.animacao = animacao;
    }
    
    @Override
    public String toString() {
        String str = "<html><b><center>"+ this.getNome() +
               "<br>Level: "+ this.getLevelNecessario() +
               "</center>Tipo: "+ this.getTipo() +
               "<br>Alvo: "+ this.getAlvo() +
               "<br>Custo de mana: "+ this.getCusto();
        
            switch(this.getTipo()) {
                case "dano" : str += "<br>Dano: "+ this.getValorEfeito()
                                   + " + ( <font color=\"purple\">"+ (int) (Stats.danoMagico*getValorEfeitoRatio()) +"</font> )</html>";
                break;
                            
                case "cura" : str += "<br>Cura: "+ this.getValorEfeito() +
                                     "( <font color=\"purple\">"+ (int) (Stats.danoMagico*getValorEfeitoRatio()) +"</font> )</html>";
                break;
            }
        
        return str;
    }
    
    public int compareTo(Magia outraMagia) {
        return (this.getLevelNecessario() - outraMagia.getLevelNecessario());
    }
}
