package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author osminin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 * ѕолучение наименовани€ получател€
 */
public interface ReceiverNameService extends Service 
{
	/**
	 * Ќаименование получател€
	 * @param account номер счета получател€ платежа
	 * @param bic бик получател€ платежа
	 * @param tb  номер “Ѕ получател€ платежа
	 * @return наименование
	 */
	String getReceiverName(String account, String bic, String tb) throws GateException, GateLogicException;
}
