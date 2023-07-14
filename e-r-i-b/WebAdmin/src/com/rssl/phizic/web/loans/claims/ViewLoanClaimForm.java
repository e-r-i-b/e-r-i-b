package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author gladishev
 * @ created 04.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class ViewLoanClaimForm extends EditFormBase
{
	private StringBuffer html;
	private BusinessDocument document;
	private boolean      comment; //true - можно добавить/изменить комментарий
	private boolean      archive;
	private boolean      printDocuments;

	public StringBuffer getHtml()
	{
		return html;
	}

	public void setHtml(StringBuffer html)
	{
		this.html = html;
	}

	public boolean isArchive()
	{
		return archive;
	}

	public void setArchive(boolean archive)
	{
		this.archive = archive;
	}

	public boolean isPrintDocuments()
	{
		return printDocuments;
	}

	public void setPrintDocuments(boolean printDocuments)
	{
		this.printDocuments = printDocuments;
	}

	public boolean isComment()
	{
		return comment;
	}

	public void setComment(boolean comment)
	{
		this.comment = comment;
	}

	public BusinessDocument getDocument()
	{
		return document;
	}

	public void setDocument(BusinessDocument document)
	{
		this.document = document;
	}
}
