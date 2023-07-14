package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * ВСП с возможностью выдачи кредита
 * @author Pankin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class VSPLoanReplicaDepartment  extends DictionaryRecordBase
{
	private String tb;
	private String osb;
	private String office;
	private boolean loanAvalible;

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getOsb()
	{
		return osb;
	}

	public void setOsb(String osb)
	{
		this.osb = osb;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public Comparable getSynchKey()
	{
		return getTb()+getOsb()+getOffice();
	}

	public boolean isLoanAvalible()
	{
		return loanAvalible;
	}

	public void setLoanAvalible(boolean loanAvalible)
	{
		this.loanAvalible = loanAvalible;
	}

}
