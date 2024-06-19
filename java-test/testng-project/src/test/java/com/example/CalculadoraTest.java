package com.example;

import org.testng.annotations.Test;
import org.testng.Assert;

public class CalculadoraTest {
  
  @Test
  public void testSomar() {
    Calculadora calculadora = new Calculadora();
    int resultado = calculadora.somar(2, 3);
    Assert.assertEquals(resultado, 5, "A soma está incorreta!");
  }
  
  @Test
  public void testSubtrair() {
    Calculadora calculadora = new Calculadora();
    int resultado = calculadora.subtrair(5, 3);
    Assert.assertEquals(resultado, 2, "A subtração está incorreta!");
  }
}