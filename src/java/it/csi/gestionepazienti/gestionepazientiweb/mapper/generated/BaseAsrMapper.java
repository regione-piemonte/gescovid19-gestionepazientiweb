package it.csi.gestionepazienti.gestionepazientiweb.mapper.generated;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Asr;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

public interface BaseAsrMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table asr
	 * @mbg.generated
	 */
	@Delete({ "delete from asr", "where id_asr = #{idAsr,jdbcType=BIGINT}" })
	int deleteByPrimaryKey(Long idAsr);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table asr
	 * @mbg.generated
	 */
	@Insert({ "insert into asr (descrizione, id_ente)",
			"values (#{descrizione,jdbcType=VARCHAR}, #{idEnte,jdbcType=INTEGER})" })
	@Options(useGeneratedKeys = true, keyProperty = "idAsr")
	int insert(Asr record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table asr
	 * @mbg.generated
	 */
	@Select({ "select", "id_asr, descrizione, id_ente", "from asr", "where id_asr = #{idAsr,jdbcType=BIGINT}" })
	@Results({ @Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "descrizione", property = "descrizione", jdbcType = JdbcType.VARCHAR),
			@Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER) })
	Asr selectByPrimaryKey(Long idAsr);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table asr
	 * @mbg.generated
	 */
	@Select({ "select", "id_asr, descrizione, id_ente", "from asr" })
	@Results({ @Result(column = "id_asr", property = "idAsr", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "descrizione", property = "descrizione", jdbcType = JdbcType.VARCHAR),
			@Result(column = "id_ente", property = "idEnte", jdbcType = JdbcType.INTEGER) })
	List<Asr> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table asr
	 * @mbg.generated
	 */
	@Update({ "update asr", "set descrizione = #{descrizione,jdbcType=VARCHAR},",
			"id_ente = #{idEnte,jdbcType=INTEGER}", "where id_asr = #{idAsr,jdbcType=BIGINT}" })
	int updateByPrimaryKey(Asr record);
}