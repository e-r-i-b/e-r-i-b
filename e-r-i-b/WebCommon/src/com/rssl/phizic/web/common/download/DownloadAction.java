package com.rssl.phizic.web.common.download;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.actions.DefaultExceptionHandler;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен выгрузки файлов
 */

public class DownloadAction extends OperationalActionBase
{
	private static final String X_FILE_DOWNLOAD_CONTENT_TYPE = "x-file-download";
	private static final String TMP_FILE_NAME = "downloadedTempFile_%s_%s.tmp"; //название временного файла

	/**
	 * @param fileType тип файла
	 * @return имя временного файла
	 */
	private static String getFileName(String fileType)
	{
		Long loginId = AuthModule.getAuthModule().getPrincipal().getLogin().getId();
		return String.format(TMP_FILE_NAME, loginId, StringHelper.getEmptyIfNull(fileType));
	}

	/**
	 * выгрузка в темповую директорию данных
	 * @param data данные
	 * @param fileType тип файла
	 * @param clientFileName имя файла (для клиента)
	 * @param form форма
	 * @param request реквест
	 * @throws Exception
	 */
	public static void unload(byte[] data, String fileType, String clientFileName, FilterActionForm form, HttpServletRequest request) throws Exception
	{
		if (ArrayUtils.isNotEmpty(data))
		{
			//сохраняем файл во временной директории для последующей выгрузки
			FileHelper.writeToTempDirectory(data, getFileName(fileType));
			form.setField("relocateToDownload", true);
			form.setField("clientFileName",     clientFileName);
		}
		else
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Произошла ошибка при выгрузке файла", false));
			ActionMessageHelper.saveErrors(request, msgs);
		}
	}

	/**
	 * Кидает в response данные для выгрузки.
	 * Метод должен быть выполнен после перезагрузки страницы.
	 * Для этого нобходимо на jsp после загрузки страницы если
	 * флаг relocateToDownload=true выполнить window.location на данный метод.
	 * @param mapping стратс.маппинг
	 * @param form форма
	 * @param request запрос
	 * @param response ответ
	 * @return при успешно результате (т.е. файл был выгружен) метод должен возвращать null,
	 *         иначе форму выгрузки с сообщением об ошибке
	 */
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DownloadForm frm = (DownloadForm) form;
		String fileName = getFileName(frm.getFileType());
		try
		{
			byte[] data = FileHelper.readTempDirectoryFile(fileName);
			if (ArrayUtils.isEmpty(data))
				return mapping.findForward(DefaultExceptionHandler.FORWARD_SHOW_ERROR_PAGE);

			String contentType = frm.getContentType();
			if (StringHelper.isEmpty(contentType))
				contentType = X_FILE_DOWNLOAD_CONTENT_TYPE;
			String clientFileName = frm.getClientFileName();

			response.setContentType("application/" + contentType);
			response.setHeader("Content-disposition", "attachment; filename=\"" + StringUtils.from1251To1252(clientFileName) + "\"");
			response.setHeader("Pragma", null);
			response.setHeader("Cache-Control", null);

			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
			return null;
		}
		finally
		{
			FileHelper.deleteTempDirectoryFile(fileName);
		}
	}
}
