package ua.kpi.tef.essd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClothingPartKey implements Serializable {

    @Column(name = "clothing_id")
    private Integer clothingId;

    @Column(name = "part_id")
    private Integer partId;

    public ClothingPartKey(Integer clothingId, Integer partId) {
        this.clothingId = clothingId;
        this.partId = partId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothingPartKey that = (ClothingPartKey) o;
        return clothingId.equals(that.clothingId) && partId.equals(that.partId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clothingId, partId);
    }
}
