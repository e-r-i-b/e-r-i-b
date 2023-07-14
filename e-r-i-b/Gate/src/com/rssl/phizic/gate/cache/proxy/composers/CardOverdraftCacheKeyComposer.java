package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.utils.DateHelper;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

//на входе карта и дата
public class CardOverdraftCacheKeyComposer extends CardCacheKeyComposer
{
	private static final String SEPARATOR = "|";
	public String getKey(Object[] args, String params)
	{
		StringBuilder builder= new StringBuilder();
		builder.append(super.getKey(args, params));
		builder.append(SEPARATOR);
		if(args[0]!=null)
		{
			Calendar date = (Calendar)args[0];
			builder.append(DateHelper.toXMLDateFormat(date.getTime()));
		}
		return builder.toString();
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		return null;
	}

	public boolean isKeyFromResultSupported()
	{
		return false;
	}
}
