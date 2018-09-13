package rpg.personagem;

import java.util.HashMap;
import java.util.Map;

public enum ArmazemSlots {
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
    QUINZE(14),
    DEZESSEIS(15),
    DEZESSETE(16),
    DEZOITO(17),
    DEZENOVE(18),
    VINTE(19);
    
    private int value;
    private static Map map = new HashMap<>();

    private ArmazemSlots(int valor) {
        this.value = valor;
    }

    static {
        for (ArmazemSlots slot : ArmazemSlots.values()) {
            map.put(slot.value, slot);
        }
    }

    public static ArmazemSlots valueOf(int slot) {
        return (ArmazemSlots) map.get(slot);
    }
    
    public int getValue() {
        return value;
    }
}
