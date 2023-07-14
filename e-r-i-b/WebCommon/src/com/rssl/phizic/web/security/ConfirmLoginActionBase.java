package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.web.util.SkinHelper;
import org.apache.struts.action.ActionForm;

/**
 * @author Erkin
 * @ created 16.06.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class ConfirmLoginActionBase extends LoginStageActionSupport
{
	private static final SkinsService skinsService = new SkinsService();

	///////////////////////////////////////////////////////////////////////////

	protected String getSkinUrl(ActionForm form) throws BusinessException
	{
		AuthenticationContext context = getAuthenticationContext();
		// контекста может не быть, если пользователь был заблокирован в результате 3-х разового ввода неверного пароля с чека
		if(context != null)
		{
			CommonLogin login = context.getLogin();
			if (login != null)
			{
				Skin skin = skinsService.getPersonActiveSkin(login.getId());
				return SkinHelper.updateSkinPath(skin.getUrl());
			}
		}
		return super.getSkinUrl(form);
	}
}
