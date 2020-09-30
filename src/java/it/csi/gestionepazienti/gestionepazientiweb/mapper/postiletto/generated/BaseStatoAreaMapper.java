package it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.StatoArea;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface BaseStatoAreaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table s_area
	 * @mbg.generated
	 */
	@Delete({ "delete from s_area", "where id_area = #{idArea,jdbcType=INTEGER}",
			"and data_evento = #{dataEvento,jdbcType=TIMESTAMP}" })
	int deleteByPrimaryKey(@Param("idArea") Integer idArea, @Param("dataEvento") Date dataEvento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table s_area
	 * @mbg.generated
	 */
	@Insert({ "insert into s_area (id_area, data_evento, ", "stato_validita)",
			"values (#{idArea,jdbcType=INTEGER}, #{dataEvento,jdbcType=TIMESTAMP}, ",
			"#{statoValidita,jdbcType=VARCHAR})" })
	int insert(StatoArea record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table s_area
	 * @mbg.generated
	 */
	@Select({ "select", "id_area, data_evento, stato_validita", "from s_area",
			"where id_area = #{idArea,jdbcType=INTEGER}", "and data_evento = #{dataEvento,jdbcType=TIMESTAMP}" })
	@Results({ @Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP, id = true),
			@Result(column = "stato_validita", property = "statoValidita", jdbcType = JdbcType.VARCHAR) })
	StatoArea selectByPrimaryKey(@Param("idArea") Integer idArea, @Param("dataEvento") Date dataEvento);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table s_area
	 * @mbg.generated
	 */
	@Select({ "select", "id_area, data_evento, stato_validita", "from s_area" })
	@Results({ @Result(column = "id_area", property = "idArea", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "data_evento", property = "dataEvento", jdbcType = JdbcType.TIMESTAMP, id = true),
			@Result(column = "stato_validita", property = "statoValidita", jdbcType = JdbcType.VARCHAR) })
	List<StatoArea> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table s_area
	 * @mbg.generated
	 */
	@Update({ "update s_area", "set stato_validita = #{statoValidita,jdbcType=VARCHAR}",
			"where id_area = #{idArea,jdbcType=INTEGER}", "and data_evento = #{dataEvento,jdbcType=TIMESTAMP}" })
	int updateByPrimaryKey(StatoArea record);
}