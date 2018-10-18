package com.example.abhishek.newsapp.models;

import java.util.ArrayList;

public class SourceResponseWrapper {
    private String status;
    private ArrayList<Source> sources;

    /**
     * @param status  If the request was successful or not. Options: ok, error.
     * @param sources The results of the request.
     */
    public SourceResponseWrapper(String status, ArrayList<Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }
}
