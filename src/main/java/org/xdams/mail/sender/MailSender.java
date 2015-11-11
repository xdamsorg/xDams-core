package org.xdams.mail.sender;

import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MailSender extends JavaMailSenderImpl {

    String[] adminTo;
    String acceptURL;
    String mailFrom;
    String loginURL;   

    public String getAcceptURL() {
        return acceptURL;
    }

    public void setAcceptURL(String acceptURL) {
        this.acceptURL = acceptURL;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

    public String[] getAdminTo() {
        return adminTo;
    }

    public void setAdminTo(String[] adminTo) {
        this.adminTo = adminTo;
    }

}