package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizicgate.manager.GateManager;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;

/**
 * @author osminin
 * @ created 20.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeDictionariesExtrOperation extends SynchronizeDictionariesOperation
{
	protected Gate getGate() throws BusinessException, BusinessLogicException
	{
		try
		{
			AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
			Adapter adapter = config.getDictionatyReplicationAdapter();
			if (adapter == null)
			{
				throw new BusinessLogicException("Ќе задана внешн€€ система дл€ репликации");
			}

			//при получении uuid адаптера делаем проверку на активность внешней системы
			String uuid = ExternalSystemHelper.getCode(adapter.getUUID());
			Gate gate = GateManager.getInstance().getGate(uuid);
			if (gate == null)
			{
				throw new BusinessLogicException("Ќе задана внешн€€ система дл€ репликации");
			}
			return gate;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
