package grupo2equipamento.Tranca;

import java.util.UUID;

public class Tranca {
	private String id;
	private String idBicicleta;
	private int numero;
	private String localizacao;
	private String anoFabricacao;
	private String modelo;
	private String status;

	UUID uniqueKey = UUID.randomUUID();

	public Tranca() {

	}

	public Tranca(int numero, String localizacao, String anoFabricacao, String modelo) {
		this.id = UUID.randomUUID().toString();
		if (localizacao == null || anoFabricacao == null || modelo == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.numero = numero;
		this.localizacao = localizacao;
		this.anoFabricacao = anoFabricacao;
		this.modelo = modelo;
		this.status = "NOVA";
	}

	// getters
	public String getId() {
		return this.id;
	}

	public String getIdBicicleta() {
		return this.idBicicleta;
	}

	public int getNumero() {
		return this.numero;
	}

	public String getLocalizacao() {
		return this.localizacao;
	}

	public String getAnoFabricacao() {
		return this.anoFabricacao;
	}

	public String getModelo() {
		return this.modelo;
	}

	public String getStatus() {
		return this.status;
	}

	// setters
	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setLocalizacao(String localizacao) {
		if (localizacao == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.localizacao = localizacao;
	}

	public void setAnoFabricacao(String ano) {
		if (ano == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.anoFabricacao = ano;
	}

	public void setModelo(String modelo) {
		if (modelo == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.modelo = modelo;
	}

	public void setStatus(String status) {
		if (status == null) {
			throw new IllegalArgumentException("nao pode ser nulo");
		}
		this.status = status;
	}

}
