package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Òýã "operation"
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"date", "sum", "description", "docNumber", "operationType", "corrAccount", "correspondent", "balance"})
@XmlRootElement(name = "operation")
public class OperationTag
{
	private String date;
	private MoneyTag sum;
	private String description;
	private String docNumber;
	private String operationType;
	private String corrAccount;
	private String correspondent;
	private MoneyTag balance;

	@XmlElement(name = "date", required = true)
	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	@XmlElement(name = "sum", required = false)
	public MoneyTag getSum()
	{
		return sum;
	}

	public void setSum(MoneyTag sum)
	{
		this.sum = sum;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "description", required = true)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@XmlElement(name = "docNumber", required = false)
	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	@XmlElement(name = "operationType", required = false)
	public String getOperationType()
	{
		return operationType;
	}

	public void setOperationType(String operationType)
	{
		this.operationType = operationType;
	}

	@XmlElement(name = "corrAccount", required = false)
	public String getCorrAccount()
	{
		return corrAccount;
	}

	public void setCorrAccount(String corrAccount)
	{
		this.corrAccount = corrAccount;
	}

	@XmlElement(name = "correspondent", required = false)
	public String getCorrespondent()
	{
		return correspondent;
	}

	public void setCorrespondent(String correspondent)
	{
		this.correspondent = correspondent;
	}

	@XmlElement(name = "balance", required = false)
	public MoneyTag getBalance()
	{
		return balance;
	}

	public void setBalance(MoneyTag balance)
	{
		this.balance = balance;
	}
}
