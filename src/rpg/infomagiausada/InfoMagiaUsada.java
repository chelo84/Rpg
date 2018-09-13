package rpg.infomagiausada;

public abstract class InfoMagiaUsada {
    private boolean sucesso;
            
    public abstract void setDanoMagia(int dano);
    public int getDanoMagia() { return 0; }
    public abstract void setCuraMagia(int cura);
    public int getCuraMagia() { return 0; }
    
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    
    public boolean isSucesso() {
        return this.sucesso;
    }
}
