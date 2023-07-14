package com.rssl.phizic.business.documents.forms;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author khudyakov
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DocumentContext
{
	private OperationData operationData;
	private BusinessDocument document;
	private Metadata metadata;

	public DocumentContext(BusinessDocument document, Metadata metadata)
	{
		this.document = document;
		this.metadata = metadata;
	}

	public DocumentContext(BusinessDocument document, Metadata metadata, OperationData operationData)
	{
		this.document = document;
		this.metadata = metadata;
		this.operationData = operationData;
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public Metadata getMetadata()
	{
		return metadata;
	}

	public OperationData getOperationData()
	{
		return operationData;
	}

	public void setOperationData(OperationData operationData)
	{
		this.operationData = operationData;
	}
}
