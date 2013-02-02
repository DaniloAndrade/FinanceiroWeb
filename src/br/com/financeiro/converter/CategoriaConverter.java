package br.com.financeiro.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financeiro.entitys.Categoria;


//@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value !=null&& value.trim().length()>0){
			Integer codigo = Integer.valueOf(value);
			
			try{
				
			}catch(Exception e){
				
			}
			
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
