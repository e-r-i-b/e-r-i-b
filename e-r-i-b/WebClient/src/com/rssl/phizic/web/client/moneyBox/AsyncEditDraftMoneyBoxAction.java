package com.rssl.phizic.web.client.moneyBox;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateMoneyBoxOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 * Экшен для редактирования заявки в статусе черновик с формы просмотра детальной информации по копилке.
 */
public class AsyncEditDraftMoneyBoxAction extends AsyncCreateMoneyBoxAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.edit");
		map.remove("button.remove");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return editPayment(mapping, form, request, response);
	}

	protected void editSave(CreateMoneyBoxOperation operation) throws BusinessLogicException, BusinessException
	{
		operation.editDraftClaim();
	}

	protected void updateForm(CreatePaymentForm frm, EditDocumentOperation operation, FieldValuesSource valuesSource) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, operation,valuesSource);
		frm.setType("editDraftClaim");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = super.save(mapping, form, request, response);
		if(getErrors(request).isEmpty())
			saveMessage(request, StrutsUtils.getMessage("moneyBox.edit.message.success", "moneyboxBundle"));
		return forward;
	}
}
