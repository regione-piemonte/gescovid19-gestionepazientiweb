package it.csi.gestionepazienti.gestionepazientiweb.mapper.util;

import org.apache.commons.lang3.StringUtils;

public class Paginazione {
	String orderBy;
	Long offset;
	Long limit = -1L;
	boolean descending = false;
	
	public Paginazione(String orderby, Long rowsPerPage, Long pageNumber, boolean descending) {
		super();
		this.orderBy = orderby;
		this.limit = -1L;
		this.offset = 0L;
		this.descending = descending;

		if (rowsPerPage != null && rowsPerPage != -1L) {
			if (pageNumber == null || pageNumber <= 0) {
				pageNumber = 1L;
			}
			this.limit = rowsPerPage;
			this.offset = (pageNumber - 1) * rowsPerPage;
		}
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Long getOffset() {
		return offset;
	}

	public Long getLimit() {
		return limit;
	}

	public boolean isDescending() {
		return descending;
	}
	
	public String orderAscDesc() {
		if(StringUtils.isBlank(orderBy)){
			return "";
		}
		return isDescending()? "DESC": "ASC";
	}
	
	public boolean isActive() {
		return limit != -1L;
	}
	
}