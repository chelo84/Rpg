package rpg.personagem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import static rpg.GUI.util.Listas.MAGIASJOGO;
import rpg.magia.Magia;

public final class MagiasPersonagem {
    private final ArrayList<Magia> magias;
    
    public void ordernarPorLevel() {
        this.magias.sort(new LevelComparator());
    }
    
    public MagiasPersonagem(Magia[] magias) {
        this.magias = new ArrayList<>();
        this.magias.addAll(Arrays.asList(magias));
        
        ordernarPorLevel();
    }
    
    public MagiasPersonagem() throws IOException {
        this.magias = new ArrayList<>();
        
        ordernarPorLevel();
    }
    
    public boolean add(Magia magia) throws IOException {
        //Verifica se o id é vazio, se for retorna false e não adiciona a lista
        if(magia.getId().trim().isEmpty()) {
            return false;
        }
        
        //Verifica se a lista já não contém um personagem com o mesmo id
        if(get(magia.getId().trim()) == null) {
            this.magias.add(magia);
            
            ordernarPorLevel();
            
            return true;
        }
        
        return false;
    }
    
    public boolean add(String magiaId) throws IOException {
        Magia magia = MAGIASJOGO.get(magiaId);
        
        if(this.get(magia.getId()) == null) {
            this.magias.add(magia);
            
            this.magias.sort(new LevelComparator());
            
            return true;
        }
        
        return false;
    }
    
    public boolean remove(String magiaId) {
        Magia magia = get(magiaId);
        
        if(magia != null) {
            this.magias.remove(magia);
            
            ordernarPorLevel();
            
            return true;
        } else {
            return false;
        }
    }
    
    public Magia get(int i) {
        return this.magias.get(i);
    }
    
    public Magia get(String id) {
        for(Magia magia : this.magias) {
            if(magia.getId().equals(id)) {
                return magia;
            }
        }
        
        return null;
    }
    
    public int size() {
        return this.magias.size();
    }    

    public void remove(int i) {
        this.magias.remove(i);
        
        ordernarPorLevel();
    }

    public class LevelComparator implements Comparator<Magia> {
        @Override
        public int compare(Magia primeiraMagia, Magia segundaMagia) {
           return (primeiraMagia.getLevelNecessario() - segundaMagia.getLevelNecessario());
        }
    }
}
