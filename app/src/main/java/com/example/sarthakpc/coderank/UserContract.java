package com.example.sarthakpc.coderank;

import android.provider.BaseColumns;

/**
 * Created by Sarthakpc on 8/1/2016.
 */
public class UserContract
{
    //Users Table class
    public static final class UserEntry implements BaseColumns
    {
        public static final String TABLE_NAME="users";
        public static final String FRIEND_NAME="friend_name";
        public static final String CODECHEF_HANDLE="codechef";
        public static final String CODEFORCES_HANDLE="codeforces";
        public static final String HACKERRANK_HANDLE="hackerrank";
        public static final String HACKEREARTH_HANDLE="hackerearth";
        public static final String SPOJ_HANDLE="spoj";

    }

    //DetailUserActivity Codechef class
    public static final class CodechefEntry implements BaseColumns
    {
        public static final String TABLE_NAME="codechef_data";
        public static final String USER_ID="user_id";
        public static final String PROFILE_NAME="handle";
        public static final String PROBLEMS_SOLVED="problems_solved";
        public static final String LONG_GLOBAL_RANK="long_global_rank";
        public static final String LONG_COUNTRY_RANK="long_country_rank";
        public static final String LONG_RATING="long_rating";
        public static final String SHORT_GLOBAL_RANK="short_global_rank";
        public static final String SHORT_COUNTRY_RANK="short_country_rank";
        public static final String SHORT_RATING="short_rating";
        public static final String LTIME_GLOABAL_RANK="ltime_global_rank";
        public static final String LTIME_COUNTRY_RANK="ltime_country_rank";
        public static final String LTIME_RATING="ltime_rating";
    }

    //DetailUserActivity CodeforcesEntry
    public static final class CodeforcesEntry implements BaseColumns
    {
        public static final String TABLE_NAME="codeforces_data";
        public static final String USER_ID="user_id";
        public static final String PROFILE_NAME="handle";
        public static final String OLD_RATING="old_rating";
        public static final String CURRENT_RATING="current_rating";
        public static final String MAX_RATING="max_rating";
        public static final String RECENT_RATING_CHANGE="recent_rating_change";
        public static final String RECENT_COMP_GIVEN="recent_comp_given";
        public static final String RANK="rank";
        public static final String MAX_RANK="max_rank";
    }

    //DetailUserActivity SpojClass
    public static final class SpojEntry implements BaseColumns
    {
        public static final String TABLE_NAME="spoj_data";
        public static final String USER_ID="user_id";
        public static final String PROFILE_NAME="handle";
        public static final String WORLD_RANK="world_rank";
        public static final String PROBLEMS_SOLVED="problems_solved";
        public static final String SOLUTIONS_SUBMITTED="solutions_submitted";
    }

}
