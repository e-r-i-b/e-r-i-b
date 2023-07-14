package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.CardNotActiveAndMainException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ATMAuthenticationOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 *
 * обработчик запроса на начало входа пользователя через АТМ
 *
 * Парметры запроса:
 * cardNumber                   Номер карты                                                 [1]
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
public class StartCreateATMSessionRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE    = "startCreateATMSessionRq";
	private static final String RESPONSE_TYPE   = "startCreateATMSessionRs";

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
		return doRequest(context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String cardNumber = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		return LogIdentificationContext.createByCardNumber(cardNumber);
	}

	private LogableResponseInfo doRequest(final IdentificationContext identificationContext) throws Exception
	{
		try
		{
			trace("создаем заявку на аутентификацию для профиля " + identificationContext.getProfile().getId());
			ATMAuthenticationOperation operation = createAtmAuthenticationOperation(identificationContext);
			trace("проводим аутентификацию");
			Connector connector = operation.execute();

			return new LogableResponseInfo(prepareApiLogon(identificationContext, connector));
		}
		catch (CardNotActiveAndMainException e)
		{
			String errorMessage = "ошибка аутентификации - карта должна быть основной и активной";
			error(errorMessage, e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildAuthenticationFailedResponse(errorMessage), e);
		}
	}

	private ATMAuthenticationOperation createAtmAuthenticationOperation(IdentificationContext context) throws Exception
	{
		ATMAuthenticationOperation operation = new ATMAuthenticationOperation(context);
		operation.initialize();
		return operation;
	}
}
