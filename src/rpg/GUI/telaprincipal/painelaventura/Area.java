package rpg.GUI.telaprincipal.painelaventura;

import java.io.IOException;
import rpg.monstro.Monstro;
import rpg.monstro.Monstros;

//Criação de uma área
public class Area {
    private String nome;
    private Monstro[] monstros;
    
    //Constrói a área com o seu nome e os monstros que vivem dentro
    public void setMonstros(MonstroArea[] monstrosArea) throws IOException {
        Monstro[] monstros = new Monstro[monstrosArea.length];
        Monstros monstrosJogo = new Monstros();
        
        int i = 0;
        for(MonstroArea monstroArea : monstrosArea) {
            monstros[i] = monstrosJogo.get(monstroArea.getId());
            
            i++;
        }
        
        this.monstros = monstros;
    }
    
    //Retorna um array com os monstros da área
    public Monstro[] getMonstros() {
        return monstros;
    }
    
    //Insere o nome da área
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //Retorna o nome da área
    public String getNome() {
        return this.nome;
    }
}
