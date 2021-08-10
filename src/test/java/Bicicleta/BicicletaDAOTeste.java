package Bicicleta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import grupo2equipamento.Bicicleta.Bicicleta;
import grupo2equipamento.Bicicleta.BicicletaDAO;

public class BicicletaDAOTeste {
  BicicletaDAO bicicletaDao;

  @Before
  public void init() {
    bicicletaDao = BicicletaDAO.instance();
  }

  @After
  public void finalize() {
    bicicletaDao.resetInstance();
  }

  @Test
  public void instanceTest1() {
    bicicletaDao = null;
    bicicletaDao = BicicletaDAO.instance();
    assertNotNull(bicicletaDao);
  }

  @Test
  public void instanceTest2() {
    assertNotNull(bicicletaDao);
  }

  @Test
  public void getAllBicicletaDAOTest() {
    ArrayList<Bicicleta> bicicletas = bicicletaDao.findAllBicicletas();
    assertTrue(bicicletas.size() > 0);
  }

  @Test
  public void getByIdBicicletaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L11, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaById(bicicletaTeste.getId());
    if (bicicletaExists.isPresent()) {
      assertEquals(bicicletaTeste.getId(), bicicletaExists.get().getId());
    }
  }

  @Test
  public void getByIdNullBicicletaDAOTest() {
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaById("testeId");
    assertTrue(!bicicletaExists.isPresent());
  }

  @Test
  public void getByLocalBicicletaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L11, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("L11, L22");
    if (bicicletaExists.isPresent()) {
      assertEquals("L11, L22", bicicletaExists.get().getLocalizacao());
    }
  }

  @Test
  public void getByLocalNullBicicletaDAOTest() {
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("testeId");
    assertTrue(!bicicletaExists.isPresent());
  }

  @Test
  public void postTrancaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L11, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("L11, L22");
    if (bicicletaExists.isPresent()) {
      Bicicleta bicicletaEncontrado = bicicletaExists.get();
      assertEquals("L11, L22", bicicletaEncontrado.getLocalizacao());
    }
  }

  @Test
  public void updateBicicletaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L11, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("L11, L22");
    if (bicicletaExists.isPresent()) {
      Bicicleta bicicletaEncontrado = bicicletaExists.get();
      bicicletaDao.updateBicicleta(bicicletaEncontrado.getId(), "teste", "Caloi", "c10", "2010", 100);
      assertEquals("teste", bicicletaEncontrado.getLocalizacao());
    }
  }

  @Test
  public void updateNullBicicletaDAOTest() {
    Bicicleta bicicletaResponse = bicicletaDao.updateBicicleta("testeId", "L11, L22", "Caloi", "c10", "2010", 100);
    assertNull(bicicletaResponse);
  }

  @Test
  public void updateStatusBicicletaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L11, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("L11, L22");
    if (bicicletaExists.isPresent()) {
      Bicicleta bicicletaEncontrado = bicicletaExists.get();
      bicicletaDao.updateStatusBicicleta(bicicletaEncontrado.getId(), "teste");
      assertEquals("teste", bicicletaEncontrado.getStatus());
    }
  }

  @Test
  public void updateNullStatusBicicletaDAOTest() {
    Bicicleta bicicletaResponse = bicicletaDao.updateStatusBicicleta("testeId", "DISPONIVEL");
    assertNull(bicicletaResponse);
  }

  @Test
  public void deleteBicicletaDAOTest() {
    Bicicleta bicicletaTeste = new Bicicleta("L10, L22", "Caloi", "c10", "2010", 100);
    bicicletaDao.postBicicleta(bicicletaTeste);
    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaByLocalizacao("L10, L22");
    if (bicicletaExists.isPresent()) {
      Bicicleta bicicletaEncontrado = bicicletaExists.get();
      bicicletaDao.deleteBicicleta(bicicletaEncontrado.getId());
    }
    Optional<Bicicleta> bicicletaFind = bicicletaDao.findBicicletaByLocalizacao("L10, L22");
    assertFalse(bicicletaFind.isPresent());
  }

}
