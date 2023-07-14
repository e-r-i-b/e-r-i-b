package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запрос на аутентификацию пользователя чераз МАПИ
 *
 * Параметры запроса:
 * OUID		                    операции входа. 	[1]
 *
 * Параметры ответа:
 * blockingTimeout              время оставшееся до снятия                                  [0..1]
 *                              блокировки в сек в случае кода ответа
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * SessionInfo                  информация о сессии                                         [0..1]
 *      SID		                идентификатор открытой сессии.                              [1]
 *      creationDate            дата создания сессии                                        [1]
 *      expireDate              дата инвалиации сессии (после истечения этой даты
 *                              запросы по сессии не будут приниматься)                     [1]
 *      prevSessionDate         дата открытия предыдущей сессии                             [0..1]
 *      prevSID                 идентификатор предыдущей сессии                             [0..1]
 * UserInfo		                Информация о пользователе                                   [0..1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 * ConnectorInfo                Информация о коннекторе                                     [0..1]
 *      GUID                    идентификатор коннектора                                     [1]
 *      deviceState             предыдущее состояние устройства                             [0..1]
 *      deviceInfo              информация об устройстве                                    [0..1]
 *      cbCode                  Подразделение пользователя                                  [1]
 *      userId                  Цифровой логин пользователя                                 [1]
 *      cardNumber              "карта входа"                                               [1]
 *      login                   логин/алиас                                                 [0..1]
 *      type                    тип коннектора(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            дата создания коннектора                                    [1]
 *      passwordCreationDate    дата создания пароля                                        [0..1]
 *      devID                   идентификатор устройства                                    [0..1]
 * authorizedZone               тип зоны входы пользователя                                 [1]
 */
public class FinishCreateMobileSessionRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE    = "finishCreateMobileSessionRq";
	private static final String RESPONSE_TYPE   = "finishCreateMobileSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
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

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return doRequest(context, ouid);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

    protected LogableResponseInfo doRequest(final IdentificationContext identificationContext, String ouid) throws Exception
	{
		final UserLogonOperation operation = identificationContext.findOperation(UserLogonOperation.class, ouid, UserLogonOperation.getLifeTime());
		try
		{
			trace("завка на вход " + operation.getOuid() + " успешно получена. Исполняем ее.");
			Session session = operation.execute();
			trace("заявка на вход " + operation.getOuid() + " успешно исполнена.");
			return new LogableResponseInfo(buildSuccessResponse(session, operation.getAuthorizedZoneType(), operation.getMobileSDKData(), identificationContext.getProfile()));
		}
		catch (ConnectorNotFoundException e)
		{
			error("ошибка проверки сессии", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadOUIDResponse(),e);
		}
	}

	private ResponseInfo buildSuccessResponse(Session session, AuthorizedZoneType authorizedZoneType, String mobileSdkData, Profile profile) throws Exception
	{
		Connector connector = session.getConnector();
		return getSuccessResponseBuilder()
				.addSessionInfo(session)
				.addUserInfo(connector.getProfile())
				.addConnectorInfo(connector)
				.addAuthorizedZoneType(authorizedZoneType)
				.addSecurityType(connector)
				.addProfileNodeInfo(Utils.getActiveProfileNode(connector.getProfile().getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES))
				.addMobileSDKData(mobileSdkData)
				.addProfileId(profile)
				.end().getResponceInfo();
	}
}
