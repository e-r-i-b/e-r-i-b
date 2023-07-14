package com.rssl.phizic.web.client.autosubscriptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateFreeDetailAutoSubOperation;
import com.rssl.phizic.web.actions.payments.forms.CreateFreeDetailAutoSubForm;
import com.rssl.phizic.web.struts.forms.ActionMessagesManager;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.MapUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClientFreeDetatilAutoSubRecipientAction extends ClientFreeDetatilAutoSubAction
{
	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return next(mapping, form, request, response);
	}

	protected ActionForward findRecipients(CreateFreeDetailAutoSubForm frm, CreateFreeDetailAutoSubOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		//ищем ѕ” как дл€ оплаты по свободным реквизитам
		List<Object[]> recipients = operation.findRecipients();

		if (recipients.size() == 1)
		{
			Object[] prov = recipients.get(0);
			//если поставщик один и не из региона пользовател€-отображаем всплывающее окно с выбором
			if (prov[11].equals("allRegion"))
			{
				frm.setServiceProviders(recipients);
				return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
			}
			frm.setNextURL(makeRecipientAutoSubscription(Long.valueOf((String)prov[6]), frm.getFromResource()).getPath());
			return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		}
		if (recipients.size() > 1)
		{
			frm.setProviderRegions(operation.getRegions());
			frm.setServiceProviders(recipients);
			return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
		}
		frm.setNextURL(doPrepareExternalProviderPayment(operation, frm, request).getPath());
		return getCurrentMapping().findForward(FORWARD_SHOW_FORM);
	}

	protected void saveFormErrors(HttpServletRequest request,CreateFreeDetailAutoSubForm frm, ActionMessages errors)
	{
		saveErrors(request, errors); // сохран€ем ошибки валидации

		List<String> validationErrors = ActionMessagesManager.getFormValidationError(
				ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR);
		String errorText = StringUtils.join(validationErrors,'|');
		frm.setField("error",errorText);

		if (StringHelper.isEmpty(errorText))
		{
			Map<String, String> validationMessages = ActionMessagesManager.getFieldsValidationError(
					ActionMessagesManager.COMMON_BUNDLE, ActionMessagesManager.ERROR);
			String messagesText = MapUtil.format(validationMessages, "=", "|");
			frm.setField("messages", messagesText);
		}
	}
}
