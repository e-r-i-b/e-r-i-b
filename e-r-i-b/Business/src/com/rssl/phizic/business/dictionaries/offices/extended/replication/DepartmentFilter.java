package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.List;

/**
 * Фильтр подразделений
 * @author niculichev
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentFilter
{
	private List<Code> codes;

	public DepartmentFilter(List<Code> codes)
	{
		this.codes = codes;
	}

	public boolean filter(Code filterCode)
	{
		if(codes == null)
			return true;

		for(Code code : codes)
		{
			// оборачиваем для удобства
			ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(filterCode);

			if(code.equals(extendedCode))
				return true;

			extendedCode.setOffice(null);
			if(code.equals(extendedCode))
				return true;

			extendedCode.setBranch(null);
			if(code.equals(extendedCode))
				return true;
		}

		return false;
	}
}
