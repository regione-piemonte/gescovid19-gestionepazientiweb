package it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Area;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.generated.BaseUtenteAreaMapper;

public interface UtenteAreaMapper extends BaseUtenteAreaMapper  {



	@Select({ "select", 
			"area.id_area, area.id_struttura, area.id_d_area, area.nome, area.responsabile, area.riferimento, area.stato_validita",
			"from area",
			"join r_utente_area on r_utente_area.id_area = area.id_area",
			"where r_utente_area.cf_utente = #{codiceFiscale,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "id_struttura", property = "idStruttura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_d_area", property = "idDArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "responsabile", property = "responsabile", jdbcType = JdbcType.VARCHAR),
		@Result(column = "riferimento", property = "riferimento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "stato_validita", property = "statoValidita", jdbcType = JdbcType.VARCHAR) })
	List<Area> selectElencoAreaProfiloByCodiceFiscale(String codiceFiscale);
	
}