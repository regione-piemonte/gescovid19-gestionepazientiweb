package it.csi.gestionepazienti.gestionepazientiweb.mapper.postiletto.generated;

import it.csi.gestionepazienti.gestionepazientiweb.dto.postiletto.Disponibilita;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface BaseDisponibilitaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Delete({ "delete from disponibilita", "where id_disponibilita = #{idDisponibilita,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer idDisponibilita);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Insert({ "insert into disponibilita (id_disponibilita, id_area, ", "data_evento, tipo_operazione, ",
			"posti_target, posti_disponibili, ", "posti_occupati, flag_valido, ",
			"utente_inserimento, data_inserimento)",
			"values (#{idDisponibilita,jdbcType=INTEGER}, #{idArea,jdbcType=INTEGER}, ",
			"#{dataEvento,jdbcType=TIMESTAMP}, #{tipoOperazione,jdbcType=VARCHAR}, ",
			"#{postiTarget,jdbcType=INTEGER}, #{postiDisponibili,jdbcType=INTEGER}, ",
			"#{postiOccupati,jdbcType=INTEGER}, #{flagValido,jdbcType=VARCHAR}, ",
			"#{utenteInserimento,jdbcType=VARCHAR}, #{dataInserimento,jdbcType=TIMESTAMP})" })
	int insert(Disponibilita record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Select({ "select", "id_disponibilita, id_area, data_evento, tipo_operazione, posti_target, posti_disponibili, ",
			"posti_occupati, flag_valido, utente_inserimento, data_inserimento", "from disponibilita",
			"where id_disponibilita = #{idDisponibilita,jdbcType=INTEGER}" })
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
	Disponibilita selectByPrimaryKey(Integer idDisponibilita);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Select({ "select", "id_disponibilita, id_area, data_evento, tipo_operazione, posti_target, posti_disponibili, ",
			"posti_occupati, flag_valido, utente_inserimento, data_inserimento", "from disponibilita" })
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
	List<Disponibilita> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table disponibilita
	 * @mbg.generated
	 */
	@Update({ "update disponibilita", "set id_area = #{idArea,jdbcType=INTEGER},",
			"data_evento = #{dataEvento,jdbcType=TIMESTAMP},", "tipo_operazione = #{tipoOperazione,jdbcType=VARCHAR},",
			"posti_target = #{postiTarget,jdbcType=INTEGER},",
			"posti_disponibili = #{postiDisponibili,jdbcType=INTEGER},",
			"posti_occupati = #{postiOccupati,jdbcType=INTEGER},", "flag_valido = #{flagValido,jdbcType=VARCHAR},",
			"utente_inserimento = #{utenteInserimento,jdbcType=VARCHAR},",
			"data_inserimento = #{dataInserimento,jdbcType=TIMESTAMP}",
			"where id_disponibilita = #{idDisponibilita,jdbcType=INTEGER}" })
	int updateByPrimaryKey(Disponibilita record);
}