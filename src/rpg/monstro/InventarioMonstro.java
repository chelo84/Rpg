package rpg.monstro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import rpg.itens.Item;

public class InventarioMonstro {
    public static int TAMANHOINVENTARIO = 10;
    private ArrayList<Item> itens;
    private ArrayList<Integer> itensQtd;
    
    public InventarioMonstro(Drop[] drops) {
        this();
        
        for(Drop drop : drops) {
            double dado = ThreadLocalRandom.current().nextDouble();
            
            if(dado < drop.getChance()) {
                this.itens.add(drop.getItem());
                int qtd = ThreadLocalRandom.current().nextInt(drop.getQtdMin(), drop.getQtdMax() + 1);
                this.itensQtd.add(qtd);
            }
        }
    }
    
    public InventarioMonstro() {
        this.itens = new ArrayList<>();
        this.itensQtd = new ArrayList<>();
    }
    
    public boolean add(Item item, int quantidade) throws IOException {
        if(this.itens.size() >= 10 || this.itensQtd.size() >= 10) {
            JOptionPane.showMessageDialog(new JOptionPane(), "Inventário está cheio, utilize o armazém", "Erro", JOptionPane.ERROR_MESSAGE);
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
            this.itens.add(item);
            this.itensQtd.add(quantidade);
            
            return true;
        }
    }   
    
    public boolean remove(String id) {
        if(InventarioMonstro.this.getItem(id) != null) {
            Item item = InventarioMonstro.this.getItem(id);
            
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
       Item item = InventarioMonstro.this.getItem(id);
       
       int i = this.itens.indexOf(item);
       
       return this.itensQtd.get(i);
    }
    
    public int getQtd(int index) {
       return this.itensQtd.get(index);
    }
    
    public int size() {
        return this.itens.size();
    }
}
