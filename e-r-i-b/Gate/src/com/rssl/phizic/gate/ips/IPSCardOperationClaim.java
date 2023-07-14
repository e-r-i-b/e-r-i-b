package com.rssl.phizic.gate.ips;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ��������� �������� �� �����
 */
public interface IPSCardOperationClaim extends Serializable
{
	/**
	 * @return ������, �� �������� ���� �������� ����������
	 */
	Client getClient();
	
	/**
	 * @return �����, �� ������� ���� �������� ����������
	 */
	Card getCard();

	/**
	 * @return ����, ������� � ������� ����� �������� ���������� (������������)
	 */
	Calendar getStartDate();
}
