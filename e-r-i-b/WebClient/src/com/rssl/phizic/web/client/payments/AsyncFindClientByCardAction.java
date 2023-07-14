package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.payment.FindClientByCardOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.client.userprofile.addressbook.GetContactInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение информации о клиенте по номеру карты
 *
 * @author gladishev
 * @ created 16.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class AsyncFindClientByCardAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			GetContactInfoForm frm = (GetContactInfoForm) form;
			FindClientByCardOperation operation = createOperation(FindClientByCardOperation.class);
			if (StringHelper.isNotEmpty(frm.getCard()))
				operation.initialize(frm.getCard());
			else
			{
				log.error("Не указан номер карты.");
				return mapping.findForward(FORWARD_START);
			}

			frm.setUserInfo(operation.getResult());
		}
		catch (BusinessException ex)
		{
			log.error(ex);
		}

		return mapping.findForward(FORWARD_START);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
