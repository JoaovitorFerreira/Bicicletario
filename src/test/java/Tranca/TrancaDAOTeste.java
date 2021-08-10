package Tranca;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;

public class TrancaDAOTeste {

  TrancaDAO trancaDao;

  @Before
  public void init() {
    trancaDao = TrancaDAO.instance();
  }

  @After
  public void finalize() {
    trancaDao.resetInstance();
  }

  @Test
  public void instanceTest1() {
    trancaDao = null;
    trancaDao = TrancaDAO.instance();
    assertNotNull(trancaDao);
  }

  @Test
  public void instanceTest2() {
    assertNotNull(trancaDao);
  }

  @Test
  public void getAllTrancaDAOTest() {
    ArrayList<Tranca> trancas = trancaDao.findAllTrancas();
    assertTrue(trancas.size() > 0);
  }

  @Test
  public void getByIdTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L33,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L33,L12");
    if (trancaExists.isPresent()) {
      //Optional<Tranca> trancaExists2 = trancaDao.findTrancaById(trancaExists.get().getId());
      //assertEquals("L32,L12", trancaExists2.get().getLocalizacao());
    }
  }

  @Test
  public void getByIdNullTrancaDAOTest() {
    Optional<Tranca> trancaExists = trancaDao.findTrancaById("testeId");
    assertTrue(!trancaExists.isPresent());
  }

  @Test
  public void getByLocalTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      assertEquals("L32,L12", trancaExists.get().getLocalizacao());
    }
  }

  @Test
  public void getByLocalNullTrancaDAOTest() {
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("testeId");
    assertTrue(!trancaExists.isPresent());
  }

  @Test
  public void postTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      //Tranca trancaEncontrado = trancaExists.get();
      //assertEquals("L32,L12", trancaEncontrado.getLocalizacao());
    }
  }

  @Test
  public void updateTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      Tranca trancaEncontrado = trancaExists.get();
      Tranca trancaUpdate = trancaDao.updateTranca(trancaEncontrado.getId(), 11, "L11,L11", "2011", "A111");
      assertEquals("L11,L11", trancaUpdate.getLocalizacao());
    }
  }

  @Test
  public void updateNullTrancaDAOTest() {
    Tranca trancaResponse = trancaDao.updateTranca("testeId", 11, "L11,L11", "2011", "A111");
    assertNull(trancaResponse);
  }

  @Test
  public void updateStatusTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      Tranca trancaEncontrado = trancaExists.get();
      Tranca trancaUpdate = trancaDao.updateStatusTranca(trancaEncontrado.getId(), "DISPONIVEL");
      assertEquals("DISPONIVEL", trancaUpdate.getStatus());
    }
  }

  @Test
  public void updateNullStatusTrancaaDAOTest() {
    Tranca trancaResponse = trancaDao.updateStatusTranca("testeId", "DISPONIVEL");
    assertNull(trancaResponse);
  }

  @Test
  public void updateBicicletaTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      Tranca trancaEncontrado = trancaExists.get();
      Tranca trancaUpdate = trancaDao.updateBicicletaTranca(trancaEncontrado.getId(), "idBike");
      assertEquals("idBike", trancaUpdate.getIdBicicleta());
    }
  }

  @Test
  public void updateNullBicicletaTrancaaDAOTest() {
    Tranca trancaResponse = trancaDao.updateBicicletaTranca("testeId", "idbike");
    assertNull(trancaResponse);
  }

  @Test
  public void deleteTrancaDAOTest() {
    Tranca trancaTeste = new Tranca(12, "L32,L12", "2010", "A2000");
    trancaDao.postTranca(trancaTeste);
    Optional<Tranca> trancaExists = trancaDao.findTrancaByLocalizacao("L32,L12");
    if (trancaExists.isPresent()) {
      Tranca trancaEncontrado = trancaExists.get();
      trancaDao.deleteTranca(trancaEncontrado.getId());
    }
    Optional<Tranca> trancaFind = trancaDao.findTrancaByLocalizacao("L32,L12");
    assertFalse(trancaFind.isPresent());
  }

}
