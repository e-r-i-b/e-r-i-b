package com.rssl.phizic.web.persons;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author emakarov
 * @ created 26.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListActivePersonsAction extends ListPersonsAction
{
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("state", "0");

		return filterParameters;
	}
}
