package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.ExistOperationsInCardOperationCategoryException;
import com.rssl.phizic.operations.finances.CategoriesListOperation;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ƒобавление / удаление категорий операций
 * @author Pankin
 * @ created 16.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class AsyncCategoryAction extends CategoriesListAction
{
	private static final String EDIT_RESULT = "EditResult";
	private static final String REMOVE_RESULT = "RemoveResult";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(1);
		keyMethodMap.put("button.add", "add");
		keyMethodMap.put("button.delete", "delete");
		return keyMethodMap;
	}

	/**
	 * ƒобавление категории клиента
	 * @param mapping - mapping
	 * @param form - form
	 * @param request - request
	 * @param response - response
	 * @return forward
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCategoryForm frm = (AsyncCategoryForm) form;
		CategoriesListOperation operation = createOperation("CategoriesEditOperation", "EditCategoriesService");
		operation.initialize(frm.getId());

		FieldValuesSource valuesSource = getMapValueSource(frm);

		Form newCategoryForm = AsyncCategoryForm.NEW_CATEGORY_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			CardOperationCategory category = operation.getEntity();
			category.setName((String) result.get("name"));
			if (!StringHelper.isEmpty(frm.getIncomeType()))
				category.setIncome((Boolean) result.get("incomeType"));
			category.setCash(true);
			category.setCashless(true);
			category.setColor(operation.getFreeColor());

			try
			{
				operation.save();
				log.info("—охранена категори€ операций по карте. Ќаименование категории - \"" + category.getName() + "\", тип операций - расходные.");
				frm.setCategory(category);
			}
			catch (BusinessLogicException e)
			{
				ActionMessages msg = new ActionMessages();
				msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
				saveErrors(request, msg);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(EDIT_RESULT);
	}

	/**
	 * ”даление категории клиента
	 * @param mapping - mapping
	 * @param form - form
	 * @param request - request
	 * @param response - response
	 * @return forward
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncCategoryForm frm = (AsyncCategoryForm) form;
        CategoriesListOperation operation = createOperation("CategoriesEditOperation", "EditCategoriesService");
		try
        {
	        CardOperationCategory category = operation.delete(frm.getId());
	        log.info("”далена категори€ операций по карте. Ќаименование категории - \"" + category.getName() + "\", тип операций - расходные.");
        }
		catch (ExistOperationsInCardOperationCategoryException ex)
		{
			frm.setOperationState("ExistOperationsInCategory");
		}

		return mapping.findForward(REMOVE_RESULT);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}

	protected boolean isAjax()
	{
		return true;
	}

	@Override
	protected MapValuesSource getMapValueSource(FinanceFormBase frm)
	{
		return new MapValuesSource(frm.getFields());
	}
}
