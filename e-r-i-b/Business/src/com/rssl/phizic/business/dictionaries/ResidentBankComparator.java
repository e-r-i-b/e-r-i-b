package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 25.10.2005
 * Time: 17:59:58
 */
@PublicDefaultCreatable
public class ResidentBankComparator extends AbstractCompatator
{
	public int compare ( Object o1, Object o2 )
	{
		ResidentBank bank1 = (ResidentBank)o1;
		ResidentBank bank2 = (ResidentBank)o2;

		if(!isObjectsEquals(bank1.getBIC(),bank2.getBIC()))
			return -1;

		if(!isObjectsEquals(bank1.getName(),bank2.getName()))
			return -1;

		if(!isObjectsEquals(bank1.getShortName(),bank2.getShortName()))
			return -1;

		if(!isObjectsEquals(bank1.getAccount(),bank2.getAccount()))
			return -1;

		if(!isObjectsEquals(bank1.getPlace(),bank2.getPlace()))
			return -1;

		if(!isObjectsEquals(bank1.isOur(),bank2.isOur()))
			return -1;
		if (!isObjectsEquals(bank1.getAddress(), bank2.getAddress()))
			return -1;
		if (!isObjectsEquals(bank1.getParticipantCode(), bank2.getParticipantCode()))
			return -1;
		if (!isObjectsEquals(bank1.getINN(), bank2.getINN()))
			return -1;
		if (!isObjectsEquals(bank1.getKPP(), bank2.getKPP()))
			return -1;
		if (!isObjectsEquals(bank1.getDateCh(), bank2.getDateCh()))
			return -1;

		return 0;

	}
}
