package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.RequestProcessor;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetClientNodeStateRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetClientProfileIdProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetTemporaryNodeClientsCountRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.UpdateClientMDMInfoProcessor;
import com.rssl.auth.csa.back.protocol.handlers.guest.*;
import com.rssl.auth.csa.back.protocol.handlers.nodes.ChangeNodesAvailabilityInfoRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.CreateProfileRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileCardNumberListRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileInformationRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileInformationWithNodeInfoRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.LockProfileCHG071536RequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.LockProfileForExecuteDocumentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.UnlockProfileForExecuteDocumentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.verification.InitializeVerifyBusinessEnvironmentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.verification.VerifyBusinessEnvironmentRequestProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * "Маршрутизатор" запросов. Ведет соответствие типа запроса и обработчика. 
 */

public class RequestRouter implements RequestProcessor
{
	private static final RequestRouter INSTANCE = new RequestRouter();
	private final Map<String, RequestProcessor> processors = new HashMap<String, RequestProcessor>();

	private RequestRouter()
	{
		registerProcessor(new ConfirmOperationRequestProcessor());
		registerProcessor(new ConfirmGuestOperationRequestProcessor());
		// аутентификация пользователя
		registerProcessor(new AuthenticationRequestProcessor());

		//Регистрация пользователя
		registerProcessor(new StartUserRegistrationRequestProcessor());
		registerProcessor(new FinishUserRegistrationRequestProcessor());

		//Самостоятельная регистрация пользователя
		registerProcessor(new StartUserSelfRegistrationRequestProcessor());
		registerProcessor(new FinishUserSelfRegistrationRequestProcessor());

		//Восстановление пароля
		registerProcessor(new RouteRestorePasswordRequestProcessor());
		registerProcessor(new FinishClientRestorePasswordRequestProcessor());
		registerProcessor(new FinishGuestRestorePasswordRequestProcessor());

		//Открытие сессии ЕРИБ
		registerProcessor(new RouterCreateSessionRequestProcessor());
		registerProcessor(new FinishCreateSessionRequestProcessor());
		registerProcessor(new ActualizationLogonInfoRequestProcessor());

		//Пинг сессии
		registerProcessor(new CheckSessionRequestProcessor());

		//Проверка пароля текущей сессии
		registerProcessor(new CheckPasswordRequestProcessor());

		//Закрытие сессии
		registerProcessor(new CloseSessionRequestProcessor());

		//Генерация пароля
		registerProcessor(new GeneratePasswordRequestProcessor());
		registerProcessor(new GeneratePassword2RequestProcessor());

		//Валидация пароля
		registerProcessor(new ValidatePasswordRequestProcessor());
		//Смена пароля
		registerProcessor(new ChangePasswordRequestProcessor());

		//Валидация логина
		registerProcessor(new ValidateLoginRequestProcessor());
		//Смена логина
		registerProcessor(new ChangeLoginRequestProcessor());

		//Регистрация мобильного приложения
		registerProcessor(new StartMobileRegistrationRequestProcessor());
		registerProcessor(new FinishMobileRegistrationRequestProcessor());

		//Двухшаговое открытие мобильной сессии
		registerProcessor(new StartCreateMobileSessionRequestProcessor());
		registerProcessor(new FinishCreateMobileSessionRequestProcessor());

		//Отмена регистрации мобильного приложения
		registerProcessor(new CancelMobileRegistrationRequestProcessor());

		//Регистрация мобильного приложения
		registerProcessor(new StartSocialRegistrationRequestProcessor());
		registerProcessor(new FinishSocialRegistrationRequestProcessor());

		//Двухшаговое открытие мобильной сессии
		registerProcessor(new StartCreateSocialSessionRequestProcessor());
		registerProcessor(new FinishCreateSocialSessionRequestProcessor());

		//Отмена регистрации мобильного приложения
		registerProcessor(new CancelSocialRegistrationRequestProcessor());

		//интеграция с деловой средой
		//начинает процесс подтверждения верификации
		registerProcessor(new InitializeVerifyBusinessEnvironmentRequestProcessor());
		//запуск процесса верификации
		registerProcessor(new VerifyBusinessEnvironmentRequestProcessor());
		//получение информации о способах подтверждения операций
		registerProcessor(new GetConfirmationInfoRequestProcessor());

		//иземенение признака подключения push-уведомлений
		registerProcessor(new ChangePushSupportedRequestProcessor());

		//Блокировка профиля пользователя
		registerProcessor(new LockProfileRequestProcessor());
		//Разлокировка профиля пользователя
		registerProcessor(new UnlockProfileRequestProcessor());

		//Двухшаговое открытие сессии АТМ
		registerProcessor(new StartCreateATMSessionRequestProcessor());
		registerProcessor(new FinishCreateATMSessionRequestProcessor());

		//Регистрация телефона
		registerProcessor(new UpdatePhoneRegistrationsRequestProcessor());
		registerProcessor(new UpdatePhoneRegRemoveDuplicateRequestProcessor());
		//Поиск блока пользователя по телефону
		registerProcessor(new FindProfileNodeByPhoneRequestProcessor());
		//Поиск блока пользователя по ФИО ДУЛ ДР ТБ
		registerProcessor(new FindProfileNodeByUserInfoRequestProcessor());
		//Обновление профиля
		registerProcessor(new UpdateProfileRequestProcessor());
		//Получение информации о блоках
		registerProcessor(new GetNodesInfoRequestProcessor());

		//Получить информацию о пользователе по телефону с запросом МБК, если в ЦСА данные не найдены
		registerProcessor(new GetUserInfoByPhoneWithMBRequestProcessor());
		//Получить информацию о пользователе по телефону
		registerProcessor(new GetUserInfoByPhoneRequestProcessor());
		//Получить информацию о пользователе по ИД в соцсети
		registerProcessor(new GetUserInfoByDeviceIdAndInfoRequestProcessor());

		//получение настройки приватности клиента
		registerProcessor(new GetIncognitoRequestProcessor());
		//иземенение настройки приватности клиента
		registerProcessor(new ChangeIncognitoRequestProcessor());
		//получение списка клиентов
		registerProcessor(new GetClientsInformationRequestProcessor());
		//получение списка клиентов, ожидающих миграции
		registerProcessor(new GetTemporaryNodeClientsInformationRequestProcessor());
		//получение количества клиентов, ожидающих миграции
		registerProcessor(new GetTemporaryNodeClientsCountRequestProcessor());
		//получение состояния клиента
		registerProcessor(new GetClientNodeStateRequestProcessor());
		//получение истории изменения клиента
		registerProcessor(new GetProfileHistoryInfoRequestProcessor());
		//Обновление дополнительными данными профиля клиента
		registerProcessor(new UpdateProfileAdditionalDataRequestProcessor());
		//Установка лока на профиль для исполнения документа сотрудником
		registerProcessor(new LockProfileForExecuteDocumentRequestProcessor());
		//Снятие лока с профиля после исполнения документа сотрудником
		registerProcessor(new UnlockProfileForExecuteDocumentRequestProcessor());

		//получение информации о блоках изменных профилей
		registerProcessor(new FindNodesByUpdatedProfilesRequestProcessor());
		//Поиск информации о пользователе
		registerProcessor(new FindProfileInformationRequestProcessor());
		//Поиск профиля клиента ЦСА с полной информацией об узлах в которых работал клиент
		registerProcessor(new FindProfileInformationWithNodeInfoRequestProcessor());
		//Создание профиля
		registerProcessor(new CreateProfileRequestProcessor());
		//получение одноразового логина и пароля через УС
		registerProcessor(new UserRegistrationDisposableProcessor());

		//присвоение профилю низкого уровня безопасности
		registerProcessor(new LowerProfileSecurityTypeRequestProcessor());

		//получение информации о текущих коннекторах ермб
		registerProcessor(new GetErmbConnectorInfoProcessor());

		//получение информации о незакрытых коннекторах клиента
		registerProcessor(new GetClientConnectorsProcessor());
		//блокировка пользователя в рамках запроса CHG071536
		registerProcessor(new LockProfileCHG071536RequestProcessor());
		//блокировка пользователя в рамках запроса CHG071536
		registerProcessor(new LockProfileCHG071536ByProfileIdRequestProcessor());
		//получение списка карт для профиля
		registerProcessor(new FindProfileCardNumberListRequestProcessor());
		//получение списка телефонов клиента
		registerProcessor(new GetErmbPhonesProcessor());

		//информации о состоянии блоков
		registerProcessor(new ChangeNodesAvailabilityInfoRequestProcessor());

		//Список номеров ЕРМБ
		registerProcessor(new HasUserByPhoneRequestProcessor());

		//Выставление/снятие технологического перерыва МБК
		registerProcessor(new MbkTechnoBreakSaveOrUpdateProcessor());

		//Получение информации о получателе запроса на сбор средств
		registerProcessor(new FindFundSenderInfoRequestProcessor());

		//Получение информации о наличии мАпи про-версии
		registerProcessor(new GetContainsProMAPIInfoRequestProcessor());

		//Аутентификация гостевого входа
		registerProcessor(new StartCreateGuestSessionRequestProcessor());
		registerProcessor(new AuthGuestSessionRequestProcessor());
		registerProcessor(new FinishCreateGuestSessionRequestProcessor());

		//Получение доп информации по гостю
		registerProcessor(new AdditionInformationForGuestRequestProcessor());

		// Регистрация гостя
		registerProcessor(new GuestRegistrationRequestProcessor());

		//проверка IMSI
		registerProcessor(new CheckIMSIRequestProcessor());
		//обновление профиля данными из мдм
		registerProcessor(new UpdateClientMDMInfoProcessor());
		registerProcessor(new GetClientProfileIdProcessor());
	}

	private void registerProcessor(RequestProcessorBase processor)
	{
		processors.put(processor.getRequestType(), processor);
	}

	/**
	 * @return инстанс.
	 */
	public static RequestRouter getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Найти соотвествующий запросу обработчик и делегировать вызов
	 * @param requestInfo информация о запросе
	 * @return информация об ответе
	 */
	public ResponseInfo process(RequestInfo requestInfo) throws Exception
	{
		RequestProcessor requestHandler = processors.get(requestInfo.getType());
		if (requestHandler == null)
		{
			requestHandler = UnsupportedMessageTypeProcessor.INSTANCE;
		}
		return requestHandler.process(requestInfo);
	}

	public boolean isAccessStandIn()
	{
		return true;
	}
}
