package br.com.financeiro.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.financeiro.exceptions.UtilException;

public class EmailUtil {
	
	public void enviarEmail(String de, String para, String assunto, String Mensagem) throws UtilException{
		
		try{
			Context initialContext = new InitialContext();
			Context envContext = (Context) initialContext.lookup("java:comp/env");
			Session session = (Session) envContext.lookup("mail/Session");
			SimpleEmail email = new SimpleEmail();
			email.setMailSession(session);
			
			if(de !=null){
				email.setFrom(de);
			}else{
				Properties properties = session.getProperties();
				email.setFrom(properties.getProperty("mail.smtp.user"));
			}
			
			email.addTo(para);
			email.setSubject(assunto);
			email.setMsg(Mensagem);
			email.setSentDate(new Date());
			email.send();
			
		}catch (EmailException e){
			throw new UtilException(e);
		}catch (NamingException e) {
			throw new UtilException(e);
		}
	}
}
