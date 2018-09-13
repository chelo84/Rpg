package rpg.itens;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import rpg.personagem.Stats;

public class ItemMagia extends Item {
    private String nomeEfeito;
    private String valorEfeito;
    private int levelNecessario;
    private int forcaNecessaria;
    private int inteligenciaNecessaria;
    private int vitalidadeNecessaria;
    private int sorteNecessaria;
    private int agilidadeNecessaria;
    
    @JsonSetter("efeito")
    @Override
    public void setEfeito(String efeito) {
        String[] str = efeito.split(":");
        
        String nomeEfeito = str[0];
        String valorEfeito = str[1];
        
        this.nomeEfeito = nomeEfeito;
        this.valorEfeito = valorEfeito;
    }

    @Override
    public String getNomeEfeito() {
        return this.nomeEfeito;
    }

    @Override
    public String getValorEfeito() {
        return this.valorEfeito;
    }
    
    @JsonGetter("efeito")
    public String JsonGetEfeito() {
        return nomeEfeito +":"+ valorEfeito;
    }
    
    @Override
    public void setLevelNecessario(int levelNecessario) {
        this.levelNecessario = levelNecessario;
    }

    @Override
    public int getLevelNecessario() {
        return this.levelNecessario;
    }
    @Override
    public void setAtaque(int ataque) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAtaque() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAtaqueMagico(int ataque) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAtaqueMagico() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDefesa(int defesa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDefesa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void setForcaNecessaria(int forcaNecessaria) {
        this.forcaNecessaria = forcaNecessaria;
    }

    @Override
    public int getForcaNecessaria() {
        return this.forcaNecessaria;
    }

    @Override
    public void setVitalidadeNecessaria(int vitalidadeNecessaria) {
        this.vitalidadeNecessaria = vitalidadeNecessaria;
    }

    @Override
    public int getVitalidadeNecessaria() {
        return this.vitalidadeNecessaria;
    }

    @Override
    public void setSorteNecessaria(int sorteNecessaria) {
        this.sorteNecessaria = sorteNecessaria;
    }

    @Override
    public int getSorteNecessaria() {
        return this.sorteNecessaria;
    }

    @Override
    public void setAgilidadeNecessaria(int agilidadeNecessaria) {
        this.agilidadeNecessaria = agilidadeNecessaria;
    }

    @Override
    public int getAgilidadeNecessaria() {
        return this.agilidadeNecessaria;
    }

    @Override
    public void setInteligenciaNecessaria(int inteligenciaNecessaria) {
        this.inteligenciaNecessaria = inteligenciaNecessaria;
    }

    @Override
    public int getInteligenciaNecessaria() {
        return this.inteligenciaNecessaria;
    }
    
    @Override
    public String toString() {
        String str = "<html><b><center>"+ this.getNome();
        
        if(this.levelNecessario > 0)  {
            if(this.levelNecessario > Stats.level) str += "<br><font color=\"red\">Level: "
                                                        + this.getLevelNecessario()+"</font>";
            else str += "<br>Level: "+ this.getLevelNecessario();
        } 
        if(this.forcaNecessaria > 0)  {
            if(this.forcaNecessaria > Stats.forca) str += "<br><font color=\"red\">Força: "
                                                        + this.getForcaNecessaria()+"</font>";
            else str += "<br>Força: "+ this.getForcaNecessaria();
        } 
        if(this.inteligenciaNecessaria > 0)  {
            if(this.inteligenciaNecessaria > Stats.inteligencia) str += "<br><font color=\"red\">Inteligência: "
                                                                      + this.getInteligenciaNecessaria()+"</font>";
            else str += "<br>Inteligência: "+ this.getInteligenciaNecessaria();
        } 
        if(this.vitalidadeNecessaria > 0)  {
            if(this.vitalidadeNecessaria > Stats.vitalidade) str += "<br><font color=\"red\">Vitalidade: "
                                                                  + this.getVitalidadeNecessaria()+"</font>";
            else str += "<br>Vitalidade: "+ this.getVitalidadeNecessaria();
        } 
        if(this.sorteNecessaria > 0)  {
            if(this.sorteNecessaria > Stats.sorte) str += "<br><font color=\"red\">Sorte: "
                                                        + this.getSorteNecessaria()+"</font>";
            else str += "<br>Sorte: "+ this.getSorteNecessaria();
        } 
        if(this.agilidadeNecessaria > 0)  {
            if(this.agilidadeNecessaria > Stats.agilidade) str += "<br><font color=\"red\">Agilidade: "
                                                                + this.getAgilidadeNecessaria()+"</font>";
            else str += "<br>Agilidade: "+ this.getAgilidadeNecessaria();
        } 
        
        str += "</center>Tipo: "+ this.getTipo() +
               "<br>Descricao: "+ this.getDescricao();
        
        return str;
    }
    
}
