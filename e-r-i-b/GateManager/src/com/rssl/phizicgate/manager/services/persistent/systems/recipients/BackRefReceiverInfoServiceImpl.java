package com.rssl.phizicgate.manager.services.persistent.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefReceiverInfoServiceImpl extends PersistentServiceBase<BackRefReceiverInfoService> implements BackRefReceiverInfoService
{
	public BackRefReceiverInfoServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws GateException, GateLogicException
	{
		GateInfoService infoService = getFactory().service(GateInfoService.class);
		return delegate.getRecipientInfo(pointCode + "|" + infoService.getUID(null), serviceCode);
	}
}
