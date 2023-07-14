package com.rssl.phizic.web.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.news.ListAllowedTerbanksOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListAllowedTerbanksAction extends OperationalActionBase
{

	private ListAllowedTerbanksOperation createListOperation() throws BusinessException
	{
		if(checkAccess(ListAllowedTerbanksOperation.class, "NewsManagment"))
			return createOperation(ListAllowedTerbanksOperation.class, "NewsManagment");
		if(checkAccess(ListAllowedTerbanksOperation.class, "AdvertisingBlockManagment"))
			return createOperation(ListAllowedTerbanksOperation.class, "AdvertisingBlockManagment");
		if(checkAccess(ListAllowedTerbanksOperation.class, "OfferNotificationManagment"))
			return createOperation(ListAllowedTerbanksOperation.class, "OfferNotificationManagment");
		if(checkAccess(ListAllowedTerbanksOperation.class, "SeachClientsByTB"))
			return createOperation(ListAllowedTerbanksOperation.class, "SeachClientsByTB");
		if(checkAccess(ListAllowedTerbanksOperation.class, "ExternalSystemSettingsManagement"))
			return createOperation(ListAllowedTerbanksOperation.class, "ExternalSystemSettingsManagement");
		if(checkAccess(ListAllowedTerbanksOperation.class, "CalendarManagement"))
			return createOperation(ListAllowedTerbanksOperation.class, "CalendarManagement");
		if(checkAccess(ListAllowedTerbanksOperation.class, "ErmbProfileManagment"))
			return createOperation(ListAllowedTerbanksOperation.class, "ErmbProfileManagment");
		if(checkAccess(ListAllowedTerbanksOperation.class, "Monitoring"))
			return createOperation(ListAllowedTerbanksOperation.class, "Monitoring");
		return createOperation(ListAllowedTerbanksOperation.class, "ContactCenterAreaManagment");
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListAllowedTerbanksForm frm = (ListAllowedTerbanksForm) form;
		ListAllowedTerbanksOperation operation = createListOperation();
		frm.setTerbanks(operation.getTerbanks());
		return mapping.findForward(FORWARD_START);
	}

}
