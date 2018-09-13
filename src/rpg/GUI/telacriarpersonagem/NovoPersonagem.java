package rpg.GUI.telacriarpersonagem;

import rpg.itens.Itens;
import java.io.IOException;
import java.util.HashMap;
import rpg.itens.Item;
import rpg.personagem.Armazem;
import rpg.personagem.EquipSlots;
import rpg.personagem.Inventario;
import rpg.personagem.Personagem;

public class NovoPersonagem extends Personagem {
    
    public NovoPersonagem() throws IOException {
        //É criado um novo personagem sem nome, level 1 e com 1 ponto de stat
        super.setXpAtual(0);
        super.setXpNecessaria(100);
        super.setXpTotal(0);
        super.setLevel(1);
        super.setManaMax(50);
        super.setManaAtual(getManaMax());
        super.setForca(0);
        super.setSorte(0);
        super.setAgilidade(0);
        super.setVitalidade(0);
        super.setInteligencia(0);
        super.setVidaMax(1);
        super.setVidaAtual(getVidaMax());
        super.setPontosStat(1);
        
        //É criado um HashMap que será o equipamento do personagem
        HashMap<EquipSlots, Item> equipamento = new HashMap<>();
        
        Itens itensJogo = new Itens();
        
        //O personagem começa o jogo com uma adaga
        equipamento.put(EquipSlots.ARMA, itensJogo.get("adaga"));
        super.setEquipamento(equipamento);
        
        super.setAtaque(1);
        super.setDanoMagico(1);
        super.setDefesa(1);
        super.setInventario(new Inventario());
        super.setArmazem(new Armazem());
        super.setMagias(new String[0]);
        super.setChanceCrit(1);
        super.setChanceEvasao(1);
    }
}
