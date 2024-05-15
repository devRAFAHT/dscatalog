package com.andradscorporation.dscatalog.repositories;

import com.andradscorporation.dscatalog.entities.Role;
import com.andradscorporation.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
