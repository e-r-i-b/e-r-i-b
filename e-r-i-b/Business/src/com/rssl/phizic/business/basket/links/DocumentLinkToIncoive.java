package com.rssl.phizic.business.basket.links;

import java.io.Serializable;

/**
 * ����� ��� ������������ � ���������.
 *
 * @author bogdanov
 * @ created 22.05.14
 * @ $Author$
 * @ $Revision$
 */

public class DocumentLinkToIncoive implements Serializable
{
	private Long loginId;
	private String documentType;
	private Long invoiceId;

	/**
	 * ������� �����.
	 */
	public DocumentLinkToIncoive() {}

	/**
	 * @param loginId ������������� ������.
	 * @param documentType ��� ���������.
	 * @param invoiceId ������������� �������.
	 */
	public DocumentLinkToIncoive(Long loginId, String documentType, Long invoiceId)
	{
		this.loginId = loginId;
		this.documentType = documentType;
		this.invoiceId = invoiceId;
	}

	/**
	 * @return ��� ���������.
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType ��� ���������.
	 */
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return ������������� �������.
	 */
	public Long getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId ������������� �������.
	 */
	public void setInvoiceId(Long invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return ������������� ������.
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId ������������� ������.
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}
}
