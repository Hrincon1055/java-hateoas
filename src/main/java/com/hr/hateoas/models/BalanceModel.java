package com.hr.hateoas.models;

public class BalanceModel {
  private float balance;

  public BalanceModel() {
  }

  public BalanceModel(float balance) {
    this.balance = balance;
  }

  public float getBalance() {
    return this.balance;
  }

  public void setBalance(float balance) {
    this.balance = balance;
  }

}
