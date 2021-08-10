package TrancaTotem;

import static org.junit.Assert.*;
import org.junit.Test;

import grupo2equipamento.TrancaTotem.TrancaTotem;

public class TrancaTotemTeste {
  @Test
  public void testCriacaoTrancaTotem1() {

    String testId = "123";
    TrancaTotem a = new TrancaTotem("123", "345", "testefunc");
    assertEquals(testId, a.getIdTotem());
  }

  @Test(expected = AssertionError.class)
  public void testCriacaoTrancaTotem2() {

    TrancaTotem b = new TrancaTotem("123", "345", "testefunc");
    TrancaTotem testeObj = new TrancaTotem();
    assertEquals(testeObj, b);
  }

  @Test
  public void testCriacaoTrancaTotem3() {

    TrancaTotem c = new TrancaTotem("123", "345", "testefunc");
    TrancaTotem testeObj = new TrancaTotem("123", "345", "testefunc");
    assertEquals(testeObj.getIdTotem(), c.getIdTotem());
  }

  @Test
  public void testCriacaoTrancaTotem4() {

    TrancaTotem d = new TrancaTotem();
    TrancaTotem testeObj = new TrancaTotem();
    assertEquals(testeObj.getClass(), d.getClass());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCriacaoTrancaTotem5Totem() {

    String testId = null;
    TrancaTotem e = new TrancaTotem(null, "345", "testefunc");
    assertEquals(testId, e.getIdTotem());
  }


  @Test
  public void testgetIdTotem() {

    String testeObj = "123";
    TrancaTotem f = new TrancaTotem("123", "345", "testefunc");
    assertEquals(testeObj, f.getIdTotem());
  }

  @Test
  public void testsetIdTotem() {

    String testeObj = "111";
    TrancaTotem g = new TrancaTotem("123", "345", "testefunc");
    g.setIdTotem(testeObj);

    assertEquals(testeObj, g.getIdTotem());
  }

  @Test
  public void testgetIdTTranca() {

    String testeObj = "345";
    TrancaTotem f = new TrancaTotem("123", "345", "testefunc");
    assertEquals(testeObj, f.getIdTranca());
  }

  @Test
  public void testsetIdTTranca() {

    String testeObj = "111";
    TrancaTotem g = new TrancaTotem("123", "345", "testefunc");
    g.setIdTranca(testeObj);

    assertEquals(testeObj, g.getIdTranca());
  }
}
