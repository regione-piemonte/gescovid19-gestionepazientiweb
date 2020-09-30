package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.RMedicoMedico;
import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.MedicoExt;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseMedicoMapper;

public interface MedicoMapper extends BaseMedicoMapper {

	@Select({ "select", "id_medico, cf_medico, codice_reg, cognome, nome", "from medico",
			"where cf_medico = #{codiceFiscale,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "id_medico", property = "idMedico", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "cf_medico", property = "cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_reg", property = "codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR) })
	Medico selectByCodiceFiscale(String codiceFiscale);

	@Select({ "select", "id_medico, cf_medico, codice_reg, cognome, nome", "from medico_usca",
			"where cf_medico = #{codiceFiscale,jdbcType=VARCHAR}" })
	@Results({ @Result(column = "id_medico", property = "idMedico", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "cf_medico", property = "cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_reg", property = "codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR) })
	Medico selectMedicoUscaByCodiceFiscale(String codiceFiscale);

	@Select({ "select", "medico.id_medico, cf_medico, codice_reg, cognome, nome", "from medico",
			" join r_medico_sogg_aura on r_medico_sogg_aura.id_medico = medico.id_medico ",
			"where id_aura_sogg = #{idAuraSogg,jdbcType=BIGINT} " })
	@Results({ @Result(column = "id_medico", property = "idMedico", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "cf_medico", property = "cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "codice_reg", property = "codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nome", property = "nome", jdbcType = JdbcType.VARCHAR) })
	List<Medico> selectByIdAuraSogg(Long idAuraSogg);

//   @Select({
//	   "select",
//	   "medico.id_medico, cf_medico, codice_reg, cognome, nome",
//	   "from medico",
//	   "join r_medico_medico on r_medico_medico.id_medico_delegato = medico.id_medico", 
//	   "where cf_medico = #{cf,jdbcType=VARCHAR}"
//   })
	@Select({ "select b.id_medico medico_delegante_id, b.cf_medico medico_delegante_cf, ",
			"b.codice_reg medico_delegante_creg, b.cognome medico_delegante_cognome, b.nome medico_delegante_nome, r.validita_inizio inizio_val, r.validita_fine fine_val",
			"	from medico a ", "	   join r_medico_medico r on r.id_medico_delegato = a.id_medico,",
			"	   medico b", "	   where a.cf_medico = #{cf,jdbcType=VARCHAR} and r.id_medico_delegante = b.id_medico ",
			" and r.data_cancellazione is null ",
			"and current_date between r.validita_inizio::date and coalesce(r.validita_fine::date, '2999-01-01 12:00:00'::timestamp::date)" })
	@Results({ @Result(column = "medico_delegante_id", property = "idMedico", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "medico_delegante_cf", property = "cfMedico", jdbcType = JdbcType.VARCHAR),
			@Result(column = "medico_delegante_creg", property = "codiceReg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "medico_delegante_cognome", property = "cognome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "medico_delegante_nome", property = "nome", jdbcType = JdbcType.VARCHAR),
			@Result(column = "inizio_val", property = "dataInizioValidita", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "fine_val", property = "dataFineValidita", jdbcType = JdbcType.TIMESTAMP)
	})
	List<MedicoExt> selectForDelegantiByCodiceFiscale(String cf);
   
   @Select({
	   "<script>",
	   "select * from medico " ,
		"<if test='codicefiscale != null or cognome != null or nome != null' >",
		"<where>",
		"<if test='codicefiscale != null'> and cf_medico=upper(#{codicefiscale,jdbcType=VARCHAR}) </if> ",
		"<if test='cognome != null'> and cognome=upper(#{cognome,jdbcType=VARCHAR}) </if> ",
		"<if test='nome != null'> and nome=upper(#{nome,jdbcType=VARCHAR}) </if>",
		"</where>",
		" </if>",
	"</script>"
   })
   @Results({
       @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
       @Result(column="cf_medico", property="cfMedico", jdbcType=JdbcType.VARCHAR),
       @Result(column="codice_reg", property="codiceReg", jdbcType=JdbcType.VARCHAR),
       @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
       @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR)       
   })
   List<Medico> selectMediciDelegaliByFiltro(@Param("codicefiscale") String codicefiscale, @Param("cognome") String cognome, @Param("nome") String nome);

   
   @Select({
	   "select delegato.id_medico medico_delegato_id, delegato.cf_medico medico_delegato_cf, ",
	   "delegato.codice_reg medico_delegato_creg, delegato.cognome medico_delegato_cognome, delegato.nome medico_delegato_nome," ,
	   " r.validita_inizio inizio_val, r.validita_fine fine_val ",
	   "	from medico delegante ",
	   "	   join r_medico_medico r on r.id_medico_delegante = delegante.id_medico, medico delegato ",
	   "	   where delegante.cf_medico = #{cfDelegante,jdbcType=VARCHAR} ",
	   "	   and r.id_medico_delegato = delegato.id_medico ",
	   " 	   and r.data_cancellazione is null ",
	   " 	   and current_date <= coalesce(r.validita_fine::date, '2999-01-01 12:00:00'::timestamp::date) "
   })
   @Results({
       @Result(column="medico_delegato_id", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
       @Result(column="medico_delegato_cf", property="cfMedico", jdbcType=JdbcType.VARCHAR),
       @Result(column="medico_delegato_creg", property="codiceReg", jdbcType=JdbcType.VARCHAR),
       @Result(column="medico_delegato_cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
       @Result(column="medico_delegato_nome", property="nome", jdbcType=JdbcType.VARCHAR),
       @Result(column="inizio_val", property="dataInizioValidita", jdbcType=JdbcType.TIMESTAMP),
       @Result(column="fine_val", property="dataFineValidita", jdbcType=JdbcType.TIMESTAMP)
       
   })
   List<MedicoExt> selectDelegatiByCFDelegante(String cfDelegante);

	
	@Update({ "update r_medico_medico", "set ", "data_cancellazione = NOW(), data_modifica = NOW()",
		 " where id_medico_delegante = #{idMedicoDelegante,jdbcType=BIGINT}",
		 " and id_medico_delegato = #{idMedicoDelegato,jdbcType=BIGINT}",
		 " and data_cancellazione is null" })
	int deleteDelega(@Param("idMedicoDelegante") Long idMedicoDelegante, @Param("idMedicoDelegato") Long idMedicoDelegato);

	
	@Insert({
		 "insert into r_medico_medico ",
		 "(id_medico_medico, id_medico_delegante, id_medico_delegato,",
		 " validita_inizio, validita_fine, utente_operazione, data_creazione, data_modifica, data_cancellazione)",
		 " values ", "( nextval('r_medico_medico_id_medico_medico_seq'), #{idMedicoDelegante,jdbcType=BIGINT}, #{idMedicoDelegato,jdbcType=BIGINT}, ",
		 " #{validitaInizio,jdbcType=TIMESTAMP}, #{validitaFine,jdbcType=TIMESTAMP}, #{utenteOperazione,jdbcType=VARCHAR}, ",
		 " NOW(), NOW(), NULL)"})
	@Options(useGeneratedKeys = true, keyProperty = "idMedicoMedico")
	int insertDelega(RMedicoMedico medico);

	
	@Update({ "update r_medico_medico", "set ", " data_modifica = NOW(), ",
		" validita_fine = #{dataFineValidita,jdbcType=TIMESTAMP}",
		 " where id_medico_delegante = #{idMedicoDelegante,jdbcType=BIGINT}",
		 " and id_medico_delegato = #{idMedicoDelegato,jdbcType=BIGINT}",
		 " and data_cancellazione is null" })
	int updateDelega(@Param("idMedicoDelegante") Long idMedicoDelegante, @Param("idMedicoDelegato") Long idMedicoDelegato,
			@Param("dataFineValidita") Date dataFineValidita);


}
