package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.payment.billing.EditServicePaymentOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Операция для создания шинного автоплатежа(автоподписки) с первого шага
 * @author niculichev
 * @ created 13.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class CreateESBAutoPayOperation extends EditServicePaymentOperation
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private List<ServiceProviderShort> billingServiceProviders;
	private String eventSource; // источник события (employee, client)

	public void initialize(DocumentSource source, Long receiverId, String eventSource) throws BusinessException, BusinessLogicException
	{
		super.initialize(source, receiverId);
		this.eventSource = eventSource;
		JurPayment doc = (JurPayment)source.getDocument();
		//если ПУ не задан значит оплата по свободным реквизитам
		if (receiverId != null || doc.getReceiverInternalId() != null)
		{
			if (!(getProvider() instanceof BillingServiceProvider))
				throw new BusinessException("Не верный тип поставщика " + getProvider().getClass());
			billingServiceProviders = serviceProviderService.getProviderAllServicesSupportAutoPay((BillingServiceProvider) getProvider());
		}
		else
			billingServiceProviders = new ArrayList<ServiceProviderShort>();
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

		if (PersonHelper.getLastClientLogin().getLastUserVisitingMode() == UserVisitingMode.EMPLOYEE_INPUT_BY_CARD)
			result.addAll(personData.getCards(new LoginedCardFilter(getProvider().isCreditCardSupported())));
		else
		{
			CardFilter filter = getProvider().isCreditCardSupported() ?
				new CardFilterConjunction(new ActiveNotVirtualCardsFilter(), new CardOwnFilter()) : new ActiveNotVirtualNotCreditOwnCardFilter();
			result.addAll(personData.getCards(filter));
		}

		return result;
	}


	public List<ServiceProviderShort> getProviderAllServices() throws BusinessException
	{
		return Collections.unmodifiableList(billingServiceProviders);
	}

	public List<FieldDescription> getProviderAllServicesFields() throws BusinessException
	{
		/*
		if(CollectionUtils.isEmpty(billingServiceProviders))
			return Collections.emptyList();

		List<Long> providerIds = new ArrayList<Long>();
		for(BillingServiceProvider provider : billingServiceProviders)
			providerIds.add(provider.getId());

		return serviceProviderService.getKeyFieldDescriptionsByServiceId(providerIds);
		*/
		//toDo временное решение, на форме должны быть поля поставщика только скрытые.BUG040998
		return super.getProviderAllServicesFields();
	}

	protected String getSourceEvent()
	{
		return eventSource;
	}


	protected boolean validateProvider(BillingServiceProviderBase providerBase)
	{
		BillingServiceProvider provider  = (BillingServiceProvider) providerBase;
		return (provider.getState() == ServiceProviderState.ACTIVE || provider.getState() == ServiceProviderState.MIGRATION) && AutoPaymentHelper.checkAutoPaymentSupport(provider);
	}
}
