package it.csi.gestionepazienti.gestionepazientiweb.util.csv;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class LocalizedPropertyNamingStrategy extends PropertyNamingStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MessageSource messageSource;
	private Locale locale;

	public LocalizedPropertyNamingStrategy(MessageSource messageSource, Locale locale) {
		this.messageSource = messageSource;
		this.locale = locale;
	}

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
		return localize(defaultName);
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
		return localize(defaultName);
	}

	@Override
	public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
		return localize(defaultName);
	}

	private String localize(String defaultName) {

	    if (defaultName!=null && !defaultName.trim().contentEquals("")) {
	        return messageSource.getMessage(defaultName, null, locale);
	    }
	    return defaultName;
	}
}
