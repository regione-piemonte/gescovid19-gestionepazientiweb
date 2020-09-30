package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.controllodati.generated.ControlloDatiMapper;

public interface IndicatoriMapper extends ControlloDatiMapper {

	
	
	@Select({"<script>",
		"select * from fnc_controllo_dati(#{cfUtente,jdbcType=VARCHAR},#{offset,jdbcType=BIGINT}, #{limit,jdbcType=BIGINT}, #{codiceControllo,jdbcType=VARCHAR}) ",
		" WHERE 1=1 ",
		" <if test='filter != null'> ",
		" AND ( lower(nome_out) like lower(#{filter,jdbcType=VARCHAR}) ",
		" OR lower(cognome_out) like lower(#{filter,jdbcType=VARCHAR}) ",
		" OR lower(domicilio_out) like lower(#{filter,jdbcType=VARCHAR}) ",
		" OR lower(asl_residenza_out) like lower(#{filter,jdbcType=VARCHAR}) ",
		" OR lower(asl_domicilio_out) like lower(#{filter,jdbcType=VARCHAR}) )",
		" </if>",
		" order by cognome_out ${ascOrDesc} ",
    	"</script>"
    })
    
    @Results({
    	@Result(column="count_out", property="totalCount", jdbcType=JdbcType.BIGINT),
    	@Result(column="cf_out", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
    	@Result(column="cognome_out", property="cognome", jdbcType=JdbcType.VARCHAR),
    	@Result(column="nome_out", property="nome", jdbcType=JdbcType.VARCHAR),
    	@Result(column="domicilio_out", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
    	@Result(column="asr_out", property="asr.descrizione", jdbcType=JdbcType.BIGINT),
    	@Result(column = "asl_residenza_out", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    	@Result(column = "asl_domicilio_out", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    	@Result(column="telefono_out", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR)
    	
    })
	List<SoggettoForElenco> functionControlloDati(@Param("cfUtente") String cfUtente, @Param("offset") Long offset,
			@Param("limit") Long limit, @Param("ascOrDesc") String ascOrDesc, @Param("codiceControllo") String codiceControllo,
			@Param("filter") String filter);
   
}