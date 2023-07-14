package com.rssl.phizicgate.manager.services.persistent.util;

import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author egorova
 * @ created 02.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateInfoServiceImpl extends PersistentServiceBase<GateInfoService> implements GateInfoService
{
	public GateInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public String getUID(Office office) throws GateException, GateLogicException
	{
		return delegate.getUID(removeRouteInfo(office));
	}

	public Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException
	{
		return delegate.isNeedChargeOff(removeRouteInfo(office));
	}

	public Boolean isClientImportEnable(Office office) throws GateException, GateLogicException
	{
		return delegate.isClientImportEnable(removeRouteInfo(office));
	}

	public Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException
	{
		return delegate.isRegistrationEnable(removeRouteInfo(office));
	}

	public Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException
	{
		return delegate.isNeedAgrementCancellation(removeRouteInfo(office));
	}

	public Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException
	{
		return delegate.isCurrencyRateAvailable(removeRouteInfo(office));
	}

	public Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException
	{
		return delegate.isPaymentCommissionAvailable(removeRouteInfo(office));
	}

	public Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException
	{
		return delegate.isPaymentCommissionAvailable(removeRouteInfo(billing));
	}

	public Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException
	{
		return delegate.isCalendarAvailable(removeRouteInfo(office));
	}

	public InputMode getAccountInputMode(Office office) throws GateException, GateLogicException
	{
		return delegate.getAccountInputMode(removeRouteInfo(office));
	}

	public InputMode getCardInputMode(Office office) throws GateException, GateLogicException
	{
		return delegate.getCardInputMode(removeRouteInfo(office));
	}

	public Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException
	{
		return delegate.isDelayedPaymentNeedSend(removeRouteInfo(office));
	}

	public Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException
	{
		return delegate.isOfficesHierarchySupported(removeRouteInfo(office));
	}

	public Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException
	{
		return delegate.isPaymentsRecallSupported(removeRouteInfo(office));
	}

	public Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException
	{
		return delegate.isRecipientExtedendAttributesAvailable(removeRouteInfo(billing));
	}

	public GateConfiguration getConfiguration(Billing billing) throws GateException, GateLogicException
	{
		return delegate.getConfiguration(removeRouteInfo(billing));
	}

	public Boolean needTwoPhaseTransaction(Billing billing) throws GateException, GateLogicException
	{
		return delegate.needTwoPhaseTransaction(removeRouteInfo(billing));
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
		return delegate.needTwoPhaseTransaction(removeRouteInfo(receiverCodePoint));
	}

	public Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException
	{
		return delegate.isPersonalRecipientAvailable(billing);
	}
}
