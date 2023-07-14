package com.rssl.phizic.operations.dictionaries.provider;

import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.business.dictionaries.providers.ReplicateProvidersBackgroundTask;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;

/**
 * @author krenev
 * @ created 10.08.2011
 * @ $Author$
 * @ $Revision$
 * �������� ��������� ������� ������ ���������� �����������
 */
public class ViewReplicateProvidersBackroundTaskOperation extends ViewBackgroundTaskOperationBase<ReplicationTaskResult, ReplicateProvidersBackgroundTask>
{
	protected Class<ReplicateProvidersBackgroundTask> getBackgroundTaskClass()
	{
		return ReplicateProvidersBackgroundTask.class;
	}
}
