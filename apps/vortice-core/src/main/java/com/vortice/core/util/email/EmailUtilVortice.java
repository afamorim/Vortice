package com.vortice.core.util.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.vortice.core.exception.AplicacaoException;

public class EmailUtilVortice {

    public EmailUtilVortice(){}
    
    private static Logger LOG = Logger.getLogger(EmailUtilVortice.class);
    

    public static void enviarEmailComAutenticacao(String smtphost, String username, 
                                                  String password, String subject, String from, String fromText, 
                                                  String to, String text) throws AplicacaoException {
    	try{
            String mailer = "JavaMail API";
            Properties props = System.getProperties();
            props.put("mail.smtp.auth", "true");
           

            Session session = Session.getDefaultInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, fromText));
            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to, false));
            System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
            msg.setText(text);
            msg.setHeader("X-Mailer", mailer);
            msg.setSentDate(new Date());
            Transport tr = session.getTransport("smtp");
            tr.connect(smtphost, username, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new AplicacaoException("Mensagem de E-mail não enviada", e);
        }    
        
    }
    
    public static void enviarEmailHTMLFileComAutenticacao(String smtphost, String username, String password, String subject, 
                                                          String from, String fromText, String to, String htmlFileName,
                                                          File images[]) throws AplicacaoException {
        try{
            String mailer = "JavaMail API";
            Properties properties = System.getProperties();
            properties.put("mail.transport.protocol", "SMTP");
            
            properties.put("mail.smtp.starttls.enable","true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.smtp.port", "25"); 
            
            properties.put("mail.smtp.host", smtphost);
            properties.put("mail.smtp.auth", "true");
            
            Session session = Session.getInstance(properties, null);
            MimeMessage msg = new MimeMessage(session);
            InternetAddress lFrom = new InternetAddress(from);
            InternetAddress lTo = new InternetAddress(to);
            msg.setFrom(lFrom);
            msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);
            System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
            BodyPart bpHTML = getFileBodyPart(htmlFileName, "text/html");
            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(bpHTML);
            if(images != null) {
                for(int i = 0; i < images.length; i++) {
                    BodyPart messageBodyPart = new MimeBodyPart();
                    javax.activation.DataSource fds = new FileDataSource(images[i]);
                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<" + images[i].getName() + ">");
                    multipart.addBodyPart(messageBodyPart);
                }
                
            }
            msg.setContent(multipart);
            Transport tr = session.getTransport("smtp");
            tr.connect(smtphost, username, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        }catch(Exception e){
            e.printStackTrace();
            throw new AplicacaoException("Mensagem de E-mail não enviada", e);
        }    
       
    }
    
    public static void enviarEmailHTMLTextComAutenticacaoGmail(String smtphost, String username, String password, String subject, 
                                                          String from, String fromText, String to, String htmlText,
                                                          File images[]) throws AplicacaoException {
        try {
            String mailer = "JavaMail API";
            Properties properties = System.getProperties();
            properties.put("mail.transport.protocol", "SMTP");
            
            properties.put("mail.smtp.starttls.enable","true");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
            properties.put("mail.smtp.port","465"); 
            
            properties.put("mail.smtp.host", smtphost);
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, null);
            MimeMessage msg = new MimeMessage(session);
            InternetAddress lFrom = new InternetAddress(from, fromText);
            InternetAddress lTo = new InternetAddress(to);
            msg.setFrom(lFrom);
            msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);
            
            System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
            
            BodyPart bpHTML = new MimeBodyPart();
            bpHTML.setContent(htmlText, "text/html");
            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(bpHTML);
            if (images != null) {
                for (int i = 0; i < images.length; i++) {
                    BodyPart messageBodyPart = new MimeBodyPart();
                    javax.activation.DataSource fds = new FileDataSource(
                            images[i]);
                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<"
                            + images[i].getName() + ">");
                    multipart.addBodyPart(messageBodyPart);
                }

            }
            msg.setContent(multipart);
            Transport tr = session.getTransport("smtp");
            tr.connect(smtphost, username, password);
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AplicacaoException("Mensagem de E-mail não enviada", e);
        }
        
        return;
    }
    
    public static void enviarEmailHTMLTextComAutenticacao(String smtphost, String username, String password, String subject, 
            String from, String fromText, String to, String htmlText,
            File images[]) throws AplicacaoException, SendFailedException {
		try {
			String mailer = "JavaMail API";
			Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", "SMTP");
			properties.put("mail.smtp.host", smtphost);
			properties.put("mail.smtp.auth", "true");
			Session session = Session.getInstance(properties, null);
			MimeMessage msg = new MimeMessage(session);
			InternetAddress lFrom = new InternetAddress(from, fromText);
			InternetAddress lTo = new InternetAddress(to);
			msg.setFrom(lFrom);
			msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);

			System.setProperty("mail.mime.charset","UTF-8");
			LOG.debug("SUBJECT ANTES " + subject);
			subject = MimeUtility.encodeText(subject);
			LOG.debug("SUBJECT DEPOIS " + subject);
            msg.setSubject(subject, "UTF-8");
			
			BodyPart bpHTML = new MimeBodyPart();
			bpHTML.setContent(htmlText, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(bpHTML);
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					LOG.debug("i " + i);
					LOG.debug("file " + images[i]);
					
					BodyPart messageBodyPart = new MimeBodyPart();
					javax.activation.DataSource fds = new FileDataSource(
					images[i]);
					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<"
					+ images[i].getName() + ">");
					multipart.addBodyPart(messageBodyPart);
				}
			
			}
			msg.setContent(multipart);
			Transport tr = session.getTransport("smtp");
			tr.connect(smtphost, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		}catch (SendFailedException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AplicacaoException("Mensagem de E-mail não enviada", e);
		}
		return;
    }

    public static void enviarEmailSemAutenticacao(String smtphost, String subject, String from, String fromText, 
                                                  String to, String text) throws AplicacaoException, SendFailedException {
        try {
            String mailer = "JavaMail API";
            Properties props = System.getProperties();
            props.put("mail.smtp.auth", "false");
            Session session = Session.getDefaultInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, fromText));
            msg.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            
            System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
            
            msg.setText(text);
            msg.setHeader("X-Mailer", mailer);
            msg.setSentDate(new Date());
            Transport tr = session.getTransport("smtp");
            msg.saveChanges();
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (SendFailedException e){
			throw e;
		} catch (Exception e) {
            e.printStackTrace();
            throw new AplicacaoException("Mensagem de E-mail não enviada", e);
        }
        
       
    }

    private static BodyPart getFileBodyPart(String filename, String contentType) throws MessagingException {
        BodyPart bp = new MimeBodyPart();
        bp.setDataHandler(new DataHandler(new FileDataSource(filename)));
        return bp;
    }

    private static BodyPart getStringBodyPart(String texto, String contentType) throws MessagingException {
        BodyPart bp = new MimeBodyPart();
        bp.setDataHandler(new DataHandler(new FileDataSource(texto)));
        return bp;
    }
    
    
    public static void enviarEmailHTMLFileComAutenticacao(String smtphost, String port, String username, String password, String subject, 
            String from, String fromText, String to, String htmlFileName,
            File images[]) throws SendFailedException, AplicacaoException{
    	try{
    		System.out.println("+++++++VORTICE : ENVIO DO E-MAIL");
			//String mailer = "JavaMail API";
			Properties properties = System.getProperties();
			
			//properties.put("mail.smtp.starttls.enable","true");
			//properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			//properties.put("mail.smtp.socketFactory.fallback", "false");
			 
			
			SimpleAuth auth = new SimpleAuth(username, password);
			PasswordAuthentication pass =  auth.getPasswordAuthentication();
			
			properties.put("mail.transport.protocol", "SMTP");
			//properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.host", smtphost);
			//properties.put("mail.user", pass.getUserName());
			//properties.put("mail.pwd", pass.getPassword());
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.from", from);
			properties.put("mail.to", to);
			
			Session session = Session.getInstance(properties, null);
			
			/*
			properties.put("mail.transport.protocol", "SMTP");
            properties.put("mail.smtp.host", smtphost);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable","true");
            Session session = Session.getInstance(properties, null);
			*/

			MimeMessage msg = new MimeMessage(session);
			InternetAddress lFrom = new InternetAddress(from);
			InternetAddress lTo = new InternetAddress(to);
			msg.setFrom(lFrom);
			msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);

			System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
			
			BodyPart bpHTML = getFileBodyPart(htmlFileName, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(bpHTML);
			if(images != null) {
				for(int i = 0; i < images.length; i++) {
					BodyPart messageBodyPart = new MimeBodyPart();
					javax.activation.DataSource fds = new FileDataSource(images[i]);
					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<" + images[i].getName() + ">");
					multipart.addBodyPart(messageBodyPart);
				}
			}
			msg.setContent(multipart);
			Transport tr = session.getTransport("smtp");
			tr.connect(smtphost, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		}catch (SendFailedException e){
			throw e;
		} catch(Exception e){
			e.printStackTrace();
		throw new AplicacaoException("Mensagem de E-mail não enviada", e);
		}    
	}
    
	public static void enviarEmailHTMLTextComAutenticacao(String smtphost, String username, String password, String subject, 
            String from, String fromText, String[] to, String bcc[], String htmlText,
            File images[]) throws AplicacaoException, SendFailedException 
    {
		System.out.println("------VORTICE : ENVIO DO E-MAIL");
		try {
			String mailer = "JavaMail API";
			Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", "SMTP");
			properties.put("mail.smtp.host", smtphost);
			properties.put("mail.smtp.auth", "true");
			Session session = Session.getInstance(properties, null);
			MimeMessage msg = new MimeMessage(session);
			InternetAddress lFrom = new InternetAddress(from, fromText);
			msg.setFrom(lFrom);
			for (int i = 0; i < to.length; i++){
				if (!"".equals(to[i].trim()))
				{
					InternetAddress lTo = new InternetAddress(to[i]);
					if (i == 0){
						msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);
					}else msg.addRecipient(javax.mail.Message.RecipientType.CC, lTo);
				}
			}
			if (bcc != null && bcc.length > 0){
				for (int i = 0; i < bcc.length; i++){
					if (!"".equals(bcc[i].trim()))
					{
						InternetAddress iBcc = new InternetAddress(bcc[i]);
						msg.addRecipient(javax.mail.Message.RecipientType.BCC, iBcc);
					}
				}
			}
			
			System.setProperty("mail.mime.charset","UTF-8");
            msg.setSubject(MimeUtility.encodeText(subject));//, "UTF-8");
			
			enviaEmail(msg, session, htmlText, images, smtphost, username, password);
		}catch (SendFailedException e){
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new AplicacaoException("Mensagem de E-mail não enviada", e);
		}
		return;
    }
	
	private static void enviaEmail(MimeMessage msg, Session session, String htmlText, File images[], String smtphost, String username, 
		String password) throws SendFailedException, AplicacaoException
	{
		try{
			BodyPart bpHTML = new MimeBodyPart();
			bpHTML.setContent(htmlText, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(bpHTML);
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					LOG.debug("i " + i);
					LOG.debug("file " + images[i]);
					
					BodyPart messageBodyPart = new MimeBodyPart();
					javax.activation.DataSource fds = new FileDataSource(
					images[i]);
					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<"
					+ images[i].getName() + ">");
					multipart.addBodyPart(messageBodyPart);
				}
			
			}
			msg.setContent(multipart);
			Transport tr = session.getTransport("smtp");
			tr.connect(smtphost, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		}catch(SendFailedException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new AplicacaoException("Mensagem de e-mail não enviada", e);
		}
	}
    
    public static void enviarEmailHTMLTextComAutenticacao(String smtphost, String port, String username, String password, String subject, 
            String from, String fromText, String to, String htmlText,
            File images[]) throws AplicacaoException, SendFailedException {
		try {
			//String mailer = "JavaMail API";
			Properties properties = System.getProperties();
			
			SimpleAuth auth = new SimpleAuth(username, password);
			properties.put("mail.transport.protocol", "SMTP");
			//properties.put("mail.smtp.port", port); 
			properties.put("mail.smtp.host", smtphost);
			properties.put("mail.user", auth.username);
			properties.put("mail.pwd", auth.password); 
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.from", from);
			properties.put("mail.to", to);
			//a partir daqui,,
			Session session = Session.getInstance(properties, auth);
            
			MimeMessage msg = new MimeMessage(session);
			InternetAddress lFrom = new InternetAddress(from, fromText);
			InternetAddress lTo = new InternetAddress(to);
			msg.setFrom(lFrom);
			msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);

			System.setProperty("mail.mime.charset","UTF-8");
			LOG.debug("SUBJECT ANTES " + subject);
			subject = MimeUtility.encodeText(subject);
			LOG.debug("SUBJECT DEPOIS " + subject);
            msg.setSubject(subject, "UTF-8");
			
			BodyPart bpHTML = new MimeBodyPart();
			bpHTML.setContent(htmlText, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(bpHTML);
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					BodyPart messageBodyPart = new MimeBodyPart();
					javax.activation.DataSource fds = new FileDataSource(
					images[i]);
					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<"
					+ images[i].getName() + ">");
					multipart.addBodyPart(messageBodyPart);
				}
			
			}
			SecurityManager security = System.getSecurityManager();
			System.out.println("Security Manager" + security);
			
			msg.setContent(multipart);
			Transport tr = session.getTransport("smtp");
			tr.connect(smtphost, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		}catch (SendFailedException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AplicacaoException("Mensagem de E-mail não enviada", e);
		}
		
		return;
	}
    
    private static class SimpleAuth extends Authenticator {
        public String username = null;
        public String password = null;

        public SimpleAuth(String user, String pwd) {
            username = user;
            password = pwd;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username,password);
        }
    }
    
    public static void enviarEmailHTMLTextComAutenticacao(String smtphost, String username, String password, String subject, 
            String from, String fromText, String to, String htmlText,
            File images[], String[] ccs) throws AplicacaoException, SendFailedException {
		try {
			String mailer = "JavaMail API";
			Properties properties = System.getProperties();
			properties.put("mail.transport.protocol", "SMTP");
			properties.put("mail.smtp.host", smtphost);
			properties.put("mail.smtp.auth", "true");
			
			Session session = Session.getInstance(properties, null);
			MimeMessage msg = new MimeMessage(session);
			InternetAddress lFrom = new InternetAddress(from, fromText);
			InternetAddress lTo = new InternetAddress(to);
			msg.setFrom(lFrom);
			msg.addRecipient(javax.mail.Message.RecipientType.TO, lTo);
			for (int i = 0; i < ccs.length; i++){
				InternetAddress lcc = new InternetAddress(ccs[i]);
				msg.addRecipient(javax.mail.Message.RecipientType.CC, lcc);
			}
			msg.setSubject(subject);
			BodyPart bpHTML = new MimeBodyPart();
			bpHTML.setContent(htmlText, "text/html");
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(bpHTML);
			if (images != null) {
				for (int i = 0; i < images.length; i++) {
					LOG.debug("i " + i);
					LOG.debug("file " + images[i]);
					
					BodyPart messageBodyPart = new MimeBodyPart();
					javax.activation.DataSource fds = new FileDataSource(
					images[i]);
					messageBodyPart.setDataHandler(new DataHandler(fds));
					messageBodyPart.setHeader("Content-ID", "<"
					+ images[i].getName() + ">");
					multipart.addBodyPart(messageBodyPart);
				}
			
			}
			
			msg.setContent(multipart);
			Transport tr = session.getTransport("smtp");
			tr.connect(smtphost, username, password);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (SendFailedException e){
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw new AplicacaoException("Mensagem de E-mail não enviada", e);
		}
		return;
    }
}
