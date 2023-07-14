package com.rssl.phizic.web.validators;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Omeliyanchuk
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class FileSizeLimitValidator implements FileValidator
{
	public static final int BYTE_IN_KB = 1024;
	public static final int BYTE_IN_MB = 1024 * BYTE_IN_KB;

	private int maxSize;

	public FileSizeLimitValidator(int maxSize)
	{
		this.maxSize = maxSize;
	}

	public static ActionMessages validate(FormFile file, int maxSize)
	{
		ActionMessages msgs = new ActionMessages();
		
		if (file == null)
			return msgs;

		if (file.getFileSize() > maxSize)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.validators.error.fileSizeTooBig", file.getFileName(), maxSize);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}

		return msgs;
	}

	public ActionMessages validate(FormFile file) throws BusinessException
	{
		return validate(file, maxSize);
	}
}
