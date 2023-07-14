package com.rssl.phizic.logging.csaAction;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Сущность-запись журнала входов.
 */
public class CSAActionLogEntry extends CSAActionLogEntryBase
{
	private String cardNumber;
	private String employeeFio;
	private String employeeLogin;

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getEmployeeFio()
	{
		return employeeFio;
	}

	public void setEmployeeFio(String employeeFio)
	{
		this.employeeFio = employeeFio;
	}

	public String getEmployeeLogin()
	{
		return employeeLogin;
	}

	public void setEmployeeLogin(String employeeLogin)
	{
		this.employeeLogin = employeeLogin;
	}

	private static final long serialVersionUID = 1L;
}
