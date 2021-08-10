package grupo2equipamento.Tranca;

import java.util.Objects;
import java.util.Optional;
import java.util.NoSuchElementException;
import io.javalin.http.Handler;

public class TrancaController {
	static String msgExists = "Tranca nao encontrada";
	static String msgAvailability = "Tranca nao está disponível para essa operacao";
	static String msgData = "Dados invalidos";

	public static Handler getAllTrancas = ctx -> {
		TrancaDAO dao = TrancaDAO.instance();
		try {
			ctx.json(dao.findAllTrancas());
			ctx.status(200);

		} catch (Exception e) {
			ctx.json(e).status(500);
		}
	};

	public static Handler getTrancaById = ctx -> {
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		TrancaDAO dao = TrancaDAO.instance();

		try {
			Optional<Tranca> tranca = dao.findTrancaById(id);

			if (tranca.isPresent()) {
				ctx.json(tranca.get()).status(200);
			} else {
				ctx.status(404).html(msgExists);
			}

		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}

	};

	public static Handler postTranca = ctx -> {

		TrancaDAO dao = TrancaDAO.instance();
		Tranca newTranca = ctx.bodyAsClass(Tranca.class);

		try {
			Tranca tranca = dao.postTranca(newTranca);
			ctx.json(tranca).status(200);

		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateTranca = ctx -> {

		TrancaDAO dao = TrancaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Tranca newTranca = Objects.requireNonNull(ctx.bodyAsClass(Tranca.class));

		try {

			// validate if exists
			Optional<Tranca> trancaExists = dao.findTrancaById(id);
			if (trancaExists.isPresent()) {
				Tranca tranca = dao.updateTranca(id, newTranca.getNumero(), newTranca.getLocalizacao(),
						newTranca.getAnoFabricacao(), newTranca.getModelo());
				ctx.json(tranca).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateStatusTranca = ctx -> {

		TrancaDAO dao = TrancaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Tranca newTranca = Objects.requireNonNull(ctx.bodyAsClass(Tranca.class));

		try {

			// validate if exists
			Optional<Tranca> trancaExists = dao.findTrancaById(id);
			if (trancaExists.isPresent()) {
				Tranca tranca = dao.updateStatusTranca(id, newTranca.getStatus());
				ctx.json(tranca).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateBicicletaTranca = ctx -> {

		TrancaDAO dao = TrancaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Tranca newTranca = Objects.requireNonNull(ctx.bodyAsClass(Tranca.class));

		try {

			// validate if exists
			Optional<Tranca> trancaExists = dao.findTrancaById(id);
			if (trancaExists.isPresent()) {
				Tranca tranca = dao.updateBicicletaTranca(id, newTranca.getIdBicicleta());
				ctx.json(tranca).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler deleteTranca = ctx -> {

		TrancaDAO dao = TrancaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		try {

			// validate if exists
			Optional<Tranca> trancaExists = dao.findTrancaById(id);
			if (trancaExists.isPresent()) {
				Tranca tranca = trancaExists.get();
				if (tranca.getIdBicicleta() == null && tranca.getStatus().equals("APOSENTADA")) {
					dao.deleteTranca(id);
					ctx.json("Tranca com id: " + id + " deletado").status(200);
				} else {
					ctx.json(msgAvailability);
				}

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};
}
