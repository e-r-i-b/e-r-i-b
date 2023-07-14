package com.rssl.phizic.test.web.atm.payments;

/**
 * @author khudyakov
 * @ created 14.01.15
 * @ $Author$
 * @ $Revision$
 */
public class TestATMP2PAutoTransferClaimForm extends TestATMRurPaymentForm
{
	private String documentNumber;    //����� ���������
	private String documentDate;      //���� ���������
	private String fromResource;      //�����
	private String toResource;        //������ ����������
	private String destinationAmount; //����� ����������
	private String autoSubNumber;     //������������� ������������


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

	public String getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(String destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	public String getAutoSubNumber()
	{
		return autoSubNumber;
	}

	public void setAutoSubNumber(String autoSubNumber)
	{
		this.autoSubNumber = autoSubNumber;
	}
}
