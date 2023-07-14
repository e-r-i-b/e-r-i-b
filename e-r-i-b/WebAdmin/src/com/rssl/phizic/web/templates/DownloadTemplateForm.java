package com.rssl.phizic.web.templates;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 30.01.2007 Time: 16:01:05 To change this template use File
 * | Settings | File Templates.
 */
public class DownloadTemplateForm extends ActionFormBase
{
	private Long   documentId;

	public void setId(Long documentId)
	{
		this.documentId = documentId;
	}

	public Long getId()
	{
		return this.documentId;
	}

}
