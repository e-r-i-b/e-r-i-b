package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.monitoring.fraud.ClientTransactionCompositeId;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.Calendar;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Хелпер формирования запроса во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public class FraudMonitoringRequestHelper
{
	public static final String WEB_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE                   = "WEBAPI";
	public static final String MOBILE_API_CLIENT_DEFINED_CHANNEL_INDICATOR_VALUE                = "MOBILEAPI";
	public static final String FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY                 = "FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY";


	/**
	 * Сформировать заголовок запроса (MessageHeader)
	 * @param requestType тип запроса
	 * @return заголовок запроса
	 */
	public static MessageHeader createMessageHeader(RequestType requestType)
	{
		return new MessageHeader(APIType.DIRECT_SOAP_API, null, requestType, DateHelper.toISO8601_24HourDateFormat(Calendar.getInstance()), MessageVersion.value1);
	}

	/**
	 * Сформировать заголовок запроса (SecurityHeader)
	 * @return заголовок запроса
	 */
	public static SecurityHeader createSecurityHeader()
	{
		return new SecurityHeader(RSAConfig.getInstance().getPassword(), RSAConfig.getInstance().getLogin(), AuthorizationMethod.PASSWORD);
	}

	/**
	 * Идентификатор профиля клиента в БД ЦСА
	 * @param context контекст
	 * @return идентификатор
	 */
	public static Long getUserName(AuthenticationContext context)
	{
		UserVisitingMode visitingMode = context.getVisitingMode();
		if (UserVisitingMode.GUEST == visitingMode)
		{
			return context.getGuestProfileId();
		}
		return context.getCsaProfileId();
	}

	/**
	 * Заполнить структуру DeviceRequest
	 * @return DeviceRequest
	 */
	public static DeviceRequest createDeviceRequest()
	{
		DeviceRequest deviceRequest = new DeviceRequest();
		deviceRequest.setHttpAccept(HeaderContext.getHttpAccept());
		deviceRequest.setHttpAcceptChars(HeaderContext.getHttpAcceptChars());
		deviceRequest.setHttpAcceptEncoding(HeaderContext.getHttpAcceptEncoding());
		deviceRequest.setHttpAcceptLanguage(HeaderContext.getHttpAcceptLanguage());
		deviceRequest.setHttpReferrer(HeaderContext.getHttpReferrer());
		deviceRequest.setIpAddress(LogThreadContext.getIPAddress());
		deviceRequest.setUserAgent(HeaderContext.getUserAgent());
		deviceRequest.setPageId(HeaderContext.getPageId());
		deviceRequest.setDomElements(RSAContext.getDomElements());
		deviceRequest.setJsEvents(RSAContext.getJsEvents());
		deviceRequest.setDevicePrint(RSAContext.getDevicePrint());
		deviceRequest.setDeviceTokenFSO(RSAContext.getDeviceTokenFSO());
		deviceRequest.setDeviceTokenCookie(RSAContext.getDeviceTokenCookie());
		if(ApplicationConfig.getIt().getApplicationInfo().isMobileApi())
		{
			MobileDevice mobileDevice = new MobileDevice();
			mobileDevice.setMobileSdkData(RSAContext.getMobileSdkData());
			deviceRequest.setDeviceIdentifier(new DeviceIdentifier[]{mobileDevice});
		}

		return deviceRequest;
	}

	/**
	 * Сгенерировать и сохранить lkz для текущего приложения в store ClientTransactionId
	 * @param csaProfileId идентификаторо профиля клиента в ЦСА
	 * @param nodeProfileId идентификаторо профиля клиента в блоке
	 * @param nodeLoginId идентификатор логина в блоке
	 * @return ClientTransactionId
	 */
	public static String generateAndStoreClientTransactionId(Long csaProfileId, Long nodeProfileId, Long nodeLoginId)
	{
		String id = generateClientTransactionId(csaProfileId, nodeProfileId, nodeLoginId);
		StoreManager.getCurrentStore().save(ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY, id);
		return id;
	}

	/**
	 * Сгенерировать ClientTransactionId
	 * @param csaProfileId идентификатор профиля клиента в ЦСА
	 * @param nodeProfileId идентификатор анкеты клиента в блоке
	 * @param nodeLoginId идентификатор логина в блоке
	 * @return ClientTransactionId
	 */
	public static String generateClientTransactionId(Long csaProfileId, Long nodeProfileId, Long nodeLoginId)
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return new ClientTransactionCompositeId(config.getGroupId(), config.getNodeNumber(), csaProfileId, nodeProfileId, nodeLoginId).toString();
	}

	/**
	 * Сгенерировать ClientTransactionId
	 * @param csaProfileId идентификатор профиля клиента в ЦСА
	 * @return ClientTransactionId
	 */
	public static String generateClientTransactionId(Long csaProfileId)
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return new ClientTransactionCompositeId(config.getGroupId(), config.getNodeNumber(), csaProfileId, null, null).toString();
	}

	/**
	 * Восстановить из store ClientTransactionId
	 * @return ClientTransactionId
	 */
	public static String restoreClientTransactionId()
	{
		Store store = StoreManager.getCurrentStore();
		String key = ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY;

		Object id = store.restore(key);
		if (id == null)
		{
			throw new IllegalStateException("Для выполняемой операции в приложении " + ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + " не установлен ClientTransactionId");
		}

		store.remove(key);
		return id.toString();
	}
}