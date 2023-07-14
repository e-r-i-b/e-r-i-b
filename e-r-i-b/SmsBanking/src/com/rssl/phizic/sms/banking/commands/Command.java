package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.smsbanking.pseudonyms.NullPseudonymException;
import com.rssl.phizic.sms.banking.security.UserSendException;

import java.util.Map;

/**
 * @author hudyakov
 * @ created 30.10.2008
 * @ $Author$
 * @ $Revision$
 */
public interface Command
{
	void initialize(String message);

	String execute() throws BusinessException, BusinessLogicException, UserSendException, NullPseudonymException;

	String getHelp();

	void setHelp(String help);

	void setParameters(Map<String, String> parameters);

	void setExceptions(Map<String, String> exceptions);
}
