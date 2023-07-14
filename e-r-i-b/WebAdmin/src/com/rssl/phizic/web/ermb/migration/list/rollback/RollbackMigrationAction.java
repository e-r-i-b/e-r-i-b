package com.rssl.phizic.web.ermb.migration.list.rollback;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.operations.RollbackListMigrationOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн отката миграции МБК/МБВ в ЕРИБ
 * @author Puzikov
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RollbackMigrationAction extends OperationalActionBase
{
	private static final String FORWARD_REPORT = "Report";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.rollback", "rollback");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward rollback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		RollbackListMigrationOperation operation = createOperation(RollbackListMigrationOperation.class);
		RollbackMigrationForm frm = (RollbackMigrationForm) form;
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(mapValuesSource,RollbackMigrationForm.FORM);

		try
		{
			if(processor.process())
			{
				Map<String,Object> result = processor.getResult();
				Date date = (Date)result.get("date");
				Date time = (Date)result.get("time");
				Calendar rollbackDate = DateHelper.createCalendar(date, time);
				operation.initialize(rollbackDate);

				operation.rollback();
				frm.setStatus(operation.getStatus());
			}
			else
			{
				saveErrors(request,processor.getErrors());
				return mapping.findForward(FORWARD_START);
			}
		}
		catch (BusinessLogicException e)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_REPORT);
	}
}
