package com.rssl.phizic.business.statemachine.documents.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.statemachine.*;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.config.ConfigurationException;

import java.util.List;

/**
 * @author Erkin
 * @ created 11.07.2014
 * @ $Author$
 * @ $Revision$
 */
@ThreadSafe
public class PFPStateMachineService extends StateMachineService
{
	private static volatile StateMachineContainer container;

	@Override
	protected StateMachineContainer getContainer()
	{
		if (container == null)
		{
			synchronized (this.getClass())
			{
				if (container == null)
				{
					try
					{
						StateMachineParser parser = new StateMachineParser();
						List<StateMachine> stateMachines = parser.parse("pfp-state-machine.xml");
						container = new StateMachineContainer(stateMachines);

						log.info("Загружен контейнер стейт-машин службой " + getServiceName());
					}
					catch (BusinessException e)
					{
						throw new ConfigurationException("Сбой на загрузке контейнера стейт-машин службой " + getServiceName(), e);
					}
				}
			}
		}
		return container;
	}
}
