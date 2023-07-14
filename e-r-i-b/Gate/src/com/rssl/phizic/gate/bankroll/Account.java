package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ��������� �����.
 * ������������ ��� �������� ����������� (���������� �� ���������� ���� ����� �����) ������
 */
public interface Account extends Serializable
{
	/**
	 * ���������� ID ����� �� ������� �������
	 * Domain: ExternalID
	 *
	 * @return ID ����� �� ������� �������
	 */
	String getId();

	/**
	 * ���������� ��������� (�������) �������� ���� ����� (����������� | �� ������� | etc)
	 * Domain: Text
	 *
	 * @return ��� �����
	 */
	String getDescription();

	/**
	 * 
	 * ���������� ��������� �������� ���� ����� (������� | �� ������������� | etc)
	 * Domain: Text
	 *
	 * @return ��� ����� � ���� ������
	 * @deprecated ����������� getDescription()
	 */
	@Deprecated
	String getType();

	/**
	 * ���������� ����� �����
	 * Domain: AccountNumber
	 *
	 * @return ����� �����
	 */
	String getNumber();

	/**
	 * ���������� ������ �����
	 * @return ������ �����
	 */
	Currency getCurrency();

	/**
	 * ���������� ���� �������� �����
	 * Domain: Date
	 *
	 * @return ���� �������� �����
	 */
	Calendar getOpenDate();

	/**
     * ���������� ������� ������������ ��������� �������� �� �����
	 *
     * @return ����������� ��������� �������� �� �����
     */
	public Boolean getCreditAllowed();

	/**
     * ���������� ������� ������������ ��������� �������� �� �����
	 * 
     * @return ����������� ��������� �������� �� �����
     */
	public Boolean getDebitAllowed();

	/**
     * ���������� ���������� ������ �� �����
     *
     * @return ���������� ������ �� ����� ��� null ���� ���� - �������
     */
	BigDecimal getInterestRate();

	/**
     * ���������� �������
     *
     * @return ������� � ������ �����
     */
	Money getBalance();

	/**
	 * ���������� ������������ ����� ��������.
	 * ������ ��������� ������ ������ ���� �����.
	 *
	 * @return ������������ ����� �������� ��� null, ���� ����������� ���
	 */
	Money getMaxSumWrite();

	//TODO: ����������� ������ �� ����. ������� ��� v6 � ����� ���, ���� ��������. ENH025315
	/**
	 * ���������� ����������� �������
	 *
	 * @return ����������� �������
	 */
	Money getMinimumBalance();

	/**
	 * ���������� ������ �����
	 *
	 * @return ������ �����
	 */
	AccountState getAccountState();

	/**
     * ���������� �������������, � ������� ������ ����
     *
     * @return �������������, � ������� ������ ���� ��� null, ���� ������ �������� ��� ����������
     */
    Office getOffice();

	/**
	 * ���������� ��� ������
	 *
	 * @return ��� ������
	 */
	Long getKind();

	/**
	 * ���������� ������ ������
	 * 
	 * @return ������ ������
	 */
	Long getSubKind();

	/**
	 * ���������� ������� "�������� �� ����� ���������� (�� �������������)" (�� demand deposit)
	 *
	 * @return true - ����� ���������� (�� �������������), false - ������� �����
	 */
	Boolean getDemand();

	/**
	 * ���������� ������� ������� ����. ������ �� ������
	 *
	 * @return true - ���������� ����
	 */
	Boolean getPassbook();

	/**
	 * ���������� ����� ��������
	 * Domain: Text
	 *
	 * @return ����� ��������
	 */
	String getAgreementNumber();

	/**
	 * ���������� ���� �������� �����
	 * ����������: � ������� �� ���� �������� ����� ������� (���������) �� ���� ������� ��������� ���.
	 * Domain: Date
	 *
	 * @return ���� �������� �����
	 */
	Calendar getCloseDate();

	/**
	 * ���������� ������ ������
	 *
	 * @return ������ ������ �� �������� ��� null, ���� ����� �� �������������
	 */
	DateSpan getPeriod();

	//TODO: ����������� ������ �� ����. ������� ��� v6 � ����� ���, ���� ��������. ENH025315
	/**
	 * ���������� ������� "��������� �� �������� �� ����� � ������ ���" (������� �������� ������)
	 *
	 * @return ��������� (true), ��������� (false).
	 */
	Boolean getCreditCrossAgencyAllowed();

	//TODO: ����������� ������ �� ����. ������� ��� v6 � ����� ���, ���� ��������. ENH025315
	/**
	 * ���������� ������� "��������� �� ���������� �� ���� � ������ ���" (������� �������� ������)
	 *
	 * @return ��������� (true), ��������� (false).
	 */
	Boolean getDebitCrossAgencyAllowed();

	/**
	 * ���������� ������� "�������� �� ��������������� ������ �� ��������� ����"
	 *
	 * @return ����� ���� ������� (true), �� ����� ���� ������� (false).
	 */
	Boolean getProlongationAllowed();

	//TODO: ����������� ������ �� ����. ������� ��� v6 � ����� ���, ���� ��������. ENH025315
	/**
	 * ���������� ����� ����� ��� ������������ ���������
	 *
	 * @return ����� ����� ��� ������������ ��������� ��� null, ���� ���� �������
	 */
	String getInterestTransferAccount();

	//TODO: ����������� ������ �� ����. ������� ��� v6 � ����� ���, ���� ��������. ENH025315
	/**
	 * ���������� ����� ����� ��� ������������ ���������
	 *
	 * @return ����� ����� ��� ������������ ���������
	 */
	String getInterestTransferCard();

	/**
	 * ���������� ������� �� ������ ��� �������������
	 *
	 * @return ����� ������� ��� �������������, ��� null
	 */
	BigDecimal getClearBalance();

	/**
	 * ���������� ������������ ����� ������
	 *
	 * @return ������������ ����� ������ ��� null
	 */
	BigDecimal getMaxBalance();

	/**
	 * ���������� ����� ��������� ��������
	 * Domain: DateTime
	 *
	 * @return ����-����� ��������� ��������
	 */
	Calendar getLastTransactionDate();

	/**
	 * @return �������� � ���������
	 */
	Client getAccountClient();

	/**
	 * ���������� ���� �����������
	 * Domain: Date
	 *
	 * @return ���� �����������
	 */
	Calendar getProlongationDate();

	/**
	 * ���������� ������� �������
	 *
	 * @return ������� �������
	 */
	Long getClientKind();
}
