package rpg.infomagiausada;

public class InfoMagiaCura extends InfoMagiaUsada {
    private int cura;
    
    @Override
    public void setDanoMagia(int dano) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCuraMagia(int cura) {
        this.cura = cura;
    }
    
    @Override
    public int getCuraMagia() {
        return this.cura;
    }
    
}
