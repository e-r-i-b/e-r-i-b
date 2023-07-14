package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.operations.finances.EditDeletedCategoryAbstractOperation;
import com.rssl.phizic.operations.finances.FinanceFilterData;
import com.rssl.phizic.operations.finances.FinancesOperationBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowCategoryOperationsAbstractAction extends FinanceFilterActionBase
{
	private static final String FORWARD_NEXT = "Next";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.next","next");
		return map;
	}

	protected void doFilter(FinanceFilterData filterData, FinancesOperationBase operation, FinanceFormBase frm) throws BusinessException
	{
		ShowCategoryOperationsAbstractForm form = (ShowCategoryOperationsAbstractForm) frm;
		EditDeletedCategoryAbstractOperation showOperation = (EditDeletedCategoryAbstractOperation) operation;
		updateFormData(form, showOperation, ShowCategoryOperationsAbstractForm.MIN_RES_ON_PAGE, 0);
	}

	protected FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException
	{
		ShowCategoryAbstractFormInterface form = (ShowCategoryAbstractFormInterface) frm;
		Long categoryId = form.getCategoryId();
		if (categoryId == null)
			throw new BusinessException("Не указан categoryId");

		EditDeletedCategoryAbstractOperation operation = createOperation(EditDeletedCategoryAbstractOperation.class);
		operation.initialize(categoryId);
		return operation;
	}

	/**
	 * Сохранение новых категорий для операций
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowCategoryOperationsAbstractForm frm = (ShowCategoryOperationsAbstractForm) form;
		EditDeletedCategoryAbstractOperation operation = (EditDeletedCategoryAbstractOperation)createFinancesOperation(frm);

		Long generalCategoryId = Long.parseLong((String)frm.getField("generalCategory"));
		if (generalCategoryId > 0)
			operation.setGeneralCategory(generalCategoryId);
		else
		{
			Map<String, String> newCategories = frm.getNewCategory();
			for (Map.Entry<String, String> entry : newCategories.entrySet())
			{
				Long newCategoryId = Long.parseLong(entry.getValue());
				if (newCategoryId > 0)
					operation.addChangedOperation(Long.parseLong(entry.getKey()), newCategoryId);
				else
				{
					ActionMessages message = new ActionMessages();
					message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Измените тип категории у всех операций!", false));
					saveErrors(request, message);
					return start(mapping, form, request, response);
				}
			}
			operation.saveOperations();
		}

		return mapping.findForward(FORWARD_NEXT);
	}

	private void updateFormData(ShowCategoryOperationsAbstractForm form, EditDeletedCategoryAbstractOperation operation, int resOnPage, int searchPage) throws BusinessException
	{
		form.setCategory(operation.getCategory());
		List<CardOperation> operations = operation.getCardOperations(resOnPage+1, searchPage*resOnPage);
		form.setData(operations);
		form.setOtherCategories(operation.getOtherAvailableCategories());
	}
}
