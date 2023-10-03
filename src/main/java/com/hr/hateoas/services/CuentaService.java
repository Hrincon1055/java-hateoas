package com.hr.hateoas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hr.hateoas.exception.CuentaNotFoundException;
import com.hr.hateoas.models.CuentaModel;
import com.hr.hateoas.repositories.CuentaRepository;

@Service
@Transactional
public class CuentaService {
  @Autowired
  private CuentaRepository cuentaRepository;

  public List<CuentaModel> listAll() {
    return cuentaRepository.findAll();
  }

  public CuentaModel get(Integer id) {
    return cuentaRepository.findById(id).get();
  }

  public CuentaModel save(CuentaModel cuenta) {
    return cuentaRepository.save(cuenta);
  }

  public void delete(Integer id) throws CuentaNotFoundException {
    if (!cuentaRepository.existsById(id)) {
      throw new CuentaNotFoundException("Cuenta no encontrada con el ID " + id);
    }
    cuentaRepository.deleteById(id);
  }

  public CuentaModel depositar(float balance, Integer id) {
    this.cuentaRepository.actualizarBalance(balance, id);
    return this.cuentaRepository.findById(id).get();
  }

  public CuentaModel retirar(float balance, Integer id) {
    this.cuentaRepository.actualizarBalance(-balance, id);
    return this.cuentaRepository.findById(id).get();
  }
}
