package it.csi.gestionepazienti.gestionepazientiweb.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateSerializer extends JsonSerializer<XMLGregorianCalendar> {

    @Override
    public void serialize(XMLGregorianCalendar date, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	jgen.writeString(sdf.format(date.toGregorianCalendar().getTime()));
    }

}