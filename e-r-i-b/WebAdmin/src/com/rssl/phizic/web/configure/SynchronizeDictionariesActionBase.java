package com.rssl.phizic.web.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.dictionaries.SynchronizeDictionariesOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roshka
 * @ created 23.11.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class SynchronizeDictionariesActionBase extends ViewActionBase
{
	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.loadDictionaries", "load");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		return createOperation(SynchronizeDictionariesOperation.class);
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
	{
		SynchronizeDictionariesForm      frm = (SynchronizeDictionariesForm)form;
		SynchronizeDictionariesOperation op  = (SynchronizeDictionariesOperation) operation;

		frm.setDescriptors(op.getEntity());
	}

	public abstract ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                          HttpServletResponse response) throws Exception;
}