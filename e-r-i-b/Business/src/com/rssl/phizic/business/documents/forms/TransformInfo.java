package com.rssl.phizic.business.documents.forms;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TransformInfo
{
	private String templateMode;
	private String transformMode;

	public TransformInfo(String templateMode, String transformMode)
	{
		this.templateMode  = templateMode;
		this.transformMode = transformMode;
	}

	public String getTemplateMode()
	{
		return templateMode;
	}

	public String getTransformMode()
	{
		return transformMode;
	}
}
