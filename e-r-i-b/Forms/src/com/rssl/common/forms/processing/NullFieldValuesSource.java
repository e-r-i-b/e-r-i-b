package com.rssl.common.forms.processing;

import java.util.Collections;
import java.util.Map;

/**
 * @author Krenev
 * @ created 01.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class NullFieldValuesSource implements FieldValuesSource
{
	public String getValue(String name)
	{
		return null;
	}

	public Map<String, String> getAllValues()
	{
		return Collections.emptyMap();
	}

	public boolean isChanged(String name)
	{
		return false; 
	}

	public boolean isEmpty()
	{
		return true;
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
