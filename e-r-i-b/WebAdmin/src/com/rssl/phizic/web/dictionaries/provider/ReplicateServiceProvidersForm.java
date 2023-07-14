package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.common.background.CreateBackgroundTaskFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author hudyakov
 * @ created 24.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ReplicateServiceProvidersForm extends CreateBackgroundTaskFormBase
{
	private String[] selectedIds = new String[]{};
	private FormFile file;

	public String[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}
}
