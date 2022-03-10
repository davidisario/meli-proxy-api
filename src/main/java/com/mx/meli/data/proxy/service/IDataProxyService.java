package com.mx.meli.data.proxy.service;

import java.util.Date;
import java.util.List;

import com.mx.meli.data.proxy.bo.ResourceStatsBO;
import com.mx.meli.data.proxy.bo.TopBO;

public interface IDataProxyService {

	
	public List<TopBO> getTopPath		( Date start, Date end, Integer limit );
	
	public List<TopBO> getTopIp			( Date start, Date end, Integer limit	);

	public List<TopBO> getTopIpAndPath	( Date start, Date end, Integer limit	);

	public List<String> get				( Date start, Date end	);
	
	public List<ResourceStatsBO> getDataResourceToday	();
	
	
	
}
