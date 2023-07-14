package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.security.SecurityLogicException;
import org.w3c.dom.Document;

import java.io.Serializable;
import javax.xml.transform.Source;

/**
 * @author Roshka
 * @ created 18.05.2006
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmFormOperation extends ConfirmableOperation, Serializable
{
	/**
	 * »нициализировать операцию
	 * @param source источник документа
	 */
	void initialize(DocumentSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @return метаданные документа
	 */
	Metadata getMetadata();

	/**
	 * @return строка идентифицирующа€ метаданные
	 */
	String getMetadataPath() throws BusinessException;

	/**
	 * создать Source с данными формы
	 * @return данные формы;
	 */
	Source createFormData() throws BusinessException;

	/**
	 * ќтправка документа на обработку (например в ретейл).
	 * @throws SecurityLogicException - ошибка подтверждени€ (передан неверный пароль, неверна€ подпись etc)
	 * @throws BusinessLogicException - шлюз отклонил передачу из-за неверно заполненных пользователем данных
	 */
	void confirm() throws BusinessException, SecurityLogicException, BusinessLogicException;

	/**
	 * @return XML представление формы
	 * @throws BusinessException
	 */
    Document createDocumentXml() throws BusinessException;

}
