package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.doc.StateMachineInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.config.ConfigurationException;

import java.util.List;

/**
 * @author Erkin
 * @ created 11.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class PaymentStateMachineService extends StateMachineService
{
	private static volatile StateMachineContainer container;

	private static final Object lock = new Object();

	@Override
	protected StateMachineContainer getContainer()
	{
		if (container == null)
		{
			synchronized (lock)
			{
				if (container == null)
				{
					try
					{
						StateMachineParser parser = new PaymentStateMachineParser();
						List<StateMachine> stateMachines = parser.parse("state-machine.xml");
						container = new StateMachineContainer(stateMachines);

						log.info("Загружен контейнер стейт-машин в службе " + getServiceName());
					}
					catch (BusinessException e)
					{
						throw new ConfigurationException("Сбой на загрузке контейнера стейт-машин в службе " + getServiceName(), e);
					}
				}
			}
		}
		return container;
	}

	/**
	 * Получение машины состояний по formName
	 * @param formName имя формы
	 * @return машина состояний
	 * @throws IllegalStateException - служба ещё не запущена
	 * @throws IllegalArgumentException - не найдена машина состояний с указанным именем
	 */
	public StateMachine getStateMachineByFormName(String formName)
	{
		StateMachineInfo machineInfo = MetadataCache.getBasicMetadata(formName).getStateMachineInfo();
		return getStateMachineByName(machineInfo.getName());
	}
}
