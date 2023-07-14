package com.rssl.phizic.web.client.insurance;

import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.gate.insurance.InsuranceApp;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author lukina
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListInsuranceProgramForm  extends ActionFormBase
{
	private List<InsuranceLink> insuranceLinks;
	private Map<InsuranceLink, InsuranceApp> insuranceAppList;
	private PFRLink pfrLink;
	private List<PFRStatementClaim> pfrClaims = new ArrayList<PFRStatementClaim>(); //заявки на получение выписки из ПФР
	private boolean isPfrClaimsInitialized; //были ли установлены заявки pfrClaims. Для отличия пустого списка от отложенной инициализации
	private ActivePerson user;
	private boolean isPfrDown;
	private boolean isBackError;

	public List<InsuranceLink> getInsuranceLinks()
	{
		return insuranceLinks;
	}

	public void setInsuranceLinks(List<InsuranceLink> insuranceLinks)
	{
		this.insuranceLinks = insuranceLinks;
	}

	public PFRLink getPfrLink()
	{
		return pfrLink;
	}

	public void setPfrLink(PFRLink pfrLink)
	{
		this.pfrLink = pfrLink;
	}

	public List<PFRStatementClaim> getPfrClaims()
	{
		return pfrClaims;
	}

	public void setPfrClaims(List<PFRStatementClaim> pfrClaims)
	{
		this.pfrClaims = pfrClaims;
	}

	public boolean isPfrClaimsInitialized()
	{
		return isPfrClaimsInitialized;
	}

	public void setPfrClaimsInitialized(boolean pfrClaimsInitialized)
	{
		isPfrClaimsInitialized = pfrClaimsInitialized;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public boolean isPfrDown()
	{
		return isPfrDown;
	}

	public void setPfrDown(boolean pfrDown)
	{
		isPfrDown = pfrDown;
	}

	public Map<InsuranceLink, InsuranceApp> getInsuranceAppList()
	{
		return insuranceAppList;
	}

	public void setInsuranceAppList(Map<InsuranceLink, InsuranceApp> insuranceAppList)
	{
		this.insuranceAppList = insuranceAppList;
	}

	public boolean isBackError()
	{
		return isBackError;
	}

	public void setBackError(boolean backError)
	{
		isBackError = backError;
	}
}
