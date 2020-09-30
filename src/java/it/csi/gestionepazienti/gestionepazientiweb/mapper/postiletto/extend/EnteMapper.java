package it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Ente;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated.BaseEnteMapper;

public interface EnteMapper extends BaseEnteMapper {

	@Select({ "select", "ente.id_ente, ente.nome, ente.tot_posti_target", "from ente",
		"join struttura on struttura.id_ente = ente.id_ente", 
		"join area on area.id_struttura = struttura.id_struttura",
		"where id_area = #{idArea,jdbcType=INTEGER}" })
	@Results({ @Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tot_posti_target", property = "totPostiTarget", jdbcType = JdbcType.INTEGER) })
	Ente selectByIdArea(Integer idArea);
}
