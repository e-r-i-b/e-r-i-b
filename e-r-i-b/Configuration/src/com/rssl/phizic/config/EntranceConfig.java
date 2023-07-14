package com.rssl.phizic.config;

/**
 * Конфиг для включения и отключения дополнительного запроса CEDBO по карте входа
 *
 * @ author: sergunin
 * @ created: 28.01.14
 * @ $Author$
 * @ $Revision$
 */
public class EntranceConfig extends Config
{
	private static final String USE_UBDO_CARD_REQUEST = "com.rssl.iccs.card.request.by.udbo.state";

	private boolean useUbdoCardRequest;


	public EntranceConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		useUbdoCardRequest = getBoolProperty(USE_UBDO_CARD_REQUEST);
	}

	public boolean isUseUbdoCardRequest()
	{
		return useUbdoCardRequest;
	}
}
