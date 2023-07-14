package com.rssl.phizicgate.manager.services.routable.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author khudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 * Маршрутизатор на основе офиса.
 */

public class OfficeRoutableDocumentService extends AbstractRoutableDocumentService implements DocumentService, DocumentSender, CommissionCalculator
{
	public OfficeRoutableDocumentService(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params){}

	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
	    if(getDelegateFactory(document).service(GateInfoService.class).isPaymentCommissionAvailable(document.getOffice()))
	    {
		    super.calcCommission(document);
	    }
	}

	/**
	 * Получение делегата по документу
	 * @param document документ
	 * @return делегат.
	 * @throws GateException
	 */
	protected DocumentService getDelegate(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegateFactory(document).service(DocumentService.class);
	}
}