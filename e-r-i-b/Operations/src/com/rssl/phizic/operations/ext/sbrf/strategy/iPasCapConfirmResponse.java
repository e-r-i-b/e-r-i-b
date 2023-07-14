package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.phizic.auth.modes.ConfirmResponse;

/**
 * User: Moshenko
 * Date: 14.05.12
 * Time: 11:51
 * Ответ для стратегии iPasCapConfirmStrategy
 */
public class iPasCapConfirmResponse implements ConfirmResponse
{
	private String capToken;

	public iPasCapConfirmResponse(String capToken)
	{
		this.capToken = capToken;
	}

	public String getCapToken()
	{
		return capToken;
	}
}
