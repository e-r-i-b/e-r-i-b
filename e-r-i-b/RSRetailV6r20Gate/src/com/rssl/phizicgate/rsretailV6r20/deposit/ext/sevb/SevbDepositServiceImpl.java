package com.rssl.phizicgate.rsretailV6r20.deposit.ext.sevb;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizicgate.rsretailV6r20.deposit.DepositServiceImpl;
import com.rssl.phizicgate.rsretailV6r20.deposit.DepositImpl;

import java.util.List;
import java.util.ArrayList;

/**
 * @author: Pakhomova
 * @created: 28.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class SevbDepositServiceImpl extends DepositServiceImpl
{

	public SevbDepositServiceImpl(GateFactory factory)
    {
        super(factory);
    }

    public List<? extends Deposit> getClientDeposits(final Client client)
    {
		return new ArrayList<DepositImpl>();
    }
}
