package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.statemachine.StateObjectCondition;

/**
 * User: Balovtsev
 * Date: 27.12.2011
 * Time: 11:23:13
 *
 *
 * ��������� ������� ������������ ����� ����� ������������� ���������
 */
public class FieldsAreChangedStateObjectCondition implements StateObjectCondition
{
	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		/*
		 * ���� ���� ������������ ���� ����� ������������ ������������ � �������� �� ������� ��������.
		 */
		return stateMachineEvent.hasChangedFields();
	}
}
