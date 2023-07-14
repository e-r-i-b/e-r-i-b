package com.rssl.phizic.web.dictionaries.regions;

import com.rssl.phizic.operations.dictionaries.regions.SynchronizeRegionOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.regions.GetRegionsListOperation;
import com.rssl.phizic.operations.dictionaries.regions.RemoveRegionOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchDictionariesService;
import com.rssl.phizic.business.dictionaries.asyncSearch.AsynchSearchObjectState;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 02.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ListRegionsAction extends ListActionBase
{
	@Override
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> keymap = super.getAditionalKeyMethodMap();
		keymap.put("button.synchronize", "syncRegionDictionary");
		return keymap;
	}

	private static final AsynchSearchDictionariesService asynchSearchDictionariesService = new AsynchSearchDictionariesService();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetRegionsListOperation.class, "RegionDictionaryManagement");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveRegionOperation.class, "RegionDictionaryManagement");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
			Region region = (Region) operation.getEntity();
			asynchSearchDictionariesService.addObjectInfoForAsynchSearch(region, AsynchSearchObjectState.DELETED);
		}
		catch (BusinessLogicException ignore)
		{
			String name = ((RemoveRegionOperation) operation).getEntity().getName();
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.dictionaries.regions.removeException", name);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}

	/**
	 * Синхронизирует справочник регионов с бд ЦСА
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward syncRegionDictionary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SynchronizeRegionOperation operation = createOperation(SynchronizeRegionOperation.class);
		try
		{
			operation.synchronize();
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, new ActionMessage(ble.getMessage(), false));
		}
		return start(mapping, form, request, response);
	}
}
