package ua.kpi.tef.essd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.backend.entity.ClothesSet;

@Repository
public interface ClothesSetRepository extends JpaRepository<ClothesSet, Integer> {

}
