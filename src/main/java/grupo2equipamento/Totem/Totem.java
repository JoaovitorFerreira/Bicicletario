package grupo2equipamento.Totem;

import java.util.ArrayList;
import java.util.UUID;

public class Totem {

	private String id;
	private String localizacao;
	private ArrayList<String> trancas = new ArrayList<>();
	UUID uniqueKey = UUID.randomUUID();

	public Totem() {

	}

	public Totem(String localizacao) {
		this.id = UUID.randomUUID().toString();
		if (localizacao == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.localizacao = localizacao;
	}

	// getters
	public String getId() {
		return this.id;
	}

	public String getLocalizacao() {
		return this.localizacao;
	}

	public ArrayList<String> getTrancas() {
		return this.trancas;
	}

	// setters
	public void setLocalizacao(String localizacao) {
		if (localizacao == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.localizacao = localizacao;
	}

	public void setTrancas(ArrayList<String> arrayTrancas) {
		this.trancas = arrayTrancas;
	}

}
