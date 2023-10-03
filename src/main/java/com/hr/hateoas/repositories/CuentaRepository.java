package com.hr.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hr.hateoas.models.CuentaModel;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaModel, Integer> {
  @Query("UPDATE CuentaModel c SET c.balance=c.balance +?1 WHERE c.id=?2")
  @Modifying
  void actualizarBalance(float monto, Integer id);
}
