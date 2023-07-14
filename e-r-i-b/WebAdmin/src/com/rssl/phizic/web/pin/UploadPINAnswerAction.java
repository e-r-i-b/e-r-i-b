package com.rssl.phizic.web.pin;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.security.UploadPINAnswerOperation;
import com.rssl.phizic.operations.security.OldPINEnvelopeException;
import com.rssl.phizic.operations.security.UnknownPINEnvelopeException;
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
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class UploadPINAnswerAction extends OperationalActionBase
{
    public static final String  FORWARD_START   = "Start";
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

        return mapping.findForward(UploadPINAnswerAction.FORWARD_START);
    }


	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UploadPINAnswerForm frm = (UploadPINAnswerForm) form;

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
			UploadPINAnswerOperation operation = createOperation(UploadPINAnswerOperation.class);

			operation.setAnswerXml( convert2String( xmlAnswerFile ) );
			operation.upload();
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.endUpload");
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch(BusinessException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.updateData", e.getMessage());
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);			
		}
		catch(OldPINEnvelopeException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.updateData", e.getMessage());
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch(UnknownPINEnvelopeException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.updateData", e.getMessage());
			actionMessages.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (BusinessLogicException e)
		{

			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.updateData", e.getMessage());
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

		// TODO может надо использовать FileSizeLimitValidator?
		if (file == null || file.getFileName().length() == 0)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else
		{
			if (file.getFileSize() > MAX_FILE_SIZE)
			{
				ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.fileSizeTooBig", file.getFileName());
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
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.xmlValidate", e.getMessage());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (MalformedURLException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.malformedURL", file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		catch (IOException e)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.pin.error.fileNotSelected");
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