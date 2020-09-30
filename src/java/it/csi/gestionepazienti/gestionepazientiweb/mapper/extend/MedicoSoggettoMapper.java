package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.MedicoSoggetto;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseMedicoSoggettoMapper;

public interface MedicoSoggettoMapper extends BaseMedicoSoggettoMapper{


    @Select({
        "select",
        "id_medico, id_soggetto",
        "from r_medico_soggetto ", 
        "where id_medico=#{idMedico,jdbcType=BIGINT} and id_soggetto= #{idSoggetto,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT, id=true)
    })
    List<MedicoSoggetto> selectByPk(MedicoSoggetto record);
    
    
    
    @Select({
        "select",
        "id_medico, id_soggetto",
        "from r_medico_soggetto ", 
        "where id_soggetto= #{idSoggetto,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT, id=true)
    })
    List<MedicoSoggetto> selectByIdSoggetto(MedicoSoggetto record);
}
