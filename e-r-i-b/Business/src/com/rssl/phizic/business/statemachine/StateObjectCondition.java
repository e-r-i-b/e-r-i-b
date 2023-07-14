package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Gainanov
 * @ created 01.10.2009
 * @ $Author$
 * @ $Revision$
 */
public interface StateObjectCondition<SO extends StateObject>
{
	/**
	 * ��������� ������� �������� �� ���������
	 * @param stateObject - ������, ��� �������� ��� ��������
	 * @param stateMachineEvent ������ ������ ��������� ������� ���������� ������� ������������ � ������
	 * ������������ �����
	 * @return true ���� ����������� �������, false ���� ���
	 */
	boolean accepted(SO stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException;
}
