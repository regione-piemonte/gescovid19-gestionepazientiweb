package it.csi.gestionepazienti.gestionepazientiweb.mapper.generated;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Laboratorio;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

public interface BaseLaboratorioMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table laboratorio
	 * @mbg.generated
	 */
	@Delete({ "delete from laboratorio", "where id_laboratorio = #{idLaboratorio,jdbcType=BIGINT}" })
	int deleteByPrimaryKey(Long idLaboratorio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table laboratorio
	 * @mbg.generated
	 */
	@Insert({ "insert into laboratorio (descrizione, sigla_laboratorio, ", "capacita_giornaliera)",
			"values (#{descrizione,jdbcType=VARCHAR}, #{siglaLaboratorio,jdbcType=VARCHAR}, ",
			"#{capacitaGiornaliera,jdbcType=INTEGER})" })
	@Options(useGeneratedKeys = true, keyProperty = "idLaboratorio")
	int insert(Laboratorio record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table laboratorio
	 * @mbg.generated
	 */
	@Select({ "select", "id_laboratorio, descrizione, sigla_laboratorio, capacita_giornaliera", "from laboratorio",
			"where id_laboratorio = #{idLaboratorio,jdbcType=BIGINT}" })
	@Results({ @Result(column = "id_laboratorio", property = "idLaboratorio", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "descrizione", property = "descrizione", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sigla_laboratorio", property = "siglaLaboratorio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "capacita_giornaliera", property = "capacitaGiornaliera", jdbcType = JdbcType.INTEGER) })
	Laboratorio selectByPrimaryKey(Long idLaboratorio);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table laboratorio
	 * @mbg.generated
	 */
	@Select({ "select", "id_laboratorio, descrizione, sigla_laboratorio, capacita_giornaliera", "from laboratorio" })
	@Results({ @Result(column = "id_laboratorio", property = "idLaboratorio", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "descrizione", property = "descrizione", jdbcType = JdbcType.VARCHAR),
			@Result(column = "sigla_laboratorio", property = "siglaLaboratorio", jdbcType = JdbcType.VARCHAR),
			@Result(column = "capacita_giornaliera", property = "capacitaGiornaliera", jdbcType = JdbcType.INTEGER) })
	List<Laboratorio> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table laboratorio
	 * @mbg.generated
	 */
	@Update({ "update laboratorio", "set descrizione = #{descrizione,jdbcType=VARCHAR},",
			"sigla_laboratorio = #{siglaLaboratorio,jdbcType=VARCHAR},",
			"capacita_giornaliera = #{capacitaGiornaliera,jdbcType=INTEGER}",
			"where id_laboratorio = #{idLaboratorio,jdbcType=BIGINT}" })
	int updateByPrimaryKey(Laboratorio record);
}