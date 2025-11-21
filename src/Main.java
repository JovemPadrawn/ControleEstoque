import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormGerenciadorEstoque form = new FormGerenciadorEstoque();
                form.setVisible(true);
            }
        });
    }
}