package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.RMedicoMedico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.Soggetto;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForTrasferimento;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisura;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.SoggettoForVisuraWithFlags;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseSoggettoMapper;

public interface SoggettoMapper extends BaseSoggettoMapper {
    
	@Insert({ "insert into soggetto (id_soggetto, codice_fiscale, id_asr, ", "cognome, nome, ", "comune_residenza_istat, comune_domicilio_istat, ",
		"indirizzo_domicilio, telefono_recapito, data_nascita, asl_residenza, asl_domicilio, email,id_tipo_soggetto, caso_importato, stato_contagio, regione_contagio)", "values (nextval('seq_id_soggetto'),  UPPER(#{codiceFiscale,jdbcType=VARCHAR}), #{idAsr,jdbcType=BIGINT}, ",
		"UPPER(#{cognome,jdbcType=VARCHAR}), UPPER(#{nome,jdbcType=VARCHAR}),  ",
		"#{comuneResidenzaIstat,jdbcType=VARCHAR}, #{comuneDomicilioIstat,jdbcType=VARCHAR}, ",
		"UPPER(#{indirizzoDomicilio,jdbcType=VARCHAR}), UPPER(#{telefonoRecapito,jdbcType=VARCHAR}),#{dataNascita,jdbcType=DATE},"
		+ "#{aslResidenza,jdbcType=VARCHAR}, #{aslDomicilio,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{idTipoSoggetto,jdbcType=BIGINT}, #{casoImportato,jdbcType=VARCHAR}, #{statoContagio,jdbcType=VARCHAR}, #{regioneContagio,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "idSoggetto")
	int insert(Soggetto record);
	
	@Insert({ "insert into soggetto (id_soggetto, codice_fiscale, ", "cognome, nome, ", "comune_residenza_istat, comune_domicilio_istat, ",
		"indirizzo_domicilio, telefono_recapito, data_nascita, asl_residenza, asl_domicilio, email,id_tipo_soggetto, caso_importato, stato_contagio, regione_contagio)", "values (nextval('seq_id_soggetto'),  UPPER(#{codiceFiscale,jdbcType=VARCHAR}),  ",
		"UPPER(#{cognome,jdbcType=VARCHAR}), UPPER(#{nome,jdbcType=VARCHAR}),  ",
		"#{comuneResidenzaIstat,jdbcType=VARCHAR}, #{comuneDomicilioIstat,jdbcType=VARCHAR}, ",
		"UPPER(#{indirizzoDomicilio,jdbcType=VARCHAR}), UPPER(#{telefonoRecapito,jdbcType=VARCHAR}),#{dataNascita,jdbcType=DATE},"
		+ "#{aslResidenza,jdbcType=VARCHAR}, #{aslDomicilio,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{idTipoSoggetto,jdbcType=BIGINT}, #{casoImportato,jdbcType=VARCHAR}, #{statoContagio,jdbcType=VARCHAR}, #{regioneContagio,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "idSoggetto")
	int insertSenzaAsr(Soggetto record);

	@Select({
		"select fnc_trasf_asr_sogg(#{idSoggetto,jdbcType=BIGINT},#{idAsr,jdbcType=BIGINT},#{dataEvento,jdbcType=DATE})"
	})
	int callMigrazioneAsr(@Param("idSoggetto") String idSoggetto, @Param("idAsr") String idAsr, @Param("dataEvento") String dataEvento);
	

	@Select({
		"select fnc_trasf_asr_sogg(#{idSoggetto,jdbcType=BIGINT},#{idAsr,jdbcType=BIGINT},#{dataEvento,jdbcType=DATE})"
	})
	int callAumentoVisibilitaAsr(@Param("idSoggetto") String idSoggetto, @Param("idAsr") String idAsr, @Param("dataEvento") String dataEvento);
	
	
	@Update({ "update soggetto", "set ",
			"id_asr = #{idAsr,jdbcType=BIGINT}",
			"where id_soggetto = #{idSoggetto,jdbcType=BIGINT}" })
	int updateAsrByPrimaryKey(Soggetto record);
	
    
    @Select({"<script>",
    	" select	",
    	"     ( case when  d.id_ente is not null then d.id_ente	",				
    	"          when  l.id_ente is not null then  l.id_ente  ",
    	"          else   g.id_ente ",
    	"          end     ",
    	"     ) as id_ente ",
    	" from soggetto a  ",
    	" left join comuni b on (b.istat_comune=a.comune_domicilio_istat)  ",
    	" left join asr c on (c.id_asr=a.id_asr)  ",
    	" left join ente d on (d.nome = ",
    	"              CASE WHEN a.asl_domicilio  = 'A.S.L. VERBANO CUSIO OSSOLA' THEN 'A.S.L. VERBANO-CUSIO-OSSOLA' ",
    	"                 when a.asl_domicilio = 'ASL BI' then 'A.S.L. BIELLA'   ",
    	"              else a.asl_domicilio END ",
    	" )                                     ",
    	" left join asr f on (f.id_asr=a.id_asr) ",
    	" left join ente g on (g.id_ente=f.id_ente) ",
    	" left join r_soggetto_asr h on (h.id_soggetto=a.id_soggetto and ",
    	"                                h.id_asr = (                    ",
    	"                                   select max(x.id_soggetto_asr) from r_soggetto_asr x  ",
    	"                                   where x.id_soggetto=h.id_soggetto ",
    	"                                 ) ",
    	"                                )  ",
    	" left join asr i on (i.id_asr=h.id_asr) ",
    	" left join ente l on (l.id_ente=i.id_ente) ",
    	" where a.id_soggetto = #{idSoggetto,jdbcType=BIGINT} ",
        "</script>"
    })
    @Results({
		@Result(column = "id_ente", property = "enteId", jdbcType = JdbcType.INTEGER)
    })
    Integer selectEnteByIdSoggetto(@Param("idSoggetto") Long idSoggetto);
    
  
    
    
    @Select({"<script>",
    	"select",
    	"    SOGGETTO.ID_SOGGETTO ,",
    	"    SOGGETTO.CODICE_FISCALE,",
    	"    SOGGETTO.ID_ASR,",
    	"    SOGGETTO.COGNOME,",
    	"    SOGGETTO.NOME,",
    	"    SOGGETTO.DATA_NASCITA,",
    	"    SOGGETTO.COMUNE_RESIDENZA_ISTAT,",
    	"    SOGGETTO.COMUNE_DOMICILIO_ISTAT,",
    	"    SOGGETTO.INDIRIZZO_DOMICILIO,",
    	"    SOGGETTO.TELEFONO_RECAPITO,",
    	"    SOGGETTO.ASL_RESIDENZA,",
    	"    SOGGETTO.ASL_DOMICILIO,",
    	"    SOGGETTO.id_tipo_soggetto,",
    	"    d_tipo_soggetto.desc_tipo_soggetto,",
    	"    COMUNI_DOM.NOME_COMUNE as COMUNE_DOMICILIO_NOME,",
    	"    COMUNI_RES.NOME_COMUNE as COMUNE_RESIDENZA_NOME,",
    	"    ASR.DESCRIZIONE as ASR_DESCRIZIONE,",
    	"    count(distinct id_tampone) as numero_tamponi,",
    	"    count(distinct decorso.id_decorso) as numero_decorsi",
        "	 ,d_max.id_tipo_evento as ultimo_id_tipo_evento",
    	"from",
    	"SOGGETTO",
    	"left join decorso on",
    	"    soggetto.id_Soggetto = decorso.id_soggetto",
    	"left join tampone on",
    	"    soggetto.id_soggetto = tampone.id_soggetto",
    	"left join COMUNI COMUNI_RES on",
    	"    SOGGETTO.COMUNE_RESIDENZA_ISTAT = COMUNI_RES.ISTAT_COMUNE",
    	"left join COMUNI COMUNI_DOM on",
    	"    SOGGETTO.COMUNE_DOMICILIO_ISTAT = COMUNI_DOM.ISTAT_COMUNE",
    	"left join ASR on",
    	"    SOGGETTO.ID_ASR = ASR.ID_ASR",
    	"left join d_tipo_soggetto on",
    	"    soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto",

    	"left join ASL ASL_RES on",
    	"    SOGGETTO.asl_residenza = ASL_RES.desc_asl_estesa",
    	"left join ASL ASL_DOM on",
    	"    SOGGETTO.asl_domicilio = ASL_DOM.desc_asl_estesa",
    	"left join r_soggetto_asr on",
    	"    SOGGETTO.id_soggetto = r_soggetto_asr.id_soggetto ",
    	"left join (select distinct on(id_soggetto) * from decorso order by" ,
    	"	id_soggetto, " ,
    	"	data_dimissioni desc nulls last ," , 
    	"	data_evento desc nulls last," ,
    	"	id_decorso desc) d_max on ", 
    	"	d_max.id_soggetto = soggetto.id_soggetto",
    	" <if test='idAsr != -1'> where soggetto.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_RES.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_DOM.id_asr=#{idAsr,jdbcType=BIGINT} or r_soggetto_asr.id_asr=#{idAsr,jdbcType=BIGINT} </if>",
    	"group by",
    	"    SOGGETTO.ID_SOGGETTO ,",
    	"    SOGGETTO.CODICE_FISCALE,",
    	"    SOGGETTO.ID_ASR,",
    	"    SOGGETTO.COGNOME,",
    	"    SOGGETTO.NOME,",
    	"    SOGGETTO.DATA_NASCITA,",
    	"    SOGGETTO.COMUNE_RESIDENZA_ISTAT,",
    	"    SOGGETTO.COMUNE_DOMICILIO_ISTAT,",
    	"    SOGGETTO.INDIRIZZO_DOMICILIO,",
    	"    SOGGETTO.TELEFONO_RECAPITO,",
    	"    SOGGETTO.ASL_RESIDENZA,",
    	"    SOGGETTO.ASL_DOMICILIO,",
    	"    COMUNI_DOM.NOME_COMUNE,",
    	"    COMUNI_RES.NOME_COMUNE,",
    	"    ASR.DESCRIZIONE",
    	"    ,d_max.id_tipo_evento",
    	"    ,d_tipo_soggetto.desc_tipo_soggetto",
    	"</script>"
    })
    
    
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
        @Result(column="numero_decorsi", property="numeroDecorsi", jdbcType=JdbcType.BIGINT),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ultimo_id_tipo_evento", property = "ultimoIdTipoEvento", jdbcType = JdbcType.INTEGER)
    })
    List<SoggettoForElenco> selectForElencoByIdAsr(@Param("idAsr") Long idAsr);
    // ^ cambiare questo con SoggettoForVisura che contiene tutto (il metodo  sotto e viene utilizzato per excle
    
    
    
    
    static final String SOGGETTI =
    		
    	
    	" with soggetti_filtrati as (                                                         " +
    	" 		select                                                                        " +
    	" 			SOGGETTO.*,                                                               " +
    	" 			count(distinct id_tampone) as numero_tamponi,                             " +
    	" 			ASR.DESCRIZIONE as asr_descrizione,                                       " +
    	" 			count(*) over() as full_count                                             " +
    	" 		from                                                                          " +
    	" 			SOGGETTO                                                                  " +
    	" 		left join tampone on                                                          " + //  necessario solo se ordinamento è per numero_tamponi
    	" 		 	soggetto.id_soggetto = tampone.id_soggetto                                " +
    	" 		left join COMUNI COMUNI_RES on                                                " +
    	" 			SOGGETTO.COMUNE_RESIDENZA_ISTAT = COMUNI_RES.ISTAT_COMUNE                 " +
    	" 		left join COMUNI COMUNI_DOM on                                                " +
    	" 			SOGGETTO.COMUNE_DOMICILIO_ISTAT = COMUNI_DOM.ISTAT_COMUNE                 " +
    	" 		left join ASR on                                                              " +
    	" 			SOGGETTO.ID_ASR = ASR.ID_ASR                                              " +
    	" 		left join ASL ASL_RES on                                                      " +
    	" 			SOGGETTO.asl_residenza = ASL_RES.desc_asl_estesa                          " +
    	" 		left join ASL ASL_DOM on                                                      " +
    	" 			SOGGETTO.asl_domicilio = ASL_DOM.desc_asl_estesa                          " +
    	" 		left join r_soggetto_asr on                                                   " +
    	" 			SOGGETTO.id_soggetto = r_soggetto_asr.id_soggetto                         " +
    	" 		left join (                                                                   " +
    	" 			select                                                                    " +
    	" 				distinct on                                                           " +
    	" 				(id_soggetto) *                                                       " +
    	" 			from                                                                      " +
    	" 				decorso                                                               " +
    	" 			order by                                                                  " +
    	" 				id_soggetto,                                                          " +
    	" 				data_dimissioni desc nulls last,                                      " +
    	" 				data_evento desc nulls last,                                          " +
    	" 				id_decorso desc ) d_max on                                            " +
    	" 			d_max.id_soggetto = soggetto.id_soggetto                                  " +
    	" 	    <where>                                                                       " +
    	" 	        <if test='idAsr!=null and idAsr != -1'> (soggetto.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_RES.id_asr=#{idAsr,jdbcType=BIGINT}                                                           " +
    	"                      or ASL_DOM.id_asr=#{idAsr,jdbcType=BIGINT} or r_soggetto_asr.id_asr=#{idAsr,jdbcType=BIGINT}) </if>                                                         " +
    	" 	        <if test='idTipoEvento != null'> AND d_max.id_tipo_evento IN (<foreach collection='idTipoEvento'  item='te' separator=','>#{te,jdbcType=BIGINT} </foreach>) </if>      " +
    	" 	        <if test='idTipoSoggetto!= null and idTipoSoggetto != -2 and idTipoSoggetto != -1'> AND soggetto.id_tipo_soggetto  = #{idTipoSoggetto,jdbcType=INTEGER} </if>          " +
    	" 	        <if test='idTipoSoggetto!= null and idTipoSoggetto == -1 '> AND soggetto.id_tipo_soggetto is null </if>                                                                " +


		"           <if test=\"codiceFiscale != null and codiceFiscale != '' \"> AND upper(soggetto.CODICE_FISCALE)=upper(#{codiceFiscale,jdbcType=VARCHAR}) </if>" +
		"           <if test=\"nome != null and nome != '' \">AND upper(soggetto.NOME) like upper(#{nome,jdbcType=VARCHAR}) </if>" +
		"           <if test=\"cognome != null and cognome != '' \"> AND upper(SOGGETTO.COGNOME) " +
		"               <if test='cognomeexact != null and cognomeexact == true '> = upper(#{cognome,jdbcType=VARCHAR}) </if> " +
		"               <if test='cognomeexact == null or cognomeexact == false  '> like upper(#{cognome,jdbcType=VARCHAR}) </if> " +
		"           </if>" +
		"           <if test=\"dataNascita != null \">AND soggetto.data_nascita= #{dataNascita,jdbcType=DATE} </if>" +
    	
    	
    	" 	        <if test='filter != null'>                                                        " +
    	" 	            AND (                                                                         " +
    	" 	                lower(soggetto.codice_fiscale)    like lower(#{filter,jdbcType=VARCHAR})  " +
    	" 	                OR lower(soggetto.cognome)        like lower(#{filter,jdbcType=VARCHAR})  " +
    	" 	                OR lower(soggetto.nome)           like lower(#{filter,jdbcType=VARCHAR})  " +
    	" 	                OR lower(COMUNI_DOM.nome_comune) like lower(#{filter,jdbcType=VARCHAR})   " +
    	" 	                OR lower(COMUNI_RES.nome_comune) like lower(#{filter,jdbcType=VARCHAR})   " +
    	" 	            )                                                                             " +
    	" 	        </if>                                                                             " +
    	" 	    </where>                                                                              " +
    	" 		group by SOGGETTO.ID_SOGGETTO,                                                               " +
    	" 		ASR.DESCRIZIONE                                             " + // necessario quando order by asr_descrizione
    	" 		<if test='oderByFiled != null'> order by ${oderByFiled} ${orderAscDesc} </if>                " + //CODICE_FISCALE, COGNOME, COMUNE_DOMICILIO_ISTAT, COMUNE_RESIDENZA_ISTAT, TELEFONO_RECAPITO, NUMERO_TAMPONI, ASR_DESCRIZIONE
    	" 		<if test='limit != null and limit !=-1'> offset #{offset,jdbcType=BIGINT} limit  #{limit,jdbcType=BIGINT}</if> " +
    	" 		) " +
    		
    		
    		
    	" select                                                                            " +
    	"     SOGGETTO.ID_SOGGETTO ,                                                        " +
    	"     SOGGETTO.CODICE_FISCALE,                                                      " +
    	"     SOGGETTO.ID_ASR,                                                              " +
    	"     SOGGETTO.COGNOME,                                                             " +
    	"     SOGGETTO.NOME,                                                                " +
    	"     SOGGETTO.DATA_NASCITA,                                                        " +
    	"     SOGGETTO.COMUNE_RESIDENZA_ISTAT,                                              " +
    	"     SOGGETTO.COMUNE_DOMICILIO_ISTAT,                                              " +
    	"     SOGGETTO.INDIRIZZO_DOMICILIO,                                                 " +
    	"     SOGGETTO.TELEFONO_RECAPITO,                                                   " +
    	"     SOGGETTO.ASL_RESIDENZA,                                                       " +
    	"     SOGGETTO.ASL_DOMICILIO,                                                       " +
    	"     SOGGETTO.id_tipo_soggetto,                                                    " +
    	"     d_tipo_soggetto.desc_tipo_soggetto,                                           " +
    	"     COMUNI_DOM.NOME_COMUNE as COMUNE_DOMICILIO_NOME,                              " +
    	"     COMUNI_RES.NOME_COMUNE as COMUNE_RESIDENZA_NOME,                              " +
    	"     ASR.DESCRIZIONE as ASR_DESCRIZIONE,                                           " +
    	"     count(distinct id_tampone) as numero_tamponi,                                 " +
    	"     count(distinct decorso.id_decorso) as numero_decorsi                          " +
    	" 	 ,d_max.id_tipo_evento as ultimo_id_tipo_evento                                 " +
    	"     ,soggetto.full_count AS full_count                                                " +
    	" from                                                                              " +
    	" soggetti_filtrati as soggetto                                                     " +
    	" left join decorso on                                                              " +
    	"     soggetto.id_Soggetto = decorso.id_soggetto                                    " +
    	" left join tampone on                                                              " +
    	"     soggetto.id_soggetto = tampone.id_soggetto                                    " +
    	" left join COMUNI COMUNI_RES on                                                    " +
    	"     SOGGETTO.COMUNE_RESIDENZA_ISTAT = COMUNI_RES.ISTAT_COMUNE                     " +
    	" left join COMUNI COMUNI_DOM on                                                    " +
    	"     SOGGETTO.COMUNE_DOMICILIO_ISTAT = COMUNI_DOM.ISTAT_COMUNE                     " +
    	" left join ASR on                                                                  " +
    	"     SOGGETTO.ID_ASR = ASR.ID_ASR                                                  " +
    	" left join d_tipo_soggetto on                                                      " +
    	"     soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto                  " +
    	" left join ASL ASL_RES on                                                          " +
    	"     SOGGETTO.asl_residenza = ASL_RES.desc_asl_estesa                              " +
    	" left join ASL ASL_DOM on                                                          " +
    	"     SOGGETTO.asl_domicilio = ASL_DOM.desc_asl_estesa                              " +
    	" left join r_soggetto_asr on                                                       " +
    	"     SOGGETTO.id_soggetto = r_soggetto_asr.id_soggetto                             " +
    	" left join (select distinct on(id_soggetto) * from decorso order by                " +
    	" 	id_soggetto,                                                                    " +
    	" 	data_dimissioni desc nulls last ,                                               " +
    	" 	data_evento desc nulls last,                                                    " +
    	" 	id_decorso desc) d_max on                                                       " +
    	" 	d_max.id_soggetto = soggetto.id_soggetto                                        " +
    	" group by SOGGETTO.ID_SOGGETTO ,                                                   " +
    	"	SOGGETTO.CODICE_FISCALE,                                                        " +
    	"	SOGGETTO.ID_ASR,                                                                " +
    	"	SOGGETTO.COGNOME,                                                               " +
    	"	SOGGETTO.NOME,                                                                  " +
    	"	SOGGETTO.DATA_NASCITA,                                                          " +
    	"	SOGGETTO.COMUNE_RESIDENZA_ISTAT,                                                " +
    	"	SOGGETTO.COMUNE_DOMICILIO_ISTAT,                                                " +
    	"	SOGGETTO.INDIRIZZO_DOMICILIO,                                                   " +
    	"	SOGGETTO.TELEFONO_RECAPITO,                                                     " +
    	"	SOGGETTO.ASL_RESIDENZA,                                                         " +
    	"	SOGGETTO.ASL_DOMICILIO,                                                         " +
    	"	COMUNI_DOM.NOME_COMUNE,                                                         " +
    	"	COMUNI_RES.NOME_COMUNE,                                                         " +
    	"	ASR.DESCRIZIONE ,                                                               " +
    	"	d_max.id_tipo_evento ,                                                          " +
    	"	d_tipo_soggetto.desc_tipo_soggetto,                                             " +
    	"   soggetto.id_tipo_soggetto, "+
    	"   soggetto.full_count ";

    		
    @Select({
    	"<script>",
    	SOGGETTI,
//		" <if test='oderByFiled != null'> order by ${oderByFiled} ${orderAscDesc} </if>",
//		" <if test='limit !=-1'> offset #{offset,jdbcType=BIGINT} limit  #{limit,jdbcType=BIGINT}</if>",
    	"</script>"
    })
    
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
        @Result(column="numero_decorsi", property="numeroDecorsi", jdbcType=JdbcType.BIGINT),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ultimo_id_tipo_evento", property = "ultimoIdTipoEvento", jdbcType = JdbcType.INTEGER),
		@Result(column = "full_count", property = "totalCount", jdbcType = JdbcType.INTEGER)
        
        
        
    })
    List<SoggettoForElenco> selectForElencoByIdAsrPaged(
    		@Param("idAsr") Long idAsr, 
    		
			@Param("codiceFiscale") String codiceFiscale, 
			@Param("cognome")String cognome, 
			@Param("nome")String nome,
			@Param("cognomeexact")Boolean cognomeexact, 
			@Param("dataNascita")Date dataNascita,
    		
    		@Param("idTipoEvento") List<Long> idTipoEvento,
    		@Param("idTipoSoggetto") Integer idTipoSoggetto,
    		@Param("filter") String filter,
    		
    		@Param("oderByFiled") String oderByFiled,
    		@Param("orderAscDesc") String orderAscDesc,
    		@Param("offset") Long offset,
    		@Param("limit") Long limit
    		);
    
    // ^ cambiare questo con SoggettoForVisura che contiene tutto (il metodo  sotto e viene utilizzato per excle   
    
    @Select(
        	{
        	"<script>",
        	"select ",
        	SoggettoSelectSQL.selectForSoggetto,
        	",", SoggettoSelectSQL.selectForDecorsoDmax,
        	",", SoggettoSelectSQL.selectForArea,
        	",", SoggettoSelectSQL.selectForStruttura,
        	",", SoggettoSelectSQL.selectForEnte,
        	",", SoggettoSelectSQL.selectForDTipoEvento,
        	",", SoggettoSelectSQL.selectForAsr,
        	",", SoggettoSelectSQL.selectForComuneRicovero,
        	",", SoggettoSelectSQL.selectForComuneDomicilio,
        	",", SoggettoSelectSQL.selectForComuneResidenza,
        	",", SoggettoSelectSQL.selectForTampone,  
        	", d_tipo_soggetto.desc_tipo_soggetto ",
        	" from soggetto s",
        	"    left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto",
    		"    left join decorso d on s.id_soggetto=d.id_soggetto",
        	"    left join ASR on s.ID_ASR = ASR.ID_ASR",
        	"    left join (select distinct on(id_soggetto) * from decorso order by" ,
        	"				id_soggetto, " ,
        	"				data_dimissioni desc nulls last ," , 
        	"				data_evento desc nulls last," ,
        	"				id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto",
    		"    left join area a on a.id_area=d_max.id_area",
    		"    left join struttura st on st.id_struttura=a.id_struttura",
    		"    left join ente e on e.id_ente=st.id_ente",
    		"    left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento",
    		"    LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune ",
            "	 LEFT join comuni comuni_residenza on s.comune_residenza_istat = comuni_residenza.istat_comune ",
            "	 LEFT join comuni comuni_domicilio on s.comune_domicilio_istat = comuni_domicilio.istat_comune ",
        	"    left join (select distinct on(id_soggetto) * from tampone ",
        	"				where id_ris_tamp is not null" ,  // l'ultimo tampone con esito negativo o positivo
         	"			order by" ,
        	"				id_soggetto, " ,
        	"				data_test desc nulls last " , 
        	"				) tam on tam.id_soggetto = s.id_soggetto",
        	"    left join ASL ASL_RES on s.asl_residenza = ASL_RES.desc_asl_estesa ",
        	"    left join ASL ASL_DOM on s.asl_domicilio = ASL_DOM.desc_asl_estesa ",
          	"    left join r_soggetto_asr on s.id_soggetto = r_soggetto_asr.id_soggetto ",
          	"    WHERE s.id_soggetto = s.id_soggetto ",
        	"    <if test='idAsr != -1'> AND ( s.id_asr=#{idAsr,jdbcType=BIGINT} "
			        	+ "       or ASL_RES.id_asr=#{idAsr,jdbcType=BIGINT} "
			        	+ "       or ASL_DOM.id_asr=#{idAsr,jdbcType=BIGINT} "
			        	+ "       or r_soggetto_asr.id_asr=#{idAsr,jdbcType=BIGINT} )</if>",
        	"    <if test='codiceFiscale != null'>   AND lower(s.codice_fiscale) like lower(#{codiceFiscale,jdbcType=VARCHAR}) </if>",
        	"    <if test='cognome != null'>         AND lower(s.cognome)        like lower(#{cognome,jdbcType=VARCHAR}) </if>",
        	"    <if test='nome != null'>            AND lower(s.nome)           like lower(#{nome,jdbcType=VARCHAR}) </if>",
        	"    <if test='idTipoEvento != null'>    AND d_max.id_tipo_evento IN (<foreach collection='idTipoEvento'  item='te' separator=','>#{te,jdbcType=BIGINT} </foreach>) </if>",
        	"    <if test='idTipoSoggetto!= null and idTipoSoggetto != -2 and idTipoSoggetto != -1'>  AND s.id_tipo_soggetto  = #{idTipoSoggetto,jdbcType=INTEGER} </if>",
        	"    <if test='idTipoSoggetto!= null and idTipoSoggetto == -1 '>  AND s.id_tipo_soggetto is null </if>",
        	"    <if test='comuneRicovero != null'>  AND lower(comuni_ricovero.nome_comune) like lower(#{comuneRicovero,jdbcType=VARCHAR}) </if>",
        	"    <if test='comuneDomicilio != null'> AND lower(comuni_domicilio.nome_comune) like lower(#{comuneDomicilio,jdbcType=VARCHAR}) </if>",
        	"    <if test='comuneResidenza != null'> AND lower(comuni_residenza.nome_comune) like lower(#{comuneResidenza,jdbcType=VARCHAR}) </if>",
        	"    <if test='filter != null'> ",
        	"        AND ( ",
        	"            lower(s.codice_fiscale)    like lower(#{filter,jdbcType=VARCHAR}) ",
        	"            OR lower(s.cognome)        like lower(#{filter,jdbcType=VARCHAR}) ",
        	"            OR lower(s.nome)           like lower(#{filter,jdbcType=VARCHAR}) ",
        	"            OR lower(comuni_ricovero.nome_comune)  like lower(#{filter,jdbcType=VARCHAR}) ",
        	"            OR lower(comuni_domicilio.nome_comune) like lower(#{filter,jdbcType=VARCHAR}) ",
        	"            OR lower(comuni_residenza.nome_comune) like lower(#{filter,jdbcType=VARCHAR}) ",
        	"        )",
        	"    </if>",
        	
        	"group by " , 
        	SoggettoSelectSQL.groupbyForSoggetto,
        	",", SoggettoSelectSQL.groupbyForDecorsoDmax,
        	",", SoggettoSelectSQL.groupbyForArea,
        	",", SoggettoSelectSQL.groupbyForStruttura,
        	",", SoggettoSelectSQL.groupbyForEnte,
        	",", SoggettoSelectSQL.groupbyForDTipoEvento,
        	",", SoggettoSelectSQL.groupbyForAsr,
        	",", SoggettoSelectSQL.groupbyForComuneRicovero,
        	",", SoggettoSelectSQL.groupbyForComuneResidenza,
        	",", SoggettoSelectSQL.groupbyForComuneDomicilio,
        	",", SoggettoSelectSQL.groupbyForTampone,
        	", desc_tipo_soggetto", 
        	"</script>"
        })
        @Results({
            @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
            @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
    		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
    		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

            @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
    		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
    		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),

    		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
            
    		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_residenza_nome_comune", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_residenza_istat_comune", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_domicilio_nome_comune", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_domicilio_istat_comune", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),

            @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
            @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
                  
            @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
            @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
            
            @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
            
            @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
            @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
            
            
            @Result(column = "tam_id_tampone", property = "tampone.idTampone", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_soggetto", property = "tampone.idSoggetto", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_id_ris_tamp", property = "tampone.idRisTamp", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_test", property = "tampone.dataTest", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "tam_criterio_epidemiologico_covid19", property = "tampone.criterioEpidemiologicoCovid19", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tam_id_test_covid", property = "tampone.idTestCovid", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_inserimento_richiesta", property = "tampone.dataInserimentoRichiesta", jdbcType = JdbcType.TIMESTAMP),

            
        })
        List<SoggettoForVisura> selectForElencoVisuraByIdAsr(
        		@Param("idAsr") Long idAsr,
        		@Param("codiceFiscale") String codiceFiscale,
        		@Param("cognome") String cognome,
        		@Param("nome") String nome,
        		@Param("idTipoEvento") List<Long> idTipoEvento,
        		@Param("idTipoSoggetto") Integer idTipoSoggetto,
        		@Param("comuneRicovero") String comuneRicovero,
        		@Param("comuneDomicilio") String comuneDomicilio,
        		@Param("comuneResidenza") String comuneResidenza, 
        		@Param("filter") String filter
        		);

    
    
    
    
    
    String SOGGETTI_TRASF = "select " +
    	     SoggettoSelectSQL.selectForSoggetto +
    	     ", " + SoggettoSelectSQL.selectForDecorsoDmax +
    	     ", " + SoggettoSelectSQL.selectForArea +
    	     ", " + SoggettoSelectSQL.selectForStruttura +
    	     ", " + SoggettoSelectSQL.selectForEnte +
    	     ", " + SoggettoSelectSQL.selectForDTipoEvento +
    	     ", " + SoggettoSelectSQL.selectForAsr +
    	     ", " + SoggettoSelectSQL.selectForComuneRicovero +
    	     ",  d_tipo_soggetto.desc_tipo_soggetto " +
    	     ",     count(*) OVER() AS full_count " +
    	     " from soggetto s " +
    	     " left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto " +
    	     "    left join decorso d on s.id_soggetto=d.id_soggetto " +
    	     "    left join ASR on s.ID_ASR = ASR.ID_ASR " +
    	     "    left join (select distinct on(id_soggetto) * from decorso order by " +
    	     "				id_soggetto, " +
    	     "				data_dimissioni desc nulls last , " +
    	     "				data_evento desc nulls last, " +
    	     "				id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto " +
    	     "    left join area a on a.id_area=d_max.id_area" +
    	     "    left join struttura st on st.id_struttura=a.id_struttura" +
    	     "    left join ente e on e.id_ente=st.id_ente" +
    	     "    left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento" +
    	     "    LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune " +
    	     " left join ASL ASL_RES on " +
    	     "    s.asl_residenza = ASL_RES.desc_asl_estesa " +
    	     " left join ASL ASL_DOM on " +
    	     "    s.asl_domicilio = ASL_DOM.desc_asl_estesa " +
    	     " left join r_soggetto_asr on" +
    	     "    s.id_soggetto = r_soggetto_asr.id_soggetto " +
    	     "   where d_max.id_tipo_evento in   " +
    	     "		<foreach item='item' index='index' collection='idTipoEvento' " +
    	     "		open='(' separator=',' close=')'>" +
    	     "			#{item} " +
    	     "		</foreach>" +
    	     " 	<if test='idAsr != -1'> and (s.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_RES.id_asr=#{idAsr,jdbcType=BIGINT} or ASL_DOM.id_asr=#{idAsr,jdbcType=BIGINT} or r_soggetto_asr.id_asr=#{idAsr,jdbcType=BIGINT}) </if>" +
    	     " 	<if test='idSoggetto != null'> and s.id_soggetto=#{idSoggetto,jdbcType=BIGINT} </if>" +
    	     
    		"    <if test='filter != null'> " +
    		"        AND ( " +
    		"            lower(s.codice_fiscale)    like lower(#{filter,jdbcType=VARCHAR}) " +
    		"            OR lower(s.cognome)        like lower(#{filter,jdbcType=VARCHAR}) " +
    		"            OR lower(s.nome)           like lower(#{filter,jdbcType=VARCHAR}) " +
    		"            OR lower(s.asl_residenza)  like lower(#{filter,jdbcType=VARCHAR}) " +
    		"            OR lower(s.asl_domicilio)  like lower(#{filter,jdbcType=VARCHAR}) " +
    		"        )" +
    		"    </if>" +
    	     
    	     " group by " + 
    	     SoggettoSelectSQL.groupbyForSoggetto+
    	     ", " + SoggettoSelectSQL.groupbyForDecorsoDmax+
    	     ", " + SoggettoSelectSQL.groupbyForArea+
    	     ", " + SoggettoSelectSQL.groupbyForStruttura+
    	     ", " + SoggettoSelectSQL.groupbyForEnte+
    	     ", " + SoggettoSelectSQL.groupbyForDTipoEvento+
    	     ", " + SoggettoSelectSQL.groupbyForAsr+
    	     ", " + SoggettoSelectSQL.groupbyForComuneRicovero+
    	     ",  desc_tipo_soggetto" ;
    
    
//    @Select(
//        	{
//        	"<script>",
//        	" select count(*) from ( ",
//        	SOGGETTI_TRASF,
//        	" ) as count_table_inner",
//        	"</script>"
//        })
//    Long selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEventoCount(
//    		@Param("idAsr") Long idAsr, 
//    		@Param("idSoggetto") Long idSoggetto,
//    		@Param("idTipoEvento") Integer[] idTipoEvento,
//    		@Param("filter") String filter
//    		);
    
    
    
    @Select(
    	{
    	"<script>",
    	SOGGETTI_TRASF,
		" <if test='oderByFiled != null'> order by ${oderByFiled} ${orderAscDesc} </if>",
		" <if test='limit !=-1'> offset #{offset,jdbcType=BIGINT} limit  #{limit,jdbcType=BIGINT}</if>",
    	"</script>"
    })
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

        @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
        @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
        @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
        @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),

		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
        @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
        
		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),
		
        @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione_asr", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
              
        @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
        @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
        @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
        @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
        @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
        @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
        
        @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
        @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
        @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
        
        @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
        @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
        @Result(column = "full_count", property = "totalCount", jdbcType = JdbcType.INTEGER)
    })
    List<SoggettoForTrasferimento> selectForElencoTrasferitiByIdAsrIdSoggettoListIdTipoEvento(
    		@Param("idAsr") Long idAsr, 
    		@Param("idSoggetto") Long idSoggetto,
    		@Param("idTipoEvento") Integer[] idTipoEvento,
    		@Param("filter") String filter,
    		
    		//Paginazione
    		@Param("oderByFiled") String oderByFiled,
    		@Param("orderAscDesc") String orderAscDesc,
    		@Param("offset") Long offset,
    		@Param("limit") Long limit
    		);
    
    
    @Select({"<script>",
        "select",
        "soggetto.id_soggetto , soggetto.codice_fiscale, soggetto.id_asr, soggetto.cognome, soggetto.nome, soggetto.data_nascita, soggetto.comune_residenza_istat, ",
        "soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, soggetto.telefono_recapito, ",
        "soggetto.asl_residenza, soggetto.asl_domicilio,soggetto.email,soggetto.id_tipo_soggetto, d_tipo_soggetto.desc_tipo_soggetto, ",
        "comuni_dom.nome_comune as comune_domicilio_nome, comuni_res.nome_comune as comune_residenza_nome, ",
        "asr.descrizione as asr_descrizione, (select count(*) from tampone where id_soggetto = soggetto.id_soggetto) as numero_tamponi, caso_importato, stato_contagio, regione_contagio ",
        "from soggetto "
        + "LEFT join comuni comuni_res on soggetto.comune_residenza_istat = comuni_res.istat_comune "
        + "LEFT join comuni comuni_dom on soggetto.comune_domicilio_istat = comuni_dom.istat_comune "
        + "left join d_tipo_soggetto on  soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto "
        + "LEFT join asr on  soggetto.id_asr = asr.id_asr "
        + "where ",
        " soggetto.id_soggetto=#{idSoggetto,jdbcType=BIGINT}  ",
        " <if test='idAsr != -1'> AND soggetto.id_asr=#{idAsr,jdbcType=BIGINT} </if>",
        "</script>"
    })
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
        @Result(column = "caso_importato", property = "casoImportato", jdbcType = JdbcType.VARCHAR),
        @Result(column = "stato_contagio", property = "statoContagio", jdbcType = JdbcType.VARCHAR),
        @Result(column = "regione_contagio", property = "regioneContagio", jdbcType = JdbcType.VARCHAR)
    })
    SoggettoForElenco selectForElencoByIdSoggetto(@Param("idSoggetto") Long idSoggetto, @Param("idAsr") Long idAsr);
    
    @Select({"<script>",
        "select",
        "soggetto.id_soggetto , soggetto.codice_fiscale, soggetto.id_asr, soggetto.cognome, soggetto.nome, soggetto.data_nascita, soggetto.comune_residenza_istat, ",
        "soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, soggetto.telefono_recapito, ",
        "soggetto.asl_residenza, soggetto.asl_domicilio,soggetto.email,soggetto.id_tipo_soggetto, d_tipo_soggetto.desc_tipo_soggetto, ",
        "comuni_dom.nome_comune as comune_domicilio_nome, comuni_res.nome_comune as comune_residenza_nome, ",
        "asr.descrizione as asr_descrizione, (select count(*) from tampone where id_soggetto = soggetto.id_soggetto) as numero_tamponi ",
        "from soggetto "
        + "LEFT join comuni comuni_res on soggetto.comune_residenza_istat = comuni_res.istat_comune "
        + "LEFT join comuni comuni_dom on soggetto.comune_domicilio_istat = comuni_dom.istat_comune "
        + "left join d_tipo_soggetto on  soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto "
        + "LEFT join asr on  soggetto.id_asr = asr.id_asr "
        + "where ",
        " soggetto.id_soggetto=#{idSoggetto,jdbcType=BIGINT}  ",
        " <if test='idAsr != -1'> AND (soggetto.id_asr=#{idAsr,jdbcType=BIGINT} OR #{idAsr,jdbcType=BIGINT} in (SELECT id_asr from r_soggetto_asr rsa where id_soggetto =#{idSoggetto,jdbcType=BIGINT} ) )</if>",
        "</script>"
    })
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR)
    })
    SoggettoForElenco selectForElencoByIdSoggettoASR(@Param("idSoggetto") Long idSoggetto, @Param("idAsr") Long idAsr);
    
    @Select({"<script>",
        "select",
        "soggetto.id_soggetto , soggetto.codice_fiscale, soggetto.id_asr, soggetto.cognome, soggetto.nome, soggetto.data_nascita, soggetto.comune_residenza_istat, ",
        "soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, soggetto.telefono_recapito, ",
        "soggetto.asl_residenza, soggetto.asl_domicilio,soggetto.email,soggetto.id_tipo_soggetto, d_tipo_soggetto.desc_tipo_soggetto, ",
        "comuni_dom.nome_comune as comune_domicilio_nome, comuni_res.nome_comune as comune_residenza_nome, ",
        "asr.descrizione as asr_descrizione, (select count(*) from tampone where id_soggetto = soggetto.id_soggetto) as numero_tamponi ",
        "from soggetto ",
    	"	 left join r_soggetto_aura rsa on soggetto.id_soggetto = rsa.id_soggetto ",
    	"	 left join r_medico_sogg_aura rmsa on rmsa.id_aura_sogg = rsa.id_aura ",
    	"	 left join r_medico_soggetto rms on soggetto.id_soggetto = rms.id_soggetto ",
    	" LEFT join comuni comuni_res on soggetto.comune_residenza_istat = comuni_res.istat_comune ",
        " LEFT join comuni comuni_dom on soggetto.comune_domicilio_istat = comuni_dom.istat_comune ",
        " left join d_tipo_soggetto on  soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto ",
        " LEFT join asr on  soggetto.id_asr = asr.id_asr "
        + "where ",
        " soggetto.id_soggetto=#{idSoggetto,jdbcType=BIGINT}  ",
        "  AND (rmsa.id_medico=#{idMedico,jdbcType=BIGINT} or rms.id_medico=#{idMedico,jdbcType=BIGINT}  ) ",
        "</script>"
    })
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR)
    })
    SoggettoForElenco selectForElencoByIdSoggettoIdMedico(@Param("idSoggetto") Long idSoggetto, @Param("idMedico") Long idMedico);


    

    @Select({"<script>",
        "select",
        "soggetto.id_soggetto , soggetto.codice_fiscale, soggetto.id_asr, soggetto.cognome, soggetto.nome, soggetto.data_nascita, soggetto.comune_residenza_istat, ",
        "soggetto.comune_domicilio_istat, soggetto.indirizzo_domicilio, soggetto.telefono_recapito, soggetto.email, ",
        "soggetto.asl_residenza, soggetto.asl_domicilio",
        " from soggetto ",
        " where codice_fiscale=#{codiceFiscale,jdbcType=VARCHAR} ",
        " <if test='idAsr != -1'> and soggetto.id_asr=#{idAsr,jdbcType=BIGINT} </if>",
        "</script>"
    })
    @Results({
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
        @Result(column="data_nascita_str", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR)
    })
    List<Soggetto> selectForElencoByCodiceFiscaleIdAsr(@Param("codiceFiscale") String codiceFiscale,@Param("idAsr") Long idAsr);

    
    // soggetto for visura
    
    @Select(
        	{
        	"<script>",
        	"select ",
        	SoggettoSelectSQL.selectForSoggetto,
        	",", SoggettoSelectSQL.selectForDecorsoDmax,
        	",", SoggettoSelectSQL.selectForArea,
        	",", SoggettoSelectSQL.selectForStruttura,
        	",", SoggettoSelectSQL.selectForEnte,
        	",", SoggettoSelectSQL.selectForDTipoEvento,
        	",", SoggettoSelectSQL.selectForAsr,
        	",", SoggettoSelectSQL.selectForComuneRicovero,
        	",", SoggettoSelectSQL.selectForComuneDomicilio,
        	",", SoggettoSelectSQL.selectForComuneResidenza,
        	",", SoggettoSelectSQL.selectForTampone,        	
        	", d_tipo_soggetto.desc_tipo_soggetto ",
        	", fgdwh.attualmente_positivo flags_attualmente_positivo ",
        	" from soggetto s",
        	" left join (select id_soggetto,  decode(attualmente_positivo,cast (0.0 as double precision),'NO','SI') as attualmente_positivo ", 
        	"			from v_ro_soggetti_flag_dwh)", //from sset_meta_toolchain_ro.soggetti_flag_dwh)
        	"							fgdwh on s.id_soggetto = fgdwh.id_soggetto",
        	" left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto",
    		"    left join decorso d on s.id_soggetto=d.id_soggetto",
        	"    left join ASR on s.ID_ASR = ASR.ID_ASR",
        	"    left join (select distinct on(id_soggetto) * from decorso order by" ,
        	"				id_soggetto, " ,
        	"				data_dimissioni desc nulls last ," , 
        	"				data_evento desc nulls last," ,
        	"				id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto",
    		"    left join area a on a.id_area=d_max.id_area",
    		"    left join struttura st on st.id_struttura=a.id_struttura",
    		"    left join ente e on e.id_ente=st.id_ente",
    		"    left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento",
    		"    LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune ",
            "	 LEFT join comuni comuni_residenza on s.comune_residenza_istat = comuni_residenza.istat_comune ",
            "	 LEFT join comuni comuni_domicilio on s.comune_domicilio_istat = comuni_domicilio.istat_comune ",
        	"    left join (select distinct on(id_soggetto) * from tampone ",
        	"				where id_ris_tamp is not null" ,  // l'ultimo tampone con esito negativo o positivo
         	"			order by" ,
        	"				id_soggetto, " ,
        	"				data_test desc nulls last " , 
        	"				) tam on tam.id_soggetto = s.id_soggetto",
        	"    left join (select distinct on(id_soggetto) * from tampone ",
         	"			order by" ,
        	"				id_soggetto, " ,
        	"				data_test desc nulls last " , 
        	"				) tam_last on tam_last.id_soggetto = s.id_soggetto",
        	"   where ",
        	"		(",
        	"			s.comune_domicilio_istat = #{istatComune} OR ",
        	"			s.comune_residenza_istat = #{istatComune} OR ",
        	"			(",
        	"				d_max.comune_ricovero_istat = #{istatComune} ",
        	"			 AND ",
        	"				d_max.id_tipo_evento in   ",
            "				<foreach item='item' index='index' collection='list' ",
            "					open='(' separator=',' close=')'>",
            "					#{item} ",
            "				</foreach>",
        	"			) ",
        	"		) ", 	
        	"group by " , 
        	SoggettoSelectSQL.groupbyForSoggetto,
        	",", SoggettoSelectSQL.groupbyForDecorsoDmax,
        	",", SoggettoSelectSQL.groupbyForArea,
        	",", SoggettoSelectSQL.groupbyForStruttura,
        	",", SoggettoSelectSQL.groupbyForEnte,
        	",", SoggettoSelectSQL.groupbyForDTipoEvento,
        	",", SoggettoSelectSQL.groupbyForAsr,
        	",", SoggettoSelectSQL.groupbyForComuneRicovero,
        	",", SoggettoSelectSQL.groupbyForComuneResidenza,
        	",", SoggettoSelectSQL.groupbyForComuneDomicilio,
        	",", SoggettoSelectSQL.groupbyForTampone,
        	", desc_tipo_soggetto", 
        	", fgdwh.attualmente_positivo ",
        	"</script>"
        })
        @Results({
            @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
            @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
    		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
    		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

            @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
//            @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
//            @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
    		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
//    		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
    		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),

    		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
            
    		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_residenza_nome_comune", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_residenza_istat_comune", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_domicilio_nome_comune", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_domicilio_istat_comune", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),

            @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
            @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
                  
            @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
            @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
            
            @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
            
            @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
            @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
            
            
            @Result(column = "tam_id_tampone", property = "tampone.idTampone", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_soggetto", property = "tampone.idSoggetto", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_id_ris_tamp", property = "tampone.idRisTamp", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_test", property = "tampone.dataTest", jdbcType = JdbcType.TIMESTAMP),
			
			@Result(column = "flags_attualmente_positivo", property = "attualmentePositivo", jdbcType = JdbcType.VARCHAR),

            
        })
        List<SoggettoForVisuraWithFlags> selectForElencoVisuraByIdTipoEventoByDomicilioByTampone(
        		@Param("istatComune") String istatComune, 
        		@Param("list") Integer[] listIdTipoEvento);

    
    

    @Select(
        	{
        	"<script>",
        	"select ",
        	SoggettoSelectSQL.selectForSoggetto,
        	",", SoggettoSelectSQL.selectForDecorsoDmax,
        	",", SoggettoSelectSQL.selectForArea,
        	",", SoggettoSelectSQL.selectForStruttura,
        	",", SoggettoSelectSQL.selectForEnte,
        	",", SoggettoSelectSQL.selectForDTipoEvento,
        	",", SoggettoSelectSQL.selectForAsr,
        	",", SoggettoSelectSQL.selectForComuneRicovero,
        	",", SoggettoSelectSQL.selectForComuneDomicilio,
        	",", SoggettoSelectSQL.selectForComuneResidenza,
        	",", SoggettoSelectSQL.selectForTampone,        	
        	", d_tipo_soggetto.desc_tipo_soggetto ",
        	" from soggetto s",
        	" left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto",
        	"	 left join r_soggetto_aura rsa on s.id_soggetto = rsa.id_soggetto ",
        	"	 left join r_medico_soggetto rms on s.id_soggetto = rms.id_soggetto ",
        	"	 left join r_medico_sogg_aura rmsa on rmsa.id_aura_sogg = rsa.id_aura ",
    		"    left join decorso d on s.id_soggetto=d.id_soggetto",
        	"    left join ASR on s.ID_ASR = ASR.ID_ASR",
        	"    left join (select distinct on(id_soggetto) * from decorso order by" ,
        	"				id_soggetto, " ,
        	"				data_dimissioni desc nulls last ," , 
        	"				data_evento desc nulls last," ,
        	"				id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto",
    		"    left join area a on a.id_area=d_max.id_area",
    		"    left join struttura st on st.id_struttura=a.id_struttura",
    		"    left join ente e on e.id_ente=st.id_ente",
    		"    left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento",
    		"    LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune ",
            "	 LEFT join comuni comuni_residenza on s.comune_residenza_istat = comuni_residenza.istat_comune ",
            "	 LEFT join comuni comuni_domicilio on s.comune_domicilio_istat = comuni_domicilio.istat_comune ",
        	"    left join (select distinct on(id_soggetto) * from tampone ",
        	"				where id_ris_tamp is not null" ,  // l'ultimo tampone con esito negativo o positivo
         	"			order by" ,
        	"				id_soggetto, " ,
        	"				data_test desc nulls last " , 
        	"				) tam on tam.id_soggetto = s.id_soggetto",
        	"   where ",
        	"		rmsa.id_medico = #{idMedico} or ",
        	"		rms.id_medico = #{idMedico}",
        	"group by " , 
        	SoggettoSelectSQL.groupbyForSoggetto,
        	",", SoggettoSelectSQL.groupbyForDecorsoDmax,
        	",", SoggettoSelectSQL.groupbyForArea,
        	",", SoggettoSelectSQL.groupbyForStruttura,
        	",", SoggettoSelectSQL.groupbyForEnte,
        	",", SoggettoSelectSQL.groupbyForDTipoEvento,
        	",", SoggettoSelectSQL.groupbyForAsr,
        	",", SoggettoSelectSQL.groupbyForComuneRicovero,
        	",", SoggettoSelectSQL.groupbyForComuneResidenza,
        	",", SoggettoSelectSQL.groupbyForComuneDomicilio,
        	",", SoggettoSelectSQL.groupbyForTampone,
        	", desc_tipo_soggetto", 
        	"</script>"
        })
        @Results({
            @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
            @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
    		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
    		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

            @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
    		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
    		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),

    		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
            
    		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_residenza_nome_comune", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_residenza_istat_comune", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_domicilio_nome_comune", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_domicilio_istat_comune", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),

            @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
            @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
                  
            @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
            @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
            
            @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
            
            @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
            @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
            
            
            @Result(column = "tam_id_tampone", property = "tampone.idTampone", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_soggetto", property = "tampone.idSoggetto", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_id_ris_tamp", property = "tampone.idRisTamp", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_test", property = "tampone.dataTest", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "tam_criterio_epidemiologico_covid19", property = "tampone.criterioEpidemiologicoCovid19", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tam_id_test_covid", property = "tampone.idTestCovid", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_inserimento_richiesta", property = "tampone.dataInserimentoRichiesta", jdbcType = JdbcType.TIMESTAMP),

			// TODO: qui andranno aggiunte le info del medico
			@Result(column = "id_medico", property = "medico.idMedico", jdbcType = JdbcType.BIGINT),
			@Result(column = "cf_medico", property = "medico.cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_reg", property = "medico.codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cognome_medico", property = "medico.cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nome_medico", property = "medico.nome", jdbcType = JdbcType.VARCHAR)
        })
        List<SoggettoForVisura> selectForElencoVisuraByMedico(
        		@Param("idMedico") Long idMedico);
    
    
    
    
    @Select(
        	{
        		
        		"<script>",
            	"with medicotitolato as ( ",
            	"with med as ( ",
            	"select tb.id_soggetto, tb.id_aura_sogg, ",
            	"tb.wt, ",
            	"rank() over (partition by tb.id_soggetto order by tb.wt,tb.ordine), tb.id_medico  From ( ",
            	"        with aa as ( ",
            	"        select b.id_soggetto, a.id_aura_sogg , 'aa'::text  wt, rank() over (partition by b.id_soggetto order by a.data_scelta desc) as ordine, a.id_medico ",
            	"        from r_medico_sogg_aura a,r_soggetto_aura b  ",
            	"        where b.id_aura=a.id_aura_sogg and a.data_revoca is null  ",
            	"        order by b.id_soggetto, ordine)   ",
            	"        , bb as (   ",
            	"        select c.id_soggetto, d.id_aura, 'bb'::text  wt, rank() over (partition by c.id_soggetto   order by c.id_medico desc) as ordine,c.id_medico  ",
            	"        from r_medico_soggetto c left join r_soggetto_aura d  ",
            	"        on d.id_soggetto=c.id_soggetto  ",
            	"        order by c.id_soggetto, ordine) ",
            	"        select * from aa ",
            	"        union   ",
            	"        select * from bb    ",
            	"        order by 1,3,4  ",
            	"        ) as tb  ",
            	"     ) select   ",
            	"     a.iD_soggetto,  ",
            	"     b.* from  med a, medico b where rank=1   ",
            	"     and b.id_medico=a.id_medico)   ",
            	"        select distinct   ",
            	"             s.id_soggetto, s.codice_fiscale, s.id_asr, s.cognome,  ",
            	"             s.nome, s.comune_residenza_istat, s.comune_domicilio_istat, s.indirizzo_domicilio, s.telefono_recapito,  ",
            	"             s.data_nascita, s.asl_residenza, s.asl_domicilio, s.id_tipo_soggetto, s.email ,  ",
            	"             d_max.id_decorso d_max_id_decorso,                          ",                                                                                   
            	"                d_max.id_tipo_evento d_max_id_tipo_evento,               ",                                                                                   
            	"                d_max.comune_ricovero_istat d_max_comune_ricovero_istat, ",                                                                                   
            	"                d_max.condizioni_cliniche d_max_condizioni_cliniche,     ",                                                                                   
            	"                d_max.sintomi d_max_sintomi,                             ",                                                                                   
            	"                d_max.data_dimissioni d_max_data_dimissioni,             ",                                                                                   
            	"                d_max.data_inizio_sint d_max_data_inizio_sint,           ",                                                                                   
            	"                d_max.data_evento d_max_data_evento,                     ",                                                                                   
            	"                d_max.id_area d_max_id_area,                             ",                                                                                   
            	"                d_max.note d_max_note,                                   ",                                                                                   
            	"                d_max.data_prev_fine_evento d_max_data_prev_fine_evento, ",                                                                                   
            	"                d_max.indirizzo_decorso d_max_indirizzo_decorso,         ",                                                                                   
            	"                d_max.decorso_presso d_max_decorso_presso ,              ",                                                                                   
            	"                d_max.descrizione_contesto d_max_descrizione_contesto ,  ",                                                                                   
            	"                d_max.luogo_paziente dmax_luogo_paziente,                ",                                                                                   
            	"                a.id_area a_id_area,                                     ",                                                                                   
            	"                a.id_struttura a_id_struttura,                           ",                                                                                   
            	"                a.id_d_area a_id_d_area,                                 ",                                                                                   
            	"                a.responsabile a_responsabile,                           ",                                                                                   
            	"                a.riferimento a_riferimento,                             ",                                                                                   
            	"                a.stato_validita a_stato_validita,                       ",                                                                                   
            	"                a.nome a_nome, st.id_struttura st_id_struttura,          ",                                                                                   
            	"             st.nome st_nome,                                            ",                                                                                   
            	"             st.natura  st_natura,                                       ",                                                                                   
            	"             st.id_ente st_id_ente,                                      ",                                                                                   
            	"             dte.id_tipo_evento dte_d_tipo_evento, dte.desc_tipo_evento dte_desc_tipo_evento, ",
            	"              e.id_ente e_id_ente, e.nome e_nome_ente,   ",
            	"             asr.id_asr asr_id_asr,asr.descrizione asr_descrizione,  ",
            	"             comuni_ricovero.nome_comune  comuni_ricovero_nome_comune,    comuni_ricovero.istat_comune   comuni_ricovero_istat_comune, ",
            	"             comuni_domicilio.nome_comune  comuni_domicilio_nome_comune, comuni_domicilio.istat_comune   comuni_domicilio_istat_comune, ",
            	"             comuni_residenza.nome_comune  comuni_residenza_nome_comune, comuni_residenza.istat_comune   comuni_residenza_istat_comune,",
            	"             tam.id_tampone tam_id_tampone,                                                                  ",                                        
            	"             tam.id_ris_tamp tam_id_ris_tamp,                                                                ",                                        
            	"             tam.criterio_epidemiologico_covid19 tam_criterio_epidemiologico_covid19,                        ",                                        
            	"             tam.id_test_covid tam_id_test_covid,                                                            ",                                        
            	"             tam.data_inserimento_richiesta tam_data_inserimento_richiesta,                                  ",                                        
            	"             tam.data_test tam_data_test                                                                     ",                                        
            	"             ,       d_tipo_soggetto.desc_tipo_soggetto ,                                                    ",                                        
            	"             medt.id_medico,                                                                                 ",                                        
            	"             medt.cf_medico,                                                                                 ",                                        
            	"             medt.codice_reg,                                                                                ",                                        
            	"             medt.cognome medico_cognome,                                                                                   ",                                        
            	"             medt.nome medico_nome",                                        
            	"             from soggetto s                                                                                 ",                                        
            	"             left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto             ",                                        
            	"             left join r_soggetto_aura rsa on s.id_soggetto = rsa.id_soggetto                                ",                                        
            	"             left join r_medico_soggetto rms on s.id_soggetto = rms.id_soggetto                              ",                                        
            	"             left join r_medico_sogg_aura rmsa on rmsa.id_aura_sogg = rsa.id_aura                            ",                                        
            	"             left join decorso d on s.id_soggetto=d.id_soggetto                                              ",                                        
            	"             left join ASR on s.ID_ASR = ASR.ID_ASR                                                          ",                                        
            	"             left join (select distinct on(id_soggetto) * from decorso order by                              ",                                        
            	"                        id_soggetto,                                                                         ",                                        
            	"                        data_dimissioni desc nulls last ,                                                    ",                                        
            	"                        data_evento desc nulls last,                                                         ",                                        
            	"                        id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto                          ",                                        
            	"             left join area a on a.id_area=d_max.id_area                                                     ",                                        
            	"             left join struttura st on st.id_struttura=a.id_struttura                                        ",                                        
            	"             left join ente e on e.id_ente=st.id_ente                                                        ",                                        
            	"             left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento                          ",                                        
            	"             LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune  ",                                        
            	"             LEFT join comuni comuni_residenza on s.comune_residenza_istat = comuni_residenza.istat_comune   ",                                        
            	"             LEFT join comuni comuni_domicilio on s.comune_domicilio_istat = comuni_domicilio.istat_comune   ",                                        
            	"             left join (select distinct on(id_soggetto) * from tampone where id_ris_tamp is not null order by id_soggetto, data_test desc nulls last ",
            	"                        ) tam on tam.id_soggetto = s.id_soggetto                                                               ",                            
            	"            left join medicotitolato medt on medt.id_soggetto=s.id_soggetto                                                    ",                            
            	"            where                                                                                                              ",                            
            	"          rmsa.id_medico = #{idMedico} or rms.id_medico = #{idMedico}   ",                            
            	"             or                                                                                                                ",                            
            	"                rmsa.id_medico in (select y.id_medico_delegante from r_medico_medico y where y.id_medico_delegato=#{idMedico} and y.data_cancellazione is null", 
            	"                and  now() between y.validita_inizio and coalesce(y.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy')))  ",                            
            	"                or                                                                                                             ",                            
            	"                rms.id_medico in (select z.id_medico_delegante from r_medico_medico z where z.id_medico_delegato=#{idMedico} and z.data_cancellazione is null",
            	"								and  now() between z.validita_inizio and coalesce(z.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy')))",
            	"            group by                                                                                                           ",                            
            	"            s.id_soggetto, s.codice_fiscale, s.id_asr, s.cognome,                                                              ",                            
            	"             s.nome, s.comune_residenza_istat, s.comune_domicilio_istat, s.indirizzo_domicilio, s.telefono_recapito,           ",                            
            	"             s.data_nascita, s.asl_residenza, s.asl_domicilio, s.id_tipo_soggetto, s.email ,                                   ",                            
            	"             d_max_id_decorso,d_max_id_tipo_evento,    d_max_comune_ricovero_istat, d_max_condizioni_cliniche, d_max_sintomi,  ",                            
            	"                d_max_data_dimissioni,                                     ",                                                                              
            	"                d_max_data_inizio_sint,                                    ",                                                                              
            	"                d_max_data_evento,                                         ",                                                                              
            	"                d_max_id_area,                                             ",                                                                              
            	"                d_max_note,                                                ",                                                                              
            	"                d_max_data_prev_fine_evento,                               ",                                                                              
            	"                d_max_indirizzo_decorso,                                   ",                                                                              
            	"                d_max_decorso_presso ,                                     ",                                                                              
            	"                d_max_descrizione_contesto ,                               ",                                                                              
            	"                dmax_luogo_paziente,                                       ",                                                                              
            	"                a_id_area,                                                 ",                                                                              
            	"                a_id_struttura,                                            ",                                                                              
            	"                a_id_d_area,                                               ",                                                                              
            	"                a_responsabile,                                            ",                                                                              
            	"                a_riferimento,                                             ",                                                                              
            	"                a_stato_validita,                                          ",                                                                              
            	"                st_id_struttura,                                           ",                                                                              
            	"             st_nome,                                                      ",                                                                              
            	"             st_natura,                                                    ",                                                                              
            	"             st_id_ente,                                                   ",                                                                              
            	"             dte_d_tipo_evento, dte_desc_tipo_evento,                      ",                                                                              
            	"              e_id_ente, e_nome_ente,                                      ",                                                                              
            	"             asr_id_asr,asr_descrizione,                                   ",                                                                              
            	"             comuni_ricovero.nome_comune,    comuni_ricovero.istat_comune ,",                                                                              
            	"             comuni_domicilio.nome_comune  , comuni_domicilio.istat_comune, ",                                                                             
            	"             comuni_residenza.nome_comune, comuni_residenza.istat_comune , ",                                                                              
            	"             tam.id_tampone ,                          ",                                                                                                      
            	"             tam.id_ris_tamp,                          ",                                                                                                      
            	"             tam.criterio_epidemiologico_covid19 ,     ",                                                                                                      
            	"             tam.id_test_covid ,                       ",                                                                                                      
            	"             tam.data_inserimento_richiesta ,          ",                                                                                                      
            	"             tam.data_test ,                           ",                                                                                                      
            	"             d_tipo_soggetto.desc_tipo_soggetto,       ",                                                                                                      
            	"             medt.id_medico,                           ",                                                                                                      
            	"             medt.cf_medico,                           ",                                                                                                      
            	"             medt.codice_reg,                          ",                                                                                                      
            	"             medt.cognome,                             ",                                                                                                      
            	"             medt.nome                                ",                                                                                                      
            	"</script>"
            	/*
        		"<script>",
        		//" --selectSoggettiForVisuraMmgByMedico ",
        		" with t_medico_soggetto as (",
        		"                 select ",
        		"                 b.id_soggetto, b.codice_fiscale, b.id_asr, b.cognome,  ",
        		"        	             b.nome, b.comune_residenza_istat, b.comune_domicilio_istat, b.indirizzo_domicilio, b.telefono_recapito,  ",
        		"        	             b.data_nascita, b.asl_residenza, b.asl_domicilio, b.id_tipo_soggetto, b.email , ",
        		"                  a.id_medico from r_medico_soggetto a, soggetto b where ",
        		"                 b.id_soggetto=a.id_soggetto and ",
        		"                 (a.id_medico = #{idMedico} or",
        		"                 a.id_medico in (select z.id_medico_delegante from r_medico_medico z where z.id_medico_delegato=#{idMedico} and z.data_cancellazione is null",
        		"        									and  now() between z.validita_inizio and coalesce(z.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))",
        		"                                            ))",
        		"                 union                                            ",
        		"                  select ",
        		"                  c.id_soggetto, c.codice_fiscale, c.id_asr, c.cognome,  ",
        		"        	             c.nome, c.comune_residenza_istat, c.comune_domicilio_istat, c.indirizzo_domicilio, c.telefono_recapito,  ",
        		"        	             c.data_nascita, c.asl_residenza, c.asl_domicilio, c.id_tipo_soggetto, c.email , ",
        		"                   a.id_medico from r_medico_sogg_aura a,r_soggetto_aura b, soggetto c where ",
        		"                  c.id_soggetto=b.id_soggetto and",
        		"                  (",
        		"                    a.id_aura_sogg = b.id_aura   and ",
        		"                    (a.id_medico =#{idMedico} ",
        		"                    or                                                                                                                                            ",
        		"        	        a.id_medico in ",
        		"                    		(select y.id_medico_delegante from r_medico_medico y where y.id_medico_delegato =#{idMedico} and y.data_cancellazione is null ",
        		"        	                and  now() between y.validita_inizio and coalesce(y.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))",
        		"                            )              ",
        		"                    )        )",
        		"                    )",
        		"                   , d_max as (",
        		"                   select tb.*, a.id_area a_id_area,                                                                                                                        ",
        		"        	                a.id_struttura a_id_struttura,                                                                                                              ",
        		"        	                a.id_d_area a_id_d_area,                                                                                                                    ",
        		"        	                a.responsabile a_responsabile,                                                                                                              ",
        		"        	                a.riferimento a_riferimento,                                                                                                                ",
        		"        	                a.stato_validita a_stato_validita,                                                                                                          ",
        		"        	                a.nome a_nome,",
        		"                              st.id_struttura st_id_struttura,                                                                                             ",
        		"        	             st.nome st_nome,                                                                                                                               ",
        		"        	             st.natura  st_natura,                                                                                                                          ",
        		"        	             st.id_ente st_id_ente,",
        		"                         dte.id_tipo_evento dte_d_tipo_evento, ",
        		"                         dte.desc_tipo_evento dte_desc_tipo_evento, ",
        		"        	             e.id_ente e_id_ente, e.nome e_nome_ente,",
        		"                         f.nome_comune  comuni_ricovero_nome_comune,    f.istat_comune   comuni_ricovero_istat_comune",
        		"                             from (",
        		"                   					(select distinct on(id_soggetto) * from decorso  order by                                                                     ",
        		"        	                        id_soggetto,data_dimissioni desc nulls last ,data_evento desc nulls last,id_decorso desc) as tb ",
        		"                                    left join area a on a.id_area=tb.id_area ",
        		"                                    left join struttura st on st.id_struttura=a.id_struttura ",
        		"                                    left join ente e on e.id_ente=st.id_ente                                                                                                ",
        		"        	             			left join d_tipo_evento dte on dte.id_tipo_evento=tb.id_tipo_evento ",
        		"                                    left join comuni f on tb.comune_ricovero_istat = f.istat_comune  ",
        		"                                    )",
        		"                                    ),",
        		" medicotitolato as ( ",
        		" with med as ( ",
        		"        	select tb.id_soggetto, tb.id_aura_sogg, ",
        		"        	tb.wt, ",
        		"        	rank() over (partition by tb.id_soggetto order by tb.wt,tb.ordine), tb.id_medico  From ( ",
        		"        	        with aa as ( ",
        		"        	        select b.id_soggetto, a.id_aura_sogg , 'aa'::text  wt, rank() over (partition by b.id_soggetto order by a.data_scelta desc) as ordine, a.id_medico ",
        		"        	        from r_medico_sogg_aura a,r_soggetto_aura b  ",
        		"        	        where b.id_aura=a.id_aura_sogg and a.data_revoca is null  ",
        		"                    and exists  (select 1  from r_medico_sogg_aura z,r_soggetto_aura x where",
        		"                     x.id_aura=z.id_aura_sogg and z.data_revoca is null  and ",
        		"                     x.id_soggetto=b.id_soggetto ",
        		"                    and   (z.id_medico =#{idMedico} ",
        		"                    or                                                                                                                                            ",
        		"        	        z.id_medico in ",
        		"                    		(select y.id_medico_delegante from r_medico_medico y where y.id_medico_delegato = #{idMedico} and y.data_cancellazione is null ",
        		"        	                and  now() between y.validita_inizio and coalesce(y.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))",
        		"                            )              ",
        		"                    ) ",
        		"                    )",
        		"        	        order by b.id_soggetto, ordine)   ",
        		"        	        , bb as (   ",
        		"        	        select c.id_soggetto, d.id_aura, 'bb'::text  wt, rank() over (partition by c.id_soggetto   order by c.id_medico desc) as ordine,c.id_medico  ",
        		"        	        from r_medico_soggetto c , r_soggetto_aura d  ",
        		"        	        where d.id_soggetto=c.id_soggetto ",
        		"                    and exists (select 1 from r_medico_soggetto z where z.id_soggetto=c.id_soggetto",
        		"                     and",
        		"                    (c.id_medico =#{idMedico} or",
        		"                 c.id_medico in (select z.id_medico_delegante from r_medico_medico z where z.id_medico_delegato=#{idMedico} and z.data_cancellazione is null",
        		"        									and  now() between z.validita_inizio and coalesce(z.validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))",
        		"                                            ))",
        		"                                            )",
        		"        	        order by c.id_soggetto, ordine) ",
        		"        	        select * from aa ",
        		"        	        union   ",
        		"        	        select * from bb    ",
        		"        	        order by 1,3,4  ",
        		"        	        ) as tb  ) select   ",
        		"        	     a.iD_soggetto,  ",
        		"        	     b.* from  med a, medico b where rank=1   ",
        		"        	     and b.id_medico=a.id_medico )                              ",
        		"                 select  s.id_soggetto, s.codice_fiscale, s.id_asr, s.cognome,  ",
        		"        	             s.nome, s.comune_residenza_istat, s.comune_domicilio_istat, s.indirizzo_domicilio, s.telefono_recapito,  ",
        		"        	             s.data_nascita, s.asl_residenza, s.asl_domicilio, s.id_tipo_soggetto, s.email ,  ",
        		"        	             d_max.id_decorso d_max_id_decorso,                                                                                                             ",
        		"        	                d_max.id_tipo_evento d_max_id_tipo_evento,                                                                                                  ",
        		"        	                d_max.comune_ricovero_istat d_max_comune_ricovero_istat,                                                                                    ",
        		"        	                d_max.condizioni_cliniche d_max_condizioni_cliniche,                                                                                        ",
        		"        	                d_max.sintomi d_max_sintomi,                                                                                                                ",
        		"        	                d_max.data_dimissioni d_max_data_dimissioni,                                                                                                ",
        		"        	                d_max.data_inizio_sint d_max_data_inizio_sint,                                                                                              ",
        		"        	                d_max.data_evento d_max_data_evento,                                                                                                        ",
        		"        	                d_max.id_area d_max_id_area,                                                                                                                ",
        		"        	                d_max.note d_max_note,                                                                                                                      ",
        		"        	                d_max.data_prev_fine_evento d_max_data_prev_fine_evento,                                                                                    ",
        		"        	                d_max.indirizzo_decorso d_max_indirizzo_decorso,                                                                                            ",
        		"        	                d_max.decorso_presso d_max_decorso_presso ,                                                                                                 ",
        		"        	                d_max.descrizione_contesto d_max_descrizione_contesto ,                                                                                     ",
        		"        	                d_max.luogo_paziente dmax_luogo_paziente ,",
        		"                            d_max.a_id_area,                                                                                                                        ",
        		"        	                d_max.a_id_struttura,                                                                                                              ",
        		"        	                d_max.a_id_d_area,                                                                                                                    ",
        		"        	                d_max.a_responsabile,                                                                                                              ",
        		"        	                d_max.a_riferimento,                                                                                                                ",
        		"        	                d_max.a_stato_validita,                                                                                                          ",
        		"        	                d_max.a_nome,",
        		"                            d_max.st_id_struttura,                                                                                             ",
        		"        	             d_max.st_nome,                                                                                                                               ",
        		"        	             d_max.st_natura,                                                                                                                          ",
        		"        	             d_max.st_id_ente,",
        		"                         d_max.dte_d_tipo_evento, ",
        		"                         d_max.dte_desc_tipo_evento, ",
        		"        	             d_max.e_id_ente, d_max.e_nome_ente,",
        		"                         c.id_asr asr_id_asr,c.descrizione asr_descrizione ,",
        		"                         d_max.comuni_ricovero_nome_comune,  d_max.comuni_ricovero_istat_comune,",
        		"                         cd.nome_comune  comuni_domicilio_nome_comune, cd.istat_comune   comuni_domicilio_istat_comune, ",
        		"        	             cr.nome_comune  comuni_residenza_nome_comune, cr.istat_comune   comuni_residenza_istat_comune,",
        		"                         tam.id_tampone tam_id_tampone,                                                                                                          ",
        		"        	             tam.id_ris_tamp tam_id_ris_tamp,                                                                                                        ",
        		"        	             tam.criterio_epidemiologico_covid19 tam_criterio_epidemiologico_covid19,                                                                ",
        		"        	             tam.id_test_covid tam_id_test_covid,                                                                                                    ",
        		"        	             tam.data_inserimento_richiesta tam_data_inserimento_richiesta,                                                                          ",
        		"        	             tam.data_test tam_data_test,",
        		"                           ts.desc_tipo_soggetto,",
        		"        	             medt.id_medico,                                                                                                                         ",
        		"        	             medt.cf_medico,                                                                                                                         ",
        		"        	             medt.codice_reg,                                                                                                                        ",
        		"        	             medt.cognome medico_cognome,                                                                                                                           ",
        		"        	             medt.nome medico_nome                                                         ",
        		"                 from t_medico_soggetto s",
        		"                  left join d_max on d_max.id_soggetto = s.id_soggetto ",
        		"                  left join asr c on c.id_asr=s.id_asr",
        		"                  LEFT join comuni cr on s.comune_residenza_istat = cr.istat_comune                                           ",
        		"        	      LEFT join comuni cd on s.comune_domicilio_istat = cd.istat_comune  ",
        		"                  left join (select distinct on(id_soggetto) * from tampone where id_ris_tamp is not null order by id_soggetto, data_test desc nulls last ",
        		"        	                        ) tam on tam.id_soggetto = s.id_soggetto                                                                                           ",
        		"        	      left join d_tipo_soggetto ts on  s.id_tipo_soggetto = ts.id_tipo_soggetto      ",
        		"                  left join medicotitolato medt on medt.id_soggetto=s.id_soggetto    ",
        		" </script>"*/
        })
        @Results({
            @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
            @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
    		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
    		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

            @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
            @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
    		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
    		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),

    		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
            
    		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_residenza_nome_comune", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_residenza_istat_comune", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_domicilio_nome_comune", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_domicilio_istat_comune", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),

            @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
            @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
                  
            @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
            @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
            
            @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
            
            @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
            @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
            
            
            @Result(column = "tam_id_tampone", property = "tampone.idTampone", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_soggetto", property = "tampone.idSoggetto", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_id_ris_tamp", property = "tampone.idRisTamp", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_test", property = "tampone.dataTest", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "tam_criterio_epidemiologico_covid19", property = "tampone.criterioEpidemiologicoCovid19", jdbcType = JdbcType.VARCHAR),
			@Result(column = "tam_id_test_covid", property = "tampone.idTestCovid", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_inserimento_richiesta", property = "tampone.dataInserimentoRichiesta", jdbcType = JdbcType.TIMESTAMP),

			// TODO: qui andranno aggiunte le info del medico
			@Result(column = "id_medico", property = "medico.idMedico", jdbcType = JdbcType.BIGINT),
			@Result(column = "cf_medico", property = "medico.cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_reg", property = "medico.codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "medico_cognome", property = "medico.cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "medico_nome", property = "medico.nome", jdbcType = JdbcType.VARCHAR)
        })
        List<SoggettoForVisura> selectSoggettiForVisuraMmgByMedico(
        		@Param("idMedico") Long idMedico);
    
    
    
    
    
    @Select(
        	{
        	"<script>",
        	"select ",
        	SoggettoSelectSQL.selectForSoggetto,
        	",", SoggettoSelectSQL.selectForDecorsoDmax,
        	",", SoggettoSelectSQL.selectForArea,
        	",", SoggettoSelectSQL.selectForStruttura,
        	",", SoggettoSelectSQL.selectForEnte,
        	",", SoggettoSelectSQL.selectForDTipoEvento,
        	",", SoggettoSelectSQL.selectForAsr,
        	",", SoggettoSelectSQL.selectForComuneRicovero,
        	",", SoggettoSelectSQL.selectForComuneDomicilio,
        	",", SoggettoSelectSQL.selectForComuneResidenza,
        	",", SoggettoSelectSQL.selectForTampone,        	
        	", d_tipo_soggetto.desc_tipo_soggetto ",
        	" from soggetto s",
        	" left join d_tipo_soggetto on  s.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto",
    		"    left join decorso d on s.id_soggetto=d.id_soggetto",
        	"    left join ASR on s.ID_ASR = ASR.ID_ASR",
        	"    left join (select distinct on(id_soggetto) * from decorso order by" ,
        	"				id_soggetto, " ,
        	"				data_dimissioni desc nulls last ," , 
        	"				data_evento desc nulls last," ,
        	"				id_decorso desc) d_max on d_max.id_soggetto = s.id_soggetto",
    		"    left join area a on a.id_area=d_max.id_area",
    		"    left join struttura st on st.id_struttura=a.id_struttura",
    		"    left join ente e on e.id_ente=st.id_ente",
    		"    left join d_tipo_evento dte on dte.id_tipo_evento=d_max.id_tipo_evento",
    		"    LEFT join comuni comuni_ricovero on d_max.comune_ricovero_istat = comuni_ricovero.istat_comune ",
            "	 LEFT join comuni comuni_residenza on s.comune_residenza_istat = comuni_residenza.istat_comune ",
            "	 LEFT join comuni comuni_domicilio on s.comune_domicilio_istat = comuni_domicilio.istat_comune ",
        	"    left join (select distinct on(id_soggetto) * from tampone ",
        	"				where id_ris_tamp is not null" ,  // l'ultimo tampone con esito negativo o positivo
         	"			order by" ,
        	"				id_soggetto, " ,
        	"				data_test desc nulls last " , 
        	"				) tam on tam.id_soggetto = s.id_soggetto",
        	"   where ",
        	"		s.id_asr = #{idAsr} ",
        	"		AND ( ",	
        	"		d_max.id_tipo_evento in   ",
            "		<foreach item='item' index='index' collection='list' ",
            "		open='(' separator=',' close=')'>",
            "			#{item} ",
            "		</foreach>",
        	"		 )",	// tamponi positivi
        	"group by " , 
        	SoggettoSelectSQL.groupbyForSoggetto,
        	",", SoggettoSelectSQL.groupbyForDecorsoDmax,
        	",", SoggettoSelectSQL.groupbyForArea,
        	",", SoggettoSelectSQL.groupbyForStruttura,
        	",", SoggettoSelectSQL.groupbyForEnte,
        	",", SoggettoSelectSQL.groupbyForDTipoEvento,
        	",", SoggettoSelectSQL.groupbyForAsr,
        	",", SoggettoSelectSQL.groupbyForComuneRicovero,
        	",", SoggettoSelectSQL.groupbyForComuneResidenza,
        	",", SoggettoSelectSQL.groupbyForComuneDomicilio,
        	",", SoggettoSelectSQL.groupbyForTampone,
        	", desc_tipo_soggetto", 
       	"</script>"
        })
        @Results({
            @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
            @Result(column = "codice_fiscale", property = "codiceFiscale", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT),
    		@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_residenza_istat", property = "comuneResidenzaIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "comune_domicilio_istat", property = "comuneDomicilioIstat", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "indirizzo_domicilio", property = "indirizzoDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "telefono_recapito", property = "telefonoRecapito", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "data_nascita", property = "dataNascita", jdbcType = JdbcType.DATE),
    		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
    		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),

            @Result(column="d_max_id_decorso", property="decorso.idDecorso", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_id_tipo_evento", property="decorso.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="d_max_comune_ricovero_istat", property="decorso.comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
//            @Result(column="d_max_condizioni_cliniche", property="decorso.condizioniCliniche", jdbcType=JdbcType.VARCHAR),
//            @Result(column="d_max_sintomi", property="decorso.sintomi", jdbcType=JdbcType.VARCHAR),
    		@Result(column = "d_max_data_dimissioni", property = "decorso.dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
//    		@Result(column = "d_max_data_inizio_sint", property = "decorso.dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_data_evento", property = "decorso.dataEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_id_area", property = "decorso.idArea", jdbcType = JdbcType.INTEGER),
    		@Result(column = "d_max_note", property = "decorso.note", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_data_prev_fine_evento", property = "decorso.dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
    		@Result(column = "d_max_indirizzo_decorso", property = "decorso.indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_decorso_presso", property = "decorso.decorsoPresso", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "dmax_luogo_paziente", property = "decorso.luogoPaziente", jdbcType = JdbcType.VARCHAR),
    		@Result(column = "d_max_descrizione_contesto", property = "decorso.descrizioneContesto", jdbcType = JdbcType.VARCHAR),

    		@Result(column="dte_d_tipo_evento", property="tipoEvento.idTipoEvento", jdbcType=JdbcType.BIGINT),
            @Result(column="dte_desc_tipo_evento", property="tipoEvento.descTipoEvento", jdbcType=JdbcType.BIGINT),
            
    		@Result(column="comuni_ricovero_nome_comune", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_ricovero_istat_comune", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_residenza_nome_comune", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_residenza_istat_comune", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),

    		@Result(column="comuni_domicilio_nome_comune", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
            @Result(column="comuni_domicilio_istat_comune", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),

            @Result(column="asr_id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
            @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
                  
            @Result(column="a_id_area", property="area.idArea", jdbcType=JdbcType.BIGINT),
            @Result(column="a_nome", property="area.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_struttura", property="area.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="a_responsabile", property="area.responsabile", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_id_d_area", property="area.idDArea", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_riferimento", property="area.riferimento", jdbcType=JdbcType.VARCHAR),
            @Result(column="a_stato_validita", property="area.statoValidita", jdbcType=JdbcType.VARCHAR),
            
            @Result(column="st_id_struttura", property="struttura.idStruttura", jdbcType=JdbcType.BIGINT),
            @Result(column="st_nome", property="struttura.nome", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_natura", property="struttura.natura", jdbcType=JdbcType.VARCHAR),
            @Result(column="st_id_ente", property="struttura.idEnte", jdbcType=JdbcType.BIGINT),
            
            @Result(column="e_id_ente", property="ente.idEnte", jdbcType=JdbcType.BIGINT),
            @Result(column="e_nome_ente", property="ente.nome", jdbcType=JdbcType.VARCHAR),
            
            
            @Result(column = "tam_id_tampone", property = "tampone.idTampone", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "id_soggetto", property = "tampone.idSoggetto", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_id_ris_tamp", property = "tampone.idRisTamp", jdbcType = JdbcType.BIGINT),
			@Result(column = "tam_data_test", property = "tampone.dataTest", jdbcType = JdbcType.TIMESTAMP),

            
        })
        List<SoggettoForVisura> selectForElencoVisuraByIdTipoEventoByIdAsr(
        		@Param("idAsr") Long idAsr, 
        		@Param("list") Integer[] listIdTipoEvento);

    
//    static final String SOGGETTI_RICERCA = 
//    		" select" +
//    		"     SOGGETTO.ID_SOGGETTO ," +
//    		"     SOGGETTO.CODICE_FISCALE," +
//    		"     SOGGETTO.ID_ASR," +
//    		"     SOGGETTO.COGNOME," +
//    		"     SOGGETTO.NOME," +
//    		"     SOGGETTO.DATA_NASCITA," +
//    		"     SOGGETTO.COMUNE_RESIDENZA_ISTAT," +
//    		"     SOGGETTO.COMUNE_DOMICILIO_ISTAT," +
//    		"     SOGGETTO.INDIRIZZO_DOMICILIO," +
//    		"     SOGGETTO.TELEFONO_RECAPITO," +
//    		"     SOGGETTO.ASL_RESIDENZA," +
//    		"     SOGGETTO.ASL_DOMICILIO," +
//    		"     SOGGETTO.id_tipo_soggetto," +
//    		"     d_tipo_soggetto.desc_tipo_soggetto," +
//    		"     COMUNI_DOM.NOME_COMUNE as COMUNE_DOMICILIO_NOME," +
//    		"     COMUNI_RES.NOME_COMUNE as COMUNE_RESIDENZA_NOME," +
//    		"     ASR.DESCRIZIONE as ASR_DESCRIZIONE," +
//    		"     count(distinct id_tampone) as numero_tamponi," +
//    		"     count(distinct decorso.id_decorso) as numero_decorsi," +
//    		" 	  d_max.id_tipo_evento as ultimo_id_tipo_evento," +
//    		"     count(*) OVER() AS full_count " +
//    		" from" +
//    		" SOGGETTO" +
//    		" left join decorso on" +
//    		"     soggetto.id_Soggetto = decorso.id_soggetto" +
//    		" left join tampone on" +
//    		"     soggetto.id_soggetto = tampone.id_soggetto" +
//    		" left join COMUNI COMUNI_RES on" +
//    		"     SOGGETTO.COMUNE_RESIDENZA_ISTAT = COMUNI_RES.ISTAT_COMUNE" +
//    		" left join COMUNI COMUNI_DOM on" +
//    		"     SOGGETTO.COMUNE_DOMICILIO_ISTAT = COMUNI_DOM.ISTAT_COMUNE" +
//    		" left join ASR on" +
//    		"     SOGGETTO.ID_ASR = ASR.ID_ASR" +
//    		" left join d_tipo_soggetto on" +
//    		"     soggetto.id_tipo_soggetto = d_tipo_soggetto.id_tipo_soggetto" +
//    		" left join (select distinct on(id_soggetto) * from decorso order by" +
//    		" 	id_soggetto, " +
//    		" 	data_dimissioni desc nulls last ," + 
//    		" 	data_evento desc nulls last," +
//    		" 	id_decorso desc) d_max on " + 
//    		" 	d_max.id_soggetto = soggetto.id_soggetto" +
//    		" 	where 1=1 " +
//    		"  <if test=\"codiceFiscale != null and codiceFiscale != '' \"> AND upper(soggetto.CODICE_FISCALE)=upper(#{codiceFiscale,jdbcType=VARCHAR}) </if>" +
//    		"  <if test=\"nome != null and nome != '' \">AND upper(soggetto.NOME) like upper(#{nome,jdbcType=VARCHAR}) </if>" +
//    		"  <if test=\"cognome != null and cognome != '' \"> AND upper(SOGGETTO.COGNOME) " +
//    		"      <if test='cognomeexact == true '> = upper(#{cognome,jdbcType=VARCHAR}) </if> " +
//    		"      <if test='cognomeexact == false '> like upper(#{cognome,jdbcType=VARCHAR}) </if> " +
//    		"  </if>" +
//    		"  <if test=\"dataNascita != null \">AND soggetto.data_nascita= #{dataNascita,jdbcType=DATE} </if>" +
//    		
//    		"  <if test='idTipoEvento != null'> AND d_max.id_tipo_evento IN (<foreach collection='idTipoEvento'  item='te' separator=','>#{te,jdbcType=BIGINT} </foreach>) </if>" +
//    		"  <if test='idTipoSoggetto!= null and idTipoSoggetto != -2 and idTipoSoggetto != -1'> AND soggetto.id_tipo_soggetto  = #{idTipoSoggetto,jdbcType=INTEGER} </if>" +
//    		"  <if test='idTipoSoggetto!= null and idTipoSoggetto == -1 '> AND soggetto.id_tipo_soggetto is null </if>" +
//    		"  <if test='filter != null'> " +
//    		"      AND ( " +
//    		"          lower(soggetto.codice_fiscale)    like lower(#{filter,jdbcType=VARCHAR}) " +
//    		"          OR lower(soggetto.cognome)        like lower(#{filter,jdbcType=VARCHAR}) " +
//    		"          OR lower(soggetto.nome)           like lower(#{filter,jdbcType=VARCHAR}) " +
//    		"          OR lower(COMUNI_DOM.nome_comune) like lower(#{filter,jdbcType=VARCHAR}) " +
//    		"          OR lower(COMUNI_RES.nome_comune) like lower(#{filter,jdbcType=VARCHAR}) " +
//    		"      )" +
//    		"  </if>" +
//    		
//    		" group by" +
//    		"     SOGGETTO.ID_SOGGETTO ," +
//    		"     SOGGETTO.CODICE_FISCALE," +
//    		"     SOGGETTO.ID_ASR," +
//    		"     SOGGETTO.COGNOME," +
//    		"     SOGGETTO.NOME," +
//    		"     SOGGETTO.DATA_NASCITA," +
//    		"     SOGGETTO.COMUNE_RESIDENZA_ISTAT," +
//    		"     SOGGETTO.COMUNE_DOMICILIO_ISTAT," +
//    		"     SOGGETTO.INDIRIZZO_DOMICILIO," +
//    		"     SOGGETTO.TELEFONO_RECAPITO," +
//    		"     SOGGETTO.ASL_RESIDENZA," +
//    		"     SOGGETTO.ASL_DOMICILIO," +
//    		"     COMUNI_DOM.NOME_COMUNE," +
//    		"     COMUNI_RES.NOME_COMUNE," +
//    		"     ASR.DESCRIZIONE," +
//    		"     d_max.id_tipo_evento," +
//    		"     d_tipo_soggetto.desc_tipo_soggetto";
    
//    @Select({
//    	"<script>",
//    	" select count(*) from ( ",
//    	SOGGETTI_RICERCA,
//    	" ) as count_table_inner",
//    	"</script>"
//    	
//    })
//    Long selectForElencoByCFCognomeNomeCount(
//			@Param("codiceFiscale") String codiceFiscale, 
//			@Param("cognome")String cognome, 
//			@Param("nome")String nome,
//			@Param("cognomeexact")Boolean cognomeexact, 
//			@Param("dataNascita")Date dataNascita,
//			@Param("idTipoEvento") List<Long> idTipoEvento,
//    		@Param("idTipoSoggetto") Integer idTipoSoggetto,
//    		@Param("filter") String filter
//			
//			);
    
    
    
//    @Select({"<script>",
//    	SOGGETTI_RICERCA,
//    	" <if test='oderByFiled != null'> order by ${oderByFiled} ${orderAscDesc} </if>",
//    	" <if test='limit !=-1'> offset #{offset,jdbcType=BIGINT} limit  #{limit,jdbcType=BIGINT}</if>",
//    	"</script>"
//    })
//    @Results({
//        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
//        @Result(column="codice_fiscale", property="codiceFiscale", jdbcType=JdbcType.VARCHAR),
//        @Result(column="id_asr", property="idAsr", jdbcType=JdbcType.BIGINT),
//        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
//        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR),
//        @Result(column="data_nascita", property="dataNascita", jdbcType=JdbcType.TIMESTAMP),
//        @Result(column="comune_residenza_istat", property="comuneResidenzaIstat", jdbcType=JdbcType.VARCHAR),
//        @Result(column="comune_domicilio_istat", property="comuneDomicilioIstat", jdbcType=JdbcType.VARCHAR),
//        @Result(column="indirizzo_domicilio", property="indirizzoDomicilio", jdbcType=JdbcType.VARCHAR),
//        @Result(column="telefono_recapito", property="telefonoRecapito", jdbcType=JdbcType.VARCHAR),
//		@Result(column = "id_tipo_soggetto", property = "idTipoSoggetto", jdbcType = JdbcType.BIGINT),
//		@Result(column = "id_tipo_soggetto", property = "tipoSoggetto.idTipoSoggetto", jdbcType = JdbcType.BIGINT),
//		@Result(column = "desc_tipo_soggetto", property = "tipoSoggetto.descTipoSoggetto", jdbcType = JdbcType.VARCHAR),
//        @Result(column="id_asr", property="asr.idAsr", jdbcType=JdbcType.BIGINT),
//        @Result(column="asr_descrizione", property="asr.descrizione", jdbcType=JdbcType.VARCHAR),
//        @Result(column="comune_domicilio_nome", property="comuneDomicilio.nomeComune", jdbcType=JdbcType.VARCHAR),
//        @Result(column="comune_domicilio_istat", property="comuneDomicilio.istatComune", jdbcType=JdbcType.VARCHAR),
//        @Result(column="comune_residenza_nome", property="comuneResidenza.nomeComune", jdbcType=JdbcType.VARCHAR),
//        @Result(column="comune_residenza_istat", property="comuneResidenza.istatComune", jdbcType=JdbcType.VARCHAR),
//        @Result(column="numero_tamponi", property="numeroCampioni", jdbcType=JdbcType.BIGINT),
//        @Result(column="numero_decorsi", property="numeroDecorsi", jdbcType=JdbcType.BIGINT),
//		@Result(column = "asl_residenza", property = "aslResidenza", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "asl_domicilio", property = "aslDomicilio", jdbcType = JdbcType.VARCHAR),
//		@Result(column = "ultimo_id_tipo_evento", property = "ultimoIdTipoEvento", jdbcType = JdbcType.INTEGER),
//		@Result(column = "full_count", property = "totalCount", jdbcType = JdbcType.INTEGER)
//    })
//	List<SoggettoForElenco> selectForElencoByCFCognomeNomeDeprecated(
//			@Param("codiceFiscale") String codiceFiscale, 
//			@Param("cognome")String cognome, 
//			@Param("nome")String nome,
//			@Param("cognomeexact")Boolean cognomeexact, 
//			@Param("dataNascita")Date dataNascita,
//			@Param("idTipoEvento") List<Long> idTipoEvento,
//    		@Param("idTipoSoggetto") Integer idTipoSoggetto,
//    		@Param("filter") String filter,
//			
//			//Paginazione
//			@Param("oderByFiled") String oderByFiled,
//    		@Param("orderAscDesc") String orderAscDesc,
//    		@Param("offset") Long offset,
//    		@Param("limit") Long limit
//			
//			);
    
    
}
