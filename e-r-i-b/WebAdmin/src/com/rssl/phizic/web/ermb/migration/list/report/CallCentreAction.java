package com.rssl.phizic.web.ermb.migration.list.report;

import com.rssl.phizic.business.ermb.migration.list.operations.CallCentreMigrationOperation;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.ActionForm;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн отображени€ формы выгрузки отчета дл€ сотрудников call-центра
 */
public class CallCentreAction extends UnloadOperationalActionBase<File>
{
	@Override
	public Pair<String, File> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CallCentreMigrationOperation operation = createOperation(CallCentreMigrationOperation.class, "ErmbMigrationService");
		operation.setSegments(new String[] {"3_2_3"});
		String fileName = operation.createFileName();
		File file = operation.makeReport(fileName);
		return new Pair<String, File>(fileName, file);
	}
}
