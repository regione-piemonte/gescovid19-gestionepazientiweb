package it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Struttura;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated.BaseStrutturaMapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface StrutturaMapper extends BaseStrutturaMapper {

	

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table struttura
	 * @mbg.generated
	 */
	@Select({ "select", "id_struttura, id_ente, nome, natura", "from struttura",
		"where id_ente = #{idEnte,jdbcType=INTEGER}"})
	@Results({ @Result(column = "id_struttura", property = "idStruttura", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER),
			@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "natura", property = "natura", jdbcType = JdbcType.VARCHAR) })
	List<Struttura> selectByIdEnte(Integer idEnte);


	@Select({ "select distinct",
		"struttura.id_struttura, struttura.id_ente, struttura.nome, natura",
		"from struttura",
		"join area on area.id_struttura = struttura.id_struttura",	
		"where (area.stato_validita='ATTIVO' or area.stato_validita is null)"})
	@Results({ @Result(column = "id_struttura", property = "idStruttura", jdbcType = JdbcType.VARCHAR, id = true),
	@Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER),
	@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
	@Result(column = "natura", property = "natura", jdbcType = JdbcType.VARCHAR) })
	List<Struttura> selectElencoStrutturaWithAreaValid();	
}