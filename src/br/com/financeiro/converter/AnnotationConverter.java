package br.com.financeiro.converter;

import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="annotationConverter")
public class AnnotationConverter implements Converter {
	


	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent ui, String value) {
		//EntityManager em = new JPAUtil().getEntityManager();	
		if (value != null) { 
			 
			Object object = this.getAttributesFrom(ui).get(value);
			/*if(object.getClass().isAnnotationPresent(Entity.class))
				object = em.getReference(object.getClass(), object);
				*/
				//em.merge(object);
			 return object;  
	     }  
		 return null;  
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent ui, Object value) {
		 if (value != null  
	                && !"".equals(value)) {  
	  
			 	String key = getValueKey(value);
	            // adiciona item como atributo do componente  
	            this.addAttribute(ui, key, value);  
	  
	            return key;
	        }  
		
		return null;
	}

	
	
	private String getValueKey(Object value) {
		String key = "" ;
		
		
		
		if(value instanceof String){
			return (String) value;
		}
		
		Class<? extends Object> classe = value.getClass();
		Field[] declaredFields = classe.getDeclaredFields();
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(ConverterID.class)){
				field.setAccessible(true);
				try {
					key = field.get(value).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return key;
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {  
        return component.getAttributes();  
    }  
	
	
	
	 protected void addAttribute(UIComponent component,String key, Object o) {  
	     if(key !=null ){
	    	 this.getAttributesFrom(component).put(key, o);  
	     }
	  }  
	
	
	
	/*
	 * public class SimpleEntityConverter implements Converter {  
  
		    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {  
		        if (value != null) {  
		            return this.getAttributesFrom(component).get(value);  
		        }  
		        return null;  
		    }  
		  
		    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
		  
		        if (value != null  
		                && !"".equals(value)) {  
		  
		            BaseEntity entity = (BaseEntity) value;  
		  
		            // adiciona item como atributo do componente  
		            this.addAttribute(component, entity);  
		  
		            Long codigo = entity.getId();  
		            if (codigo != null) {  
		                return String.valueOf(codigo);  
		            }  
		        }  
		  
		        return (String) value;  
		    }  
		  
		    protected void addAttribute(UIComponent component, BaseEntity o) {  
		        String key = o.getId().toString(); // codigo da empresa como chave neste caso  
		        this.getAttributesFrom(component).put(key, o);  
		    }  
		  
		    protected Map<String, Object> getAttributesFrom(UIComponent component) {  
		        return component.getAttributes();  
		    }  
  
}  
	 */
}
