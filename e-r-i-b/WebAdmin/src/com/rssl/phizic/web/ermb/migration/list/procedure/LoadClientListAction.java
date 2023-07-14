package com.rssl.phizic.web.ermb.migration.list.procedure;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.ermb.migration.list.operations.LoadClientListOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн загрузки списка сотрудников для миграции
 */
public class LoadClientListAction extends OperationalActionBase
{
	private static final String FORWARD_REPORT = "Report";

	private static final String FILE_IS_TOO_LARGE = "Размер файла не может быть больше %s КБ";
	private static final String SHARED_FILE_NOT_EXISTS = "Выбранный файл с клиентами должен находиться в %s";
	private static final String CANNOT_READ_FILE = "Файл %s не может быть прочитан";

	protected Map<String, String> getKeyMethodMap ()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.load", "load");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoadClientListForm frm = (LoadClientListForm) form;
		LoadClientListOperation operation = createOperation(LoadClientListOperation.class, "ErmbMigrationService");

		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), LoadClientListForm.LOAD_FORM);
		if(processor.process())
		{
			FormFile content = frm.getContent();
			ActionMessages actionMessages = validateFile(content, operation.getSharedPath(), operation.getMaxFileSize());
			if (!actionMessages.isEmpty())
			{
				saveErrors(request, actionMessages);
				return mapping.findForward(FORWARD_START);
			}
			operation.setFileName(content.getFileName());
			operation.setMonthCount(Integer.parseInt((String) frm.getField(LoadClientListForm.MONTH_COUNT)));
			operation.save();

			frm.setStatus(operation.getStatus());
		}
		else
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_REPORT);
	}

	private ActionMessages validateFile(FormFile content, String sharedPath, long maxFileSize)
	{
		ActionMessages actionMessages = FileNotEmptyValidator.validate(content);
		try
		{
			File sharedFile = new File(sharedPath + content.getFileName());
			if (!sharedFile.exists() ||
					sharedFile.length() != content.getFileSize()||
					!IOUtils.contentEquals(FileUtils.openInputStream(sharedFile), content.getInputStream()))
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(String.format(SHARED_FILE_NOT_EXISTS, sharedPath), false));
			else if (content.getFileSize() > maxFileSize * 1024)
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(String.format(FILE_IS_TOO_LARGE, maxFileSize), false));
		}
		catch (IOException e)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(String.format(CANNOT_READ_FILE, sharedPath + content.getFileName()), false));
		}
		return actionMessages;
	}
}
