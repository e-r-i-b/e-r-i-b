package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.resources.external.ActiveNotVirtualNotCreditCardFilter;
import com.rssl.phizic.business.resources.external.ClientOwnCardFilter;

/**
 * User: Balovtsev
 * Date: 11.11.2011
 * Time: 13:37:29
 */
public class ActiveNotVirtualNotCreditNotAdditionalCardFilter extends ActiveNotVirtualNotCreditCardFilter
{
	public ActiveNotVirtualNotCreditNotAdditionalCardFilter()
	{
		super();
		//Дополнительной картой в данном случае является карта с AdditionalCardType == CLIENTTOOTHER. 
		addFilter(new ClientOwnCardFilter());
	}
}
