
package sanfl;

/**
 *
 * @author tghising
 */
public class AustralianWomensFootballLeague {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("######################## WELCOME TO SANFL WOMEN’S FOOTBALL LEAGUE ##########################");
        // creation of Table class object
        Table table = new Table();
        // creation of Controller class object
        Controller controller = new Controller(table);
        // method call to display menu commands
        controller.commandLoop();
        System.out.println("########################### ENDED SANFL WOMEN’S FOOTBALL LEAGUE ############################");        
    }
}
