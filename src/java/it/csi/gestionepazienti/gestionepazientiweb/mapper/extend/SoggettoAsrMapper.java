package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.SoggettoAsr;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseSoggettoAsrMapper;
public interface SoggettoAsrMapper extends BaseSoggettoAsrMapper {

	

    @Select({
        "select",
        "id_soggetto_asr, id_soggetto, id_asr",
        "from r_soggetto_asr",
        "where id_soggetto = #{idSoggetto,jdbcType=BIGINT} and id_asr = #{idAsr,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_soggetto_asr", property="idSoggettoAsr", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT)
    })
    SoggettoAsr selectByIdSoggettoIdAsr(@Param("idSoggetto") Long idSoggetto, @Param("idAsr") Long idAsr);
    
    @Select({
        "select",
        "id_soggetto_asr, id_soggetto, id_asr",
        "from r_soggetto_asr",
        "where id_soggetto = #{idSoggetto,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_soggetto_asr", property="idSoggettoAsr", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT)
    })
    List<SoggettoAsr> selectByIdSoggetto(@Param("idSoggetto") Long idSoggetto);
	
	
}
