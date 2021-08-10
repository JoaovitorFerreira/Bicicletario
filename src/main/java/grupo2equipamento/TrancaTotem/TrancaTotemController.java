package grupo2equipamento.TrancaTotem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;
import grupo2equipamento.JavalinApp.JavalinMailer;
import grupo2equipamento.Respostas.Funcionario.Funcionario;
import grupo2equipamento.Totem.Totem;
import grupo2equipamento.Totem.TotemDAO;
import io.javalin.http.Handler;
import kong.unirest.Unirest;

public class TrancaTotemController {
  static String msgAvailability = "Tranca nao está disponível para essa operacao";
  static String msgData = "Dados invalidos";
  static String msgExistsTT = "Tranca ou totem não existe";

  public static Handler integrarNaRede = ctx -> {
    TotemDAO totemDao = TotemDAO.instance();
    TrancaDAO trancaDao = TrancaDAO.instance();

    TrancaTotem redeTotem = ctx.bodyAsClass(TrancaTotem.class);

    try {
      // validate if exists
      Optional<Totem> totemExists = totemDao.findTotemById(redeTotem.getIdTotem());
      Optional<Tranca> trancaExists = trancaDao.findTrancaById(redeTotem.getIdTranca());
      JavalinMailer mail = new JavalinMailer();
      if (totemExists.isPresent() && trancaExists.isPresent()) {

        Totem totem = totemExists.get();
        String idTotem = totem.getId();

        Tranca tranca = trancaExists.get();
        String statusTranca = tranca.getStatus();
        String idTranca = tranca.getId();
        String idFuncionario = redeTotem.getIdFuncionario();
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter padraoDaData = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormatada = dataAtual.format(padraoDaData);

        Funcionario res = Unirest.get("https://grupo3-aluguel.herokuapp.com/funcionario/" + idFuncionario)
            .asObject(Funcionario.class).getBody();
        if (res.getMatricula() != null) {

          String mensagemDeEmail = "matricula do reparador:" + res.getMatricula() + ";\n" + "id-Tranca:" + idTranca
              + ";\n" + "data de retirada:" + dataFormatada;

          if (statusTranca.equals("NOVA") || statusTranca.equals("EM REPARO")) {
            // atualizar status tranca
            trancaDao.updateStatusTranca(idTranca, "DISPONIVEL");
            // vincular tranca no totem
            totemDao.addTrancaTotem(idTranca, idTotem);

            mail.SendEmail(res.getEmail(), "Integracao na rede de Tranca", mensagemDeEmail);
            ctx.html("Tranca de id:" + idTranca + " integrada no totem de id:" + idTotem).status(200);

          } else {
            ctx.json(msgAvailability).status(400);
          }
        }
      } else {
        ctx.status(404).html(msgExistsTT);
      }
    } catch (Exception e) {
      ctx.json(msgData).status(500);
    }

  };

  public static Handler retirarDaRedeReparo = ctx -> {
    TotemDAO totemDao = TotemDAO.instance();
    TrancaDAO trancaDao = TrancaDAO.instance();

    TrancaTotem redeTotem = ctx.bodyAsClass(TrancaTotem.class);

    try {
      // validate if exists
      Optional<Totem> totemExists = totemDao.findTotemById(redeTotem.getIdTotem());
      Optional<Tranca> trancaExists = trancaDao.findTrancaById(redeTotem.getIdTranca());
      String idFuncionario = redeTotem.getIdFuncionario();
      String response = retirarDaRede(totemExists, totemDao, trancaExists, trancaDao, idFuncionario, "EM REPARO");
      ctx.json(response);

    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }

  };

  public static Handler retirarDaRedeAposentadoria = ctx -> {
    TotemDAO totemDao = TotemDAO.instance();
    TrancaDAO trancaDao = TrancaDAO.instance();

    TrancaTotem redeTotem = ctx.bodyAsClass(TrancaTotem.class);

    try {
      // validate if exists
      Optional<Totem> totemExists = totemDao.findTotemById(redeTotem.getIdTotem());
      Optional<Tranca> trancaExists = trancaDao.findTrancaById(redeTotem.getIdTranca());
      String idFuncionario = redeTotem.getIdFuncionario();
      String response = retirarDaRede(totemExists, totemDao, trancaExists, trancaDao, idFuncionario, "APOSENTADA");
      ctx.json(response);

    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }

  };

  private static String retirarDaRede(Optional<Totem> totemExists, TotemDAO totemDao, Optional<Tranca> trancaExists,
      TrancaDAO trancaDao, String idFuncionario, String op) {
    JavalinMailer mail = new JavalinMailer();
    if (totemExists.isPresent() && trancaExists.isPresent()) {

      Totem totem = totemExists.get();
      String idTotem = totem.getId();
      Tranca tranca = trancaExists.get();
      String statusTranca = tranca.getStatus();
      String idTranca = tranca.getId();
      String idBicicletaTranca = tranca.getIdBicicleta();
      LocalDateTime dataAtual = LocalDateTime.now();
      DateTimeFormatter padraoDaData = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      String dataFormatada = dataAtual.format(padraoDaData);

      Funcionario res = Unirest.get("https://grupo3-aluguel.herokuapp.com/funcionario/" + idFuncionario)
          .asObject(Funcionario.class).getBody();
      if (res.getMatricula() != null) {

        String mensagemDeEmail = "matricula do reparador:" + res.getMatricula() + ";\n" + "id-Tranca:" + idTranca
            + ";\n" + "data de retirada:" + dataFormatada;

        if (statusTranca.equals("REPARO SOLICITADO") && idBicicletaTranca == null) {
          // atualizar status tranca
          trancaDao.updateStatusTranca(idTranca, op);
          // atualizar tranca no totem
          totemDao.removeTrancaTotem(idTranca, idTotem);
          mail.SendEmail(res.getEmail(), "Retirada de Tranca da rede", mensagemDeEmail);
          return "Tranca de id:" + idTranca + " foi retirada com sucesso";
        } 
          return msgAvailability;
      } 
        return "Funcionario nao existe";
    } 
      return msgExistsTT;
  }

}
