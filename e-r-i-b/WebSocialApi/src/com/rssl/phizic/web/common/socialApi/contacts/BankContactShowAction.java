package com.rssl.phizic.web.common.socialApi.contacts;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.operations.contacts.ContactSyncOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Необходимо ли отображать клиентов сбербанка в мобильном приложении
 * @author bogdanov
 * @ created 17.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class BankContactShowAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BankContactShowForm frm = (BankContactShowForm) form;

		MobilePlatform platform = ContactSyncOperation.mobilePlatformService.findByPlatformId(AuthenticationContext.getContext().getDeviceInfo());
		frm.setNeedShowBankClient(platform != null && platform.isShowSbAttribute());

		return mapping.findForward(FORWARD_START);
	}
}
