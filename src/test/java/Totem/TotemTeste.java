package Totem;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import grupo2equipamento.Totem.Totem;

public class TotemTeste {

  @Test
  public void testCriacaoTotem1() {

    String testObj = "-23.9219335,-43.237562,17z";
    Totem a = new Totem("-23.9219335,-43.237562,17z");
    assertEquals(testObj, a.getLocalizacao());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoTotem2() {

    String testObj = null;
    Totem b = new Totem(null);
    assertEquals(testObj, b.getLocalizacao());
  }

  @Test(expected = AssertionError.class)
  public void testCriacaoTotem3() {

    String testObj = "-23.9219335,-43.237562,17z";
    Totem c = new Totem(testObj);
    Totem testeObjectTotem = new Totem();

    assertEquals(testeObjectTotem, c);
  }

  @Test
  public void testCriacaoTotem4() {

    String testObj = "-23.9219335,-43.237562,17z";
    Totem d = new Totem(testObj);
    Totem testeObjectTotem = new Totem(testObj);

    assertEquals(testeObjectTotem.getLocalizacao(), d.getLocalizacao());
  }

  @Test
  public void testCriacaoTotem5() {

    Totem e = new Totem();
    Totem testeObjectTotem = new Totem();

    assertEquals(testeObjectTotem.getClass(), e.getClass());
  }

  @Test
  public void testsetTrancas() {

    Totem f = new Totem("teste");
    ArrayList<String> arrayTrancas = new ArrayList<String>();
    arrayTrancas.add("1234");
    arrayTrancas.add("1334");
    f.setTrancas(arrayTrancas);
    assertEquals(arrayTrancas, f.getTrancas());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetLocalizacao2() {

    String testObj = "-23.9219335,-43.237562,17z";
    Totem g = new Totem(testObj);
    g.setLocalizacao(null);
    assertNull(null, g.getLocalizacao());
  }

  @Test
  public void testgetTrancas() {

    Totem h = new Totem("teste");
    int todasTrancas = h.getTrancas().size();
    assertTrue(todasTrancas == 0);
  }

}
