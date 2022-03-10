package com.mx.meli.data.proxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.meli.data.proxy.bo.ResourceStatsBO;
import com.mx.meli.data.proxy.bo.TopBO;
import com.mx.meli.data.proxy.service.IDataProxyService;
import com.mx.meli.data.proxy.utils.DateUtils;

import io.swagger.annotations.Api;


@RestController
@Api(value = "1.0 Data Proxy")
@RequestMapping(value = "/proxy")
public class DataProxyController {

	@Autowired
	private IDataProxyService dataProxyService;

	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/top/ip-and-tarjet/{start}/{end}/{limit}")
	public ResponseEntity<List<TopBO>> getTopIpAndPath( @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String start, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String end, @PathVariable Integer limit) {
		return new ResponseEntity<List<TopBO>>(dataProxyService.getTopIpAndPath(DateUtils.getDateByString(start, "yyyy-MM-dd'T'HH:mm:ss"), DateUtils.getDateByString(end, "yyyy-MM-dd'T'HH:mm:ss"), limit), HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/top/ip/{start}/{end}/{limit}")
	public ResponseEntity<List<TopBO>> getTopIp( @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String start, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String end, @PathVariable Integer limit) {
		return new ResponseEntity<List<TopBO>>(dataProxyService.getTopIp(DateUtils.getDateByString(start, "yyyy-MM-dd'T'HH:mm:ss"), DateUtils.getDateByString(end, "yyyy-MM-dd'T'HH:mm:ss"), limit), HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/top/tarjet/{start}/{end}/{limit}")
	public ResponseEntity<List<TopBO>> getTopPath(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String start, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") String end, @PathVariable Integer limit) {
		return new ResponseEntity<List<TopBO>>(dataProxyService.getTopPath(DateUtils.getDateByString(start, "yyyy-MM-dd'T'HH:mm:ss"), DateUtils.getDateByString(end, "yyyy-MM-dd'T'HH:mm:ss"), limit), HttpStatus.OK);
		
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/data/day/tarjet")
	public ResponseEntity<List<ResourceStatsBO>> getDataResourceToday() {
		return new ResponseEntity<List<ResourceStatsBO>>(dataProxyService.getDataResourceToday(), HttpStatus.OK);
		
	}
	
}
