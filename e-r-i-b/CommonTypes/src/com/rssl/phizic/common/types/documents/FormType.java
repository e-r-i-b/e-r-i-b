package com.rssl.phizic.common.types.documents;

import java.util.HashMap;
import java.util.Map;

/**
 * ��� ����� �������
 *
 * @author khudyakov
 * @ created 28.04.2013
 * @ $Author$
 * @ $Revision$
 */
public enum FormType
{
	INTERNAL_TRANSFER("InternalPayment", "������� ����� ������ ������� � �������"),                                                         //������� ����� ����� �������/�������
	CONVERT_CURRENCY_TRANSFER("ConvertCurrencyPayment", "����� �����"),                                                                     //����� �����
	IMA_PAYMENT("IMAPayment", "�������/������� �������"),                                                                                   //�������/������� �������
	LOAN_PAYMENT("LoanPayment", "��������� �������"),                                                                                       //��������� �������
	INDIVIDUAL_TRANSFER("RurPayment", "������� �������� ����"),                                                                             //������� �������� ����
	INDIVIDUAL_TRANSFER_NEW("NewRurPayment", "������� �������� ����"),                                                                             //������� �������� ����
	EXTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "������ �����"),                                                                        //������ ����� �� ������������ ����������
	INTERNAL_PAYMENT_SYSTEM_TRANSFER("RurPayJurSB", "������ �����"),                                                                        //������ ����� ���. ���������� �� ����� ��
	JURIDICAL_TRANSFER("JurPayment", "������� �����������"),                                                                                //������ ����� �������� �����������
	SECURITIES_TRANSFER_CLAIM("SecuritiesTransferClaim", "��������� �� �������/����� �������� ������ �����"),                               //��������� �� �������/����� �������� ������ �����
	CREATE_P2P_AUTO_TRANSFER_CLAIM("CreateP2PAutoTransferClaim", "������ �� �������� ������������ P2P"),                                    //������ �� �������� ������������ �����-�����
	EDIT_P2P_AUTO_TRANSFER_CLAIM("EditP2PAutoTransferClaim", "������ �������������� ������������ P2P"),                                     //������ �� �������������� ������������ �����-�����
	CLOSE_P2P_AUTO_TRANSFER_CLAIM("CloseP2PAutoTransferClaim", "������ �������� ������������ P2P"),                                         //������ �� �������� ������������ �����-�����
	DELAY_P2P_AUTO_TRANSFER_CLAIM("DelayP2PAutoTransferClaim", "������ ������������ ������������ P2P"),                                     //������ �� ������������ ������������ �����-�����
	RECOVERY_P2P_AUTO_TRANSFER_CLAIM("RecoveryP2PAutoTransferClaim", "������ ������������� ������������ P2P"),                              //������ �� ������������ ������������ �����-�����
	CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("CreateMoneyBoxPayment", "������ �� �������� �������"),                                         //������ �� �������� �������
	EDIT_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("EditMoneyBoxClaim", "������ �� �������������� �������"),                                  //������ �� �������������� �������
	CLOSE_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("CloseMoneyBoxPayment", "������ �� ���������� �������"),                                  //������ �� �������� �������
	REFUSE_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("RefuseMoneyBoxPayment", "������ �� ������������ �������"),                              //������ �� ������������ �������
	RECOVER_CARD_TO_ACCOUNT_AUTO_SUBSCRIPTION_CLAIM("RecoverMoneyBoxPayment", "������ �� ������������� �������"),                           //������ �� ������������� �������
	CREATE_INVOICE_SUBSCRIPTION_CLAIM("CreateInvoiceSubscriptionPayment", "������ �� �������� ����������"),                                //������ �� �������� ����������
	EDIT_INVOICE_SUBSCRIPTION_CLAIM("EditInvoiceSubscriptionClaim", "������ �� �������������� ����������"),                                 //������ �� �������������� ����������
	EXTENDED_LOAN_CLAIM("ExtendedLoanClaim", "����������� ������ �� ������");

	private String name;
	private String description;

	FormType(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	/**
	 * @return �������� ����� ������� (*.pfd.xml ��� name)
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return �������� ������� (*.pfd.xml ��� description)
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * ������� �������������� �������� �������
	 * @param formType ��� �����
	 * @return true ������������
	 */
	public static boolean isTemplateSupported(FormType formType)
	{
		return INDIVIDUAL_TRANSFER == formType || INDIVIDUAL_TRANSFER_NEW == formType || JURIDICAL_TRANSFER == formType
			|| SECURITIES_TRANSFER_CLAIM == formType || LOAN_PAYMENT == formType
			|| EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType	|| INTERNAL_PAYMENT_SYSTEM_TRANSFER == formType
			|| INTERNAL_TRANSFER == formType || CONVERT_CURRENCY_TRANSFER == formType || IMA_PAYMENT == formType;
	}

	/**
	 * �������� �����������, ������������� ��
	 * @param formType ��� �����
	 * @return true - ��
	 */
	public static boolean isPaymentSystemPayment(FormType formType)
	{
		return EXTERNAL_PAYMENT_SYSTEM_TRANSFER == formType	|| INTERNAL_PAYMENT_SYSTEM_TRANSFER == formType;
	}

	/**
	 * ������ ����������� ��� ����������
	 * @param formType ��� �����
	 * @return true - ��
	 */
	public static boolean isJurPayment(FormType formType)
	{
		return isPaymentSystemPayment(formType) || JURIDICAL_TRANSFER == formType;
	}

	/**
	 * ������� �������
	 * @param formType ��� �����
	 * @return true ������������
	 */
	public static boolean isExternalDocument(FormType formType)
	{
		return JURIDICAL_TRANSFER == formType || INDIVIDUAL_TRANSFER == formType || INDIVIDUAL_TRANSFER_NEW == formType
				|| isPaymentSystemPayment(formType);
	}

	/**
	 * ���������� �������
	 * @param formType ��� �����
	 * @return true, ���� ���� �� ���������� ��������
	 */
	public static boolean isInternalDocument (FormType formType)
	{
		return INTERNAL_TRANSFER == formType || CONVERT_CURRENCY_TRANSFER == formType
				|| IMA_PAYMENT == formType || LOAN_PAYMENT == formType;
	}

	/**
	 * ������������� ���������� � ����� ���� ��������
	 * @return formTypes ���� < ��� �����, ��� ����� >
	 */
	public static Map<String, String> getFormTypes()
	{
		Map<String, String> formTypes = new HashMap<String, String>();
		for (FormType type : FormType.values())
		{
			formTypes.put(type.name(), type.getName());
		}
		return formTypes;
	}
}
