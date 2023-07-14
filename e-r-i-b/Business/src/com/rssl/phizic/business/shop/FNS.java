package com.rssl.phizic.business.shop;

/**
 * @author Mescheryakova
 * @ created 06.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 *  �����, ����������� ���� ��� ������ �� ���
 */

public class FNS  implements OrderInfo
{
	private String KBK;
	private String OKATO;
	private String indexTaxationDocument;    // ������ ���������� ���������
	private String paymentGround;            // ��������� �������
	private String paymentType;              // ��� ���������� �������
	private String taxStatus;                 // ������ �����������
	private Order order;                     // �������� ���� ������
	private Long id;
	private String period;            // ������ ���������� �������
	private String payerINN;          // ��� �����������

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKBK()
	{
		return KBK;
	}

	public void setKBK(String KBK)
	{
		this.KBK = KBK;
	}

	public String getOKATO()
	{
		return OKATO;
	}

	public void setOKATO(String OKATO)
	{
		this.OKATO = OKATO;
	}

	public String getIndexTaxationDocument()
	{
		return indexTaxationDocument;
	}

	public void setIndexTaxationDocument(String indexTaxationDocument)
	{
		this.indexTaxationDocument = indexTaxationDocument;
	}

	public String getPaymentGround()
	{
		return paymentGround;
	}

	public void setPaymentGround(String paymentGround)
	{
		this.paymentGround = paymentGround;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getTaxStatus()
	{
		return taxStatus;
	}

	public void setTaxStatus(String taxStatus)
	{
		this.taxStatus = taxStatus;
	}

	public String getPayerINN()
	{
		return payerINN;
	}

	public void setPayerINN(String payerINN)
	{
		this.payerINN = payerINN;
	}
}