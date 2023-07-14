package com.rssl.phizic.business.resources.external.security;

import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;

import java.util.List;

/**
 * @author lukina
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SecurityAccountServiceHelper
{
	public static SecurityAccountLink findSecurityAccountLinkByNumber(List<SecurityAccountLink> securityAccountLinks, String number)
	{
		for (SecurityAccountLink link : securityAccountLinks)
		{
			if(link.getNumber().equals(number))
				return link;
		}
		return null;
	}
}
