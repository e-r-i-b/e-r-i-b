package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.common.types.SegmentCodeType;

/**
 * @author mihaylov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Рестрикшен для поиска клиентов с заданными статусами с учетом департамента
 */
public class SimpleClientDepartmentRestriction extends CompositeClientRestricnion
{
	private static final SegmentCodeType[] SIMPLE_ACCESS = new SegmentCodeType[]{SegmentCodeType.NOTEXISTS, SegmentCodeType.MVCPOTENTIAL, SegmentCodeType.MVC};

	/**
	 * Конструктор
	 */
	public SimpleClientDepartmentRestriction()
	{
		super(new ClientDepartmentRestriction(), new ClientSegmentRestricnion(SIMPLE_ACCESS));
	}
}
