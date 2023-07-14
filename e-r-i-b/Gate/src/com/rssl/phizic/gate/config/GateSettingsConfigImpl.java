package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateSettingsConfigImpl extends GateSettingsConfig
{
	private Boolean needChargeOff;
	private Boolean clientImportEnable;
	private Boolean registrationEnable;
	private Boolean agreementSignMandatory;
	private Boolean needAgrementCancellation;
	private Boolean currencyRateAvailable;
	private Boolean paymentCommissionAvailable;
	private Boolean calendarAvailable;
	private InputMode accountInputMode;
	private InputMode cardInputMode;
	private Boolean delayedPaymentNeedSend;
	private Boolean officesHierarchySupported;
	private Boolean paymentsRecallSupported;
	private Boolean recipientExtedendAttributesAvailable;
	private Boolean personalRecipientAvailable;
	private Boolean twoPhaseTransactionSupported;
	private Boolean useDepoCodWS;

	public GateSettingsConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		needChargeOff                           = getBoolProperty(NEED_CHARGE_OFF);
		clientImportEnable                      = getBoolProperty(CLIENT_IMPORT_ENABLE);
		registrationEnable                      = getBoolProperty(REGISTRATION_ENABLE);
		agreementSignMandatory                  = getBoolProperty(AGREMENT_SIGN_MANDATORY);
		needAgrementCancellation                = getBoolProperty(NEED_AGREMENT_CANCELLATION);
		currencyRateAvailable                   = getBoolProperty(CURRENCY_RATE_AVAILABLE);
		paymentCommissionAvailable              = getBoolProperty(PAYMENT_COMMISSION_AVAILABLE);
		calendarAvailable                       = getBoolProperty(CALENDAR_AVAILABLE);
		accountInputMode                        = getEnumProperty(InputMode.class, ACCOUNT_INPUT_MODE);
		cardInputMode                           = getEnumProperty(InputMode.class, CARD_INPUT_MODE);
		delayedPaymentNeedSend                  = getBoolProperty(DELAYED_PAYMENT_NEED_SEND);
		officesHierarchySupported               = getBoolProperty(OFFICES_HIERARCHY_SUPPORTED);
		paymentsRecallSupported                 = getBoolProperty(PAYMENTS_RECALL_SUPPORTED);
		recipientExtedendAttributesAvailable    = getBoolProperty(RECIPIENT_EXTEDEND_ATTRIBUTES_AVAILABLE);
		twoPhaseTransactionSupported            = getBoolProperty(TWO_PHASE_TRANSACTION_SUPPORTED);
		personalRecipientAvailable              = getBoolProperty(PERSONAL_RECIPIENT_AVAILABLE);
		useDepoCodWS                            = getBoolProperty(USE_DEPO_COD_WS);
	}

	public Boolean isNeedChargeOff()
	{
		return needChargeOff;
	}

	public Boolean isClientImportEnable()
	{
		return clientImportEnable;
	}

	public Boolean isRegistrationEnable()
	{
		return registrationEnable;
	}

	public Boolean isAgreementSignMandatory()
	{
		return agreementSignMandatory;
	}

	public Boolean isNeedAgrementCancellation()
	{
		return needAgrementCancellation;
	}

	public Boolean isCurrencyRateAvailable()
	{
		return currencyRateAvailable;
	}

	public Boolean isPaymentCommissionAvailable()
	{
		return paymentCommissionAvailable;
	}

	public Boolean isCalendarAvailable()
	{
		return calendarAvailable;
	}

	public InputMode getAccountInputMode()
	{
		return accountInputMode;
	}

	public InputMode getCardInputMode()
	{
		return cardInputMode;
	}

	public Boolean isDelayedPaymentNeedSend()
	{
		return delayedPaymentNeedSend;
	}

	public Boolean isOfficesHierarchySupported()
	{
		return officesHierarchySupported;
	}

	public Boolean isPaymentsRecallSupported()
	{
		return paymentsRecallSupported;
	}

	public Boolean isRecipientExtedendAttributesAvailable()
	{
		return recipientExtedendAttributesAvailable;
	}

	public Boolean isTwoPhaseTransactionSupported()
	{
		return twoPhaseTransactionSupported;
	}

	public Boolean isPersonalRecipientAvailable()
	{
		return personalRecipientAvailable;
	}

	public Boolean useDepoCodWS()
	{
		return useDepoCodWS;
	}

	protected <E extends Enum<E>> E getEnumProperty(Class<E> enumCalss, String key)
	{
		String  value = getProperty(key);
		return StringHelper.isNotEmpty(value)? Enum.valueOf(enumCalss, value): null;
	}
}