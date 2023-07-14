package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author Pankin
 @ created 23.09.2010
 @ $Author$
 @ $Revision$
 */
public class PrintCardInfoAction extends OperationalActionBase
{
	private static final String FORWARD_PRINT = "print";
	private static final String FORWARD_ABSTRACT_PRINT = "printAbstract";
	private static final Long MAX_COUNT_OF_TRANSACTIONS = 10L;

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetCardInfoOperation operation = createOperation(GetCardInfoOperation.class);

		PrintCardInfoForm frm = (PrintCardInfoForm) form;

		operation.initialize(frm.getId());
		frm.setCardLink(operation.getEntity());
		if (frm.isPrintAbstract())
		{
			frm.setCardAbstract(getCardAbstract(frm.getId()));
			return mapping.findForward(FORWARD_ABSTRACT_PRINT);
		}

		return mapping.findForward(FORWARD_PRINT);
	}

	private CardAbstract getCardAbstract(Long cardId) throws BusinessException, BusinessLogicException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(cardId);
		CardAbstract cardAbstract = operation.getCardAbstract(MAX_COUNT_OF_TRANSACTIONS).get(operation.getCard());
		return cardAbstract;
	}
}
