package com.rssl.phizic.business.bki;

/**
 * @author Nady
 * @ created 13.01.15
 * @ $Author$
 * @ $Revision$
 */
public class TotalArrears
{
	/**
	 * дата просрочки
	 */
	private String dateArrears;
	/**
	 * Название банка
	 */
	private String bankName;
	/**
	 * сумма просрочки
	 */
	private Money arrears;

	public TotalArrears(String dateArrears, String bankName, Money arrears)
	{
		this.dateArrears = dateArrears;
		this.bankName = bankName;
		this.arrears = arrears;
	}

	public String getDateArrears()
	{
		return dateArrears;
	}

	public void setDateArrears(String dateArrears)
	{
		this.dateArrears = dateArrears;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public Money getArrears()
	{
		return arrears;
	}

	public void setArrears(Money arrears)
	{
		this.arrears = arrears;
	}
}
