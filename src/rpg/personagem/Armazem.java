package rpg.personagem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import rpg.itens.Item;
import static rpg.personagem.Inventario.TAMANHOINVENTARIO;

public class Armazem {
    static int TAMANHOARMAZEM = 20;    
    
    ArrayList<Item> itens;
    public ArrayList<Integer> itensQtd;
    
    public Armazem(Item[] itens, Integer[] qtds) {
        this.itens = new ArrayList<>(Arrays.asList(itens));
        this.itensQtd = new ArrayList<>(Arrays.asList(qtds));
    }
    
    public Armazem() {
        this.itens = new ArrayList<>();
        this.itensQtd = new ArrayList<>();
    }
    
    public boolean add(Item item, int quantidade) throws IOException {
        if((this.itens.indexOf(item) == -1) && (this.itens.size() >= TAMANHOARMAZEM || this.itensQtd.size() >= TAMANHOARMAZEM)) {
            return false;
        }
        try {
            int i = this.itens.indexOf(item);
            if(itensQtd.get(i) < item.getQtdMax()) {
                this.itensQtd.set(i, this.itensQtd.get(i) + quantidade);
            } else {
                throw new Exception("excecao");
            }
                return true;
        } catch(Exception ex) {
            if(this.itens.size() >= TAMANHOINVENTARIO || this.itensQtd.size() >= TAMANHOINVENTARIO) {
                return false;
            } else {
                this.itens.add(item);
                this.itensQtd.add(quantidade);
            
                return true;
            }
        }
    }   
    
    public boolean add(Item item, int quantidade, int index) throws IOException {
        if(this.itens.size() >= TAMANHOARMAZEM || this.itensQtd.size() >= TAMANHOARMAZEM) {
            return false;
        }
        try {
            int i = this.itens.indexOf(item);
            if(itensQtd.get(i) < item.getQtdMax()) {
                this.itensQtd.set(i, this.itensQtd.get(i) + quantidade);
            } else {
                throw new Exception("excecao");
            }
                return true;
        } catch(Exception ex) {
            this.itens.add(index, item);
            this.itensQtd.add(index, quantidade);
            
            return true;
        }
    }   
    
    public boolean remove(String id) {
        if(this.getItem(id) != null) {
            Item item = this.getItem(id);
            
            int i = this.itens.indexOf(item);
            
            this.itensQtd.set(i, this.itensQtd.get(i) - 1);
            if(this.itensQtd.get(i) < 1) {
                this.itens.remove(i);
                this.itensQtd.remove(i);
            }
            return true;
        }
        
        return false;
    }
    
    public boolean remove(String id, int qtd) {
        if(this.getItem(id) != null) {
            Item item = this.getItem(id);
            
            int i = this.itens.indexOf(item);
            
            this.itensQtd.set(i, this.itensQtd.get(i) - qtd);
            if(this.itensQtd.get(i) < 1) {
                this.itens.remove(i);
                this.itensQtd.remove(i);
            }
            return true;
        }
        
        return false;
    }
    
    public Item getItem(String id) {
        for(Item item : this.itens) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
        
        return null;
    }
    
    public Item getItem(int index) {
        return this.itens.get(index);
    }
    
    public int getQtd(String id) {
       Item item = this.getItem(id);
       
       int i = this.itens.indexOf(item);
       
       return this.itensQtd.get(i);
    }
    
    public int getQtd(int index) {
       return this.itensQtd.get(index);
    }
    
    public int getIndex(String id) {
        Item item = this.getItem(id);
        
        int i = this.itens.indexOf(item);
        
        return i;
    }
    
    public int size() {
        return this.itens.size();
    }
    
}
