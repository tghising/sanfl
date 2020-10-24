
package sanfl;

import javax.lang.model.SourceVersion;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author tghising
 */
public class Controller {
    // create Table object to enable various menu functions
    Table table;

    public Controller(Table t) {
        this.table = t;
    }

    public void commandLoop(){
        // helpMenuItems() method called to display menu items
        helpMenuItems();

        // handle user commands
        boolean exit = false;
        do {
            // create a scanner object to read console input
            Scanner input = new Scanner(System.in);
            System.out.print("Choose option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    helpMenuItems();
                    break;
                case 2:
                    // method called to pull all entries
                    ArrayList<Entry> allEntries = table.getAllEntries();
                    // method called to display details
                    displayLeagueTable(allEntries, "All Teams", "ALL STATISTICS");
                    break;
                case 3:
                    //Sub-menu for display selected statistics
                    System.out.println("******************************************************************************************");
                    System.out.println("                      SANFL WOMEN’S FOOTBALL LEAGUE SELECTED STATISTICS                   ");
                    System.out.println("******************************************************************************************");
                    System.out.println("\tSUB-MENU OPTIONS:                                                                      ");
                    System.out.println("\t\t1. The team that has scored the highest number of points");
                    System.out.println("\t\t2. The team that has had the lowest number of points scored against them");
                    System.out.println("\t\t3. The average value of the points scored for all teams");
                    System.out.println("\t\t0. Return back to Main Menu");

                    // handle user commands in submenu
                    boolean exitSubmenu = false;
                    do {
                        // create a scanner object to read console input
                        Scanner subMenuInput = new Scanner(System.in);
                        System.out.print("Choose option (sub-menu): ");
                        int subMenuChoice = subMenuInput.nextInt();
                        switch(subMenuChoice) {
                            case 1:
                                Entry highestForScorerTeam = table.highestScoreFor();
                                displaySelectedTeam(highestForScorerTeam, "HIGHEST SCORE FOR POINTS");
                                break;
                            case 2:
                                Entry lowestAgainstScorerTeam = table.lowestScoreAgainst();
                                displaySelectedTeam(lowestAgainstScorerTeam, "LOWEST SCORE AGAINST POINTS");
                                break;
                            case 3:
                                System.out.println("******************************************************************************************");
                                System.out.println("               SANFL WOMEN’S FOOTBALL LEAGUE : AVERAGE POINTS SCORED BY ALL TEAMS         ");
                                System.out.println("*******************************************************************************************");
                                System.out.println(" THE AVERAGE POINTS SCORED BY ALL TEAMS : " + table.averageScoreFor());
                                System.out.println("--------------------------------------------------------------------------------------------");
                                break;
                            case 0:
                                helpMenuItems();
                                exitSubmenu = true;
                                break;
                            default:
                                System.out.println("\t\tInvalid sub-menu option!");
                                break;
                        }

                    } while (!exitSubmenu);

                    break;
                case 4:
                    System.out.print("Enter a Team Name:");
                    String teamName = input.next();
                    // call method to search team details in arrays of entry
                    Entry searchTeam = table.lookupTeam(teamName);
                    if (searchTeam.getTeam() == null) {
                        System.out.println(teamName + " IS NOT FOUND IN LEAGUE TABLE!");
                    } else {
                        displaySelectedTeam(searchTeam, "SPECIFIED TEAM");
                    }
                    break;
                case 5:
                    System.out.print("Enter a Team Name:");
                    String findTeam = input.next();
                    // call method to display all team entries which have the same points with the given team
                    ArrayList<Entry> teamsOnSamePoints = table.findTeamsOnSamePoints(findTeam);
                    if (teamsOnSamePoints.isEmpty()) {
                        System.out.println(findTeam + " IS NOT FOUND IN LEAGUE TABLE!");
                    } else {
                        displayLeagueTable(teamsOnSamePoints, findTeam, "TEAMS WITH SAME POINTS WITH " + findTeam);
                    }
                    break;
                case 6:
                    // read and process final scores for each game
                    System.out.println("Enter a result of match (Format: Name Goals Behinds)");

                    Entry e1 = new Entry();
                    Entry e2 = new Entry();
                    String team1, team2;
                    int goal1, goal2, behind1, behind2;
                    do {
                        System.out.print("Home Team>");
                        // Team1  name, scored goal and behind identifier of Home team
                        team1 = input.next();
                        goal1 = input.nextInt();
                        behind1 = input.nextInt();
                        e1 = table.lookupTeam(team1);
                        if (e1.getTeam() == null) {
                            System.out.println(team1 + " IS NOT FOUND IN LEAGUE TABLE!");
                        }
                    } while (e1.getTeam() == null);

                    do {
                        System.out.print("Away Team>");
                        // Team1  name, scored goal and behind identifier of Away team
                        team2 = input.next();
                        goal2 = input.nextInt();
                        behind2 = input.nextInt();
                        e2 = table.lookupTeam(team2);
                        if (e2.getTeam() == null) {
                            System.out.println(team2 + " IS NOT FOUND IN LEAGUE TABLE!");
                        }
                    } while (e2.getTeam() == null);

                    // create a Game object for map value via constructor and determination of points, margin and winner of game
                    table.processGame(team1, goal1, behind1, team2, goal2, behind2);
                    System.out.println("---------------- NEW RESULT IS ADDED FOR " + team1 + " AND " + team2 +  " ------------------");
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("\tInvalid option.");
            }

        } while (!exit);
    }

    public void helpMenuItems() {
        System.out.println("|=========================================================================================|");
        System.out.println("|                      MENU OPTIONS OF SANFL WOMEN’S FOOTBALL LEAGUE                      |");
        System.out.println("|=========================================================================================|");
        System.out.println("| Available OPTIONS:                                                                      |");
        System.out.println("|       1. Help - Display available commands                                              |");
        System.out.println("|       2. Display current table                                                          |");
        System.out.println("|       3. Display selected statistics                                                    |");
        System.out.println("|       4. Lookup a specified team                                                        |");
        System.out.println("|       5. Find teams with same points as a specified team                                |");
        System.out.println("|       6. Add a new result                                                               |");
        System.out.println("|       7. Quit                                                                           |");
        System.out.println("|=========================================================================================|");
    }

    // method to display all main menu options in the application
    public void displayLeagueTable(ArrayList<Entry> data, String team, String resultTitle) {
        System.out.println("*******************************************************************************************");
        System.out.println("                    SANFL WOMEN’S FOOTBALL LEAGUE TABLE : " + resultTitle + "              ");
        System.out.println("*******************************************************************************************");
        System.out.println("POS\tTEAM\tPLAYED\tWINS\tLOSSES\tDRAWN\tSCORE FOR\tSCORE AGAINST\tPOINTS\tPERCENTAGE(%)");
        if (data.isEmpty()){
            System.out.println(team + " IS NOT FOUND IN LEAGUE TABLE!");
        } else {
            for (int i = 0; i < data.size(); i++) {
                double percentage = data.get(i).getPercentage();
                // if there is score for and score against is zero then percentage is return as NaN,
                // if NaN, percentage will display 0.0
                if (Double.isNaN(percentage)){
                    percentage = 0.0;
                }
                System.out.printf("%d\t%s\t%d\t%d\t%d\t%d\t%d\t\t%d\t\t%d\t%.1f\n", data.get(i).getPosition(), data.get(i).getTeam(), data.get(i).getPlayed(), data.get(i).getWon(), data.get(i).getLost(), data.get(i).getDrawn(), data.get(i).getScoreFor(), data.get(i).getScoreAgainst(), data.get(i).getPoints(), percentage);
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }
    // method to display single team entry details
    public void displaySelectedTeam(Entry entry, String resultTitle) {
        System.out.println("*******************************************************************************************");
        System.out.println("                    SANFL WOMEN’S FOOTBALL LEAGUE : " + resultTitle);
        System.out.println("********************************************************************************************");
        System.out.println("POS\tTEAM\tPLAYED\tWINS\tLOSSES\tDRAWN\tSCORE FOR\tSCORE AGAINST\tPOINTS\tPERCENTAGE(%)");

        double percentage = entry.getPercentage();
        if (Double.isNaN(percentage)){
            percentage = 0.0;
        }
        System.out.printf("%d\t%s\t%d\t%d\t%d\t%d\t%d\t\t%d\t\t%d\t%.1f\n", entry.getPosition(), entry.getTeam(), entry.getPlayed(), entry.getWon(), entry.getLost(), entry.getDrawn(), entry.getScoreFor(), entry.getScoreAgainst(), entry.getPoints(), percentage);
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}
