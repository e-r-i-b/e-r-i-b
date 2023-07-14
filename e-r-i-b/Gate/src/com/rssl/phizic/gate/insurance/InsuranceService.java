package com.rssl.phizic.gate.insurance;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.InsuranceAppIdCacheKeyComposer;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

public interface InsuranceService extends Service
{
	/**
	 * —писок страховых продуктов клиента
	 * @param client - клиент, дл€ которого получаем продукты
	 * @return список страховых продуктов
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ClientCacheKeyComposer.class, name = "InsuranceApp.getInsuranceList")
   List<InsuranceApp> getInsuranceList(Client client) throws GateException, GateLogicException;

	/**
	 * ƒетальна€ информаци€ по страховому продукту
	 * @param externalId   - внешний ID продукта
	 * @return детальна€ информаци€
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= InsuranceAppIdCacheKeyComposer.class, name = "InsuranceApp.getInsuranceApp")
   InsuranceApp getInsuranceApp(String externalId)throws GateException, GateLogicException;

}
