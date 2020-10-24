
package sanfl;

import java.util.ArrayList;

/**
 *
 * @author tghising
 */
public class Table {

    // points per Goals score in the game
    final int POINTS_PER_GOAL = 6;

    private final int nTeams = 8;
    private Entry[] table = new Entry[nTeams];

    public Table() {
        // load the dummy Entry array
        load();
    }

    // Load the dummy Entry array values
    private void load() {
        // initial dummy values for teams, wins, losses, drawns, score for, score against
        String teams[] = new String[]{"Team1", "Team2", "Team3", "Team4", "Team5", "Team6", "Team7", "Team8"};
//        int wins[] = new int[]{3, 3, 2, 1, 1, 1, 1, 0};
        int wins[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//        int losses[] = new int[]{0, 0, 1, 2, 2, 2, 2, 3};
        int losses[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//        int drawn[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        int drawn[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//        int scoreFor[] = new int[]{149, 127, 105, 90, 85, 60, 50, 81};
        int scoreFor[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
//        int scoreAgainst[] = new int[]{50, 59, 65, 104, 118, 107, 150, 94};
        int scoreAgainst[] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

        // initialization of dummy data into Arrays of Entry
        for (int i = 0; i < nTeams; i++) {
            Entry entry = new Entry();
            entry.setPosition(i+1);
            entry.setTeam(teams[i]);
            entry.setPlayed(wins[i] + losses[i] + drawn[i]);
            entry.setWon(wins[i]);
            entry.setLost(losses[i]);
            entry.setDrawn(drawn[i]);
            entry.setScoreFor(scoreFor[i]);
            entry.setScoreAgainst(scoreAgainst[i]);
            entry.setPercentage();
//            entry.setPoints(wins[i] * WINNER_PREMIERSHIP_POINTS + drawn[i] * DRAWN_PREMIERSHIP_POINTS + LOOSER_PREMIERSHIP_POINTS);
            entry.setPoints();
            table[i] = entry;
        }
        // sort Entry array by call method
        table = pointsOrPercentageSelectionSort(table);
        // update the position of each team as per sort of entries
        table = updateEntryPosition(table);
    }

    // Method used to find all data and return Array list of entry
    public ArrayList<Entry> getAllEntries() {
        ArrayList<Entry> data = new ArrayList<Entry>();
        if (table != null) {
            for (int i = 0; i < table.length; i++) {
                data.add(table[i]);
            }
        }
        return data;
    }

    // Method used to lookup specified team Entry by using team.
    // Returns, Entry Object If found, otherwise return null value
    public Entry lookupTeam(String t) {
        // search Team Entry with team name t from sorted arrays of Entry table
        int index = searchTeamUsingLinearSearch(table, t);
        Entry le = new Entry();
        if (index > -1) {
            // get entry from index of Entry arrays table
            le = table[index];
        }
        return le;
    }

    // Method used to find teams (data) with having same points.
    // Returns, Array list of data
    public ArrayList<Entry> findTeamsOnSamePoints(String t) {
        ArrayList<Entry> teamsOnSamePoints = new ArrayList<Entry>();
        Entry lt = lookupTeam(t);
        if (lt.getTeam() != null) {
            for (int i = 0; i < table.length; i++) {
                // if each entry has points equal to the given team
                if (table[i].getPoints() == lt.getPoints()) {
                    // team add to the ArrayList of Entry
                    teamsOnSamePoints.add(table[i]);
                }
            }
        }
        return teamsOnSamePoints;
    }

    // Method used to find team name with highest score for
    public Entry highestScoreFor() {
        int max = Integer.MIN_VALUE;
        Entry highestScoreForTeam = new Entry();
        for (int i = 0; i < table.length; i++) {
            if ( table[i].getScoreFor() > max) {
                max = table[i].getScoreFor();
                highestScoreForTeam = table[i];
            }
        }
        return highestScoreForTeam;
    }

    // Method used to find team name with lowest score Against
    public Entry lowestScoreAgainst() {
        int min = Integer.MAX_VALUE;
        Entry lowestScoreAgainstTeam = new Entry();
        for (int i = 0; i < table.length; i++) {
            if (table[i].getScoreAgainst() < min) {
                min = table[i].getScoreAgainst();
                lowestScoreAgainstTeam = table[i];
            }
        }
        return lowestScoreAgainstTeam;
    }

    // Method used to find average score for points among all entry array list, average value can be decimal. So it is double return type method
    public double averageScoreFor() {
        int size = table.length;
        int totalScoreFor = 0;
        for (int i = 0; i < size; i++) {
            totalScoreFor = totalScoreFor + table[i].getScoreFor();
        }
        double averageScoreFor = (double) totalScoreFor / size;
        return averageScoreFor;
    }

    // method process each game, update new result
    // and sort all data – mainly on premiership points and secondarily on percentage
    public void processGame(String t1, int g1, int b1, String t2, int g2, int b2) {
        // Calculation of points for home team and away team
        int pointsOfTeam1 = calculateGamePoints(g1, b1);
        int pointsOfTeam2 = calculateGamePoints(g2, b2);

        // Update details entry of home team t1 and Away team t2 in array of entry
        for (int i = 0; i < table.length; i++) {
            if (table[i].getTeam().equals(t1)){
                table[i].processResult(pointsOfTeam1, pointsOfTeam2);
            } else if (table[i].getTeam().equals(t2)) {
                table[i].processResult(pointsOfTeam2, pointsOfTeam1);
            }
        }

        // method called for sort Entry arrays after added of new entries
        table = pointsOrPercentageSelectionSort(table);
        this.table = updateEntryPosition(table);
    }

    // method to compare two Entry on the basis of mainly team points and secondly percentage of team
    private int compare(Entry e1, Entry e2) {
        // compare according to scored teams points
        if (e1.getPoints() > e2.getPoints()) return 1;
        else if (e1.getPoints() < e2.getPoints()) return -1;
        else { // compare according to scored teams equal points and on the basis of team scored percentage
            if (e1.getPercentage() > e2.getPercentage()) return 1;
            else if (e1.getPercentage() < e2.getPercentage()) return -1;
            else return 0;
        }
    }

    // method for sort Entry array elements using selection sort
    private Entry[] pointsOrPercentageSelectionSort(Entry[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int smallest = i;
            for (int index = i + 1; index < data.length; index++) {
                // compare two Entries and sort in descending order
                if(compare(data[i], data[index]) < 0) {
                    smallest = index;
                }
            }
            // swapping the smallest value into position
            Entry temp = data[i];
            data[i] = data[smallest];
            data[smallest] = temp;
        }
        return data;
    }

    // Method to update position of Team in the league table
    private Entry[] updateEntryPosition(Entry[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i].setPosition(i + 1);
        }
        return data;
    }

    // search index of specified team name from Entry table using linear search algorithm
    private int searchTeamUsingLinearSearch(Entry[] data, String key) {
        int index = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i].getTeam().equals(key)){
                index = i;
            }
        }
        return index;
    }

    private int calculateGamePoints(int goals, int behinds) {
        int scoredPoints = goals * POINTS_PER_GOAL + behinds;
        return scoredPoints;
    }
}