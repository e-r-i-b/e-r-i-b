package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;

/**
 * @author egorova
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class SpecificGateConfigImpl extends SpecificGateConfig
{
	public SpecificGateConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
	}

	public String getCreditsRoutesDictionaryPath()
	{
		return FileHelper.normalizePath(getProperty(CREDITS_ROUTES_DICTIONARY_PATH));
	}

	public String getReceiversRoutesDictionaryPath()
	{
		return FileHelper.normalizePath(getProperty(RECEIVERS_ROUTES_DICTIONARY_PATH));
	}

	public String getUsePaymentOrderForAccountJurTransfer()
	{
		return getProperty(USE_PAYMENT_ORDER_ACCOUNT_JUR_TRANSFER);
	}

	public String getUsePaymentOrderForAccountJurIntrabankTransfer()
	{
		return getProperty(USE_PAYMENT_ORDER_ACCOUNT_JUR_INTRA_BANK_TRANSFER);
	}

	public String getUsePaymentOrderForRUSTaxPayment()
	{
		return getProperty(USE_PAYMENT_ORDER_RUS_TAX_PAYMENT);
	}

	public String getUsePaymentOrderForAccountPaymentSystemPayment()
	{
		return getProperty(USE_PAYMENT_ORDER_ACCOUNT_PAYMENT);
	}

	public Integer getDocumentUpdateWaitingTime()
	{
		String property = getProperty(DOCUMENT_UPDATE_WAITING_TIME);
		return StringHelper.isNotEmpty(property) ? Integer.parseInt(property) : 0;
	}
}
