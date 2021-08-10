package Bicicleta;

import grupo2equipamento.JavalinApp.JavalinAppBuilder;
import grupo2equipamento.Bicicleta.Bicicleta;
import grupo2equipamento.Bicicleta.BicicletaDAO;
import io.javalin.plugin.json.JavalinJson;
import static org.junit.Assert.*;
import java.text.MessageFormat;
import java.util.Optional;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import org.junit.Test;

public class BicicletaControllerTeste {

  public BicicletaDAO bicicletaDao;
  public JavalinAppBuilder appTeste = new JavalinAppBuilder();

  @Test
  public void getAllBicicletasTest() {
    appTeste.startApplication(8100);
    HttpResponse<String> res = Unirest.get("http://localhost:8100/bicicleta").asString();
    assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void getBicicletaByIdTest() {
    appTeste.startApplication(8101);
    BicicletaDAO bike = BicicletaDAO.instance();
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("-22.9976533,-43.3603155,17z");
    if (bikeExists.isPresent()) {
      Bicicleta bicicleta = bikeExists.get();
      HttpResponse<String> res = Unirest.get("http://localhost:8101/bicicleta/" + bicicleta.getId()).asString();
      assertEquals(200, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();

  }

  @Test
  public void getBicicletaById404Test() {
    appTeste.startApplication(8102);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta bicicleta = new Bicicleta("teste3", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(bicicleta);
    HttpResponse<String> res = Unirest.get("http://localhost:8102/bicicleta/" + bicicleta.getId()).asString();
    assertEquals(404, res.getStatus());
    bike.resetInstance();
    appTeste.stop();
  }

  @Test
  public void postBicicletaTest() {
    appTeste.startApplication(8103);
    Bicicleta b = new Bicicleta("teste5", "teste2", "teste3", "teste4", 12);
    String bicicletaToJson = JavalinJson.toJson(b);
    HttpResponse<String> res = Unirest.post("http://localhost:8103/bicicleta").body(bicicletaToJson).asString();
    assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void post400BicicletaTest() {
    appTeste.startApplication(8104);
    Bicicleta b = null;
    HttpResponse<String> res = Unirest.post("http://localhost:8104/bicicleta").body(b).asString();
    assertEquals(400, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void putBicicletaTest() {
    appTeste.startApplication(8105);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste7", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste7");
    if (bikeExists.isPresent()) {
      Bicicleta bikeToUpdate = bikeExists.get();
      String id = bikeToUpdate.getId();
      String bicicletaToJson = JavalinJson.toJson(bikeToUpdate);
      HttpResponse<String> res = Unirest.put("http://localhost:8105/bicicleta/" + id).body(bicicletaToJson).asString();
      assertEquals(200, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();
  }

  @Test
  public void putBicicletaInvalidTest() {
    appTeste.startApplication(8106);
    BicicletaDAO bike = null;
    String id = "900";
    HttpResponse<String> res = Unirest.put("http://localhost:8106/bicicleta/" + id).body(bike).asString();
    assertEquals(400, res.getStatus());
    appTeste.stop();
  }

  @Test(expected = IllegalArgumentException.class)
  public void putBicicletaNullTest() {
    appTeste.startApplication(8200);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste8", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste8");
    if (bikeExists.isPresent()) {
      Bicicleta bikeToUpdate = bikeExists.get();
      String id = bikeToUpdate.getId();
      Bicicleta bikeToInsert = null;
      String bicicletaToJson = JavalinJson.toJson(bikeToInsert);

      HttpResponse<String> res = Unirest.put("http://localhost:8200/bicicleta/" + id).body(bicicletaToJson).asString();
      assertEquals(404, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();
  }

  @Test
  public void UpdateStatusInvalidBicicletaTest() {
    appTeste.startApplication(8201);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste5", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste5");
    if (bikeExists.isPresent()) {
      Bicicleta bikeToSetNewStatus = bikeExists.get();
      String bikeJson = "EM REPARO";
      HttpResponse<String> res = Unirest
          .patch("http://localhost:8201/bicicleta/" + bikeToSetNewStatus.getId() + "/status/").body(bikeJson)
          .asString();
      assertEquals(400, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();
  }

  @Test
  public void UpdateStatusBicicletaTest() {
    appTeste.startApplication(8202);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste13", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste13");
    if (bikeExists.isPresent()) {
      Bicicleta bikeToSetNewStatus = bikeExists.get();
      char plick = '"';
      char colch = '{';
      char colchInv = '}';
      String result = MessageFormat.format("{1}{0}status{0}:{0}EM REPARO{0}{2}", plick, colch, colchInv);
      HttpResponse<String> res = Unirest
          .patch("http://localhost:8202/bicicleta/" + bikeToSetNewStatus.getId() + "/status/").body(result).asString();
      assertEquals(200, res.getStatus());
    }
    appTeste.stop();
  }

  @Test
  public void deleteBicicletaTest() {
    appTeste.startApplication(8301);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste6", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste6");
    if (bikeExists.isPresent()) {
      Bicicleta bicicletaAAposentar = bikeExists.get();
      bicicletaAAposentar.setStatus("APOSENTADA");
      HttpResponse<String> res = Unirest.delete("http://localhost:8301/bicicleta/" + bicicletaAAposentar.getId())
          .asString();
      assertEquals(200, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();
  }

  @Test
  public void DeleteInvalidBicicletaTest() {
    appTeste.startApplication(8302);
    BicicletaDAO bike = BicicletaDAO.instance();
    Bicicleta b = new Bicicleta("teste10", "teste2", "teste3", "teste4", 12);
    bike.postBicicleta(b);
    Optional<Bicicleta> bikeExists = bike.findBicicletaByLocalizacao("teste10");
    if (bikeExists.isPresent()) {
      Bicicleta bicicletaAAposentar = bikeExists.get();
      HttpResponse<String> res = Unirest.delete("http://localhost:8302/bicicleta/" + bicicletaAAposentar.getId())
          .asString();
      assertEquals(405, res.getStatus());
    }
    bike.resetInstance();
    appTeste.stop();
  }

}
