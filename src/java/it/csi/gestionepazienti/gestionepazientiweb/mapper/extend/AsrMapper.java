package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseAsrMapper;

public interface AsrMapper extends BaseAsrMapper{

	
	@Select({"select id_asr from asr where id_ente = #{idEnte,jdbcType=BIGINT}" })
	@Results({ @Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT)})
	List<Asr> selectListaAsrByIdEnte(Long idEnte);
	
	
	
	@Select({"select a.id_asr from asr a",
					 //" left outer join asl b on a.id_asr = b.id_asr and b.desc_asl_estesa = #{descEstesaAsl,jdbcType=VARCHAR} ",
					 " where a.id_ente = #{idEnte,jdbcType=BIGINT} " })
	@Results({ @Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT)})
	Long selectByIdEnte(@Param("idEnte") Long idEnte /*, @Param("descEstesaAsl") String descEstesaAsl*/);
	
	@Select({"select a.id_asr from asr a",
		 " where a.id_ente = #{idEnte,jdbcType=BIGINT} " })
@Results({ @Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT)})
Long findByIdAsr(@Param("idEnte") Long idEnte );
	
	
}
