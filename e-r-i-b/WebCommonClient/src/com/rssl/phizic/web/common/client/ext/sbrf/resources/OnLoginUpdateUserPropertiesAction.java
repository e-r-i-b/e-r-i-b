package com.rssl.phizic.web.common.client.ext.sbrf.resources;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * @author tisov
 * @ created 29.08.14
 * @ $Author$
 * @ $Revision$
 * Обновление настроек клиента при входе в приложение
 */

public class OnLoginUpdateUserPropertiesAction implements AthenticationCompleteAction
{
	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		if (PermissionUtil.impliesServiceRigid(FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM.getName()))
		{
			UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
			config.increaseP2PPromoShownTimes();
			config.increaseP2PNewMarkShownTimes();
		}
	}
}
