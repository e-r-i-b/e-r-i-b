package com.rssl.phizic.operations.externalPayment;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.configuration.gate.MonitoringGateConfigBusinessService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.payments.FNSFormatHelper;
import com.rssl.phizic.business.ext.sbrf.payments.UECFormatHelper;
import com.rssl.phizic.business.persons.ClientDataImpl;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.shop.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.monitoring.InactiveTypeHelper;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.List;

import static com.rssl.phizic.business.ext.sbrf.payments.PaymentsSystemNameConstants.SYSTEM_NAME_FNS;
import static com.rssl.phizic.business.ext.sbrf.payments.PaymentsSystemNameConstants.SYSTEM_NAME_UEC;
import static com.rssl.phizic.einvoicing.EInvoicingConstants.*;

/**
 * User: Moshenko
 * Date: 30.05.12
 * Time: 17:34
 * �������� ��� ��������� ������ ������� ������� � �����
 */

/**
 * ���������� ������ ������� ������� � ����� (���/OZON/���).
 * ��������� ��������� ������ ������/������
 * � ��������� �� � ���� � ���� com.rssl.phizic.business.internetshop.Order.
 *
 * ��������� �������� �� �������� ������� � �������� "�� ����" � �����������
 * ����� ������� ��������� ��������������, ������� ����������� �� ������ ��������������
 * � ���������� � ��������� ��������������.
 *
 * ���������� ����� ����� ��������, �� ������� �������� ������������ ����� ����� �������� ��������������.
 */

public class ProcessExternalPaymentOperation extends OperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ServiceProviderService providerService	= new ServiceProviderService();
	private static final PersonService personService = new PersonService();
	private static final MonitoringGateConfigBusinessService gateServicesService = new MonitoringGateConfigBusinessService();

	/**
	 * ���� � �������� �������������� ������� (������ ���)
	 */
	private static final String EDIT_PAYMENT_URL = "/external/payments/servicesPayments/edit.do";

	/**
	 * ���� � �������� �������������� ������� �� �������� ������ (������ ���)
	 */
	private static final String EDIT_INTERNET_SHOP_PAYMENT_URL = "/external/payments/servicesPayments/internetShop/edit.do";

	/**
	 * ���� � �������� ������������� �������
	 */
	private static final String CONFIRM_PAYMENT_URL = "/external/payments/confirm.do";

	/**
	 * ���� � �������� ��������� �������
	 */
	private static final String VIEW_PAYMENT_URL = "/external/payments/view.do";

	/**
	 * ���� � �������� ������ ������� �� ������ ���������
	 */
	private static final String SELECT_PAYMENT_URL = "/external/fns/list.do";

	private static final String ORDER_READY = "� ������� ������ ��� �������� ��������.";

	private static final String FNS_FORMAT_ERROR = "����� �������� ��������� ��� ����� ������������ ������:";
	private static final String FNS_ECP_ERROR = "�������� ��������� ��� �� ������������� ����� ���.�������� ��������� (XML):";
	private static final String FNS_PROVIDER_ERROR = "��������� ��� �� ����� � ������ ������� �������";
	private static final String FNS_PACKAGE_EMPTY = "����� �������� ��������� ��� ����";

	private static final String UEC_PROVIDER_ERROR = "��������� ��� �� ����� � ������ ������� �������";
	private static final String UEC_FORMAT_ERROR = "����� �������� ��������� ��� ����� ������������ ������:";
	private static final String UEC_ECP_ERROR = "�������� ��������� ��� �� ������������� ����� ���. �������� ��������� (XML):";
	private static final String UEC_PACKAGE_EMPTY = "����� �������� ��������� ��� ����";

	private static final String CLIENT_MESSAGE = " � ������ ������ ������ ������� �� ���� ��������. �������� ��������� �������� �� ������.";
	private static final String PROVIDER_ERROR = "� ��������� ����� ������ ������ ����������.";

	//������, ����� ������� ���� ������ ������� ��������
	private static final String ONE_PHASE_PAYMENT_SERVICE = "phiz-gate-iqwave.OnePhasePayment";

	/**
	 * ��������� ������ ��������� ���������, ���������� �� �������� ��������
	 * @param context - �������� ��������������
	 */
	public void processWebShopOrders(AuthenticationContext context) throws BusinessException, BusinessLogicException
	{
		// <reqId> - ��� ������������� (UUID) ������, ������� ��� �.�. � ��� � ����
		String reqId = context.getAuthenticationParameter(WEBSHOP_REQ_ID);
		Person person = getAuthenticatedPerson(context);

		log.info("������ ��������� ��������� ��������� ReqId=" + reqId + ". " +
				"LOGIN_ID: " + person.getLogin().getId());

		// 1. �������� �����, ��������������� <reqId>
		ShopOrder order = ShopHelper.get().getShopOrder(reqId);
		if (order == null) {
			log.error("�� ������ ������� ����� ReqId=" + reqId);
			throw new BusinessLogicException(CLIENT_MESSAGE);
		}

		// 2.��������� �� �������� �� ������ ����� ��� � ������� �������,
		if (order.getProfile() != null && !ShopHelper.get().isSameClient(order.getProfile(), context))
		{
			log.error("�������� ��������� ��� ������� �������� ������ ������ ReqId=" + reqId);
			throw new BusinessLogicException(CLIENT_MESSAGE);
		}

        // 3. ��������� ���������� (������� � ����������) ����������
		InternetShopsServiceProvider provider = ShopHelper.get().getRecipientBySystemName(order.getReceiverCode());
		if (provider == null)
		{
			log.error("��������� �� ����� � ������ ��������� ��������� ReqId=" + reqId);
			throw new BusinessLogicException(PROVIDER_ERROR);
		}

		// 3.1 ��������� ������ �������, ����� ������� ���� ������ ������
		MonitoringServiceGateConfig serviceGateConfig = gateServicesService.findConfig(ONE_PHASE_PAYMENT_SERVICE);
		if(serviceGateConfig != null && serviceGateConfig.getState() != MonitoringGateState.NORMAL)
		{
			MonitoringServiceGateStateConfig stateConfig = serviceGateConfig.getStateConfig(serviceGateConfig.getState());
			throw new BusinessLogicException(InactiveTypeHelper.getClientMessage(stateConfig));
		}

		// 3.2 ��������� ������ ������� ������� (IQWave), ����� ������� ���� ������ ������
		String adapterUUID = provider.getBilling().getAdapterUUID();
		if (ServiceProviderHelper.getIQWaveUUID().equals(adapterUUID) )
		{
			try
			{
				ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
				externalSystemGateService.getTechnoBreakToDateWithAllowPayments(adapterUUID);
			}
			catch (InactiveExternalSystemException e)
			{
				throw new BusinessLogicException(e);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
		}

		// 4.���� � ������ ��� �������� ��������
		BusinessDocument document = DocumentHelper.getPaymentByOrder(order.getUuid());
		if (document !=  null)
		{
			String stateCode = document.getState().getCode();
			log.info(ORDER_READY + reqId);
			if (stateCode.equals("SAVED"))
				context.setStartJobPagePath(formatConfirmPaymentURL(document));
			else if (stateCode.equals("DISPATCHED")||stateCode.equals("EXECUTED")||stateCode.equals("TICKETS_WAITING")||stateCode.equals("ERROR")
					||stateCode.equals("REFUSED")||stateCode.equals("WAIT_CONFIRM"))
				context.setStartJobPagePath(formatViewPaymentURL(document));
			else if (stateCode.equals("INITIAL"))
				context.setStartJobPagePath(formatEditExistsPaymentURL(document, EDIT_INTERNET_SHOP_PAYMENT_URL));
		}
		else
		{
			if (order.getState() == OrderState.CREATED)
			{
				// 5. ��������� ����� ������ �������: ������-��������
				ShopHelper.get().linkPerson(reqId, new ClientDataImpl(context));
				log.info("�������� ��������� ReqId=" + reqId + " ������ � ������. " +
						"������� �������: " + order.getReceiverCode() + ". " +
						"���������� � ������ ��������� ���������.");
			}

			// 6. ���������� � �������� ��������� ����� �������������� �������� ���������� �������
			context.setStartJobPagePath(formatEditNewPaymentURL(order.getUuid(), EDIT_INTERNET_SHOP_PAYMENT_URL));
		}
	}

	/**
	 * ��������� ������� �������� ���������, ��������� �� ���
	 * @param context - �������� ��������������
	 */
	public void processFNSPayOrders(AuthenticationContext context) throws BusinessException, BusinessLogicException
	{
		// <payPackXml> - ��� xml � ������� �� ������������ ������
		String payPackXml = context.getAuthenticationParameter(FNS_PAY_INFO);
		Person person = getAuthenticatedPerson(context);

		writeToLog(FNS_PAY_INFO, payPackXml);      // ����� � ��� ��������� ��������� xml

		// 1. ������ � ���������� payPack DOM
		Document payPackDocument;
		try
		{
			payPackDocument = FNSFormatHelper.parsePayOrdersXml(payPackXml);
		}
		catch (ValidateException ex)
		{
			log.error(FNS_FORMAT_ERROR + ex.getMessage(), ex);
			throw new BusinessLogicException(CLIENT_MESSAGE, ex);
		}

		// 2. ��������� ��� ��� ������� ��������� � ������
		List<String> payOrderTags = FNSFormatHelper.expandPayOrderPack(payPackXml);
		for (String payOrderTag : payOrderTags) {
			if (!FNSFormatHelper.checkPayOrdersPackDigitalSignature(payOrderTag)) {
				log.error(FNS_ECP_ERROR + payOrderTag);
				throw new BusinessLogicException(CLIENT_MESSAGE);
			}
		}

		// 3. ��������� ���������� (����������) ���-����������
		InternetShopsServiceProvider provider = getServiceProvider(SYSTEM_NAME_FNS);
		if (provider == null)
		{
			log.error(FNS_PROVIDER_ERROR);
			throw new BusinessLogicException(PROVIDER_ERROR);
		}

		// 3.1 ��������� ������ �������, ����� ������� ���� ������ ������
		MonitoringServiceGateConfig serviceGateConfig = gateServicesService.findConfig(ONE_PHASE_PAYMENT_SERVICE);
		if(serviceGateConfig != null && serviceGateConfig.getState() != MonitoringGateState.NORMAL)
		{
			MonitoringServiceGateStateConfig stateConfig = serviceGateConfig.getStateConfig(serviceGateConfig.getState());
			throw new BusinessLogicException(InactiveTypeHelper.getClientMessage(stateConfig));
		}

		// 4. �������� �� payPackXml ������ Order-�� � ��������� ���
		List<FNS> orderInfos = FNSFormatHelper.buildOrderInfos(person, payPackDocument);
		if (CollectionUtils.isEmpty(orderInfos)) {
			log.error(FNS_PACKAGE_EMPTY);
			throw new BusinessLogicException(CLIENT_MESSAGE);
		}
		for (FNS order : orderInfos)
			ExternalPaymentService.get().registerGeneralInfo(order);

		log.info("� ���� ��������� " + orderInfos.size() + " �������� ��������� ���. " +
				"���������� � ��������� ������ �������� ���������");

		// 5.A ������ ���� =>
		// ���������� � �������� ��������� ����� �������������� �������� ���������� �������
		if (orderInfos.size() == 1) {
			OrderInfo orderInfo = orderInfos.iterator().next();
			context.setStartJobPagePath(formatEditNewPaymentURL(orderInfo.getOrder().getUuid(), EDIT_PAYMENT_URL));
		}

		// 5.B ����� ����� =>
		// ���������� � �������� ��������� ����� �������������� �������� ������ ������ ��� ������
		else
		{
			context.setStartJobPagePath(SELECT_PAYMENT_URL);
			context.getAuthenticationParameters().clear();
		}
	}

	/**
	 * ��������� ������� �������� ���������, ��������� �� ���
	 * @param context - �������� ��������������
	 */
	public void processUECPayOrders(AuthenticationContext context) throws BusinessException, BusinessLogicException
	{
		// <payPackXml> - ��� xml � ������� �� ������������ ������
		String payPackXml = context.getAuthenticationParameter(UEC_PAY_INFO);
		Person person = getAuthenticatedPerson(context);

		writeToLog(UEC_PAY_INFO, payPackXml);      // ����� � ��� ��������� ��������� xml

		// 1. ������ � ���������� payPack DOM
		Document payPackDocument;
		try
		{
			payPackDocument = UECFormatHelper.parsePayOrdersXml(payPackXml);
		}
		catch (ValidateException ex)
		{
			log.error(UEC_FORMAT_ERROR + ex.getMessage(), ex);
			throw new BusinessLogicException(CLIENT_MESSAGE, ex);
		}

		// 2. ��������� ��� ��� ������� ��������� � ������
		String payOrderTag = UECFormatHelper.expandPayOrderPack(payPackXml);
		if (!UECFormatHelper.checkPayOrdersPackDigitalSignature(payOrderTag))
		{
			log.error(UEC_ECP_ERROR + payOrderTag);
			throw new BusinessLogicException(CLIENT_MESSAGE);
		}

		// 3. ��������� ���������� (����������) ���-����������
		InternetShopsServiceProvider provider = getServiceProvider(SYSTEM_NAME_UEC);
		if (provider.getState() != ServiceProviderState.ACTIVE)
		{
			log.error(UEC_PROVIDER_ERROR);
			throw new BusinessLogicException(PROVIDER_ERROR);
		}

		// 4. ��������� ������ �������, ����� ������� ���� ������ ������
		MonitoringServiceGateConfig serviceGateConfig = gateServicesService.findConfig(ONE_PHASE_PAYMENT_SERVICE);
		if(serviceGateConfig != null && serviceGateConfig.getState() != MonitoringGateState.NORMAL)
		{
			MonitoringServiceGateStateConfig stateConfig = serviceGateConfig.getStateConfig(serviceGateConfig.getState());
			throw new BusinessLogicException(InactiveTypeHelper.getClientMessage(stateConfig));
		}

		// 5. �������� �� payPackXml Order � ��������� ���
		Order order = UECFormatHelper.buildOrderInfo(person, payPackDocument);
		if (order == null)
		{
			log.error(UEC_PACKAGE_EMPTY);
			throw new BusinessLogicException(CLIENT_MESSAGE);
		}

		// 6. �������� url, � �������� �������� ������ ������������ � ������� (������� �� ����, ��� �� �� ����� ��� ��������������� ������ ����� ��� ���)
		context.setStartJobPagePath(getUECStartURL(order));
	}

	private InternetShopsServiceProvider getServiceProvider(String systemName) throws BusinessException, BusinessLogicException
	{
		InternetShopsServiceProvider provider = providerService.getRecipientActivityBySystemName(systemName);

		// ���������� ���� ���, ���� �� ��������
		if (provider == null)
			log.error("�� ������ �������� ��������� systemName=" + systemName);

		return provider;
	}

	private String getUECStartURL(Order order) throws BusinessException, BusinessLogicException
	{
		// ���������, �� �������� �� ��� ���� ����� � �������
		Order existingOrder = ExternalPaymentService.get().getOrder(order.getExtendedId(), SYSTEM_NAME_UEC);
		if (existingOrder != null)
		{
			//���������, �� �������� �� � ������ ��������
			BusinessDocument document = DocumentHelper.getPaymentByOrder(existingOrder.getUuid());
			// ���� ��������� ��� ���, ���������� ������������ �� �������� ���������� ������� ��� ���
			if (document == null)
				return formatEditNewPaymentURL(existingOrder.getUuid(), EDIT_PAYMENT_URL);

			else
			{
				// ���� �������� ��� ����, ��������� ��� ������ � � ����������� �� ������� ���������� ������������ �� �������� ����������, ������������� ���� ��������� �������
				String stateCode = document.getState().getCode();
				log.info(ORDER_READY);

				if (stateCode.equals("DRAFT") || stateCode.equals("INITIAL"))
					return formatEditExistsPaymentURL(document, EDIT_PAYMENT_URL);

				else if (stateCode.equals("SAVED") || stateCode.equals("WAIT_CONFIRM") || stateCode.equals("OFFLINE_SAVED"))
					return formatConfirmPaymentURL(document);

				else if (stateCode.equals("DISPATCHED") || stateCode.equals("DELAYED_DISPATCH") || stateCode.equals("EXECUTED") || stateCode.equals("TICKETS_WAITING") || stateCode.equals("ERROR")
						||stateCode.equals("REFUSED"))
					return formatViewPaymentURL(document);
			}
		}

		else
		{
			// ���� ����� ��� �� ��� �������� � �������, ���������� ������������ �� �������� ���������� ������� ��� ��� 
			ExternalPaymentService.get().registerUECOrder(order);

			log.info("� ���� ��������� ��������� ��������� ���. " +
					"���������� � ��������� ������ ��������� ���������");

			return formatEditNewPaymentURL(order.getUuid(), EDIT_PAYMENT_URL);
		}

		return null;
	}

	private void writeToLog(String requestType, String request) throws BusinessException
	{
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		logEntry.setMessageRequest(request);
		logEntry.setMessageRequestId("0");
		logEntry.setApplication(LogThreadContext.getApplication());
		logEntry.setMessageType(requestType);
		logEntry.setSystem(com.rssl.phizic.logging.messaging.System.shop);
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ������ � ��� ��������� �� ���", e);
		}
	}

	private Person getAuthenticatedPerson(AuthenticationContext context) throws BusinessException
	{
		CommonLogin login = context.getLogin();
		if (login == null)
			throw new IllegalStateException("������� �������������� ��� �� ��������");

		Person person = personService.findByLogin((Login) context.getLogin());
		if (person == null)
			throw new BusinessException("�� ������ ������ � LOGIN_ID="+login.getId());
		return person;
	}

	private String formatEditNewPaymentURL(String uuid, String editPaymentUrlKey)
	{
		UrlBuilder urlBuilder = new UrlBuilder(editPaymentUrlKey);
		urlBuilder.addParameter(PaymentFieldKeys.ORDER_ID_KEY, uuid);
		return urlBuilder.toString();
	}

	private String formatEditExistsPaymentURL(BusinessDocument payment, String editPaymentUrlKey)
	{
		UrlBuilder urlBuilder = new UrlBuilder(editPaymentUrlKey);
		urlBuilder.addParameter(PaymentFieldKeys.PAYMENT_ID_KEY, String.valueOf(payment.getId()));
		return urlBuilder.toString();
	}

	private String formatConfirmPaymentURL(BusinessDocument payment)
	{
		UrlBuilder urlBuilder = new UrlBuilder(CONFIRM_PAYMENT_URL);
		urlBuilder.addParameter(PaymentFieldKeys.PAYMENT_ID_KEY, String.valueOf(payment.getId()));
		return urlBuilder.toString();
	}

	private String formatViewPaymentURL(BusinessDocument payment)
	{
		UrlBuilder urlBuilder = new UrlBuilder(VIEW_PAYMENT_URL);
		urlBuilder.addParameter(PaymentFieldKeys.PAYMENT_ID_KEY, String.valueOf(payment.getId()));
		return urlBuilder.toString();
	}
}
