package rpg.GUI.telaprincipal.painelaventura;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.monstro.Monstro;

//Criação de uma lista com as áreas
public class Areas {
    ArrayList<Area> areas = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    
    public Areas() throws IOException {
        List<Area> p = Arrays.asList(mapper.readValue(new File("elementos\\jsonfiles\\areas.json"), Area[].class));
        this.areas.addAll(p);
    }
    
    //Adiciona uma área
    public void add(Area area) {
        this.areas.add(area);
    }
    
    //Remove uma área
    public boolean remove(String nome) {
        Area area = this.get(nome);
        
        if(area != null) {
            this.areas.remove(area);
            return true;
        } else {
            return false;
        }
    }
    
    //Retorna a área em certo índice
    public Area get(int i) {
        return this.areas.get(i);
    }
    
    //Retorna a área com certo nome
    public Area get(String nome) {
        for(Area area : this.areas) {
            if(area.getNome().equals(nome)) {
                return area;
            }
        }
        
        return null;
    }
    
    //Retorna o tamanho da lista
    public int size() {
        return this.areas.size();
    }    
}
