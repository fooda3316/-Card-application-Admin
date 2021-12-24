package com.example.adminapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Results {
      private List<UnfinishedRQ> unfinishedRQS;
      private List<Admin> admins;
      private List<User> users;
      private List<Card> cards;
      private List<AdminCard> adminCards;
      private List<Request> requests;
    private List<Branch> branches;


    public Results() {

    }
    public List<Branch> getBranches() {
        return branches;
    }
    public List<Admin> getAdmins() {
        return admins;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<AdminCard> getAdminCards() {
        return adminCards;
    }

    public List<UnfinishedRQ> getUnfinishedRQS() {
        return unfinishedRQS;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
