package it.csi.gestionepazienti.gestionepazientiweb.mapper.extend;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import it.csi.gestionepazienti.gestionepazientiweb.dto.custom.Audit;

public interface AuditMapper {

	@Insert({ "insert into \"COVID19_audit\" (datetime, ip, \"user\", \"table\", action, description)",
			"values (NOW(), #{ip,jdbcType=VARCHAR}, #{user,jdbcType=VARCHAR}, #{table,jdbcType=VARCHAR}, "
			+ "#{action,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR})" })
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Audit audit);

//	@Insert({ "insert into COVID19_audit (datetime, ip, \"user\", \"table\", action, description)",
//		"values (NOW(), #{ip,jdbcType=VARCHAR}), #{user,jdbcType=VARCHAR}, #{table,jdbcType=VARCHAR}, "
//		+ "#{action,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR})" })
//@Options(useGeneratedKeys = true, keyProperty = "id")
//int insert(Audit audit);
	
}