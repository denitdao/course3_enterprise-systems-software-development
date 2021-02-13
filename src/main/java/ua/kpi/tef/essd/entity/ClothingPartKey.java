package ua.kpi.tef.essd.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClothingPartKey implements Serializable {

    @Column(name = "clothing_id")
    private Integer clothingId;

    @Column(name = "part_id")
    private Integer partId;

    public ClothingPartKey() { }

    public ClothingPartKey(Integer clothingId, Integer partId) {
        this.clothingId = clothingId;
        this.partId = partId;
    }

    public Integer getClothingId() {
        return clothingId;
    }

    public void setClothingId(Integer clothingId) {
        this.clothingId = clothingId;
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {
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
