package com.rssl.phizic.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbPaymentType;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasField;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * –еализаци€ платежной задачи "ќплата услуг"
 * @author Rtischeva
 * @created 03.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ServicePaymentTaskImpl extends PaymentTaskBase implements ServicePaymentTask
{
	private List<String> requisites; //реквизиты

	private transient ServiceProviderSmsAlias serviceProviderSmsAlias; //смс-алиас поставщика услуг

	private static final ServiceProviderSmsAliasService smsAliasService = new ServiceProviderSmsAliasService();

	@Override
	protected String getFormName()
	{
		return FormConstants.SERVICE_PAYMENT_FORM;
	}

	@Override
	protected FieldValuesSource createRequestFieldValuesSource()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(PaymentFieldKeys.PROVIDER_KEY, String.valueOf(serviceProviderSmsAlias.getServiceProvider().getId()));

		Map<String, String> paymentRequisites = getRequisitesMap();
		if (paymentRequisites != null)
			map.putAll(paymentRequisites);

		return new MapValuesSource(map);
	}

	@Override
	protected EditDocumentOperation createEditOperation()
	{
		if (existingDocument != null)
			return super.createEditOperation();
		else
		{
			try
			{
				String serviceKey = documentSource.getMetadata().getName();
				EditServicePaymentOperation operation = createOperation(EditServicePaymentOperation.class, serviceKey);
				operation.initialize(documentSource, serviceProviderSmsAlias.getServiceProvider().getId());
				return operation;
			}
			catch (TemporalBusinessException e)
			{
				String exceptionMessage = "ѕо техническим причинам операци€ временно недоступна. ѕовторите попытку позже";
				throw new UserErrorException(new TextMessage(exceptionMessage), e);
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}
			catch (BusinessLogicException e)
			{
				throw new UserErrorException(e);
			}
		}
	}

	protected BusinessDocument findIncompletePayment()
	{
		try
		{
			Long paymentId = ((ErmbProfileImpl) getPerson().getErmbProfile()).getIncompleteSmsPayment();
			if (paymentId != null)
			{
				BusinessDocumentService businessDocumentService = new BusinessDocumentService();
				BusinessDocument document = businessDocumentService.findById(paymentId);
				if (document instanceof AbstractBillingPayment)
				{
					AbstractBillingPayment payment = (AbstractBillingPayment) document;
					Long receiverInternalId = payment.getReceiverInternalId();

					boolean isIncomplete = "DRAFT".equals(document.getState().getCode());
					boolean isSmsChannel = document.getCreationType() == CreationType.sms;
					boolean isCurrentProvider = receiverInternalId != null && receiverInternalId.equals(serviceProviderSmsAlias.getServiceProvider().getId());
					if (isIncomplete && isSmsChannel && isCurrentProvider)
					{
						return document;
					}
				}
			}

			return null;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private Map<String, String> getRequisitesMap()
	{
		if (existingDocument == null)
			 return getNewDocumentRequisitesMap();
		else
			return getExistingDocumentRequisitesMap();
	}

	private Map<String, String> getNewDocumentRequisitesMap()
	{
		Map<String, String> requisitesMap = new HashMap<String, String>();

		List<ServiceProviderSmsAliasField> smsAliasFields;
		try
		{
			smsAliasFields = smsAliasService.findAllSortedFieldsByProviderSmsAlias(serviceProviderSmsAlias);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}

		Iterator<String> iterator = null;
		if (requisites != null)
			iterator = requisites.iterator();

		List<Field> extendedFields = new ArrayList<Field>();
		List<String> requiredFields = new ArrayList<String>();
		boolean isError = false;

		String sum = null;
		for (ServiceProviderSmsAliasField smsAliasField : smsAliasFields)
		{
			Field field = smsAliasField.getField();
			extendedFields.add(field);
			if (smsAliasField.isEditable() && (smsAliasField.getValue() == null || fieldIsZeroSum(smsAliasField.getField())) && field.isRequired())
			{
				//добавл€ем название пол€ в список названий полей, которые нужно заполнить
				requiredFields.add(field.getName());

				//если не хватает реквизитов, выставл€ем флаг, что есть ошибка
				if (iterator == null || !iterator.hasNext())
				{
					isError = true;
				}
				else
				{
					String value = iterator.next();
					if (field.getBusinessSubType() == BusinessFieldSubType.phone)
						value = PhoneNumberFormat.SIMPLE_NUMBER.translate(value);
					requisitesMap.put(field.getExternalId(), value);
					if (field.isMainSum())
						sum = value;
				}
			}
			else if (smsAliasField.getField().isMainSum())
				sum = smsAliasField.getValue();
		}
		//TODO: переделать сей костыль. »сполнитель  ћошенко ј.ћ
		if (StringUtils.contains(sum,','))
			throw new UserErrorException(messageBuilder. buildPaymentCommandIncorrectAmountMessage());

		//если была ошибка при выставлении реквизитов - сообщаем об этом клиенту
		if (isError)
		{
			TextMessage message = messageBuilder.buildNotSufficientBillingPaymentRequisitesMessage(serviceProviderSmsAlias.getName(), requiredFields, extendedFields);
			throw new UserErrorException(message);
		}

		PersonBankrollManager bankrollManager = getPersonBankrollManager();

		BankrollProductLink cardLink = null;
		//если осталс€ реквизит, значит это алиас карты. ≈сли реквизита нет - значит используем приоритетную карту.
		if (iterator != null && iterator.hasNext())
		{
			String fromResourceAlias = iterator.next();
			//если остались реквизиты, значит клиент ввел что-то лишнее. выбрасываем исключение с названи€ми полей, которые нужно ввести
			if (iterator.hasNext())
				throw new UserErrorException(messageBuilder.buildNotSufficientBillingPaymentRequisitesMessage(serviceProviderSmsAlias.getName(), requiredFields, extendedFields));
			//noinspection unchecked
			cardLink = bankrollManager.findProductBySmsAlias(fromResourceAlias, CardLink.class);
		}
		else
		{
			cardLink = getPriorityCardLink(bankrollManager, sum);
		}
		checkCardLink(cardLink);

		requisitesMap.put(PaymentFieldKeys.FROM_RESOURCE_KEY, cardLink.getCode());
		requisitesMap.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
		requisitesMap.put("fromResourceCurrency", "RUB");

		return requisitesMap;
	}

	private CardLink getPriorityCardLink(PersonBankrollManager bankrollManager, String amountString)
	{
		BigDecimal amountValue = StringUtils.isNotEmpty(amountString) ? new BigDecimal(amountString) : BigDecimal.ZERO;
		Money amount = new Money(amountValue, bankrollManager.getRURCurrency());
		try
		{
			return ErmbHelper.getPriorityCardLink(amount);
		}
		catch (BusinessException e)
		{
			TextMessage message = messageBuilder.buildPaymentCardNotFoundAmountMessage(getProviderName(), amount);
			throw new UserErrorException(message, e);
		}
		catch (BusinessLogicException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void checkCardLink(BankrollProductLink cardLink)
	{
		if (cardLink == null)
			throw new UserErrorException(messageBuilder.buildPaymentCardNotFoundMessage());
		if (!cardLink.getShowInSms())
			throw new UserErrorException(messageBuilder.buildPaymentCardWrongMessage());
	}

	private Map<String, String> getExistingDocumentRequisitesMap()
	{
		Map<String, String> requisitesMap = new HashMap<String, String>();

		try
		{
			if (existingDocument instanceof AbstractPaymentSystemPayment)
			{
				AbstractPaymentSystemPayment paymentSystemPayment = (AbstractPaymentSystemPayment) existingDocument;
				List<Field> extendedFields = paymentSystemPayment.getExtendedFields();

				Iterator<String> iterator = null;
				if (requisites != null)
					iterator = requisites.iterator();

				List<String> requiredFields = new ArrayList<String>();
				boolean isError = false;

				String sum = null;
				for (Field extendedField : extendedFields)
				{
					if ((StringHelper.isEmpty((String) extendedField.getValue()) || fieldIsZeroSum(extendedField)) && extendedField.isEditable() && extendedField.isVisible() && extendedField.isRequired())
					{
	                    //добавл€ем название пол€ в список названий полей, которые нужно заполнить
	                    requiredFields.add(extendedField.getName());

	                    //если не хватает реквизитов, выставл€ем флаг, что есть ошибка
	                    if (iterator == null || !iterator.hasNext())
	                    {
	                        isError = true;
	                    }
						else
	                    {
		                    String value = iterator.next();
		                    if (extendedField.getBusinessSubType() == BusinessFieldSubType.phone)
			                    value = PhoneNumberFormat.SIMPLE_NUMBER.translate(value);
		                    requisitesMap.put(extendedField.getExternalId(), value);
		                    if (extendedField.isMainSum())
			                    sum = value;
	                    }
					}
				}

				//если остались реквизиты (значит клиент ввел что-то лишнее) или есть ошибка (клиент ввел не все реквизиты), отправл€ем ему сообщение
				if (iterator != null && iterator.hasNext() || isError)
					throw new UserErrorException(messageBuilder.buildNotSufficientBillingPaymentRequisitesMessage(serviceProviderSmsAlias.getName(), requiredFields, extendedFields));

				if (existingDocument instanceof RurPayment)
				{
					RurPayment document = (RurPayment) existingDocument;
					CardLink link = (CardLink) document.getChargeOffResourceLink();

					//≈сли из биллинга пришла сумма платежа, нужно перепроверить карту списани€ из документа. Ѕаланс старой карты может быть меньше суммы.
					if (StringUtils.isNotEmpty(sum))
					{
						Money amount = new Money(new BigDecimal(sum), getPersonBankrollManager().getRURCurrency());
						if (!checkChargeOfProductAmount(link, amount))
						{
							CardLink priorityCardLink = getPriorityCardLink(getPersonBankrollManager(), sum);
							checkCardLink(priorityCardLink);
							link = priorityCardLink;
						}
					}

					requisitesMap.put(PaymentFieldKeys.FROM_RESOURCE_KEY, link.getCode());
					requisitesMap.put(PaymentFieldKeys.FROM_RESOURCE_TYPE, CardLink.class.getName());
					requisitesMap.put("fromResourceCurrency", "RUB");
				}
			}
		}
		catch (DocumentException e)
		{
	        throw new InternalErrorException(e);
		}
		return requisitesMap;
	}

	@Override
	protected boolean needSendMBOperCode(BusinessDocument document)
	{
		if (!ConfigFactory.getConfig(ErmbConfig.class).needSendPaymentSmsNotification())
			return false;
		try
		{
			if (DocumentHelper.isIQWaveDocument(document))
				return true;
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	protected void setExtendedDocumentFields(BusinessDocument document)
	{
		super.setExtendedDocumentFields(document);
		document.setReceiverSmsAlias(serviceProviderSmsAlias.getName());
	}

	public void setRequisites(List<String> requisites)
	{
		this.requisites = requisites;
	}

	public void setServiceProviderSmsAlias(ServiceProviderSmsAlias serviceProviderSmsAlias)
	{
		this.serviceProviderSmsAlias = serviceProviderSmsAlias;
	}

	public String getProviderName()
	{
		return serviceProviderSmsAlias.getServiceProvider().getName();
	}

	@Override
	public ErmbPaymentType getPaymentType()
	{
		return ErmbPaymentType.SERVICE_PAYMENT;
	}

	@Override
	protected boolean needCheckCardBeforeConfirm(BusinessDocument document)
	{
		return true;
	}

	private boolean fieldIsZeroSum(Field field)
	{
		if (!field.isMainSum())
			return false;

		String value = (String) field.getValue();
		if (StringHelper.isEmpty(value))
			return false;

		if (BigDecimal.ZERO.compareTo(new BigDecimal(value)) == 0)
			return true;

		return false;
	}
}
