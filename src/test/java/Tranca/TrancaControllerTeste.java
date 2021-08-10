package Tranca;

import grupo2equipamento.JavalinApp.JavalinAppBuilder;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;
import io.javalin.plugin.json.JavalinJson;
import static org.junit.Assert.*;
import java.text.MessageFormat;
import java.util.Optional;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import org.junit.Test;

public class TrancaControllerTeste {

  public JavalinAppBuilder appTeste = new JavalinAppBuilder();

  @Test
  public void getAllTrancasTest() {
    appTeste.startApplication(4100);
    HttpResponse<String> res = Unirest.get("http://localhost:4100/tranca").asString();
    assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void getTrancaByIdTest() {
    appTeste.startApplication(4101);
    TrancaDAO trancadao = TrancaDAO.instance();
    Tranca trancaResultado =trancadao.findTrancaByLocalizacao("1.11,11.1").get();
      HttpResponse<String> res = Unirest.get("http://localhost:4101/tranca/" + trancaResultado.getId()).asString();
      assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void getTrancaById404Test() {
    appTeste.startApplication(4102);
    TrancaDAO trancadao = TrancaDAO.instance();
    HttpResponse<String> res = Unirest.get("http://localhost:4102/tranca/" + "f34dfg").asString();
    assertEquals(404, res.getStatus());
    trancadao.resetInstance();
    appTeste.stop();
  }

  @Test
  public void postTrancaTest() {
    appTeste.startApplication(4103);
    Tranca b = new Tranca(12, "teste2", "teste3", "teste4");
    String trancaToJson = JavalinJson.toJson(b);
    HttpResponse<String> res = Unirest.post("http://localhost:4103/tranca").body(trancaToJson).asString();
    assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void post400TrancaTest() {
    appTeste.startApplication(4104);
    Tranca b = null;
    HttpResponse<String> res = Unirest.post("http://localhost:4104/tranca").body(b).asString();
    assertEquals(400, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void updateTrancaTest() {
    appTeste.startApplication(4105);
    Tranca b = new Tranca(12, "teste2", "teste3", "teste4");
      String id = b.getId();
      String trancaToJson = JavalinJson.toJson(b);
      HttpResponse<String> res = Unirest.put("http://localhost:4105/tranca/" + id).body(trancaToJson).asString();
      assertEquals(200, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void updateTrancaInvalidTest() {
    appTeste.startApplication(4106);
    TrancaDAO trancadao = null;
    String id = "900";
    HttpResponse<String> res = Unirest.put("http://localhost:4106/tranca/" + id).body(trancadao).asString();
    assertEquals(400, res.getStatus());
    appTeste.stop();
  }

  @Test
  public void updateTrancaNullTest() {
    appTeste.startApplication(4200);
    TrancaDAO trancadao = TrancaDAO.instance();
    Tranca b = new Tranca(12, "teste2", "teste3", "teste4");
    trancadao.postTranca(b);
    Optional<Tranca> trancadaoExists = trancadao.findTrancaByLocalizacao("teste2");
    assertTrue(!trancadaoExists.isPresent());
    trancadao.resetInstance();
    appTeste.stop();
  }

  @Test
  public void UpdateStatusInvalidTrancaTest() {
    appTeste.startApplication(4201);
    TrancaDAO trancadao = TrancaDAO.instance();
    Optional<Tranca> trancadaoExists = trancadao.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
    if (trancadaoExists.isPresent()) {
      Tranca trancadaoToSetNewStatus = trancadaoExists.get();
      String trancadaoJson = "EM USO";
      HttpResponse<String> res = Unirest
          .patch("http://localhost:4201/tranca/" + trancadaoToSetNewStatus.getId() + "/status/").body(trancadaoJson)
          .asString();
      assertEquals(400, res.getStatus());
    }
    trancadao.resetInstance();
    appTeste.stop();
  }

  @Test
  public void UpdateStatusTrancaTest() {
    appTeste.startApplication(4202);
    TrancaDAO trancadao = TrancaDAO.instance();
    Optional<Tranca> trancadaoExists = trancadao.findTrancaByLocalizacao("1.11,12.2");
    if (trancadaoExists.isPresent()) {
      Tranca trancadaoToSetNewStatus = trancadaoExists.get();
      char plick = '"';
      char colch = '{';
      char colchInv = '}';
      String result = MessageFormat.format("{1}{0}status{0}:{0}EM REPARO{0}{2}", plick, colch, colchInv);
      HttpResponse<String> res = Unirest
          .patch("http://localhost:4202/tranca/" + trancadaoToSetNewStatus.getId() + "/status/").body(result).asString();
      assertEquals(200, res.getStatus());
    }
    appTeste.stop();
  }

  @Test
  public void deleteTrancaTest() {
    appTeste.startApplication(4301);
    TrancaDAO trancadao = TrancaDAO.instance();
    Optional<Tranca> trancadaoExists = trancadao.findTrancaByLocalizacao("0.00,00.0");
    if (trancadaoExists.isPresent()) {
      Tranca trancaAAposentar = trancadaoExists.get();
      trancaAAposentar.setStatus("APOSENTADA");
      HttpResponse<String> res = Unirest.delete("http://localhost:4301/tranca/" + trancaAAposentar.getId())
          .asString();
      assertEquals(200, res.getStatus());
    }
    trancadao.resetInstance();
    appTeste.stop();
  }

  @Test
  public void DeleteInvalidTrancaTest() {
    appTeste.startApplication(4302);
    
      HttpResponse<String> res = Unirest.delete("http://localhost:4302/tranca/")
          .asString();
      assertEquals(404, res.getStatus());
    appTeste.stop();
  }

}