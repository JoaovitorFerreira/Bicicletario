package Bicicleta;

import static org.junit.Assert.*;
import org.junit.Test;

import grupo2equipamento.Bicicleta.Bicicleta;

public class BicicletaTeste {
  @Test
  public void testCriacaoBicicleta1() {

    int testNumero = 200;
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    assertEquals(testNumero, a.getNumero());
  }

  @Test(expected = AssertionError.class)
  public void testCriacaoBicicleta2() {

    Bicicleta b = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    Bicicleta testeObj = new Bicicleta();
    assertEquals(testeObj, b);
  }

  @Test
  public void testCriacaoBicicleta3() {

    Bicicleta c = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    Bicicleta testeObj = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    assertEquals(testeObj.getLocalizacao(), c.getLocalizacao());
  }

  @Test
  public void testCriacaoBicicleta4() {

    Bicicleta d = new Bicicleta();
    Bicicleta testeObj = new Bicicleta();
    assertEquals(testeObj.getClass(), d.getClass());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicleta5Local() {

    String testeObj = null;
    Bicicleta b = new Bicicleta(null, "Caloi", "AZ10", "2010", 200);

    assertEquals(testeObj, b.getLocalizacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicleta5Marca() {

    String testeObj = null;
    Bicicleta b = new Bicicleta("L32mL22", null, "AZ10", "2010", 200);

    assertEquals(testeObj, b.getMarca());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicleta5Modelo() {

    String testeObj = null;
    Bicicleta b = new Bicicleta("L32mL22", "Caloi", null, "2010", 200);

    assertEquals(testeObj, b.getModelo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicleta5Ano() {

    String testeObj = null;
    Bicicleta b = new Bicicleta("L32mL22", "Caloi", "AZ10", null, 200);

    assertEquals(testeObj, b.getAno());
  }

  @Test
  public void testgetId() {

    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertNotNull(a.getId());
  }

  @Test
  public void testgetLocalizacao() {

    String testLocalizacao = "L32,L12";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testLocalizacao, a.getLocalizacao());
  }

  @Test
  public void testsetLocalizacao() {

    String testLocalizacao = "L12,L22";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setLocalizacao(testLocalizacao);

    assertEquals(testLocalizacao, a.getLocalizacao());
  }

  @Test
  public void testgetMarca() {

    String testMarca = "Caloi";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testMarca, a.getMarca());
  }

  @Test
  public void testsetMarca() {

    String testMarca = "Scoot";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setMarca(testMarca);

    assertEquals(testMarca, a.getMarca());
  }

  @Test
  public void testgetModelo() {

    String testModelo = "AZ10";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testModelo, a.getModelo());
  }

  @Test
  public void testsetModelo() {

    String testModelo = "AS20";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setModelo(testModelo);

    assertEquals(testModelo, a.getModelo());
  }

  @Test
  public void testgetAno() {

    String testAno = "2010";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testAno, a.getAno());
  }

  @Test
  public void testsetAno() {

    String testAno = "2011";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setAno(testAno);

    assertEquals(testAno, a.getAno());
  }

  @Test
  public void testgetNumero() {

    int testNumero = 200;
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testNumero, a.getNumero());
  }

  @Test
  public void testsetNumero() {

    int testNumero = 220;
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setNumero(testNumero);

    assertEquals(testNumero, a.getNumero());
  }

  @Test
  public void testgetStatus() {

    String testStatus = "NOVA";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);

    assertEquals(testStatus, a.getStatus());
  }

  @Test
  public void testsetStatus() {

    String testStatus = "EM REPARO";
    Bicicleta a = new Bicicleta("L32,L12", "Caloi", "AZ10", "2010", 200);
    a.setStatus(testStatus);

    assertEquals(testStatus, a.getStatus());
  }

}
