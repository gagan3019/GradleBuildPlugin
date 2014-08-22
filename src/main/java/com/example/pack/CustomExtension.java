package com.example.pack;


/**
 * Created by gagandeep on 20/8/14.
 */
public class CustomExtension {


    public boolean sendEmail;
    public boolean commitToSVN;
    public boolean emailAttachment;
    public String emailHost;
    public String emailPort;
    public String fromEmailAddress;
    public String emailUsername;
    public String emailPassword;
    public String toEmailAddresses;
    public String ccEmailAddresses;
    public String bccEmailAddress;
    public String emailSubject;
    public String finalApkName;
    public String apkRepoUrl;
    public String svnUsername;
    public String svnPassword;

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public boolean isCommitToSVN() {
        return commitToSVN;
    }

    public void setCommitToSVN(boolean commitToSVN) {
        this.commitToSVN = commitToSVN;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getToEmailAddresses() {
        return toEmailAddresses;
    }

    public void setToEmailAddresses(String toEmailAddresses) {
        this.toEmailAddresses = toEmailAddresses;
    }

    public String getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses(String ccEmailAddresses) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getFinalApkName() {
        return finalApkName;
    }

    public void setFinalApkName(String finalApkName) {
        this.finalApkName = finalApkName;
    }

    public String getApkRepoUrl() {
        return apkRepoUrl;
    }

    public void setApkRepoUrl(String apkRepoUrl) {
        this.apkRepoUrl = apkRepoUrl;
    }

    public String getSvnUsername() {
        return svnUsername;
    }

    public void setSvnUsername(String svnUsername) {
        this.svnUsername = svnUsername;
    }

    public String getSvnPassword() {
        return svnPassword;
    }

    public void setSvnPassword(String svnPassword) {
        this.svnPassword = svnPassword;
    }

    public String getBccEmailAddress() {
        return bccEmailAddress;
    }

    public void setBccEmailAddress(String bccEmailAddress) {
        this.bccEmailAddress = bccEmailAddress;
    }

    public boolean isEmailAttachment() {
        return emailAttachment;
    }

    public void setEmailAttachment(boolean emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    @Override
    public String toString() {
        return "CustomExtension{" +
                "sendEmail=" + sendEmail +
                ", commitToSVN=" + commitToSVN +
                ", emailAttachment=" + emailAttachment +
                ", emailHost='" + emailHost + '\'' +
                ", emailPort='" + emailPort + '\'' +
                ", fromEmailAddress='" + fromEmailAddress + '\'' +
                ", emailUsername='" + emailUsername + '\'' +
                ", emailPassword='" + emailPassword + '\'' +
                ", toEmailAddresses='" + toEmailAddresses + '\'' +
                ", ccEmailAddresses='" + ccEmailAddresses + '\'' +
                ", bccEmailAddress='" + bccEmailAddress + '\'' +
                ", emailSubject='" + emailSubject + '\'' +
                ", finalApkName='" + finalApkName + '\'' +
                ", apkRepoUrl='" + apkRepoUrl + '\'' +
                ", svnUsername='" + svnUsername + '\'' +
                ", svnPassword='" + svnPassword + '\'' +
                '}';
    }
}
