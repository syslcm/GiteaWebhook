package org.james.persistance;

import oracle.jdbc.OraclePreparedStatement;
import org.james.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Component
@Service
public class IntoDb {

    private DataSource dataSource;
    private String giteaEvent;
    private String giteaDelivery;
    private JdbcTemplate jt;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.TAIWAN);

    public static void main(String[] args) {
        IntoDb intoDb = new IntoDb();
        intoDb.toDate("");
    }


    //"SMARTQUOTE"."ISEQ$$_67981".nextval
    public long insert(ContentObject contentObject) {
        long eventId = 0;
        jt = new JdbcTemplate(dataSource);
        OraclePreparedStatement ps0;
        try {
            // create a new Event record
            String generatedColumns[] = {"ID"};
            ps0 = (OraclePreparedStatement) jt.getDataSource().getConnection().prepareStatement("insert into git_event (ID,X_GITEA_EVENT,X_GITEA_DELIVERY) values (ISEQ$$_67981.NEXTVAL,?,?) ", generatedColumns); //Statement.RETURN_GENERATED_KEYS
            ps0.setNString(1, this.getGiteaEvent());
            ps0.setNString(2, this.getGiteaDelivery());
            ps0.executeUpdate();
            ResultSet rset = ps0.getGeneratedKeys();
            rset.next();
            eventId = rset.getLong(1);
            if (eventId > 0) {
                long repoId = insertRepository(eventId, contentObject.getRepository());
                for (Commit commit : contentObject.getCommits()){
                    insertCommit(eventId,commit);
                }
                jt.update("update git_event set REF=?,BEFORE=?,AFTER=?,COMPARE_URL=?,HEAD_COMMIT=?,REPO_ID=?,PUSHER_GITEA_ID=?,SENDER_GITEA_ID=?,CREATE_DATE=? where id=?",
                        new Object[]{contentObject.getRef(), contentObject.getBefore(),contentObject.getAfter(),
                                contentObject.getCompareUrl(),contentObject.getHeadCommit(),repoId,
                        insertPusher(contentObject),insertSender(contentObject),new Date(),eventId});

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            eventId = -2;
        }
        return eventId;

    }

    private long insertRepository(long eventId, Repository repository) {
        long repoId = 0;
        if (jt == null) jt = new JdbcTemplate(dataSource);
        String sql = "update GIT_REPOSITORY set GITEA_ID=?,OWNER_GIT_USER_ID=?,NAME=?,FULL_NAME=?,DESCRIPTION=?,EMPTY=?,PRIVATE=?,FORK=?,TEMPLATE=?,PARENT=?,MIRROR=?,GITEA_SIZE=?,HTML_URL=?,SSH_URL=?,CLONE_URL=?,ORIGINAL_URL=?,WEBSITE=?,STARS_COUNT=?,FORKS_COUNT=?,WATCHERS_COUNT=?,OPEN_ISSUES_COUNT=?,OPEN_PR_COUNTER=?,RELEASE_COUNTER=?,DEFAULT_BRANCH=?,ARCHIVED=?,CREATED_AT=?,UPDATED_AT=?,PERMISSION_ADMIN=?,PERMISSION_PUSH=?,PERMISSION_PULL=?,HAS_ISSUES=?,INTERNAL_TRACKER_TIME=?,INTERNAL_TRACKER_CONTRIB=?,INTERNAL_TRACKER_DEPEND=?,HAS_WIKI=?,HAS_PULL_REQUESTS=?,IGNORE_WHITESPACE=?,ALLOW_MERGE_COMMITS=?,ALLOW_REBASE=?,ALLOW_REBASE_EXPLICIT=?,ALLOW_SQUASH_MERGE=?,AVARTAR_URL=? where id=?";

        OraclePreparedStatement ps0;
        try {
            String generatedColumns[] = {"ID"};
            ps0 = (OraclePreparedStatement) jt.getDataSource().getConnection().prepareStatement("insert into GIT_REPOSITORY (ID,EVENT_ID) values (ISEQ$$_67948.NEXTVAL,?) ", generatedColumns);
            ps0.setLong(1, eventId);
            ps0.executeUpdate();
            ResultSet rset = ps0.getGeneratedKeys();
            rset.next();
            repoId = rset.getLong(1);
            if (repoId > 0) {
                Object[] vals = {repository.getId(), insertOwner(repository.getOwner()) , repository.getName(), repository.getFullName(), repository.getDescription(), bool2Int(repository.isEmpty()), bool2Int(repository.isPrivat()),
                        bool2Int(repository.isFork()), bool2Int(repository.isTemplate()), repository.getParent(), bool2Int(repository.isMirror()), repository.getSize(), repository.getHtmlUrl(), repository.getSshUrl(), repository.getCloneUrl(), repository.getOriginalUrl(), repository.getWebsite(), repository.getStarsCount(),
                        repository.getForksCount(), repository.getWatchersCount(), repository.getOpenIssueCcount(), repository.getOpenPrCounter(), repository.getReleaseCounter(), repository.getDefaultBranch(), bool2Int(repository.isArchived()),
                        toDate(repository.getCreatedAt()), toDate(repository.getUpdatedAt()), bool2Int(repository.getPermissions().isAdmin()),
                        bool2Int(repository.getPermissions().isPush()), bool2Int(repository.getPermissions().isPull()), bool2Int(repository.isHasIssues()),
                        bool2Int(repository.getInternalTracker().isEnableTimeTracker()), bool2Int(repository.getInternalTracker().isAllowOnlyContributorsToTrackTime()),
                        bool2Int(repository.getInternalTracker().isEnableIssueDependencies()), bool2Int(repository.isHasWiki()), bool2Int(repository.isHasPullRequests()),
                        bool2Int(repository.isIgnoreWhitespaceConflicts()), bool2Int(repository.isAllowMergeCommits()), bool2Int(repository.isAllowRebase()),
                        bool2Int(repository.isAllowRebaseExplicit()), bool2Int(repository.isAllowSquashMerge()), repository.getAvatarUrl(), repoId};
                int u = jt.update(sql, vals);
            } else {
                repoId = -1;
            }
            return repoId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            repoId = -2;
            return repoId;
        }

    }

    private void insertChanged(String tableName, long eventId, long commitId, String val){
        //GIT_ADDED
        if (jt == null) jt = new JdbcTemplate(dataSource);
        jt.update("insert into " + tableName + " (event_id,commit_id,STRING) values (?,?,?)",
                new Object[]{eventId, commitId, val});
    }

    private long insertCommit(long eventId, Commit commit){
        long commitId = 0;
        if (jt == null) jt = new JdbcTemplate(dataSource);
        String sql = "update GIT_COMMIT set GITEA_ID=?,MESSAGE=?,URL=?,AUTHOR_NAME=?,AUTHOR_EMAIL=?,AUTHOR_USERNAME=?,COMMITTER_NAME=?,COMMITTER_EMAIL=?,COMMITTER_USERNAME=?,VERIFICATION=?,\"TIMESTAMP\"=? where id=?";

        OraclePreparedStatement ps0;
        try {
            String generatedColumns[] = {"ID"};
            ps0 = (OraclePreparedStatement) jt.getDataSource().getConnection().prepareStatement("insert into GIT_COMMIT (ID,EVENT_ID) values (ISEQ$$_67951.NEXTVAL,?) ", generatedColumns);
            ps0.setLong(1, eventId);
            ps0.executeUpdate();
            ResultSet rset = ps0.getGeneratedKeys();
            rset.next();
            commitId = rset.getLong(1);
            if (commitId > 0) {
                Object[] vals = {commit.getId(),commit.getMessage(),commit.getUrl(),
                        commit.getAuthor().getName(), commit.getAuthor().getEmail(), commit.getAuthor().getUsername(),
                        commit.getCommitter().getName(),commit.getCommitter().getEmail(),commit.getCommitter().getUsername(),
                        commit.getVerification(),toDate(commit.getTimestamp()),commitId};
                int u = jt.update(sql, vals);
                if (u > 0){
                    if (commit.getAdded() != null) {
                        for (String addedVal : commit.getAdded()) {
                            insertChanged("GIT_ADDED", eventId, commitId, addedVal);
                        }
                    }
                    if (commit.getRemoved() != null) {
                        for (String removedVal : commit.getRemoved()) {
                            insertChanged("GIT_REMOVED", eventId, commitId, removedVal);
                        }
                    }
                    if (commit.getModified() != null) {
                        for (String modifiedVal : commit.getModified()) {
                            insertChanged("GIT_MODIFIED", eventId, commitId, modifiedVal);
                        }
                    }
                }
            } else {
                commitId = -1;
            }
            return commitId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            commitId = -2;
            return commitId;
        }
    }

    private int insertPusher(ContentObject contentObject) {
        if (jt == null) jt = new JdbcTemplate(dataSource);
        int pusherId = contentObject.getPusher().getId();
        List giteaUserList = jt.queryForList("select * from GIT_USER where GITEA_ID = ?",
                new Object[]{new Integer(pusherId)});
        if (giteaUserList.size() > 0) {
            return pusherId;
        } else {
            Pusher pusher = contentObject.getPusher();
            int c = jt.update("insert into git_user (GITEA_ID,LOGIN,FULL_NAME,EMAIL,\"LANGUAGE\",IS_ADMIN,LAST_LOGIN,CREATED,USERNAME) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{pusher.getId(), pusher.getLogin(), pusher.getFullName(),
                            pusher.getEmail(), pusher.getLanguage(), bool2Int(pusher.isAdmin()),
                            toDate(pusher.getLastLogin()), toDate(pusher.getCreated()), pusher.getUsername()});
        }
        return pusherId;

    }

    private int insertSender(ContentObject contentObject) {
        if (jt == null) jt = new JdbcTemplate(dataSource);
        int senderId = contentObject.getSender().getId();
        List giteaUserList = jt.queryForList("select * from GIT_USER where GITEA_ID = ?",
                new Object[]{new Integer(senderId)});
        if (giteaUserList.size() > 0) {
            return senderId;
        } else {
            Sender sender = contentObject.getSender();
            int c = jt.update("insert into git_user (GITEA_ID,LOGIN,FULL_NAME,EMAIL,\"LANGUAGE\",IS_ADMIN,LAST_LOGIN,CREATED,USERNAME) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{sender.getId(), sender.getLogin(), sender.getFullName(),
                            sender.getEmail(), sender.getLanguage(), bool2Int(sender.isAdmin()),
                            toDate(sender.getLastLogin()), toDate(sender.getCreated()), sender.getUsername()});
        }
        return senderId;

    }

    private int insertOwner(Owner owner) {
        if (jt == null) jt = new JdbcTemplate(dataSource);
        int ownerId = owner.getId();
        List giteaUserList = jt.queryForList("select * from GIT_USER where GITEA_ID = ?",
                new Object[]{new Integer(ownerId)});
        if (giteaUserList.size() > 0) {
            return ownerId;
        } else {
            int c = jt.update("insert into git_user (GITEA_ID,LOGIN,FULL_NAME,EMAIL,\"LANGUAGE\",IS_ADMIN,LAST_LOGIN,CREATED,USERNAME) VALUES (?,?,?,?,?,?,?,?,?)",
                    new Object[]{owner.getId(), owner.getLogin(), owner.getFullName(),
                            owner.getEmail(), owner.getLanguage(), bool2Int(owner.isAdmin()),
                            toDate(owner.getLastLogin()), toDate(owner.getCreated()), owner.getUsername()});
        }
        return ownerId;

    }

    public Date toDate(String in) {
//        String in = "2020-09-16T15:41:33+08:00";
        Date d = null;
        try {
//            String s = df.format(new Date());
//            System.out.println(s);
            d = df.parse(in);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private int bool2Int(boolean boo) {
        return boo ? 1 : 0;
    }

    public String insert2(ContentObject contentObject) {
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        String newid = "0";
        Statement sta = null;
        String sql = "insert into git_event (ID,CREATE_DATE) values (SEQ01.NEXTVAL,sysdate)";
        ResultSet resultSet = null;
        try {
            sta = jt.getDataSource().getConnection().createStatement();

            sta.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            resultSet = sta.getGeneratedKeys();
            if (resultSet.next()) {
                newid = resultSet.getObject(Statement.RETURN_GENERATED_KEYS).toString();
            } else {
                System.out.println("No RETURN_GENERATED_KEYS ");
            }
            if (sta != null) sta.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newid;

    }


    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getGiteaEvent() {
        return giteaEvent;
    }

    public void setGiteaEvent(String giteaEvent) {
        this.giteaEvent = giteaEvent;
    }

    public String getGiteaDelivery() {
        return giteaDelivery;
    }

    public void setGiteaDelivery(String giteaDelivery) {
        this.giteaDelivery = giteaDelivery;
    }
}
