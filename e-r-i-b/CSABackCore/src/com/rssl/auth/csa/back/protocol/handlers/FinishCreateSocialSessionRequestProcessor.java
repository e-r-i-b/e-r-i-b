package com.rssl.auth.csa.back.protocol.handlers;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запрос на аутентификацию пользователя чераз SocialApi
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
public class FinishCreateSocialSessionRequestProcessor extends FinishCreateMobileSessionRequestProcessor
{
	private static final String REQUEST_TYPE    = "finishCreateSocialSessionRq";
	private static final String RESPONSE_TYPE   = "finishCreateSocialSessionRs";

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
}
