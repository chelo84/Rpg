package rpg.itens;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ItemUsavel extends Item {
    private String nomeEfeito;
    private String valorEfeito;
    
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

    public String getValorEfeito() {
        return this.valorEfeito;
    }
    
    @JsonGetter("efeito")
    public String JsonGetEfeito() {
        return nomeEfeito +":"+ valorEfeito;
    }
    @Override
    public void setAtaque(int ataque) {
        
    }

    @Override
    public int getAtaque() {
        return 0;
    }

    @Override
    public void setDefesa(int defesa) {
    }

    @Override
    public int getDefesa() {
        return 0;
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
    public void setLevelNecessario(int levelNecessario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLevelNecessario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setForcaNecessaria(int forcaNecessaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getForcaNecessaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVitalidadeNecessaria(int vitalidadeNecessaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getVitalidadeNecessaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSorteNecessaria(int sorteNecessaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSorteNecessaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAgilidadeNecessaria(int agilidadeNecessaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAgilidadeNecessaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setInteligenciaNecessaria(int inteligenciaNecessaria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getInteligenciaNecessaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }
