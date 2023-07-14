package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;

/**  Возникает, если обрабатываемое машиной состояний событие недопустимо
 *  * @author butvin
 * @ created 14.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class IllegalEventException extends BusinessException
{
	private final ObjectEvent objectEvent;
	private final StateObject stateObject;
	private static final String ILLEGAL_EVENT_EXCEPTION = "Событие '%S' типа '%S' недопустимо для состояния " +
			"'%S' машины '%S'.";

	/**
	 *  конструктор
	 * @param objectEvent событие
	 * @param stateObject объект машины состояний
	 */
	public IllegalEventException(ObjectEvent objectEvent, StateObject stateObject)
	{
		super(String.format(ILLEGAL_EVENT_EXCEPTION, objectEvent.getEvent(), objectEvent.getType(),
				stateObject.getState().getCode(), stateObject.getStateMachineInfo().getName()));
		this.objectEvent = objectEvent;
		this.stateObject = stateObject;
	}

	/**
	 * @return событие
	 */
	public ObjectEvent getObjectEvent()
	{
		return objectEvent;
	}

	/**
	 * @return объект машины состояний
	 */
	public StateObject getStateObject()
	{
		return stateObject;
	}
}
