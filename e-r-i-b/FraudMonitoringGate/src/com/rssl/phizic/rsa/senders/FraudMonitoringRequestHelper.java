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
 * ������ ������������ ������� �� ����-����������
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
	 * ������������ ��������� ������� (MessageHeader)
	 * @param requestType ��� �������
	 * @return ��������� �������
	 */
	public static MessageHeader createMessageHeader(RequestType requestType)
	{
		return new MessageHeader(APIType.DIRECT_SOAP_API, null, requestType, DateHelper.toISO8601_24HourDateFormat(Calendar.getInstance()), MessageVersion.value1);
	}

	/**
	 * ������������ ��������� ������� (SecurityHeader)
	 * @return ��������� �������
	 */
	public static SecurityHeader createSecurityHeader()
	{
		return new SecurityHeader(RSAConfig.getInstance().getPassword(), RSAConfig.getInstance().getLogin(), AuthorizationMethod.PASSWORD);
	}

	/**
	 * ������������� ������� ������� � �� ���
	 * @param context ��������
	 * @return �������������
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
	 * ��������� ��������� DeviceRequest
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
	 * ������������� � ��������� lkz ��� �������� ���������� � store ClientTransactionId
	 * @param csaProfileId �������������� ������� ������� � ���
	 * @param nodeProfileId �������������� ������� ������� � �����
	 * @param nodeLoginId ������������� ������ � �����
	 * @return ClientTransactionId
	 */
	public static String generateAndStoreClientTransactionId(Long csaProfileId, Long nodeProfileId, Long nodeLoginId)
	{
		String id = generateClientTransactionId(csaProfileId, nodeProfileId, nodeLoginId);
		StoreManager.getCurrentStore().save(ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY, id);
		return id;
	}

	/**
	 * ������������� ClientTransactionId
	 * @param csaProfileId ������������� ������� ������� � ���
	 * @param nodeProfileId ������������� ������ ������� � �����
	 * @param nodeLoginId ������������� ������ � �����
	 * @return ClientTransactionId
	 */
	public static String generateClientTransactionId(Long csaProfileId, Long nodeProfileId, Long nodeLoginId)
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return new ClientTransactionCompositeId(config.getGroupId(), config.getNodeNumber(), csaProfileId, nodeProfileId, nodeLoginId).toString();
	}

	/**
	 * ������������� ClientTransactionId
	 * @param csaProfileId ������������� ������� ������� � ���
	 * @return ClientTransactionId
	 */
	public static String generateClientTransactionId(Long csaProfileId)
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		return new ClientTransactionCompositeId(config.getGroupId(), config.getNodeNumber(), csaProfileId, null, null).toString();
	}

	/**
	 * ������������ �� store ClientTransactionId
	 * @return ClientTransactionId
	 */
	public static String restoreClientTransactionId()
	{
		Store store = StoreManager.getCurrentStore();
		String key = ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + FRAUD_MONITORING_CLIENT_TRANSACTION_ID_STORE_KEY;

		Object id = store.restore(key);
		if (id == null)
		{
			throw new IllegalStateException("��� ����������� �������� � ���������� " + ApplicationConfig.getIt().getApplicationInfo().getApplication().name() + " �� ���������� ClientTransactionId");
		}

		store.remove(key);
		return id.toString();
	}
}