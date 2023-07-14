package com.rssl.phizic.web.util;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOld;
import com.rssl.phizic.business.dictionaries.payment.services.api.PaymentServiceOldService;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 02.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentServiceUtil
{
	private static final String SERVICE_PARAMETER_PREFIX = "services.payment.";
	private static final PropertyReader propertyRader = ConfigFactory.getReaderByFileName("systemServices.properties");
	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();
	private static final PaymentServiceOldService paymentServiceOldService = new PaymentServiceOldService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * �������� ������ ����������� �����
	 * @param paymentService ������������ ������
	 * @return ������ ����������� �����
	 */
	public static List<PaymentService> getPaymentServiceChildren(PaymentService paymentService)
	{
		try
		{
			return paymentServiceService.getChildren(paymentService);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ����������� �����", e);
			return new ArrayList();
		}
	}
	/**
	 * �������� ������ ����������� �����(� ������������ �� ������ ����������, ���������� ��� API)
	 * @param paymentService ������������ ������
	 * @return ������ ����������� �����
	 */
	public static List<PaymentServiceOld> getPaymentServiceChildrensOld(PaymentServiceOld paymentService)
	{
		try
		{
			return paymentServiceOldService.getChildrens(paymentService);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ����������� �����", e);
			return new ArrayList();
		}
	}

	/**
	 * �������� ������ �� id
	 * @param id ������
	 * @return ������
	 */
	public static PaymentService getPaymentServiceById(Long id)
	{
		try
		{
			return paymentServiceService.findById(id);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �� id", e);
			return null;
		}
	}
	/**
	 * �������� ������ �� id
	 * @param id ������
	 * @return ������
	 */
	public static PaymentServiceOld getPaymentServiceOldById(Long id)
	{
		try
		{
			return paymentServiceOldService.findById(id);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �� id", e);
			return null;
		}
	}

	/**
	 * �������� ������ �� ����������
	 * @param code ��������� ������
	 * @return ������
	 */
	public static PaymentService getSystemPaymentService(String code)
	{
		try
		{
			String service = propertyRader.getProperty(SERVICE_PARAMETER_PREFIX + code);
			if (StringHelper.isEmpty(service))
				return null;

			return paymentServiceService.findBySynchKey(service.trim().split("\\|")[0]);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �� ����������", e);
			return null;
		}
	}

	/**
	 *  ����� ������ �� ����
	 * @param synchKey ��� ������
	 * @return ������
	 */
	public static PaymentService getPaymentServiceBySynchKey(String synchKey)
	{
		try
		{
			return paymentServiceService.findBySynchKey(synchKey);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ �� synchKey", e);
			return null;
		}
	}

	/**
	 * @return ������������� ���������� ����� ������-������
	 */
	public static Long getYandexPaymentId()
	{
		ProvidersConfig providerConfig = ConfigFactory.getConfig(ProvidersConfig.class);
		return providerConfig.getYandexPaymentId();
	}
}
