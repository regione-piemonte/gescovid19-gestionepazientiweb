package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Decorso;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.DecorsoForElenco;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseDecorsoMapper;

public interface DecorsoMapper extends BaseDecorsoMapper {

	@Insert({ "insert into decorso (id_decorso, id_soggetto, comune_ricovero_istat, ", "id_rep_ricovero, condizioni_cliniche, ", "id_dimiss, ",
		"sintomi, ", "ospedale_ricovero, data_dimissioni, ", "data_inizio_sint, data_evento, id_tipo_evento, id_area, ",
		"note, data_prev_fine_evento, indirizzo_decorso, decorso_presso, descrizione_contesto, luogo_paziente)",
		"values (nextval('seq_id_decorso'), #{idSoggetto,jdbcType=BIGINT}, #{comuneRicoveroIstat,jdbcType=VARCHAR}, ",
		"#{idRepRicovero,jdbcType=BIGINT}, UPPER(#{condizioniCliniche,jdbcType=VARCHAR}), ",
		"#{idDimiss,jdbcType=BIGINT}, ", "#{sintomi,jdbcType=VARCHAR}, ",
		"UPPER(#{ospedaleRicovero,jdbcType=VARCHAR}), #{dataDimissioni,jdbcType=TIMESTAMP}, ", "#{dataInizioSint,jdbcType=TIMESTAMP},",
		" #{dataEvento,jdbcType=TIMESTAMP}, #{idTipoEvento,jdbcType=INTEGER}, #{idArea,jdbcType=INTEGER},  #{note,jdbcType=VARCHAR},",
		" #{dataPrevFineEvento,jdbcType=TIMESTAMP}, #{indirizzoDecorso,jdbcType=VARCHAR}, #{decorsoPresso,jdbcType=VARCHAR} , #{descrizioneContesto,jdbcType=VARCHAR} , #{luogoPaziente,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "idDecorso")
	int insert(Decorso record);

	
	
    @Select({
        "select decorso.id_decorso, id_soggetto, comune_ricovero_istat, decorso.id_rep_ricovero, condizioni_cliniche,  " , 
        "       decorso.id_dimiss, sintomi, ospedale_ricovero, data_dimissioni, data_inizio_sint, " ,
        "		decorso.data_evento, decorso.id_tipo_evento, decorso.id_area, decorso.note, decorso.data_prev_fine_evento,  ",
        "		decorso.indirizzo_decorso, decorso.decorso_presso, decorso.descrizione_contesto, decorso.luogo_paziente, ",
        "       comuni.nome_comune as comune_ricovero_nome,  " , 
        "       reparto_ricovero.reparto_ricovero, " , 
        "       esito_dimissioni.esito_dim, ",
        "       d_tipo_evento.id_tipo_evento d_id_tipo_evento, d_tipo_evento.desc_tipo_evento, d_tipo_evento.segno, ",
        "	 	area.id_area area_id_area,  area.id_struttura area_id_struttura,  area.id_d_area area_id_d_area, ",
        "	area.nome area_nome,  area.responsabile area_responsabile,  area.riferimento area_riferimento,  area.stato_validita area_stato_validita,",
        " 	struttura.id_struttura struttura_id_struttura, struttura.id_ente struttura_id_ente, struttura.nome struttura_nome, natura struttura_natura,",
        " 	ente.id_ente ente_id_ente, ente.nome ente_nome, ente.tot_posti_target ente_tot_posti_target,",
        "   sintomo.id_sintomo           sintomo_id_sintomo,",        
        "   sintomo.temperatura          sintomo_temperatura,",        
        "   sintomo.flg_gusto            sintomo_flg_gusto,",        
        "   sintomo.flg_olfatto          sintomo_flg_olfatto,",        
        "   sintomo.flg_tosse            sintomo_flg_tosse,",        
        "   sintomo.flg_dolori_musc      sintomo_flg_dolori_musc,",        
        "   sintomo.flg_stanchezza       sintomo_flg_stanchezza,",        
        "   sintomo.flg_congiuntivite    sintomo_flg_congiuntivite,",        
        "   sintomo.flg_diarrea          sintomo_flg_diarrea,",        
        "   sintomo.flg_raffreddore      sintomo_flg_raffreddore,",   
        "   sintomo.flg_dispnea      sintomo_flg_dispnea, ",  
        "   sintomo.flg_cefalea      sintomo_flg_cefalea, ",
        "   sintomo.flg_faringodinia      sintomo_flg_faringodinia, ",
        "   sintomo.note_sintomo         sintomo_note_sintomo ",          
        "from decorso "
        + "LEFT join comuni on decorso.comune_ricovero_istat = comuni.istat_comune "
        + "	LEFT join sintomo on decorso.id_decorso = sintomo.id_decorso " 		
        + "LEFT join reparto_ricovero on decorso.id_rep_ricovero = reparto_ricovero.id_rep_ricovero "
        + "LEFT join esito_dimissioni on decorso.id_dimiss = esito_dimissioni.id_dimiss " 
        + "LEFT join d_tipo_evento on decorso.id_tipo_evento = d_tipo_evento.id_tipo_evento ",
        " LEFT join area on decorso.id_area = area.id_area ", 
        " left join struttura on area.id_struttura = struttura.id_struttura ", 
        " left join ente on ente.id_ente = struttura.id_ente ", 
        " where  " + 
        "id_soggetto=#{idSoggetto,jdbcType=BIGINT} ", 
        "order by data_dimissioni desc nulls last , data_evento desc nulls last, id_decorso desc"
    })
    @Results({
        @Result(column="id_decorso", property="idDecorso", jdbcType=JdbcType.BIGINT),
        @Result(column="id_soggetto", property="idSoggetto", jdbcType=JdbcType.BIGINT),
        @Result(column="comune_ricovero_istat", property="comuneRicoveroIstat", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_rep_ricovero", property="idRepRicovero", jdbcType=JdbcType.BIGINT),
        @Result(column="condizioni_cliniche", property="condizioniCliniche", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_dimiss", property="idDimiss", jdbcType=JdbcType.BIGINT),
        @Result(column="sintomi", property="sintomi", jdbcType=JdbcType.VARCHAR),
		@Result(column = "ospedale_ricovero", property = "ospedaleRicovero", jdbcType = JdbcType.VARCHAR),
        @Result(column="id_dimiss", property="esitoDimissioni.idDimiss", jdbcType=JdbcType.BIGINT),
        @Result(column="esito_dim", property="esitoDimissioni.esitoDim", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_ricovero_nome", property="comuneRicovero.nomeComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="comune_ricovero_istat", property="comuneRicovero.istatComune", jdbcType=JdbcType.VARCHAR),
        @Result(column="id_rep_ricovero", property="repartoRicovero.idRepRicovero", jdbcType=JdbcType.BIGINT),
        @Result(column="reparto_ricovero", property="repartoRicovero.repartoRicovero", jdbcType=JdbcType.VARCHAR),
		@Result(column = "data_dimissioni", property = "dataDimissioni", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "data_inizio_sint", property = "dataInizioSint", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "id_tipo_evento", property = "idTipoEvento", jdbcType = JdbcType.INTEGER),
		@Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER),
		@Result(column = "note", property = "note", jdbcType = JdbcType.VARCHAR),
		@Result(column = "data_prev_fine_evento", property = "dataPrevFineEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "indirizzo_decorso", property = "indirizzoDecorso", jdbcType = JdbcType.VARCHAR),
		@Result(column = "decorso_presso", property = "decorsoPresso", jdbcType = JdbcType.VARCHAR),

		@Result(column = "descrizione_contesto", property = "descrizioneContesto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "luogo_paziente", property = "luogoPaziente", jdbcType = JdbcType.VARCHAR),

		
		@Result(column = "d_id_tipo_evento", property = "decodeTipoEvento.idTipoEvento", jdbcType = JdbcType.INTEGER),
        @Result(column="desc_tipo_evento", property="decodeTipoEvento.descTipoEvento", jdbcType=JdbcType.VARCHAR),
        @Result(column="segno", property="decodeTipoEvento.segno", jdbcType=JdbcType.VARCHAR)	,
        
		@Result(column = "area_id_area", property = "area.idArea", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "area_id_struttura", property = "area.idStruttura", jdbcType = JdbcType.VARCHAR),
		@Result(column = "area_id_d_area", property = "area.idDArea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "area_nome", property = "area.nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "area_responsabile", property = "area.responsabile", jdbcType = JdbcType.VARCHAR),
		@Result(column = "area_riferimento", property = "area.riferimento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "area_stato_validita", property = "area.statoValidita", jdbcType = JdbcType.VARCHAR), 
		
		@Result(column = "struttura_id_struttura", property = "struttura.idStruttura", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "struttura_id_ente", property = "struttura.idEnte", jdbcType = JdbcType.INTEGER),
		@Result(column = "struttura_nome", property = "struttura.nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "struttura_natura", property = "struttura.natura", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "ente_id_ente", property = "ente.idEnte", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "ente_nome", property = "ente.nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ente_tot_posti_target", property = "ente.totPostiTarget", jdbcType = JdbcType.INTEGER),

    
		@Result(column = "sintomo_id_sintomo", property = "sintomo.idSintomo", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "sintomo_temperatura", property = "sintomo.temperatura", jdbcType = JdbcType.DECIMAL),
		@Result(column = "sintomo_flg_gusto", property = "sintomo.flgGusto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_olfatto", property = "sintomo.flgOlfatto", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_tosse", property = "sintomo.flgTosse", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_dolori_musc", property = "sintomo.flgDoloriMusc", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_stanchezza", property = "sintomo.flgStanchezza", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_congiuntivite", property = "sintomo.flgCongiuntivite", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_diarrea", property = "sintomo.flgDiarrea", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_flg_raffreddore", property = "sintomo.flgRaffreddore", jdbcType = JdbcType.VARCHAR),
		@Result(column = "sintomo_note_sintomo", property = "sintomo.noteSintomo", jdbcType = JdbcType.VARCHAR),
	    @Result(column="sintomo_flg_dispnea", property="sintomo.flgDispnea", jdbcType=JdbcType.VARCHAR),
	    @Result(column="sintomo_flg_cefalea", property="sintomo.flgCefalea", jdbcType=JdbcType.VARCHAR),
	    @Result(column="sintomo_flg_faringodinia", property="sintomo.flgFaringodinia", jdbcType=JdbcType.VARCHAR)
    })
    List<DecorsoForElenco> selectForElencoByIdSoggetto(Long idSoggetto);
}
