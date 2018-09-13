package rpg.monstro;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import rpg.GUI.telaprincipal.painelaventura.batalha.DanoFisico;
import rpg.personagem.Personagem;

public class Monstro {
    private String id;
    private String nome;
    private String tipo;
    private int vida;
    private int xp;
    private int ataque;
    private int defesa;
    private String img;
    private InventarioMonstro drop;
    private int vidaAtual;

    @Override
    public String toString() {
        return "Id: "+ this.getId()+
               "\nNome: "+ this.getNome()+
               "\nTipo: "+ this.getTipo()+
               "\nVida: "+ this.getVida()+
               "\nXp: "+ this.getXp()+
               "\nAtaque: "+ this.getAtaque()+
               "\nDefesa: "+ this.getDefesa();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        
        this.img = img;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
        
        this.setVidaAtual(vida);
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public InventarioMonstro getDrop() {
        return drop;
    }
    
    public void setDrop(Drop[] drops) {
        this.drop = new InventarioMonstro(drops);
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public DanoFisico causarDanoFisico(Personagem personagem) {
        DanoFisico dano = new DanoFisico();
        
        boolean evasao = (ThreadLocalRandom.current().nextDouble() < personagem.getChanceEvasao());
        int defende = (ThreadLocalRandom.current().nextInt(0, personagem.getDefesa()+1));
        int danoTomado = (evasao)? 0 : ataque - defende;
        if (danoTomado < 0) danoTomado = 0;
        
        if(personagem.getVidaAtual() - danoTomado > 0) {
            personagem.setVidaAtual(personagem.getVidaAtual() - danoTomado);
            
            dano.setDano(danoTomado);
            
            return dano;
        } else {
            int vida = personagem.getVidaAtual();
            
            personagem.setVidaAtual(0);
            dano.setDano(vida);
            
            return dano;
        }
    }
}
