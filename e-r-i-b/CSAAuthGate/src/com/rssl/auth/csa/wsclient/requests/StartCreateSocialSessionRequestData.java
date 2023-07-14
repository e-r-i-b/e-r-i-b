package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.common.types.csa.AuthorizedZoneType;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * запрос на начало входа пользователя через socialAPI
 */
public class StartCreateSocialSessionRequestData extends StartCreateMobileSessionRequestData
{
	private static final String REQUEST_DATA_NAME = "startCreateSocialSessionRq";

	/**
	 * конструктор
	 * @param guid идентификатор приложения
	 * @param deviceState состояние устройства
	 * @param deviceId идентификатор устройства
	 * @param authorizedZoneType Тип зоны входа пользователя
	 */
	public StartCreateSocialSessionRequestData(String guid, String deviceState, String deviceId, AuthorizedZoneType authorizedZoneType, String pin)
	{
		super(guid, deviceState, deviceId, null, authorizedZoneType, pin, null);
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}
}
