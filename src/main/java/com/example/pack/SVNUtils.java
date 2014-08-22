package com.example.pack;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Created by gagandeep on 19/8/14.
 */
public class SVNUtils {

    private static SVNUtils mInstance;
    private SVNURL mUrl;
    private SVNClientManager mClientManager;
    private SVNCommitClient mCommitClient;
    private ISVNAuthenticationManager mAuthenticationManager;
    private SVNRepository mRepository;
    private String mRepoUrl;

    private SVNUtils(String repoUrl) {
        try {
            FSRepositoryFactory.setup();
            mRepoUrl = repoUrl;
            mUrl = SVNURL.parseURIEncoded(repoUrl);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    public static SVNUtils getInstance(String repoUrl) {
        if (mInstance == null) {
            mInstance = new SVNUtils(repoUrl);
        }
        return mInstance;
    }

    public void initSession(String username, String password) {
        mAuthenticationManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        try {
            mRepository = SVNRepositoryFactory.create(mUrl);
            mRepository.setAuthenticationManager(mAuthenticationManager);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        mClientManager = SVNClientManager.newInstance();
        mCommitClient = mClientManager.getCommitClient();
    }

    public String commitApkFile(String apkFilePath, String comment) {
        try {
            long latestRevision = mRepository.getLatestRevision();
            ISVNEditor editor = mRepository.getCommitEditor( "Apk file commited by automatic build generation system" , null );
            SVNCommitInfo commitInfo = addFile( editor ,apkFilePath ,  comment.getBytes());
            return prepareAfterCommitMessage(new File(apkFilePath).getName());
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static SVNCommitInfo addFile(ISVNEditor editor, String filePath, byte[] data) throws SVNException {
        editor.openRoot(-1);
        editor.addFile(filePath, null, -1);
        editor.applyTextDelta(filePath, null);
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        String checksum = deltaGenerator.sendDelta(filePath, new ByteArrayInputStream(data), editor, true);
        editor.closeFile(filePath, checksum);
        editor.closeDir();
        return editor.closeEdit();
    }

    public String prepareAfterCommitMessage(String apkFileName) {
        StringBuffer message = new StringBuffer();
        message.append("Hi all,\n\nPlease download and verify the build with latest changes from the following path :\n\n"+mRepoUrl+"/"+apkFileName+"\n\nIf you are not able to download from this path then please find attached apk in email\n\nThanks\nAndroid Apk Build System");
        return message.toString();
    }
}
