package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.operations.ima.IMAOpeningLicenseOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningLicenseAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
    {
	    return new HashMap<String, String>();
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		IMAOpeningLicenseForm frm = (IMAOpeningLicenseForm) form;
		IMAOpeningLicenseOperation openingLicenseOperation = getIMAOpeningLicenseOperation();
		openingLicenseOperation.initialize(frm.getImaProductId(), frm.getDocumentId());
		frm.setContractTemplate(openingLicenseOperation.getConvertContractTemplate());
		return mapping.findForward(FORWARD_START);
	}

	protected IMAOpeningLicenseOperation getIMAOpeningLicenseOperation() throws BusinessException
	{
		return createOperation(IMAOpeningLicenseOperation.class, "IMAOpeningClaim");
	}
}
