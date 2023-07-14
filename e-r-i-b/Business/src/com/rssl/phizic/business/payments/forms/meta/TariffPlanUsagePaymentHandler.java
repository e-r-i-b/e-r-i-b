package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountClosingPayment;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.IMAOpeningClaim;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.payments.ExchangeCurrencyTransferBase;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

/**
 * Сообщение об использовании тарифного плана
 * @author sergunin
 * @ created 20.03.2015
 * @ $Author$
 * @ $Revision$
 */

public class TariffPlanUsagePaymentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
    {
        if (!ApplicationUtil.isMobileApi())
            return;

        if (!(document instanceof ExchangeCurrencyTransferBase))
			throw new DocumentException("Неверный тип платежа. Ожидается ExchangeCurrencyTransferBase");

        try {

            if  (document instanceof IMAOpeningClaim || document instanceof AccountOpeningClaim || document instanceof AccountClosingPayment ||
                 (document instanceof InternalTransfer && "IMAPayment".equals(((InternalTransfer) document).getFormName()))) {

                String tariffPlanCode =  PersonHelper.getActivePersonTarifPlanCode();

                if (tariffPlanCode != null && !TariffPlanHelper.isUnknownTariffPlan(tariffPlanCode))
                {
                    String tarifPlanMessage = PersonHelper.getTarifPlanConfigMeessage(tariffPlanCode);

                    ExtendedAttribute fromResource = ((ExchangeCurrencyTransferBase) document).getAttribute("from-resource-currency");
                    String fromResourceCurrency = fromResource == null ? null : fromResource .getStringValue();
                    ExtendedAttribute toResource = ((ExchangeCurrencyTransferBase) document).getAttribute("to-resource-currency");
                    String toResourceCurrency = toResource == null ? null : toResource .getStringValue();

                    if (StringHelper.isNotEmpty(fromResourceCurrency) && StringHelper.isNotEmpty(toResourceCurrency) &&
                        !fromResourceCurrency.equalsIgnoreCase(toResourceCurrency))
                        stateMachineEvent.getMessageCollector().addMessage(tarifPlanMessage);
                }
            }
        }
        catch (BusinessException e)
        {
            throw new DocumentException(e);
        }
	}
}
