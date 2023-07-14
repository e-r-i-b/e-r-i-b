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
public class VIPClientDepartmentRestriction extends CompositeClientRestricnion
{
	private static final SegmentCodeType[] VIP_ACCESS = new SegmentCodeType[]{SegmentCodeType.NOTEXISTS, SegmentCodeType.MVCPOTENTIAL, SegmentCodeType.MVC, SegmentCodeType.VIP};

	/**
	 * Конструктор
	 */
	public VIPClientDepartmentRestriction()
	{
		super(new ClientDepartmentRestriction(), new ClientSegmentRestricnion(VIP_ACCESS));
	}
}
