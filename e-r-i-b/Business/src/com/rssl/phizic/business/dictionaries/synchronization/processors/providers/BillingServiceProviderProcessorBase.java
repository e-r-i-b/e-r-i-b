package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.BillingProcessor;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.PaymentServiceProcessor;

/**
 * @author akrenev
 * @ created 29.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый процессор записей биллинговых поставщиков
 */

public abstract class BillingServiceProviderProcessorBase<P extends BillingServiceProviderBase> extends ProviderProcessorBase<P>
{
	private Billing getBilling(Billing source) throws BusinessException
	{
		return getLocalVersionByGlobal(source, BillingProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	private PaymentService getPaymentService(PaymentService source) throws BusinessException
	{
		return getLocalVersionByGlobal(source, PaymentServiceProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	@Override
	protected void update(P source, P destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setCodeService(source.getCodeService());
		destination.setGround(source.isGround());
		destination.setAccountType(source.getAccountType());
		destination.setBilling(getBilling(source.getBilling()));
		destination.setPaymentService(getPaymentService(source.getPaymentService()));
		destination.setAttrDelimiter(source.getAttrDelimiter());
		destination.setAttrValuesDelimiter(source.getAttrValuesDelimiter());
		destination.setNameService(source.getNameService());
		destination.setCodeRecipientSBOL(source.getCodeRecipientSBOL());
		destination.setAlias(source.getAlias());
		destination.setLegalName(source.getLegalName());
		destination.setNameOnBill(source.getNameOnBill());
		destination.setFederal(source.isFederal());
	}
}
