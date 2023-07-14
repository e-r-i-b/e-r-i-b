package com.rssl.phizic.web.common.background;

import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author krenev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewBackgroundTaskForm extends EditFormBase
{
	private BackgroundTask task;

	public void setTask(BackgroundTask task)
	{
		this.task = task;
	}

	public BackgroundTask getTask()
	{
		return task;
	}

}
