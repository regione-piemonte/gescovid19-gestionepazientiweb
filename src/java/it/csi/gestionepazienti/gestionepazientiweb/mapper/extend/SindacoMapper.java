package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SindacoComune;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseSindacoMapper;

public interface SindacoMapper extends BaseSindacoMapper {

	
    @Select({
        "select",
        "cf_utente, comune_istat, cognome, nome, telefono, email,",
        "comuni.nome_comune as comune_nome ",
        "from sindaco",
        " LEFT join comuni on sindaco.comune_istat = comuni.istat_comune ",
        "where cf_utente = #{cfUtente,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="cf_utente", property="cfUtente", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="comune_istat", property="comuneIstat", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono", property="telefono", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),

        @Result(column="comune_nome", property="comune.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_istat", property="comune.istatComune", jdbcType=JdbcType.VARCHAR),

    })
    SindacoComune selectByCodiceFiscale(@Param("cfUtente") String cfUtente);

}
