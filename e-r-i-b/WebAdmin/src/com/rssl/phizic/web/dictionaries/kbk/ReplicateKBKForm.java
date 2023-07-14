package com.rssl.phizic.web.dictionaries.kbk;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author akrenev
 * @ created 10.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateKBKForm extends ActionFormBase
{
	private FormFile fileImage;
	private long recordCount;
	private long wrongCount;
	private Object[] errors;

	public FormFile getFileImage()
	{
		return fileImage;
	}

	public void setFileImage(FormFile fileImage)
	{
		this.fileImage = fileImage;
	}

	public long getRecordCount()
	{
		return recordCount;
	}

	public void setRecordCount(long recordCount)
	{
		this.recordCount = recordCount;
	}

	public long getWrongCount()
	{
		return wrongCount;
	}

	public void setWrongCount(long wrongCount)
	{
		this.wrongCount = wrongCount;
	}

	public Object[] getErrors()
	{
		return errors;
	}

	public void setErrors(Object[] errors)
	{
		this.errors = errors;
	}
}
