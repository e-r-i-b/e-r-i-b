package com.rssl.phizic.business.documents.templates;

import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * Услуга в билинговой системе
 *
 * @author khudyakov
 * @ created 08.05.2013
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
