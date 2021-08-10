package grupo2equipamento.Totem;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TotemDAO {

	private static ArrayList<Totem> totens = new ArrayList<>();

	static {
		totens.add(new Totem("-22.9219335,-43.237562,17z"));
		totens.add(new Totem("-22.9976533,-43.3603155,17z"));
		totens.add(new Totem("-22.9569748,-43.1790104,17z"));
	}

	private static TotemDAO totemDAO = null;

	private TotemDAO() {

	}

	public static TotemDAO instance() {
		if (totemDAO == null) {
			totemDAO = new TotemDAO();
		}
		return totemDAO;
	}

	public void resetInstance() {
		totemDAO = null;
	}

	// get all totens
	public ArrayList<Totem> findAllTotens() {

		return totens;

	}

	// get totem by id
	public Optional<Totem> findTotemById(String id) {

		return totens.stream().filter(x -> x.getId().compareTo(id) == 0).findFirst();

	}

	// get totem by localizacao
	public Optional<Totem> findTotemByLocalizacao(String localizacao) {
		return totens.stream().filter(x -> x.getLocalizacao().compareTo(localizacao) == 0).findFirst();
	}

	// post totem
	public Totem postTotem(Totem totem) {

		Totem createdTotem = new Totem(totem.getLocalizacao());
		totens.add(createdTotem);
		return createdTotem;

	}

	// update totem
	public Totem updateTotem(String totemId, String localizacao) {

		Optional<Totem> totemExists = findTotemById(totemId);
		if (totemExists.isPresent()) {
			Totem totemToUpdate = totemExists.get();
			totemToUpdate.setLocalizacao(localizacao);
			return totemToUpdate;
		}
		return null;
	}

	// delete totem
	public void deleteTotem(String id) {

		totens.removeIf(x -> x.getId().compareTo(id) == 0);

	}

	// listar trancas de um totem
	public ArrayList<String> allTrancasTotem(String idTotem) {
		Optional<Totem> totemExists = totens.stream().filter(x -> x.getId().compareTo(idTotem) == 0).findFirst();
		if (totemExists.isPresent()) {
			Totem totem = totemExists.get();
			return totem.getTrancas();
		}
		return null;
	}

	// adicionar tranca ao totem
	public ArrayList<String> addTrancaTotem(String idTranca, String idTotem) {
		Optional<Totem> totemExists = totens.stream().filter(x -> x.getId().compareTo(idTotem) == 0).findFirst();
		if (totemExists.isPresent()) {
			Totem totemToAddTranca = totemExists.get();
			ArrayList<String> array = new ArrayList<>();
			array = totemToAddTranca.getTrancas();
			array.add(idTranca);
			totemToAddTranca.setTrancas(array);
			return totemToAddTranca.getTrancas();
		}
		return null;
	}

	// remover tranca do totem
	public ArrayList<String> removeTrancaTotem(String idTranca, String idTotem) {
		Optional<Totem> totemExists = totens.stream().filter(x -> x.getId().compareTo(idTotem) == 0).findFirst();
		if (totemExists.isPresent()) {
			Totem totemToRemoveTranca = totemExists.get();
			ArrayList<String> array = new ArrayList<>();
			array = totemToRemoveTranca.getTrancas();
			array.remove(idTranca);
			totemToRemoveTranca.setTrancas(array);
			return totemToRemoveTranca.getTrancas();
		}
		return null;
	}
}
