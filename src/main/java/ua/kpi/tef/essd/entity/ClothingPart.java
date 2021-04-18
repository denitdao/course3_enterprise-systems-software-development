package ua.kpi.tef.essd.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clothing_part")
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Part part;

    private Integer amount;

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

    @Override
    public String toString() {
        return "ClothingPart{" +
                "id=" + id +
                ", clothing=" + clothing.getName() +
                ", part=" + part.getName() +
                ", amount=" + amount +
                '}';
    }
}
