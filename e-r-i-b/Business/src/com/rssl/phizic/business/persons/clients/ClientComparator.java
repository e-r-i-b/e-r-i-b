package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import org.apache.commons.lang.time.DateUtils;

/**
 *  омпаратор клиентов.
 *
 * ќграничение: при сравнении документов клиентов используетс€ паспорт way,
 * которого в сущности client при пр€мом получении из внешней системы может не быть
 *
 * @author khudyakov
 * @ created 08.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientComparator extends AbstractCompatator<Client>
{
	public int compare(Client ts, Client that)
	{
		if (!isObjectsEquals(ts.getFirstName(), that.getFirstName()))
		{
			return -1;
		}
		if (!isObjectsEquals(ts.getSurName(), that.getSurName()))
		{
			return -1;
		}
		if (!isObjectsEquals(ts.getPatrName(), that.getPatrName()))
		{
			return -1;
		}
		if (!DateUtils.isSameDay(ts.getBirthDay(), that.getBirthDay()))
		{
			return -1;
		}

		if (!TBSynonymsDictionary.isSameTB(new SBRFOfficeCodeAdapter(ts.getOffice().getCode()).getRegion(), new SBRFOfficeCodeAdapter(that.getOffice().getCode()).getRegion()))
		{
			return -1;
		}

		try
		{
			if (!(new ClientDocumentComparator().compare(ClientHelper.getClientWayDocument(ts), ClientHelper.getClientWayDocument(that)) == 0))
			{
				return -1;
			}
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}

		return 0;
	}
}
