package com.rssl.phizic.business.statemachine.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.StateMachineContainer;
import com.rssl.phizic.business.statemachine.StateMachineParser;
import com.rssl.phizic.business.statemachine.StateMachineService;
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
public class TemplateStateMachineService extends StateMachineService
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
						StateMachineParser parser = new TemplateStateMachineParser();
						List<StateMachine> stateMachines = parser.parse(null);
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

	/**
	 * Получение машины состояний по formName объекта
	 * @param formName имя формы объекта
	 * @return машина состояний
	 * @throws IllegalStateException - служба ещё не запущена
	 * @throws IllegalArgumentException - не найдена машина состояний с указанным именем
	 */
	public StateMachine getStateMachineByFormName(String formName)
	{
		return getStateMachineByName(MetadataCache.getBasicMetadata(formName).getName());
	}
}
