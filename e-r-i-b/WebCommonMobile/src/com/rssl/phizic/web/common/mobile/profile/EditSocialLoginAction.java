package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сохранение данных в профиле ЕРИБ
 * @author Jatsky
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditSocialLoginAction extends OperationalActionBase
{
	private static final ProfileService profileService = new ProfileService();
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditSocialLoginForm frm = (EditSocialLoginForm) form;
		if (!mobilePlatformService.getSocialPlatformIds().contains(AuthenticationContext.getContext().getDeviceInfo()))
			throw new BusinessException("Доступно только для социальных приложений");
		if (!"login".equals(frm.getFieldName()))
			throw new BusinessException("Можно изменить только логин клиента во внешнем приложении");
		if (frm.getFieldValue() == null || frm.getFieldValue().length() > 50)
			throw new BusinessException("неверно задано значение атрибута");
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		profileService.setSocialId(personData.getLogin(), AuthenticationContext.getContext().getDeviceInfo(), frm.getFieldValue());
		return mapping.findForward(FORWARD_START);
	}
}
