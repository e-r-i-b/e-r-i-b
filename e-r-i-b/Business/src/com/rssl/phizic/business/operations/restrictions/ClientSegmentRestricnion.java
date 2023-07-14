package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.common.types.SegmentCodeType;
import org.apache.commons.lang.ArrayUtils;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;

/**
 * @author akrenev
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * рестрикшен, провер€ющий клиента на соответствие сегменту
 */
public class ClientSegmentRestricnion implements ClientRestriction
{
	private final SegmentCodeType[] segments;

	/**
	 * @param segments разрешенные сегменты
	 */
	public ClientSegmentRestricnion(SegmentCodeType... segments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.segments = segments;
	}

	public boolean accept(Client client)
	{
		return ArrayUtils.contains(segments, SegmentHelper.getSegmentCodeType(client.getSegmentCodeType()));
	}
}
