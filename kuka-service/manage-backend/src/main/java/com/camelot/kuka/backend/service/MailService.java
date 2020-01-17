package com.camelot.kuka.backend.service;

import com.camelot.kuka.model.common.Page;
import com.camelot.kuka.model.mail.Mail;

import java.util.Map;

public interface MailService {

    void saveMail(Mail mail);

    void updateMail(Mail mail);

    void sendMail(Mail mail);

    Mail findById(Long id);

    Page<Mail> findMails(Map<String, Object> params);
}
