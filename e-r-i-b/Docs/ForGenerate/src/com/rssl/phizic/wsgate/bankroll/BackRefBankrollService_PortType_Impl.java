package com.rssl.phizic.wsgate.bankroll;

import com.rssl.phizic.wsgate.bankroll.types.Account;

import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankrollService_PortType_Impl implements BackRefBankrollService_PortType
{
	public String findAccountExternalId(Long loginId, String accountNumber) throws RemoteException
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public String findAccountBusinessOwner(Account account) throws RemoteException
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
