package com.rssl.phizic.test.web.atm.payments;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * «а€вка на возобновление/приостановка/закрытие автоперевода
 *
 * @author khudyakov
 * @ created 01.03.15
 * @ $Author$
 * @ $Revision$
 */
public class TestATMChangeP2PAutoTransferAction extends TestATMDocumentAction
{	private static final String FORWARD_INIT                    = "Init";
	private static final String FORWARD_EDIT                    = "Edit";
	private static final String FORWARD_CONFIRM                 = "Confirm";

	private static final String INIT_OPERATION_NAME             = "init";
	private static final String SAVE_OPERATION_NAME             = "save";
	private static final String NEXT_OPERATION_NAME             = "next";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		TestATMP2PAutoTransferClaimForm  frm = (TestATMP2PAutoTransferClaimForm) form;

		String operation = frm.getOperation();

		//шаг 0:
		if (INIT_OPERATION_NAME.equals(operation))
		{
			return init(mapping, frm);
		}

		//шаг 2: ввод сохранение
		if (SAVE_OPERATION_NAME.equals(operation) || NEXT_OPERATION_NAME.equals(operation))
		{
			return submitSaveAutoPayment(mapping, frm);
		}

		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward init(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
		{
			return mapping.findForward(FORWARD_EDIT);
		}
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitSaveAutoPayment(ActionMapping mapping, TestATMDocumentForm form)
	{
		final int result = send(form);
		if (result != 0)
		{
			return mapping.findForward(FORWARD_EDIT);
		}
		return mapping.findForward(FORWARD_CONFIRM);
	}
}
