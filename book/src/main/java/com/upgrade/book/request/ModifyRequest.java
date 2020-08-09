package com.upgrade.book.request;

import javax.validation.constraints.NotNull;

import org.junit.platform.commons.util.StringUtils;

public class ModifyRequest extends BookRequest implements IRequestValidator{
	@NotNull
	private String reserveId;

	public ModifyRequest() {
		super();
	}

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	
	@Override
	public boolean isValid() {
		return super.isValid() && !StringUtils.isBlank(reserveId);
	}

}
