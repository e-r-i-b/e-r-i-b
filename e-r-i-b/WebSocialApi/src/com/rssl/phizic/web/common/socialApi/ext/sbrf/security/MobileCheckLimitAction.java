package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.operations.limits.MobileCheckLimitOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ѕроверка баланса мобильного кошелька
 * @author Pankin
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileCheckLimitAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(1);
		keyMethodMap.put("check", "start");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CheckLimitForm frm = (CheckLimitForm) form;
		MobileCheckLimitOperation operation = createOperation();

		updateForm(frm, operation);
		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(CheckLimitForm frm, MobileCheckLimitOperation operation) throws BusinessException, BusinessLogicException
	{
		frm.setTotalAmount(((Profile) operation.getEntity()).getMobileWallet());
	}

	private MobileCheckLimitOperation createOperation() throws BusinessException
	{
		MobileCheckLimitOperation operation = createOperation(MobileCheckLimitOperation.class);
		operation.initialize();
		return operation;
	}
}
