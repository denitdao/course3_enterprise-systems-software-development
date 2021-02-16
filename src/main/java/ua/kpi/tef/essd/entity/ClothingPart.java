package ua.kpi.tef.essd.entity;

import javax.persistence.*;

@Entity
@Table(name = "clothing_part")
public class ClothingPart {

    @EmbeddedId
    private ClothingPartKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clothingId")
    @JoinColumn(name = "clothing_id")
    private Clothing clothing;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("partId")
    @JoinColumn(name = "part_id")
    private Part part;

    private Integer amount;

    public ClothingPart() { }

    public ClothingPart(Clothing clothing, Part part, Integer amount) {
        this.id = new ClothingPartKey(clothing.getId(), part.getId());
        this.clothing = clothing;
        this.part = part;
        this.amount = amount;
    }

    public ClothingPart(Clothing clothing, Part part) {
        this.id = new ClothingPartKey(clothing.getId(), part.getId());
        this.clothing = clothing;
        this.part = part;
        this.amount = 1;
    }

    public ClothingPartKey getId() {
        return id;
    }

    public void setId(ClothingPartKey id) {
        this.id = id;
    }

    public Clothing getClothing() {
        return clothing;
    }

    public void setClothing(Clothing clothing) {
        this.clothing = clothing;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClothingPart{" +
                "id=" + id +
                ", clothing=" + clothing +
                ", part=" + part +
                ", amount=" + amount +
                '}';
    }
}
