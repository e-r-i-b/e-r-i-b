package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.gate.depo.DepoAccountSecurity;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author komarov
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 */


public class ShowClientSecuritiesForm extends ActionFormBase
{
	private String depoAccount;
	private String divisionType;
	private String divisionNumber;

	private List<DepoAccountSecurity> securityList;

	public List<DepoAccountSecurity> getSecurityList()
	{
		return securityList;
	}

	public void setSecurityList(List<DepoAccountSecurity> securityList)
	{
		this.securityList = securityList;
	}

	public String getDepoAccount()
	{
		return depoAccount;
	}

	public void setDepoAccount(String depoAccount)
	{
		this.depoAccount = depoAccount;
	}

	public String getDivisionType()
	{
		return divisionType;
	}

	public void setDivisionType(String divisionType)
	{
		this.divisionType = divisionType;
	}

	public String getDivisionNumber()
	{
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber)
	{
		this.divisionNumber = divisionNumber;
	}
}
