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
 * ������� ����� ��� �������� ������
 * ����������� ���������� ����������� ����� outData
 * @ author gorshkov
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class DownloadOperationalActionBase<T extends Serializable> extends OperationalActionBase
{
	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String FORWARD_START = "Start";
	private static final String FILE_NAME = "fileName"; //�������� ������������ ������� �����
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //�������� ���������� �����

	/**
	 * ����� ������ � outputStream ������ ��� ��������.
	 * ���������� ������� ����� �������� ��������� �����
	 * @param data - ������ ��� ��������
	 * @param outputStream - �������� �����
	 */
	protected abstract void outData(T data, ServletOutputStream outputStream) throws Exception;

	/**
	 * ������ � response ������ ��� ��������.
	 * ����� ������ ���� �������� ����� ������������ ��������.
	 * ��� ����� ��������� �� jsp ����� �������� �������� ����
	 * ���� relocateToDownload=true ��������� window.location �� ������ �����.
	 * @return ��� ������� ���������� (�.�. ���� ��� ��������) ����� ������ ���������� null,
	 *         ����� ����� �������� � ���������� �� ������
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		phizLog.debug("����� ������� ��������.");
		String filePath = (String) HttpSessionUtils.getSessionAttribute(request, TMP_FILE_NAME);
		File file = null;
		ObjectInputStream inputStream = null;
		T data;
		try
		{
			file = new File(filePath);
			if (!file.exists())
			{
				phizLog.debug("��� ����� ��� ��������.");
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("��������� ������ ��� �������� �����", false));
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
				log.info("�� ������� ������� ��������� ���� �������� ( "+ file.getName()+" ).");
			}
		}

		if (data != null)
		{
			Object filename = HttpSessionUtils.getSessionAttribute(request, FILE_NAME);

			phizLog.debug("������ ��� �������� ��������.");
			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");
			response.setHeader("Pragma", null);
			response.setHeader("Cache-Control", null);

			outData(data, response.getOutputStream());

			phizLog.debug("������ ��� �������� ������� ����������.");
			HttpSessionUtils.removeSessionAttribute(request, FILE_NAME);
			HttpSessionUtils.removeSessionAttribute(request, TMP_FILE_NAME);
		}
		else
		{
			phizLog.debug("��� ������ ��� �������� (data is null).");
		}

		return null;
	}

}
