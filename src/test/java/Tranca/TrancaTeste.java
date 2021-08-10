package Tranca;

import static org.junit.Assert.*;
import org.junit.Test;

import grupo2equipamento.Tranca.Tranca;

public class TrancaTeste {
  @Test
  public void testCriacaoTranca1() {

    int testNumero = 12;

    Tranca a = new Tranca(12, "L32,L12", "2010", "A2000");
    assertEquals(testNumero, a.getNumero());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoTranca2Local() {

    String testeObj = null;
    Tranca b = new Tranca(12, null, "2010", "A2000");

    assertEquals(testeObj, b.getLocalizacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoTranca2Ano() {

    String testeObj = null;
    Tranca b = new Tranca(12, "L32,L12", null, "A2000");

    assertEquals(testeObj, b.getAnoFabricacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoTranca2Modelo() {

    String testeObj = null;
    Tranca b = new Tranca(12, "L32,L12", "2010", null);

    assertEquals(testeObj, b.getModelo());
  }

  @Test
  public void testCriacaoTranca3() {

    Tranca c = new Tranca();
    Tranca testeObjectTranca = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals(testeObjectTranca.getClass(), c.getClass());
  }

  @Test
  public void testCriacaoTranca4() {

    Tranca d = new Tranca(15, "L322,L12", "2010", "A2000");
    Tranca testeObjectTranca = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals(testeObjectTranca.getClass(), d.getClass());
  }

  @Test
  public void testgetIdBicicleta() {

    Tranca e = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals(null, e.getIdBicicleta());
  }

  @Test
  public void testsetIdBicicleta() {

    Tranca f = new Tranca(12, "L32,L12", "2010", "A2000");
    f.setIdBicicleta("idTeste");

    assertEquals("idTeste", f.getIdBicicleta());
  }

  @Test
  public void testsetIdBicicleta2() {

    Tranca g = new Tranca(12, "L32,L12", "2010", "A2000");
    g.setIdBicicleta(null);

    assertEquals(null, g.getIdBicicleta());
  }

  @Test
  public void testgetNumero() {

    Tranca h = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals(12, h.getNumero());
  }

  @Test
  public void testsetNumero() {

    Tranca i = new Tranca(12, "L32,L12", "2010", "A2000");
    i.setNumero(14);

    assertEquals(14, i.getNumero());
  }

  @Test(expected = AssertionError.class)
  public void testsetNumero2() {

    Tranca j = new Tranca(12, "L32,L12", "2010", "A2000");
    j.setNumero(0);

    assertEquals(14, j.getNumero());
  }

  @Test
  public void testgetLocalizacao() {

    Tranca k = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals("L32,L12", k.getLocalizacao());
  }

  @Test
  public void testsetLocalizacao() {

    Tranca l = new Tranca(12, "L32,L12", "2010", "A2000");
    l.setLocalizacao("L12,L22");

    assertEquals("L12,L22", l.getLocalizacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLocalizacao2() {

    String testObj = null;
    Tranca m = new Tranca(12, "L32,L12", "2010", "A2000");
    m.setLocalizacao(testObj);
    assertNull(testObj, m.getLocalizacao());
  }

  @Test
  public void testgetAnoFabricacao() {

    Tranca n = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals("2010", n.getAnoFabricacao());
  }

  @Test
  public void testsetAnoFabricacao() {

    Tranca o = new Tranca(12, "L32,L12", "2010", "A2000");
    o.setAnoFabricacao("2020");

    assertEquals("2020", o.getAnoFabricacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testsetAnoFabricacao2() {

    String testObj = null;
    Tranca p = new Tranca(12, "L32,L12", "2010", "A2000");
    p.setAnoFabricacao(testObj);
    assertNull(testObj, p.getAnoFabricacao());
  }

  @Test
  public void testgetModelo() {

    Tranca q = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals("A2000", q.getModelo());
  }

  @Test
  public void testsetModelo() {

    Tranca r = new Tranca(12, "L32,L12", "2010", "A2000");
    r.setModelo("AZ21");

    assertEquals("AZ21", r.getModelo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testsetModelo2() {

    String testObj = null;
    Tranca s = new Tranca(12, "L32,L12", "2010", "A2000");
    s.setModelo(testObj);
    assertNull(testObj, s.getModelo());
  }

  @Test
  public void testgetStatus() {

    Tranca t = new Tranca(12, "L32,L12", "2010", "A2000");

    assertEquals("NOVA", t.getStatus());
  }

  @Test
  public void testsetStatus() {

    Tranca u = new Tranca(12, "L32,L12", "2010", "A2000");
    u.setStatus("OCUPADA");

    assertEquals("OCUPADA", u.getStatus());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testsetStatus2() {

    String testObj = null;
    Tranca v = new Tranca(12, "L32,L12", "2010", "A2000");
    v.setStatus(testObj);
    assertNull(testObj, v.getStatus());
  }

}
