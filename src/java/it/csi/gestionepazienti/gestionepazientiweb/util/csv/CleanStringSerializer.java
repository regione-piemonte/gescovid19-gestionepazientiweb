package it.csi.gestionepazienti.gestionepazientiweb.util.csv;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class CleanStringSerializer extends ToStringSerializer {

	private static final long serialVersionUID = 1L;
	


	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		 gen.writeString(((String) value).replaceAll("[\\t\\n\\r]+"," "));
	}

}
