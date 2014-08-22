package com.example.pack;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gagandeep on 19/8/14.
 */
public class GenerateApk extends DefaultTask {


    private CustomExtension mCurrentBuildConfig;

    public GenerateApk() {
        
    }

    @TaskAction
    public void start() {
        try {;
            Project project = getProject();
            ProjectSpecifics projectSpecifics = new ProjectSpecifics(project);
            String apkFile = projectSpecifics.generateApk();
            mCurrentBuildConfig = projectSpecifics.getCurrentBuildConfig();
            String finalApkFile = generateFinalApk(projectSpecifics,apkFile);
            String commitMessage = "Hi All,\n\nSuccesfully generated apk in build system\n" +
                    "\n" +
                    "Thanks\n" +
                    "Android Apk Build System";

            if(mCurrentBuildConfig.isCommitToSVN()) {
                SVNUtils svnUtils = SVNUtils.getInstance(mCurrentBuildConfig.getApkRepoUrl());
                svnUtils.initSession(mCurrentBuildConfig.getSvnUsername(), mCurrentBuildConfig.getSvnPassword());
                commitMessage = svnUtils.commitApkFile(finalApkFile, "Apk file getting commited by Android Build System");
            } else {
                System.out.println("Skipping committing in svn..");
            }

            if(mCurrentBuildConfig.isSendEmail()) {
                EmailUtils.getInstance().initSession(mCurrentBuildConfig.getEmailHost(),mCurrentBuildConfig.getEmailPort(),mCurrentBuildConfig.getEmailUsername(),mCurrentBuildConfig.getEmailPassword());
                if(mCurrentBuildConfig.isEmailAttachment()) {
                    EmailUtils.getInstance().sendEmailWithAttachment(mCurrentBuildConfig.getFromEmailAddress(), mCurrentBuildConfig.getToEmailAddresses(),mCurrentBuildConfig.getCcEmailAddresses(),mCurrentBuildConfig.getBccEmailAddress(), mCurrentBuildConfig.getEmailSubject(), commitMessage,finalApkFile);
                } else {
                    EmailUtils.getInstance().sendEmailWithoutAttachment(mCurrentBuildConfig.getFromEmailAddress(), mCurrentBuildConfig.getToEmailAddresses(),mCurrentBuildConfig.getCcEmailAddresses(),mCurrentBuildConfig.getBccEmailAddress(), mCurrentBuildConfig.getEmailSubject(), commitMessage);
                }
            } else {
                System.out.println("Skipping sending email....");
                System.out.println(commitMessage);
            }
            if(mCurrentBuildConfig.isCommitToSVN() || mCurrentBuildConfig.isSendEmail()) {
                clearOutputDir(projectSpecifics);
            } else {
                System.out.println("Final apk generated at path : \n"+finalApkFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateFinalApk(ProjectSpecifics projectSpecifics,String apkPath) throws Exception {
        String finalApkPath = null;
        File apkFile = new File(apkPath);
        if(!apkFile.exists()) {
            throw new Exception("File ["+apkPath+"] was not generated, aborting task...");
        }
        finalApkPath = projectSpecifics.getmOutputApkDirPath()+File.separator+mCurrentBuildConfig.getFinalApkName()+(new SimpleDateFormat("yyyyMMddhhmm").format(new Date()))+".apk";
        File finalApkFile = new File(finalApkPath);
        if(apkFile.renameTo(finalApkFile)) {
            return finalApkPath;
        }
        return null;
    }

    private void clearOutputDir(ProjectSpecifics projectSpecifics) {
        System.out.println("Clearing apk output directory");
        File apkOutputDir = new File(projectSpecifics.getmOutputApkDirPath());
        if(apkOutputDir.exists()) {
            File[] files = apkOutputDir.listFiles();
            for (File file:files) {
                if(file.delete()) {

                }
            }
        }
        System.out.println("Clearing apk output directory Done....");
    }

}
