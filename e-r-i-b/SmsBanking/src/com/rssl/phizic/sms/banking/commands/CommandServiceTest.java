package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.sms.banking.security.UserSendException;

/**
 * @author hudyakov
 * @ created 01.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CommandServiceTest extends BusinessTestCaseBase
{
	public void testCS() throws BusinessException, UserSendException
	{
		CommandService cs = new CommandService();
		cs.getCommand("?"); 
	}
}
