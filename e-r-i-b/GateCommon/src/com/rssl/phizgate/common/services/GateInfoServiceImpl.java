package com.rssl.phizgate.common.services;

import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.utils.*;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.config.GateSettingsConfig;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author egorova
 * @ created 03.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInfoServiceImpl extends AbstractService implements GateInfoService
{
    public GateInfoServiceImpl(GateFactory factory)
    {
        super(factory);
    }

	protected GateSettingsConfig getGateSettingsConfig()
	{
		return ConfigFactory.getConfig(GateSettingsConfig.class);
	}

	public String getUID(Office office_1) throws GateException, GateLogicException
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		return config.getApplicationPrefix();
	}

	public Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isCalendarAvailable();
	}

	public Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isCurrencyRateAvailable();
	}

	public Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isPaymentCommissionAvailable();
	}

	public Boolean isAgreementSignMandatory(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isAgreementSignMandatory();
	}

	public Boolean isClientImportEnable(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isClientImportEnable();
	}

	public Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isNeedAgrementCancellation();
	}

	public Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isNeedChargeOff();
	}

	public Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isRegistrationEnable();
	}

	public InputMode getAccountInputMode(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().getAccountInputMode();
	}

	public InputMode getCardInputMode(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().getCardInputMode();
	}

	public Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isDelayedPaymentNeedSend();
	}

	public Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isOfficesHierarchySupported();
	}

	public Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isPaymentsRecallSupported();
	}

	public Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isPaymentCommissionAvailable();
	}

	public Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isRecipientExtedendAttributesAvailable();
	}

	public GateConfiguration getConfiguration(Billing billing) throws GateException, GateLogicException
	{
		GateConnectionConfig gateSettingsConfig = ConfigFactory.getConfig(GateConnectionConfig.class);
		GateConfigurationImpl config = new GateConfigurationImpl();
		config.setUserName(gateSettingsConfig.getUserName());
		config.setConnectionTimeout(gateSettingsConfig.getConnectionTimeout());
		config.setConnectMode(gateSettingsConfig.getConnectMode());
		return config;
	}

	public Boolean needTwoPhaseTransaction(Billing billing) throws GateException, GateLogicException
	{
		return !getGateSettingsConfig().isTwoPhaseTransactionSupported();
	}

	/**
	 * Возвращает признак необходимости вести двухфазную транзакцию между биллнгом и АБС
	 * @param receiverCodePoint идентификатор получателя в биллинге
	 * @return true - нужно, false - не нужно: билллинг сам производит списание средств.
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Boolean needTwoPhaseTransaction(String receiverCodePoint) throws GateException, GateLogicException
	{
		return !getGateSettingsConfig().isTwoPhaseTransactionSupported();
	}

	public Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException
	{
		return getGateSettingsConfig().isPersonalRecipientAvailable();
	}
}