package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import it.csi.gestionepazienti.gestionepazientiweb.dto.RMedicoMedico;
import it.csi.gestionepazienti.gestionepazientiweb.mapper.generated.BaseRMedicoMedicoMapper;

public interface RMedicoMedicoMapper extends BaseRMedicoMedicoMapper{


   @Select({
       "select ",
	   " id_medico_medico, id_medico_delegante, id_medico_delegato, validita_inizio, ", 
       " validita_fine, utente_operazione, data_creazione, data_modifica, data_cancellazione " , 
       " from r_medico_medico ",
       " where id_medico_delegato = #{idMedicoDelegato,jdbcType=INTEGER} and ", // current user
       " data_cancellazione is null and  now() between validita_inizio and coalesce(validita_fine, to_timestamp('01/01/2999','dd/mm/yyyy'))"
   })
   @Results({
       @Result(column="id_medico_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
       @Result(column="id_medico_delegante", property="cfMedico", jdbcType=JdbcType.VARCHAR),
       @Result(column="id_medico_delegato", property="codiceReg", jdbcType=JdbcType.VARCHAR),
       @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
       @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR)
   })
   List<RMedicoMedico> selectDelegatiByIdMedicoDelegato(Long idMedicoDelegato);
 
}
