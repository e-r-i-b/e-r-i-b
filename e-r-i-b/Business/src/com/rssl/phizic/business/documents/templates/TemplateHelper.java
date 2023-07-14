package com.rssl.phizic.business.documents.templates;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.counter.CounterUtils;
import com.rssl.phizic.utils.counter.NameCounterAction;
import com.rssl.phizic.utils.counter.NamingStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * ������ ������ � ���������
 *
 * @author khudyakov
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateHelper
{
	private static final String RESTRICTED_TB_NUMBER                        = "40";
	private static final int MAX_TEMPLATE_NAME_SIZE                         = 50;

	private static final ServiceProviderService  serviceProviderService  = new ServiceProviderService();

	/**
	 * ����� �� id
	 * @param id �������������
	 * @return ������
	 * @throws BusinessException
	 */
	public static TemplateDocument findById(Long id) throws BusinessException
	{
		return TemplateDocumentService.getInstance().findById(id);
	}

	/**
	 * ����������, �������� �� ������ �������� �� ������� ���� �������
	 * ��� ���������� ��������� �� ������ ������� ����
	 * @param document ������
	 * @return true - ��������
	 */
	public static boolean isByOldCPFLTemplate(BusinessDocument document) throws BusinessException
	{
		if (document == null)
		{
			return false;
		}

		if (!document.isByTemplate())
		{
			return false;
		}

		TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
		if (template == null)
		{
			throw new BusinessException("������ �� ����� ���� null, templateId = " + document.getTemplateId());
		}

		return isOldCPFLTemplate(template);
	}

	/**
	 * ����������, �������� �� ������ ������ ������ ���� ��������
	 * ��� ���������� ��������� �� ������ ������� ����
	 * @param template ������
	 * @return true - ��������
	 */
	public static boolean isOldCPFLTemplate(TemplateDocument template) throws BusinessException
	{
		if (template == null)
		{
			return false;
		}

		try
		{
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(DateHelper.parseDate(ConfigFactory.getConfig(ConfigImpl.class).getOldDocumentDate()));
			//�������� ��������� ������, ���� �� ������ ������, ��� OLD_DOCUMENT_DATE.
			Calendar creationDate = template.getClientCreationDate();
			if (currentDate.compareTo(creationDate) < 0)
			{
				return false;
			}

			//��������� ���������� �������
			List<String> codes = ConfigFactory.getConfig(DocumentConfig.class).getOldDocAdapters();
			if (CollectionUtils.isNotEmpty(codes))
			{
				return codes.contains(template.getBillingCode());
			}

			String[] billingCodes = ConfigFactory.getConfig(ConfigImpl.class).getBillingSystemsAdapter();
			return ArrayUtils.contains(billingCodes, template.getBillingCode());
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� �� �������� �� ������� ������ ����� �� ����� � 40��.
	 *
	 * @param document ��������.
	 * @return �� ������� ��� ���.
	 */
	public static boolean isBy40TBTemplateFromAccount(BusinessDocument document) throws BusinessException
	{
		if (document == null)
		{
			return false;
		}

		if (!document.isByTemplate())
		{
			return false;
		}

		TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
		if (template == null)
		{
			throw new BusinessException("������ �� ����� ���� null, templateId = " + document.getTemplateId());
		}
		return is40TBTemplateFromAccount(template);
	}

	/**
	 * �������� �� �������� �� ������� ������ ����� �� ����� � 40��.
	 *
	 * @param template ������.
	 * @return ������ ��� ���.
	 */
	public static boolean is40TBTemplateFromAccount(TemplateDocument template) throws BusinessException
	{
		if (template == null)
		{
			return false;
		}

		if (AccountPaymentSystemPayment.class != template.getType())
		{
			return false;
		}

		try
		{
			SBRFOfficeCodeAdapter code = new SBRFOfficeCodeAdapter(template.getOffice().getCode());
			return RESTRICTED_TB_NUMBER.equals(code.getRegion());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��������� �������� � ����� �������, ����� ������� ��� (���) ����������.
	 *
	 * @param client �������� �������
	 * @param templateName �������� �������
	 * @param namingStrategy ��������� ���������� ��������� �������
	 * @return templateName ����� �������� �������
	 * @throws BusinessException
	 */
	public static String addCounter(Client client, String templateName, NamingStrategy namingStrategy) throws BusinessException, BusinessLogicException
	{
		String validName = namingStrategy.transform(templateName);
		//�������� ����� ��������, ������� �� ����.
		List<TemplateDocument> templates = TemplateDocumentService.getInstance().getResemblingNames(client, validName);

		String number = CounterUtils.calcNumber(templates, validName, new NameCounterAction(namingStrategy)
		{
			@Override
			public String getName(Object o)
			{
				TemplateDocument templateDocument = (TemplateDocument) o;
				return templateDocument.getTemplateInfo().getName();
			}
		});

		return getValidTemplateName(validName, number) + number;
	}

	/**
	 * �������� �������� �������� ������� (��� �� ����� ����� ��� ������������ � ������� �� ��������� 50 ���������)
	 * @param templateName �������� �������
	 * @param number �����
	 * @return �������� �������� �������
	 */
	private static String getValidTemplateName(String templateName, String number)
	{
		if (templateName.length() + number.length() > MAX_TEMPLATE_NAME_SIZE)
		{
			return templateName.substring(0, MAX_TEMPLATE_NAME_SIZE - number.length());
		}
		return templateName;
	}

	/**
	 * �������� ����� �� �������
	 * @param template ������
	 * @return �����
	 */
	public static Money getExactAmount(TemplateDocument template)
	{
		if (InputSumType.CHARGEOFF == template.getInputSumType())
		{
			return template.getChargeOffAmount();
		}
		if (InputSumType.DESTINATION == template.getInputSumType())
		{
			return template.getDestinationAmount();
		}
		return null;
	}

	/**
	 * ������� �������� �������
	 *
	 * @param client ������
	 * @param templateName ��������
	 * @return �������� �������
	 * @throws BusinessException
	 */
	public static TemplateInfo createTemplateInfo(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		return new TemplateInfoImpl(templateName, true, true, true, true, getOrderInd(client), true, new State(ActivityCode.ACTIVE.name(), ActivityCode.ACTIVE.getDescription()));
	}

	/**
	 * �������� ��������� ���������� ����� �������
	 *
	 * @param client ������
	 * @return ��������� �����
	 * @throws BusinessException
	 */
	public static int getOrderInd(Client client) throws BusinessException, BusinessLogicException
	{
		List<TemplateDocument> templates = TemplateDocumentService.getInstance().getAll(client);
		if (CollectionUtils.isEmpty(templates))
		{
			return 1;
		}

		return getMaxOrderInd(templates) + 1;
	}

	private static int getMaxOrderInd(List<TemplateDocument> templates)
	{
		int max = 1;
		for (TemplateDocument template : templates)
		{
			int current = template.getTemplateInfo().getOrderInd();
			if (max < current)
			{
				max = current;
			}
		}
		return max;
	}

	/**
	 * �������� ���������� ����� �� ���������
	 *
	 * @param template ������
	 * @return ��������� �����
	 * @throws BusinessException
	 */
	public static ServiceProviderBase getTemplateProvider(TemplateDocument template) throws BusinessException
	{
		if (template.getReceiverInternalId() != null)
		{
			return serviceProviderService.findById(template.getReceiverInternalId());
		}
		return null;
	}

	/**
	 * �������� "�������" ���������� ����� �� ���������
	 *
	 * @param template ������
	 * @return ��������� �����
	 * @throws BusinessException
	 */
	public static ServiceProviderShort getTemplateProviderShort(TemplateDocument template) throws BusinessException
	{
		if (template.getReceiverInternalId() != null)
		{
			return serviceProviderService.findShortProviderById(template.getReceiverInternalId());
		}
		return null;
	}

	/**
	 * @param template ������
	 * @return true - �������� � �������� �� ����� ���������
	 */
	public static boolean isTemplateDisallowedFromAccount(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (template == null)
		{
			return false;
		}

		boolean result = false;
		if (FormType.INDIVIDUAL_TRANSFER == template.getFormType() || FormType.INDIVIDUAL_TRANSFER_NEW == template.getFormType())
		{
			result = !DocumentHelper.isExternalPhizAccountPaymentsAllowed();
		}
		else if (FormType.JURIDICAL_TRANSFER == template.getFormType() || FormType.isPaymentSystemPayment(template.getFormType()))
		{
			result = !DocumentHelper.isExternalJurAccountPaymentsAllowed();
		}

		ResourceType resourceType = template.getChargeOffResourceType();
		if (ResourceType.NULL == resourceType)
		{
			return result;
		}

		return ResourceType.ACCOUNT == resourceType && result;
	}

	/**
	 * ��������� �� ������ �� �����
	 * (����������� - ��� ���������� ������������ �������� ��� ���������)
	 *
	 * @param template ������
	 * @return true - ���������
	 */
	public static boolean isFromAccountPaymentAllow(TemplateDocument template)
	{
		if (template == null)
		{
			return true;
		}

		if (template.getType() == null)
		{
			return true;
		}

		return AbstractAccountTransfer.class.isAssignableFrom(template.getType());
	}

	/**
	 * ��������� �� ������ � ����
	 * (����������� - ��� ���������� ������������ �������� ��� ���������)
	 *
	 * @param template ������
	 * @return true - ���������
	 */
	public static boolean isFromCardPaymentAllow(TemplateDocument template)
	{
		if (template == null)
		{
			return true;
		}

		if (template.getType() == null)
		{
			return true;
		}

		return AbstractCardTransfer.class.isAssignableFrom(template.getType());
	}

	/**
	 * ��� ������� ��������� � ���
	 * @param template ������
	 * @return ���
	 */
	public static APITemplateType getAPITemplateType(TemplateDocument template)
	{
		if (template == null)
		{
			return null;
		}

		FormType formType = template.getFormType();
		if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER == formType)
		{
			return APITemplateType.servicesPayments;
		}
		if (FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType || FormType.JURIDICAL_TRANSFER == formType)
		{
			return APITemplateType.jurPayment;
		}
		return APITemplateType.payment;
	}

	/**
	 * ����������� ����� �� ����� ������ � ������ �� ����� �������
	 * @param money - ����� �����������
	 * @param currencyTo ������, � ������� ��������������
	 * @param template - ������ ���������
	 * @return - ����������������� ��������
	 * @throws BusinessException
	 */
	public static Money moneyConvert(Money money, Currency currencyTo, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (money.getCurrency().compare(currencyTo))
			{
				return money;
			}

			CurrencyRateService currencyRateService = GateSingleton.getFactory().service(CurrencyRateService.class);

			//���� ���� �� �� ������� � ��������� �����
			String tarifPlanCodeType = template.getTarifPlanCodeType();
			CurrencyRate rate = currencyRateService.convert(money, currencyTo, template.getOffice(), tarifPlanCodeType);
			if (rate != null)
			{
				return new Money(rate.getToValue(), currencyTo);
			}

			CurrencyRate cbRate = DocumentHelper.getDefaultCurrencyRate(money.getCurrency(), currencyTo, tarifPlanCodeType);
			if (cbRate != null)
			{
				return new Money(CurrencyUtils.getFromCurrencyRate(money.getDecimal(), cbRate).getToValue(), currencyTo);
			}

			throw new BusinessLogicException("�� ������� �������� ����� �����. �������� �������� ����������.");
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
