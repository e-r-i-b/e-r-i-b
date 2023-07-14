package com.rssl.phizic.business.documents;

import org.hibernate.Session;

import java.util.Map;

/**
* @author Balovtsev
* @since  22.04.2015.
*/
public interface BusinessDocumentChainElement<Type>
{
	/**
	 *
	 * @param parameters параметры запроса
	 * @return результат выполнения
	 * @throws Exception
	 */
	Type chain(final Session session, final Map<String, ?> parameters) throws Exception;
}
