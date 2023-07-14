package com.rssl.phizic.utils.pdf;

import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * @author akrenev
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * Исключение создания PDF документа
 */
public class PDFBuilderException extends IKFLException
{
	public PDFBuilderException(String message)
	{
		super(message);
	}

	public PDFBuilderException(Throwable cause)
	{
		super(cause);
	}

	public PDFBuilderException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
