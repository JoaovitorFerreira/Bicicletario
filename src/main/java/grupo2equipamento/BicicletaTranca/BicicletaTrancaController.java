package grupo2equipamento.BicicletaTranca;

import grupo2equipamento.JavalinApp.JavalinMailer;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;
import grupo2equipamento.Bicicleta.Bicicleta;
import grupo2equipamento.Bicicleta.BicicletaDAO;
import io.javalin.http.Handler;
import kong.unirest.Unirest;
import grupo2equipamento.Respostas.Funcionario.Funcionario;

public class BicicletaTrancaController {
  static final String msgExistsB = "Bicicleta nao encontrada";
  static final String msgExistsT = "Tranca nao encontrada";
  static final String msgData = "Dados invalidos";
  static final String msgAvailabilityTB = "Bicicleta ou tranca não está disponível para essa operação";
  static final String msgExistsTB = "Bicicleta ou tranca não existe";
  final static String disponivel = "DISPONIVEL";
  final static String nova = "NOVA";
  final static String ocupada = "OCUPADA";
  static final String reparo = "EM REPARO";
  
  public BicicletaTrancaController(){}

  public static Handler integrarNaRede = ctx -> {
    BicicletaDAO bicicletaDao = BicicletaDAO.instance();
    TrancaDAO trancaDao = TrancaDAO.instance();
    BicicletaTranca redeBicicleta = ctx.bodyAsClass(BicicletaTranca.class);
    JavalinMailer mail = new JavalinMailer();

    try {
      // validate if exists
      Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaById(redeBicicleta.getIdBicicleta());
      Optional<Tranca> trancaExists = trancaDao.findTrancaById(redeBicicleta.getIdTranca());
      String idFuncionario = redeBicicleta.getIdFuncionario();

      if (bicicletaExists.isPresent() && trancaExists.isPresent()) {

        Bicicleta bicicleta = bicicletaExists.get();
        String statusBicicleta = bicicleta.getStatus();
        String idBicicleta = bicicleta.getId();

        Tranca tranca = trancaExists.get();
        String statusTranca = tranca.getStatus();
        String idTranca = tranca.getId();
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter padraoDaData = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormatada = dataAtual.format(padraoDaData);

        Funcionario res = Unirest.get("https://grupo3-aluguel.herokuapp.com/funcionario/" + idFuncionario)
            .asObject(Funcionario.class).getBody();
        if (res.getMatricula() != null) {

          String mensagemDeEmail = "id-tranca:" + idTranca + ";\n" + "id-Bicicleta:" + idBicicleta + ";\n"
              + "data de insercao:" + dataFormatada;

          if ((statusBicicleta.equals(nova) || statusBicicleta.equals(reparo)) && statusTranca.equals(disponivel)) {
            // atualizar status bicicleta
            bicicletaDao.updateStatusBicicleta(idBicicleta, disponivel);
            // atualizar status tranca
            trancaDao.updateStatusTranca(idTranca, ocupada);
            // atualizar bicicleta na tranca
            trancaDao.updateBicicletaTranca(idTranca, idBicicleta);

            mail.SendEmail(res.getEmail(), "Integracao na rede de Bicicleta", mensagemDeEmail);

            ctx.html("Bicicleta de id:" + idBicicleta + " integrada na tranca de id:" + idTranca).status(200);

          } else {
            ctx.json(msgExistsTB);
          }
        } else {
          ctx.json("Funcionario nao existe");
        }

      } else {
        ctx.status(404).html(msgExistsTB);
      }
    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }
  };
  

  public static Handler devolverBicicleta = ctx ->{
	  BicicletaDAO bicicletaDao = BicicletaDAO.instance();
	  TrancaDAO trancaDao = TrancaDAO.instance();
	  BicicletaTranca redeBicicleta = ctx.bodyAsClass(BicicletaTranca.class);
	  try {
	      // validate if exists
	      Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaById(redeBicicleta.getIdBicicleta());
	      Optional<Tranca> trancaExists = trancaDao.findTrancaById(redeBicicleta.getIdTranca());
	      
	      if (bicicletaExists.isPresent() && trancaExists.isPresent()) {

	          Bicicleta bicicleta = bicicletaExists.get();
	          String statusBicicleta = bicicleta.getStatus();
	          String idBicicleta = bicicleta.getId();
	          Tranca tranca = trancaExists.get();
	          String statusTranca = tranca.getStatus();
	          
	          if(statusBicicleta.equals("EM USO") && statusTranca.equals(disponivel)) {
	        	  tranca.setIdBicicleta(idBicicleta);
	        	  tranca.setStatus(ocupada);
	        	  bicicleta.setStatus(disponivel);
	        	  
	        	  ctx.json("bicicleta associada a tranca com sucesso").status(200);
	          }

	      }
	  }catch(IllegalArgumentException ex) {
		  ctx.json(msgData).status(422);  
	  }
	  catch(Exception e) {
		  ctx.json(e.getMessage()).status(500);
	  }
	  
	  

  }; 
  

  public static Handler retirarDaRedeReparo = ctx -> {

    BicicletaTranca redeBicicleta = ctx.bodyAsClass(BicicletaTranca.class);

    try {
      // validate if exists
      String funcionarioExists = redeBicicleta.getIdFuncionario();

      String response = retirarDaRede(redeBicicleta.getIdBicicleta(),redeBicicleta.getIdTranca(), funcionarioExists,
          reparo);
      ctx.json(response);

    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }

  };

  public static Handler retirarDaRedeAposentadoria = ctx -> {
    
    BicicletaTranca redeBicicleta = ctx.bodyAsClass(BicicletaTranca.class);
    String funcionarioExists = redeBicicleta.getIdFuncionario();

    try {
      // validate if exists
     

      String response = retirarDaRede(redeBicicleta.getIdBicicleta(),redeBicicleta.getIdTranca(), funcionarioExists,
          "APOSENTADA");
      ctx.json(response);
    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }
  };

  private static String retirarDaRede(String idBicicleta,String idTranca, String idFuncionario, String op) {
	  BicicletaDAO bicicletaDao = BicicletaDAO.instance();
	    TrancaDAO trancaDao = TrancaDAO.instance();
	    Optional<Bicicleta> bicicletaExists = bicicletaDao.findBicicletaById(idBicicleta);
	      Optional<Tranca> trancaExists = trancaDao.findTrancaById(idTranca);
	    
	  
    JavalinMailer mail = new JavalinMailer();
    if (bicicletaExists.isPresent() && trancaExists.isPresent()) {

      Bicicleta bicicleta = bicicletaExists.get();
      String statusBicicleta = bicicleta.getStatus();
      String idNovaBicicleta = bicicleta.getId();
      Tranca tranca = trancaExists.get();
      String statusTranca = tranca.getStatus();
      String idNovaTranca = tranca.getId();
      LocalDateTime dataAtual = LocalDateTime.now();
      DateTimeFormatter padraoDaData = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      String dataFormatada = dataAtual.format(padraoDaData);

      Funcionario res = Unirest.get("https://grupo3-aluguel.herokuapp.com/funcionario/" + idFuncionario)
          .asObject(Funcionario.class).getBody();
      if (res.getMatricula() != null) {

        String mensagemDeEmail = "matricula do reparador:" + res.getMatricula() + ";\n" + "id-Bicicleta:" + idNovaBicicleta
            + ";\n" + "data de retirada:" + dataFormatada;

        if (statusBicicleta.equals("REPARO SOLICITADO") && statusTranca.equals(ocupada)) {
          // atualizar status bicicleta
          bicicletaDao.updateStatusBicicleta(idNovaBicicleta, op);
          // atualizar status tranca
          trancaDao.updateStatusTranca(idNovaTranca, disponivel);
          // atualizar bicicleta na tranca
          trancaDao.updateBicicletaTranca(idNovaTranca, null);

          mail.SendEmail(res.getEmail(), "Retirada de bicicleta da rede", mensagemDeEmail);

          return "Bicicleta de id:" + idNovaBicicleta + " foi retirada com sucesso";
        } else {
          return msgAvailabilityTB;
        }
      } else {
        return "funcionario nao existe.";
      }

    } else {
      return msgExistsTB;
    }
  }

  public static Handler getBicicletaTranca = ctx -> {

    TrancaDAO trancaDAO = TrancaDAO.instance();
    BicicletaDAO bicicletaDAO = BicicletaDAO.instance();

    String trancaId = Objects.requireNonNull(ctx.pathParam("id"));

    try {
      Optional<Tranca> trancaExists = trancaDAO.findTrancaById(trancaId);
      if (trancaExists.isPresent()) {
        Tranca trancaWithBike = trancaExists.get();
        Optional<Bicicleta> bicicletaExists = bicicletaDAO.findBicicletaById(trancaWithBike.getIdBicicleta());
        if (bicicletaExists.isPresent()) {
          Bicicleta bicicletaFound = bicicletaExists.get();
          ctx.json(bicicletaFound).status(200);
        } else {
          ctx.status(404).html(msgExistsB);
        }

      }
    } catch (IllegalArgumentException e) {
      ctx.json(msgData).status(422);
    } catch (NoSuchElementException e) {
      ctx.status(404).html(msgExistsT);
    } catch (Exception e) {
      ctx.json(e.getMessage()).status(500);
    }
  };

  
}
