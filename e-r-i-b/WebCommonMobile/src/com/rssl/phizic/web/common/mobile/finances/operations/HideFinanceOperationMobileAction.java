package com.rssl.phizic.web.common.mobile.finances.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.finances.HideCardOperationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author koptyaev
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 */
public class HideFinanceOperationMobileAction  extends OperationalActionBase
{
	public static final String HIDE_RESULT = "HideResult";
	public static final String SHOW_RESULT = "ShowResult";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keys = new HashMap<String, String>();
		keys.put("hide", "hide");
		keys.put("show", "show");
		return keys;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new NoSuchMethodException();
	}
	/**
	 * Скрытие операции в мобильном приложении
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActionForward hide(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		HideFinanceOperationMobileForm frm = (HideFinanceOperationMobileForm)form;
		HideCardOperationOperation operation = createOperation(HideCardOperationOperation.class, "AddOperationsService");
		operation.initialize(frm.getId());
		operation.hide();
		operation.save();
		addLogParameters(new BeanLogParemetersReader("Данные скрытой сущности", operation.getEntity()));

		return mapping.findForward(HIDE_RESULT);
	}
	/**
	 * Возвращение видимости скрытой операции в мобильном приложении
	 * @param mapping маппинг
	 * @param form форма
	 * @param request реквест
	 * @param response респонс
	 * @return экшенфорвард
	 * @throws BusinessException, BusinessLogicException
	 */
	public ActionForward show(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException, BusinessLogicException
	{
		HideFinanceOperationMobileForm frm = (HideFinanceOperationMobileForm)form;
		HideCardOperationOperation operation = createOperation(HideCardOperationOperation.class, "AddOperationsService");
		operation.initialize(frm.getId());
		operation.show();
		operation.save();
		addLogParameters(new BeanLogParemetersReader("Данные сущности с возвращенной видимостью", operation.getEntity()));

		return mapping.findForward(SHOW_RESULT);
	}
}
