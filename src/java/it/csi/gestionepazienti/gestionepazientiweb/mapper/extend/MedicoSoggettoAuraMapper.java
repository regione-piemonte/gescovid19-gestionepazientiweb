package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.MedicoSoggettoAura;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseMedicoSoggettoAuraMapper;

public interface MedicoSoggettoAuraMapper extends BaseMedicoSoggettoAuraMapper{

    @Select({
        "select",
        "id_medico, id_aura_sogg, data_scelta, data_revoca",
        "from r_medico_sogg_aura",
        "where id_aura_sogg = #{idAuraSogg,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="id_aura_sogg", property="idAuraSogg", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="data_scelta", property="dataScelta", jdbcType=JdbcType.DATE),
        @Result(column="data_revoca", property="dataRevoca", jdbcType=JdbcType.DATE)
    })
    List<MedicoSoggettoAura> selectByIdAuraSogg(@Param("idAuraSogg") Long idAuraSogg);
	

    @Delete({
        "delete from r_medico_sogg_aura",
        "where id_aura_sogg = #{idAuraSogg,jdbcType=BIGINT}"
    })
    int deleteByIdAuraSogg(@Param("idAuraSogg") Long idAuraSogg);
}
