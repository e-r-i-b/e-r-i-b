package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.clients.Client;

/**
 * @author Erkin
 * @ created 28.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �� �����
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public interface IPSCardOperation extends com.rssl.phizic.gate.bankroll.CardOperation
{
	/**
	 * @return ������, ������� ����� ��������
	 */
	Client getOwner();

	/**
	 * @return MCC-��� ��������
	 */
	long getMccCode();

	/**
	 * @return ������� "�������� � ��������� / � ������������ ����������"
	 */
	boolean isCash();

	String getAuthCode();
}
