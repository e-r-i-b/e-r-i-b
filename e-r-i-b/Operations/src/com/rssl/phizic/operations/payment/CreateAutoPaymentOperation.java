package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.resources.external.ActiveRurNotVirtualCardFilter;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author niculichev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentOperation extends EditDocumentOperationBase
{
	private static final String NOT_DEFINE_RECEPIENT_MESSAGE = "Не задан поставщик услуг для создания автоплатежа";
	private static final String NOT_FOUND_RECEPIENT_MESSAGE = "Не найден поставщик услуг для создания автоплатежа с id=%d";
	private static final String NOT_BILLING_RECEPIENT_MESSAGE = "Поставщик услуг для создания автоплатежа с id=%d не является биллинговым";
	private static final String NOT_SUPPORT_AUTOPAYMENT_MESSAGE = "Поставщик услуг для создания автоплатежа с id=%d не поддерживает автоплатеж";
	private static final String KEYFIELD_ERROR_MESSAGE = "У поставщика услуг c id=%d, для которого создается автоплатеж, должно быть одно ключевое поле";
	private static final String NOT_ACTIVE_PROVIDER_MESSAGE = "Поставщик услуг c id=%d, для которого создается автоплатеж, не активный или не позволяет осуществлять платежи и операции";

	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private BillingServiceProvider provider;
	private FieldDescription requisite;

	public void initialize(Long receiverId) throws BusinessException, BusinessLogicException
	{
		if(receiverId == null)
			throw new BusinessException(NOT_DEFINE_RECEPIENT_MESSAGE);

		ServiceProviderBase serviceProvider = serviceProviderService.findById(receiverId);
		if(serviceProvider == null)
			throw new BusinessException(String.format(NOT_FOUND_RECEPIENT_MESSAGE, receiverId));

		if(!(serviceProvider instanceof BillingServiceProvider))
			throw new BusinessException(String.format(NOT_BILLING_RECEPIENT_MESSAGE, receiverId));

		provider = (BillingServiceProvider) serviceProvider;

		if((provider.getState() != ServiceProviderState.ACTIVE && provider.getState() != ServiceProviderState.MIGRATION) )
			throw new BusinessException(String.format(NOT_ACTIVE_PROVIDER_MESSAGE, receiverId));

		if(!AutoPaymentHelper.checkAutoPaymentSupport(provider))
			throw new BusinessException(String.format(NOT_SUPPORT_AUTOPAYMENT_MESSAGE, receiverId));

		List<FieldDescription> fields = (List<FieldDescription>) serviceProviderService.getRecipientKeyFields(provider);
		if(fields == null || fields.size() != 1)
			throw new BusinessException(String.format(KEYFIELD_ERROR_MESSAGE, receiverId));

		setProviderName(provider.getName());
		requisite = fields.get(0);

	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException, BusinessLogicException
	{
		List<PaymentAbilityERL> result = new ArrayList<PaymentAbilityERL>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		result.addAll(personData.getCards(new ActiveRurNotVirtualCardFilter()));
		return result;
	}

	public String getServiceName()
	{
		return provider.getNameService();
	}

	public FieldDescription getRequisite()
	{
		return requisite;
	}
}
