package com.rssl.phizic.web.ext.sbrf.documents;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Evgrafov
 * @ created 03.04.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 3932 $
 */

public class CheckPaymentSignatureForm extends ActionFormBase
{
	private String            paymentId;
	private Metadata metadata;
	private DocumentSignature signature;
	private Boolean           success;
	private String            documentContent;

	public String getPaymentId()
	{
		return paymentId;
	}

	public void setPaymentId(String paymentId)
	{
		this.paymentId = paymentId;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public DocumentSignature getSignature()
	{
		return signature;
	}

	public void setSignature(DocumentSignature signature)
	{
		this.signature = signature;
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public String getDocumentContent()
	{
		return documentContent;
	}

	public void setDocumentContent(String documentContent)
	{
		this.documentContent = documentContent;
	}
}