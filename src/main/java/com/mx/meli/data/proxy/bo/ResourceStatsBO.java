package com.mx.meli.data.proxy.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceStatsBO  implements IBO{

	private static final long serialVersionUID = 1L;
	
	private String resource;
	private List<HourStatBO> total;
}
