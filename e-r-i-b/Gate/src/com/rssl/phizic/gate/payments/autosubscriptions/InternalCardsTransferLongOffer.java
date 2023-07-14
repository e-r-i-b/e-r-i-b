package com.rssl.phizic.gate.payments.autosubscriptions;

import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;

/**
 * Интерфейс автоподписок карта-карта (между моими картами)
 *
 * @author khudyakov
 * @ created 28.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface InternalCardsTransferLongOffer extends InternalCardsTransfer, AbstractPhizTransfer, AutoSubscriptionClaim
{
}
