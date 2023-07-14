package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.*;
import com.rssl.phizic.business.loanOffer.*;
import com.rssl.phizic.utils.xml.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * @author Balovtsev
 * @since 19.01.14
 */
public class LoanOffersDescriptions implements EntityListSource
{
    public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
    {
        EntityListBuilder builder = getEntityListBuilder();
        return new StreamSource(new StringReader(builder.toString()));
    }

    public Document getDocument(Map<String, String> params) throws BusinessException
    {
        EntityListBuilder builder = getEntityListBuilder();
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

    private EntityListBuilder getEntityListBuilder() throws BusinessException
    {
        EntityListBuilder builder = new EntityListBuilder();
        builder.openEntityListTag();

        List<String> values = LoanOfferHelper.getProfitPreapprovedCredit();
        if (values.size() > 0)
        {
            builder.openEntityTag("descriptions");
            for (String value : values)
            {
                builder.appentField("value", value);
            }
            builder.closeEntityTag();
        }

        builder.closeEntityListTag();
        return builder;
    }
}