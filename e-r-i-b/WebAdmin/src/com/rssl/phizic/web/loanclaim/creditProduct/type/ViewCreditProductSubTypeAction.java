package com.rssl.phizic.web.loanclaim.creditProduct.type;

import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type.ViewCreditProductSubTypeOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Moshenko
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 * ѕросмотр суб кодов кредитного продукта.
 */
public class ViewCreditProductSubTypeAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewCreditProductSubTypeForm frm = (ViewCreditProductSubTypeForm) form;
		ViewCreditProductSubTypeOperation op = createOperation(ViewCreditProductSubTypeOperation.class);
		op.initialize(frm.getId());
		frm.setTbNumbers(op.getTerbanksNumbers());
		frm.setCreditSubProductTypes(op.getEntity().getCreditSubProductTypes());
		return mapping.findForward(FORWARD_START);
	}
}
