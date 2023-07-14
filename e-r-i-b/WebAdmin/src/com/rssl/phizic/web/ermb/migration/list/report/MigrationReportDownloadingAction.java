package com.rssl.phizic.web.ermb.migration.list.report;

import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.web.actions.DownloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.File;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 28.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн выгрузки отчетов для миграции
 */
public class MigrationReportDownloadingAction extends DownloadOperationalActionBase<File>
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
	}

	@Override
	protected void outData(File data, ServletOutputStream outputStream) throws Exception
	{
		byte[] bytes = FileHelper.readTempDirectoryFile(data.getName());
		outputStream.write(bytes);
		outputStream.flush();
		outputStream.close();
		if (!data.delete())
			log.info("Не удалось удалить временный файл выгрузки ( " + data.getName() + " ).");
	}
}

