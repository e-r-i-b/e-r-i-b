package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.security.SecurityAccount;

/**
 * ������ ��� ���������� ���������� � ����������� ������� ���������
 * @author Pankin
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */
public interface StoredResourcesService extends Service
{

	/**
	 * ���������� ����������� ���������� � �����
	 * @param loginId ������������� ������ �������
	 * @param account ����
	 */
	void updateStoredAccount(Long loginId, Account account);

	/**
	 * ���������� ����������� ���������� � �����
	 * @param loginId ������������� ������ �������
	 * @param card �����
	 */
	void updateStoredCard(Long loginId, Card card);

	/**
	 * ���������� ����������� ���������� � �������
	 * @param loginId ������������� ������ �������
	 * @param loan ������
	 */
	void updateStoredLoan(Long loginId, Loan loan);

	/**
	 * ���������� ����������� ���������� � ����� ����
	 * @param loginId ������������� ������ �������
	 * @param depoAccount ���� ����
	 */
	void updateStoredDepoAccount(Long loginId, DepoAccount depoAccount);

	/**
	 * ���������� ����������� ���������� �� ���
	 * @param loginId ������������� ������ �������
	 * @param imAccount ���
	 */
	void updateStoredIMAccount(Long loginId, IMAccount imAccount);

	/**
	 * ���������� ����������� ���������� � ���������� ����������
	 * @param loginId ������������� ������ �������
	 * @param longOffer ���������� ���������
	 */
	void updateStoredLongOffer(Long loginId, LongOffer longOffer);

	/**
	 * ���������� ����������� ���������� � �������������� ������������
	 * @param loginId
	 * @param securityAccount
	 */
	void updateStoredSecurityAccount(Long loginId, SecurityAccount securityAccount);

}
