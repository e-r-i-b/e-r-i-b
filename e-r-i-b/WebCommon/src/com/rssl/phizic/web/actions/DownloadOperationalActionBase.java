package com.rssl.phizic.web.actions;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.io.ObjectInputStreamWithLoader;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.action.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Базовый класс для выгрузки файлов
 * Наследникам необходимо реализовать метод outData
 * @ author gorshkov
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class DownloadOperationalActionBase<T extends Serializable> extends OperationalActionBase
{
	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String FORWARD_START = "Start";
	private static final String FILE_NAME = "fileName"; //название выгружаемого клиенту файла
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //название временного файла

	/**
	 * Метод кидает в outputStream данные для выгрузки.
	 * Реализация доллжна после отправки закрывать поток
	 * @param data - данные для выгрузки
	 * @param outputStream - выходной поток
	 */
	protected abstract void outData(T data, ServletOutputStream outputStream) throws Exception;

	/**
	 * Кидает в response данные для выгрузки.
	 * Метод должен быть выполнен после перезагрузки страницы.
	 * Для этого нобходимо на jsp после загрузки страницы если
	 * флаг relocateToDownload=true выполнить window.location на данный метод.
	 * @return при успешно результате (т.е. файл был выгружен) метод должен возвращать null,
	 *         иначе форму выгрузки с сообщением об ошибке
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		phizLog.debug("Начат процесс зыгрузки.");
		String filePath = (String) HttpSessionUtils.getSessionAttribute(request, TMP_FILE_NAME);
		File file = null;
		ObjectInputStream inputStream = null;
		T data;
		try
		{
			file = new File(filePath);
			if (!file.exists())
			{
				phizLog.debug("Нет файла для выгрузки.");
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Произошла ошибка при выгрузке файла", false));
				saveErrors(request, msgs);
				return mapping.findForward(FORWARD_START);
			}

            inputStream = new ObjectInputStreamWithLoader(new FileInputStream(file), Thread.currentThread().getContextClassLoader());
			data = (T) inputStream.readObject();
		}
		finally
		{
			if (inputStream != null)
			{
				inputStream.close();
			}
			if (!file.delete())
			{
				log.info("Не удалось удалить временный файл выгрузки ( "+ file.getName()+" ).");
			}
		}

		if (data != null)
		{
			Object filename = HttpSessionUtils.getSessionAttribute(request, FILE_NAME);

			phizLog.debug("Данные для выгрузки получены.");
			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");
			response.setHeader("Pragma", null);
			response.setHeader("Cache-Control", null);

			outData(data, response.getOutputStream());

			phizLog.debug("Данные для выгрузки успешно отправлены.");
			HttpSessionUtils.removeSessionAttribute(request, FILE_NAME);
			HttpSessionUtils.removeSessionAttribute(request, TMP_FILE_NAME);
		}
		else
		{
			phizLog.debug("Нет данных для выгрузки (data is null).");
		}

		return null;
	}

}
