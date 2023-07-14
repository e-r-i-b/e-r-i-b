package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ����������� �����. �������� �������� ������ �� �����. ��� ������ �� ���������� � �������� ����� �����.
 */
public interface Card extends Serializable
{
	/**
	 * ������� ID �����
	 * Domain: ExternalID
	 *
	 * @return id �����
	 */
	String getId();

	/**
	 * �������� ����� (Visa Classic, MasterCard, Maestro Cirrus etc)
	 * Domain: Text
	 *
	 * @return �������� �����
	 */
	String getDescription();

	/**
	 * @return ��� �������� ���������� �������.
	 */
	StatusDescExternalCode getStatusDescExternalCode();

	/**
	 * �������� ����� (Visa Classic, MasterCard, Maestro Cirrus etc)
	 *
	 * @return �������� �����
	 * @deprecated ����������� getDescription()
	 */
	@Deprecated
	String getType();

	/**
	 * ����� �����
	 * Domain: CardNumber
	 *
	 * @return ����� �����
	 */
	String getNumber();

	/**
	 * ���� ������� ����� (��, ��� ������ �� �����)
	 * Domain: Date
	 *
	 * @return ���� ������� �����
	 */
	Calendar getIssueDate();

	/**
	 * ���� ��������� ����� �������� ����� (��, ��� ������ �� �����).
	 * ������������ ����� ����� ������ ���� ��������� ����� ����� �� ����� ���� ��. CHG032182.
	 * Domain: Date
	 *
	 * @return ���� ��������� ����� �������� �����
	 */
	Calendar getExpireDate();

	/**
	 *
	 * ������������ ������ ��� ������ ���� ��������� ����� ����� �� ����� ���� ��. CHG032182
	 *
	 * @return ��������� ����� �������� �����. ����� ���������� null.
	 */
	String getDisplayedExpireDate();

	/**
	 * ������� �������� �����
	 * @return true - �������� �����, false - ���
	 */
	boolean isMain();
	
   /**
    * ��������� ���� �����(���������, ���������, ������������).
    * �������� �� CardType
    * @return ��� �����
    */
   CardType getCardType();

	   /**
    * ��������� ����� �� �����.
    * ���� ���������� null - ������ �����������.
    *
    * @return ���� ���������� null - ������ �����������.
    */
   Money getAvailableLimit();
   /**
    * �������������, � ������� ������� �����
    *
    * @return �������������, � ������� ������� ����� ��� null, ���� ������ �������� ��� ����������
    */
   Office getOffice();

	/**
	 * ��� ������������� ����� � ������������������� ��������� (�� ������������ �������� ��� ������)
	 * @return
	 */
	Code getOriginalTbCode();

   /**
    * ��� �������������� �����.
    *
    * @return null ��� �������� �����, ��� ��� ��������������
    */
   AdditionalCardType getAdditionalCardType();
   /**
    * �������� ������� �����
    *
    * @return ��������� ������������� ������� �����
    */
   String getStatusDescription();
   /**
    * ��������� �������� ������� �����(��������, ��������, ���������������...)
    *
    * @return CardState
    */
   CardState getCardState();

	/**
    * ��������� �������� ������� ���������� �����
	* �� ������ ���������� ������ ������������� ������ ��� ������������ ��������� ������,
	* � ��������� ������� ������������ null
    *
    * @return AccountState
    */
   AccountState getCardAccountState();
   /**
    * �������� ������ �����.
    */
   Currency getCurrency();
   /**
    * �������� ����� �������� ����� ��� ��������������.
    */
   String getMainCardNumber();

	/**
	 * ������� ����, ��� ����� �������� �����������
	 *
 	 * @return true - ����� �����������, false - ������� �����
	 */
   boolean isVirtual();

   /**
    * �������� ����� ���
	*
	* @return String
	*/
   String getPrimaryAccountNumber();

   /**
    * �������� ������� ������������� ���
	*
	* @return String
	*/
   String getPrimaryAccountExternalId();

	/**
	 * ��� �����
	 * @return Long
	 */
	Long getKind();

	/**
	 * ������ �����
	 * @return Long
	 */
	Long getSubkind();

	/**
	 * @return ��� �����.
	 */
	String getUNICardType();

	/**
	 * ��� �����.
	 * ���������� ��������:
	 * B � �������������
	 * D � ������������
	 * I � ������
	 * K � ��������� � ������ ���������� �������
	 * L � ��������
	 * M � ����������
	 * N � ���������� � �����������
	 * O � ���������� � ����������� ��� ����������� ��������� ������
	 * P � �����������������
	 * R � �����������������
	 * S � ����������
	 * U � ������������� ��� ����������� ��������� ������
	 * X � ������������ (� ������ ����������� �������)
	 * Z � ����������
	 * C � ���������
	 * F � Momentum
	 * ����� ����������, ���� ��� in ('Z', 'O', 'N'), ����� ����� ������
	 * @return ��� �����.
	 */
	String getUNIAccountType();

	/**
	 * @return ��� �����
	 */
	CardLevel getCardLevel();

	/**
	 * @return ������� �������������� ����� � �������� ���������
	 */
	CardBonusSign getCardBonusSign();

	/**
	 * @return ������� ������������� �������
	 */
	String getClientId();

	/**
	 * @return ������������ �� ��������
	 */
	boolean isUseReportDelivery();

	/**
	 * @return e-mail
	 */
	String getEmailAddress();

	/**
	 * @return ��� ������ ������ � ��������
	 */
	ReportDeliveryType getReportDeliveryType();

	/**
	 * @return ���� ������ ������ � ��������
	 */
	ReportDeliveryLanguage getReportDeliveryLanguage();

	/**
	 * ����� �������.
	 * null - ������ ������ ���.
	 * @return ��������� ����� �� �������
	 */
	Money getPurchaseLimit();

	/**
	 * ����� �� ������ ��������
	 *
	 * @return ����� �� ������ �������� ��� null, ���� ����� �����������
	 */
	Money getAvailableCashLimit();

	/**
	 * ������� ��������� ����� (��, ��� ������ �� �����)
	 * Domain: Text
	 *
	 * @return ������� ��������� �����
	 */
	String getHolderName();

	/**
	 * ����� ��������
	 * @return ����� ��������
	 */
	String getContractNumber();

    /**
     * ���� ���������� ������ �� ����� �����
     * @return ���� ������
     */
    Calendar getNextReportDate();

	/**
	 * @return ����� ����������
	 */
	Money getOverdraftLimit();

	/**
	 * @return ����� ����� �����
	 */
	Money getOverdraftTotalDebtSum();

	/**
	 * ����� ������������ �������.
	 *
	 * @return ����� ������������ ������� ��� null, ���� ��� ���� ����� ����� ����� ���.
	 */
	Money getOverdraftMinimalPayment();

	/**
	 * ���� ������������ �������.
	 *
	 * @return ���� ������������ ������� ��� null, ���� ��� ������ ����� �� �������������
	 */
	Calendar getOverdraftMinimalPaymentDate();

	/**
	 * @return ����� ����������� �������.
	 */
	Money getOverdraftOwnSum();

	/**
	 * @return �������� �����
	 */
	Client getCardClient();
}