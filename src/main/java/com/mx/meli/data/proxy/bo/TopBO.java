package com.mx.meli.data.proxy.bo;

import lombok.Data;

@Data
public class TopBO implements IBO{

	private static final long serialVersionUID = 1L;
	private String ip;
	private String resource;
	private Integer total;
}
