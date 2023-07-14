package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * @author mihaylov
 * @ created 08.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ServiceImpl implements Service
{
	private String code;
	private String name;

	public ServiceImpl(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
