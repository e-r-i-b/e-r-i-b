package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.utils.ListTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Преобразователь результатов получения многоязычных данных.
 */
public class MultiLocaleResultTransformer implements ListTransformer
{
	public List transform(List input) throws SystemException
	{
		List resultList = new ArrayList(input.size());
		for(Object resultElement: input)
		{
			if(resultElement instanceof Object[] && ((Object[]) resultElement).length <= 2)
				resultList.add(((Object[])resultElement)[0]);
			else
				resultList.add(resultElement);
		}
		return resultList;
	}
}
