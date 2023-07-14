package com.rssl.phizicgate.manager.services.routable.util;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * Селектор для сервиса получения настроек шлюза(часть настроек, если билинг шинный должна браться из шины)
 * @author niculichev
 * @ created 02.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class GateInfoServiceSelector extends AbstractService implements GateInfoService
{
	private GateInfoService esb;
	private GateInfoService routable;

	public GateInfoServiceSelector(GateFactory factory)
	{
		super(factory);

		try
		{
			String esbClass = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(GateInfoService.class.getName() + ESB_DELEGATE_KEY);
			Class esbService = ClassHelper.loadClass(esbClass);
			esb = (GateInfoService) esbService.getConstructor(GateFactory.class).newInstance(factory);

			String routableClass = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(GateInfoService.class.getName() + ROUTABLE_DELEGATE_KEY);
			Class routableService = ClassHelper.loadClass(routableClass);
			routable = (GateInfoService) routableService.getConstructor(GateFactory.class).newInstance(factory);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getUID(Office office) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBSupported(office))
			return esb.getUID(office);

		return routable.getUID(office);
	}

	public Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException
	{
		return routable.isNeedChargeOff(office);
	}

	public Boolean isClientImportEnable(Office office) throws GateException, GateLogicException
	{
		return routable.isClientImportEnable(office);
	}

	public Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException
	{
		return routable.isRegistrationEnable(office);
	}

	public Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException
	{
		return routable.isNeedAgrementCancellation(office);
	}

	public Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException
	{
		return routable.isCurrencyRateAvailable(office);
	}

	public Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException
	{
		return routable.isPaymentCommissionAvailable(office);
	}

	public Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBBilling(billing))
			return esb.isPaymentCommissionAvailable(billing);

		return routable.isPaymentCommissionAvailable(billing);
	}

	public Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException
	{
		return routable.isCalendarAvailable(office);
	}

	public InputMode getAccountInputMode(Office office) throws GateException, GateLogicException
	{
		return routable.getAccountInputMode(office);
	}

	public InputMode getCardInputMode(Office office) throws GateException, GateLogicException
	{
		return routable.getCardInputMode(office);
	}

	public Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBSupported(office))
			return esb.isDelayedPaymentNeedSend(office);

		return routable.isDelayedPaymentNeedSend(office);
	}

	public Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException
	{
		return routable.isOfficesHierarchySupported(office);
	}

	public Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException
	{
		return routable.isPaymentsRecallSupported(office);
	}

	public Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBBilling(billing))
			return esb.isRecipientExtedendAttributesAvailable(billing);

		return routable.isRecipientExtedendAttributesAvailable(billing);
	}

	public GateConfiguration getConfiguration(Billing billing) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBBilling(billing))
			return esb.getConfiguration(billing);

		return routable.getConfiguration(billing);
	}

	public Boolean needTwoPhaseTransaction(Billing billing) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBBilling(billing))
			return esb.needTwoPhaseTransaction(billing);

		return routable.needTwoPhaseTransaction(billing);
	}

	public Boolean needTwoPhaseTransaction(String receiverCodePoint) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBAdapter(IDHelper.restoreRouteInfo(receiverCodePoint)))
			return esb.needTwoPhaseTransaction(receiverCodePoint);

		return routable.needTwoPhaseTransaction(receiverCodePoint);
	}

	public Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException
	{
		if(ESBHelper.isESBBilling(billing))
			return esb.isPersonalRecipientAvailable(billing);

		return routable.isPersonalRecipientAvailable(billing);
	}
}
