package ua.kpi.tef.essd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.backend.entity.Role;
import ua.kpi.tef.essd.backend.entity.User;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
