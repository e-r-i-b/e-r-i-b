package com.rssl.phizic.business.securityAccount;

import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.security.SecurityAccount;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Объект-заглушка для сберсертификатов клиента
 * @author lukina
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 */
public class MockSecurityAccount implements SecurityAccount, MockObject
{

	private String id; //идентификатор

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBlankType()
	{
		return null;
	}

	public String getSerialNumber()
	{
		return null;
	}

	public Money getNominalAmount()
	{
		return null;
	}

	public Money getIncomeAmt()
	{
		return null;
	}

	public BigDecimal getIncomeRate()
	{
		return null;
	}

	public String getTermType()
	{
		return null;
	}

	public Long getTermDays()
	{
		return null;
	}

	public Calendar getComposeDt()
	{
		return null;
	}

	public Calendar getTermStartDt()
	{
		return null;
	}

	public Calendar getTermFinishDt()
	{
		return null;
	}

	public Calendar getTermLimitDt()
	{
		return null;
	}

	public boolean getOnStorageInBank()
	{
		return false;
	}

	public String getDocNum()
	{
		return null;
	}

	public Calendar getDocDt()
	{
		return null;
	}

	public String getBankId()
	{
		return null;
	}

	public String getBankName()
	{
		return null;
	}

	public String getBankPostAddr()
	{
		return null;
	}

	public String getIssuerBankId()
	{
		return null;
	}

	public String getIssuerBankName()
	{
		return null;
	}
}
