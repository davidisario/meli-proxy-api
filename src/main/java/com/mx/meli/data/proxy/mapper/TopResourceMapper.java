package com.mx.meli.data.proxy.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.mx.meli.data.proxy.bo.TopBO;

public class TopResourceMapper implements RowMapper<TopBO>{

	@Override
	public TopBO mapRow(ResultSet rs, int rowNum) throws SQLException {
		TopBO top = new TopBO();
		top.setResource ( rs.getString("resource")	);
		top.setTotal	( rs.getInt("count")	);
		return top; 
	}

	
}