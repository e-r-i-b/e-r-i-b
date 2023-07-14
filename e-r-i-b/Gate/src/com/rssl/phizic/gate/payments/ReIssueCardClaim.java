package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ������ �� ���������� �����.
 *
 * @author bogdanov
 * @ created 21.03.2013
 * @ $Author$
 * @ $Revision$
 */

public interface ReIssueCardClaim extends Claim
{
	/**
	 * @return �������������� �����.
	 */
	public String getCardId();

	/**
	 * @return ��� �����.
	 */
	public String getCardType() throws GateException;

	/**
	 * @return ����� �����.
	 */
	public String getCardNumber();

	/**
	 * @return ��� ��������� ����������� �����.
	 */
	public Code getDestinationCode();

	/**
	 * @return ���������������� �� ���������� ������������� ������2 ��� ��������� ����������� �����.
	 */
	public Code getConvertedDestinationCode() throws GateException;

	/**
	 * @return ��� ���������.
	 */
	public Code getBankInfoCode();

	/**
	 * @return ���������������� �� ���������� ������������� ������2 ��� ���������.
	 */
	public Code getConvertedBankInfoCode() throws GateException;

	/**
	 * @return ������� �����������.
	 */
	public String getReasonCode();

	/**
	 * @return ��������� ��������� ��� ���.
	 */
	public boolean getIsCommission();
}
