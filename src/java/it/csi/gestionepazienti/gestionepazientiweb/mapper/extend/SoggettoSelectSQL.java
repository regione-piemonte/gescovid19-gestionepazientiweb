package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import org.apache.ibatis.annotations.Result;

public class SoggettoSelectSQL {


    static final  String  selectForSoggetto = 
    	    	"s.id_soggetto, "+
    	    	"s.codice_fiscale, "+
    	    	"s.id_asr, "+
    	    	"s.cognome, "+
    	    	"s.nome, "+
    	    	"s.comune_residenza_istat, "+
    	    	"s.comune_domicilio_istat, "+
    	    	"s.indirizzo_domicilio, "+
    	    	"s.telefono_recapito, "+
    	    	"s.data_nascita, "+
    	    	"s.asl_residenza, "+
    	    	"s.asl_domicilio, "+
    	    	"s.id_tipo_soggetto, " +
    	    	"s.email "
    	;
    
    static final String groupbyForSoggetto = selectForSoggetto;
    
    static final String selectForDecorsoDmax= 
    	    	"d_max.id_decorso d_max_id_decorso, "+
    	    	"d_max.id_tipo_evento d_max_id_tipo_evento, "+
    	    	"d_max.comune_ricovero_istat d_max_comune_ricovero_istat, "+
    	    	"d_max.condizioni_cliniche d_max_condizioni_cliniche, "+
    	    	"d_max.sintomi d_max_sintomi, "+
    	    	"d_max.data_dimissioni d_max_data_dimissioni, "+
    	    	"d_max.data_inizio_sint d_max_data_inizio_sint, "+
    	    	"d_max.data_evento d_max_data_evento, "+
    	    	"d_max.id_area d_max_id_area, "+
    	    	"d_max.note d_max_note, "+
    	    	"d_max.data_prev_fine_evento d_max_data_prev_fine_evento, "+
    	    	"d_max.indirizzo_decorso d_max_indirizzo_decorso, "+
    	    	"d_max.decorso_presso d_max_decorso_presso ,"+
    	    	"d_max.descrizione_contesto d_max_descrizione_contesto ,"+
    	    	"d_max.luogo_paziente dmax_luogo_paziente"
    	;
    static final String groupbyForDecorsoDmax = 
	    	"d_max.id_decorso , "+
	    	"d_max.id_tipo_evento , "+
	    	"d_max.comune_ricovero_istat , "+
	    	"d_max.condizioni_cliniche , "+
	    	"d_max.sintomi , "+
	    	"d_max.data_dimissioni , "+
	    	"d_max.data_inizio_sint , "+
	    	"d_max.data_evento , "+
	    	"d_max.id_area , "+
	    	"d_max.note , "+
	    	"d_max.data_prev_fine_evento , "+
	    	"d_max.indirizzo_decorso , "+
	    	"d_max.decorso_presso, "+
  	    	"d_max.descrizione_contesto  ,"+
  	    	 "d_max.luogo_paziente "
  	 
	;

    
    static final String selectForArea = 
	    	"    a.id_area a_id_area, "+
	    	"    a.id_struttura a_id_struttura, "+
	    	"	 a.id_d_area a_id_d_area, "+
	    	"	 a.responsabile a_responsabile, "+
	    	"	 a.riferimento a_riferimento, "+
	    	"	 a.stato_validita a_stato_validita, "+
	    	"    a.nome a_nome"
   ;
    
    static final String groupbyForArea = 
	    	"    a.id_area , "+
	    	"    a.id_struttura , "+
	    	"	 a.id_d_area , "+
	    	"	 a.responsabile , "+
	    	"	 a.riferimento , "+
	    	"	 a.stato_validita , "+
	    	"    a.nome "
   ;

    
    static final String selectForStruttura = 
	    	"    st.id_struttura st_id_struttura, "+
	    	"    st.nome st_nome, "+
	    	"    st.natura  st_natura, "+
	    	"    st.id_ente st_id_ente"
   ;

    static final String groupbyForStruttura = 
	    	"    st.id_struttura, "+
	    	"    st.nome , "+
	    	"    st.natura  , "+
	    	"    st.id_ente "
   ;

    
    static final String selectForEnte = 
	    	"    e.id_ente e_id_ente, "+
	    	"    e.nome e_nome_ente "
   ;
    
    static final String groupbyForEnte = 
	    	"    e.id_ente , "+
	    	"    e.nome "
   ;
    static final String selectForDTipoEvento = 
    		"  dte.id_tipo_evento dte_d_tipo_evento, "+
    		"  dte.desc_tipo_evento dte_desc_tipo_evento"
   ;
    static final String groupbyForDTipoEvento = 
    		"  dte.id_tipo_evento , "+
    		"  dte.desc_tipo_evento "
   ;


    static final String selectForAsr= 
    		"  asr.id_asr asr_id_asr, "+
    		"  asr.descrizione asr_descrizione"
   ;
    static final String groupbyForAsr= 
    		"  asr.id_asr , "+
    		"  asr.descrizione "
   ;

    static final String selectForComuneRicovero= 
    		"  comuni_ricovero.nome_comune  comuni_ricovero_nome_comune, "+
    		"  comuni_ricovero.istat_comune   comuni_ricovero_istat_comune"
   ;

    static final String groupbyForComuneRicovero= 
    		"  comuni_ricovero.nome_comune  , "+
    		"  comuni_ricovero.istat_comune "
   ;

    static final String selectForComuneDomicilio= 
    		"  comuni_domicilio.nome_comune  comuni_domicilio_nome_comune, "+
    		"  comuni_domicilio.istat_comune   comuni_domicilio_istat_comune"
   ;

    static final String groupbyForComuneDomicilio= 
    		"  comuni_domicilio.nome_comune  , "+
    		"  comuni_domicilio.istat_comune "
   ;

    static final String selectForComuneResidenza= 
    		"  comuni_residenza.nome_comune  comuni_residenza_nome_comune, "+
    		"  comuni_residenza.istat_comune   comuni_residenza_istat_comune"
   ;

    static final String groupbyForComuneResidenza= 
    		"  comuni_residenza.nome_comune  , "+
    		"  comuni_residenza.istat_comune "
   ;

// per ora solo quelli che servono per visura
    static final String selectForTampone=
    		"tam.id_tampone tam_id_tampone, " +
    		"tam.id_ris_tamp tam_id_ris_tamp, "+
    		"tam.criterio_epidemiologico_covid19 tam_criterio_epidemiologico_covid19, "+
    		"tam.id_test_covid tam_id_test_covid, "+
    		"tam.data_inserimento_richiesta tam_data_inserimento_richiesta,"+
			"tam.data_test tam_data_test"
    ;

    static final String groupbyForTampone=
    		"tam.id_tampone ," +
    	    "tam.id_ris_tamp,"+
    		"tam.criterio_epidemiologico_covid19, "+
    		"tam.id_test_covid, "+
    		"tam.data_inserimento_richiesta,"+
			"tam.data_test "
    ;

}
