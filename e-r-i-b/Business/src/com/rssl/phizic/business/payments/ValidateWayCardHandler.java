package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ext.sbrf.payments.forms.meta.ESBERIBPaymentsCondition;
import com.rssl.phizic.business.resources.external.CardLink;

/**
 * @author komarov
 * @ created 14.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class ValidateWayCardHandler extends BusinessDocumentHandlerBase
{
	private static final String MESSAGE = "Вы не можете совершить операцию по данной карте. Пожалуйста, выберите другую карту или %s.";
	private static final String DEPO_MESSAGE = "Вы не можете совершить операцию по данной карте. Пожалуйста, выберите другую карту.";
	private static final String CHARGE_OF_ACCOUNT = "счет списания";
	private static final String RECEIVER_ACCOUNT = "счет зачисления";
	private ESBERIBPaymentsCondition paymentsCondition = new ESBERIBPaymentsCondition();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			if (paymentsCondition.accepted(document, stateMachineEvent))
			{
				if ((document instanceof AbstractPaymentDocument))
				{
					AbstractPaymentDocument abstractPaymentDocument = (AbstractPaymentDocument) document;
					if (abstractPaymentDocument.getChargeOffResourceType() == ResourceType.CARD)
					{
						CardLink cardLink = (CardLink) abstractPaymentDocument.getChargeOffResourceLink();
						if (cardLink == null)
						{
							throw new DocumentException("Не найден линк-на-источник списания типа " + abstractPaymentDocument.getChargeOffResourceType());
						}
						String externalID = cardLink.getExternalId();
						if (externalID.indexOf("99-way") >= 0)
						{
							if(document instanceof RurPayment && ((RurPayment)document).isDepoPayment())
							{
								throw new DocumentLogicException(DEPO_MESSAGE); 
							}
							throw new DocumentLogicException(String.format(MESSAGE, CHARGE_OF_ACCOUNT));
						}
					}
				}

				if (document instanceof AbstractAccountsTransfer)
				{
					AbstractAccountsTransfer accountsTransfer = (AbstractAccountsTransfer) document;
					ResourceType destinationResourceType = accountsTransfer.getDestinationResourceType();
					if (destinationResourceType == ResourceType.CARD)
					{
						CardLink cardLink = (CardLink) accountsTransfer.getDestinationResourceLink();
						if (cardLink == null)
							throw new DocumentException("Не найден линк-на-приёмник зачисления типа " + destinationResourceType);
						String externalID = cardLink.getExternalId();
						if (externalID.indexOf("99-way") >= 0)
							throw new DocumentLogicException(String.format(MESSAGE, RECEIVER_ACCOUNT));
					}
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}
	}
}
