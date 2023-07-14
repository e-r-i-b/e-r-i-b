package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.security.UploadCardsAnswerOperation;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author Khivin
 * @ created 01.01.2007
 * @ $Author$
 * @ $Revision$
 */

public class UploadCardsAnswerAction extends OperationalActionBase
{
    public static final String  FORWARD_START   = "Start";
    public static final String  FORWARD_CLOSE   = "Close";
	private static final String FORWARD_FAILURE = "Failure";
	private static final String FORWARD_SUCCESS = "Success";

	private static final int MAX_FILE_SIZE = 100 * 1024;

	protected Map<String, String> getKeyMethodMap()
    {
        Map<String,String> map = new HashMap<String, String>();
	    map.put("button.save", "save");

        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        return mapping.findForward(FORWARD_START);
    }


	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UploadCardsAnswerForm frm = (UploadCardsAnswerForm) form;

		FormFile xmlAnswerFile = frm.getXmlAnswer();

		ActionMessages actionMessages = validate(xmlAnswerFile);
		if (actionMessages.size() > 0)
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_FAILURE);
		}

		return updateData(xmlAnswerFile, mapping, request);
	}

	private ActionForward updateData(FormFile xmlAnswerFile, ActionMapping mapping, HttpServletRequest request)
			throws BusinessException, IOException
	{
		ActionMessages actionMessages = new ActionMessages();
		try
		{
			UploadCardsAnswerOperation operation = createOperation(UploadCardsAnswerOperation.class);

			operation.upload( convert2String( xmlAnswerFile ));
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.endUpload");
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);

		}
		catch(BusinessException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.updateData", e.getMessage());
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (BusinessLogicException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.updateData", e.getMessage());
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		if(!actionMessages.isEmpty())
		{
			saveErrors(request, actionMessages);
			return mapping.findForward(FORWARD_FAILURE);
		}

		return mapping.findForward(FORWARD_SUCCESS);
	}

	private ActionMessages validate(FormFile file) throws ParserConfigurationException
	{
		ActionMessages msgs = new ActionMessages();

		if (file == null || file.getFileName().length() == 0)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else
		{
			if (file.getFileSize() > MAX_FILE_SIZE)
			{
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.fileSizeTooBig", file.getFileName());
				msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			}
			else
			{
				msgs.add(validateXml(file));
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
		}
		catch (SAXException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.xmlValidate", e.getMessage());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (MalformedURLException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.malformedURL", file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (IOException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.passwordcards.error.fileNotSelected");
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
