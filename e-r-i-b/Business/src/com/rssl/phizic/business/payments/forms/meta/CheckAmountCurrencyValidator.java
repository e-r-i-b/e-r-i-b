package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Money;

/**
 * @author Mescheryakova
 * @ created 25.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class CheckAmountCurrencyValidator extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		JurPayment doc = (JurPayment) document;
        PaymentAbilityERL link = doc.getChargeOffResourceLink();
        if (link == null)
            throw new DocumentException("Ќе найден линк-на-источник списани€ типа " + doc.getChargeOffResourceType());

        Money moneyForCheck = doc.getDestinationAmount();

        //≈сли валюты не совпадают, то не конвертируем, т.к. наш курс может отличатьс€ от курса внешней системы.
        if (!moneyForCheck.getCurrency().getCode().equals(link.getCurrency().getCode()))
            return;

        if (moneyForCheck.getDecimal().compareTo(link.getRest().getDecimal()) > 0)
            throw new DocumentLogicException("—умма платежа превышает остаток на карте");
	}
}
