package com.rssl.phizic.web.schemes;

import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 08.09.2005
 * Time: 21:20:11
 */

public class ListSchemesForm extends ListFormBase<SharedAccessScheme>
{
	private Map<String,String> categories = new HashMap<String, String>();
	private Long[] selectedNodes;

	/**
	 * @return категории схем
	 */
	public Map<String, String> getCategories()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return categories;
	}

	/**
	 * задать категории схем
	 * @param categories категории схем
	 */
	public void setCategories(Map<String, String> categories)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.categories = categories;
	}

	/**
	 * @return выбранные ноды
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public Long[] getSelectedNodes()
	{
		return selectedNodes;
	}

	/**
	 * @param selectedNodes выбранные ноды
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void setSelectedNodes(Long[] selectedNodes)
	{
		this.selectedNodes = selectedNodes;
	}
}
