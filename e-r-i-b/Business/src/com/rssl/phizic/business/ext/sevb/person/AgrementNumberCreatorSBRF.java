package com.rssl.phizic.business.ext.sevb.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.persons.AgrementNumberCreator;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 19.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class AgrementNumberCreatorSBRF implements AgrementNumberCreator
{
	private static final CounterService counterService = new CounterService();

	private Department department;

	public void init(Department department)
	{
		this.department = department;
	}

	public String getNextAgreementNumber() throws BusinessException
	{
		try
		{
			Map<String, String> codeFields = department.getCode().getFields();
			StringBuilder builder = new StringBuilder();
			builder.append('E');
			builder.append(StringHelper.appendLeadingZeros(codeFields.get("branch"), 4));
			builder.append(StringHelper.appendLeadingZeros(codeFields.get("office"), 5));
			Long next = counterService.getNext(Counter.createSimpleCounter(builder.toString()));
			builder.append(StringHelper.appendLeadingZeros(next.toString(), 5));
			return builder.toString();
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}
}
