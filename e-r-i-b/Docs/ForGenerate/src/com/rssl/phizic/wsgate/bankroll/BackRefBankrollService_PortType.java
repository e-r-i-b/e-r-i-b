package com.rssl.phizic.wsgate.bankroll;

import com.rssl.phizic.wsgate.bankroll.types.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefBankrollService_PortType extends Remote
{
    String findAccountExternalId(Long loginId, String accountNumber) throws RemoteException;
	String findAccountBusinessOwner(Account account) throws RemoteException;
}
