package grupo2equipamento.Bicicleta;

import java.util.Objects;
import java.util.Optional;
import java.util.NoSuchElementException;
import io.javalin.http.Handler;

public class BicicletaController {
	static String msgExists = "Bicicleta nao encontrada";
	static String msgAvailability = "Bicicleta nao está disponível para essa operacao";
	static String msgData = "Dados invalidos";

	public static Handler getAllBicicletas = ctx -> {
		BicicletaDAO dao = BicicletaDAO.instance();
		try {
			ctx.json(dao.findAllBicicletas());
			ctx.status(200);

		} catch (Exception e) {
			ctx.json(e).status(500);
		}
	};

	public static Handler getBicicletaById = ctx -> {
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		BicicletaDAO dao = BicicletaDAO.instance();

		try {
			Optional<Bicicleta> bicicleta = dao.findBicicletaById(id);
			if (bicicleta.isPresent()) {
				ctx.json(bicicleta.get()).status(200);
			} else {
				ctx.status(404).html(msgExists);
			}

		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler postBicicleta = ctx -> {

		BicicletaDAO dao = BicicletaDAO.instance();
		Bicicleta newBicicleta = ctx.bodyAsClass(Bicicleta.class);

		try {
			Bicicleta bicicleta = dao.postBicicleta(newBicicleta);
			ctx.json(bicicleta).status(200);

		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateBicicleta = ctx -> {

		BicicletaDAO dao = BicicletaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Bicicleta newBicicleta = Objects.requireNonNull(ctx.bodyAsClass(Bicicleta.class));

		try {

			// validate if exists
			Optional<Bicicleta> bicicletaExists = dao.findBicicletaById(id);
			if (bicicletaExists.isPresent()) {
				Bicicleta bicicleta = dao.updateBicicleta(id, newBicicleta.getLocalizacao(), newBicicleta.getMarca(),
						newBicicleta.getModelo(), newBicicleta.getAno(), newBicicleta.getNumero());
				ctx.json(bicicleta).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateStatusBicicleta = ctx -> {

		BicicletaDAO dao = BicicletaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Bicicleta newBicicleta = Objects.requireNonNull(ctx.bodyAsClass(Bicicleta.class));

		try {

			// validate if exists
			Optional<Bicicleta> bicicletaExists = dao.findBicicletaById(id);
			if (bicicletaExists.isPresent()) {
				Bicicleta bicicleta = dao.updateStatusBicicleta(id, newBicicleta.getStatus());
				ctx.json(bicicleta).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler deleteBicicleta = ctx -> {

		BicicletaDAO dao = BicicletaDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));

		try {
			// validate if exists
			Optional<Bicicleta> bicicletaExists = dao.findBicicletaById(id);
			if (bicicletaExists.isPresent()) {
				Bicicleta bicicleta = bicicletaExists.get();
				if (bicicleta.getStatus().equals("APOSENTADA")) {
					dao.deleteBicicleta(id);
					ctx.json("Bicicleta com id: " + id + " deletada").status(200);
				} else {
					ctx.json(msgAvailability).status(405);
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
