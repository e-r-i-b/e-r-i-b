package com.rssl.phizic.business.deposits;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * @author Roshka
 * @ created 18.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductResolver implements URIResolver
{
	private DepositProduct product;

	public DepositProductResolver(DepositProduct product)
	{
		this.product = product;
	}

	/**
	 * Called by the processor when it encounters an xsl:include, xsl:import, or document() function.
	 *
	 * @param href An href attribute, which may be relative or absolute.
	 * @param base The base URI against which the first argument will be made absolute if the absolute URI is
	 *             required.
	 * @return A Source object, or null if the href cannot be resolved, and the processor should try to resolve
	 *         the URI itself.
	 * @throws javax.xml.transform.TransformerException
	 *          if an error occurs when trying to resolve the URI.
	 */
	public Source resolve(String href, String base) throws TransformerException
	{
		StreamSource source = null;

		if( href.equals("product/description.xml") )
		{
			String description = product.getDescription();
			StringReader reader = new StringReader(description);

			source = new StreamSource(reader);
		}

		return source;
	}
}