package com.rssl.phizic.web.ermb.migration.list.start;

import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.operations.ListSegmentOperation;
import com.rssl.phizic.business.ermb.migration.list.operations.StartListMigrationOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн запуска миграции МБК/МБВ в ЕРИБ
 * @author Puzikov
 * @ created 05.12.13
 * @ $Author$
 * @ $Revision$
 */

public class StartMigrationAction extends OperationalActionBase
{
	private static final String FORWARD_REPORT = "Report";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.migration", "migrate");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		StartMigrationForm frm = (StartMigrationForm) form;

		ListSegmentOperation segmentOperation = createOperation(ListSegmentOperation.class);
		List<Segment> segments = segmentOperation.getAllSegments();

		StartListMigrationOperation operation = createOperation(StartListMigrationOperation.class);
		operation.initialize(segments);
		frm.setData(operation.getSegments());

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward migrate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		StartListMigrationOperation operation = createOperation(StartListMigrationOperation.class);
		StartMigrationForm frm = (StartMigrationForm) form;

		String[] ids = frm.getSelectedData();
		List<Segment> segments = new ArrayList<Segment>();
		for (String id : ids)
		{
			segments.add(Segment.fromValue(id));
		}

		String status = operation.migrate(segments);
		frm.setStatus(status);

		return mapping.findForward(FORWARD_REPORT);
	}
}
