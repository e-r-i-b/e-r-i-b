package com.rssl.phizic.web.configure;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.reports.BusinessReportConfig;
import com.rssl.phizic.logging.monitoring.BusinessReportHelper;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.EditBusinessReportParamsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Экшен редактирования параметров отчета по бизнес операциям
 * @author gladishev
 * @ created 12.02.2015
 * @ $Author$
 * @ $Revision$
 */

public class EditBusinessReportParamsAction extends EditPropertiesActionBase<EditBusinessReportParamsOperation>
{
	private static final String FILE_NAME = "fileName"; // параметр для записи имени файла.
	private static final String DOWNLOADED_FILE_NAME = "monitoring_report_%s.xls"; //название выгружаемого клиенту файла
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //название временного файла
	private static final String MESSAGE_MISSING_DATE = "За даты %s нет агрегированных данных. Учтите, что это может уменьшить точность расчета норм";

	@Override protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = super.getKeyMethodMap();
		keyMethodMap.put("button.unload", "unload");
		return keyMethodMap;
	}

	@Override
	protected EditBusinessReportParamsOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditBusinessReportParamsOperation.class, "BusinessReportParamsManagement");
	}

	/**
	 * Выгрузить отчеты
	 * @param mapping маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return форвард
	 */
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditBusinessReportParamsOperation operation = getEditOperation();
		EditBusinessReportParamsForm frm = (EditBusinessReportParamsForm) form;

		try
		{
			FormProcessor<ActionMessages, ?> processor = createFormProcessor(getFormProcessorValueSource(frm, operation), frm.getUploadForm());
			if(!processor.process())
				throw new FormProcessorException(processor.getErrors());

			Map<String, Object> formData  = BusinessReportHelper.getParamsFromForm(processor.getResult());
			Calendar fromDate = (Calendar) formData.get(BusinessReportConfig.FROM_PERIOD);
			Calendar toDate = DateHelper.endOfDay((Calendar) formData.get(BusinessReportConfig.TO_PERIOD));
			byte[] bts = operation.unload(formData);
			List<String> missingDate = operation.getMissingDate(fromDate, toDate);

			//сохраняем файл во временной директории для последующей выгрузки
			String tmpFilePath = System.getProperty("java.io.tmpdir") + String.format(TMP_FILE_NAME, new SimpleDateFormat("dd.MM.yyyy").format(toDate.getTime()));
			writeBytesToStream(bts, tmpFilePath);

			HttpSession session = currentRequest().getSession();
			session.setAttribute(FILE_NAME, String.format(DOWNLOADED_FILE_NAME, new SimpleDateFormat("dd.MM.yyyy").format(toDate.getTime())));
			session.setAttribute(TMP_FILE_NAME, tmpFilePath);

			((FilterActionForm)form).setField("relocateToDownload", true); //флаг о необходимости выгрузки данных

			if (CollectionUtils.isNotEmpty(missingDate))
			{
				ActionMessages actionMessages = new ActionMessages();
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(String.format(MESSAGE_MISSING_DATE, StringHelper.stringListToStringWithoutFinalDelimiter(missingDate, ", ")), false));
				saveSessionMessages(request, actionMessages);
			}

			return mapping.findForward(FORWARD_START);
		}
		catch (FormProcessorException e)
		{
			saveErrors(request, e.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		catch (Exception e)
		{
			saveError(request, e.getMessage());
			return mapping.findForward(FORWARD_START);
		}
	}

	private void writeBytesToStream(byte[] bts, String tmpFilePath) throws BusinessException
	{
		ObjectOutputStream outputStream = null;
		try
		{
			outputStream = new ObjectOutputStream(new FileOutputStream(tmpFilePath));
			outputStream.writeObject(bts);
			outputStream.flush();
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			try
			{
				outputStream.close();
			}
			catch (IOException e)
			{
				throw new BusinessException(e);
			}
		}
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation op) throws Exception
	{
		super.updateFormAdditionalData(form, op);
		EditBusinessReportParamsOperation operation = (EditBusinessReportParamsOperation) op;
		EditBusinessReportParamsForm frm = (EditBusinessReportParamsForm) form;
		frm.setNodes(operation.getNodes());
	}

	@Override
	protected boolean initNodes()
	{
		return true;
	}
}
