package com.rssl.phizic.business.xml;

import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

/**
 * Листенер для обработки ошибок при преобразовании xslt
 * @author gladishev
 * @ created 20.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class TransformErrorListener implements ErrorListener
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void warning(TransformerException exception) throws TransformerException
	{
		Throwable throwable = exception.getException();
		if (throwable instanceof InactiveExternalSystemException)
		{
			throw new InactiveExternalSystemException(throwable.getMessage());
		}

		StringBuilder warning = new StringBuilder("Предупреждение при преобразовании документа: ");
		warning.append(exception.getMessageAndLocation());
		log.warn(warning, exception);
	}

	public void error(TransformerException exception) throws TransformerException
	{
		Throwable throwable = exception.getException();
		if (throwable instanceof InactiveExternalSystemException)
		{
			throw new InactiveExternalSystemException(throwable.getMessage());
		}

		StringBuilder error = new StringBuilder("Ошибка при преобразовании документа: ");
		error.append(exception.getMessageAndLocation());
		throw new TransformerException(error.toString(), exception);
	}

	public void fatalError(TransformerException exception) throws TransformerException
	{
		Throwable throwable = exception.getException();
		if (throwable instanceof InactiveExternalSystemException)
		{
			throw new InactiveExternalSystemException(throwable.getMessage());
		}

		StringBuilder error = new StringBuilder("Ошибка при преобразовании документа: ");
		error.append(exception.getMessageAndLocation());
		throw new TransformerException(error.toString(), exception);
	}
}
