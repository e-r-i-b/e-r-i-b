package com.rssl.phizic.web.actions.templates;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.templates.DownloadBankDocumentOperation;
import com.rssl.phizic.business.template.BanksDocument;
import org.apache.struts.action.*;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 11.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoadBanksDocumentAction extends OperationalActionBase
{

	protected Map<String,String> getKeyMethodMap()
    {
	    Map<String, String> map = new HashMap<String, String>();
	    return map;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                throws Exception
    {
        LoadBanksDocumentForm frm       = (LoadBanksDocumentForm) form;
		Long id = frm.getId();

		DownloadBankDocumentOperation operation = createOperation(DownloadBankDocumentOperation.class,"ShowBankDocuments");
		operation.initialize(id);
		BanksDocument document = operation.getBanksDocument();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		buffer.write(document.getData());

        response.setContentLength(buffer.size());
		response.setContentType("application/msword");
		response.setHeader("Content-Disposition", "attachment; filename=template.doc");
		response.setHeader("cache-control", "");

        ServletOutputStream outStream = response.getOutputStream();
		buffer.writeTo(outStream);
		outStream.flush();
		outStream.close();

	    addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getBanksDocument()));

		return null;
    }
}
