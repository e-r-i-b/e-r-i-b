package com.rssl.phizic.web.actions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ������� ����� ��� �������� ������
 * ����������� ���������� ����������� ����� createData
 * @author gladishev
 * @ created 28.03.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class UnloadOperationalActionBase<T extends Serializable> extends OperationalActionBase
{
	private static final Log phizLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String FILE_NAME = "fileName"; //�������� ������������ ������� �����
	private static final String TMP_FILE_NAME = "UnloadedFileForLogin%s.tmp"; //�������� ���������� �����


	/**
	 * @return ���� - ��� ����� ��������, ������ ��� ��������
	 */
	public abstract Pair<String, T> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = getAditionalKeyMethodMap();
		map.put("button.unload", "unload");
		map.put("download.file", "download");
		return map;
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ����� ��� ���������� ��������������� ��������
	 * @param mapping  ������.�������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return �������
	 * @throws Exception
	 */
	public ActionForward actionAfterUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * ����� ������� ������ ��� ��������, ��������� �� ��������� �����
	 * � ������������ �������� ��������
	 * @param mapping ������.�������
	 * @param form �����
	 * @param request ������
	 * @param response �����
	 * @return ������� ��� ���������� �������� ����� ������� �� ������ ���������
	 */
	public ActionForward unload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		phizLog.debug("1 ������ ��������");
		Pair<String, T> data = createData(form, request, response);

		if (data != null)
		{
			phizLog.debug("2 ������ ��� �������� ��������");
			Long loginId = AuthModule.getAuthModule().getPrincipal().getLogin().getId();
			//��������� ���� �� ��������� ���������� ��� ����������� ��������
			String tmpFilePath = System.getProperty("java.io.tmpdir") + String.format(TMP_FILE_NAME, loginId.toString());
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(tmpFilePath));
			outputStream.writeObject(data.getSecond());
			outputStream.flush();
			outputStream.close();

			HttpSession session = currentRequest().getSession();
			session.setAttribute(FILE_NAME, data.getFirst());
			session.setAttribute(TMP_FILE_NAME, tmpFilePath);

			((FilterActionForm)form).setField("relocateToDownload", true); //���� � ������������� �������� ������
			phizLog.debug("3 ���� ��� �������� �������� �� ��������� ����������.");
		}
		else
		{
			phizLog.debug("999 �� ������� �������� ������ ��� ��������");
		}
		return actionAfterUnload(mapping,form,request,response);
	}
}


