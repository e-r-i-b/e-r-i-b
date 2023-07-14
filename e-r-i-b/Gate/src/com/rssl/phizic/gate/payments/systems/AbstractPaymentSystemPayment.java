package com.rssl.phizic.gate.payments.systems;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.payments.AbstractBillingPayment;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

import java.util.List;

/**
 * ������ ����� ��������� �������
 * ��� �������� � ���������� ������ ����������.
 *
 * @author Krenev
 * @ created 23.11.2007
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPaymentSystemPayment extends AbstractJurTransfer, AbstractBillingPayment
{

	/**
	 * @return ��� ��������, � ������� ������������ ������
	 */
	String getBillingCode();

	/**
	 * ���������� ��� ��������
	 * @param billingCode ��� ��������
	 */
	void setBillingCode(String billingCode);

	/**
	 * �������� ������������� ������� � ��������.
	 * ������� ������� ���� � ������� �������� �������� �� ����������� ����� ����� ��� ��������� ��
	 * ���������� ��� ���������  ��������. ��������, ������� ����, ���, ��������� ��������� � ��.
	 * @return ������������ ������� � ��������. ����� ������������. � ������ ������ ���������� null.
	 */
	String getBillingClientId();

	/**
	 * �������� ������ � ��
	 *
	 * @return ������
	 */
	Service getService();

	/**
	 * ���������� �������� ������ � ��
	 *
	 * @return ������
	 */
	void setService(Service service);

	/**
	 * @return ������ �������������� ����� �������
	 */
	//TODO ������� GateException
	List<Field> getExtendedFields() throws DocumentException;

	/**
	 * ���������� �������� ������ ����������
	 * @param receiverAccount ����� ����������
	 */
	void setReceiverAccount(String receiverAccount);

	/**
	 * @return ���������� ���� ����������
	 */
	public String getReceiverTransitAccount();

	/**
	 * ���������� ���������� ���� ����������
	 * @param receiverTransitAccount ���������� ���� ����������
	 */
	void setReceiverTransitAccount(String receiverTransitAccount);

	/**
	 * ���������� ��� ����������
	 * @param receiverINN ��� ����������
	 */
	void setReceiverINN(String receiverINN);

	/**
	 * ���������� ��� ����������
	 * @param receiverKPP ��� ����������
	 */
	void setReceiverKPP(String receiverKPP);

	/**
	 * ���������� ���� ����������
	 * @param receiverBank ���� ����������
	 */
	void setReceiverBank(ResidentBank receiverBank);

	/**
     * @return ���������� ���� ����������
     */
	ResidentBank getReceiverTransitBank();

	/**
	 * @return ������� ���������� ��� ��������� ��������
	 */
	String getReceiverPhone();

	/**
	 * ���������� ������� ���������� ��� ��������� ��������
	 * @param receiverPhone ������� ����������
	 */
	void setReceiverPhone(String receiverPhone);

	/**
	 * @return ��� ���������� ��� ������ � ����
	 */
	String getReceiverNameForBill();

	/**
	 * ���������� ��� ���������� ��� ������ � ����
	 * @param receiverNameForBill ��� ���������� ��� ������ � ����
	 */
	void setReceiverNameForBill(String receiverNameForBill);

	/**
	 * @return ������� ���������������� ������������ ���������� ���������� ���������� ������������.
	 */
	boolean isNotVisibleBankDetails();

	/**
	 * ���������� ������� ���������������� ������������ ���������� ���������� ���������� ������������.
	 * @param notVisibleBankDetails ������� ���������������� ������������ ���������� ���������� ���������� ������������.
	 */
	void setNotVisibleBankDetails(boolean notVisibleBankDetails);

	/**
     * @return ��� �����, � ������� �������� ������� � �����������.
     */
	Code getReceiverOfficeCode();

    /**
     * ���������� ��� �����, � ������� �������� ������� � �����������.
     * @param code ��� �����, � ������� �������� ������� � �����������.
     */
	void setReceiverOfficeCode(Code code);

	/**
	 * ���������� �������������� ����
	 *
	 * @param extendedFields ������������� ������ ��� �����.
	 */

	void setExtendedFields(List<Field> extendedFields) throws DocumentException;

	/**
	 * �������� ������������� ������� �� ������� �������
	 * @return ������������� ������� �� ������� �������
	 */
	String getIdFromPaymentSystem();

	/**
	 * ���������� ������������� ������� �� ������� �������
	 * @param id ������������ ������� �� ������� �������
	 */
	void setIdFromPaymentSystem(String id);

	/**
	 * ���������� ��� ����� ����������.
	 * @param receiverPointCode ��� ����� ����������.
	 */
	void setReceiverPointCode(String receiverPointCode);

	/**
	 * ��������� ������ ����
	 * @return ����� ����
	 */
	String getSalesCheck();

	/**
	 * ���������� ����� ����
	 * @param salesCheck ����� ����
	 */
	void setSalesCheck(String salesCheck);
}