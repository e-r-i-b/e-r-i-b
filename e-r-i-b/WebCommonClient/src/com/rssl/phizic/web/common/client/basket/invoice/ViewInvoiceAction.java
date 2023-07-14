package com.rssl.phizic.web.common.client.basket.invoice;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.invoice.ViewInvoiceOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

import java.util.List;

/**
 * @author osminin
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра детальной информации по задолженности
 */
public class ViewInvoiceAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceOperation operation = createOperation(ViewInvoiceOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewInvoiceForm frm = (ViewInvoiceForm) form;
		ViewInvoiceOperation op = (ViewInvoiceOperation) operation;
		Invoice invoice = op.getEntity();

		frm.setInvoice(invoice);
		frm.setBankName(op.getBankName());
		frm.setBankAccount(op.getBankAccount());
		frm.setSubscriptionName(op.getInvoiceSubscriptionName());
		frm.setAccessRecoverAutoSub(op.isAccessRecoverAutoSub());
		frm.setOperationAvailable(op.isAvailableOperations());
		frm.setConfirmSubscription(op.isConfirmSubscription());
		// для взаимодейтвтия через шину есть возможность выбирать истоничк списания
		if(BasketPaymentsListenerConfig.WorkingMode.esb == BasketHelper.getBasketInteractMode())
		{
			List<CardLink> listCardLinks = op.getChargeOffResource();
			if (listCardLinks.size() == 0 || !CardsUtil.isCardLinkInList(listCardLinks, invoice.getCardNumber()))
			{
				CardLink defaultCard = CardsUtil.getCardLinkWithoutVisible(invoice.getCardNumber());
				if (defaultCard != null)
				{
					listCardLinks.add(defaultCard);
				}
			}
			if (listCardLinks.size() == 0)
			{
				throw new BusinessLogicException("Нет доступных источников списания.");
			}
			frm.setChargeOffResources(listCardLinks);
		}
	}
}
