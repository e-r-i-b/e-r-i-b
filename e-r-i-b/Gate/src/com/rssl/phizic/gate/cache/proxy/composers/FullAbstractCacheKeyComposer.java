package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class FullAbstractCacheKeyComposer  extends ObjectToEntityCachKeyComposer
{
	protected static final String SEPARATOR = "|";


	public String getKey(Object[] args, String params)
	{
		StringBuilder key = new StringBuilder();
		key.append(super.getKey(args,params));
		//учитываем в ключе даты
		key.append( FullAbstractCacheKeyComposer.SEPARATOR );
		if(args[1]!=null)
			key.append( DateHelper.toXMLDateFormat( ((Calendar)args[1]).getTime() ));

		key.append( FullAbstractCacheKeyComposer.SEPARATOR );

		if(args[2]!=null)
			key.append( DateHelper.toXMLDateFormat( ((Calendar)args[2]).getTime() ));

		key.append( FullAbstractCacheKeyComposer.SEPARATOR );

		if(args.length>3 && args[3]!=null)
			key.append(args[3]);

		return key.toString();
	}

}
