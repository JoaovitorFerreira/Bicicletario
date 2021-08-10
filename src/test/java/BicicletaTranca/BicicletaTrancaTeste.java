package BicicletaTranca;

import static org.junit.Assert.*;
import org.junit.Test;

import grupo2equipamento.BicicletaTranca.BicicletaTranca;

public class BicicletaTrancaTeste {
  @Test
  public void testCriacaoBicicletaTranca1() {

    String testId = "123";
    BicicletaTranca a = new BicicletaTranca("123", "345", "testefunc");
    assertEquals(testId, a.getIdTranca());
  }

  @Test(expected = AssertionError.class)
  public void testCriacaoTrancaTotem2() {

    BicicletaTranca b = new BicicletaTranca("123", "345", "testefunc");
    BicicletaTranca testeObj = new BicicletaTranca();
    assertEquals(testeObj, b);
  }

  @Test
  public void testCriacaoBicicletaTranca3() {

    BicicletaTranca c = new BicicletaTranca("123", "345", "testefunc");
    BicicletaTranca testeObj = new BicicletaTranca("123", "345", "testefunc");
    assertEquals(testeObj.getIdTranca(), c.getIdTranca());
  }

  @Test
  public void testCriacaoBicicletaTranca4() {

    BicicletaTranca d = new BicicletaTranca();
    BicicletaTranca testeObj = new BicicletaTranca();
    assertEquals(testeObj.getClass(), d.getClass());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicletaTranca5Totem() {

    String testId = null;
    BicicletaTranca e = new BicicletaTranca(null, "345", "testefunc");
    assertEquals(testId, e.getIdTranca());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicletaTranca5Tranca() {

    String testId = null;
    BicicletaTranca e = new BicicletaTranca("123", null, "testefunc");
    assertEquals(testId, e.getIdBicicleta());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoBicicletaTranca6Tranca() {

    String testId = null;
    BicicletaTranca e = new BicicletaTranca("123", "345", null);
    assertEquals(testId, e.getIdBicicleta());
  }

  @Test
  public void testgetIdTranca() {

    String testeObj = "123";
    BicicletaTranca f = new BicicletaTranca("123", "345", "testefunc");
    assertEquals(testeObj, f.getIdTranca());
  }

  @Test
  public void testsetIdTranca() {

    String testeObj = "111";
    BicicletaTranca g = new BicicletaTranca("123", "345", "testefunc");
    g.setIdTranca(testeObj);

    assertEquals(testeObj, g.getIdTranca());
  }

  @Test
  public void testgetIdBicicleta() {

    String testeObj = "345";
    BicicletaTranca f = new BicicletaTranca("123", "345", "testefunc");
    assertEquals(testeObj, f.getIdBicicleta());
  }

  @Test
  public void testsetIdBicicleta() {

    String testeObj = "111";
    BicicletaTranca g = new BicicletaTranca("123", "345", "testefunc");
    g.setIdBicicleta(testeObj);

    assertEquals(testeObj, g.getIdBicicleta());
  }
}
