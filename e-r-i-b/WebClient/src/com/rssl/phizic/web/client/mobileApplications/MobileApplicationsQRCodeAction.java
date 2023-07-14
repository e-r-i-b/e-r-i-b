package com.rssl.phizic.web.client.mobileApplications;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Gololobov
 * @ created: 05.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileApplicationsQRCodeAction extends OperationalActionBase
{
	//URL к картинке QR-кодов
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApplicationsQRCodeForm frm = (MobileApplicationsQRCodeForm) form;
		if (StringHelper.isNotEmpty(frm.getMobileApplication()))
		{
			MobilePlatform platform = mobilePlatformService.findByPlatformId(frm.getMobileApplication());
			if(platform == null)
			{
				throw new BusinessLogicException("Данная платформа не поддерживается");
			}
			frm.setMobilePlatformName(platform.getPlatformName());
			frm.setMobileApplicationDistribURL(platform.getExternalURL());
			frm.setMobileApplicationQRImgName(platform.getQrName());
		}
		return mapping.findForward(FORWARD_SHOW);
	}
}
