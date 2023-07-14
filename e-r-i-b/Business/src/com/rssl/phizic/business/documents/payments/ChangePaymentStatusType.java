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
	REFUSE("EmployeeRefuseMoneyBoxPayment", "RefuseMoneyBoxPayment", AutoPayStatusType.Paused, "Копилка приостановлена"),
	RECOVER("EmployeeRecoverMoneyBoxPayment", "RecoverMoneyBoxPayment", AutoPayStatusType.Active, "Копилка возобновлена"),
	CLOSE("EmployeeCloseMoneyBoxPayment", "CloseMoneyBoxPayment", AutoPayStatusType.Closed, "Копилка закрыта");

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
	 * @return сообщение об успешности перехода к статусу
	 */
	public String getSuccessMessage()
	{
		return successMessage;
	}

	/**
	 * @return ключ для поиска услуги сотрудника
	 */
	public String getEmployeeServiceKey()
	{
		return employeeServiceKey;
	}

	/**
	 * @return ключ для поиска услуги клиента
	 */
	public String getClientServiceKey()
	{
		return clientServiceKey;
	}

	/**
	 * @return Состояние подписки на автоплатеж
	 */
	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoPayStatusType;
	}
}
