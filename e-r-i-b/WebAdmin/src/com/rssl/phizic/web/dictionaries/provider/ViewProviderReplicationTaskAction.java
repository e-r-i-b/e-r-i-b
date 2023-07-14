package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ReplicateProvidersBackgroundTask;
import com.rssl.phizic.business.operations.background.BackgroundTask;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;
import com.rssl.phizic.operations.dictionaries.provider.ViewReplicateProvidersBackroundTaskOperation;
import com.rssl.phizic.web.common.background.ViewBackgroundTaskAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 26.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewProviderReplicationTaskAction extends ViewBackgroundTaskAction
{
	private UnloadReplicationTaskReportAction downloadAction;
	private DownloadProviderReplicationReportAction downloadReportAction;

	public ViewProviderReplicationTaskAction()
	{
		downloadAction = new UnloadReplicationTaskReportAction()
		{
			@Override
			protected String getReport(BackgroundTask task)
			{
				ReplicateProvidersBackgroundTask backgroundTask = ((ReplicateProvidersBackgroundTask) task);
				return backgroundTask.getResult() != null ? backgroundTask.getResult().getReport() : null;
			}

			@Override
			protected ViewBackgroundTaskOperationBase createEntityOperation() throws BusinessException
			{
				return createOperation(ViewReplicateProvidersBackroundTaskOperation.class);
			}
		};
		downloadReportAction = new DownloadProviderReplicationReportAction();
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.download.replication.report", "downloadReport");
		map.put("download.file", "download");
		return map;
	}

	public ActionForward downloadReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return downloadAction.unload(mapping, form, request, response);
	}

	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return downloadReportAction.download(mapping, form, request, response);
	}
}
