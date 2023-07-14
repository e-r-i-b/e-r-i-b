package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.documents.ResourceType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Erkin
 * @ created 09.11.2014
 * @ $Author$
 * @ $Revision$
 */
public final class ExternalResourceUtils
{
	public static List<AccountLink> selectAccountLinks(List<? extends ExternalResourceLink> resources)
	{
		List<AccountLink> accounts = new LinkedList<AccountLink>();
		for (ExternalResourceLink link : resources)
		{
			if (link.getResourceType() == ResourceType.ACCOUNT)
				accounts.add((AccountLink) link);
		}
		return accounts;
	}

	public static List<CardLink> selectCardLinks(List<? extends ExternalResourceLink> resources)
	{
		List<CardLink> cards = new LinkedList<CardLink>();
		for (ExternalResourceLink link : resources)
		{
			if (link.getResourceType() == ResourceType.CARD)
				cards.add((CardLink) link);
		}
		return cards;
	}
}
