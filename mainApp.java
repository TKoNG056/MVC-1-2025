import Model.DataModel;
import View.LoginFrame;

import javax.swing.SwingUtilities;

public class mainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataModel dataModel = new DataModel();
            new LoginFrame(dataModel).setVisible(true);
        });
    }
    
}
