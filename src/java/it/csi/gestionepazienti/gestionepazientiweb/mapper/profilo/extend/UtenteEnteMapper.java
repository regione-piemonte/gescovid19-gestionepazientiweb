package it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.dto.profilo.Profilo;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.generated.BaseProfiloMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.profilo.generated.BaseUtenteEnteMapper;

public interface UtenteEnteMapper extends BaseUtenteEnteMapper {


	@Select({ "select", 
			"ente.id_ente, ente.nome, ente.tot_posti_target",
			"from ente",
			"join r_utente_ente on r_utente_ente.id_ente = ente.id_ente",
			"where r_utente_ente.cf_utente = #{codiceFiscale,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "tot_posti_target", property = "totPostiTarget", jdbcType = JdbcType.INTEGER) })
	List<Ente> selectElencoEnteProfiloByCodiceFiscale(String codiceFiscale);


}
