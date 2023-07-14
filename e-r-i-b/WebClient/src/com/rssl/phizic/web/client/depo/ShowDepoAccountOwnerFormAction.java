package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.depo.GetDepoAccountOwnerFormOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 13.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountOwnerFormAction    extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLAIM = "Claim";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws Exception
	{
		GetDepoAccountOwnerFormOperation op = (GetDepoAccountOwnerFormOperation) operation;
		ShowDepoAccountOwnerFormForm form = (ShowDepoAccountOwnerFormForm) frm;
		op.initialize(form.getLinkId());
		form.setForm(op.getEntity());
		form.setAccount(op.getDepoAccountLink());
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	 	ShowDepoAccountOwnerFormForm frm = (ShowDepoAccountOwnerFormForm) form;
		GetDepoAccountOwnerFormOperation operation = createOperation(GetDepoAccountOwnerFormOperation.class);

		updateFormData(operation, frm);

		if (frm.getForm() == null)
		{
			ActionForward forward = new ActionForward( mapping.findForward(FORWARD_CLAIM) );
			String path = forward.getPath();			
			forward.setPath(path + "&depoId="+frm.getAccount().getId());
			return forward;
		}
		return mapping.findForward(FORWARD_START);
	}
}
