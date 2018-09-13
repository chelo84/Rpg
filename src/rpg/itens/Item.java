package rpg.itens;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
@JsonIgnoreProperties(ignoreUnknown = true,
                      value = {"@type", "usavel", "nome", "tipo", "efeito", "descricao", "qtdMax", "img", "ataque", "defesa"
                               , "magia"},
                      allowSetters = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, visible = false, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ArmaFisica.class, name = "ArmaFisica"),
    
    @JsonSubTypes.Type(value = ArmaMagica.class, name = "ArmaMagica"),
    
    @JsonSubTypes.Type(value = Armadura.class, name = "Armadura"),
    
    @JsonSubTypes.Type(value = ItemUsavel.class, name = "Usavel"),
    
    @JsonSubTypes.Type(value = ItemMagia.class, name = "Magia"),
    
    @JsonSubTypes.Type(value = Outros.class, name = "Outros") }

)

public abstract class Item {
    private String id;
    private String nome;
    private String tipo;
    private String descricao;
    private int qtdMax;
    private String img;
    private boolean usavel;
    private int precoVenda;
    private int precoCompra;
    
    public abstract void setAtaque(int ataque);
    public abstract int getAtaque();
    public abstract void setAtaqueMagico(int ataque);
    public abstract int getAtaqueMagico();
    public abstract void setDefesa(int defesa);
    public abstract int getDefesa();
    public abstract void setEfeito(String efeito);
    public abstract String getNomeEfeito();
    public abstract String getValorEfeito();
    public abstract void setLevelNecessario(int levelNecessario);
    public abstract int getLevelNecessario();
    public abstract void setForcaNecessaria(int forcaNecessaria);
    public abstract int getForcaNecessaria();
    public abstract void setVitalidadeNecessaria(int vitalidadeNecessaria);
    public abstract int getVitalidadeNecessaria();
    public abstract void setSorteNecessaria(int sorteNecessaria);
    public abstract int getSorteNecessaria();
    public abstract void setAgilidadeNecessaria(int agilidadeNecessaria);
    public abstract int getAgilidadeNecessaria();
    public abstract void setInteligenciaNecessaria(int inteligenciaNecessaria);
    public abstract int getInteligenciaNecessaria();
    
    @JsonSetter("precoVenda")
    public void setPrecoVenda(int precoVenda) {
        this.precoVenda = precoVenda;
    }
    
    @JsonGetter("precoVenda")
    public int getPrecoVenda() {
        return this.precoVenda;
    }
    
    @JsonSetter("precoCompra")
    public int getPrecoCompra() {
        return precoCompra;
    }
    
    @JsonGetter("precoCompra")
    public void setPrecoCompra(int precoCompra) {
        this.precoCompra = precoCompra;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        /*ImageIcon icone = new ImageIcon(img);
        Image image = icone.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);*/
        
        this.img = img;
    }

    public int getQtdMax() {
        return qtdMax;
    }

    public void setQtdMax(int qtdMax) {
        this.qtdMax = qtdMax;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isUsavel() {
        return usavel;
    }

    public void setUsavel(boolean usavel) {
        if(this.getTipo().trim().toLowerCase().equals("usavel")) this.usavel = true;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        String str = "<html><b>Nome: "+ this.getNome()+
               "<br>Tipo: "+ this.getTipo()+
               "<br>Descrição: "+ this.getDescricao()+
               "</b></html>";
        return str;
    }

    public boolean isEquipavel() {
        return this.getClass() == ArmaFisica.class ||
               this.getClass() == ArmaMagica.class ||
               this.getClass() == Armadura.class;
    }
    
    @Override
    public boolean equals(Object other) {
        return this.hashCode() == other.hashCode();
    }    
    
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
