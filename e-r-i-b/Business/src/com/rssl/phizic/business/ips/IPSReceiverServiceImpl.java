package com.rssl.phizic.business.ips;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSReceiverService;

import java.util.List;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */
class IPSReceiverServiceImpl extends AbstractService implements IPSReceiverService
{
	IPSReceiverServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<IPSCardOperationClaim> receiveCardOperationClaimResult(GroupResult<IPSCardOperationClaim, List<IPSCardOperation>> result) throws GateException
	{
		CardOperationReceiver receiver = new CardOperationReceiver(getFactory());
		return receiver.receive(result);
	}

	public void setTimeoutStatusClaims(List<IPSCardOperationClaim> claims) throws GateException
	{
		CardOperationReceiver receiver = new CardOperationReceiver(getFactory());
		receiver.setTimeoutStatusClaims(claims);
	}
}
