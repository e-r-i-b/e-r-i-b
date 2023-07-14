package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.mobileoperators.ShowMobileOperatorsOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowMobileOperatorsListAction extends AsyncOperationalActionBase
{
	protected static final String FORWARD_START = "Start";
	
	protected  ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ShowMobileOperatorsOperation", "VirtualCardClaim");
	}

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.emptyMap();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		ShowMobileOperatorsOperation operation = (ShowMobileOperatorsOperation) createListOperation((ListFormBase) form);
		operation.init();
		frm.setData(operation.getMobileOperators());

		return mapping.findForward(FORWARD_START);
	}
}
