package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author egorova
 * @ created 26.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedOfficesInfoComparator extends AbstractCompatator
{
	public int compare ( Object o1, Object o2 )
	{
		SBRFOfficeAdapter office1 = new SBRFOfficeAdapter((Office)o1);
		SBRFOfficeAdapter office2 = new SBRFOfficeAdapter((Office)o2);

		if (compareToNull(office1,office1)!=9)
			return compareToNull(office1,office1);

		String BIC1 = StringHelper.getNullIfEmpty(office1.getBIC());
		String address1 = StringHelper.getNullIfEmpty(office1.getAddress());
		String telephone1 = StringHelper.getNullIfEmpty(office1.getTelephone());
		boolean isCreditCardOffice1 = office1.isCreditCardOffice();
		boolean isNeedUpdateCreditCardOffice1 = office1.isNeedUpdateCreditCardOffice();
		boolean isOpenIMAOffice1 = office1.isOpenIMAOffice();

		String BIC2 = StringHelper.getNullIfEmpty(office2.getBIC());
		String address2 = StringHelper.getNullIfEmpty(office2.getAddress());
		String telephone2 = StringHelper.getNullIfEmpty(office2.getTelephone());
		boolean isCreditCardOffice2 = office2.isCreditCardOffice();
		boolean isNeedUpdateCreditCardOffice2 = office2.isNeedUpdateCreditCardOffice();
		boolean isOpenIMAOffice2 = office2.isOpenIMAOffice();

		//т.к. нужно для репликации, если они на равны, то апдейтим офис. не вижу смысла решать какой больше
		if (BIC1 != null ? !BIC1.equals(BIC2) : BIC2 != null) return -1;
		if (address1 != null ? !address1.equals(address2) : address2 != null) return -1;
		if (office1.getCode() != null ? !office1.getCode().equals(office2.getCode()) : office2.getCode() != null) return -1;
		if (isNeedUpdateCreditCardOffice1 || isNeedUpdateCreditCardOffice2) isCreditCardOffice1 = isCreditCardOffice2;
		if ( isCreditCardOffice1 != isCreditCardOffice2 ) return -1;
		//не понятно
		//if (sbidnt != null ? !sbidnt.equals(that.sbidnt) : that.sbidnt != null) return false;
		if (office1.getSynchKey() != null ? !office1.getSynchKey().equals(office2.getSynchKey()) : office2.getSynchKey() != null) return -1;
		if (telephone1 != null ? !telephone1.equals(telephone2) : telephone2 != null) return -1;
		if (isOpenIMAOffice1 != isOpenIMAOffice2) return -1;

		return 0;
	}

	private int compareToNull(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null && o2 != null)
		{
			return -1;
		}
		if (o1 != null && o2 == null)
		{
			return 1;
		}
		return 9;
	}
}
