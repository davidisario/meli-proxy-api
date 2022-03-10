package com.mx.meli.data.proxy.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mx.meli.data.proxy.utils.JacksonUtils;

@JsonInclude(Include.NON_NULL) 
@JsonIgnoreProperties( ignoreUnknown = true)
public interface IBO extends Serializable {
	

	default String toJsonString() throws JsonProcessingException{
		
		  return JacksonUtils.JACKSON.writeValueAsString(this); 
	}
}
