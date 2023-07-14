package com.rssl.phizic.web.validators;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 * @author eMakarov
 * @ created 14.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class FileNotEmptyValidator
{
	public static ActionMessages validate(FormFile file)
	{
		ActionMessages msgs = new ActionMessages();

		if (file == null || file.getFileName().length() == 0)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileNotSelected");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else if (file.getFileSize() == 0)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileIsEmpty", file.getFileName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}
}
