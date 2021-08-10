package Totem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import grupo2equipamento.Totem.Totem;
import grupo2equipamento.Totem.TotemDAO;

public class TotemDAOTeste {

  TotemDAO totemDao;

  @Before
  public void init() {
    totemDao = TotemDAO.instance();
  }

  @After
  public void finalize() {
    totemDao.resetInstance();
  }

  @Test
  public void instanceTest1() {
    totemDao = null;
    totemDao = TotemDAO.instance();
    assertNotNull(totemDao);
  }

  @Test
  public void instanceTest2() {
    assertNotNull(totemDao);
  }

  @Test
  public void getAllTotemDAOTest() {
    ArrayList<Totem> totens = totemDao.findAllTotens();
    assertTrue(totens.size() > 0);
  }

  @Test
  public void getByIdTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);
    String totemId = totemTeste.getId();
    Optional<Totem> totemExists = totemDao.findTotemById(totemId);
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      assertEquals(totemId, totemEncontrado.getId());
    }
  }

  @Test
  public void postTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);
    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      assertEquals("123,f23", totemEncontrado.getLocalizacao());
    }
  }

  @Test
  public void updateTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);
    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      totemDao.updateTotem(totemEncontrado.getId(), "teste");
      assertEquals("teste", totemEncontrado.getLocalizacao());
    }
  }

  @Test
  public void updateNullTotemDAOTest() {
    Totem totemResponse = totemDao.updateTotem("testeId", "123,f23");
    assertNull(totemResponse);
  }

  @Test
  public void deleteTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);
    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      totemDao.deleteTotem(totemEncontrado.getId());
    }
    Optional<Totem> totemFind = totemDao.findTotemByLocalizacao("123,f23");
    assertFalse(totemFind.isPresent());
  }

  @Test
  public void getAllTrancasTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);
    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      totemDao.addTrancaTotem("123", totemEncontrado.getId());
      ArrayList<String> arrayTrancasTotem = totemDao.allTrancasTotem(totemEncontrado.getId());
      assertEquals(1, arrayTrancasTotem.size());
    }
  }

  @Test
  public void getAllNullTrancasTotemDAOTest() {
    ArrayList<String> totemResponse = totemDao.allTrancasTotem("testeId");
    assertNull(totemResponse);
  }

  @Test
  public void addTrancaTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);

    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      ArrayList<String> totemTranca = totemDao.addTrancaTotem("testetranca", totemEncontrado.getId());
      assertEquals(totemTranca, totemEncontrado.getTrancas());
    }
  }

  @Test
  public void addTrancaNullTotemDAOTest() {
    ArrayList<String> totemResponse = totemDao.addTrancaTotem("testeTranca", "testeTotem");
    assertNull(totemResponse);
  }

  @Test
  public void removeTrancaTotemDAOTest() {
    Totem totemTeste = new Totem("123,f23");
    totemDao.postTotem(totemTeste);

    Optional<Totem> totemExists = totemDao.findTotemByLocalizacao("123,f23");
    if (totemExists.isPresent()) {
      Totem totemEncontrado = totemExists.get();
      ArrayList<String> totemTranca = totemDao.removeTrancaTotem("testetranca", totemEncontrado.getId());
      assertEquals(totemTranca, totemEncontrado.getTrancas());
    }
  }

  @Test
  public void removeNullTrancaTotemDAOTest() {
    ArrayList<String> totemResponse = totemDao.removeTrancaTotem("testeTranca", "testeTotem");
    assertNull(totemResponse);
  }
}
