package com.rssl.phizic.web.common.client.finances.ajax;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.Budget;
import com.rssl.phizic.operations.finances.EditBudgetOperation;
import com.rssl.phizic.operations.finances.HintStateOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.struts.action.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Добавляет и удаляет бюджет
 * @author komarov
 * @ created 06.05.2013 
 * @ $Author$
 * @ $Revision$
 */

public class SaveBudgetAction extends AsyncOperationalActionBase
{
	private static final String FORWARD_HINTS_READ = "HintsRead";
	
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>();
		keyMethodMap.put("button.save", "save");
		keyMethodMap.put("button.delete", "delete");
		keyMethodMap.put("button.allHintsRead", "allHintsRead");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	/**
	 * сохраняет бюджет
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SaveBudgetForm frm = (SaveBudgetForm) form;
		EditBudgetOperation operation = (EditBudgetOperation) createOperation(EditBudgetOperation.class);
		operation.initialize(frm.getId());

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());

		Form newCategoryForm = SaveBudgetForm.SAVE_BUDGET_FORM;

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
		if (processor.process())
		{
			Map<String, Object> result = processor.getResult();
			Budget budget = operation.getEntity();
			Date date = (Date)result.get("date");
			BigDecimal summ = (BigDecimal)result.get("budgetSumm");
			try
			{
				if(date != null)
					budget.setBudget(operation.calculateAvgBudget(DateHelper.toCalendar(date)));
				else if(summ != null)
					budget.setBudget(summ.doubleValue());					
				operation.save(result);
				frm.setField("budgetSumm", budget.getBudget());
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
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * удаляет бюджет
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SaveBudgetForm frm = (SaveBudgetForm) form;
        EditBudgetOperation operation = (EditBudgetOperation) createOperation(EditBudgetOperation.class);
		operation.initialize(frm.getId());

		FieldValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, SaveBudgetForm.DELETE_BUDGET_FORM);
		if (processor.process())
		{
			operation.remove(processor.getResult());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Запомнить, что клиент прочитал все подсказки в бюджетировании
	 * @param mapping мапинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 * @throws BusinessException
	 */
	public ActionForward allHintsRead(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		HintStateOperation op = createOperation(HintStateOperation.class);
		op.setupHintsRead();

		return mapping.findForward(FORWARD_HINTS_READ);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
