package com.rssl.phizicgate.manager.services.routable.util;

import com.rssl.phizic.gate.GateConfiguration;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.InputMode;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class GateInfoServiceImpl extends RoutableServiceBase implements GateInfoService
{
	public GateInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public String getUID(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.getUID(office);
	}

	public Boolean isNeedChargeOff(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isNeedChargeOff(office);
	}

	public Boolean isClientImportEnable(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isClientImportEnable(office);
	}

	public Boolean isRegistrationEnable(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isRegistrationEnable(office);
	}

	public Boolean isNeedAgrementCancellation(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isNeedAgrementCancellation(office);
	}

	public Boolean isCurrencyRateAvailable(Office office) throws GateException, GateLogicException
	{
		if (ESBHelper.isESBSupported(office))
			return true;
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isCurrencyRateAvailable(office);
	}

	public Boolean isPaymentCommissionAvailable(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isPaymentCommissionAvailable(office);
	}

	public Boolean isPaymentCommissionAvailable(Billing billing) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(billing).service(GateInfoService.class);
		return delegate.isPaymentCommissionAvailable(billing);
	}

	public Boolean isCalendarAvailable(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isCalendarAvailable(office);
	}

	public InputMode getAccountInputMode(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.getAccountInputMode(office);
	}

	public InputMode getCardInputMode(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.getCardInputMode(office);
	}

	public Boolean isDelayedPaymentNeedSend(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isDelayedPaymentNeedSend(office);
	}

	public Boolean isOfficesHierarchySupported(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isOfficesHierarchySupported(office);
	}

	public Boolean isPaymentsRecallSupported(Office office) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(office).service(GateInfoService.class);
		return delegate.isPaymentsRecallSupported(office);
	}

	public Boolean isRecipientExtedendAttributesAvailable(Billing billing) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(billing).service(GateInfoService.class);
		return delegate.isRecipientExtedendAttributesAvailable(billing);
	}

	public GateConfiguration getConfiguration(Billing billing) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(billing).service(GateInfoService.class);
		return delegate.getConfiguration(billing);
	}

	public Boolean needTwoPhaseTransaction(Billing billing) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(billing).service(GateInfoService.class);
		return delegate.needTwoPhaseTransaction(billing);
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
		GateInfoService delegate = getDelegateFactory(receiverCodePoint).service(GateInfoService.class);
		return delegate.needTwoPhaseTransaction(receiverCodePoint);
	}

	public Boolean isPersonalRecipientAvailable(Billing billing) throws GateException, GateLogicException
	{
		GateInfoService delegate = getDelegateFactory(billing).service(GateInfoService.class);
		return delegate.isPersonalRecipientAvailable(billing);
	}
}