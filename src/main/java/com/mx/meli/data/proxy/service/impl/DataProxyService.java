package com.mx.meli.data.proxy.service.impl;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.mx.meli.data.proxy.bo.HourStatBO;
import com.mx.meli.data.proxy.bo.ResourceStatsBO;
import com.mx.meli.data.proxy.bo.TopBO;
import com.mx.meli.data.proxy.mapper.TopIpMapper;
import com.mx.meli.data.proxy.mapper.TopResourceMapper;
import com.mx.meli.data.proxy.service.IDataProxyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataProxyService implements IDataProxyService{
	
	@Autowired
    @Qualifier("endpointsCat")
    private Map<String, Integer> endPointsCat;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	
	@Override
	public List<TopBO> getTopPath( Date start, Date end, Integer limit ) {
		List<TopBO> top = null;
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuffer query = new StringBuffer();
		
		if(limit == null || limit == 0) {
			limit = 10;
		}

		query.append( "SELECT tarjet_path AS resource, COUNT(*) AS count "	);
		query.append( "FROM fidena.requests AS re "							);
		query.append( "JOIN fidena.cat_endpoints as ca "					);
		query.append( "ON ca.id = re.tarjet_path_id "						);
		query.append( "WHERE 1= 1 	 "										);

		if(start != null) {
			query.append( "AND  date >= :start  "); 
			parameters.addValue("start", start);
		}
		
		if(end != null) {
			query.append( "AND  date <= :end  "); 
			parameters.addValue("end", end);
		}
		
		query.append( "GROUP BY resource ORDER BY count DESC  LIMIT :limit");
		
		parameters.addValue("limit", limit, Types.SMALLINT);
		
		log.debug("QUERY [{}]", query);
		
		top = namedJdbcTemplate.query(query.toString(), parameters, new TopResourceMapper());
		
		if(top == null) {
			top = new ArrayList<TopBO>();
		}

		log.info("response [{}]", top.size());
		
		return top;
	}

	@Override
	public List<TopBO> getTopIp( Date start, Date end, Integer limit ) {
		List<TopBO> top = null;
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuffer query = new StringBuffer();
		
		if(limit == null || limit == 0) {
			limit = 10;
		}

		query.append( "SELECT ip AS ip, COUNT(*) AS count "	);
		query.append( "FROM fidena.requests "				);
		query.append( "WHERE 1= 1 	 "						);

		if(start != null) {
			query.append( "AND  date >= :start  "); 
			parameters.addValue("start", start);
		}
		
		if(end != null) {
			query.append( "AND  date <= :end  "); 
			parameters.addValue("end", end);
		}
		
		query.append( "GROUP BY ip ORDER BY count DESC  LIMIT :limit");
		
		parameters.addValue("limit", limit, Types.SMALLINT);
		
		log.debug("QUERY [{}]", query);
		
		top = namedJdbcTemplate.query(query.toString(), parameters, new TopIpMapper());
		
		if(top == null) {
			top = new ArrayList<TopBO>();
		}

		log.info("response [{}]", top.size());
		
		return top;
	}

	@Override
	public List<TopBO> getTopIpAndPath(Date start, Date end, Integer limit) {
		List<TopBO> top = null;
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		StringBuffer query = new StringBuffer();
		
		if(limit == null || limit == 0) {
			limit = 10;
		}

		query.append( "SELECT ip AS ip, tarjet_path AS resource, COUNT(*) AS count "	);
		query.append( "FROM fidena.requests AS re "										);
		query.append( "JOIN fidena.cat_endpoints as ca "								);
		query.append( "ON ca.id = re.tarjet_path_id "									);
		query.append( "WHERE 1= 1 	 "													);

		if(start != null) {
			query.append( "AND  date >= :start  "); 
			parameters.addValue("start", start);
		}
		
		if(end != null) {
			query.append( "AND  date <= :end  "); 
			parameters.addValue("end", end);
		}
		
		query.append( "GROUP BY ip,resource ORDER BY count DESC  LIMIT :limit");
		
		parameters.addValue("limit", limit, Types.SMALLINT);
		
		log.debug("QUERY [{}]", query);
		
		top = namedJdbcTemplate.query(query.toString(), parameters, new TopResourceMapper());
		
		if(top == null) {
			top = new ArrayList<TopBO>();
		}

		log.info("response [{}]", top.size());
		
		return top;
	}
	
	@Override
	public List<ResourceStatsBO> getDataResourceToday() {

		List<ResourceStatsBO> response = new ArrayList<ResourceStatsBO>();
		String dateString = com.mx.meli.data.proxy.utils.DateUtils.getDateNow("yyyy-MM-dd");
		
		Map<String, List<HourStatBO>> stats = getPaths();
	

		int hour = LocalDateTime.now().getHour();
		
		log.debug("dateString {}",dateString );
		log.debug("hour {}",hour );
		
		
		Date startDate = com.mx.meli.data.proxy.utils.DateUtils.getDateByString( dateString + " 00:00:00" , "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtils.addHours(startDate, 1); 
		
		
		for (int i = 0; i <= hour; i++) {
			
			log.debug("startDate {} ", startDate);
			log.debug("endDate {} ", endDate);
			
			List<TopBO> tops = execQuery(startDate , endDate);
			
			insertStats( tops, stats,  i ) ;
			
			startDate = DateUtils.addHours(startDate, 1); 
			endDate   = DateUtils.addHours(endDate, 1); 
			
		}
		
		response = fillResponse ( stats );
		return response;
	}
	private List<ResourceStatsBO>  fillResponse( Map<String, List<HourStatBO>> stats ) {
		
		List<ResourceStatsBO> response = new ArrayList<ResourceStatsBO>();
		
		 for (Map.Entry<String, List<HourStatBO>>  entry : stats.entrySet()) {
			 ResourceStatsBO resourceStatsBO = new ResourceStatsBO( entry.getKey(), entry.getValue() );
			 response.add(resourceStatsBO);
		 }
	
		
		 return response;
	}
	
	private void insertStats( final List<TopBO> tops, Map<String, List<HourStatBO>> stats, final int index ) {
		
		 for (Map.Entry<String, List<HourStatBO>>  entry : stats.entrySet()) {
			 HourStatBO stat = new HourStatBO(  index + 1 + " Hrs" , 0);
			 entry.getValue().add(stat);
			
		 }
		 
		 for (TopBO topBO : tops) {
			 
			 HourStatBO stat = new HourStatBO();
			 stat.setHour( index + 1 + " Hrs" );
			 stat.setTotal(topBO.getTotal());
			 
			 stats.get(topBO.getResource()).remove(index);
			 stats.get(topBO.getResource()).add(stat);
			
		}
		
		 log.debug("res map {}",stats);
		
	}
	
	
	private List<TopBO> execQuery(final Date startDate , final Date endDate ){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<TopBO> tops = null;
		
		StringBuffer query = new StringBuffer();
		
		query.append( "SELECT tarjet_path AS resource, COUNT(*) AS count "	);
		query.append( "FROM fidena.requests AS re "							);
		query.append( "JOIN fidena.cat_endpoints as ca "					);
		query.append( "ON ca.id = re.tarjet_path_id "						);
		query.append( "WHERE 1= 1 	 "										);


		if(startDate != null) {
			query.append( "AND  date >= :startDate  "); 
			parameters.addValue("startDate", startDate);
		}
		
		if(endDate != null) {
			query.append( "AND  date < :endDate  "); 
			parameters.addValue("endDate", endDate);
		}
		
		query.append( "GROUP BY resource ORDER BY count DESC ");
	
		
		log.debug("QUERY [{}]", query);
		
		tops = namedJdbcTemplate.query(query.toString(), parameters, new TopResourceMapper());
		
		
		 
		if(tops == null) {
			tops = new  ArrayList<>();
		}
		return tops;
		
		
	}
	
	private Map<String, List<HourStatBO>> getPaths(){
		
		Map<String, List<HourStatBO>> res = new HashMap<String, List<HourStatBO>>();
		
		 for (Map.Entry<String, Integer>  entry : endPointsCat.entrySet()) {
			 res.put(entry.getKey(), new ArrayList<HourStatBO>(0));
		 }
		 log.info("res map {}",res);
		return res;
		
	}
	
	@Override
	public List<String> get(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}



}
