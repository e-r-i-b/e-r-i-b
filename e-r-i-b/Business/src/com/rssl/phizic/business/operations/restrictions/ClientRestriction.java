package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author mihaylov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Интерфейс проверок ограничений на клиента
 */
public interface ClientRestriction extends Restriction
{

	/**
	 * Проверка возможности работы с клиентом
	 * @param client клиент для проверки
	 * @return можно ли работать с клиентом
	 */
	boolean accept(Client client) throws BusinessException;

}
