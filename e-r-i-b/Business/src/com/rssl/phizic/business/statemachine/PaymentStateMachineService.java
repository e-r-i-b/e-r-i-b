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

						log.info("�������� ��������� �����-����� � ������ " + getServiceName());
					}
					catch (BusinessException e)
					{
						throw new ConfigurationException("���� �� �������� ���������� �����-����� � ������ " + getServiceName(), e);
					}
				}
			}
		}
		return container;
	}

	/**
	 * ��������� ������ ��������� �� formName
	 * @param formName ��� �����
	 * @return ������ ���������
	 * @throws IllegalStateException - ������ ��� �� ��������
	 * @throws IllegalArgumentException - �� ������� ������ ��������� � ��������� ������
	 */
	public StateMachine getStateMachineByFormName(String formName)
	{
		StateMachineInfo machineInfo = MetadataCache.getBasicMetadata(formName).getStateMachineInfo();
		return getStateMachineByName(machineInfo.getName());
	}
}
