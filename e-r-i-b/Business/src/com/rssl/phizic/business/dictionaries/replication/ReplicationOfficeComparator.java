package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.business.dictionaries.offices.OfficesComparator;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;

/**
 * @author Krenev
 * @ created 12.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class ReplicationOfficeComparator extends OfficesComparator
{
	protected boolean isCodeEquals(Code code1, Code code2)
	{
		return super.isCodeEquals(new CodeImpl(code1), new CodeImpl(code2));
	}
}
