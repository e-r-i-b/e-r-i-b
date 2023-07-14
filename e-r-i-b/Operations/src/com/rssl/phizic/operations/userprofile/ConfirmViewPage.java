package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author lukina
 * @ created 22.09.2011
 * @ $Author$
 * @ $Revision$
 */
//подтверждение входа на страницу
public class ConfirmViewPage implements ConfirmableObject
{
    private Long id;
	private String parameters;

	ConfirmViewPage(String url)
	{
		parameters = "pageUrl = " + url;
	}

	public Long getId()
	{
		hashParameters();
		return id;
	}

	private void hashParameters()
	{
		Integer intId = parameters.hashCode();
		id = intId.longValue();
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}
}
