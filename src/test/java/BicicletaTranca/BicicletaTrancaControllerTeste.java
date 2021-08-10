package BicicletaTranca;

import java.util.Optional;

import org.junit.Test;

import grupo2equipamento.JavalinApp.JavalinAppBuilder;
import grupo2equipamento.Respostas.Funcionario.Funcionario;
import grupo2equipamento.Bicicleta.Bicicleta;
import grupo2equipamento.Bicicleta.BicicletaDAO;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;
import grupo2equipamento.BicicletaTranca.BicicletaTranca;
import grupo2equipamento.BicicletaTranca.BicicletaTrancaController;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static org.junit.Assert.*;

public class BicicletaTrancaControllerTeste {

	public JavalinAppBuilder appTeste = new JavalinAppBuilder();

	@Test
	public void TesteIntegrarNaRede() {
		appTeste.startApplication(8100);
		BicicletaDAO bicicleta = BicicletaDAO.instance();
		TrancaDAO tranca = TrancaDAO.instance();
		tranca.resetInstance();
		bicicleta.resetInstance();
		Optional<Bicicleta> bicicletaExists = bicicleta.findBicicletaByLocalizacao("-22.9219335,-43.237562,17z");
		Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.9923446533,-43.3603155,17z");

		if (bicicletaExists.isPresent()) {
			Bicicleta bicicletaExistente = bicicletaExists.get();

			if (trancaExists.isPresent()) {
				Tranca trancaExistente = trancaExists.get();

				tranca.updateStatusTranca(trancaExistente.getId(), "DISPONIVEL");

				Funcionario funcionario = Unirest.get(
						"https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
						.asObject(Funcionario.class).getBody();

				BicicletaTranca bicicletaTranca = new BicicletaTranca(trancaExistente.getId(),
						bicicletaExistente.getId(), funcionario.getId());
				String bicicletaTrancaToJson = JavalinJson.toJson(bicicletaTranca);
				HttpResponse<String> res = Unirest.post("http://localhost:8100/bicicleta/integrarNaRede")
						.body(bicicletaTrancaToJson).asString();
				assertEquals(200, res.getStatus());
			}
		}

		appTeste.stop();
	}

	@Test
	public void TesteRetirarDaRedeReparo() {

		appTeste.startApplication(8199);
		BicicletaDAO bicicleta = BicicletaDAO.instance();
		TrancaDAO tranca = TrancaDAO.instance();
		tranca.resetInstance();
		bicicleta.resetInstance();
		Optional<Bicicleta> bicicletaExists = bicicleta.findBicicletaByLocalizacao("-22.7999988,-43.23232323,16z");
		Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7999988,-43.23232323,16z");

		if (bicicletaExists.isPresent()) {
			Bicicleta bicicletaExistente = bicicletaExists.get();

			bicicleta.updateStatusBicicleta(bicicletaExistente.getId(), "REPARO SOLICITADO");
			if (trancaExists.isPresent()) {

				Tranca trancaExistente = trancaExists.get();

				tranca.updateStatusTranca(trancaExistente.getId(), "OCUPADA");

				Funcionario funcionario = Unirest.get(
						"https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
						.asObject(Funcionario.class).getBody();

				BicicletaTranca bicicletaTranca = new BicicletaTranca(trancaExistente.getId(),
						bicicletaExistente.getId(), funcionario.getId());
				String bicicletaTrancaToJson = JavalinJson.toJson(bicicletaTranca);
				HttpResponse<String> res = Unirest.post("http://localhost:8199/bicicleta/retirarDaRede/reparo")
						.body(bicicletaTrancaToJson).asString();
				assertEquals(200, res.getStatus());
			}
		}

		appTeste.stop();
	}

	@Test
	public void TesteRetirarDaRedeAposentadoria() {

		appTeste.startApplication(3040);
		BicicletaDAO bicicleta = BicicletaDAO.instance();
		TrancaDAO tranca = TrancaDAO.instance();
		tranca.resetInstance();
		bicicleta.resetInstance();
		Optional<Bicicleta> bicicletaExists = bicicleta.findBicicletaByLocalizacao("-22.9213435,-43.237562,17z");
		Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7e87364,-43.4522269,16z");

		if (bicicletaExists.isPresent()) {
			Bicicleta bicicletaExistente = bicicletaExists.get();

			bicicleta.updateStatusBicicleta(bicicletaExistente.getId(), "REPARO SOLICITADO");
			if (trancaExists.isPresent()) {

				Tranca trancaExistente = trancaExists.get();
				tranca.updateStatusTranca(trancaExistente.getId(), "OCUPADA");

				Funcionario funcionario = Unirest.get(
						"https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
						.asObject(Funcionario.class).getBody();

				BicicletaTranca bicicletaTranca = new BicicletaTranca(trancaExistente.getId(),
						bicicletaExistente.getId(), funcionario.getId());
				String bicicletaTrancaToJson = JavalinJson.toJson(bicicletaTranca);
				HttpResponse<String> res = Unirest.post("http://localhost:3040/bicicleta/retirarDaRede/aposentadoria")
						.body(bicicletaTrancaToJson).asString();
				assertEquals(200, res.getStatus());
			}
		}

		appTeste.stop();
	}
	
	@Test
	public void TesteConstrutor() {
		BicicletaTrancaController bcController = new BicicletaTrancaController();
		assertTrue(!bcController.equals(null));
	}

}
