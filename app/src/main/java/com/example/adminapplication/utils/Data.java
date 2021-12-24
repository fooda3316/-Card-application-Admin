package com.example.adminapplication.utils;




import com.example.adminapplication.model.Home;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<String> branch=new ArrayList<>();
    public List<String> titles=new ArrayList<>();
    private List<String> homeList=new ArrayList<>();
    private List<Home> mainList=new ArrayList<>();

    public List<String> getTitles() {
        titles.add("1");
        titles.add("2");
        titles.add("3");
        titles.add("4");
        titles.add("5");
        titles.add("6");
        titles.add("8");
        titles.add("10");
        titles.add("12");
        titles.add("15");
        titles.add("20");
        titles.add("25");
        titles.add("26");
        titles.add("30");
        titles.add("35");
        titles.add("50");
        titles.add("59");
        titles.add("60");
        titles.add("75");
        titles.add("99");
        titles.add("100");
        titles.add("60uc");
        titles.add("325uc");
        titles.add("660uc");
        titles.add("1800uc");
        titles.add("8100uc");
        titles.add("210");
        titles.add("530");
        titles.add("1080");
        titles.add("2200");

       titles.add("1 Month");
        titles.add("3 Months");
        titles.add("6 Months");
        titles.add("9 Months");
        titles.add("12 Months");

        titles.add("3 Months plus");
//        titles.add("");
//        titles.add("");



        return titles;
    }

    public List<String> getBranch() {
        branch.add("a");
        branch.add("usa");
        branch.add("uk");
        branch.add("cd");
        branch.add("ksa");
        branch.add("uae");
        return branch;
    }

    public List<String> getHomeList() {
        homeList.add("google");
        homeList.add("itunes");
        homeList.add("playstation");
        homeList.add("freefire");
        homeList.add("razer");
        homeList.add("gamestop");
        homeList.add("starzplay");
        homeList.add("steam");
        homeList.add("xbox");
        homeList.add("amizon");
        homeList.add("netflix");
        homeList.add("ebay");
        homeList.add("osn");
        homeList.add("spotify");
        homeList.add("mobilelegend");
        homeList.add("pubg");
        homeList.add("fortnit");
        homeList.add("minecraft");
        homeList.add("leaguelegends");
        homeList.add("imvu");

    return homeList;
    }



    public List<Home> mainList() {
        mainList.add(new Home("google", true));
        mainList.add(new Home("itunes", true));
        mainList.add(new Home("pubg", false));
        mainList.add(new Home("freefire", false));
        mainList.add(new Home("razer", false));
        mainList.add(new Home("playstation", false));
        mainList.add(new Home("gamestop", false));
        mainList.add(new Home("steam", true));
        mainList.add(new Home("starzplay", false));
        mainList.add(new Home("xbox", true));
        mainList.add(new Home("amizon", false));
        mainList.add(new Home("netflix", false));
        mainList.add(new Home("ebay", false));
        mainList.add(new Home("spotify", false));
        mainList.add(new Home("osn", false));
        mainList.add(new Home("mobilelegend", false));
        mainList.add(new Home("fortnit", false));
        mainList.add(new Home("minecraft", false));
        mainList.add(new Home("imvu", false));
        mainList.add(new Home("hulu", false));

//


        return mainList;
    }

}
