/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpg.monstro;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author W7
 */
public enum InvMonstroSlots {
    UM(0),
    DOIS(1),
    TRES(2),
    QUATRO(3),
    CINCO(4),
    SEIS(5),
    SETE(6),
    OITO(7),
    NOVE(8),
    DEZ(9);
    
    private int value;
    private static Map map = new HashMap<>();

    private InvMonstroSlots(int valor) {
        this.value = valor;
    }

    static {
        for (InvMonstroSlots slot : InvMonstroSlots.values()) {
            map.put(slot.value, slot);
        }
    }

    public static InvMonstroSlots valueOf(int slot) {
        return (InvMonstroSlots) map.get(slot);
    }
    
    public int getValue() {
        return value;
    }
}
