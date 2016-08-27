package com.example.sarthakpc.coderank;

import java.io.Serializable;

/**
 * Created by Sarthakpc on 8/2/2016.
 */
public class UserModel implements Serializable
{
    private static final long serialVersionUID=100;
    private long mId;
    private String mFriendName;
    private String mCodechefHandle;
    private String mCodeforcesHandle;
    private String mSpojHandle;
    private String mHackerRankHandle;
    private String mHackerEarthHandle;

    public String getFriendName() {
        return mFriendName;
    }

    public void setFriendName(String friendName) {
        mFriendName = friendName;
    }

    public long getId() {

        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getCodechefHandle() {
        return mCodechefHandle;
    }

    public void setCodechefHandle(String codechefHandle) {
        mCodechefHandle = codechefHandle;
    }

    public String getCodeforcesHandle() {
        return mCodeforcesHandle;
    }

    public void setCodeforcesHandle(String codeforcesHandle) {
        mCodeforcesHandle = codeforcesHandle;
    }

    public String getSpojHandle() {
        return mSpojHandle;
    }

    public void setSpojHandle(String spojHandle) {
        mSpojHandle = spojHandle;
    }

    public String getHackerRankHandle() {
        return mHackerRankHandle;
    }

    public void setHackerRankHandle(String hackerRankHandle) {
        mHackerRankHandle = hackerRankHandle;
    }

    public String getHackerEarthHandle() {
        return mHackerEarthHandle;
    }

    public void setHackerEarthHandle(String hackerEarthHandle) {
        mHackerEarthHandle = hackerEarthHandle;
    }

    public String toString()
    {
        return getFriendName();
    }
}
