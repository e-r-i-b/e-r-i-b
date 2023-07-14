package com.rssl.phizic.web.log;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.log.*;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.zip.ZipHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.certification.TemporyFileHelper;
import com.rssl.phizic.web.validators.FileNotEmptyValidator;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveLogAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	private final String SYSTEM_LOG = "system";
	private final String OPERATIONS_LOG = "operations";
	private final String MESSAGES_LOG = "messages";
	private final String ENTRIES_LOG =  "entries";
	private final String TEMP_FOLDER_NAME = "logArchiveFolder";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.archive", "archive");
		map.put("button.unarchive", "unarchive");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward archive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ArchiveLogForm frm = (ArchiveLogForm) form;

		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, ArchiveLogForm.FILTER_FORM);
		ActionMessages actionMessages = new ActionMessages();
		if (formProcessor.process())
		{
			Map<String, Object> result = formProcessor.getResult();
			try
			{
				String protocolType = result.get("protocolType").toString();
				ArchiveLogOperationBase operation = initArchiveOperation(protocolType);
				if (!operation.hasRecords())
				{
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("За данный период нет ни одной записи.", false));
				}
				else
				{
					operation.archive();
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Журнал успешно заархивирован", false));
				}
			}
			catch (FileNotFoundException e)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Ошибка при создании архива: не найден каталог архивации", false));
			}
			catch (IOException e)
			{
				actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Ошибка при создании архива", false));
			}
		}
		else
		{
			actionMessages.add(formProcessor.getErrors());
		}
		saveErrors(request, actionMessages);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward unarchive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FormFile zipArchive = ((ArchiveLogForm) form).getArchive();

		ActionMessages actionMessages = validateFile(zipArchive);
		if (!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}

		File tmpDir = FileHelper.createDirectory(TemporyFileHelper.getTemporaryDirPath(currentServletContext()) + File.separator + TEMP_FOLDER_NAME);
		try
		{
			ZipHelper.unpack(zipArchive.getInputStream(), tmpDir.getAbsolutePath() + File.separator);
		}
		catch (Exception e)
		{
			actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Невозможно распаковать указанный архив.", false));
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}
		InputStream inputStream = null;
		try
		{
			int i = -1;
			String[] protocolTypes = {SYSTEM_LOG, OPERATIONS_LOG, MESSAGES_LOG, ENTRIES_LOG};
			while (++i < protocolTypes.length)
			{
				File file = new File(tmpDir.getAbsolutePath() + File.separator + getXMLFileName(protocolTypes[i]));

				if (!file.exists())
					continue;

				inputStream = new FileInputStream(file);
				ArchiveLogOperationBase operation = initUnarchiveOperation(protocolTypes[i], inputStream);
				long duplicate = operation.unarchive();

				if (duplicate > 0)
				{
					actionMessages = new ActionMessages();
					actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Из архива не было добавлено записей: " + duplicate + ". Эти записи уже присутствуют в базе.", false));
					saveErrors(request, actionMessages);
				}
				return mapping.findForward(FORWARD_START);
			}
		}
		catch (BusinessLogicException e)
		{
			actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_START);
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
			FileHelper.deleteDirectory(tmpDir.getAbsolutePath());
		}

		actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("В архиве не найден искомый xml-файл", false));
		saveErrors(request, actionMessages);
		return mapping.findForward(FORWARD_START);
	}

	private ActionMessages validateFile(FormFile file)
	{
		if (!file.getFileName().toUpperCase().endsWith(".ZIP"))
		{
			ActionMessages actionMessages = new ActionMessages();
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Неверный тип файла", false));
			return actionMessages;
		}
		return FileNotEmptyValidator.validate(file);
	}

	private ArchiveLogOperationBase initArchiveOperation(String protocolType) throws BusinessException, BusinessLogicException
	{
		ArchiveLogOperationBase operation = null;
		//TODO цепочки ифов - зло. и они будут искореняться
		if (protocolType.equals(SYSTEM_LOG))
		{
			operation = createOperation(ArchiveSystemLogOperation.class);
		}
		else if (protocolType.equals(OPERATIONS_LOG))
		{
			operation = createOperation(ArchiveOperationsLogOperation.class);
		}
		else if(protocolType.equals(MESSAGES_LOG))
		{
			operation = createOperation(ArchiveMessagesLogOperation.class);
		}
		else
		{
			operation = createOperation(ArchiveEntriesLogOperation.class);
		}
		operation.initialize();
		return operation;
	}

	private ArchiveLogOperationBase initUnarchiveOperation(String protocolType, InputStream stream) throws BusinessLogicException, BusinessException
	{
		//TODO цепочки ифов - зло. и они будут искореняться
		if (protocolType.equals(SYSTEM_LOG))
		{
			ArchiveSystemLogOperation operation = createOperation(ArchiveSystemLogOperation.class);
			operation.initialize(stream);
			return operation;
		}
		else if (protocolType.equals(OPERATIONS_LOG))
		{
			ArchiveOperationsLogOperation operation = createOperation(ArchiveOperationsLogOperation.class);
			operation.initialize(stream);
			return operation;
		}
		else if (protocolType.equals(MESSAGES_LOG))
		{
			ArchiveMessagesLogOperation operation = createOperation(ArchiveMessagesLogOperation.class);
			operation.initialize(stream);
			return operation;
		}
		else
		{
			ArchiveEntriesLogOperation operation = createOperation(ArchiveEntriesLogOperation.class);
			operation.initialize(stream);
			return operation;
		}
	}

	private String getXMLFileName(String protocolType)
	{
		return protocolType + "log.xml";
	}

	private String getArchiveName(String protocolType, Map<String, Object> result)
	{
		String fromDate = String.format(ArchiveLogOperationBase.DATE_FORMAT_STRING, (Date) result.get("fromDate"));
		String toDate = String.format(ArchiveLogOperationBase.DATE_FORMAT_STRING, (Date) result.get("toDate"));
		String fromTime = String.format("%1$tH_%1$tM_%1$tS", (Date) result.get("fromTime"));
		String toTime = String.format("%1$tH_%1$tM_%1$tS", (Date) result.get("toTime"));

		StringBuffer name = new StringBuffer(protocolType);
		name.append("-logs-archive_");
		name.append(fromDate).append("_").append(fromTime);
		name.append("-");
		name.append(toDate).append("_").append(toTime);
		name.append(".zip");

		return name.toString();
	}
}