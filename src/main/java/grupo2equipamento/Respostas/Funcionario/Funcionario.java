package grupo2equipamento.Respostas.Funcionario;

public class Funcionario {

  private String id;
  private String matricula;
  private String email;

  public Funcionario() {

  }

  public Funcionario(String id, String matricula, String senha, String nome, String idade, String funcao, String cpf,
      String email) {

    this.id = id;
    this.matricula = matricula;
    this.email = email;

  }

  // getters
  public String getId() {
    return this.id;
  }

  public String getMatricula() {
    return this.matricula;
  }

  public String getEmail() {
    return this.email;
  }

}