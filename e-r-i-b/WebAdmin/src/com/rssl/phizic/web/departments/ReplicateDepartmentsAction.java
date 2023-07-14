package com.rssl.phizic.web.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsMode;
import com.rssl.phizic.business.operations.background.BackgroundOperation;
import com.rssl.phizic.operations.ext.sbrf.departments.ReplicateDepartmentsBackgroundOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.background.CreateBackgroundTaskActionBase;
import com.rssl.phizic.web.common.background.CreateBackgroundTaskFormBase;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн создания фоновой задачи репликацию подразделений
 * @author niculichev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsAction extends CreateBackgroundTaskActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReplicateDepartmentsForm frm = (ReplicateDepartmentsForm) form;
		ReplicateDepartmentsBackgroundOperation operation = createEntityOperation();
		// обновляем форму пришедшими выбранными подразделениями
		if(StringHelper.isNotEmpty(frm.getIds()))
			frm.setSelectedDepartments(operation.getSelectedDepartments(StrutsUtils.parseIds(frm.getIds())));

		return mapping.findForward(FORWARD_START);
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.replic.load", "doIt");

		return map;
	}

	protected BackgroundOperation createOperation(CreateBackgroundTaskFormBase form) throws BusinessException, BusinessLogicException
	{
		ReplicateDepartmentsForm frm = (ReplicateDepartmentsForm) form;
		ReplicateDepartmentsBackgroundOperation operation = createEntityOperation();
		try
		{
			operation.initialize(
					frm.getFile().getInputStream(),
					StrutsUtils.parseIds(frm.getSelectedIds()),
					ReplicationDepartmentsMode.valueOf(frm.getReplicationMode()));
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}

		return operation;
	}

	private ReplicateDepartmentsBackgroundOperation createEntityOperation() throws BusinessException
	{
		return createOperation(ReplicateDepartmentsBackgroundOperation.class);
	}

	protected ActionMessages validateFormData(CreateBackgroundTaskFormBase form)
	{
		ReplicateDepartmentsForm frm = (ReplicateDepartmentsForm)form;
		ActionMessages msgs = FileNotEmptyValidator.validate(frm.getFile());
		String selected = new String("selected");
		if (msgs.isEmpty() && selected.equals(frm.getType()) && (frm.getSelectedIds() == null || frm.getSelectedIds().length == 0))
		{
			ActionMessage message = new ActionMessage(getResourceMessage("departmentsBundle", "com.rssl.phizic.web.validators.error.empty.departments.list"), false);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}
}
