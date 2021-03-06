package it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.extend;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Disponibilita;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReport;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReportTransposed;
import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.custom.DisponibilitaForReportTransposedPlain;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated.BaseDisponibilitaMapper;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated.BaseStrutturaMapper;

public interface DisponibilitaMapper extends BaseDisponibilitaMapper {

	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Insert({ "insert into disponibilita (id_area, ", "data_evento, tipo_operazione, ",
			"posti_target, posti_disponibili, ", "posti_occupati, flag_valido, ",
			"utente_inserimento, data_inserimento)",
			"values ( #{idArea,jdbcType=INTEGER}, ",
			"#{dataEvento,jdbcType=TIMESTAMP}, #{tipoOperazione,jdbcType=VARCHAR}, ",
			"#{postiTarget,jdbcType=INTEGER}, #{postiDisponibili,jdbcType=INTEGER}, ",
			"#{postiOccupati,jdbcType=INTEGER}, #{flagValido,jdbcType=VARCHAR}, ",
			"#{utenteInserimento,jdbcType=VARCHAR}, #{dataInserimento,jdbcType=TIMESTAMP})" })
	int insert(Disponibilita record);
	
	@Select({ "<script>",
			"select",
			"		id_disponibilita,disponibilita.id_area, data_evento, tipo_operazione, posti_target, posti_disponibili, ",
			"		posti_occupati, flag_valido, utente_inserimento, data_inserimento,",
			"		area.id_area area_id_area, area.id_struttura area_id_struttura, area.nome area_nome, area.responsabile area_responsabile, ",
			"		area.id_d_area area_id_d_area,",
			"		area.riferimento area_riferimento, area.stato_validita area_stato_validita, ",
			"		struttura.id_struttura struttura_id_struttura, struttura.nome struttura_nome, ",
			"		struttura.natura struttura_natura, struttura.id_ente struttura_id_ente, ",
			"		ente.id_ente ente_id_ente, ente.nome ente_nome, ente.tot_posti_target ente_tot_posti_target ",
			" from area",
			" left join struttura on area.id_struttura = struttura.id_struttura",
			" left join ente on struttura.id_ente = ente.id_ente",
			" left join disponibilita on disponibilita.id_area = area.id_area",
			" where (area.stato_validita!='DISMESSO' or area.stato_validita is null)  ", 
			" <if test='idEnte != null'> and ente.id_ente=#{idEnte,jdbcType=BIGINT} </if>",
			" <if test='onlyValid'> and (disponibilita.flag_valido = 'SI' or disponibilita.flag_valido is null) </if>",
			 "</script>"})
	@Results({
		@Result(column = "id_disponibilita", property = "idDisponibilita", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER),
		@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "tipo_operazione", property = "tipoOperazione", jdbcType = JdbcType.VARCHAR),
		@Result(column = "posti_target", property = "postiTarget", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_disponibili", property = "postiDisponibili", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_occupati", property = "postiOccupati", jdbcType = JdbcType.INTEGER),
		@Result(column = "flag_valido", property = "flagValido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "utente_inserimento", property = "utenteInserimento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "data_inserimento", property = "dataInserimento", jdbcType = JdbcType.TIMESTAMP),
		
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
		
	
		})
	List<DisponibilitaForReport> selectForReportByIdEnte(@Param("idEnte") Integer idEnte, @Param("onlyValid") Boolean onlyValid);


	@Select({ "<script>",
		"select ",
		"		ente.id_ente ente_id_ente, ente.nome ente_nome,	ente.tot_posti_target ente_tot_posti_target,  ",
		"		struttura.id_struttura struttura_id_struttura, struttura.nome struttura_nome, struttura.natura struttura_natura,  struttura.id_ente struttura_id_ente, ",
		"		 array_to_json(array_agg( ",
		"		 json_build_object( ",
		"		 	'area',json_build_object( ",
		"		 		'id_area', area_id_area,  ",
		"		 		'nome',  area_nome,  ",
		"		 		'id_darea',  id_d_area,  ",
		" 				'id_struttura', area_id_struttura, ",
		"		 		'responsabile', responsabile,  ",
		"				'riferimento',  riferimento,  ",
		"				'stato_validita', stato_validita ",
		"		 		), ",
		"		 	'disponibilita', json_build_object( ",
		"		 		'id_disponibilita',id_disponibilita, ",
		"		 		'data_evento', data_evento, ",
		"		 		'tipo_operazione',tipo_operazione, ",
		"		 		'posti_target', posti_target, ",
		"		 		'posti_disponibili', posti_disponibili, ",
		"				'posti_occupati', posti_occupati, ",
		"				'flag_valido', flag_valido, ",
		"				'utente_inserimento', utente_inserimento, ",
		"				'data_inserimento',data_inserimento ",
		"		 		) ",
		"		 ) ",
		"		 )) dispo_array ",
		"from  ",
		"( ",
		"	select area.id_area area_id_area,area.nome area_nome, area.id_struttura area_id_struttura, area.*,  disponibilita.* from area ",
		"	left join disponibilita on area.id_area = disponibilita.id_area ",
		"  where (area.stato_validita!='DISMESSO' or area.stato_validita is null)  ",
		" <if test='onlyValid'> and (disponibilita.flag_valido = 'SI' or disponibilita.flag_valido is null) </if>",		
		" ) area_dispo ",
		"left  join struttura on area_dispo.id_struttura = struttura.id_struttura ",
		"left join ente on struttura.id_ente = ente.id_ente ",
		" <if test='idEnte != null'> where ente.id_ente=#{idEnte,jdbcType=BIGINT} </if>",		
		"group by ente.id_ente, ente.nome,	 ",
		"		struttura.id_struttura, struttura.nome, struttura.natura ",
		"order by ente.id_ente,struttura_id_struttura",
	    " </script>"
	})
	@Results({
		@Result(column = "dispo_array", property = "dispoArray", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "struttura_id_struttura", property = "struttura.idStruttura", jdbcType = JdbcType.VARCHAR, id = true),
		@Result(column = "struttura_id_ente", property = "struttura.idEnte", jdbcType = JdbcType.INTEGER),
		@Result(column = "struttura_nome", property = "struttura.nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "struttura_natura", property = "struttura.natura", jdbcType = JdbcType.VARCHAR),
		
		@Result(column = "ente_id_ente", property = "ente.idEnte", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "ente_nome", property = "ente.nome", jdbcType = JdbcType.VARCHAR),
		@Result(column = "ente_tot_posti_target", property = "ente.totPostiTarget", jdbcType = JdbcType.INTEGER),
	
		})
	List<DisponibilitaForReportTransposed> selectForReportTransposedByIdEnte(@Param("idEnte") Integer idEnte,@Param("onlyValid") Boolean onlyValid);
	
	
	@Select({ "select", "id_disponibilita, id_area, data_evento, tipo_operazione, posti_target, posti_disponibili, ",
		"posti_occupati, flag_valido, utente_inserimento, data_inserimento", "from disponibilita",
		"where id_area = #{idArea,jdbcType=INTEGER} and flag_valido = 'SI'"  })
	@Results({
		@Result(column = "id_disponibilita", property = "idDisponibilita", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER),
		@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "tipo_operazione", property = "tipoOperazione", jdbcType = JdbcType.VARCHAR),
		@Result(column = "posti_target", property = "postiTarget", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_disponibili", property = "postiDisponibili", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_occupati", property = "postiOccupati", jdbcType = JdbcType.INTEGER),
		@Result(column = "flag_valido", property = "flagValido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "utente_inserimento", property = "utenteInserimento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "data_inserimento", property = "dataInserimento", jdbcType = JdbcType.TIMESTAMP) })
	Disponibilita selectByValidAndIdArea(@Param("idArea") Integer idArea);
	
	
	@Select({ "select", "id_disponibilita, id_area, data_evento, tipo_operazione, posti_target, posti_disponibili, ",
		"posti_occupati, flag_valido, utente_inserimento, data_inserimento", "from disponibilita d",
		"where id_area = #{idArea,jdbcType=INTEGER}  ",
		"and data_evento = (" , 
		"    select max(d1.data_evento)" ,
		"    from disponibilita d1" ,
		"    where d1.id_area = d.id_area" ,
		")"  })
	@Results({
		@Result(column = "id_disponibilita", property = "idDisponibilita", jdbcType = JdbcType.INTEGER, id = true),
		@Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER),
		@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP),
		@Result(column = "tipo_operazione", property = "tipoOperazione", jdbcType = JdbcType.VARCHAR),
		@Result(column = "posti_target", property = "postiTarget", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_disponibili", property = "postiDisponibili", jdbcType = JdbcType.INTEGER),
		@Result(column = "posti_occupati", property = "postiOccupati", jdbcType = JdbcType.INTEGER),
		@Result(column = "flag_valido", property = "flagValido", jdbcType = JdbcType.VARCHAR),
		@Result(column = "utente_inserimento", property = "utenteInserimento", jdbcType = JdbcType.VARCHAR),
		@Result(column = "data_inserimento", property = "dataInserimento", jdbcType = JdbcType.TIMESTAMP) })
	Disponibilita selectLastByIdArea(@Param("idArea") Integer idArea);
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Update({ "update disponibilita", "set flag_valido = #{flagValido,jdbcType=VARCHAR}",
			"where id_disponibilita = #{idDisponibilita,jdbcType=INTEGER}" })
	int updateInvalidByPrimaryKey(Disponibilita record);
}
