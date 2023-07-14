package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author Erkin
 * @ created 11.07.2014
 * @ $Author$
 * @ $Revision$
 */
public abstract class StateMachineService
{
	@SuppressWarnings("ProtectedField")
	protected final Log log = PhizICLogFactory.getLog(LogModule.Core);

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ������� � ������������� ��������� �����-�����
	 * @return ��������� �����-����� (never null)
	 */
	protected abstract StateMachineContainer getContainer();

	protected String getServiceName()
	{
		return getClass().getSimpleName();
	}

	/**
	 * ����� ������ ��������� �� � �����
	 * @param stateMachineName - ��� ������ ��������� (never null nor empty)
	 * @return ������ ��������� (never null)
	 * @throws IllegalStateException - ������ ��� �� ��������
	 * @throws IllegalArgumentException - �� ������� ������ ��������� � ��������� ������
	 */
	public StateMachine getStateMachineByName(String stateMachineName)
	{
		StateMachineContainer container = getContainer();
		StateMachine stateMachine = container.getStateMachine(stateMachineName);
		if (stateMachine == null)
			throw new IllegalArgumentException("�� ������� ������ ��������� " + stateMachineName + " � ������ " + getServiceName());

		return stateMachine;
	}

	/**
	 * ��������� ������ ��������� �� �������
	 * @param object - ������
	 * @return - ������ ��������� (never null)
	 * @throws IllegalStateException - ������ ��� �� ��������
	 * @throws IllegalArgumentException - �� ������� ������ ��������� � ��������� ������
	 */
	public StateMachine getStateMachineByObject(StateObject object)
	{
		return getStateMachineByName(object.getStateMachineInfo().getName());
	}
}
