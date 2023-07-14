package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author Mescheryakova
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningLicenseForm  extends FilterActionForm
{
	private Long documentId;
	private Long imaProductId;
	private String contractTemplate;

	public Long getImaProductId()
	{
		return imaProductId;
	}

	public void setImaProductId(Long imaProductId)
	{
		this.imaProductId = imaProductId;
	}

	public String getContractTemplate()
	{
		return contractTemplate;
	}

	public void setContractTemplate(String contractTemplate)
	{
		this.contractTemplate = contractTemplate;
	}

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
	}
}
