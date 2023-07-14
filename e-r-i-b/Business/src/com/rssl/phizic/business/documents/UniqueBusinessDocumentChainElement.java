package com.rssl.phizic.business.documents;

import org.hibernate.Query;

/**
 * @author Balovtsev
 * @since 22.04.2015.
 */
public class UniqueBusinessDocumentChainElement<Type> extends AbstractBusinessDocumentChainElement<Type>
{
	/**
	 * @param queryName
	 */
	public UniqueBusinessDocumentChainElement(String queryName)
	{
		super(queryName);
	}

	@Override
	protected Type doChain(Query namedQuery) throws Exception
	{
		//noinspection unchecked
		return (Type) namedQuery.uniqueResult();
	}
}
