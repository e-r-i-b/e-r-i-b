package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.web.actions.payments.catalog.ApiSearchCatalogFormBase;

/**
 @author Pankin
 @ created 19.11.2010
 @ $Author$
 @ $Revision$
 */
public class SearchServiceProviderMobileForm extends ApiSearchCatalogFormBase
{
	private Long regionId;

	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
	}
}
