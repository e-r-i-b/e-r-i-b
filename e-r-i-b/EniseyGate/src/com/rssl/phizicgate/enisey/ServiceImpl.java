package com.rssl.phizicgate.enisey;

import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * @author gladishev
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ServiceImpl implements Service
{
	private String code;
	private String name;

	public ServiceImpl(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
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
