package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;

/**  ���������, ���� �������������� ������� ��������� ������� �����������
 *  * @author butvin
 * @ created 14.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class IllegalEventException extends BusinessException
{
	private final ObjectEvent objectEvent;
	private final StateObject stateObject;
	private static final String ILLEGAL_EVENT_EXCEPTION = "������� '%S' ���� '%S' ����������� ��� ��������� " +
			"'%S' ������ '%S'.";

	/**
	 *  �����������
	 * @param objectEvent �������
	 * @param stateObject ������ ������ ���������
	 */
	public IllegalEventException(ObjectEvent objectEvent, StateObject stateObject)
	{
		super(String.format(ILLEGAL_EVENT_EXCEPTION, objectEvent.getEvent(), objectEvent.getType(),
				stateObject.getState().getCode(), stateObject.getStateMachineInfo().getName()));
		this.objectEvent = objectEvent;
		this.stateObject = stateObject;
	}

	/**
	 * @return �������
	 */
	public ObjectEvent getObjectEvent()
	{
		return objectEvent;
	}

	/**
	 * @return ������ ������ ���������
	 */
	public StateObject getStateObject()
	{
		return stateObject;
	}
}
