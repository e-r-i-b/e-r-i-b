package com.rssl.phizic.web.common.client.ext.sbrf.resources;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.ClientResourceHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;

import java.util.List;
import java.util.Map;

/**
 * Обновление продуктов клиента при входе через канал УС
 * @author Pankin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

// В классе не должно быть состояния
public class AddOrUpdateClientResourcesATMAction extends AddOrUpdateClientResourcesAction
{

	protected Pair<List<Class>, Map<Class, String>> getClientProducts(ActivePerson person) throws BusinessException
	{
		return ClientResourceHelper.getClientProductsForATM(person);
	}

	protected boolean getOnlyWayCards()
	{
		return ConfigFactory.getConfig(AtmApiConfig.class).getOnlyWayCards();
	}
}
