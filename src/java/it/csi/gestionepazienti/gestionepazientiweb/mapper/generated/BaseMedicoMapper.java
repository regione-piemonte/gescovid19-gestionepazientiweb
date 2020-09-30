package it.csi.gestionepazienti.gestionepazientiweb.mapper.generated;

import it.csi.gestionepazienti.gestionepazientiweb.dto.Medico;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface BaseMedicoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medico
     *
     * @mbg.generated
     */
    @Delete({
        "delete from medico",
        "where id_medico = #{idMedico,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long idMedico);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medico
     *
     * @mbg.generated
     */
    @Insert({
        "insert into medico (id_medico, cf_medico, ",
        "codice_reg, cognome, ",
        "nome)",
        "values (#{idMedico,jdbcType=BIGINT}, #{cfMedico,jdbcType=VARCHAR}, ",
        "#{codiceReg,jdbcType=VARCHAR}, #{cognome,jdbcType=VARCHAR}, ",
        "#{nome,jdbcType=VARCHAR})"
    })
    int insert(Medico record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medico
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id_medico, cf_medico, codice_reg, cognome, nome",
        "from medico",
        "where id_medico = #{idMedico,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="cf_medico", property="cfMedico", jdbcType=JdbcType.VARCHAR),
        @Result(column="codice_reg", property="codiceReg", jdbcType=JdbcType.VARCHAR),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR)
    })
    Medico selectByPrimaryKey(Long idMedico);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medico
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id_medico, cf_medico, codice_reg, cognome, nome",
        "from medico"
    })
    @Results({
        @Result(column="id_medico", property="idMedico", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="cf_medico", property="cfMedico", jdbcType=JdbcType.VARCHAR),
        @Result(column="codice_reg", property="codiceReg", jdbcType=JdbcType.VARCHAR),
        @Result(column="cognome", property="cognome", jdbcType=JdbcType.VARCHAR),
        @Result(column="nome", property="nome", jdbcType=JdbcType.VARCHAR)
    })
    List<Medico> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medico
     *
     * @mbg.generated
     */
    @Update({
        "update medico",
        "set cf_medico = #{cfMedico,jdbcType=VARCHAR},",
          "codice_reg = #{codiceReg,jdbcType=VARCHAR},",
          "cognome = #{cognome,jdbcType=VARCHAR},",
          "nome = #{nome,jdbcType=VARCHAR}",
        "where id_medico = #{idMedico,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Medico record);
}