package com.rssl.phizic.business.documents.forms;

import java.util.Set;

/**
 * @author khudyakov
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */
public class FormInfo
{
	private String webRoot;
	private String resourceRoot;
	private String skinUrl;
	private String additionMobileInfo;
	private boolean ajax = false;
	private Set<String> changedFields;


	public FormInfo() {}

	public FormInfo(String additionMobileInfo)
	{
		this.additionMobileInfo = additionMobileInfo;
	}

	public FormInfo(Set<String> changedFields)
	{
		this.changedFields = changedFields;
	}

	public FormInfo(String webRoot, String resourceRoot)
	{
		this.webRoot = webRoot;
		this.resourceRoot = resourceRoot;
	}

	public FormInfo(String webRoot, String resourceRoot, String skinUrl)
	{
		this.webRoot = webRoot;
		this.resourceRoot = resourceRoot;
		this.skinUrl = skinUrl;
	}

	public FormInfo(String webRoot, String resourceRoot, String skinUrl, String additionMobileInfo)
	{
		this(webRoot, resourceRoot, skinUrl);

		this.additionMobileInfo = additionMobileInfo;
	}

	public FormInfo(String webRoot, String resourceRoot, String skinUrl, boolean ajax)
	{
		this(webRoot, resourceRoot, skinUrl);

		this.ajax = ajax;
	}

	public String getWebRoot()
	{
		return webRoot;
	}

	public String getResourceRoot()
	{
		return resourceRoot;
	}

	public String getSkinUrl()
	{
		return skinUrl;
	}

	public String getAdditionMobileInfo()
	{
		return additionMobileInfo;
	}

	public boolean isAjax()
	{
		return ajax;
	}

	public Set<String> getChangedFields()
	{
		return changedFields;
	}
}
