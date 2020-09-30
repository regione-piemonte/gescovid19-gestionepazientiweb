package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Utenti;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.UserLogged;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseUtentiMapper;

public interface UtentiMapper extends BaseUtentiMapper {

	

    @Select({
        "select utenti.cf_utente, utenti.id_asr, utenti.id_laboratorio, asr.descrizione asr_descrizione, laboratorio.descrizione laboratorio_descrizione " + 
        "from utenti "
        + "LEFT join asr on utenti.id_asr = asr.id_asr "
        + "LEFT join  laboratorio on utenti.id_laboratorio = laboratorio.id_laboratorio " + 
        "where " + 
        "cf_utente=#{codiceFiscale,jdbcType=VARCHAR} "
    })
    @Results({
        @Result(column="cf_utente", property="cfUtente", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="id_laboratorio", property="idLaboratorio", jdbcType=JdbcType.BIGINT),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_laboratorio", property="laboratorio.idLaboratorio", jdbcType=JdbcType.BIGINT),
        @Result(column="laboratorio_descrizione", property="laboratorio.descrizione", jdbcType=JdbcType.VARCHAR)

    })
    UserLogged selectByCf(String codiceFiscale);
    
    
    
}
