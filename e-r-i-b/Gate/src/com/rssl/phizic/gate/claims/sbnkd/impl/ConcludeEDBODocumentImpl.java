package com.rssl.phizic.gate.claims.sbnkd.impl;

import com.rssl.phizic.gate.claims.sbnkd.ConcludeEDBODocument;

/**
 * @author bogdanov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ConcludeEDBODocumentImpl extends ClientInfoDocumentImpl implements ConcludeEDBODocument
{
	private String edboBranchId;
	private String edboAgencyId;
	private String edboPhone;
	private String edboPhoneOperator;
	private long edboOrderNumber;
	private String edboTb;
	private String lastLogonCardNumber;

	/**
	 * @param eDBOBranchId Номер филиала заведения УДБО.
	 */
	public void setEDBOBranchId(String eDBOBranchId)
	{
		this.edboBranchId = eDBOBranchId;
	}

	/**
	 * @return Номер филиала заведения УДБО.
	 */
	public String getEDBOBranchId()
	{
		return edboBranchId;
	}

	/**
	 * @param eDBOAgencyId Номер отделения заведения УДБО.
	 */
	public void setEDBOAgencyId(String eDBOAgencyId)
	{
		this.edboAgencyId = eDBOAgencyId;
	}

	/**
	 * @return Номер отделения заведения УДБО.
	 */
	public String getEDBOAgencyId()
	{
		return edboAgencyId;
	}

	/**
	 * @param eDBOPhone Номер мобильного телефона УДБО.
	 */
	public void setEDBOPhone(String eDBOPhone)
	{
		this.edboPhone = eDBOPhone;
	}

	/**
	 * @return Номер мобильного телефона УДБО.
	 */
	public String getEDBOPhone()
	{
		return edboPhone;
	}

	/**
	 * @param eDBOPhoneOperator Оператор мобильного телефона УДБО.
	 */
	public void setEDBOPhoneOperator(String eDBOPhoneOperator)
	{
		this.edboPhoneOperator = eDBOPhoneOperator;
	}

	/**
	 * @return Оператор мобильного телефона УДБО.
	 */
	public String getEDBOPhoneOperator()
	{
		return edboPhoneOperator;
	}

	public String getEDBO_TB()
	{
		return edboTb;
	}

	public void setEDBOOrderNumber(Long fieldNum)
	{
		this.edboOrderNumber = fieldNum;
	}

	public Long getEDBOOrderNumber()
	{
		return edboOrderNumber;
	}

	public void setEDBO_TB(String fieldData1)
	{
		this.edboTb = fieldData1;
	}

	public String getLastLogonCardNumber()
	{
		return lastLogonCardNumber;
	}

	public void setLastLogonCardNumber(String lastLogonCardNumber)
	{
		this.lastLogonCardNumber = lastLogonCardNumber;
	}
}
