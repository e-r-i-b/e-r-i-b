package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.payments.CommissionOptionsImpl;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.CommissionOptions;
import com.rssl.phizic.gate.documents.CommissionTarget;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyPayment extends AbstractAccountsTransfer implements SWIFTPayment
{
	public static final String RECEIVER_BANK_SWIFT_ATTRIBUTE_NAME = "receiver-bank-SWIFT";
	public static final String RECEIVER_COR_ACCOUNT_ATTRIBUTE_NAME = "receiver-bank-corr-account";
	public static final String PAYMENT_CONDITIONS_ATTRIBUTE_NAME = "payment-conditions";
	public static final String RECEIVER_COUNTRY_CODE_ATTRIBUTE_NAME = "receiver-country-code";
	public static final String COMMISSION_ACCOUNT = "commission-account";
	public static final String COMMISSION_TARGET = "commission";

	/**
	 * Фактичский тип документа
	 *
	 * @return фактичский тип документа
	 */
	public Class<? extends GateDocument> getType()
	{
		return SWIFTPayment.class;
	}

	/**
	 * Код страны получателя. По ISO 3166-1 (Alpha 3).
	 * Domain: CountryCode
	 *
	 * @return код страны получателя
	 */
	public String getReceiverCountryCode()
	{
		return getNullSaveAttributeStringValue(RECEIVER_COUNTRY_CODE_ATTRIBUTE_NAME);
	}

	/**
	 * SWIFT код банка получателя
	 * Domain: SWIFT
	 *
	 * @return код swift
	 */
	public String getReceiverSWIFT()
	{
		return getNullSaveAttributeStringValue(RECEIVER_BANK_SWIFT_ATTRIBUTE_NAME);
	}

	/**
	 * Наименование банка получателя
	 * Domain: Text
	 *
	 * @return наименование банка
	 */
	public String getReceiverBankName()
	{
		return getNullSaveAttributeStringValue(RurPayment.RECEIVER_BANK_NAME_ATTRIBUTE_NAME);
	}

	public SWIFTPaymentConditions getConditions()
	{
		String conditions = getNullSaveAttributeStringValue(PAYMENT_CONDITIONS_ATTRIBUTE_NAME);
		if (conditions == null || conditions.trim().length() == 0)
		{
			return null;
		}
		return SWIFTPaymentConditions.valueOf(conditions);
	}

    /**
    * Параметры взымания комиссии.
    */
    public CommissionOptions getCommissionOptions()
    {
	    String account = getNullSaveAttributeStringValue(COMMISSION_ACCOUNT);
		String target = getNullSaveAttributeStringValue(COMMISSION_TARGET);

	    CommissionOptionsImpl commissionOptions = new CommissionOptionsImpl();

		if (account != null && account.trim().length() != 0)
			commissionOptions.setAccount(account);

	    if (target != null && target.trim().length() != 0)
		    commissionOptions.setTarget(CommissionTarget.valueOf(target));
	    
		return commissionOptions;
    }

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}
}
