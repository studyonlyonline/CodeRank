package com.example.sarthakpc.coderank;

/**
 * Created by Sarthakpc on 8/1/2016.
 */
public class CodechefModel
{
    private long mInsert_id;
    private String mUsername;
    private int mProblem_solved;
    private int mLongGLobalRank,mLongCountryRank,mShortGlobalRank,mShortCountryRank,mLunchtimeGlobalRank,mLunchtimeCountryRank;
    private String mLongRating,mShortRating,mLunchtimeRating;

    public long getInsert_id() {
        return mInsert_id;
    }

    public void setInsert_id(long insert_id) {
        mInsert_id = insert_id;
    }


    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public int getProblem_solved() {
        return mProblem_solved;
    }

    public void setProblem_solved(int problem_solved) {
        mProblem_solved = problem_solved;
    }

    public int getLongGLobalRank() {
        return mLongGLobalRank;
    }

    public void setLongGLobalRank(int longGLobalRank) {
        mLongGLobalRank = longGLobalRank;
    }

    public int getLongCountryRank() {
        return mLongCountryRank;
    }

    public void setLongCountryRank(int longCountryRank) {
        mLongCountryRank = longCountryRank;
    }

    public int getShortGlobalRank() {
        return mShortGlobalRank;
    }

    public void setShortGlobalRank(int shortGlobalRank) {
        mShortGlobalRank = shortGlobalRank;
    }

    public int getShortCountryRank() {
        return mShortCountryRank;
    }

    public void setShortCountryRank(int shortCountryRank) {
        mShortCountryRank = shortCountryRank;
    }

    public int getLunchtimeGlobalRank() {
        return mLunchtimeGlobalRank;
    }

    public void setLunchtimeGlobalRank(int lunchtimeGlobalRank) {
        mLunchtimeGlobalRank = lunchtimeGlobalRank;
    }

    public int getLunchtimeCountryRank() {
        return mLunchtimeCountryRank;
    }

    public void setLunchtimeCountryRank(int lunchtimeCountryRank) {
        mLunchtimeCountryRank = lunchtimeCountryRank;
    }

    public String getLongRating() {
        return mLongRating;
    }

    public void setLongRating(String longRating) {
        mLongRating = longRating;
    }

    public String getShortRating() {
        return mShortRating;
    }

    public void setShortRating(String shortRating) {
        mShortRating = shortRating;
    }

    public String getLunchtimeRating() {
        return mLunchtimeRating;
    }

    public void setLunchtimeRating(String lunchtimeRating) {
        mLunchtimeRating = lunchtimeRating;
    }

    public String toString()
    {
        return "a";
    }
}
