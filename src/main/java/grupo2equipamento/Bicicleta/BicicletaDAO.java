package grupo2equipamento.Bicicleta;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class BicicletaDAO {
	private static ArrayList<Bicicleta> bicicletas = new ArrayList<>();

	static {
		bicicletas.add(new Bicicleta("-22.9976533,-43.3603155,17z", "Caloi", "c10", "2010", 100));
		bicicletas.add(new Bicicleta("-22.9219335,-43.237562,17z", "Trek", "tk20", "2011", 120));
		bicicletas.add(new Bicicleta("22.8825342,-43.4180762,16z", "Scott", "sc30", "2012", 140));
		bicicletas.add(new Bicicleta("-22.9966633,-43.3603155,17z", "Caloi", "c10", "2010", 107));
		bicicletas.add(new Bicicleta("-22.9213435,-43.237562,17z", "Trek", "tk20", "2012", 121));
		bicicletas.add(new Bicicleta("22.88995342,-43.4180762,16z", "Scott", "sc30", "2012", 142));
		bicicletas.add(new Bicicleta("-22.7999988,-43.23232323,16z", "Caloi", "c50", "2020", 199));
	}

	private static BicicletaDAO bicicletaDAO = null;

	private BicicletaDAO() {

	}

	public static BicicletaDAO instance() {
		if (bicicletaDAO == null) {
			bicicletaDAO = new BicicletaDAO();
		}
		return bicicletaDAO;
	}

	public void resetInstance() {
		bicicletaDAO = null;
	}

	// get all bicicletas
	public ArrayList<Bicicleta> findAllBicicletas() {
		return bicicletas;
	}

	// get bicicleta by id
	public Optional<Bicicleta> findBicicletaById(String id) {
		return bicicletas.stream().filter(x -> x.getId().compareTo(id) == 0).findFirst();
	}

	// get bicicleta by localizacao
	public Optional<Bicicleta> findBicicletaByLocalizacao(String localizacao) {
		return bicicletas.stream().filter(x -> x.getLocalizacao().compareTo(localizacao) == 0).findFirst();
	}

	// post bicicleta
	public Bicicleta postBicicleta(Bicicleta bicicleta) {
		Bicicleta createdBicicleta = new Bicicleta(bicicleta.getLocalizacao(), bicicleta.getMarca(), bicicleta.getModelo(),
				bicicleta.getAno(), bicicleta.getNumero());
		bicicletas.add(createdBicicleta);
		return createdBicicleta;
	}

	// update bicicleta
	public Bicicleta updateBicicleta(String bicicletaId, String localizacao, String marca, String modelo, String ano,
			int numero) {

		Optional<Bicicleta> bicicletaExists = findBicicletaById(bicicletaId);

		if (bicicletaExists.isPresent()) {
			Bicicleta bicicletaToUpdate = bicicletaExists.get();
			bicicletaToUpdate.setLocalizacao(localizacao);
			bicicletaToUpdate.setMarca(marca);
			bicicletaToUpdate.setModelo(modelo);
			bicicletaToUpdate.setAno(ano);
			bicicletaToUpdate.setNumero(numero);
			return bicicletaToUpdate;
		}
		return null;
	}

	// update status
	public Bicicleta updateStatusBicicleta(String bicicletaId, String status) {
		Optional<Bicicleta> bicicletaExists = findBicicletaById(bicicletaId);

		if (bicicletaExists.isPresent()) {
			Bicicleta bicicletaToUpdate = bicicletaExists.get();
			bicicletaToUpdate.setStatus(status);
			return bicicletaToUpdate;
		}
		return null;
	}

	// delete bicicleta
	public void deleteBicicleta(String id) {
		bicicletas.removeIf(x -> x.getId().compareTo(id) == 0);
	}

}
