package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author EgorovaA
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на изменение настройки приватности changeIncognitoSettingRq
 *
 * Парметры запроса:
 * SID		                идентификатор сессии           [0-1] (0 -- если задан UserInfo)
 * UserInfo                 Информация о пользователе      [0-1] (0 -- если задан SID)
 *      firstname               Имя пользователя           [1]
 *      patrname                Отчество пользователя      [0..1]
 *      surname                 Фамилия пользователя       [1]
 *      birthdate               Дата рождения пользователя [1]
 *      passport                ДУЛ пользователя           [1]
 *      tb                      ТБ пользователя            [1]
 * incognito   		        признак приватности            [1]
 */
public class ChangeIncognitoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "changeIncognitoSettingRq";
	public static final String RESPONCE_TYPE = "changeIncognitoSettingRs";

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		boolean incognito = IncognitoState.valueOf(XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.INCOGNITO_SETTING_PARAM_NAME)) == IncognitoState.incognito;
		return setIncognitoParam(requestInfo, incognito);
	}

	private ResponseInfo setIncognitoParam(RequestInfo requestInfo, boolean incognito) throws Exception
	{
		final IdentificationContext identificationContext = getIdentificationContextByUserInfoOrSID(requestInfo);
		Profile profile = identificationContext.getProfile();
		profile.setIncognito(incognito);
		profile.save();
		info("Для профиля " + profile.getId() + " изменена настройка приватности. Новое значение - "+ incognito);
		return buildSuccessResponse();
	}
}
