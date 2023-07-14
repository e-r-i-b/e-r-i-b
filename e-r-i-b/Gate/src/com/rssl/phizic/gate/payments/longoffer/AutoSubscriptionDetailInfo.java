package com.rssl.phizic.gate.payments.longoffer;

import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;

/**
 * Объединенная детальная информация по автоплатежам из АС "Автоплатежи"
 *
 * @author khudyakov
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public interface AutoSubscriptionDetailInfo extends CardPaymentSystemPaymentLongOffer, InternalCardsTransferLongOffer, ExternalCardsTransferToOurBankLongOffer
{

}
