package com.rssl.phizic.gate.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * Услуга в билинговой системе
 *
 * @author khudyakov
 * @ created 29.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ServiceImpl implements Service
{
	private String name;
	private String code;

	public ServiceImpl(String name, String code)
	{
		this.name = name;
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public String getCode()
	{
		return code;
	}
}
