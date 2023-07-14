package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.NewDocumentHandler;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.client.LoginType;

/**
 * Соурс для создания документов автоматически(т.е. системой)
 * @author Niculichev
 * @ created 12.07.15
 * @ $Author$
 * @ $Revision$
 */
public class SystemNewDocumentSource extends NewDocumentSource
{
	/**
	 * Констуктор источника нового чистого докумена
	 * @param formName имя формы
	 * @param initialValuesSource начальные данные
	 * @param creationSourceType тип создания операции
	 * @param messageCollector хранилище ошибок, может быть null
	 */
	public SystemNewDocumentSource(String formName, FieldValuesSource initialValuesSource, CreationSourceType creationSourceType, MessageCollector messageCollector) throws BusinessException, BusinessLogicException
	{
		super(formName, initialValuesSource, CreationType.system, creationSourceType, messageCollector);
	}

	protected boolean isAnonymous()
	{
		return false;
	}

	protected Long getNodeTemporaryNumber()
	{
		return null;
	}

	protected LoginType getLoginType()
	{
		return null;
	}

	protected boolean isEmployeeCreate()
	{
		return false;
	}

	protected NewDocumentHandler getNewDocumentHandler() throws DocumentException, DocumentLogicException
	{
		return new NewDocumentHandler()
		{
			protected boolean isAnonymous()
			{
				return false;
			}
		};
	}
}
