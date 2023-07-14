package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.clients.ClientDocument;

/**
 * Компаратор документов клиента
 *
 * @author khudyakov
 * @ created 08.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientDocumentComparator extends AbstractCompatator<ClientDocument>
{
	public int compare(ClientDocument ts, ClientDocument that)
	{
		if (!isObjectsEquals(ts.getDocumentType(), that.getDocumentType()))
		{
			return -1;
		}
		if (!isObjectsEquals(ts.getDocNumber(), that.getDocNumber()))
		{
			return -1;
		}
		if (!isObjectsEquals(ts.getDocSeries(), that.getDocSeries()))
		{
			return -1;
		}

		return 0;
	}
}
