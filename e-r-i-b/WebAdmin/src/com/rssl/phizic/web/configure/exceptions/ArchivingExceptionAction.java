package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.operations.config.exceptions.ArchivingExceptionOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.download.DownloadAction;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 24.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен настройки автоматической архивации отчетов об ошибках и ручной выгрузки отчета за период
 */

public class ArchivingExceptionAction extends EditPropertiesActionBase
{
	private static final String ARCHIVING_EXCEPTION_FILE_TYPE = "ArchivingException";
	private static final String REPORT_FILE_TYPE = ".csv";
	private static final Map<ExceptionEntryType, String> fileNames;

	static
	{
		fileNames = new HashMap<ExceptionEntryType, String>(2);
		fileNames.put(ExceptionEntryType.internal, "Report_System_error_");
		fileNames.put(ExceptionEntryType.external, "Report_external_error_");
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.unload", "unload");
		return map;
	}

	@Override
	protected EditPropertiesOperation getEditOperation() throws BusinessException
	{
		return createOperation(ArchivingExceptionOperation.class);
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ArchivingExceptionForm frm = (ArchivingExceptionForm) form;
		ArchivingExceptionOperation operation = (ArchivingExceptionOperation)createViewOperation(frm);
		return doStart(operation, frm);
	}

	private ActionForward doStart(ArchivingExceptionOperation operation, ArchivingExceptionForm frm) throws Exception
	{
		frm.setArchivePath(operation.getArchivePath());
		updateForm(frm, operation.getEntity());
		updateFormAdditionalData(frm, operation);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	/**
	 * выгрузка файла отчета
	 * @param mapping  стратс-маппинг
	 * @param form     стратс-форма
	 * @param request  запрос
	 * @param response ответ
	 * @return форвард на успешное выполнение операции либо на "старт", если произошла ошибка
	 * @throws Exception
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ArchivingExceptionForm frm = (ArchivingExceptionForm) form;
		ArchivingExceptionOperation operation = (ArchivingExceptionOperation)createViewOperation(frm);
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), ArchivingExceptionForm.ADDITIONAL_FORM);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return doStart(operation, frm);
		}
		Map<String, Object> result = processor.getResult();
		Calendar date = DateHelper.toCalendar((Date) result.get(ArchivingExceptionForm.UNLOADING_DATE_PARAMETER_NAME));
		ExceptionEntryType type = (ExceptionEntryType) result.get(ArchivingExceptionForm.EXCEPTION_TYPE_PARAMETER_NAME);
		byte[] reportData = operation.getReport(date, type);
		if (reportData == null || reportData.length == 0)
		{
			saveError(request, "Отчет по указанным параметрам поиска отсутствует. Пожалуйста, укажите другие параметры.");
			return doStart(operation, frm);
		}
		String clientFileName = StringHelper.getEmptyIfNull(fileNames.get(type)).concat((String) frm.getField(ArchivingExceptionForm.UNLOADING_DATE_PARAMETER_NAME)).concat(REPORT_FILE_TYPE);
		DownloadAction.unload(reportData, ARCHIVING_EXCEPTION_FILE_TYPE, clientFileName, frm, request);
		return doStart(operation, frm);
	}
}
