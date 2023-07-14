package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizicgate.esberibgate.types.AutoSubscriptionImpl;

/**
 * @author tisov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 * ��������������� ����� ��� �������������� ������ � ��������� ��� �����������
 */
public class GateEntityViewHelper
{

	/**
	 * @param subscription - ��������� ���������� �� ������������
	 * @return - ������������ ��� ����������
	 */
	public static String getReceiverNameForView(AutoSubscriptionDetailInfo subscription)
	{
		if (subscription == null)
		{
			return "";
		}

		if (isP2PAutoTransfer(subscription.getType()))
		{
			return MaskUtil.getMaskedFIO(subscription.getReceiverFirstName(), subscription.getReceiverPatrName(), subscription.getReceiverSurName());
		}

		return subscription.getReceiverName();
	}

	/**
	 * @param subscription - ������������
	 * @return - ������������ ��� ����������
	 */
	public static String getReceiverNameForView(AutoSubscriptionImpl subscription)
	{
		if (subscription == null)
		{
			return "";
		}

		if (isP2PAutoTransfer(subscription.getType()))
		{
			return MaskUtil.getMaskedFIO(subscription.getReceiverFirstName(), subscription.getReceiverPatronymicName(), subscription.getReceiverLastName());
		}

		return subscription.getReceiverName();

	}

	private static boolean isP2PAutoTransfer(Class type)
	{
		return type == ExternalCardsTransferToOtherBankLongOffer.class
				|| type == ExternalCardsTransferToOurBankLongOffer.class
				|| type == InternalCardsTransferLongOffer.class;

	}

}
