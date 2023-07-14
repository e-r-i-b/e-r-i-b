package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;

/**
 * @author tisov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public enum ChangePaymentStatusType
{
	REFUSE("EmployeeRefuseMoneyBoxPayment", "RefuseMoneyBoxPayment", AutoPayStatusType.Paused, "������� ��������������"),
	RECOVER("EmployeeRecoverMoneyBoxPayment", "RecoverMoneyBoxPayment", AutoPayStatusType.Active, "������� ������������"),
	CLOSE("EmployeeCloseMoneyBoxPayment", "CloseMoneyBoxPayment", AutoPayStatusType.Closed, "������� �������");

	private String successMessage;
	private String employeeServiceKey;
	private String clientServiceKey;
	private AutoPayStatusType autoPayStatusType;

	private ChangePaymentStatusType(String employeeServiceKey, String clientServiceKey, AutoPayStatusType autoPayStatusType, String successMessage)
	{
		this.successMessage = successMessage;
		this.employeeServiceKey = employeeServiceKey;
		this.clientServiceKey = clientServiceKey;
		this.autoPayStatusType = autoPayStatusType;
	}

	/**
	 * @return ��������� �� ���������� �������� � �������
	 */
	public String getSuccessMessage()
	{
		return successMessage;
	}

	/**
	 * @return ���� ��� ������ ������ ����������
	 */
	public String getEmployeeServiceKey()
	{
		return employeeServiceKey;
	}

	/**
	 * @return ���� ��� ������ ������ �������
	 */
	public String getClientServiceKey()
	{
		return clientServiceKey;
	}

	/**
	 * @return ��������� �������� �� ����������
	 */
	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoPayStatusType;
	}
}
