package grupo2equipamento;

import grupo2equipamento.JavalinApp.JavalinAppBuilder;

public class App {

  public static void main(String[] args) {
    JavalinAppBuilder app = new JavalinAppBuilder();
    app.startApplication(getHerokuAssignedPort());
  }

  private static int getHerokuAssignedPort() {
    String herokuPort = System.getenv("PORT");
    if (herokuPort != null) {
      return Integer.parseInt(herokuPort);
    }
    return 7810;
  }

}
