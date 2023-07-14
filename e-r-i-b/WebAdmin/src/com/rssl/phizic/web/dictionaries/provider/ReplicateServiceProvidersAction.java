package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.replication.providers.ServiceProviderReplicaDestination;
import com.rssl.phizic.business.dictionaries.replication.providers.ServiceProvidersSAXLoader;
import com.rssl.phizic.business.operations.background.BackgroundOperation;
import com.rssl.phizic.operations.dictionaries.provider.ReplicateServiceProvidersOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.background.CreateBackgroundTaskActionBase;
import com.rssl.phizic.web.common.background.CreateBackgroundTaskFormBase;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hudyakov
 * @ created 24.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class ReplicateServiceProvidersAction extends CreateBackgroundTaskActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.replic", "doIt");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation();
		ReplicateServiceProvidersForm frm = (ReplicateServiceProvidersForm) form;
		frm.setBackground(true);

		return mapping.findForward(FORWARD_START);
	}

	protected ActionMessages validateFormData(CreateBackgroundTaskFormBase form)
	{
		ReplicateServiceProvidersForm frm = (ReplicateServiceProvidersForm) form;
		FormFile file = frm.getFile();
		return FileNotEmptyValidator.validate(file);
	}

	protected BackgroundOperation createOperation(CreateBackgroundTaskFormBase form) throws BusinessException, BusinessLogicException
	{
		ReplicateServiceProvidersForm frm = (ReplicateServiceProvidersForm) form;
		ReplicateServiceProvidersOperation operation = createOperation();
		try
		{
			operation.initialize(frm.getFile().getInputStream(), StrutsUtils.parseIds(frm.getSelectedIds()), getSpecificProperties());
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		return operation;
	}

	private ReplicateServiceProvidersOperation createOperation() throws BusinessException
	{
		// без разницы с какого сервиса, лишь бы была доступна, остальное разрулим при репликации
		if(checkAccess(ReplicateServiceProvidersOperation.class, "ServiceProvidersFullReplicateService"))
		{
			return createOperation(ReplicateServiceProvidersOperation.class, "ServiceProvidersFullReplicateService");
		}
		else
		{
			return createOperation(ReplicateServiceProvidersOperation.class, "ServiceProvidersPartialReplicateService");
		}
	}
	
	private Properties getSpecificProperties() throws BusinessException
	{
		Properties properties = new Properties();

		String isFullAccess = Boolean.toString(checkAccess(ReplicateServiceProvidersOperation.class, "ServiceProvidersFullReplicateService"));
		String isPartialAccess = Boolean.toString(checkAccess(ReplicateServiceProvidersOperation.class, "ServiceProvidersPartialReplicateService"));
		String isRemoveAccess = Boolean.toString(PermissionUtil.impliesService("ServiceProvidersRemoveFullReplicateService"));

		String prefix = ServiceProvidersSAXLoader.ACCESS_LOAD_TYPE_PREFIX;
		properties.setProperty(prefix + ServiceProvidersSAXLoader.LoadType.FULL_REPLICATION,    isFullAccess);
		properties.setProperty(prefix + ServiceProvidersSAXLoader.LoadType.DELETE,              isPartialAccess);
		properties.setProperty(prefix + ServiceProvidersSAXLoader.LoadType.PARTIAL_REPLICATION, isPartialAccess);
		properties.setProperty(ServiceProviderReplicaDestination.ACCESS_REMOVE_SETTING_NAME,    isRemoveAccess);

		return properties;
	}
}
