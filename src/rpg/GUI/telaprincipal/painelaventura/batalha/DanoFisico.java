package rpg.GUI.telaprincipal.painelaventura.batalha;

public class DanoFisico {
    int dano;
    boolean crit = false;

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public boolean isCrit() {
        return crit;
    }

    public void setCrit(boolean crit) {
        this.crit = crit;
    }
    
    @Override
    public String toString() {
        return ""+ this.dano;
    }
    
    
}
