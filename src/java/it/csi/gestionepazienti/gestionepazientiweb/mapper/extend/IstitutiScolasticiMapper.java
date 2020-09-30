package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;


import it.csi.gestionepazienti.gestionepazientiweb.dto.IstitutoScolastico;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseIstitutiScolasticiMapper;

public interface IstitutiScolasticiMapper extends BaseIstitutiScolasticiMapper {

	@Select({
    	" select a.* " ,
    	" from istituti_scolastici a, r_soggetto_istituto b  ",
    	" where a.id_istituto = b.id_istituto and ",
    	" b.id_soggetto = #{idSoggetto} and ",
    	" b.data_cancellazione is null "
    	//" now() between b.validita_inizio and coalesce(b.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))" 
    })
    @Results({
        @Result(column="id_istituto", property="idIstituto", jdbcType=JdbcType.INTEGER),
        @Result(column="codice_istituto", property="codiceIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="denominazione_istituto", property="denominazioneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_istituto", property="indirizzoIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_istituto", property="comuneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="provincia_istituto", property="provinciaIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel_istituto", property="telIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="ordine_istituto", property="ordineIstituto", jdbcType=JdbcType.VARCHAR)
    })
    List<IstitutoScolastico> selectBySoggettoId(@Param("idSoggetto") Long idSoggetto);
	
	
	@Select({
    	" select a.* " ,
    	" from istituti_scolastici a, r_soggetto_istituto b  ",
    	" where a.id_istituto = b.id_istituto and ",
    	" b.id_soggetto = #{idSoggetto} and ",
    	" b.data_cancellazione is null  and",
    	" #{adesso,jdbcType=TIMESTAMP} between b.validita_inizio and coalesce(b.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))" 
    })
    @Results({
        @Result(column="id_istituto", property="idIstituto", jdbcType=JdbcType.INTEGER),
        @Result(column="codice_istituto", property="codiceIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="denominazione_istituto", property="denominazioneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_istituto", property="indirizzoIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_istituto", property="comuneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="provincia_istituto", property="provinciaIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel_istituto", property="telIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="ordine_istituto", property="ordineIstituto", jdbcType=JdbcType.VARCHAR) 
    })
    List<IstitutoScolastico> selectIstitutiValidiBySoggettoId(@Param("idSoggetto") Long idSoggetto, @Param("adesso") Date data);
	
	

    @Select({
        "select distinct comune_istituto from istituti_scolastici order by comune_istituto asc"
    })
    @Results({
        @Result(column="comune_istituto", property="comuneIstituto", jdbcType=JdbcType.VARCHAR)
    })
    List<IstitutoScolastico> selectDistinctComune();
    
    
    @Select({
        "select distinct provincia_istituto from istituti_scolastici order by provincia_istituto asc"
    })
    @Results({
        @Result(column="provincia_istituto", property="provinciaIstituto", jdbcType=JdbcType.VARCHAR)
    })
    List<IstitutoScolastico> selectDistinctProvincia();
    
    
    @Select({
        "select distinct ordine_istituto from istituti_scolastici order by ordine_istituto asc"
    })
    @Results({
        @Result(column="ordine_istituto", property="ordineIstituto", jdbcType=JdbcType.VARCHAR)
    })
    List<IstitutoScolastico> selectDistinctOrdine();


    @Select({
    	"<script>",
        "select",
        "id_istituto, codice_istituto, denominazione_istituto, indirizzo_istituto, comune_istituto, ",
        "provincia_istituto, tel_istituto, ordine_istituto",
        "from istituti_scolastici",
        "where 1=1",
        " <if test='denominazione != null'> AND lower(denominazione_istituto) like lower(#{denominazione,jdbcType=VARCHAR}) </if>",
        " <if test='ordine != null'> AND lower(ordine_istituto) like lower(#{ordine,jdbcType=VARCHAR}) </if>",
        " <if test='provincia != null'> AND lower(provincia_istituto) like lower(#{provincia,jdbcType=VARCHAR}) </if>",
        " <if test='comune != null'> AND lower(comune_istituto) like lower(#{comune,jdbcType=VARCHAR}) </if>",
        " order by denominazione_istituto asc",
        "</script>"
        
    })
    @Results({
        @Result(column="id_istituto", property="idIstituto", jdbcType=JdbcType.INTEGER),
        @Result(column="codice_istituto", property="codiceIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="denominazione_istituto", property="denominazioneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_istituto", property="indirizzoIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_istituto", property="comuneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="provincia_istituto", property="provinciaIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel_istituto", property="telIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="ordine_istituto", property="ordineIstituto", jdbcType=JdbcType.VARCHAR)
    })
	List<IstitutoScolastico> selectListaIstituti(@Param("denominazione") String denominazione,
			@Param("ordine") String ordine, @Param("provincia") String provincia,
			@Param("comune") String comune);
    
    
    @Select({
    	" select a.* " ,
    	" from istituti_scolastici a, r_soggetto_personale_scolastico_istituto b  ",
    	" where a.id_istituto = b.id_istituto and ",
    	" b.id_soggetto = #{idSoggetto} and ",
    	" b.data_cancellazione is null  and",
    	" #{dataValidita,jdbcType=TIMESTAMP} between b.validita_inizio and coalesce(b.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))" 
    })
    @Results({
        @Result(column="id_istituto", property="idIstituto", jdbcType=JdbcType.INTEGER),
        @Result(column="codice_istituto", property="codiceIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="denominazione_istituto", property="denominazioneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_istituto", property="indirizzoIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_istituto", property="comuneIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="provincia_istituto", property="provinciaIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel_istituto", property="telIstituto", jdbcType=JdbcType.VARCHAR),
        @Result(column="ordine_istituto", property="ordineIstituto", jdbcType=JdbcType.VARCHAR) 
    })
    List<IstitutoScolastico> selectIstitutiValidiBySoggettoScolasticoId(@Param("idSoggetto") Long idSoggetto, @Param("dataValidita") Date data);
}
