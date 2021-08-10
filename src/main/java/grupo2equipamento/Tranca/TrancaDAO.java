package grupo2equipamento.Tranca;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class TrancaDAO {
	private static ArrayList<Tranca> trancas = new ArrayList<>();

	static {
		trancas.add(new Tranca(1110, "-22.9976533,-43.3603155,17z", "2010", "AZ2021"));
		trancas.add(new Tranca(1111, "-22.9219335,-43.237562,17z", "2011", "AZ2022"));
		trancas.add(new Tranca(1112, "-22.7587364,-43.4599269,16z", "2014", "AZ2023"));
		trancas.add(new Tranca(1113, "-22.9923446533,-43.3603155,17z", "2011", "A12SS1"));
		trancas.add(new Tranca(1115, "-22.9219335,-43.245662,15z", "2016", "AZ2232"));
		trancas.add(new Tranca(1114, "-22.7e87364,-43.4522269,16z", "2014", "AZ2045"));
		trancas.add(new Tranca(1118, "-22.7999988,-43.23232323,16z", "2019", "AZ2095"));
		trancas.add(new Tranca(1119, "0.00,00.0", "2019", "AZ2995"));
		trancas.add(new Tranca(1139, "1.11,11.1", "2019", "AZ2997"));
		trancas.add(new Tranca(1041, "1.11,22.1", "2017", "AZ2887"));
		trancas.add(new Tranca(1120, "1.11,12.2", "2019", "AZ2977"));
		Tranca tranca = new Tranca(1116, "-22.7587364,-43.4599269,16z", "2014", "AZ2023");
		
		tranca.setStatus("DISPONIVEL");
		trancas.add(tranca);
	}

	private static TrancaDAO trancaDAO = null;

	private TrancaDAO() {

	}

	public static TrancaDAO instance() {
		if (trancaDAO == null) {
			trancaDAO = new TrancaDAO();
		}
		return trancaDAO;
	}

	public void resetInstance() {
		trancaDAO = null;
	}

	// get all Trancas
	public ArrayList<Tranca> findAllTrancas() {
		return trancas;
	}

	// get Tranca by id
	public Optional<Tranca> findTrancaById(String id) {
		return trancas.stream().filter(x -> x.getId().compareTo(id) == 0).findFirst();
	}

	// get Tranca by localizacao
	public Optional<Tranca> findTrancaByLocalizacao(String localizacao) {
		return trancas.stream().filter(x -> x.getLocalizacao().compareTo(localizacao) == 0).findFirst();
	}

	// post Tranca
	public Tranca postTranca(Tranca tranca) {
		Tranca createdTranca = new Tranca(tranca.getNumero(), tranca.getModelo(), tranca.getLocalizacao(),
				tranca.getAnoFabricacao());
		trancas.add(createdTranca);
		return createdTranca;
	}

	// update Tranca
	public Tranca updateTranca(String trancaId, int numero, String localizacao, String anoFabricacao, String modelo) {

		Optional<Tranca> trancaExists = findTrancaById(trancaId);
		if (trancaExists.isPresent()) {
			Tranca trancaToUpdate = trancaExists.get();
			trancaToUpdate.setLocalizacao(localizacao);
			trancaToUpdate.setModelo(modelo);
			trancaToUpdate.setAnoFabricacao(anoFabricacao);
			trancaToUpdate.setNumero(numero);
			return trancaToUpdate;
		}
		return null;

	}

	// update status
	public Tranca updateStatusTranca(String trancaId, String status) {

		Optional<Tranca> trancaExists = findTrancaById(trancaId);
		if (trancaExists.isPresent()) {
			Tranca trancaToUpdate = trancaExists.get();
			trancaToUpdate.setStatus(status);
			if(status.equals("DISPONIVEL")) {
				trancaToUpdate.setIdBicicleta(null);
			}
			return trancaToUpdate;
		}
		return null;
	}

	// update bicicleta na tranca
	public Tranca updateBicicletaTranca(String trancaId, String idBicicleta) {
		Optional<Tranca> trancaExists = findTrancaById(trancaId);
		if (trancaExists.isPresent()) {
			Tranca trancaToUpdate = trancaExists.get();
			trancaToUpdate.setIdBicicleta(idBicicleta);
			return trancaToUpdate;
		}
		return null;
	}

	// delete Tranca
	public void deleteTranca(String id) {
		trancas.removeIf(x -> x.getId().compareTo(id) == 0);
	}
}
