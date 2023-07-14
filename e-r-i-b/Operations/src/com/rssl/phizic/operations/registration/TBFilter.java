package com.rssl.phizic.operations.registration;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Фильтр отсекает ТБ, не содержащиеся в параметре
 * @author koptyaev
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class TBFilter implements Predicate
{
	private List<String> terbanks = new ArrayList<String>(0);

	/**
	 * Конструктор
	 * @param tbs список номеров ТБ с разделителем ";"
	 */
	public TBFilter(String tbs)
	{
		if (StringHelper.isNotEmpty(tbs))
			this.terbanks = Arrays.asList(tbs.split(";"));
	}

	public boolean evaluate(Object object)
	{
		Department tb = (Department)object;
		if (terbanks.contains(tb.getCode().getFields().get("region")))
			return true;
		return false;
	}
}
