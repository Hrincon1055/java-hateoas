package com.hr.hateoas.models;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas")
public class CuentaModel extends RepresentationModel<CuentaModel> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 20, nullable = false, unique = true)
  private String numeroDeCuenta;

  private float balance;

  public CuentaModel() {
  }

  public CuentaModel(Integer id, String numeroDeCuenta, float balance) {
    this.id = id;
    this.numeroDeCuenta = numeroDeCuenta;
    this.balance = balance;
  }

  public CuentaModel(Integer id, String numeroDeCuenta) {
    this.id = id;
    this.numeroDeCuenta = numeroDeCuenta;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNumeroDeCuenta() {
    return this.numeroDeCuenta;
  }

  public void setNumeroDeCuenta(String numeroDeCuenta) {
    this.numeroDeCuenta = numeroDeCuenta;
  }

  public float getBalance() {
    return this.balance;
  }

  public void setBalance(float balance) {
    this.balance = balance;
  }
}
