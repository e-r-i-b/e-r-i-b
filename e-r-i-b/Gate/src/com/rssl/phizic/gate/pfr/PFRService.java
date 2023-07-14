package com.rssl.phizic.gate.pfr;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.claims.pfr.PFRStatementClaim;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.rmi.RemoteException;

/**
 * @author Erkin
 * @ created 04.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для работы с ПФР (Пенсионным Фондом РФ)
 */
public interface PFRService extends Service
{
	/**
	 * Получить ранее запрошенную выписку
	 * @param claim - заявка на выписку
	 * @return выписка
	 *  null => есть проблемы с получением, см. статус isReady() для <claim>
	 * @throws GateLogicException
	 * @throws GateException
	 * @throws RemoteException если обрыв связи 
	 */
	String getStatement(PFRStatementClaim claim) throws GateLogicException, GateException, RemoteException;
}
