package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для работы с автоплатежами
 */
public abstract class AutoPaymentBase extends RurPayment implements AutoPayment
{
	public  static final String AUTO_PAYMENT_REQUISITE_ATTRIBUTE_NAME           = "requisite";
	private static final String AUTO_PAYMENT_CODE_SERVICE_ATTRIBUTE_NAME        = "auto-payment-code-service";
	private static final String AUTO_PAYMENT_NAME_SERVICE_ATTRIBUTE_NAME        = "auto-payment-name-service";
	private static final String AUTO_PAYMENT_FLOOR_LIMIT_ATTRIBUTE_NAME         = "auto-payment-decimal-floor-limit";
	private static final String AUTO_PAYMENT_FLOOR_CURR_ATTRIBUTE_NAME          = "auto-payment-currency-floor-limit";
	private static final String AUTO_PAYMENT_FRIENDLY_NAME_ATTRIBUTE_NAME       = "auto-payment-friendly-name";
	private static final String AUTO_PAYMENT_REPORT_STATUS_ATRIBUTE_NAME        = "auto-payment-report-status";
	private static final String AUTO_PAYMENT_DATE_CREATED_ATTRIBUTE_NAME        = "auto-payment-date-created";
	private static final String AUTO_PAYMENT_NUMBER_ATTRIBUTE_NAME              = "auto-payment-number";
	private static final String AUTO_PAYMENT_REQUISITE_NAME_ATTRIBUTE_NAME      = "requisite-name";
	private static final String AUTO_PAYMENT_LINK_ID_ATTRIBUTE_NAME             = "auto-payment-link-id";
	private static final String CARD_NAME_ATTRIBUTE_NAME                        = "card-name";

	public static final String RECEIVER_INTERNAL_ID = PaymentFieldKeys.PROVIDER_KEY;
	public static final String PROVIDER_EXTERNAL_ID = PaymentFieldKeys.PROVIDER_EXTERNAL_KEY;

	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private String receiverPointCode;
	private String multiBlockReceiverPointCode;
	private Long receiverInternalId;

	public String getCodeService()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_CODE_SERVICE_ATTRIBUTE_NAME);
	}

	public void setCodeService(String codeService)
	{
		setNullSaveAttributeStringValue(AUTO_PAYMENT_CODE_SERVICE_ATTRIBUTE_NAME, codeService);
	}

	/**
	 * @return Номер карты
	 */
	public String getCardName()
	{
		return getNullSaveAttributeStringValue(CARD_NAME_ATTRIBUTE_NAME);
	}

	/**
	 * @return Название услуги
	 */
	public String getNameService()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_NAME_SERVICE_ATTRIBUTE_NAME);
	}

	public void setNameService(String nameService)
	{
		setNullSaveAttributeStringValue(AUTO_PAYMENT_NAME_SERVICE_ATTRIBUTE_NAME, nameService);
	}

	public Money getFloorLimit()
	{
		String currencyText = getNullSaveAttributeStringValue(AUTO_PAYMENT_FLOOR_CURR_ATTRIBUTE_NAME);
		String amountText = getNullSaveAttributeStringValue(AUTO_PAYMENT_FLOOR_LIMIT_ATTRIBUTE_NAME);
		return createMoney(amountText, currencyText);
	}

	public void setFloorLimit(Money floorLimit)
	{
		if (floorLimit != null)
		{
			setNullSaveAttributeStringValue(AUTO_PAYMENT_FLOOR_CURR_ATTRIBUTE_NAME, floorLimit.getCurrency().getCode());
			setNullSaveAttributeDecimalValue(AUTO_PAYMENT_FLOOR_LIMIT_ATTRIBUTE_NAME, floorLimit.getDecimal());
		}
	}

	public String getFriendlyName()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_FRIENDLY_NAME_ATTRIBUTE_NAME);
	}

	public void setFriendlyName(String name)
	{
		setNullSaveAttributeStringValue(AUTO_PAYMENT_FRIENDLY_NAME_ATTRIBUTE_NAME, name);
	}

	public AutoPaymentStatus getReportStatus()
	{
		String type = getNullSaveAttributeStringValue(AUTO_PAYMENT_REPORT_STATUS_ATRIBUTE_NAME);
		if (StringHelper.isEmpty(type))
			return null;
		return AutoPaymentStatus.fromValue(Long.parseLong(type));
	}

	public void setReportStatus(AutoPaymentStatus status)
	{
		if (status == null)
		{
			setNullSaveAttributeStringValue(AUTO_PAYMENT_REPORT_STATUS_ATRIBUTE_NAME, null);
		}
		else
		{
			setNullSaveAttributeStringValue(AUTO_PAYMENT_REPORT_STATUS_ATRIBUTE_NAME, status.getValue().toString());
		}
	}

	public void setReportStatus(String status)
	{
		setReportStatus(status == null ? null : AutoPaymentStatus.valueOf(status));
	}

	public Calendar getDateAccepted()
	{
		return getNullSaveAttributeCalendarValue(AUTO_PAYMENT_DATE_CREATED_ATTRIBUTE_NAME);
	}

	public void setDateAccepted(Calendar date)
	{
		setNullSaveAttributeCalendarValue(AUTO_PAYMENT_DATE_CREATED_ATTRIBUTE_NAME, date);
	}

	public String getNumber()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_NUMBER_ATTRIBUTE_NAME);
	}

	public void setReceiverPointCode(String externalId)
	{
		this.receiverPointCode = externalId;
	}

	public String getReceiverPointCode()
	{
		return receiverPointCode;
	}

	public String getMultiBlockReceiverPointCode()
	{
		return multiBlockReceiverPointCode;
	}

	public void setMultiBlockReceiverPointCode(String multiBlockReceiverPointCode)
	{
		this.multiBlockReceiverPointCode = multiBlockReceiverPointCode;
	}

	public String getRequisite()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_REQUISITE_ATTRIBUTE_NAME);
	}

	public void setRequisite(String value)
	{
		setNullSaveAttributeStringValue(AUTO_PAYMENT_REQUISITE_ATTRIBUTE_NAME, value);
	}

	private String getRequisiteName()
	{
		return getNullSaveAttributeStringValue(AUTO_PAYMENT_REQUISITE_NAME_ATTRIBUTE_NAME);
	}

	private void setRequisiteName(String value)
	{
		setNullSaveAttributeStringValue(AUTO_PAYMENT_REQUISITE_NAME_ATTRIBUTE_NAME, value);
	}

	protected void storeAutoPaymentData() throws DocumentException, DocumentLogicException
	{
		try
		{
			AutoPaymentLink link = getAutoPaymentLink();
			CardLink cardLink = link.getCardLink();
			AutoPayment payment = link.getValue();

			BillingServiceProvider provider = link.getServiceProvider();
			if(provider != null)
			{
				setReceiverPointCode((String) provider.getSynchKey());
				setReceiverInternalId(provider.getId());
			}

			setReceiverName(payment.getReceiverName());
			setCodeService(payment.getCodeService());

			ExecutionEventType eventType = payment.getExecutionEventType();
			setExecutionEventType(eventType);
			if (ExecutionEventType.REDUSE_OF_BALANCE == eventType)
			{
				setFloorLimit(payment.getFloorLimit());
				setClientTotalAmountLimit(payment.getTotalAmountLimit());
				setTotalAmountPeriod(payment.getTotalAmountPeriod());
			}
			else if(ExecutionEventType.BY_INVOICE == eventType)
			{
				setFloorLimit(payment.getFloorLimit());
			}
			else if (ExecutionEventType.ONCE_IN_MONTH == eventType || ExecutionEventType.ONCE_IN_QUARTER == eventType || ExecutionEventType.ONCE_IN_YEAR == eventType)
			{
				setStartDate(payment.getStartDate());
				setPayDay(payment.getStartDate());
			}

			setFriendlyName(payment.getFriendlyName());
			setDateAccepted(payment.getDateAccepted());
			setReportStatus(payment.getReportStatus());
			setRequisite(payment.getRequisite());
			setRequisiteName(link.getRequisiteName());

			setNullSaveAttributeStringValue(CARD_NAME_ATTRIBUTE_NAME, cardLink.getName());

			setChargeOffAmount(payment.getAmount());
			setChargeOffAccount(payment.getCardNumber());
			setChargeOffCurrency(cardLink.getCurrency());
			setChargeOffResourceType(cardLink.getClass().getName());
			setLongOffer(true);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Вернуть ссылку на автоплатеж
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк списания удалён либо не задан
	 */
	public AutoPaymentLink getAutoPaymentLink() throws DocumentException
	{
		Long linkId = getAutoPaymentLinkId();
		if (linkId == null)
		   return null;
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return externalResourceService.findInSystemLinkById(documentOwner.getLogin(), AutoPaymentLink.class, linkId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Возвращает идентификатор линка автоплатежа.
	 * @return
	 */
	public Long getAutoPaymentLinkId()
	{
		String linkId = getNullSaveAttributeStringValue(AUTO_PAYMENT_LINK_ID_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(linkId))
			return null;
		return Long.parseLong(linkId);
	}

	 protected int getPaymentDay() throws DocumentLogicException
     {
          int payDay = getPayDay().byteValue();
          if (payDay > 31)
               throw new DocumentLogicException("Вы неправильно указали дату платежа. Пожалуйста, введите корректное число.");

          return payDay;
     }

	/**
	 * получить запись о поставщике из БД.
	 * @return запись о поставщике или null в случае оплаты в адрес внешнего получателя.
	 */
	public ServiceProviderBase getServiceProvider() throws DocumentException
	{
		Long receiverId = getReceiverInternalId();
		if (receiverId == null)
			throw new DocumentException("Не найден поставщик услуг с идентификатором " + receiverId);
		
		try
		{
			ServiceProviderBase serviceProvider = serviceProviderService.findById(receiverId);
			if (serviceProvider == null)
			{
				throw new DocumentException("Не найден поставщик услуг с идентификатором " + receiverId);
			}
			return serviceProvider;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();

		appendNullSaveString(root, PROVIDER_EXTERNAL_ID, getReceiverPointCode());
		appendNullSaveString(root, RECEIVER_INTERNAL_ID, StringHelper.getNullIfNull(getReceiverInternalId()));

		return document;
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		//Кривое наследование от RurPayment. На самом деле заявки работат с автоплатежами.
		Set<ExternalResourceLink> links = new HashSet<ExternalResourceLink>();
		AutoPaymentLink link = getAutoPaymentLink();
		if (link != null)
		{
			links.add(link);
		}
		return links;
	}
}
