package grupo2equipamento.JavalinApp;

import grupo2equipamento.Totem.TotemController;
import grupo2equipamento.Bicicleta.BicicletaController;
import grupo2equipamento.BicicletaTranca.BicicletaTrancaController;
import grupo2equipamento.TrancaTotem.TrancaTotemController;
import grupo2equipamento.Tranca.TrancaController;
import io.javalin.Javalin;

public class JavalinAppBuilder {

  private Javalin app = Javalin.create(config -> {
    config.defaultContentType = "application/json";
  });

  public void startApplication(int port) {
    this.app.start(port);

    String rotaTotem = "/totem/:id";
    String rotaBicicleta = "/bicicleta/:id";
    String rotaTranca = "/tranca/:id";
    String rotaBicicletaTranca = "/bicicleta/integrarNaRede";
    String rotaDevolucaoBicicleta = "/bicicleta/devolucao";
    String rotaBicicletaTrancaRetirada = "/bicicleta/retirarDaRede";

    // Rotas Totem
    app.get("/", ctx -> ctx.result("Sistema de controle de bicicletário - Dupla 2"));
    app.get("/totem", TotemController.getAllTotens);
    app.get(rotaTotem, TotemController.getTotemById);
    app.get(rotaTotem + "/trancas", TotemController.trancasTotem);
    // falta a post(rotaTotem + "/trancas", TotemController.trancasTotem)
    // falta a delete(rotaTotem + "/trancas", TotemController.trancasTotem)
    app.post("/totem", TotemController.postTotem);
    app.patch(rotaTotem, TotemController.updateTotem);
    app.delete(rotaTotem, TotemController.deleteTotem);

    // Rotas Bicicleta
    app.get("/bicicleta", BicicletaController.getAllBicicletas);
    app.get(rotaBicicleta, BicicletaController.getBicicletaById);
    app.post("/bicicleta", BicicletaController.postBicicleta);
    app.put(rotaBicicleta, BicicletaController.updateBicicleta);
    app.patch(rotaBicicleta + "/status", BicicletaController.updateStatusBicicleta);
    app.delete(rotaBicicleta, BicicletaController.deleteBicicleta);

    // Rotas Tranca
    app.get("/tranca", TrancaController.getAllTrancas);
    app.get(rotaTranca, TrancaController.getTrancaById);
    app.post("/tranca", TrancaController.postTranca);
    app.put(rotaTranca, TrancaController.updateTranca);
    app.patch(rotaTranca + "/bicicleta", TrancaController.updateBicicletaTranca);
    app.patch(rotaTranca + "/status", TrancaController.updateStatusTranca);
    app.delete(rotaTranca, TrancaController.deleteTranca);

    // Rotas Integração Bicicleta-Tranca
    app.get("/tranca/:id/bicicleta", BicicletaTrancaController.getBicicletaTranca);
    app.post(rotaBicicletaTranca, BicicletaTrancaController.integrarNaRede);
    app.post(rotaBicicletaTrancaRetirada + "/reparo", BicicletaTrancaController.retirarDaRedeReparo);
    app.post(rotaBicicletaTrancaRetirada + "/aposentadoria", BicicletaTrancaController.retirarDaRedeAposentadoria);
    app.post(rotaDevolucaoBicicleta, BicicletaTrancaController.devolverBicicleta);
    
    // Rotas Integração Tranca-Totem
    app.post("/tranca/integrarNaRede", TrancaTotemController.integrarNaRede);
    app.post("/tranca/retirarDaRede/reparo", TrancaTotemController.retirarDaRedeReparo);
    app.post("/tranca/retirarDaRede/aposentadoria", TrancaTotemController.retirarDaRedeAposentadoria);
    

  }

  public void stop() {
    app.stop();
  }
}
