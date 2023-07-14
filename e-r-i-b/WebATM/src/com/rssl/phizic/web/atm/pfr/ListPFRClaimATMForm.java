package com.rssl.phizic.web.atm.pfr;

import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * Форма списка заявок на получение выписки ПФР
 * @author Jatsky
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ListPFRClaimATMForm extends ListFormBase
{
	private String from;
	private String to;
	private List<PFRStatementClaim> claims;
	private boolean error;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public List<PFRStatementClaim> getClaims()
	{
		return claims;
	}

	public void setClaims(List<PFRStatementClaim> claims)
	{
		this.claims = claims;
	}

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}
}
