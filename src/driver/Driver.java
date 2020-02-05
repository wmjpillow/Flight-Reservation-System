package driver;

import flightsystem.FlightSystemUI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author CsS509team1
 *
 */
public class Driver {
    static private Logger driverLogger;
    static {
        driverLogger = Logger.getLogger(Driver.class.getName());
        driverLogger.setLevel(Level.INFO);
    }

	/**
         * Main entry of application
	 * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
	 */
	public static void main(String[] args) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(FlightSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(FlightSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(FlightSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(FlightSystemUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
            });
        
            FlightSystemUI uiObj = new FlightSystemUI();
            uiObj.setVisible(true);
	}
}
