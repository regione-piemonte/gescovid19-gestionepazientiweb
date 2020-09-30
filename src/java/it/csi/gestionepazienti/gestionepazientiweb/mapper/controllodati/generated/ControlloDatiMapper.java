package it.csi.gestionepazienti.gestionepazientiweb.mapper.controllodati.generated;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.controllodati.ControlloDati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForControlloDati;

public interface ControlloDatiMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table controllo_dati
     *
     * @mbg.generated
     */
    @Insert({
        "insert into controllo_dati (controllo_id, controllo_code, ",
        "controllo_descrizione, controllo_istruzioni, ",
        "controllo_severita, controllo_fun_count, ",
        "controllo_fun_elenco, utente_operazione, ",
        "data_creazione, data_modifica, ",
        "data_cancellazione)",
        "values (#{controlloId,jdbcType=BIGINT}, #{controlloCode,jdbcType=VARCHAR}, ",
        "#{controlloDescrizione,jdbcType=VARCHAR}, #{controlloIstruzioni,jdbcType=VARCHAR}, ",
        "#{controlloSeverita,jdbcType=VARCHAR}, #{controlloFunCount,jdbcType=VARCHAR}, ",
        "#{controlloFunElenco,jdbcType=VARCHAR}, #{utenteOperazione,jdbcType=VARCHAR}, ",
        "#{dataCreazione,jdbcType=TIMESTAMP}, #{dataModifica,jdbcType=TIMESTAMP}, ",
        "#{dataCancellazione,jdbcType=TIMESTAMP})"
    })
    int insert(ControlloDati record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table controllo_dati
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "controllo_id, controllo_code, controllo_descrizione, controllo_istruzioni, controllo_severita, ",
        "controllo_fun_count, controllo_fun_elenco, utente_operazione, data_creazione, ",
        "data_modifica, data_cancellazione",
        "from controllo_dati"
    })
    @Results({
        @Result(column="controllo_id", property="controlloId", jdbcType=JdbcType.BIGINT),
        @Result(column="controllo_code", property="controlloCode", jdbcType=JdbcType.VARCHAR),
        @Result(column="controllo_descrizione", property="controlloDescrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="controllo_istruzioni", property="controlloIstruzioni", jdbcType=JdbcType.VARCHAR),
        @Result(column="controllo_severita", property="controlloSeverita", jdbcType=JdbcType.VARCHAR),
        @Result(column="controllo_fun_count", property="controlloFunCount", jdbcType=JdbcType.VARCHAR),
        @Result(column="controllo_fun_elenco", property="controlloFunElenco", jdbcType=JdbcType.VARCHAR),
        @Result(column="utente_operazione", property="utenteOperazione", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_creazione", property="dataCreazione", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="data_modifica", property="dataModifica", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="data_cancellazione", property="dataCancellazione", jdbcType=JdbcType.TIMESTAMP)
    })
    List<ControlloDati> selectAll();
    
    
}