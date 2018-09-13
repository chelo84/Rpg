package rpg.personagem;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rpg.GUI.telaprincipal.painelaventura.batalha.DanoFisico;
import rpg.infomagiausada.InfoMagiaUsada;
import static rpg.GUI.util.Listas.ITENSJOGO;
import rpg.infomagiausada.InfoMagiaCura;
import rpg.infomagiausada.InfoMagiaDano;
import rpg.itens.Item;
import rpg.magia.Magia;
import rpg.magia.Magias;
import rpg.monstro.Monstro;

public class Personagem {
    private String nome;
    private int xpAtual;
    private int xpTotal;
    private int xpNecessaria;
    private int level;
    private HashMap<EquipSlots, Item> equipamento;
    private Integer forca;
    private Integer sorte;
    private int agilidade;
    private int vitalidade;
    private int inteligencia;
    private int pontosStat;
    private int pontosStatTotal;
    private Integer ataque;
    private Integer danoMagico;
    private int defesa;
    private Inventario inventario;
    private double chanceCrit;
    private double chanceEvasao;
    private int vidaAtual;
    private int vidaMax;
    private Armazem armazem;
    private MagiasPersonagem magias;
    private int manaAtual;
    private int manaMax;
    
    private final int VIDAMAXPADRAO = 40;
    private final int VIDALEVELRATIO = 10;
    private final int VIDAVITRATIO = 3;
    private final int MANAMAXPADRAO = 48;
    private final int MANALEVELRATIO = 2;
    private final int MANAINTELIGENCIARATIO = 5;
    private final double CRITSORTERATIO = 0.0250;
    private final double EVASAOAGIRATIO = 100;
    private final int ATAQUESEMARMA = 5;

    public int getManaAtual() {
        return manaAtual;
    }

    public void setManaAtual(int manaAtual) {
        this.manaAtual = manaAtual;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = MANAMAXPADRAO + (MANALEVELRATIO*level) + (MANAINTELIGENCIARATIO*inteligencia);
    }

    public MagiasPersonagem getMagias() {
        return magias;
    }
    
    @JsonGetter("magias")
    public String[] jsonGetMagias() {
        String[] magias = new String[this.magias.size()];
        
        for(int i = 0; i < this.magias.size(); i++) {
            Magia magia = this.magias.get(i);
            
            try {
                String str = magia.getId();

                magias[i] = str;
            } catch(NullPointerException ex) {
                magias[i] = null;
            }
        }
        
        return magias;
    }
    
    public void setMagias(String[] magiasId) throws IOException {
        Magias magiasJogo = new Magias();
        MagiasPersonagem magiasPersonagem = new MagiasPersonagem();
        
        try {
            for(String magiaId : magiasId) {
                Magia magia = magiasJogo.get(magiaId);
                magiasPersonagem.add(magia);
            }
        } catch (Exception ex) {
            
        }
        
        this.magias = magiasPersonagem;
    }

    public int getPontosStatTotal() {
        return this.pontosStatTotal;
    }

    public void setPontosStatTotal(int pontosStatUsados) {
        this.pontosStatTotal = forca+sorte+agilidade+vitalidade+pontosStat;
    }
    
    public int getPontosStat() {
        return pontosStat;
    }

    public void setPontosStat(int pontosStats) {
        this.pontosStat = pontosStats;
    }
    
    public HashMap<EquipSlots, Item> getEquipamento() {
        return this.equipamento;
    }
    @JsonSetter("equipamento")
    public void JsonSetEquipamento(HashMap<EquipSlots, String> map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        //Cria um HashMap com os objetos Item em seus respectivos slots utilizando os IDs que estão no arquivo 'itens.json' 
        HashMap<EquipSlots, Item> equipamento = new HashMap<>();
        
        for(EquipSlots slot : map.keySet()) {
            String idItem = map.get(slot);
            equipamento.put(slot, ITENSJOGO.get(idItem));
            
        }
        
        this.equipamento = equipamento;
    }
    
    public void setEquipamento(HashMap<EquipSlots, Item> equipamento) {
        this.equipamento = equipamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getXpAtual() {
        return xpAtual;
    }

    public void setXpAtual(int xpAtual) {
        this.xpAtual = xpAtual;
    }

    public void setXpNecessaria(int xpNecessaria) {
        this.xpNecessaria = xpNecessaria;
    }
    
    public int getXpNecessaria() {
        return xpNecessaria;
    }

    public void addXp(int xp) {
        int xpAtual = this.getXpAtual();
        int xpNecessaria = this.getXpNecessaria();
        int xpDepois = xpAtual + xp;
        
        if(xpDepois < xpNecessaria) {
            this.xpAtual = xpDepois;
        } else {
            this.xpAtual = xpDepois - xpNecessaria;
            
            this.levelUp();
            
        }
        
        this.xpTotal = this.xpTotal + xp;
    }

    public int getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(int xpTotal) {
        this.xpTotal = xpTotal;
    }
    
    public void levelUp() {
        this.level++;
        this.xpNecessaria = XpTable.XPTABLE[this.level+1];
        this.pontosStat++;
        this.pontosStatTotal++;
        this.setVidaMax(0);
        this.curarVida(this.getVidaMax());
        this.setManaMax(0);
        this.setManaAtual(this.getManaMax());
        
        Stats.level = this.getLevel();
        JOptionPane.showMessageDialog(new JOptionPane(), "<html>"+this.getNome() +" upou para o nivel <font color=\"red\">"+ this.getLevel() +"</font>.</html>", "Level up", JOptionPane.INFORMATION_MESSAGE);                
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        int level1 = 0;
        int xpTotal = this.xpTotal;
        for(int i = 0; i < XpTable.XPTABLE.length; i++) {
            if(xpTotal >= XpTable.XPTABLE[i]) {
                level1 = i;
                xpTotal -= XpTable.XPTABLE[i];
            }
        }
        
        this.level = level1;
        
        Stats.level = this.getLevel();
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getVidaMax() {
        return vidaMax;
    }
    
    @JsonSetter("vidaMax")
    public void setVidaMax(int v) {
        this.vidaMax = VIDAMAXPADRAO + (VIDALEVELRATIO*level) + (VIDAVITRATIO*vitalidade);
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    
    public Inventario getInventario() {
        return inventario;
    }
    
    
    @JsonSetter("inventario")
    public void JsonSetInventario(String[] itensId) throws IOException {
        ArrayList<Item> itens = new ArrayList<>();
        ArrayList<Integer> itensQtd = new ArrayList<>();
        
        for(int i = 0; i < Inventario.TAMANHOINVENTARIO; i++) {
            try {
                String[] str = itensId[i].split(":");
                String itemId = str[0]; 
                int itemQtd = Integer.parseInt(str[1]); 
                Item item = ITENSJOGO.get(itemId);
                if(item != null) {
                    if(itens.contains(ITENSJOGO.get(itemId))) {
                        int index = itens.indexOf(ITENSJOGO.get(itemId));
                        itensQtd.add(index, itensQtd.get(index)+itemQtd);
                    } else {
                        itens.add(ITENSJOGO.get(itemId));
                        itensQtd.add(itemQtd);
                    }
                }
            } catch (NullPointerException ex) {
                 
            } catch (ArrayIndexOutOfBoundsException ex) {
                itens.add(null);
                itensQtd.add(0);
            }
        }
        
        Item[] arrayItens = itens.toArray(new Item[itens.size()]);
        Integer[] arrayQtds = itensQtd.toArray(new Integer[itensQtd.size()]);
        
        this.inventario = new Inventario(arrayItens, arrayQtds);
    }
    
    @JsonGetter("inventario")
    public String[] JsonGetInventario() throws IOException {
        String[] itens = new String[Inventario.TAMANHOINVENTARIO];
        
        for(int i = 0; i < inventario.size(); i++) {
            Item item = inventario.getItem(i);
            int qtd = inventario.getQtd(i);
            try {
                String str = item.getId() +":"+ qtd;

                itens[i] = str;
            } catch(NullPointerException ex) {
                itens[i] = null;
            }
        }
        
        return itens;
    }
    
    public Integer getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
        
        Stats.forca = getForca();
    }

    public Integer getSorte() {
        return sorte;
        
    }

    public void setSorte(int sorte) {
        this.sorte = sorte;
    }

    public Integer getAgilidade() {
        return agilidade;
    }

    public void setAgilidade(int agilidade) {
        this.agilidade = agilidade;
    }

    public Integer getVitalidade() {
        return vitalidade;
    }

    public void setVitalidade(int vitalidade) {
        this.vitalidade = vitalidade;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public double getChanceCrit() {
        return chanceCrit;
    }

    public void setChanceCrit(double n) {
        this.chanceCrit = (double) this.getSorte() * CRITSORTERATIO;
    }
    
    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        int defesaElmo = 0;
        int defesaArmadura = 0;
        int defesaEscudo = 0;
        int defesaCalcas = 0;
        int defesaBotas = 0;
        
        try { defesaElmo = this.equipamento.get(EquipSlots.ELMO).getDefesa(); } catch (NullPointerException ex) { defesaElmo = 0; }
        try { defesaArmadura = this.equipamento.get(EquipSlots.ARMADURA).getDefesa(); } catch (NullPointerException ex) { defesaArmadura = 0; }
        try { defesaEscudo = this.equipamento.get(EquipSlots.ESCUDO).getDefesa(); } catch (NullPointerException ex) { defesaEscudo = 0; }
        try { defesaCalcas = this.equipamento.get(EquipSlots.CALCAS).getDefesa(); } catch (NullPointerException ex) { defesaCalcas = 0; }
        try { defesaBotas = this.equipamento.get(EquipSlots.BOTAS).getDefesa(); } catch (NullPointerException ex) { defesaBotas = 0; }
        
        this.defesa = defesaElmo + defesaArmadura + defesaEscudo + defesaCalcas + defesaBotas;
        
        Stats.defesa = this.getDefesa();
    }
    
    public Integer getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        try {
            this.ataque = (this.equipamento.get(EquipSlots.ARMA).getAtaque() + this.getForca());
        } catch (NullPointerException ex) {
            this.ataque = ATAQUESEMARMA+this.getForca();
        }
        
        Stats.ataque = this.getAtaque();
    }
    
    public Integer getDanoMagico() {
        return danoMagico;
    }

    public void setDanoMagico(int danoMagico) {
        try {
            this.danoMagico = (this.equipamento.get(EquipSlots.ARMA).getAtaqueMagico() + this.getInteligencia());
        } catch (NullPointerException ex) {
            this.danoMagico = ATAQUESEMARMA+this.getInteligencia();
        }
        
        Stats.danoMagico = this.getDanoMagico();
    }

    public double getChanceEvasao() {
        return chanceEvasao;
    }

    public void setChanceEvasao(double i) {
        this.chanceEvasao = (double) (this.agilidade / 2) / EVASAOAGIRATIO;
    }
    
    @Override
    public String toString() {
        return "Nome: "+ this.getNome() +
               "\nLevel: "+ this.getLevel() +
               "\nXpAtual: "+ this.getXpAtual() +"/"+ this.getXpNecessaria() +
               "\nVida: "+ this.getVidaAtual() +"/"+ this.getVidaMax() +
               "\nForca: "+ this.getForca() +
               "\nSorte: "+ this.getSorte() +
               "\nAgilidade: "+ this.getAgilidade() +
               "\nVitalidade: "+ this.getVitalidade() +
               "\nAtaque: "+ this.getAtaque() +
               "\nDano Mágico: "+ this.getDanoMagico() +
               "\nDefesa: "+ this.getDefesa() +
               "\nChance Critica: "+ this.getChanceCrit() +
               "\nChance de Evasão: "+ this.getChanceEvasao() +
               "\n\n----- Equipamentos -----\n\n"+ this.equipamento.get(EquipSlots.ELMO) +
               "\n\n"+ this.equipamento.get(EquipSlots.ARMA) +
               "\n\n"+ this.equipamento.get(EquipSlots.ARMADURA) +
               "\n\n"+ this.equipamento.get(EquipSlots.ESCUDO) +
               "\n\n"+ this.equipamento.get(EquipSlots.CALCAS) +
               "\n\n"+ this.equipamento.get(EquipSlots.BOTAS);
    }

    public void setArmazem(Armazem armazem) {
        this.armazem = armazem;
    }
    
    public Armazem getArmazem() {
        return armazem;
    }
    
    @JsonSetter("armazem")
    public void JsonSetArmazem(String[] itensId) throws IOException {
        ArrayList<Item> itens = new ArrayList<>();
        ArrayList<Integer> itensQtd = new ArrayList<>();
        
        for(int i = 0; i < Armazem.TAMANHOARMAZEM; i++) {
            try {
                String[] str = itensId[i].split(":");
                String itemId = str[0]; 
                int itemQtd = Integer.parseInt(str[1]); 
                Item item = ITENSJOGO.get(itemId);
                if(item != null) {
                    itens.add(ITENSJOGO.get(itemId));
                    itensQtd.add(itemQtd);
                }
            } catch (NullPointerException ex) {

            } catch (ArrayIndexOutOfBoundsException ex) {
                itens.add(null);
                itensQtd.add(0);
            }
        }
        
        Item[] arrayItens = itens.toArray(new Item[itens.size()]);
        Integer[] arrayQtds = itensQtd.toArray(new Integer[itensQtd.size()]);
        
        this.armazem = new Armazem(arrayItens, arrayQtds);
    }
    
    @JsonGetter("armazem")
    public String[] JsonGetArmazem() throws IOException {
        String[] itens = new String[Armazem.TAMANHOARMAZEM];
        
        for(int i = 0; i < armazem.size(); i++) {
            Item item = armazem.getItem(i);
            int qtd = armazem.getQtd(i);
            try {
                String str = item.getId() +":"+ qtd;

                itens[i] = str;
            } catch(NullPointerException ex) {
                itens[i] = null;
            }
        }
        
        return itens;
    }
    
    @JsonGetter("equipamento")
    public HashMap<EquipSlots, String> jsonGetEquipamento() {
        HashMap<EquipSlots, String> equips = new HashMap<>();
        for(EquipSlots slot : equipamento.keySet()) {
            String idItem;
            try {
                idItem = equipamento.get(slot).getId();
            } catch (NullPointerException ex) {
                idItem = null;
            }
            
            equips.put(slot, idItem);
        }
        
        return equips;
    }

    public void addForca(int forca) {
        this.forca += forca;
        Stats.forca += forca;
        
        this.setAtaque(1);
        Stats.ataque = this.getAtaque();
    }

    public void addVitalidade(int vitalidade) {
        this.vitalidade += vitalidade;
        Stats.vitalidade += vitalidade;
        
        this.setVidaMax(1);
    }

    public void addAgilidade(int agilidade) {
        this.agilidade += agilidade;
        Stats.agilidade += agilidade;
        
        this.setChanceEvasao(1);
    }

    public void addSorte(int sorte) {
        this.sorte += sorte;
        Stats.sorte += sorte;
        
        this.setChanceCrit(1);
    }
    
    public void addInteligencia(int inteligencia) {
        this.inteligencia += inteligencia;
        Stats.inteligencia += inteligencia;
        
        this.setDanoMagico(1);
        Stats.danoMagico = this.getDanoMagico();
    }

    public DanoFisico causarDanoFisico(Monstro monstro) {
        DanoFisico dano = new DanoFisico();
        
        double dado = ThreadLocalRandom.current().nextDouble();
        boolean crit = (dado < this.getChanceCrit());
        dano.setCrit(crit);
        int danoTomado = (crit)? (this.getAtaque() - monstro.getDefesa())*2 : this.getAtaque() - monstro.getDefesa();
        if (danoTomado < 0) danoTomado = 0;
        
        if(monstro.getVidaAtual() - danoTomado > 0) {
            monstro.setVidaAtual(monstro.getVidaAtual() - danoTomado);
            
            dano.setDano(danoTomado);
            
           return dano;
        } else {
            int vida = monstro.getVidaAtual();
            
            monstro.setVidaAtual(0);
            dano.setDano(vida);
            
            return dano;
        }
    }
    
    public void curarVida(int valor) {
        int vidaAtual = this.getVidaAtual();
        int vidaDepois = vidaAtual + valor;
        int vidaMax = this.getVidaMax();
        
        if(vidaDepois <= vidaMax) {
            this.setVidaAtual(vidaDepois);
        } else {
            this.setVidaAtual(vidaMax);
        }
    }

    public boolean usarItem(Item item) {
        try {
            String nomeEfeito = item.getNomeEfeito();
            String valorEfeito = item.getValorEfeito();
        
            switch(nomeEfeito) {
                case "curar" : this.curarVida(Integer.parseInt(valorEfeito)); return true;
                case "aprender": {
                    Magias magias = new Magias();
                    if(this.getLevel() < item.getLevelNecessario()
                       || this.getForca() < item.getForcaNecessaria()
                       || this.getVitalidade() < item.getVitalidadeNecessaria()
                       || this.getAgilidade() < item.getAgilidadeNecessaria()
                       || this.getSorte() < item.getSorteNecessaria()
                       || this.getInteligencia() < item.getInteligenciaNecessaria()) {
                
                        return false;
                    } else {
                        return (this.magias.add(magias.get(valorEfeito)));
                    }
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Personagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public void atualizarPersonagem() {
        this.setAtaque(0);
        this.setDanoMagico(0);
        this.setChanceCrit(0);
        this.setChanceEvasao(0);
        this.setDefesa(0);
        this.setVidaMax(0);
    }

    public boolean equiparItem(Item item) throws IOException {
        if(this.getLevel() < item.getLevelNecessario()
           || this.getForca() < item.getForcaNecessaria()
           || this.getVitalidade() < item.getVitalidadeNecessaria()
           || this.getAgilidade() < item.getAgilidadeNecessaria()
           || this.getSorte() < item.getSorteNecessaria()
           || this.getInteligencia() < item.getInteligenciaNecessaria()) {
            
            return false;
        }
        
        EquipSlots slot = EquipSlots.valueOf(item.getTipo().toUpperCase());
        
        if(this.equipamento.get(slot) == null) {
            this.equipamento.put(slot, item);
            this.inventario.remove(item.getId());
            
            return true;
        } else {
            Item itemEquipado = this.equipamento.get(slot);
            int indexItemInv = this.inventario.getIndex(item.getId());
            
            this.equipamento.put(slot, item);
            this.inventario.remove(item.getId());
            this.inventario.add(itemEquipado, 1, indexItemInv);
            
            return true;
        }
    }
    
    public boolean desequiparItem(EquipSlots slot) throws IOException {
        Item item = equipamento.get(slot);
        
        if(this.inventario.add(item, 1)) {
            equipamento.remove(slot);
            
            return true;
        } else {
            
            return false;
        }
    }

    public InfoMagiaUsada usarMagia(Magia magia, Monstro monstro) {
        InfoMagiaUsada info = null;
        String tipoMagia = magia.getTipo().trim().toLowerCase();
        if(this.getManaAtual() - magia.getCusto() < 0) {
            info = new InfoMagiaDano();
            
            info.setSucesso(false);
            return info;
        }
        this.setManaAtual(this.getManaAtual()-magia.getCusto());
        
        switch(tipoMagia) {
            case "dano" : {
                info = new InfoMagiaDano();
                
                int dano = (int) (magia.getValorEfeito() + this.getDanoMagico()*magia.getValorEfeitoRatio());
                double err = this.getDanoMagico()*magia.getValorEfeitoRatio();
                
                if(monstro.getVidaAtual() - dano >= 0) {
                    monstro.setVidaAtual(monstro.getVidaAtual() - dano);
                    
                    info.setDanoMagia(dano);
                } else {
                    dano = monstro.getVidaAtual();
                    monstro.setVidaAtual(0);
                    
                    info.setDanoMagia(dano);
                }
                break;
            }
            
            case "cura": {
                info = new InfoMagiaCura();
                
                double err = this.getDanoMagico()*magia.getValorEfeitoRatio();
                
                int cura = (int) (magia.getValorEfeito() + this.getDanoMagico()*magia.getValorEfeitoRatio());
                this.curarVida(cura);
                
                info.setCuraMagia(cura);
            }
        }
        
        info.setSucesso(true);
        
        return info;
    }
}
