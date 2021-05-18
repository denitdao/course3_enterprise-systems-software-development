package ua.kpi.tef.essd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.backend.entity.Clothing;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Integer> {

    Clothing findClothingByName(String name);

}
