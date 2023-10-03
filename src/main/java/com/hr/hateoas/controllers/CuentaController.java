package com.hr.hateoas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hateoas.models.BalanceModel;
import com.hr.hateoas.models.CuentaModel;
import com.hr.hateoas.services.CuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
  @Autowired
  private CuentaService cuentaService;

  @GetMapping
  public ResponseEntity<List<CuentaModel>> listAll() {
    List<CuentaModel> cuentas = cuentaService.listAll();
    if (cuentas.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    for (CuentaModel cuenta : cuentas) {
      cuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(cuenta.getId()))
          .withSelfRel());
      cuenta.add(linkTo(methodOn(CuentaController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
    }
    CollectionModel<CuentaModel> modelo = CollectionModel.of(cuentas);
    modelo.add(linkTo(methodOn(CuentaController.class).listAll()).withSelfRel());
    return new ResponseEntity<List<CuentaModel>>(cuentas, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CuentaModel> getCuenta(@PathVariable("id") Integer id) {
    try {
      CuentaModel cuenta = cuentaService.get(id);
      cuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(cuenta.getId()))
          .withSelfRel());
      cuenta.add(linkTo(methodOn(CuentaController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
      return new ResponseEntity<CuentaModel>(cuenta, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<CuentaModel> save(@RequestBody CuentaModel cuenta) {
    CuentaModel newCuenta = cuentaService.save(cuenta);
    newCuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(newCuenta.getId())).withSelfRel());
    newCuenta.add(linkTo(methodOn(CuentaController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
    return ResponseEntity.created(linkTo(methodOn(CuentaController.class).getCuenta(newCuenta.getId())).toUri())
        .body(newCuenta);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CuentaModel> edit(@RequestBody CuentaModel cuenta, @PathVariable("id") Integer id) {
    try {
      CuentaModel newCuenta = this.cuentaService.get(id);
      if (newCuenta != null) {
        newCuenta.setNumeroDeCuenta(cuenta.getNumeroDeCuenta());
        newCuenta.setBalance(cuenta.getBalance());
        this.cuentaService.save(newCuenta);
        newCuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(newCuenta.getId())).withSelfRel());
        // newCuenta.add(linkTo(methodOn(CuentaController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
        return new ResponseEntity<>(newCuenta, HttpStatus.OK);
      }
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
    try {
      this.cuentaService.delete(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/{id}/deposito")
  public ResponseEntity<CuentaModel> depositarDinero(@PathVariable("id") Integer id,
      @RequestBody BalanceModel balance) {
    CuentaModel newCuenta = this.cuentaService.depositar(balance.getBalance(), id);
    newCuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(newCuenta.getId())).withSelfRel());
    newCuenta
        .add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
    return new ResponseEntity<CuentaModel>(newCuenta, HttpStatus.OK);
  }

  @PatchMapping("/{id}/retiro")
  public ResponseEntity<CuentaModel> retirarDinero(@PathVariable("id") Integer id,
      @RequestBody BalanceModel balance) {
    CuentaModel newCuenta = this.cuentaService.retirar(balance.getBalance(), id);
    newCuenta.add(linkTo(methodOn(CuentaController.class).getCuenta(newCuenta.getId())).withSelfRel());
    newCuenta
        .add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
    newCuenta
        .add(linkTo(methodOn(CuentaController.class).retirarDinero(newCuenta.getId(), null)).withRel("retiros"));
    return new ResponseEntity<CuentaModel>(newCuenta, HttpStatus.OK);
  }
}
