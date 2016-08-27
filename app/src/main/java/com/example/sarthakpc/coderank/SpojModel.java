package com.example.sarthakpc.coderank;

/**
 * Created by Sarthakpc on 8/5/2016.
 */
public class SpojModel
{

    private long mInsert_id;
    private String mWorldRank;
    private int mProblemsSolved;
    private int mSolutionsSubmitted;


    public long getInsert_id() {
        return mInsert_id;
    }

    public void setInsert_id(long insert_id) {
        mInsert_id = insert_id;
    }

    public String getWorldRank() {
        return mWorldRank;
    }

    public void setWorldRank(String worldRank) {
        mWorldRank = worldRank;
    }

    public int getProblemsSolved() {
        return mProblemsSolved;
    }

    public void setProblemsSolved(int problemsSolved) {
        mProblemsSolved = problemsSolved;
    }

    public int getSolutionsSubmitted() {
        return mSolutionsSubmitted;
    }

    public void setSolutionsSubmitted(int solutionsSubmitted) {
        mSolutionsSubmitted = solutionsSubmitted;
    }
}
