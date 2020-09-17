package org.james.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Commit {
    private String id = "";
    private String message = "";
    private String url = "";
    private Author author;
    private Committer committer;
    private String verification;
    private String timestamp;
    private List<String> added = new ArrayList<>();
    private List<String> removed = new ArrayList<>();
    private List<String> modified = new ArrayList<>();


}
