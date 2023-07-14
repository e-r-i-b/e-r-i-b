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

						log.info("�������� ��������� �����-����� ������� " + getServiceName());
					}
					catch (BusinessException e)
					{
						throw new ConfigurationException("���� �� �������� ���������� �����-����� ������� " + getServiceName(), e);
					}
				}
			}
		}
		return container;
	}

	/**
	 * ��������� ������ ��������� �� formName �������
	 * @param formName ��� ����� �������
	 * @return ������ ���������
	 * @throws IllegalStateException - ������ ��� �� ��������
	 * @throws IllegalArgumentException - �� ������� ������ ��������� � ��������� ������
	 */
	public StateMachine getStateMachineByFormName(String formName)
	{
		return getStateMachineByName(MetadataCache.getBasicMetadata(formName).getName());
	}
}
