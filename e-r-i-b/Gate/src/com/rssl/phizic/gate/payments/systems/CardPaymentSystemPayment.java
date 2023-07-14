package com.rssl.phizic.gate.payments.systems;

import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;

/**
 * @author krenev
 * @ created 02.12.2010
 * @ $Author$
 * @ $Revision$
 * ����������� ������ � �����
 */
public interface CardPaymentSystemPayment extends AbstractPaymentSystemPayment, AbstractCardTransfer, PossibleAddingOperationUIDObject
{
	/**
	 * @return ������ � ���������� ��������� ������
	 */
	EmployeeInfo getCreatedEmployeeInfo() throws GateException;

	/**
	 * @return ������ � ���������� ������������� ������
	 */
	EmployeeInfo getConfirmedEmployeeInfo() throws GateException;

	/**
	 * ���������� �������, �������� �� ������ �������� ������� ��������-������
	 * @return true - ������ ��������-������
	 */
	boolean isEinvoicing();
}
