package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.RSoggettoPersonaleScolasticoIstituto;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseRSoggettoPersonaleScolasticoIstitutoMapper;

public interface RSoggettoPersonaleScolasticoIstitutoMapper extends BaseRSoggettoPersonaleScolasticoIstitutoMapper {


    @Select({
        "select",
        "sogperscol_ist_id, id_soggetto, id_istituto, validita_inizio, validita_fine, ",
        "utente_operazione, data_creazione, data_modifca, data_cancellazione",
        "from r_soggetto_personale_scolastico_istituto",
        "where id_soggetto = #{idSoggetto,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="sogperscol_ist_id", property="sogperscolIstId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.INTEGER),
        @Result(column="id_istituto", property="idIstituto", jdbcType=JdbcType.INTEGER),
        @Result(column="validita_inizio", property="validitaInizio", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="validita_fine", property="validitaFine", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utente_operazione", property="utenteOperazione", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_creazione", property="dataCreazione", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="data_modifca", property="dataModifca", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="data_cancellazione", property="dataCancellazione", jdbcType=JdbcType.TIMESTAMP)
    })
    List<RSoggettoPersonaleScolasticoIstituto> selectByIdSoggettoScolastico(@Param("idSoggetto") Long idSoggetto);

    
}