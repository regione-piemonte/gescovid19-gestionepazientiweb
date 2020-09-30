package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Tampone;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForElencoNonAbbinati;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.TamponeForReport;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseTamponeMapper;

public interface TamponeMapper extends BaseTamponeMapper {

	
	@Insert({ "insert into tampone (id_tampone,id_soggetto, id_laboratorio, ", 
		"criterio_epidemiologico_covid19, id_test_covid, ", 
		"id_ris_tamp, ",
		"tampone_autorizzato, data_inserimento_richiesta, ", 
		"data_ultima_modifica, utente_ultima_modifica, ", 
		"data_test, ",
		"medico_richiedente, contatto_richiedente, tampone_motivo_id)",
		"values (nextval('seq_id_tampone'),#{idSoggetto,jdbcType=BIGINT}, #{idLaboratorio,jdbcType=BIGINT}, ",
		"#{criterioEpidemiologicoCovid19,jdbcType=VARCHAR}, #{idTestCovid,jdbcType=BIGINT}, ",
		"#{idRisTamp,jdbcType=BIGINT}, ",
		"#{tamponeAutorizzato,jdbcType=VARCHAR}, #{dataInserimentoRichiesta,jdbcType=TIMESTAMP}, ",
		"#{dataUltimaModifica,jdbcType=TIMESTAMP}, #{utenteUltimaModifica,jdbcType=VARCHAR}, ", 
		"#{dataTest,jdbcType=TIMESTAMP}, ",
		"#{medicoRichiedente,jdbcType=VARCHAR}, #{contattoRichiedente,jdbcType=VARCHAR}, #{tamponeMotivoId,jdbcType=INTEGER} )" 
	})
	@Options(useGeneratedKeys = true, keyProperty = "idTampone")
	int insert(Tampone record);

	
	
	static final String TAMPONI = 
			" select id_tampone, tampone.id_soggetto,laboratorio.id_laboratorio,data_inserimento_richiesta, "
			+ " soggetto.codice_fiscale, soggetto.cognome, soggetto.nome, soggetto.data_nascita, "
			+ " tampone_autorizzato,  ris_tampone.risultato_tampone risultato_tampone, data_test,"
			+ " tampone.criterio_epidemiologico_covid19, " + "medico_richiedente, contatto_richiedente, "
			+ " soggetto.comune_residenza_istat, soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, "
			+ " comuni_dom.nome_comune as comune_domicilio_nome, comuni_res.nome_comune as comune_residenza_nome, desc_tipo_soggetto, "
			+ " laboratorio.descrizione laboratorio_descrizione,tampone.id_ris_tamp, count(*) OVER() AS full_count " 
			+ " from tampone"
			+ " left join soggetto on tampone.id_soggetto = soggetto.id_soggetto"
			+ " left join ris_tampone on tampone.id_ris_tamp = ris_tampone.id_ris_tamp"
			+ " left join laboratorio on tampone.id_laboratorio = laboratorio.id_laboratorio"
			+ " left join d_tipo_soggetto on d_tipo_soggetto.id_tipo_soggetto = soggetto.id_tipo_soggetto  "
			+ " left join comuni comuni_res on soggetto.comune_residenza_istat = comuni_res.istat_comune"
			+ " left join comuni comuni_dom on soggetto.comune_domicilio_istat = comuni_dom.istat_comune"
			+ " left join ASL ASL_RES on SOGGETTO.asl_residenza = ASL_RES.desc_asl_estesa"
			+ " left join ASL ASL_DOM on SOGGETTO.asl_domicilio = ASL_DOM.desc_asl_estesa"
			+ " left join r_soggetto_asr on SOGGETTO.id_soggetto = r_soggetto_asr.id_soggetto "
			+ " <where> "
			+ "   <if test='idAsr != -1'>  "
			+ "      (soggetto.id_asr=#{idAsr,jdbcType=BIGINT}  or ASL_RES.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_DOM.id_asr=#{idAsr,jdbcType=BIGINT} or r_soggetto_asr.id_asr=#{idAsr,jdbcType=BIGINT} ) "
			+ "   </if>"
			+ "   <if test='filter != null'> " 
			+ "       AND ( "
			+ "           lower(soggetto.codice_fiscale)    		like lower(#{filter,jdbcType=VARCHAR}) "
			+ "           OR lower(soggetto.cognome)        		like lower(#{filter,jdbcType=VARCHAR}) "
			+ "           OR lower(soggetto.nome)           		like lower(#{filter,jdbcType=VARCHAR}) "
			+ "           OR lower(ris_tampone.risultato_tampone) 	like lower(#{filter,jdbcType=VARCHAR}) "
			+ "           OR lower(laboratorio.descrizione) 		like lower(#{filter,jdbcType=VARCHAR}) "
			+ "       )"
			+ "   </if>"
			+ "   <if test='tamponeAutorizzato!=null' > AND tampone_autorizzato = #{tamponeAutorizzato,jdbcType=VARCHAR} </if>"
			+ " </where> "
			+ " group by                                                                                        "
			+ " id_tampone, laboratorio.id_laboratorio,                                                         "
			+ " soggetto.codice_fiscale,                                                                        "
			+ " soggetto.cognome,                                                                               "
			+ " soggetto.nome, soggetto.data_nascita,                                                           "
			+ " tampone_autorizzato,  ris_tampone.risultato_tampone, risultato_tampone, data_test,              "
			+ " tampone.criterio_epidemiologico_covid19,                                                        "
			+ " medico_richiedente, contatto_richiedente,                                                       "
			+ " soggetto.comune_residenza_istat, soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, "
			+ " comuni_dom.nome_comune,                                                                         "
			+ " comuni_res.nome_comune, desc_tipo_soggetto                                                      ";
	
	
//    @Select(
//        	{
//        	"<script>",
//        	" select count(*) from ( ",
//        	TAMPONI,
//        	" ) as count_table_inner",
//        	"</script>"
//        })
//	Long selectForReportByIdAsrCount(
//			//Filtri
//			@Param("idAsr") Long idAsr,
//			@Param("filter") String filter,
//    		@Param("tamponeAutorizzato") String tamponeAutorizzato
//    		);
	
	
	@Select({"<script>",
			TAMPONI,
			" <if test='oderByFiled == null'> order by ",
	        "   case when tampone_autorizzato = 'NI' then 0 else 1 end,  ",
	        "   data_inserimento_richiesta desc nulls last",
	        " </if>",
	        
	        " <if test='oderByFiled != null'> order by ${oderByFiled} ${orderAscDesc} </if>",
			" <if test='limit !=-1'> offset #{offset,jdbcType=BIGINT} limit  #{limit,jdbcType=BIGINT}</if>",
			
	        "</script>"
	})
	@Results({
        @Result(column="id_tampone", property="idTampone", jdbcType=JdbcType.BIGINT),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="medico_richiedente", property="medicoRichiedente", jdbcType=JdbcType.VARCHAR),
        @Result(column="contatto_richiedente", property="contattoRichiedente", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_test", property="dataEsito", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="tampone_autorizzato", property="tamponeAutorizzato", jdbcType=JdbcType.VARCHAR),
		@Result(column = "data_inserimento_richiesta", property = "dataInserimentoRichiesta", jdbcType = JdbcType.TIMESTAMP),
        @Result(column="laboratorio_descrizione", property="laboratorio", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc_tipo_soggetto", property="descTipoSoggetto", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
		@Result(column = "criterio_epidemiologico_covid19", property = "criterioEpidemiologicoCovid19", jdbcType = JdbcType.VARCHAR),
        @Result(column="risultato_tampone", property="esitoTampone", jdbcType=JdbcType.VARCHAR),
        @Result(column = "full_count", property = "totalCount", jdbcType = JdbcType.INTEGER)
    })
    List<TamponeForReport> selectForReportByIdAsr(
    		//Filtri
    		@Param("idAsr") Long idAsr,
    		@Param("filter") String filter,
    		@Param("tamponeAutorizzato") String tamponeAutorizzato,
    		
    		//Paginazione
    		@Param("oderByFiled") String oderByFiled,
    		@Param("orderAscDesc") String orderAscDesc,
    		@Param("offset") Long offset,
    		@Param("limit") Long limit
    		
    		);
	
	
    @Select({
    	"select tampone.id_tampone, id_soggetto, tampone.id_laboratorio, criterio_epidemiologico_covid19, tampone.id_test_covid, ",
       " data_test, tampone.id_ris_tamp, tampone_autorizzato, data_inserimento_richiesta, data_ultima_modifica, utente_ultima_modifica,",
       " laboratorio.descrizione laboratorio_descrizione, ",
	   " medico_richiedente, contatto_richiedente, ",
       " test_covid.test_covid,",
       " ris_tampone.risultato_tampone,",
       " d_tampone_motivo.tampone_motivo_id,  d_tampone_motivo.tampone_motivo_cod , d_tampone_motivo.tampone_motivo_desc ",
       " from tampone",
       " left join laboratorio on tampone.id_laboratorio = laboratorio.id_laboratorio ", 
       " left join test_covid on tampone.id_test_covid = test_covid.id_test_covid ",
       " left join ris_tampone on tampone.id_ris_tamp = ris_tampone.id_ris_tamp ",
       " left join d_tampone_motivo on tampone.tampone_motivo_id = d_tampone_motivo.tampone_motivo_id",
       " where id_soggetto=#{idSoggetto,jdbcType=BIGINT} ",
       " UNION ", 
       "  select null, id_soggetto, null, null, null, trunc(ref.effectivetime_value),  ", 
       "  decode(ref.value_code,'P','2','N','4')::integer, ", 
       "  null,null,null,null, ", 
       "  ref_test.descr_struttura, null, null,null,ref.value_code, ", 
       "  NULL, null, ", 
       "  'Referto in corso di abbinamento alla richiesta' ", 
       "  from dmarefcovid_l_elaborazione_referto dler , dmarefcovid_t_referto_det ref ,  ", 
       "  dmarefcovid_t_referto ref_test , soggetto x ", 
       "  where dler.elabdet_scarto = true ", 
       "  and ref.intfrdet_id=dler.intfrdet_id ", 
       "  and ref.value_code in ('P','N')  ", 
       "  and ref.id_messaggio= ref_test.id_messaggio ", 
       "  and x.codice_fiscale = ref_test.codfisc     ", 
       "  and x.id_soggetto = #{idSoggetto,jdbcType=BIGINT}      ", 
       "  and not exists ( ", 
       "       select 1 from tampone a ", 
       "       where a.id_soggetto = x.id_soggetto ", 
       "       and trunc(a.data_test) = trunc(ref.effectivetime_value) ", 
       "       and decode(ref.value_code,'P','2','N','4')::integer = a.id_ris_tamp ", 
       "    ) ", 
       "UNION ", 
       "  select null, id_soggetto, null, null, null, trunc(ref.effectivetime_value),  ", 
       "  decode(ref.value_code,'P','2','N','4')::integer, ", 
       "  null,null,null,null, ", 
       "  ref_test.descr_struttura, null, null,null,ref.value_code, ", 
       "  NULL, null, ", 
       "  'Referto in corso di abbinamento alla richiesta' ", 
       "  from dmarefcovid_l_elaborazione_referto dler , dmarefcovid_t_referto_det ref ,  ", 
       "  dmarefcovid_t_referto ref_test , soggetto x ", 
       "  where dler.elabdet_scarto = true ", 
       "  and ref.intfrdet_id=dler.intfrdet_id ", 
       "  and ref.value_code in ('P','N')  ", 
       "  and ref.id_messaggio= ref_test.id_messaggio ", 
       "  and (  ", 
       "         x.codice_fiscale <> ref_test.codfisc   ", 
       "         and x.cognome = ref_test.cognome ", 
       "         and x.nome = ref_test.nome ", 
       "         and trunc(x.data_nascita) = trunc(ref_test.datanascita)  ", 
       "           ", 
       "       )    ", 
       "  and x.id_soggetto = #{idSoggetto,jdbcType=BIGINT}      ", 
       "  and not exists ( ", 
       "       select 1 from tampone a ", 
       "       where a.id_soggetto = x.id_soggetto ", 
       "       and trunc(a.data_test) = trunc(ref.effectivetime_value) ", 
       "       and decode(ref.value_code,'P','2','N','4')::integer = a.id_ris_tamp ", 
       "    )"
    })
    @Results({
        @Result(column="id_tampone", property="idTampone", jdbcType=JdbcType.BIGINT),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="id_laboratorio", property="idLaboratorio", jdbcType=JdbcType.BIGINT),
        @Result(column="criterio_epidemiologico_covid19", property="criterioEpidemiologicoCovid19", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_test_covid", property="idTestCovid", jdbcType=JdbcType.BIGINT),
        @Result(column="data_test", property="dataTest", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="id_ris_tamp", property="idRisTamp", jdbcType=JdbcType.BIGINT),
        @Result(column="medico_richiedente", property="medicoRichiedente", jdbcType=JdbcType.VARCHAR),
        @Result(column="contatto_richiedente", property="contattoRichiedente", jdbcType=JdbcType.VARCHAR),
        @Result(column="tampone_autorizzato", property="tamponeAutorizzato", jdbcType=JdbcType.VARCHAR),
		@Result(column = "data_inserimento_richiesta", property = "dataInserimentoRichiesta", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "data_ultima_modifica", property = "dataUltimaModifica", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "utente_ultima_modifica", property = "utenteUltimaModifica", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_laboratorio", property="laboratorio.idLaboratorio", jdbcType=JdbcType.BIGINT),
        @Result(column="laboratorio_descrizione", property="laboratorio.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_test_covid", property="testCovid.idTestCovid", jdbcType=JdbcType.BIGINT),
        @Result(column="test_covid", property="testCovid.testCovid", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_ris_tamp", property="risTampone.idRisTamp", jdbcType=JdbcType.BIGINT),
        @Result(column="risultato_tampone", property="risTampone.risultatoTampone", jdbcType=JdbcType.VARCHAR),
        @Result(column="tampone.tampone_motivo_id", property="tamponeMotivo.tamponeMotivoId", jdbcType=JdbcType.BIGINT),
        @Result(column="tampone_motivo_cod", property="tamponeMotivo.tamponeMotivoCod", jdbcType=JdbcType.VARCHAR),
        @Result(column="tampone_motivo_desc", property="tamponeMotivo.tamponeMotivoDesc", jdbcType=JdbcType.VARCHAR)
    })
    List<TamponeForElenco> selectForElencoByIdSoggetto(Long idSoggetto);
	
    
    @Select({
    	"select ref.data_creazione, ref_test.codfisc , ref_test.cognome, ref_test.nome, ref_test.datanascita ,  ", 
    	" ref_test.asr, ref_test.descr_struttura, ref.effectivetime_value, ", 
    	" ref.value_code, ref.value_displayname, dler ", 
    	"  from dmarefcovid_l_elaborazione_referto dler , dmarefcovid_t_referto_det ref ,  ", 
    	"  dmarefcovid_t_referto ref_test  ", 
    	"  where dler.elabdet_scarto = true ", 
    	"  and ref.intfrdet_id=dler.intfrdet_id ", 
    	"  and ref.id_messaggio= ref_test.id_messaggio ", 
    	"  and ref.value_code in ('P','N')  ", 
    	"  and trunc(dler.data_creazione) > to_date('30/06/2020','dd/mm/yyyy') ", 
    	"  and decode (ref_test.asr, '909','301', ", 
    	"                          '908','301', ", 
    	"                          decode ( ref_test.asr, '907','213', ", 
    	"                                              '906','210', ", 
    	"                                              decode ( ref_test.asr, '905','208', ", 
    	"                                                                   '904','203', ", 
    	"                                                                   ref_test.asr ", 
    	"                                                     ) ", 
    	"                                )) = #{idAsr,jdbcType=VARCHAR} ", 
    	"  and not exists ( ", 
    	"   select * from soggetto x ", 
    	"   where x.codice_fiscale = ref_test.codfisc ", 
    	"  ) ", 
    	"  and not exists ( ", 
    	"    select * from soggetto x ", 
    	"    where x.cognome = ref_test.cognome ", 
    	"    and x.nome = ref_test.nome ", 
    	"    and trunc(x.data_nascita) = trunc(ref_test.datanascita) ", 
    	"   )"
    })
    @Results({
    	@Result(column="codfisc", property="codFisc", jdbcType=JdbcType.VARCHAR),
    	@Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
    	@Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
    	@Result(column="datanascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
    	@Result(column="asr", property="asr", jdbcType=JdbcType.BIGINT),
    	@Result(column="descr_struttura", property="descrStruttura", jdbcType=JdbcType.VARCHAR),
    	@Result(column="effectivetime_value", property="effectiveTimeValue", jdbcType=JdbcType.TIMESTAMP),
    	@Result(column="value_code", property="valueCode", jdbcType=JdbcType.VARCHAR),
    	@Result(column="value_displayname", property="valueDisplayName", jdbcType=JdbcType.VARCHAR),
    	@Result(column="data_creazione", property="dataCreazione", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TamponeForElencoNonAbbinati> selectForNonAbbinatiByIdAsr(String idAsr);
    
    
}
