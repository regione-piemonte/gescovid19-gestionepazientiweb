package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoContattoExt;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseSoggettoContattoMapper;

public interface SoggettoContattoMapper extends BaseSoggettoContattoMapper {


	
	@Select({ 
		"<script>", 
		"select id_soggetto_contatti, id_soggetto_da, id_soggetto_a, note_contatto, id_decorso,                   ",
		"rsc.contatto_tipo_id, rsc.validita_inizio, rsc.validita_fine, rsc.utente_operazione, rsc.data_creazione, ",
		"rsc.data_modifca, rsc.data_cancellazione,                                                                ",
		"sda.nome as nome_soggetto_da,                                                                            ",
		"sda.cognome as cognome_soggetto_da,                                                                      ",
		"sda.codice_fiscale as codice_fiscale_soggetto_da,                                                        ",
		"sa.nome as nome_soggetto_a,                                                                              ",
		"sa.cognome as cognome_soggetto_a,                                                                        ",
		"sa.codice_fiscale as codice_fiscale_soggetto_a,                                                          ",
		"dct.contatto_tipo_cod as contatto_tipo_cod,                                                              ",
		"dct.contatto_tipo_desc as contatto_tipo_desc                                                             ",
		"from r_soggetto_contatti rsc                                                                             ",
		"left join soggetto sda on rsc.id_soggetto_da = sda.id_soggetto                                           ",
		"left join soggetto sa  on rsc.id_soggetto_a  = sa.id_soggetto                                           ",
		"left join d_contatto_tipo dct on rsc.contatto_tipo_id = dct.contatto_tipo_id                             ",
		"where rsc.id_soggetto_contatti = rsc.id_soggetto_contatti                                                ",
		"<if test='idSoggettoDa != null'> and rsc.id_soggetto_da = #{idSoggettoDa,jdbcType=BIGINT} </if>",
		"<if test='idSoggettoA != null'> and rsc.id_soggetto_a = #{idSoggettoA,jdbcType=BIGINT} </if>",
		"</script>"
	
	})
	@Results({
			@Result(column = "id_soggetto_contatti", property = "idSoggettoContatti", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "id_soggetto_da", property = "idSoggettoDa", jdbcType = JdbcType.BIGINT),
			@Result(column = "id_soggetto_a", property = "idSoggettoA", jdbcType = JdbcType.BIGINT),
			@Result(column = "note_contatto", property = "noteContatto", jdbcType = JdbcType.VARCHAR),
			@Result(column = "id_decorso", property = "idDecorso", jdbcType = JdbcType.BIGINT),
			@Result(column = "contatto_tipo_id", property = "contattoTipoId", jdbcType = JdbcType.BIGINT),
			@Result(column = "validita_inizio", property = "validitaInizio", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "validita_fine", property = "validitaFine", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "utente_operazione", property = "utenteOperazione", jdbcType = JdbcType.VARCHAR),
			@Result(column = "data_creazione", property = "dataCreazione", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "data_modifca", property = "dataModifca", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "data_cancellazione", property = "dataCancellazione", jdbcType = JdbcType.TIMESTAMP),
			
			
			@Result(column = "nome_soggetto_a", property = "nomeSoggettoA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nome_soggetto_da", property = "nomeSoggettoDa", jdbcType = JdbcType.VARCHAR),
			
			@Result(column = "cognome_soggetto_a", property = "cognomeSoggettoA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cognome_soggetto_da", property = "cognomeSoggettoDa", jdbcType = JdbcType.VARCHAR),

			@Result(column = "codice_fiscale_soggetto_a", property = "codiceFiscaleSoggettoA", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_fiscale_soggetto_da", property = "codiceFiscaleSoggettoDa", jdbcType = JdbcType.VARCHAR),
	
			@Result(column = "contatto_tipo_cod", property = "contattoTipoCodice", jdbcType = JdbcType.VARCHAR),
			@Result(column = "contatto_tipo_desc", property = "contattoTipoDescrizione", jdbcType = JdbcType.VARCHAR),
	
		
			
	
	})
	List<SoggettoContattoExt> selectContattiDaA(@Param("idSoggettoDa") Long idSoggettoDa, @Param("idSoggettoA") Long idSoggettoA);
	
	

}
