package com.rssl.phizic.web.erkcemployee.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.mail.MailManagerAction;

/**
 * @author akrenev
 * @ created 27.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * экшен перехода к письмам для сотрудника ЕРКЦ
 */

public class ERKCMailManagerAction extends MailManagerAction
{
	@Override
	protected Long getViewMailId(Long mailId) throws BusinessException
	{
		return mailId;
	}
}
