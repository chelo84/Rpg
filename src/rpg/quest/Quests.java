package rpg.quest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rpg.GUI.util.Mapper;
import rpg.itens.Item;

public class Quests extends ArrayList<Quest> {
    
    public Quests() throws IOException {
        List<Quest> p = Arrays.asList(Mapper.mapper.readValue(new File("elementos\\jsonfiles\\quests.json"), Quest[].class));
        this.addAll(p);
    }
}
