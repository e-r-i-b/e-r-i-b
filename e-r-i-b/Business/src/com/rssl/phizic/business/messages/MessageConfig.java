package com.rssl.phizic.business.messages;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Roshka
 * @ created 09.11.2005
 * @ $Author: komarov $
 * @ $Revision$
 */
public interface MessageConfig
{
    String message(String bundle, String key);
	String message(String bundle, String key, Object[] args);
}
