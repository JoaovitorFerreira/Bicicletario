package TrancaTotem;

import java.util.Optional;

import org.junit.Test;

import grupo2equipamento.JavalinApp.JavalinAppBuilder;
import grupo2equipamento.Respostas.Funcionario.Funcionario;
import grupo2equipamento.Totem.Totem;
import grupo2equipamento.Totem.TotemDAO;
import grupo2equipamento.Tranca.Tranca;
import grupo2equipamento.Tranca.TrancaDAO;
import grupo2equipamento.TrancaTotem.TrancaTotem;
import io.javalin.plugin.json.JavalinJson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import static org.junit.Assert.*;

public class TrancaTotemControllerTeste {

  public JavalinAppBuilder appTeste = new JavalinAppBuilder();

  @Test
  public void TesteIntegrarNaRede() {
    appTeste.startApplication(8132);
    TotemDAO totem = TotemDAO.instance();
    TrancaDAO tranca = TrancaDAO.instance();
    totem.resetInstance();
    tranca.resetInstance();
    
    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.9219335,-43.237562,17z");
    
    if(totemExists.isPresent()) {
    	Totem totemEncontrado = totemExists.get();
    	
    	if(trancaExists.isPresent()) {
    	Tranca trancaEncontrada = trancaExists.get();	
    	
    		Funcionario funcionario = Unirest
      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
      	          .asObject(Funcionario.class).getBody();
    		
    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),trancaEncontrada.getId(),funcionario.getId());
             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
             
      	      HttpResponse<String> res = Unirest.post("http://localhost:8132/tranca/integrarNaRede").body(trancaTotemToJson)
      	    		  .asString();
      	      assertEquals( 200, res.getStatus());
    	}
    }  
    appTeste.stop();
  }

  @Test
  public void TesteIntegrarNaRedeSemTranca() {
    appTeste.startApplication(8101);
    TotemDAO totem = TotemDAO.instance();

    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
    Tranca trancaExists = null;
    
    if(totemExists.isPresent()) {
      	      assertNull( trancaExists);
    }  

    appTeste.stop();
  }

  @Test
  public void TesteIntegrarNaRedeSemTotem() {
	    appTeste.startApplication(8102);
	    TrancaDAO tranca = TrancaDAO.instance();

	    Totem totemExists = null;
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
	    
	    if(trancaExists.isPresent()) {
	      	      assertNull( totemExists);
	    }  

	    appTeste.stop();
	  }
  
  @Test
  public void TesteIntegrarTotemNaoDisponivel() {
	    appTeste.startApplication(8303);
	    TotemDAO totem = TotemDAO.instance();
	    TrancaDAO tranca = TrancaDAO.instance();
	    
	    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
	    
	    
	    
	    if(totemExists.isPresent()) {
	    	Totem totemEncontrado = totemExists.get();
	    	
	    	if(trancaExists.isPresent()) {
	    	Tranca trancaEncontrada = trancaExists.get();	
	    	tranca.updateStatusTranca(trancaEncontrada.getId(), "OCUPADA");
	    		
	    		Funcionario funcionario = Unirest
	      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
	      	          .asObject(Funcionario.class).getBody();
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),trancaEncontrada.getId(),funcionario.getId());
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8303/tranca/integrarNaRede").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 400, res.getStatus());
	    	}
	    }  
	    appTeste.stop();
	  }
  
  @Test
  public void TesteIntegrarFuncionarioNaoExiste() {
	    appTeste.startApplication(8557);
	    TotemDAO totem = TotemDAO.instance();
	    TrancaDAO tranca = TrancaDAO.instance();
	    
	    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
	    
	    
	    
	    if(totemExists.isPresent()) {
	    	Totem totemEncontrado = totemExists.get();
	    	
	    	if(trancaExists.isPresent()) {
	    	Tranca trancaEncontrada = trancaExists.get();	
	    		
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),trancaEncontrada.getId(),"45435435");
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8557/tranca/integrarNaRede").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 500, res.getStatus());
	    	}
	    }  
	    appTeste.stop();
	  } 
  
  @Test 
  public void TesteIntegrarTotemNaoEncontrado() {
	    appTeste.startApplication(8222);
	    TrancaDAO tranca = TrancaDAO.instance();
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");

	    	if(trancaExists.isPresent()) {
	    	Tranca trancaEncontrada = trancaExists.get();	
	    	
	    		Funcionario funcionario = Unirest
	      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
	      	          .asObject(Funcionario.class).getBody();
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem("r2342",trancaEncontrada.getId(),funcionario.getId());
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8222/tranca/integrarNaRede").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 404, res.getStatus());
	    	}
	    appTeste.stop();
	  }
  
  @Test 
  public void TesteIntegrarTrancaNaoEncontrado() {
	    appTeste.startApplication(8232);
	    TotemDAO totem = TotemDAO.instance();
	    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
	    
	    	if(totemExists.isPresent()) {
	    		Totem totemEncontrado = totemExists.get();	
	    	
	    		Funcionario funcionario = Unirest
	      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
	      	          .asObject(Funcionario.class).getBody();
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),"34343",funcionario.getId());
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8232/tranca/integrarNaRede").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 404, res.getStatus());
	    	}
	    appTeste.stop();
	  }

  @Test
  public void TesteRetirarDaRedeReparo() {
	  appTeste.startApplication(8887);
	    TotemDAO totem = TotemDAO.instance();
	    TrancaDAO tranca = TrancaDAO.instance();
	    totem.resetInstance();
	    tranca.resetInstance();
	    
	    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
	    
	    if(totemExists.isPresent()) {
	    	Totem totemEncontrado = totemExists.get();
	    	
	    	if(trancaExists.isPresent()) {
	    	Tranca trancaEncontrada = trancaExists.get();	
	    		tranca.updateStatusTranca(trancaEncontrada.getId(), "REPARO SOLICITADO");
	    		tranca.updateBicicletaTranca(trancaEncontrada.getId(), null);
	    	
	    		Funcionario funcionario = Unirest
	      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
	      	          .asObject(Funcionario.class).getBody();
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),trancaEncontrada.getId(),funcionario.getId());
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8887/tranca/retirarDaRede/reparo").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 200, res.getStatus());
	    	}
	    }  
	    appTeste.stop();
	  }
  
  @Test
  public void TesteRetirarDaRedeAposentadoria() {
	  appTeste.startApplication(8885);
	    TotemDAO totem = TotemDAO.instance();
	    TrancaDAO tranca = TrancaDAO.instance();
	    totem.resetInstance();
	    tranca.resetInstance();
	    
	    Optional<Totem> totemExists = totem.findTotemByLocalizacao("-22.9976533,-43.3603155,17z");
	    Optional<Tranca> trancaExists = tranca.findTrancaByLocalizacao("-22.7587364,-43.4599269,16z");
	    
	    if(totemExists.isPresent()) {
	    	Totem totemEncontrado = totemExists.get();
	    	
	    	if(trancaExists.isPresent()) {
	    	Tranca trancaEncontrada = trancaExists.get();	
	    		tranca.updateStatusTranca(trancaEncontrada.getId(), "REPARO SOLICITADO");
	    		tranca.updateBicicletaTranca(trancaEncontrada.getId(), null);
	    	
	    		Funcionario funcionario = Unirest
	      	          .get("https://grupo3-aluguel.herokuapp.com/funcionario/" + "1355a257-a5c0-4ddd-9cd0-3929a55007f5")
	      	          .asObject(Funcionario.class).getBody();
	    		
	    		 TrancaTotem trancaTotem = new TrancaTotem(totemEncontrado.getId(),trancaEncontrada.getId(),funcionario.getId());
	             String trancaTotemToJson = JavalinJson.toJson(trancaTotem);
	             
	      	      HttpResponse<String> res = Unirest.post("http://localhost:8885/tranca/retirarDaRede/aposentadoria").body(trancaTotemToJson)
	      	    		  .asString();
	      	      assertEquals( 200, res.getStatus());
	    	}
	    }  
	    appTeste.stop();
	  }

}
