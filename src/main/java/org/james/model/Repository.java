package org.james.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    @JsonIgnore
    private long dbid;
    private int id;
    private Owner owner;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private String description = "";
    private boolean empty;
    @JsonProperty("private")
    private boolean privat;
    private boolean fork;
    private boolean template;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parent = "";
    private boolean mirror;
    private int size;
    @JsonProperty("html_url")
    private String htmlUrl = "";
    @JsonProperty("ssh_url")
    private String sshUrl = "";
    @JsonProperty("clone_url")
    private String cloneUrl = "";
    @JsonProperty("original_url")
    private String originalUrl = "";
    private String website = "";
    @JsonProperty("stars_count")
    private int starsCount;
    @JsonProperty("forks_count")
    private int forksCount;
    @JsonProperty("watchers_count")
    private int watchersCount;
    @JsonProperty("open_issues_count")
    private int openIssueCcount;
    @JsonProperty("open_pr_counter")
    private int openPrCounter;
    @JsonProperty("release_counter")
    private int releaseCounter;
    @JsonProperty("default_branch")
    private String defaultBranch = "";
    private boolean archived;
    @JsonProperty("created_at")
    private String createdAt = "";
    @JsonProperty("updated_at")
    private String updatedAt = "";
    private Permissions permissions;
    @JsonProperty("has_issues")
    private boolean hasIssues;
    @JsonProperty("internal_tracker")
    private InternalTracker internalTracker;
    @JsonProperty("has_wiki")
    private boolean hasWiki;
    @JsonProperty("has_pull_requests")
    private boolean hasPullRequests;
    @JsonProperty("ignore_whitespace_conflicts")
    private boolean ignoreWhitespaceConflicts;
    @JsonProperty("allow_merge_commits")
    private boolean allowMergeCommits;
    @JsonProperty("allow_rebase")
    private boolean allowRebase;
    @JsonProperty("allow_rebase_explicit")
    private boolean allowRebaseExplicit;
    @JsonProperty("allow_squash_merge")
    private boolean allowSquashMerge;
    @JsonProperty("avatar_url")
    private String avatarUrl = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isPrivat() {
        return privat;
    }

    public void setPrivat(boolean privat) {
        this.privat = privat;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public void setSshUrl(String sshUrl) {
        this.sshUrl = sshUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public int getOpenIssueCcount() {
        return openIssueCcount;
    }

    public void setOpenIssueCcount(int openIssueCcount) {
        this.openIssueCcount = openIssueCcount;
    }

    public int getOpenPrCounter() {
        return openPrCounter;
    }

    public void setOpenPrCounter(int openPrCounter) {
        this.openPrCounter = openPrCounter;
    }

    public int getReleaseCounter() {
        return releaseCounter;
    }

    public void setReleaseCounter(int releaseCounter) {
        this.releaseCounter = releaseCounter;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public boolean isHasIssues() {
        return hasIssues;
    }

    public void setHasIssues(boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    public InternalTracker getInternalTracker() {
        return internalTracker;
    }

    public void setInternalTracker(InternalTracker internalTracker) {
        this.internalTracker = internalTracker;
    }

    public boolean isHasWiki() {
        return hasWiki;
    }

    public void setHasWiki(boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

    public boolean isHasPullRequests() {
        return hasPullRequests;
    }

    public void setHasPullRequests(boolean hasPullRequests) {
        this.hasPullRequests = hasPullRequests;
    }

    public boolean isIgnoreWhitespaceConflicts() {
        return ignoreWhitespaceConflicts;
    }

    public void setIgnoreWhitespaceConflicts(boolean ignoreWhitespaceConflicts) {
        this.ignoreWhitespaceConflicts = ignoreWhitespaceConflicts;
    }

    public boolean isAllowMergeCommits() {
        return allowMergeCommits;
    }

    public void setAllowMergeCommits(boolean allowMergeCommits) {
        this.allowMergeCommits = allowMergeCommits;
    }

    public boolean isAllowRebase() {
        return allowRebase;
    }

    public void setAllowRebase(boolean allowRebase) {
        this.allowRebase = allowRebase;
    }

    public boolean isAllowRebaseExplicit() {
        return allowRebaseExplicit;
    }

    public void setAllowRebaseExplicit(boolean allowRebaseExplicit) {
        this.allowRebaseExplicit = allowRebaseExplicit;
    }

    public boolean isAllowSquashMerge() {
        return allowSquashMerge;
    }

    public void setAllowSquashMerge(boolean allowSquashMerge) {
        this.allowSquashMerge = allowSquashMerge;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }
}
