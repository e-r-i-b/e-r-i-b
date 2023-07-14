package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.operations.basket.EditBasketIdentifierOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшн просмотра идентфикаторов корзины по подразеделени€м.
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewBasketIdentTypesForDepAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBasketIdentifierOperation op = createOperation(EditBasketIdentifierOperation.class);
		ViewBasketIdentTypesForDepForm frm = (ViewBasketIdentTypesForDepForm) form;

		frm.setIdentifiers(op.getAllIdentifiersNames());

		return mapping.findForward(FORWARD_START);

	}
}
