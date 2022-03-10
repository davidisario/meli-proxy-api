package com.mx.meli.data.proxy.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourStatBO implements IBO{

	private static final long serialVersionUID = 1L;
	private String hour;
	private Integer total;
}
