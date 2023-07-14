package com.rssl.phizic.web.dictionaries.kbk;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.dictionaries.kbk.ReplicateKBKOperation;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 10.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateKBKAction extends OperationalActionBase
{
	protected static final String FORWARD_START  = "Start";
	protected static final String FORWARD_REPORT = "Report";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.replicate", "replicate");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation(ReplicateKBKOperation.class);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward replicate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReplicateKBKOperation operation = createOperation(ReplicateKBKOperation.class);
		ReplicateKBKForm frm = (ReplicateKBKForm) form;
		operation.initialize(frm.getFileImage().getInputStream());
		operation.replicate();
		frm.setRecordCount(operation.getRecordCount());
		frm.setWrongCount(operation.getWrongCount());
		frm.setErrors(operation.getErrors());
		return mapping.findForward(FORWARD_REPORT);
	}
}
