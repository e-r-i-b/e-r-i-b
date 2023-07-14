package com.rssl.phizic.web.common.socialApi.finances.categories;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.web.common.client.finances.AsyncCategoryAction;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * ƒобавление/редактирование/удаление категории
 * @author Dorzhinov
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditCategoryMobileAction extends AsyncCategoryAction
{
	protected boolean getEmptyErrorPage()
	{
		return false;
	}

	protected boolean isAjax()
	{
		return false;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("add", "add");
		map.put("update", "add");
		map.put("remove", "delete");
		return map;
	}

	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		EditCategoryMobileForm form = (EditCategoryMobileForm) frm;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", form.getName());
		return new MapValuesSource(map);
	}
}
