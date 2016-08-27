package com.example.sarthakpc.coderank;

/**
 * Created by Sarthakpc on 8/5/2016.
 */
public class CodeforcesModel
{
    private long mInsert_id;

    private int currentRating;
    private int maxRating;
    private int recentRatingChange;

    private int oldRating;
    private String recentCompGiven;
    private String rank;
    private String maxRank;

    public long getInsert_id() {
        return mInsert_id;
    }

    public void setInsert_id(long insert_id) {
        mInsert_id = insert_id;
    }

    public int getOldRating() {
        return oldRating;
    }

    public void setOldRating(int oldRating) {
        this.oldRating = oldRating;
    }


    public String getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }


    public int getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(int currentRating) {
        this.currentRating = currentRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }

    public int getRecentRatingChange() {
        return recentRatingChange;
    }

    public void setRecentRatingChange(int recentRatingChange) {
        this.recentRatingChange = recentRatingChange;
    }

    public String getRecentCompGiven() {
        return recentCompGiven;
    }

    public void setRecentCompGiven(String recentCompGiven) {
        this.recentCompGiven = recentCompGiven;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
