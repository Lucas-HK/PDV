import controller.VendaController;
import javax.swing.SwingUtilities;

import view.TelaLogin;
import view.TelaPDV;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VendaController controller = new VendaController();

            TelaLogin login = new TelaLogin(controller);
            login.setVisible(true);
        });
    }
}

//LOGIN -> admin
//SENHA -> 2705