package com.rssl.phizic.gate.payments.autosubscriptions;

import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOtherBank;

/**
 * Интерфейс автоподписок карта-карта (эмитенты карт разные ТБ)
 *
 * @author khudyakov
 * @ created 28.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalCardsTransferToOtherBankLongOffer extends ExternalCardsTransferToOtherBank, AutoSubscriptionClaim
{
}
