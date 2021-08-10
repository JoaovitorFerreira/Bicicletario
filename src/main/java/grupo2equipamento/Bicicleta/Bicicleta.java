package grupo2equipamento.Bicicleta;

import java.util.UUID;

public class Bicicleta {
    private String id;
    private String localizacao;
    private String marca;
    private String modelo;
    private String ano;
    private int numero;
    private String status;
    UUID uniqueKey = UUID.randomUUID();

    public Bicicleta() {

    }

    public Bicicleta(String localizacao, String marca, String modelo, String ano, int numero) {
        if (localizacao == null || marca == null || modelo == null || ano == null) {
            throw new IllegalArgumentException("nao pode ser nulo");
        }
        this.id = UUID.randomUUID().toString();
        this.localizacao = localizacao;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = "NOVA";
    }

    // getters
    public String getId() {
        return this.id;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public String getAno() {
        return this.ano;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getStatus() {
        return this.status;
    }

    // setters
    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
