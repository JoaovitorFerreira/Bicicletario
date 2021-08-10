package grupo2equipamento.BicicletaTranca;

public class BicicletaTranca {

  private String idTranca;
  private String idBicicleta;
  private String idFuncionario;

  public BicicletaTranca() {

  }

  public BicicletaTranca(String idTranca, String idBicicleta, String idFuncionario) {
    if (idTranca == null || idBicicleta == null || idFuncionario == null) {
      throw new IllegalArgumentException("nao pode ser nulo");
    }
    this.idBicicleta = idBicicleta;
    this.idTranca = idTranca;
    this.idFuncionario = idFuncionario;
  }

  public void setIdTranca(String idTranca) {
    this.idTranca = idTranca;
  }

  public void setIdFuincionario(String idFuncionario) {
    this.idFuncionario = idFuncionario;
  }

  public void setIdBicicleta(String idBicicleta) {
    this.idBicicleta = idBicicleta;
  }

  public String getIdTranca() {
    return this.idTranca;
  }

  public String getIdFuncionario() {
    return this.idFuncionario;
  }

  public String getIdBicicleta() {
    return this.idBicicleta;
  }

}
