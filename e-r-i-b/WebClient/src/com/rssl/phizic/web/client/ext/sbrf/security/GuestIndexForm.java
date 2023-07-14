package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.gate.claims.sbnkd.impl.ClaimOffer;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tisov
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestIndexForm extends ActionFormBase
{
	private List<ClaimOffer> claimsList = new ArrayList<ClaimOffer>();   //список заявок

	/**
	 * @return список заявок
	 */
	public List<ClaimOffer> getClaimsList()
	{
		return claimsList;
	}

	/**
	 * @param claimsList список заявок
	 */
	public void setClaimsList(List<ClaimOffer> claimsList)
	{
		this.claimsList = claimsList;
	}
}
