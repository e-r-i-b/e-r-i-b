package com.rssl.phizic.web.common.background;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.operations.background.TaskResult;

/**
 * @author krenev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreateBackgroundTaskFormBase extends EditFormBase
{
	private boolean background;
	private TaskResult result;

	public boolean isBackground()
	{
		return background;
	}

	public void setBackground(boolean background)
	{
		this.background = background;
	}

	public void setResult(TaskResult result)
	{
		this.result = result;
	}

	public TaskResult getResult()
	{
		return result;
	}
}
