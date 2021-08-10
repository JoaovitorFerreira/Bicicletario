package grupo2equipamento.Totem;

import java.util.Objects;
import java.util.Optional;
import java.util.NoSuchElementException;
import io.javalin.http.Handler;

public class TotemController {
	static String msgExists = "Totem nao encontrado";
	static String msgAvailability = "Totem nao está disponível para essa operacao";
	static String msgData = "Dados invalidos";
	static String msgLocation = "Ja existe um totem nessa localizacao";

	public static Handler getAllTotens = ctx -> {
		TotemDAO dao = TotemDAO.instance();
		try {
			ctx.json(dao.findAllTotens());
			ctx.status(200);

		} catch (Exception e) {
			ctx.json(e).status(500);
		}
	};

	public static Handler getTotemById = ctx -> {
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		TotemDAO dao = TotemDAO.instance();

		try {
			Optional<Totem> totem = dao.findTotemById(id);

			if (totem.isPresent()) {
				ctx.json(totem.get()).status(200);
			} else {
				ctx.status(404).html(msgExists);
			}

		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}

	};

	public static Handler postTotem = ctx -> {

		TotemDAO dao = TotemDAO.instance();
		Totem newTotem = ctx.bodyAsClass(Totem.class);

		try {
			// validate if exists
			Optional<Totem> alreadyExists = dao.findTotemByLocalizacao(newTotem.getLocalizacao());
			if (alreadyExists.isPresent()) {
				ctx.status(400).html(msgLocation);
			} else {
				Totem totem = dao.postTotem(newTotem);
				ctx.json(totem).status(200);
			}

		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler updateTotem = ctx -> {

		TotemDAO dao = TotemDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		Totem newTotem = Objects.requireNonNull(ctx.bodyAsClass(Totem.class));

		try {

			// validate if exists
			Optional<Totem> alreadyExists = dao.findTotemById(id);
			if (alreadyExists.isPresent()) {
				Totem totem = dao.updateTotem(id, newTotem.getLocalizacao());
				ctx.json(totem).status(200);

			}
		} catch (IllegalArgumentException e) {
			ctx.json(msgData).status(422);
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler deleteTotem = ctx -> {

		TotemDAO dao = TotemDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		try {

			// validate if exists
			Optional<Totem> totemExists = dao.findTotemById(id);
			if (totemExists.isPresent()) {
				Totem totem = totemExists.get();
				if (totem.getTrancas().isEmpty()) {
					dao.deleteTotem(id);
					ctx.json("Totem com id: " + id + " deletado").status(200);
				} else {
					ctx.json(msgAvailability);
				}

			}
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

	public static Handler trancasTotem = ctx -> {

		TotemDAO dao = TotemDAO.instance();
		String id = Objects.requireNonNull(ctx.pathParam("id"));
		try {
			// validate if exists
			Optional<Totem> totemExists = dao.findTotemById(id);
			if (totemExists.isPresent()) {
				ctx.json(dao.allTrancasTotem(id)).status(200);
			}
		} catch (NoSuchElementException e) {
			ctx.status(404).html(msgExists);
		} catch (Exception e) {
			ctx.json(e.getMessage()).status(500);
		}
	};

}
