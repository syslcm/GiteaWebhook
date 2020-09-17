package org.james.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InternalTracker {

    @JsonProperty("enable_time_tracker")
    private boolean enableTimeTracker;
    @JsonProperty("allow_only_contributors_to_track_time")
    private boolean allowOnlyContributorsToTrackTime;
    @JsonProperty("enable_issue_dependencies")
    private boolean enableIssueDependencies;


    public boolean isEnableTimeTracker() {
        return enableTimeTracker;
    }

    public void setEnableTimeTracker(boolean enableTimeTracker) {
        this.enableTimeTracker = enableTimeTracker;
    }

    public boolean isAllowOnlyContributorsToTrackTime() {
        return allowOnlyContributorsToTrackTime;
    }

    public void setAllowOnlyContributorsToTrackTime(boolean allowOnlyContributorsToTrackTime) {
        this.allowOnlyContributorsToTrackTime = allowOnlyContributorsToTrackTime;
    }

    public boolean isEnableIssueDependencies() {
        return enableIssueDependencies;
    }

    public void setEnableIssueDependencies(boolean enableIssueDependencies) {
        this.enableIssueDependencies = enableIssueDependencies;
    }
}
