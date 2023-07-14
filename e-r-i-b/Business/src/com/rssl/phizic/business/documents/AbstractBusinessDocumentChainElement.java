package com.rssl.phizic.business.documents;

import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
* @author Balovtsev
* @since  22.04.2015.
*/
public abstract class AbstractBusinessDocumentChainElement<Type> implements BusinessDocumentChainElement<Type>
{
	private final String queryName;

	/**
	 * @param queryName название именнованного запроса
	 */
	protected AbstractBusinessDocumentChainElement(String queryName)
	{
		if (StringHelper.isEmpty(queryName))
		{
			throw new IllegalArgumentException("Параметр 'queryName' должен быть задан");
		}

		this.queryName = queryName;
	}

	public Type chain(Session session, Map<String, ?> parameters) throws Exception
	{
		Query    query    = session.getNamedQuery(queryName);
		LockMode lockMode = (LockMode) parameters.get("lockMode");

		if (lockMode != null)
		{
			query.setLockMode("document", lockMode);
		}

		parameters.remove("lockMode");

		for (String key : parameters.keySet())
		{
			query.setParameter(key, parameters.get(key));
		}

		return doChain(query);
	}

	/**
	 *
	 * @param namedQuery именованный запрос
	 * @return результат выполнения запроса
	 * @throws Exception
	 */
	protected abstract Type doChain(Query namedQuery) throws Exception;
}
