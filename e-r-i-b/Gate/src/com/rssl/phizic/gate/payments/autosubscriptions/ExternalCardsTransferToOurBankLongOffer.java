package com.rssl.phizic.gate.payments.autosubscriptions;

import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;

/**
 * Интерфейс автоподписок карта-карта (эмитент карт ТБ Сбера)
 *
 * @author khudyakov
 * @ created 28.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalCardsTransferToOurBankLongOffer extends ExternalCardsTransferToOurBank, AbstractPhizTransfer, AutoSubscriptionClaim
{
}
