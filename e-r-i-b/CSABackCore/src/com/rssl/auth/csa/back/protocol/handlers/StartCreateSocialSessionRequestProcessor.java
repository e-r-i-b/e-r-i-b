package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.handlers.data.StartCreateMobileSessionData;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.SocialAuthenticationOperation;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.FailedLoginInitializationData;
import com.rssl.phizic.utils.StringHelper;


/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * обработчик запроса на начало входа пользователя через МАПИ
 *
 * Парметры запроса:
 * GUID		            идентификатор мобильного приложения(mGUID). 	[1]
 * deviceState		    состояние устройства.                           [0..1]
 * devID                идентификатор устройства                        [0..1]
 * authorizedZone       тип зоны входы пользователя                     [1]
 * password             PIN-код                                         [0..1]
 *
 * Параметры ответа:
 * blockingTimeout              время оставшееся до снятия                                  [0..1]
 *                              блокировки в сек в случае кода ответа
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    идентификатор операции входа.                               [0..1]
 * nodeInfo                     Информация о блоке                                          [1]
 *      host                    хост блока                                                  [1]
 * UserInfo		                Информация о пользователе                                   [0..1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 * ConnectorInfo                Информация о коннекторе                                     [0..1]
 *      GUID                    идентификатор коннектора                                    [1]
 *      deviceState             предыдущее состояние устройства                             [0..1]
 *      deviceInfo              информация об устройстве                                    [0..1]
 *      cbCode                  Подразделение пользователя                                  [1]
 *      userId                  Цифровой логин пользователя                                 [1]
 *      cardNumber              "карта входа"                                               [1]
 *      login                   логин/алиас                                                 [0..1]
 *      type                    тип коннектора(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            дата создания коннектора                                    [1]
 *      passwordCreationDate    дата создания пароля                                        [0..1]
 * ConfirmParameters            Параметры подтверждения	                                    [0] в текущей версии не передаются.
 *      Timeout		            Таймаут ожидания подтверждения	                            [1]
 *      Attempts		        Количество оставшихся попыток ввода кода подтверждения. 	[1]
 */
public class StartCreateSocialSessionRequestProcessor extends StartCreateMobileSessionRequestProcessor
{
	private static final String REQUEST_TYPE    = "startCreateSocialSessionRq";
	private static final String RESPONCE_TYPE   = "startCreateSocialSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	protected MobileAuthenticationOperation instantiateAuthenticationOperation(IdentificationContext context)
	{
		return new SocialAuthenticationOperation(context);
	}

	@Override
	protected void exceptionProcessing(LogIdentificationContext logIdentificationContext, RequestInfo requestInfo) throws GateLogicException, GateException
	{};
}
