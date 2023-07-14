package com.rssl.phizicgate.rsV55.bankroll;

import com.rssl.phizic.common.types.Currency;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 17.01.2007 Time: 9:37:33 To change this template use File |
 * Settings | File Templates.
 */
public class ReceiverTransaction
{
	private String   operationId;      // ID
    private String   counteragent;    // ����������
	private String   receiverAccount; // ���� ����������
	private String   receiverBank;    // ���� ����������
	private String   receiverBIC;     // ��� ����� ����������
//	private Currency receiverCurrency; // ������ ����������

	String getOperationId()
    {
        return operationId;
    }

	void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }
	
    public String getCounteragent()
	{
		return counteragent;
	}

	public void setCounteragent(String counteragent)
	{
		this.counteragent = counteragent;
	}
	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverBank()
	{
		return receiverBank;
	}

	public void setReceiverBank(String receiverBank)
	{
		this.receiverBank = receiverBank;
	}

	public String getReceiverBIC()
	{
		return receiverBIC;
	}

	public void setReceiverBIC(String receiverBIC)
	{
		this.receiverBIC = receiverBIC;

		
	}

}
