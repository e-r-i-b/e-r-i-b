package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.web.common.FilterActionForm;

import java.util.Map;
import java.util.List;

/**
 * @author lukina
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListProvidersByRegionsForm extends IndexForm
{
    private String pageListByRegion = "";
	private int firstPageProviderCount;

	public int getFirstPageProviderCount()
	{
		return firstPageProviderCount;
	}

	public void setFirstPageProviderCount(int firstPageProviderCount)
	{
		this.firstPageProviderCount = firstPageProviderCount;
	}

	public String getPageListByRegion()
	{
		return pageListByRegion;
	}

	public void setPageListByRegion(String pageListByRegion)
	{
		this.pageListByRegion = pageListByRegion;
	}
}
