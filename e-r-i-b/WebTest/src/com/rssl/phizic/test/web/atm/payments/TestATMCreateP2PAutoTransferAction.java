package com.rssl.phizic.test.web.atm.payments;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Создание заявки на автоперевод
 *
 * @author khudyakov
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 */
public class TestATMCreateP2PAutoTransferAction extends TestATMDocumentAction
{
	private static final String FORWARD_INIT                        = "Init";
	private static final String FORWARD_EDIT_STEP_1                 = "Step1";
	private static final String FORWARD_EDIT_STEP_2                 = "Step2";
	private static final String FORWARD_CONFIRM                     = "Confirm";
	private static final String FORWARD_ERROR                       = "Error";

	private static final String INIT_OPERATION_NAME                 = "init";
	private static final String SAVE_OPERATION_NAME                 = "save";
	private static final String NEXT_OPERATION_NAME                 = "next";
	private static final String MAKE_LONG_OFFER_OPERATION_NAME      = "makeLongOffer";
	private static final String MAKE_AUTO_TRANSFER_OPERATION_NAME   = "makeAutoTransfer";


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		TestATMP2PAutoTransferClaimForm  frm = (TestATMP2PAutoTransferClaimForm) form;

		Long id = frm.getId();
		String operation = frm.getOperation();

		//шаг 0:
		if (INIT_OPERATION_NAME.equals(operation))
		{
			//инициализация нового автоперевода
			return init(mapping, frm);
		}

		//шаг 1: ввод источников списания/зачисления
		if (MAKE_LONG_OFFER_OPERATION_NAME.equals(operation))
		{
			return submitMakeLongOffer(mapping, frm);
		}

		//шаг 2: ввод периодичности
		if (SAVE_OPERATION_NAME.equals(operation) || NEXT_OPERATION_NAME.equals(operation))
		{
			return submitSaveAutoPayment(mapping, frm);
		}

		//быстрое создание по документу
		if (MAKE_AUTO_TRANSFER_OPERATION_NAME.equals(operation))
		{
			//1. инициализация нового автоперевода
			if (id == null || id == 0)
			{
				return init(mapping, frm);
			}

			//2. либо создание автоперевода по исходному документу, поддерживающему создание автоперевода
			return submitMakeAutoTransfer(mapping, frm);
		}

		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward init(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
		{
			return mapping.findForward(FORWARD_EDIT_STEP_1);
		}
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitMakeAutoTransfer(ActionMapping mapping, TestATMDocumentForm form)
	{
		form.setOperation(MAKE_AUTO_TRANSFER_OPERATION_NAME);
		final int result = send(form);
		if (result == 0)
		{
			return mapping.findForward(FORWARD_EDIT_STEP_2);
		}
		return mapping.findForward(FORWARD_ERROR);
	}

	private ActionForward submitMakeLongOffer(ActionMapping mapping, TestATMDocumentForm form)
	{
		final int result = send(form);
		if (result == 0)
		{
			return mapping.findForward(FORWARD_EDIT_STEP_2);
		}
		return mapping.findForward(FORWARD_EDIT_STEP_1);
	}

	private ActionForward submitSaveAutoPayment(ActionMapping mapping, TestATMDocumentForm form)
	{
		final int result = send(form);
		if (result != 0)
		{
			return mapping.findForward(FORWARD_EDIT_STEP_2);
		}
		return mapping.findForward(FORWARD_CONFIRM);
	}
}
