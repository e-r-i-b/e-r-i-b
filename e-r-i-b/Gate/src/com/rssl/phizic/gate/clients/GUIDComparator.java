package com.rssl.phizic.gate.clients;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.common.AbstractCompatator;
import org.apache.commons.lang.time.DateUtils;

/**
 * Компаратор уникальных идентификаторов клиента
 *
 * @author khudyakov
 * @ created 12.11.14
 * @ $Author$
 * @ $Revision$
 */
public class GUIDComparator extends AbstractCompatator<GUID>
{
	public int compare(GUID o1, GUID o2)
	{
		if (!isObjectsEquals(o1.getSurName().toUpperCase(), o2.getSurName().toUpperCase()))
		{
			return -1;
		}
		if (!isObjectsEquals(o1.getFirstName().toUpperCase(), o2.getFirstName().toUpperCase()))
		{
			return -1;
		}
		if (!isObjectsEquals(o1.getPatrName() != null ? o1.getPatrName().toUpperCase() : null, o2.getPatrName() != null ? o2.getPatrName().toUpperCase() : null))
		{
			return -1;
		}
		if (!DateUtils.isSameDay(o1.getBirthDay(), o2.getBirthDay()))
		{
			return -1;
		}
		if (!isObjectsEquals(o1.getPassport().replace( " ", "" ), o2.getPassport().replace( " ", "" )))
		{
			return -1;
		}
		if (!TBSynonymsDictionary.isSameTB(o1.getTb(), o2.getTb()))
		{
			return -1;
		}
		return 0;
	}
}
