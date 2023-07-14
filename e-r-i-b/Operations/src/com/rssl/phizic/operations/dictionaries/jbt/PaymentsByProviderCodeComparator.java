package com.rssl.phizic.operations.dictionaries.jbt;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.payments.RUSTaxPayment;

import java.util.Comparator;

/**
 * @author: vagin
 * @ created: 01.11.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сравнивает платежи по external_id поставщика услуг(для выгрузки жбт). По ИНН для налоговых платежей.
 */
public class PaymentsByProviderCodeComparator implements Comparator<AbstractPaymentDocument>
{
	public int compare(AbstractPaymentDocument o1, AbstractPaymentDocument o2)
	{
		String code1 = null;
		String code2 = null;
		if(o1 instanceof JurPayment)
		{
		    code1 = ((JurPayment)o1).getReceiverPointCode();
		    code2 = ((JurPayment)o2).getReceiverPointCode();
		}
		if(RUSTaxPayment.class == o1.getType())
		{
			code1 = ((RUSTaxPayment)o1).getReceiverINN();
			code2 = ((RUSTaxPayment)o2).getReceiverINN();
		}
		if(code1==null && code2==null)
		{
			return 0;
		}
		if(code1==null)
		{
			return -1;
		}
		if(code2==null)
		{
			return 1;
		}
		return code1.compareTo(code2);
	}
}
