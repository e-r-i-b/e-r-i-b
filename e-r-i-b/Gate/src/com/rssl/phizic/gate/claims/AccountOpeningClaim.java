package com.rssl.phizic.gate.claims;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.payments.AccountClosingPayment;
import com.rssl.phizic.gate.payments.AccountOrIMAOpeningClaimBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 03.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������ ����� �������� �������� ������� �� �����
 */
public interface AccountOpeningClaim extends AccountClosingPayment, AccountOrIMAOpeningClaimBase, AccountChangeInterestDestinationClaim
{
	/**
    * ���� ��������� ����� �������� ������
    *
    * @return ����
    */
	Calendar getClosingDate();

	/**
    * ����, �� ������� ����������� �����
    *
    * @return ����
    */
	DateSpan getPeriod();

	/**
    * ������, � ������� ����������� �����
    *
    * @return ������
    */
	Currency getCurrency();

	/**
    * ��� ������
    *
    * @return ��� ������
    */
	Long getAccountType();

	/**
    * ������ ������
    *
    * @return ������ ������
    */
	Long getAccountSubType();

	/**
	 * ��� ������ ��������� ��������
	 *
	 * @return ��� ������
	 */
	String getAccountGroup();

	/**
	 * ��������� ���� ������ ��������� ��������
	 * @param accountGroup
	 */
	void setAccountGroup(String accountGroup);

	/**
    * ������� �������� �������� � ���������
    *
    * @return true - �������� �������� � ���������
    */
	boolean isWithClose();

	/**
    * ��������� ������ ��������� ������
    *
    */
	void setReceiverAccount(String accountNumber);

	/**
	 * @return ����� ������������ �������, ���� null, ���� ����� ��� ������������ �������
	 */
	BigDecimal getIrreducibleAmmount();

	/**
	 * ������� ������ ��������� (�� ����� "card" �� ���� ������ "account")
	 * @return
	 */
	String getPercentTransferSource();

	/**
	 * ��� ����� ��� ������������ ��������� �� ������, ���� ������� ������ ��������� "�� �����"
	 * @return
	 */
	String getPercentTransferCardSource();

	/**
	 *  ������� � ������� ����� ������� ����
	 * @return
	 */
	String getAccountTb();

	/**
	 * ��������� ��������, � ������� � ���������� ��� ������ �����
	 * @param tb
	 */
	void setAccountTb(String tb);

	/**
	 * ������ �����, � ������� ����� ������� �����
	 * @return
	 */
	String getAccountOsb();

	/**
	 * ��������� ������� �����, � ������� � ���������� ��� ������ �����
	 * @param osb
	 */
	void setAccountOsb(String osb);

	/**
	 * ��������� ������� �����, � ������� ����� ������� �����
	 * @return
	 */
	String getAccountVsp();

	/**
	 * ��������� ��������� ������� �����, � ������� � ���������� ��� ������ �����
	 * @param vsp
	 */
	void setAccountVsp(String vsp);

	/**
	 * ��������� �� ������ ��� �������� ������
	 * @return
	 */
	String getClaimErrorMsg();

	/**
	 * ��������� ��������� �� ������ ��� ������������ ������� � ����
	 * ��� ��� ��������� ������ �� ���� �� �������� ������
	 * @param claimErrorMsg
	 */
	void setClaimErrorMsg(String claimErrorMsg);

	/**
	 * @return - �� ���������� ��� ����������� ���������
	 */
	String getCuratorId();

	/**
	 * @return - ��� "��" ��� "���������"
	 */
	String getCuratorType();

	/**
	 * @return - �� ��������� ��� ����������
	 */
	String getCuratorTb();

	/**
	 * ��������� �� ��������� ��� ����������
	 * @param curatorTb
	 */
	void setCuratorTb(String curatorTb);

	/**
	 * @return - ��� ��������� ��� ����������
	 */
	String getCuratorOsb();

	/**
	 * ��������� ��� ����������� ��������� ��� ����������
	 * @param curatorOsb
	 */
	void setCuratorOsb(String curatorOsb);

	/**
	 * @return - ��� ��������� ��� ����������
	 */
	String getCuratorVsp();

	/**
	 * ��������� ��� ����������� ��������� ��� ����������
	 * @param curatorVsp
	 */
	void setCuratorVsp(String curatorVsp);

    void setAtmPlace(String atmPlace);
    String getAtmPlace();

    void setAtmTB(String atmTB);
    String getAtmTB();


    void setAtmOSB(String atmOSB);
    String getAtmOSB();


    void setAtmVSP(String atmOSB);
    String getAtmVSP();

	boolean isPension();

	/**
	 * ������� ������� (������� �����������)
	 * @param segment
	 */
	void setSegment(String segment);
	String getSegment();

	boolean isUsePromoRate();

	/**
	 * @return �������� ���������� ��� �������
	 */
	ClientDocument getMainDocument();
}
