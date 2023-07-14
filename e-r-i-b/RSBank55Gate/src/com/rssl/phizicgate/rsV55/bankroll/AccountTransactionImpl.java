package com.rssl.phizicgate.rsV55.bankroll;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.bankroll.AccountTransaction;
import com.rssl.phizic.gate.bankroll.DepositTransaction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 13.10.2005
 * @ $Author: emakarov $
 * @ $Revision: 6181 $
 */
public class AccountTransactionImpl implements AccountTransaction, DepositTransaction
{
    private String   operationId;      // ID
    private long     accountId;        // ����� �����
    private Calendar date;             // ���� ��������
	private Money    creditSum;        // ����� �������, ���������� ����� Money
    private Money    debitSum;         // ����� �������, ���������� ����� Money
    private Money    balance;          // �������, ���������� ����� Money
	private String counteragent;       // ����������
	private ReceiverTransaction receiver; // ����������
    private String   description;      // ��������� ��������
	private long     dayNumber;        // ����� ���������� �� ���� (��������������� ����)
	private String counteragentAccount;// ����� ����� ��������������
	private String counteragentBank;   // ���� ��������������, ��������� �������� (�������� ������������ + ���)
	private String counteragentBankName; // ���� ��������������, ������������
	private String bookAccount;          // C��� ����������
	private String   counteragentCorAccount;      // ����. ���� �����-��������������
	private String   documentNumber;   // ����� ���������
    private String   operationCode;    // ���� (���) ��������
	private String recipient;             // ������������ ����������/����������

    String getOperationId()
    {
        return operationId;
    }

    void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }

    long getAccountId()
    {
        return accountId;
    }

    private void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    public Calendar getDate()
    {
        return date;
    }

    void setDate(Calendar date)
    {
        this.date = date;
    }

	public Money getDebitSum()
    {
        return debitSum;
    }

    void setDebitSum(Money debitSum)
    {
        this.debitSum = debitSum;
    }

    public Money getCreditSum()
    {
        return creditSum;
    }

    void setCreditSum(Money creditSum)
    {
        this.creditSum = creditSum;
    }

    public Money getBalance()
    {
        return balance;
    }

    void setBalance(Money balance)
    {
        this.balance = balance;
    }

    public String getCounteragent()
    {
	    if (counteragent == null)
			return ""; // ������� ��-�� ����, ��� freemaker ����� assertNotNull ��� �������������� null � ��������� ��������
	                   // ���� ��� �������� � setCounteragent(...) � ���� ����� ������� null-��������... ������� ���...
        return counteragent;
    }

    public void setCounteragent(String counteragent)
    {
        if ( counteragent == null )
        {
            this.counteragent = "";
        }
        else
        {
            int index         = counteragent.indexOf(0);
            this.counteragent = ( index == -1  ?  counteragent  : counteragent.substring(0, index) );
        }
    }

	public String getCounteragentAccount()
	{
		if (counteragentAccount == null)
			return ""; // ������� ��-�� ����, ��� freemaker ����� assertNotNull ��� �������������� � ��������� �������� 
		return counteragentAccount;
	}

	void setCounteragentAccount(String counteragentAccount)
	{
		this.counteragentAccount = counteragentAccount;
	}


	public String getCounteragentBank()
	{
		if (counteragentBank == null)
			return ""; // ������� ��-�� ����, ��� freemaker ����� assertNotNull ��� �������������� � ��������� ��������
		return counteragentBank;
	}

	void setCounteragentBank(String counteragentBank)
	{
		this.counteragentBank = counteragentBank;
	}

	public String getCounteragentBankName()
	{
		if (counteragentBankName == null)
			return ""; // ������� ��-�� ����, ��� freemaker ����� assertNotNull ��� �������������� � ��������� ��������
		return counteragentBankName;
	}

	void setCounteragentBankName(String counteragentBankName)
	{
		this.counteragentBankName = counteragentBankName;
	}

	public String getDescription()
    {
        return description;
    }

	void setDescription(String description)
    {
        this.description = description;
    }

	long getDayNumber()
    {
        return dayNumber;
    }

	private void setDayNumber(long dayNumber)
    {
        this.dayNumber = dayNumber;
    }

    void setReceiver(ReceiverTransaction receiver)
    {
        this.receiver = receiver;
    }

    public ReceiverTransaction getReceiver()
    {
        return receiver;
    }

	public String getCounteragentCorAccount()
	{
		return counteragentCorAccount;
	}

	public void setCounteragentCorAccount(String counteragentCorAccount)
	{
		this.counteragentCorAccount = counteragentCorAccount;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}

	public String getBookAccount()
	{
		return bookAccount;
	}

	public void setBookAccount(String bookAccount)
	{
		this.bookAccount = bookAccount;
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}
}
