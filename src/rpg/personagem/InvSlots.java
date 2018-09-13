package rpg.personagem;

import java.util.HashMap;
import java.util.Map;

public enum InvSlots {
    UM(0),
    DOIS(1),
    TRES(2),
    QUATRO(3),
    CINCO(4),
    SEIS(5),
    SETE(6),
    OITO(7),
    NOVE(8),
    DEZ(9),
    ONZE(10),
    DOZE(11),
    TREZE(12),
    QUATORZE(13),
    QUINZE(14);
    
    private int value;
    private static Map map = new HashMap<>();

    private InvSlots(int valor) {
        this.value = valor;
    }

    static {
        for (InvSlots slot : InvSlots.values()) {
            map.put(slot.value, slot);
        }
    }

    public static InvSlots valueOf(int slot) {
        return (InvSlots) map.get(slot);
    }
    
    public int getValue() {
        return value;
    }
}
