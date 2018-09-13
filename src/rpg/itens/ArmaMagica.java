package rpg.itens;

import rpg.personagem.Stats;

public class ArmaMagica extends Item {
    private int ataqueMagico;
    private int ataque;
    private int levelNecessario;
    private int forcaNecessaria;
    private int vitalidadeNecessaria;
    private int sorteNecessaria;
    private int agilidadeNecessaria;
    private int inteligenciaNecessaria;
    
    @Override
    public int getAtaque() {
        return ataque;
    }

    @Override
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    
    @Override
    public int getAtaqueMagico() {
        return ataqueMagico;
    }

    @Override
    public void setAtaqueMagico(int ataqueMagico) {
        this.ataqueMagico = ataqueMagico;
    }
    
    @Override
    public String toString() {
        String str = "<html><b><center>Nome: "+ this.getNome();
        
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
        
        str += "</center>Tipo: "+ this.getTipo()+
               "<br>Descrição: "+ this.getDescricao()+
               "<br>Ataque mágico: "+ this.getAtaqueMagico() +" + (<font color=\"purple\">"+ Stats.inteligencia +"</font>)</b></html>";  
            
        return str;
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
    public void setEfeito(String efeito) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNomeEfeito() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public String getValorEfeito() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
