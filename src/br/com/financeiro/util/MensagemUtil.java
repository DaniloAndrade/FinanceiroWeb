package br.com.financeiro.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class MensagemUtil {

	
	private static final String PACOTE_MENSAGENS_IDIOMAS = "br.com.financeiro.idioma.mensagens";
	
	public static String getMensagem(String chave){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(chave);
	}
	
	public static String getMensagem(String chave, Object... parametros){
		String mensagem = getMensagem(chave);
		MessageFormat format = new MessageFormat(mensagem);
		return format.format(parametros);
	}
	
	
	public static String getMensagem(Locale locale,String chave){
		ResourceBundle bundle = ResourceBundle.getBundle(PACOTE_MENSAGENS_IDIOMAS,locale);
		return bundle.getString(chave);
	}
	
	public static String getMensagem(Locale locale,String chave, Object... parametros){
		String mensagem = getMensagem(locale,chave);
		MessageFormat format = new MessageFormat(mensagem);
		return format.format(parametros);
	}
}
