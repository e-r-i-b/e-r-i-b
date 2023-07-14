package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.ActualizationLogonInfoOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на аутуализацил информации о входе клиента(удаление лишних коннекторов).
 * Сценарий входа:
 * 1) пользователь вводит логин/пароль
 * 2) идет запрос startCreateSessionRq в ЦСА BACK
 * 3) В отете приходит идентфикатор ошибка, либо идентификатор операции входа
 * 4) Фронт осуществляет форвард в целевую АС с передачей идентифкатора операции входа.
 * 5) АС вызывает запрос finishCreateSessionRq в ЦСА BACK
 * 6) ЦСА BACK возвращает информацию ою аутентифицировавшемся пользователе.
 * Если после аутентификации обнаруживается, что у клиента зарегистрировано несколько CSA-коннекторов, то возвращается ответ ошибка
 * Требуется актуализация информации о входе. При этом фронт должен отобразить пользвоателю список логинов клиента и дать выбор 1 из них.
 * После выбора логина пользователя фронт посылает запрос на актуализацию информации о входе с передачей выбранного пользователем логина.
 * И вход продолжается стандарнтым способом(возвращается токен аутентификации, который требуется передать в ЦЕЛЕВУЮ АС и тд.).
 *
 * Пармерты запроса:
 * OUID		                    идентификатор операции актуализации.                        [0..1]
 * GUID		                    идентификатор коннектора, который требуется оставить        [1]
 *
 * Параметры ответа:
 * blockingTimeout              время оставшееся до снятия                                  [0..1]
 *                              блокировки в сек в случае кода ответа
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    идентификатор операции входа.                               [0..1]
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
 *
 *
 *
 * Формат ответа в случае обнаружения нескольких логинов:
 * OUID		                    идентификатор операции актуализации информации о входе.     [0..1]
 * ConnectorInfo                Информация о коннекторе                                     [1..n]
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
 * */

public class ActualizationLogonInfoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "actualizeLogonInfoRq";
	public static final String RESPONCE_TYPE = "actualizeLogonInfoRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String guid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.GUID_TAG);

		return actualizeLogonInfo(ouid, guid);
	}

	private ResponseInfo actualizeLogonInfo(final String ouid, final String guid) throws Exception
	{
		final IdentificationContext identificationContext = IdentificationContext.createByOperationUID(ouid);
		final ActualizationLogonInfoOperation operation = identificationContext.findOperation(ActualizationLogonInfoOperation.class, ouid, UserLogonOperation.getLifeTime());
		trace("заявка " + operation.getOuid() + " успешно получена. Исполняем ее.");
		Connector connector = operation.execute(guid);
		trace("заявка " + operation.getOuid() + " успешно исполнена. Осуществляем вход.");
		return prepareLogon(identificationContext, connector);
	}
}