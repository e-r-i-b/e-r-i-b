package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.RedirectDocumentLogicException;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.ConvertibleToGateDocumentAdapter;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.RedirectGateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Gainanov
 * @ created 12.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class PrepareDocumentHandler extends BusinessDocumentHandlerBase<BusinessDocument>
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	public void process(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (isOfflineDelayedSystem(document, stateMachineEvent))
		{
			return;
		}

		try
		{
			GateDocument gateDocument = new ConvertibleToGateDocumentAdapter(document).asGateDocument();
			if (isEmptyRecieverBank(gateDocument))
			{
				throw new DocumentLogicException("На данный момент оплата услуг в адрес выбранного получателя запрещена.");
			}

			DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
			documentService.prepare(gateDocument);
		}
		
		catch (TemporalGateException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (RedirectGateLogicException e)
		{
			throw new RedirectDocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	protected boolean isOfflineDelayedSystem(BusinessDocument document, StateMachineEvent stateMachineEvent)
			throws DocumentException, DocumentLogicException
	{
		return false;
	}
	    
	/**
	 * @param gateDocument документ.
	 * @return указан или не указан БИК банка получателя.
	 */
	private boolean isEmptyRecieverBank(GateDocument gateDocument) throws DocumentException
	{
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType()))
		{
			return false;
		}

		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) gateDocument;

		try
		{
			ServiceProviderShort provider = serviceProviderService.findShortProviderBySynchKey(payment.getReceiverPointCode());
			if (provider == null || !provider.isBankDetails())
				return false;
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

		return payment.getReceiverBank() == null ||	StringHelper.isEmpty(payment.getReceiverBank().getBIC());
	}
}
