package com.vortice.core.util.email;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


public class EmailMessageUtil {
	
	private JavaMailSenderImpl 		mailSender;
	private static final Logger LOG = Logger.getLogger(EmailMessageUtil.class);
	
	public void enviaEmail(final Email mail){
		
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

				if (mail.getFrom() == null) message.setFrom("me@mail.com");
				else message.setFrom(mail.getFrom());

				//SETANDO OS DESTINATARIOS.
				if (mail.getTo() != null && mail.getTo().length > 0){
					message.setTo(mail.getTo());
				}
				if (mail.getCc() != null && mail.getCc().length > 0){
					message.setCc(mail.getCc());
				}
				if (mail.getBcc() != null && mail.getBcc().length > 0){
					message.setBcc(mail.getBcc());
				}

				message.setSubject(mail.getTitulo());
				LOG.debug("mail.isHtml() " + mail.isHtml());
				message.setText(mail.getTexto(), mail.isHtml());
				if (mail.getImages() != null && mail.getImages().length > 0){
					LOG.debug("mail.getImagens().length " + mail.getImages().length);
					for (int i = 0; i < mail.getImages().length; i++){
						String imagem = mail.getImages()[i];
						String nomeImagem = imagem.substring(imagem.lastIndexOf("/")+1, imagem.length());
						LOG.debug("nomeImagem " + nomeImagem);
						LOG.debug("imagem " + imagem);
						message.addInline(nomeImagem, new File(imagem));
					}
				}
				
				if (mail.getArquivos() != null && mail.getArquivos().length > 0){
					for (int i = 0; i < mail.getArquivos().length; i++){
						ArquivoAnexo arquivo = mail.getArquivos()[i];
						String nomeArquivo = arquivo.getNomeArquivo();
						message.addAttachment(nomeArquivo, new File(arquivo.getArquivo().getAbsolutePath()));
					}
				}
				//message.setValidateAddresses(true);
				Properties properties = System.getProperties();
				properties.put("mail.smtp.auth", "true");
				mailSender.setJavaMailProperties(properties);
			}
		});
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
}