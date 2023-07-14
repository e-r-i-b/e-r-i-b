package com.rssl.phizic.web.common.socialApi.finances.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.finances.RemoveCardOperationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 05.03.14
 * $Author$
 * $Revision$
 */
public class RemoveFinanceOperationsMobileAction extends OperationalActionBase
{
	private static final String REMOVE_RESULT = "RemoveResult";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String,String>();
		map.put("remove","remove");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new NoSuchMethodException();
	}

	/**
	 * ”дал€ет операцию (клиент может удал€ть только те операции, которые сам добавил)
	 * @param mapping - стратс-маппинг
	 * @param form - форма
	 * @param request - запрос
	 * @param response - ответ
	 * @return форвард на построение ответа
	 * @throws BusinessException
	 */
	@SuppressWarnings({"UnusedDeclaration"}) //request response
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		RemoveFinanceOperationsMobileForm frm = (RemoveFinanceOperationsMobileForm)form;
		RemoveCardOperationOperation operation = createOperation(RemoveCardOperationOperation.class, "AddOperationsService");
		operation.initialize(frm.getId());
		operation.remove();
		addLogParameters(new BeanLogParemetersReader("ƒанные удаленной сущности", operation.getEntity()));

		return mapping.findForward(REMOVE_RESULT);
	}
}
