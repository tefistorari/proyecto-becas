package com.UTN_BECAS.Sistema_Becas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriasACursar extends JpaRepository<MateriasACursar, Long> {
}
