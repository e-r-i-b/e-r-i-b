package com.rssl.phizic.business.payments;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.checkers.DocumentCheckerBase;
import com.rssl.phizic.business.documents.payments.GoodsAndServicesPayment;

/**
 * @author osminin
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */

public class DocumentTypeCheker extends DocumentCheckerBase
{
	public boolean check(BusinessDocument doc){
		String [] types = getParameter("allowed-types").split(",");
		String docType=((GoodsAndServicesPayment)doc).getReceiverType();
		for (String type: types){
			if (docType.equals(type)){
				return true;
			}
		}
		return false;
	}
}