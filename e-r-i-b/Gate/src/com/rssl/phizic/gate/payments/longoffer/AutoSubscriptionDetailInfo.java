package com.rssl.phizic.gate.payments.longoffer;

import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;

/**
 * ������������ ��������� ���������� �� ������������ �� �� "�����������"
 *
 * @author khudyakov
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public interface AutoSubscriptionDetailInfo extends CardPaymentSystemPaymentLongOffer, InternalCardsTransferLongOffer, ExternalCardsTransferToOurBankLongOffer
{

}
