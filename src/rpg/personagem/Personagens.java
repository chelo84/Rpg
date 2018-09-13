package rpg.personagem;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.GUI.util.Mapper;

public class Personagens {
    public ArrayList<Personagem> personagens;
    
    public Personagens() throws IOException {
        this.personagens = new ArrayList<>();
        List<Personagem> p = Arrays.asList(Mapper.mapper.readValue(new File("elementos\\jsonfiles\\personagens.json"), Personagem[].class));
        this.personagens.addAll(p);
       
    }
    
    public boolean add(Personagem personagem) throws IOException {
        //Verifica se o nome é vazio, se for retorna false e não adiciona a lista de personagens.
        if(personagem.getNome().trim().isEmpty()) {
            return false;
        }
        
        //Verifica se a lista de personagens já não contém um personagem com o mesmo nome.
        if(get(personagem.getNome().trim()) == null) {
            this.personagens.add(personagem);
            
            ObjectWriter writer = Mapper.mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("src/elementos/jsonfiles/personagens.json"), personagens);
            
            return true;
        }
        
        return false;
    }
    
    public boolean remove(String nome) {
        Personagem personagem = get(nome);
        
        if(personagem != null) {
            this.personagens.remove(personagem);
            return true;
        } else {
            return false;
        }
    }
    
    public Personagem get(int i) {
        return this.personagens.get(i);
    }
    
    public Personagem get(String nome) {
        for(Personagem personagem : this.personagens) {
            if(personagem.getNome().toLowerCase().equals(nome.toLowerCase())) {
                return personagem;
            }
        }
        
        return null;
    }
    
    public int size() {
        return this.personagens.size();
    }
    
    public  void atualizarInfoPersonagem() throws IOException {
        ObjectWriter writer = Mapper.mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("elementos\\jsonfiles\\personagens.json"), personagens);

    }    
}
