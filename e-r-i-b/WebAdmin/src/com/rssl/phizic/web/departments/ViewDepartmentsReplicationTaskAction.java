package com.rssl.phizic.web.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicateDepartmentsBackgroundTask;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsTaskResult;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;
import com.rssl.phizic.operations.ext.sbrf.departments.ViewReplicateDepartmentsBackroundTaskOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.background.ViewBackgroundTaskAction;
import com.rssl.phizic.web.dictionaries.provider.UnloadReplicationTaskReportAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн просмотра фоновой задачи репликации подразделений
 * @author niculichev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ViewDepartmentsReplicationTaskAction extends ViewBackgroundTaskAction
{
	private UnloadReplicationTaskReportAction unloadAction;

	public ViewDepartmentsReplicationTaskAction()
	{
		unloadAction = new UnloadReplicationTaskReportAction()
		{
			@Override
			protected String getReport(BackgroundTask task)
			{
				ReplicateDepartmentsBackgroundTask backgroundTask = ((ReplicateDepartmentsBackgroundTask) task);
				ReplicationDepartmentsTaskResult result = backgroundTask.getResult();

				StringBuilder builder = new StringBuilder();

				builder.append("Всего обработано: ")
						.append(result.getSourceTotalRecordsCount() == null ? 0 : result.getSourceTotalRecordsCount()).append("\n\n");

				long destinationInseredRecordsCount = result.getDestinationInseredRecordsCount() == null ? 0 : result.getDestinationInseredRecordsCount();
				builder.append("Добавлено: ").append(destinationInseredRecordsCount).append("\n");
				if(destinationInseredRecordsCount > 0)
					builder.append("Из них:").append("\n").append(result.getDestinationInseredRecordsReport()).append("\n\n");

				long destinationUpdatedRecordsCount = result.getDestinationUpdatedRecordsCount() == null ? 0 : result.getDestinationUpdatedRecordsCount();
				builder.append("Обновлено: ").append(
						result.getDestinationUpdatedRecordsCount() == null ? 0 : result.getDestinationUpdatedRecordsCount()).append("\n");
				if(destinationUpdatedRecordsCount > 0)
					builder.append("Из них:").append("\n").append(result.getDestinationUpdatedRecordsReport()).append("\n\n");

				if(StringHelper.isNotEmpty(result.getErrorFormatReport())
						|| StringHelper.isNotEmpty(result.getDestinationInseredDecentrRecordsReport())
						|| StringHelper.isNotEmpty(result.getErrorParentReport())
						|| StringHelper.isNotEmpty(result.getReport()))
				{
					builder.append("Ошибки загрузки:").append("\n");
					builder.append(result.getErrorFormatReport()).append("\n");
					builder.append(result.getDestinationInseredDecentrRecordsReport()).append("\n");
					builder.append(result.getErrorParentReport()).append("\n");
					builder.append(result.getReport()).append("\n");
				}

				return builder.toString();
			}

			@Override
			protected ViewBackgroundTaskOperationBase createEntityOperation() throws BusinessException
			{
				return createOperation(ViewReplicateDepartmentsBackroundTaskOperation.class);
			}
		};
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.download.replication.report", "downloadReport");
		return map;
	}

	public ActionForward downloadReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return unloadAction.unload(mapping, form, request, response);
	}
}
