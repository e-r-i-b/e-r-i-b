package com.rssl.phizic.business.mail;

import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * @author Krenev
 * @ created 31.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class NeedReadMailVerifier  implements StageVerifier
{
	/**
	 * @param context контекст аутентификации
	 * @param stage стадия аутентификации
	 * @return true == стадия необходима в этом контексте
	 */
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		try
		{
			List<Mail> mails = new MailService().getNewImportantClientMails(context.getLogin());
			if (mails == null)
			{
				return false;
			}
			return mails.size() > 0;
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}
