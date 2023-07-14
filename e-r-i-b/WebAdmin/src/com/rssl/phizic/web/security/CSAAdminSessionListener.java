package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.operations.csaadmin.auth.CloseCSAAdminSessionOperation;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.common.HttpSessionStore;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import static com.rssl.phizic.common.types.security.Constants.AUTHENTICATION_CONTEXT_KEY;

/**
 * @author mihaylov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Листенер закрывающий сессию в ЦСА Админ при закрытии сессии в блоке
 */
public class CSAAdminSessionListener extends BaseCloseSessionListener
{

	public static final String DONT_CLOSE_CSA_ADMIN_SESSION_KEY = "dontCloseCsaAdminSessionKey";

	public void sessionCreated(HttpSessionEvent httpSessionEvent)
	{
	}

	@Override protected void doSessionDestroyed(HttpSessionEvent httpSessionEvent)
	{
		HttpSession session = httpSessionEvent.getSession();
		Store prevStore = StoreManager.getCurrentStore();
		StoreManager.setCurrentStore(new HttpSessionStore(session));
		try
		{
			CSAAdminGateConfig csaAdminConfig = ConfigFactory.getConfig(CSAAdminGateConfig.class);
			//если приложение работает не в многоблочном режиме, то ничего не делаем
			if(!csaAdminConfig.isMultiBlockMode())
				return;

			if(session == null)
				return;

			AuthenticationContext context = (AuthenticationContext) session.getAttribute(AUTHENTICATION_CONTEXT_KEY);
			if(context != null)
			{
				String sid = context.getCSA_SID();
				if(StringUtils.isNotEmpty(sid) && !BooleanUtils.isTrue((Boolean)session.getAttribute(DONT_CLOSE_CSA_ADMIN_SESSION_KEY)))
					new CloseCSAAdminSessionOperation().close();
			}
		}
		finally
		{
			StoreManager.setCurrentStore(prevStore);
		}
	}
}
