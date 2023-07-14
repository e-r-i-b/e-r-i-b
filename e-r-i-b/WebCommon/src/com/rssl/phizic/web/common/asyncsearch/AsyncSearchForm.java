package com.rssl.phizic.web.common.asyncsearch;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.*;

/**
 * Форма для результата живого поиска
 * @author Pankin
 * @ created 02.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class AsyncSearchForm extends ActionFormBase
{
	private String result;
	private String regionId;
	private String pageType;

	private Map<String,Object> fields = new HashMap<String, Object>();

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public void setRegionId(String regionId)
	{
		this.regionId = regionId;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}

	public Object getField(String key)
    {
        return fields.get(key);
    }

    public void setField(String key, Object obj)
    {
        fields.put(key, obj);
    }

    public Map<String, Object> getFields()
    {
        return Collections.unmodifiableMap(fields);
    }
}
