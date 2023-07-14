package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Mescheryakova
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class VirtualCardsSource implements EntityListSource
{
	private CardProductService cardProductService = new CardProductService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}
	
	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder( final Map<String,String> params ) throws BusinessException
    {
	    List<CardProduct> cardProducts = cardProductService.findActivityVirtualCards();

	    EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();
	    for(CardProduct cardProduct : cardProducts)
	    {
		    builder.openEntityTag(cardProduct.getId().toString());
	        builder.appentField("name", cardProduct.getName());
		    builder.appentField("id", cardProduct.getId().toString());

		    String kind = "";
		    for (CASNSICardProduct cas_nsi_card_product : cardProduct.getKindOfProducts())
		    {
			    if (!StringHelper.isEmpty(kind))
			        kind += ",";			    
			    kind += "{ 'kind':" + cas_nsi_card_product.getProductId() + ", 'subkind':" + cas_nsi_card_product.getProductSubId()
					    +  ",'currency' :{ 'code':'" + cas_nsi_card_product.getCurrency().getCode() + "', 'number':'" + cas_nsi_card_product.getCurrency().getNumber() + "'}}";

		    }
		    builder.appentField("kind", kind);

		    builder.closeEntityTag();
	    }
 	    builder.closeEntityListTag();

        return builder;
    }
}
