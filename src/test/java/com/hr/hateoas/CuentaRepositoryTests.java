package com.hr.hateoas;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hr.hateoas.models.CuentaModel;
import com.hr.hateoas.repositories.CuentaRepository;

@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTests {
  @Autowired
  private CuentaRepository cuentaRepository;

  @Test
  void testAgregarCuenta() {
    CuentaModel cuenta = new CuentaModel(1, "123455");
    CuentaModel cuentaSave = cuentaRepository.save(cuenta);
    Assertions.assertThat(cuentaSave).isNotNull();
    Assertions.assertThat(cuentaSave.getId()).isGreaterThan(0);
  }
}
