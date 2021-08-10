package grupo2equipamento.JavalinApp;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavalinMailer {

  private Session sessao;

  public JavalinMailer() {
    // as propriedades do nosso mailer
    Properties props = new Properties();
    /** Parâmetros de conexão com servidor Gmail */
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.ssl.checkserveridentity", true);

    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("sistema.bicicletario@gmail.com", "bike2021");
      }
    });

    setSessao(session);
    session.setDebug(true);
  }

  public void setSessao(Session sessao) {
    this.sessao = sessao;
  }

  public boolean SendEmail(String remetente, String assunto, String mensagem) {

    try {
      Message message = new MimeMessage(sessao);
      message.setFrom(new InternetAddress("sistema.bicicletario@gmail.com"));
      Address[] toUser = InternetAddress.parse(remetente);
      message.setRecipients(Message.RecipientType.TO, toUser);
      message.setSubject(assunto);
      message.setText(mensagem);
      Transport.send(message);
      return true;

    } catch (MessagingException e) {
      return false;
    }
  }
}
