package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.operations.finances.EditDeletedCategoryAbstractOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.finances.CardOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение операций клиента по категории
 * @author lepihina
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncShowCategoryOperationsAbstractAction extends ShowCategoryOperationsAbstractAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("button.getOperations","getOperations");
		return map;
	}

	/**
	 * Получение операций клиента
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public final ActionForward getOperations(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncShowCategoryOperationsAbstractForm frm = (AsyncShowCategoryOperationsAbstractForm) form;
		EditDeletedCategoryAbstractOperation operation = (EditDeletedCategoryAbstractOperation)createFinancesOperation(frm);

		updateFormData(frm, operation, frm.getResOnPage(), frm.getSearchPage());
		return mapping.findForward(FORWARD_SHOW);
	}

	private void updateFormData(AsyncShowCategoryOperationsAbstractForm form, EditDeletedCategoryAbstractOperation operation, int resOnPage, int searchPage) throws BusinessException
	{
		List<CardOperation> operations = operation.getCardOperations(resOnPage+1, searchPage*resOnPage);
		form.setCardOperations(operations);
		form.setOtherCategories(operation.getOtherAvailableCategories());
		form.setResOnPage(resOnPage);
		form.setSearchPage(searchPage);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
