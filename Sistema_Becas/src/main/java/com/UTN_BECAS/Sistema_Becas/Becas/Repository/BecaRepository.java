package com.UTN_BECAS.Sistema_Becas.Becas.Repository;

import com.UTN_BECAS.Sistema_Becas.Becas.Model.TipoBeca;
import com.UTN_BECAS.Sistema_Becas.Becas.Model.Beca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BecaRepository extends JpaRepository<Beca, Long> {

    List<Beca> findByTipoBeca(TipoBeca tipoBeca);
    List<Beca> findByRequiereIngenieria(boolean requiereIngenieria);
}
