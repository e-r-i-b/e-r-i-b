package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class UploadCardsAnswerForm extends ActionFormBase
{
	private FormFile xmlAnswer;

	public FormFile getXmlAnswer()
	{
		return xmlAnswer;
	}

	public void setXmlAnswer(FormFile xmlAnswer)
	{
		this.xmlAnswer = xmlAnswer;
	}
}
