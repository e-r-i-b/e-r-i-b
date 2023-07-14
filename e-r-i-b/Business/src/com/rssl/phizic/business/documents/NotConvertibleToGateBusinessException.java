package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * Исключение, бросаемое если бизнес документ не поддерживает преобразование к гейтовому типу
 *
 * @author khudyakov
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 */
public class NotConvertibleToGateBusinessException extends BusinessException
{
	private static final String MESSAGE = "Документ className = %s не реализует интерфейс ConvertableToGateDocument";

	public NotConvertibleToGateBusinessException(BusinessDocument document)
	{
		super(String.format(MESSAGE, document.getClass().getName()));
	}
}
