package com.rssl.phizic.test.web.mobile.payments;

/**
 * User: Moshenko
 * Date: 15.02.2012
 * Time: 15:31:21
 */
public class TestMobileAccountClosingPaymentForm extends TestMobileDocumentForm
{
	String documentNumber;    //Номер документа
	String documentDate;      //Дата документа
	String fromResource;      //Вклад 
	String toResource;        //Ресурс зачисления
	String course;            //Курс конверсии
	String closingDate;       //Дата закрытия
	String amount;   //Сумма списания
	String destinationAmount; //Сумма зачисления
	String operationCode;     //Код валютной операции

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(String documentDate)
	{
		this.documentDate = documentDate;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public String getToResource()
	{
		return toResource;
	}

	public void setToResource(String toResource)
	{
		this.toResource = toResource;
	}

	public String getCourse()
	{
		return course;
	}

	public void setCourse(String course)
	{
		this.course = course;
	}

	public String getClosingDate()
	{
		return closingDate;
	}

	public void setClosingDate(String closingDate)
	{
		this.closingDate = closingDate;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	public String getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(String destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	public String getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(String operationCode)
	{
		this.operationCode = operationCode;
	}
}
