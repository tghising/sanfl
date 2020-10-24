
package sanfl;

/**
 *
 * @author tghising
 */
public class Entry {

    // Premiership points in the game
    final int WINNER_PREMIERSHIP_POINTS = 2;
    final int DRAWN_PREMIERSHIP_POINTS = 1;
    final int LOOSER_PREMIERSHIP_POINTS = 0;

    // declarations of private member variables 
    private int position;
    private String team;
    private int played;
    private int points;
    private double percentage;
    private int won;
    private int lost;
    private int drawn;
    private int scoreFor;
    private int scoreAgainst;

    public Entry() {
    }

    // getter methods

    public int getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public int getPlayed() {
        return played;
    }

    public int getPoints() {
        return points;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getScoreFor() {
        return scoreFor;
    }

    public int getScoreAgainst() {
        return scoreAgainst;
    }

    // setter methods

    public void setPosition(int pos) {
        this.position = pos;
    }

    public void setTeam(String t) {
        this.team = t;
    }

    public void setPlayed(int point) {
        this.played = point;
    }

    // setter method for calculation of total points gained by team on the basis of total number of won, loose, and drawn games
    public void setPoints() {
        this.points = this.won * WINNER_PREMIERSHIP_POINTS + this.drawn * DRAWN_PREMIERSHIP_POINTS + LOOSER_PREMIERSHIP_POINTS;
    }
    // setter method for calculation of percentage gained by team on the basis of total number of score for and score against
    public void setPercentage() {
        double sf = this.scoreFor;
        double sa = this.scoreAgainst;
        this.percentage = (sf / (sf + sa)) * 100;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setScoreFor(int scoreFor) {
        this.scoreFor = scoreFor;
    }

    public void setScoreAgainst(int scoreAgainst) {
        this.scoreAgainst = scoreAgainst;
    }

    public void processResult(int forPoints, int againstPoints) {
        /* Game can:
        Won by Team: if score for is greater than score against, or
        Lost by Team: if score for is less than score against, or
        Drawn game : if score for is equals to score against
        */
        if (forPoints > againstPoints) {
            setWon(getWon() + 1);
        } else if( forPoints < againstPoints) {
            setLost(getLost() + 1);
        } else {
            setDrawn(getDrawn() + 1);
        }
        setPlayed(getPlayed() + 1);
        setScoreFor(getScoreFor() + forPoints);
        setScoreAgainst(getScoreAgainst() + againstPoints);
        setPoints();
        setPercentage();
    }
}
