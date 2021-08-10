package grupo2equipamento.TrancaTotem;

public class TrancaTotem {
  private String idTotem;
  private String idTranca;
  private String idFuncionario;

  public TrancaTotem() {

  }

  public TrancaTotem(String idTotem, String idTranca, String idFuncionario) {
    if (idTotem == null || idTranca == null || idFuncionario == null) {
      throw new IllegalArgumentException("nao pode ser nulo");
    }
    this.idTotem = idTotem;
    this.idTranca = idTranca;
    this.idFuncionario = idFuncionario;
  }

  public void setIdTranca(String idTranca) {
    this.idTranca = idTranca;
  }

  public void setIdTotem(String idTotem) {
    this.idTotem = idTotem;
  }

  public String getIdTranca() {
    return this.idTranca;
  }

  public String getIdFuncionario() {
    return this.idFuncionario;
  }

  public String getIdTotem() {
    return this.idTotem;
  }
}
