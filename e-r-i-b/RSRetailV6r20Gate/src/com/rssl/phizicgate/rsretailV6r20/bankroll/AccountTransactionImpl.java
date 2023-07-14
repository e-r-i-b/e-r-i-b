package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.gate.bankroll.AccountTransaction;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Kidyaev
 * @ created 13.10.2005
 * @ $Author: danilov $
 * @ $Revision$
 */
public class AccountTransactionImpl implements AccountTransaction
{
    private String   operationId;      // ID
    private long     accountId;        // Номер счета
    private Calendar date;             // Дата операции
	private Money creditSum;        // Сумма прихода, выраженная типом Money
    private Money    debitSum;         // Сумма расхода, выраженная типом Money
    private Money    balance;          // Остаток, выраженный типом Money
	private String counteragent;       // Контрагент
	private ReceiverTransaction receiver; // получатель
    private String   description;      // Основание операции
	private long     dayNumber;        // Номер транзакции за день (вспомогательное поле)
	private String counteragentAccount;// Номер счета корреспондента
	private String bookAccount;        // Номер Счетa контировки
	private String counteragentBank;   // Банк корреспондента, текстовое описание (например наименование + БИК)
	private String counteragentBankName; // Банк корреспондента, наименование
	private String counteragentCorAccount;      // Корр. Счет банка-корреспондента
	private String documentNumber;   // Номер документа
    private String operationCode;    // Шифр (вид) операции
	private String recipient;                      // Наименование получателя/плательщик

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
		return counteragentAccount;
	}

	void setCounteragentAccount(String counteragentAccount)
	{
		this.counteragentAccount = counteragentAccount;
	}

	public String getBookAccount()
	{
		return bookAccount;
	}

	void setBookAccount(String bookAccount)
	{
		this.bookAccount = bookAccount;
	}

	public String getCounteragentBank()
	{
		return counteragentBank;
	}

	void setCounteragentBank(String counteragentBank)
	{
		this.counteragentBank = counteragentBank;
	}

	public String getCounteragentBankName()
	{
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

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}
}
