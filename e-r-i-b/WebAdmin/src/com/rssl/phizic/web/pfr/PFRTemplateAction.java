package com.rssl.phizic.web.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.pfr.PFRTemplateOperation;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gulov
 * @ created 01.03.2011
 * @ $Authors$
 * @ $Revision$
 */
public class PFRTemplateAction extends OperationalActionBase
{
	private static final int MAX_FILE_SIZE = 1024 * 1000;

	private static final String XSLT_EXTENSION = "xslt";

	private static final String XSD_EXTENSION = "xsd";

	private static final String FILE_NOT_SELECTED = "Файл не выбран";

	private static final String FILE_SIZE_IS_ZERO = "Размер файла не может равняться 0";

	private static final String FILE_IS_TO_BIG = "Размер файла не может быть больше 1 МБ";

	private static final String FILE_EXTENSION_NOT_VALID = "Расширение файла должно быть %s";

	private static final String FORWARD_START = "Start";

	private static final String UNLOAD_FILE_NAME = "pfr-statement";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.xsltLoad", "xsltLoad");
		map.put("button.xsdLoad", "xsdLoad");

		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String type = request.getParameter("type");

		if (type == null || type.length() == 0 )
			return mapping.findForward(FORWARD_START);

		if (type.equals(XSLT_EXTENSION))
			return xsltUnload(mapping, form, request, response);
		else
			return xsdUnload(mapping, form, request, response);
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws Exception
	{
		return createOperation();
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm)
		throws BusinessException, BusinessLogicException
	{
		
	}

	public ActionForward xsltLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                              HttpServletResponse response) throws Exception
	{
		PFRTemplateForm frm = (PFRTemplateForm) form;
		PFRTemplateOperation operation = createOperation();

		ActionMessages actionMessages = new ActionMessages();

		validContent(frm.getXsltLoadable(), actionMessages, XSLT_EXTENSION);

		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);

			return start(mapping, form, request, response);
		}

		operation.xsltLoad(new String(frm.getXsltLoadable().getFileData(), "UTF-8"));

		return start(mapping, form, request, response);
	}

	public ActionForward xsltUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                                HttpServletResponse response) throws Exception
	{
		PFRTemplateOperation operation = createOperation();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		OutputStream outputStream = null;
		try
		{
			outputStream = new FilterOutputStream(buffer);

			byte[] buf = operation.getEntity().getXsltTemplate().getBytes("UTF-8");
			outputStream.write(buf, 0, buf.length);
		}
		finally
		{
			if (outputStream != null)
				outputStream.close();
		}

		String fileName = UNLOAD_FILE_NAME + '.' + XSLT_EXTENSION;
		response.setContentLength(buffer.size());
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setCharacterEncoding("UTF-8");

		ServletOutputStream outStream = response.getOutputStream();
		buffer.writeTo(outStream);
		outStream.flush();

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward xsdLoad(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                          HttpServletResponse response) throws Exception
	{
		PFRTemplateForm frm = (PFRTemplateForm) form;
		PFRTemplateOperation operation = createOperation();

		ActionMessages actionMessages = new ActionMessages();

		validContent(frm.getXsdLoadable(), actionMessages, XSD_EXTENSION);

		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);

			return start(mapping, form, request, response);
		}

		operation.xsdLoad(new String(frm.getXsdLoadable().getFileData(), "UTF-8"));

		return start(mapping, form, request, response);
	}

	public ActionForward xsdUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                          HttpServletResponse response) throws Exception
	{
		PFRTemplateOperation operation = createOperation();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		OutputStream outputStream = null;
		try
		{
			outputStream = new FilterOutputStream(buffer);

			byte[] buf = operation.getEntity().getXsd().getBytes("UTF-8");
			outputStream.write(buf, 0, buf.length);
		}
		finally
		{
			if (outputStream != null)
				outputStream.close();
		}

		String fileName = UNLOAD_FILE_NAME + '.' + XSD_EXTENSION;
		response.setContentLength(buffer.size());
		response.setContentType("application/x-file-download");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setCharacterEncoding("UTF-8");

		ServletOutputStream outStream = response.getOutputStream();
		buffer.writeTo(outStream);
		outStream.flush();

		return mapping.findForward(FORWARD_START);
	}

	private void validContent(FormFile content, ActionMessages actionMessages, String extension)
	{
		if (content == null || content.getFileName().length() == 0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(FILE_NOT_SELECTED, false));
		}

		if (actionMessages.size() == 0 && content.getFileSize() == 0)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(FILE_SIZE_IS_ZERO, false));
		}

		if (actionMessages.size() == 0 && content.getFileSize() > MAX_FILE_SIZE)
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(FILE_IS_TO_BIG, false));
		}

		if (actionMessages.size() == 0 && (!FileHelper.getFileExtension(content.getFileName()).toLowerCase().equals(extension)))
		{
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage(String.format(FILE_EXTENSION_NOT_VALID, extension), false));
		}
	}

	private PFRTemplateOperation createOperation() throws BusinessException, BusinessLogicException
	{
		PFRTemplateOperation result = createOperation(PFRTemplateOperation.class, "AdditionalSystemSettingsManagement");

		result.initialize();

		return result;
	}
}
