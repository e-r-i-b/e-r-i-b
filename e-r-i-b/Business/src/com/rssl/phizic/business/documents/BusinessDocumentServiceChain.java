package com.rssl.phizic.business.documents;

import org.hibernate.Session;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Balovtsev
 * @since  22.04.2015.
 */
public class BusinessDocumentServiceChain<Type>
{
	private final Set<BusinessDocumentChainElement<Type>> chain = new LinkedHashSet<BusinessDocumentChainElement<Type>>();

	/**
	 *
	 * @param link
	 * @return
	 */
	public final BusinessDocumentServiceChain<Type> link(BusinessDocumentChainElement<Type> link)
	{
		if (link == null)
		{
			throw new IllegalArgumentException("Параметр 'link' должен быть задан");
		}

		chain.add(link);
		return this;
	}

	public Type whileNull(Session session, Map<String, ?> parameters) throws Exception
	{
		for (BusinessDocumentChainElement<Type> next : chain)
		{
			Type result = next.chain(session, parameters);

			if (result != null)
			{
				return result;
			}
		}

		return null;
	}
}
