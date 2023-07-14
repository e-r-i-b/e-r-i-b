package com.rssl.phizic.web.forms;

import com.rssl.phizic.operations.forms.UploadFormOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Загрузка файлов формы платежа на сервер
 * @author Evgrafov
 * @ created 23.12.2005
 * @ $Author: Evgrafov $
 * @ $Revision: 548 $
 */
public class UploadFormsAction extends OperationalActionBase
{

	private static final String FORWARD_START   = "Start";
    private static final String FORWARD_SUCCESS = "Success";
    private static final String FORWARD_FAILURE = "Failure";

	private static final String HTML_VALIDATE = "html.xslt";
	private static final String LIST_HTML_VALIDATE = "list-html.xslt";
	private static final String LIST_PFD_VALIDATE = "list-pfd.xml";
	private static final String LIST_FILTER_HTML_VALIDATE = "listFilter-html.xslt";
	private static final String PFD_VALIDATE = "pfd.xml";
	private static final String XML_VALIDATE = "xml.xslt";

	private static final int MAX_FILE_SIZE = 100*1024;

	protected Map getKeyMethodMap()
	{
	     Map<String,String> map = new HashMap<String, String>();
	     map.put("button.save", "save");

	     return map;
	}

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
         //UploadFormsForm frm = (UploadFormsForm) metadata;

         return mapping.findForward(FORWARD_START);
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    UploadFormsForm frm = (UploadFormsForm )form;

	    ActionMessages actionMessages = validate(frm);
	    if( actionMessages.size() > 0 )
	    {
	        saveErrors( request, actionMessages );
		    return mapping.findForward(FORWARD_FAILURE);
	    }

		UploadFormOperation operation = createOperation(UploadFormOperation.class);
		initOperation(frm, operation);

		operation.save();

	    return mapping.findForward(FORWARD_SUCCESS);
    }

	private void initOperation(UploadFormsForm frm, UploadFormOperation operation)
			throws IOException
	{
		String formStr = convert2String(frm.getForm());
		String htmlFormStr = convert2String(frm.getHtmlForm());
		String xmlFormStr = convert2String(frm.getXmlForm());

		String listFormStr = convert2String(frm.getListForm());
		String htmlListFormStr = convert2String(frm.getHtmlListForm());
		String htmlFilterListFormStr = convert2String(frm.getHtmlFilterListForm());

		operation.setFormDefenition(formStr);
		operation.setHtmlForm(htmlFormStr);
		operation.setXmlForm(xmlFormStr);
		operation.setListForm(listFormStr);
		operation.setHtmlListForm(htmlListFormStr);
		operation.setHtmlListFilterForm(htmlFilterListFormStr);
	}

	private ActionMessages validate(UploadFormsForm frm) throws ParserConfigurationException
	{
		ActionMessages msgs = new ActionMessages();

		msgs.add( validate(frm.getForm(), PFD_VALIDATE));
		msgs.add( validate(frm.getHtmlForm(), HTML_VALIDATE));
		msgs.add( validate(frm.getXmlForm(), XML_VALIDATE) );
		msgs.add( validate(frm.getListForm(), LIST_PFD_VALIDATE) );
		msgs.add( validate(frm.getHtmlListForm(), LIST_HTML_VALIDATE) );
		msgs.add( validate(frm.getHtmlFilterListForm(), LIST_FILTER_HTML_VALIDATE) );

		return msgs;
	}

	private ActionMessages validate(FormFile file, String validateExtansion) throws ParserConfigurationException
	{
		ActionMessages msgs = new ActionMessages();

		if ( file == null || file.getFileName().length() == 0 )
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else if (file.toString().indexOf(validateExtansion)<0)
			{
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.incorrectExtansion", validateExtansion);
				msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
				return msgs;
			}
		else
		{
			if (file.getFileSize() > MAX_FILE_SIZE)
			{
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.fileSizeTooBig", file.getFileName());
				msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			}
			else
			{
				msgs.add( validateXml(file) );
			}
		}
		return msgs;

	}

	private ActionMessages validateXml(FormFile file) throws ParserConfigurationException
	{
		ActionMessages msgs = new ActionMessages();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();

		try
		{
			documentBuilder.parse(file.getInputStream());
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.endUpload");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (SAXException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.xmlValidateError", e.getMessage());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (MalformedURLException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.malformedURL", file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (IOException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.forms.settings.error.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}

		return msgs;
	}

	public String convert2String(FormFile file) throws IOException
	{
		InputStream streamIn = null;

		try
		{
			int fileSize = file.getFileSize();

			streamIn = file.getInputStream();

			byte[] b = new byte[fileSize];

			assert streamIn != null;
			streamIn.read(b);

			return new String(b);
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			assert streamIn != null;
			streamIn.close();
			file.destroy();
		}

	}

}