package org.james.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ContentObject {

    private String secret = "";
    private String ref = "";
    private String before = "";
    private String after = "";
    @JsonProperty("compare_url")
    private String compareUrl = "";
    private List<Commit> commits = new ArrayList<>();
    @JsonProperty("head_commit")
    private String headCommit;
    private Repository repository;
    private Pusher pusher;
    private Sender sender;

}
