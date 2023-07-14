package com.rssl.phizic.web.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.operations.templates.DownloadTemplateOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 30.01.2007 Time: 15:47:44 To change this template use File
 * | Settings | File Templates.
 */
//TODO «¿◊≈Ã Œ“ƒ≈À‹Õ€… ¿ ÿ≈Õ?!
public class DownloadTemplateAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		DownloadTemplateForm frm = (DownloadTemplateForm) form;
		DownloadTemplateOperation operation = createOperation(DownloadTemplateOperation.class, "TemplatesDocumentsManagement");
		operation.initialize(frm.getId());

		DocTemplate template = operation.getTemplate();
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			buffer.write(template.getData());

			response.setContentLength(buffer.size());

			response.setContentType("application/x-file-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + template.getFileName());
			response.setHeader("cache-control", "");

			ServletOutputStream outStream = response.getOutputStream();
			buffer.writeTo(outStream);
			outStream.flush();
			outStream.close();

			addLogParameters(new BeanLogParemetersReader("Œ·‡·‡Ú˚‚‡ÂÏ‡ˇ ÒÛ˘ÌÓÒÚ¸", template));

		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		return null;
	}
}
