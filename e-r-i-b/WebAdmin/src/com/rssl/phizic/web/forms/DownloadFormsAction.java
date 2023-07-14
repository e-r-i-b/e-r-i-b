package com.rssl.phizic.web.forms;

import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.forms.DownloadFormsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Загрузка файлов формы платежа с сервера
 *
 * @author Kidyaev
 * @ created 28.02.2006
 * @ $Author: egorovaav $
 * @ $Revision: 35050 $
 */
public class DownloadFormsAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DownloadFormsForm frm      = (DownloadFormsForm) form;
		String            formName = request.getParameter("form");
		String            fileName = request.getParameter("file");

		DownloadFormsOperation downloadFormsOperation = createOperation(DownloadFormsOperation.class);

		downloadFormsOperation.initialize(formName);

		updateForm(frm, formName, downloadFormsOperation);

		addLogParameters(new SimpleLogParametersReader("Просматриваемая сущность", "Имя формы", formName));

		if( fileName == null || fileName.length() == 0 )
			return mapping.findForward(FORWARD_START);

		return download(formName, fileName, downloadFormsOperation, mapping, request, response);
	}

	private void updateForm(DownloadFormsForm frm, String formName, DownloadFormsOperation downloadFormsOperation)
	{
		frm.setPaymentFormName(formName);
		frm.setDescription( downloadFormsOperation.getDescription() );
	}

	private ActionForward download(String formName,
	                              String fileName,
	                              DownloadFormsOperation downloadFormsOperation,
	                              ActionMapping mapping,
	                              HttpServletRequest request,
	                              HttpServletResponse response)
	{
		Map<String,String>    data     = new HashMap<String, String>();
		MessageResources messageResources = getResources(request, "formsBundle");

		final String FORM_SUFFIX            = messageResources.getMessage("payment.form.defenition.file.suffix");
		final String FORM_HTML_SUFFIX       = messageResources.getMessage("payment.form.transformation.html.file.suffix");
		final String FORM_XML_SUFFIX        = messageResources.getMessage("payment.form.transformation.xml.file.suffix");
		final String LISTFORM_SUFFIX        = messageResources.getMessage("payment.listform.defenition.file.suffix");
		final String LISTFORM_FILTER_SUFFIX = messageResources.getMessage("payment.listform.transformation.filter.file.suffix");
		final String LISTFORM_LIST__SUFFIX  = messageResources.getMessage("payment.listform.transformation.list.file.suffix");
		final String ALL__SUFFIX            = messageResources.getMessage("payment.forms.files.suffix");

		if ( fileName.equals(FORM_SUFFIX) )
		{
			data.put( formName + FORM_SUFFIX, downloadFormsOperation.getForm() );
		}
		else if ( fileName.equals(FORM_HTML_SUFFIX) )
		{
			data.put( formName + FORM_HTML_SUFFIX, downloadFormsOperation.getHtmlForm() );
		}
		else if ( fileName.equals(FORM_XML_SUFFIX) )
		{
			data.put( formName + FORM_XML_SUFFIX, downloadFormsOperation.getXmlForm() );
		}
		else if ( fileName.equals(LISTFORM_SUFFIX) )
		{
			data.put( formName + LISTFORM_SUFFIX, downloadFormsOperation.getListForm() );
		}
		else if ( fileName.equals(LISTFORM_FILTER_SUFFIX) )
		{
			data.put( formName + LISTFORM_FILTER_SUFFIX, downloadFormsOperation.getHtmlListFilterForm() );
		}
		else if ( fileName.equals(LISTFORM_LIST__SUFFIX) )
		{
			data.put( formName + LISTFORM_LIST__SUFFIX, downloadFormsOperation.getHtmlListForm() );
		}
		else if ( fileName.equals(ALL__SUFFIX) )
		{
			data.put( formName + FORM_SUFFIX, downloadFormsOperation.getForm() );
			data.put( formName + FORM_HTML_SUFFIX, downloadFormsOperation.getHtmlForm() );
			data.put( formName + FORM_XML_SUFFIX, downloadFormsOperation.getXmlForm() );
			data.put( formName + LISTFORM_SUFFIX, downloadFormsOperation.getListForm() );
			data.put( formName + LISTFORM_FILTER_SUFFIX, downloadFormsOperation.getHtmlListFilterForm() );
			data.put( formName + LISTFORM_LIST__SUFFIX, downloadFormsOperation.getHtmlListForm() );
		}


		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			createZip(buffer, data);


			String zipFileName = formName + ( fileName.equals(ALL__SUFFIX) ? "" : fileName) + ".zip";
			response.setContentLength( buffer.size() );
			response.setContentType("application/x-file-download");
			response.setHeader("Content-disposition", "attachment; filename=" + zipFileName);

			ServletOutputStream outStream = response.getOutputStream();
			buffer.writeTo(outStream);
			outStream.flush();

			addLogParameters(new SimpleLogParametersReader("Выгружаемая сущность", "Имя архива", zipFileName));
		}
		catch ( IOException e )
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("com.rssl.phizic.web.forms.download.error") );
			saveErrors(request, actionErrors);

			return mapping.findForward(FORWARD_START);
		}

		return null;
	}

	private void createZip(ByteArrayOutputStream buffer, Map<String,String> data) throws IOException
	{
		ZipOutputStream zipOutputStream = null;
		try
		{
			zipOutputStream = new ZipOutputStream(buffer);

			for (Map.Entry<String, String> entry : data.entrySet())
			{
				String name = entry.getKey();
				String file = entry.getValue();
				byte[]    buf = file.getBytes();

				zipOutputStream.putNextEntry(new ZipEntry(name));
				zipOutputStream.write(buf, 0, buf.length);
				zipOutputStream.closeEntry();
			}
		}
		finally
		{
			if(zipOutputStream != null)
				zipOutputStream.close();
		}
	}
}
