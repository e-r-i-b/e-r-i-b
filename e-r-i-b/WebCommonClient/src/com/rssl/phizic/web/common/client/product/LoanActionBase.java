package com.rssl.phizic.web.common.client.product;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 09.06.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class LoanActionBase extends ListActionBase
{
	protected static final String FORWARD_NEXT = "Next";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map =   new HashMap<String, String>();
		map.put("button.next", "next");
		map.put("next", "next");
		return map;
	}

   protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
   {
	   return FormBuilder.EMPTY_FORM;
   }

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
		actionForward.setPath(actionForward.getPath() + getParamsURL(frm));
		return actionForward;
	}

	/**
	 * Название формы заявки, на которую будет сделан редирект
	 * @return
	 */
	public abstract String getFormNameForRedirect();

	protected ActionForward createActionForward(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		frm.setPaymentForm(getFormNameForRedirect());
		return super.createActionForward(frm);
	}

	/**
	 * Получить строку get-параметров
	 * @param frm - форма, с нее читаем данные для передачи
	 * @return  строка параметров
	 */
	protected String getParamsURL(LoanProductListForm frm)
	{
		return "?form="
				+ ((frm.getDoRedirectToLoanCardOffer() == null || frm.getDoRedirectToLoanCardOffer().equals(-1)) ? getFormNameForRedirect() : FormConstants.LOAN_CARD_OFFER_FORM)
				+ "&loan=" + frm.getLoanId()
				+ "&changeDate=" + currentRequest().getParameter("changeDate");
	}
}
