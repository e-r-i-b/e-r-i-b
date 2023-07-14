package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityService;
import com.rssl.phizic.business.basket.config.ServiceCategory;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.EditInvoiceSubscriptionClaim;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 15.06.14
 * @ $Author$
 * @ $Revision$
 *
 * —оздание за€вки на операцию над услугой
 */
public class NewInvoiceSubscriptionClaimSource extends NewDocumentSource
{
	private static final String DEFAULT_GROUP_NAME = "ƒругие услуги";

	private static AccountingEntityService accountingEntityService = new AccountingEntityService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	/**
	 * ctor
	 * @param subscription услуга
	 * @param formName наименование формы за€вки
	 * @param channelType канал создани€
	 * @param sourceType источник создани€
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewInvoiceSubscriptionClaimSource(InvoiceSubscription subscription, String formName, CreationType channelType, CreationSourceType sourceType) throws BusinessException, BusinessLogicException
	{
		if (subscription == null)
		{
			throw new IllegalArgumentException("”слуга не может быть null");
		}
		if (StringHelper.isEmpty(formName))
		{
			throw new IllegalArgumentException("Ќаименование формы за€вки не может быть null");
		}

		document = createClaim(subscription, formName);
		metadata = MetadataCache.getExtendedMetadata(document);

		initDocument(channelType, sourceType);
		// недостающие данные
		JurPayment payment = (JurPayment) document;
		payment.setOffice(payment.getDepartment());
	}

	/**
	 * ctor дл€ клиентского создани€ из канала веб
	 * @param subscription услуга
	 * @param formName наименование формы за€вки
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public NewInvoiceSubscriptionClaimSource(InvoiceSubscription subscription, String formName) throws BusinessException, BusinessLogicException
	{
		this(subscription, formName, CreationType.internet, CreationSourceType.copy);
	}

	private BusinessDocument createClaim(InvoiceSubscription subscription, String formName) throws BusinessException, BusinessLogicException
	{
		Metadata basicMetadata = MetadataCache.getBasicMetadata(formName);
		FieldValuesSource source = createValueSource(subscription, formName);
		JurPayment payment = (JurPayment) documentService.createDocument(basicMetadata, source, null);

		updatePayment(payment, subscription);
		return payment;
	}

	private FieldValuesSource createValueSource(InvoiceSubscription subscription, String formName) throws BusinessException
	{
		Map<String, String> fieldsMap = new HashMap<String, String>();

		fieldsMap.put("nameService", subscription.getNameService());
		fieldsMap.put("codeService", subscription.getCodeService());
		fieldsMap.put("subscriptionName", subscription.getName());
		fieldsMap.put("billingCode", subscription.getBillingCode());
		fieldsMap.put("subscriptionId", subscription.getId().toString());
		fieldsMap.put("autoSubNumber", subscription.getAutoPayId());
		fieldsMap.put("groupName", getGroupName(subscription.getAccountingEntityId()));
		fieldsMap.put("invoiceAccountName", getAccountName(subscription.getCodeService()));
		fieldsMap.put("invoiceInfo", BasketHelper.createInvoiceInfo(subscription));


		if (FormConstants.EDIT_INVOICE_SUBSCRIPTION_CLAIM.equals(formName))
		{
			fillEditClaim(fieldsMap, subscription);
		}

		fieldsMap.putAll(getCardFields(subscription));
		return new MapValuesSource(fieldsMap);
	}

	private void fillEditClaim (Map<String, String> fieldsMap, InvoiceSubscription subscription) throws BusinessException
	{
		BillingServiceProvider provider = getServiceProvider(subscription.getRecipientId());
		fieldsMap.put("receiverName", provider.getName());
		fieldsMap.put("receiverId", provider.getSynchKey().toString());
		fieldsMap.put("bankDetails", Boolean.toString(provider.isBankDetails()));
		fieldsMap.put("receiverBankName", provider.getBankName());
		fieldsMap.put("phoneNumber", provider.getPhoneNumber());

		fieldsMap.put("recipient", subscription.getRecipientId().toString());
		fieldsMap.put("groupService", AutoPaymentHelper.getGroupServiceByProvider(subscription.getRecipientId()));
		fieldsMap.put("autoSubSumType", SumType.BY_BILLING.name());

		fieldsMap.put("receiverINN", subscription.getRecInn());
		fieldsMap.put("receiverKPP", subscription.getRecKpp());
		fieldsMap.put("receiverAccount", subscription.getRecAccount());
		fieldsMap.put("receiverBIC", subscription.getRecBic());
		fieldsMap.put("receiverCorAccount", subscription.getRecCorAccount());
		fieldsMap.put("receiverOfficeRegionCode", subscription.getRecTb());
		fieldsMap.put("isAutoSubExecuteNow", "true");
	}

	protected BillingServiceProvider getServiceProvider(Long ricipientId) throws BusinessException
	{
		ServiceProviderBase providerBase = serviceProviderService.findById(ricipientId);
		if(providerBase == null)
		{
			throw new BusinessException("Ќе найден поставщик с id = " + ricipientId);
		}
		BillingServiceProvider provider = (BillingServiceProvider) providerBase;
		return provider;
	}

	private String getGroupName(Long id) throws BusinessException
	{
		if (id == null)
		{
			return DEFAULT_GROUP_NAME;
		}

		String groupName = accountingEntityService.getNameById(id);

		return StringHelper.isEmpty(groupName) ? DEFAULT_GROUP_NAME : groupName;
	}

	private String getAccountName(String codeService)
	{
		ServiceCategory serviceCategory = BasketHelper.getServiceCategoryByCode(codeService);

		return serviceCategory == null ? "" : serviceCategory.getAccountName();
	}

	private Map<String, String> getCardFields(InvoiceSubscription subscription) throws BusinessException
	{
		Map<String, String> values = new HashMap<String, String>();
		CardLink cardLink = getFromResource(subscription.getLoginId(), subscription.getChargeOffCard());

		values.put("fromResource", cardLink.getCode());
		values.put("fromAccountSelect", cardLink.getNumber());
		values.put("fromAccountName", cardLink.getName());
		values.put("fromResourceType", cardLink.getClass().getName());
		values.put("fromAccountType", cardLink.getValue().getDescription());
		values.put("fromResourceCurrency", cardLink.getCurrency().getCode());
		values.put("fromResourceRest", cardLink.getRest().getDecimal().toString());

		return values;
	}

	private void updatePayment(JurPayment payment, InvoiceSubscription subscription) throws BusinessException
	{
		if (payment instanceof EditInvoiceSubscriptionClaim)
		{
			fillEditClaimFields((EditInvoiceSubscriptionClaim) payment, subscription);
		}

		try
		{
			payment.setReceiverName(subscription.getRecName());
			payment.setReceiverPointCode(subscription.getCodeRecipientBs());
			payment.setExtendedFields(subscription.getRequisitesAsList());
			payment.setState(new State("INITIAL")); // дл€ метадаталоадера
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	private void fillEditClaimFields(EditInvoiceSubscriptionClaim payment, InvoiceSubscription subscription) throws BusinessException
	{
		payment.setOldSubscriptionName(subscription.getName());
		payment.setAccountingEntityId(subscription.getAccountingEntityId());
		payment.setOldAccountingEntityId(subscription.getAccountingEntityId());
		payment.setOldFromResource(subscription.getChargeOffCard());
		Calendar chooseDate = subscription.getPayDate();
		payment.setDayPay(chooseDate);
		payment.setOldDayPay(chooseDate);
		payment.setExecutionEventType(subscription.getExecutionEventType());
		payment.setOldEventType(subscription.getExecutionEventType());
		payment.setChargeOffResourceType(ResourceType.CARD);
		payment.setLongOffer(true);
	}
}
