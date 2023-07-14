package com.rssl.phizic.business.basket.links;

import java.io.Serializable;

/**
 * Сявзь ДУЛ пользователя с подпиской.
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
	 * Создать связь.
	 */
	public DocumentLinkToIncoive() {}

	/**
	 * @param loginId идентификатор логина.
	 * @param documentType тип документа.
	 * @param invoiceId идентификатор инвойса.
	 */
	public DocumentLinkToIncoive(Long loginId, String documentType, Long invoiceId)
	{
		this.loginId = loginId;
		this.documentType = documentType;
		this.invoiceId = invoiceId;
	}

	/**
	 * @return тип документа.
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType тип документа.
	 */
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return идентификатор инвойса.
	 */
	public Long getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId идентификатор инвойса.
	 */
	public void setInvoiceId(Long invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return идентификатор логина.
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId идентификатор логина.
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}
}
