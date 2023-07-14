package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author Krenev
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
public interface LogParametersReader
{
	public LinkedHashMap read() throws Exception;

	String getDescription();
}
