package com.rssl.phizic.business.resources.external;

/**
 * @author EgorovaA
 * @ created 05.02.15
 * @ $Author$
 * @ $Revision$
 */
public class ActiveNotDepositAccountFilter extends ActiveAccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		return super.accept(accountLink) && (accountLink.getNumber().startsWith("40817") || accountLink.getNumber().startsWith("40820"));
	}
}
