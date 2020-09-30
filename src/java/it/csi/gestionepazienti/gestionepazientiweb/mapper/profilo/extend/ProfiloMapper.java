package it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.generated.BaseProfiloMapper;

public interface ProfiloMapper extends BaseProfiloMapper {


	@Select({ "select", 
			"profilo.id_profilo, nome_profilo", 
			"from profilo",
			"join r_utente_profilo on r_utente_profilo.id_profilo = profilo.id_profilo",
			"where r_utente_profilo.cf_utente = #{codiceFiscale,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "id_profilo", property = "idProfilo", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "nome_profilo", property = "nomeProfilo", jdbcType = JdbcType.VARCHAR) })
	List<Profilo> selectElencoProfiloByCodiceFiscale(String codiceFiscale);


}
