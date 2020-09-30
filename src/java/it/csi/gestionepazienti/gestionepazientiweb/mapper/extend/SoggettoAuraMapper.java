package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseSoggettoAuraMapper;

public interface SoggettoAuraMapper extends BaseSoggettoAuraMapper{

	
	@Delete({ "delete from r_soggetto_aura", 
		" where id_soggetto = #{idSoggetto,jdbcType=BIGINT}"})
	int deleteByIdSoggetto(@Param("idSoggetto") Long idSoggetto);

	@Select({ "select", "id_soggetto, id_aura", 
		" from r_soggetto_aura" ,
		" where id_soggetto = #{idSoggetto,jdbcType=BIGINT}"
		})
	@Results({ @Result(column = "id_soggetto", property = "idSoggetto", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_aura", property = "idAura", jdbcType = JdbcType.BIGINT, id = true) })
	List<SoggettoAura> selectByIdSoggetto(@Param("idSoggetto") Long idSoggetto);

	
}
