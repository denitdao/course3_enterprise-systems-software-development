package ua.kpi.tef.essd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
