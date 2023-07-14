package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfoService;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.business.documents.payments.CloseAutoSubscriptionPayment;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.basket.InvoiceService;
import com.rssl.phizic.gate.claims.DepoAccountClaimBase;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.AutoSubscriptionClaim;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.CreateInvoiceSubscription;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.DelayCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.longoffer.RecoveryCardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.DocStateUpdateRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.Document_Type;

/**
 * @author gladishev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Updater для запроса DocStateUpdateRq
 */
public class DocumentStateUpdater extends DocumentStateUpdaterBase<SynchronizableDocument>
{
	public DocumentStateUpdater(DocStateUpdateRq_Type docStateUpdateType)
	{
		super(docStateUpdateType);
	}

	@Override
	protected SynchronizableDocument getDocument(String documentNumber) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		return updateDocumentService.find(documentNumber);
	}

	@Override
	protected void updateDocument(SynchronizableDocument document, Document_Type documentInfo) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		updateDocumentService.update(document, getDocumentCommand(documentInfo));

		if(isSuccessfullStatus(documentInfo.getStatus()))
		{
			additionSuccessUpdate(document, documentInfo);
		}
		else
		{
			additionFailUpdate(document);
		}
	}

	@Override
	protected boolean isUpdateUnavailable(SynchronizableDocument document)
	{
		return !(AutoSubscriptionClaim.class.isAssignableFrom(document.getType()) || document instanceof DepoAccountClaimBase);
	}

	/**
	 * Костыль для обновления статуса подписок на инвойсы в случае успешного ответа
	 * @param document заявка на подписку
	 * @param documentInfo инфомация из шины
	 */
	private void additionSuccessUpdate(SynchronizableDocument document, Document_Type documentInfo)
	{
		InvoiceService invoiceService = GateSingleton.getFactory().service(InvoiceService.class);

		try
		{
			if(CreateInvoiceSubscription.class.isAssignableFrom(document.getType()))
			{
				String invoiceSubId;

				BasketPaymentsListenerConfig config  = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
				if(BasketPaymentsListenerConfig.WorkingMode.autopay == config.getWorkingMode())
				{
					String statusDesc = documentInfo.getStatus().getStatusDesc();
					if(StringHelper.isEmpty(statusDesc) || !statusDesc.matches("\\d+\\|.*"))
						return;

					invoiceSubId = statusDesc.substring(0, statusDesc.indexOf("|"));
				}
				else
				{
					invoiceSubId = Long.toString(documentInfo.getAutoSubscriptionId().getAutopayId());
				}

				invoiceService.activateInvoiceSubscription(
						invoiceSubId, ((PossibleAddingOperationUIDObject) document).getOperationUID());
			}
			else if (CloseInvoiceSubscription.class.isAssignableFrom(document.getType()))
			{
				CloseInvoiceSubscription claim = (CloseInvoiceSubscription) document;
				BasketRouteInfoService.remove(claim.getOperationUID());

				if(claim.isRecoverAutoSubscription())
					invoiceService.recoverAutoSubscription(claim);
			}
			else if(DelayCardPaymentSystemPaymentLongOffer.class == document.getType())
			{
				invoiceService.createInvoiceSubscription((DelayCardPaymentSystemPaymentLongOffer) document);
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Костыль для обновления статуса подписок на инвойсы в случае не успешного ответа
	 * @param document заявка на подписку
	 */
	private void additionFailUpdate(SynchronizableDocument document)
	{
		try
		{
			if(CreateInvoiceSubscription.class.isAssignableFrom(document.getType()))
				BasketRouteInfoService.remove(((CreateInvoiceSubscription) document).getOperationUID());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
