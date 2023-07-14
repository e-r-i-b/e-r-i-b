package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.ServiceImpl;
import com.rssl.phizic.gate.payments.systems.recipients.Service;

/**
 * @author Mescheryakova
 * @ created 08.12.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class BillingServiceProviderBase  extends ServiceProviderBase
{

	private String  codeService;
	private boolean ground;
	private AccountType accountType;
	private Billing billing;
	private PaymentService paymentService;
	private String  attrDelimiter;
	private String  attrValuesDelimiter;
	private String nameService;
	private String codeRecipientSBOL;
	private String alias;
	private String legalName;
	private String nameOnBill;
	private boolean federal;


	/**
	 * ��� ������,
	 * ������� ��������������� ������ ����������� �����
	 * @return - ���
	 */
	public String getCodeService()
	{
		return codeService;
	}

	public Service getService()
	{
		return new ServiceImpl(getCodeService(), getNameService());
	}	

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	/**
	 * ������� ������������� ����������� ���������� �������
	 * @return - true (�����������)
	 */
	public boolean isGround()
	{
		return ground;
	}

	public void setGround(boolean ground)
	{
		this.ground = ground;
	}

	/**
	 * ��� ����� ��� ������
	 * @return - ��� ����� ��� ������
	 */
	public AccountType getAccountType()
	{
		return accountType;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	/**
	 * ��������������� ������
	 * @return - ������
	 */
	public PaymentService getPaymentService()
	{
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService)
	{
		this.paymentService = paymentService;
	}	

	/**
	 * ����������� �������
	 * @return - ����������� �������
	 */
	public Billing getBilling()
	{
		return billing;
	}

	public void setBilling(Billing billing)
	{
		this.billing = billing;
	}

	/**
	 * ����������� �������������� ��������� ���������� �����
	 * (���������� �������)
	 * @return - �����������
	 */
	public String getAttrDelimiter()
	{
		return attrDelimiter;
	}

	public void setAttrDelimiter(String attrDelimiter)
	{
		this.attrDelimiter = attrDelimiter;
	}

	/**
	 * ����������� �������� �������������� ��������� ���������� �����
	 * (���������� �������)
	 * @return - �����������
	 */
	public String getAttrValuesDelimiter()
	{
		return attrValuesDelimiter;
	}

	public void setAttrValuesDelimiter(String attrValuesDelimiter)
	{
		this.attrValuesDelimiter = attrValuesDelimiter;
	}


	/**
	 * @return �������� ������ �� ����������� �������
	 */
	public String getNameService()
	{
		return nameService;
	}

	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	/**
	 * ��� ��� ����������� ������ ��� ����������� � �����.
	 * ������ ������ ����������� � ����� ��������� CodeRecipientSBOL ����� � ����� ������������ ��� ���� ���������
	 * @return ��� ��� ����������� ������ ��� ����������� � ����
	 */
	public String getCodeRecipientSBOL()
	{
		return codeRecipientSBOL;
	}

	public void setCodeRecipientSBOL(String codeRecipientSBOL)
	{
		this.codeRecipientSBOL = codeRecipientSBOL;
	}

	/**
	 * ���������(�) ����������, ���� ��������� - ����������� ����� �������.
	 * ������������ � ���� ������ ��� ������ ���������� ����� �� ������������ (�� ���������, ��������� �������������)
	 * ��� ������ � ����� � ��� �� CodeRecipientSBOL ������ ����� ���� � �� �� �������� �������� Alias
	 * @return ���������(�) ����������
	 */
	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return ����������� ������������ ���������� �����
	 * (������������ � ���� ��� ������ ����������, ����������, ���������� � Alias)
	 */
	public String getLegalName()
	{
		return legalName;
	}

	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	/**
	 * @return ������������ ����������, ��������� � ����
	 */
	public String getNameOnBill()
	{
		return nameOnBill;
	}

	public void setNameOnBill(String nameOnBill)
	{
		this.nameOnBill = nameOnBill;
	}


	/**
	 * ������� ������������ ���������� �����.
	 * ���� ������� �0� ��� ��� �����������, �� ��������� �� �����������.
	 * ���� ������� �1� - �����������.
	 * ��� ������ � ����� � ��� �� CodeRecipientSBOL ������ ����� ���� � �� �� �������� �������� IsFederal
	 * @return 0/1
	 */
	public boolean isFederal()
	{
		return federal;
	}

	public void setFederal(boolean federal)
	{
		this.federal = federal;
	}
}
