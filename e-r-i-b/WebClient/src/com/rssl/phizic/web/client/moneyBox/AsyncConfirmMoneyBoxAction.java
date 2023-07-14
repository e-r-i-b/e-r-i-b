package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.ChecksOwnersPaymentValidator;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.ConfirmDocumentAction;
import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 * Асинхронный экшен подтверждения создания копилки.
 */
public class AsyncConfirmMoneyBoxAction extends ConfirmDocumentAction
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.confirmMoneyBox", "confirm");
		map.remove("button.makeLongOffer");
		map.remove("button.edit");
		map.remove("button.preConfirm");
	    map.remove("button.confirmCard");
	    map.remove("confirmBySelectedCard");
	    map.remove("button.confirmSMS");
	    map.remove("button.confirmCap");
	    map.remove("button.confirmPush");
	    map.remove("button.remove");
	    map.remove("button.nextStage");

		return map;
	}

	public ActionForward makeLongOffer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			ConfirmPaymentByFormForm frm = (ConfirmPaymentByFormForm) form;
			//подтверждение существующей копилки в статусе "Черновик"
			if (frm.getId() != null && frm.getId() != 0)
			{
				ExistingSource source = new ExistingSource(frm.getId(), new ChecksOwnersPaymentValidator());
				ConfirmFormPaymentOperation operation = createConfirmOperation(source);
				operation.initialize(source);
				operation.confirm();
				if(getErrors(request).isEmpty())
					saveMessage(request, StrutsUtils.getMessage("moneyBox.confirm.message.success", "moneyboxBundle"));
				return getCurrentMapping().findForward("Success");
			}
			//подтверждение создаваемой копилки.
			ActionForward forward = super.confirm(mapping, form, request, response);
			if(getErrors(request).isEmpty())
				saveMessage(request, StrutsUtils.getMessage("moneyBox.create.message.success", "moneyboxBundle"));
			return forward;
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			return mapping.findForward("Failed");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			saveError(request, ExceptionSettingsService.getDefaultMessageForApplication(Application.PhizIC));
			return mapping.findForward("Failed");
		}
	}

	protected ActionForward forwardShow(ActionMapping mapping, ConfirmFormPaymentOperation operation, ConfirmPaymentByFormForm frm, HttpServletRequest request, boolean sendRequest) throws BusinessException, BusinessLogicException
	{
		forwardShow(mapping, operation, frm, request, sendRequest, true);
		return mapping.findForward("Failed");
	}

	protected ActionForward createNextStageDocumentForward(ConfirmFormPaymentOperation operation, boolean backToEdit) throws BusinessException
	{
		return getCurrentMapping().findForward("Success");
	}
}
