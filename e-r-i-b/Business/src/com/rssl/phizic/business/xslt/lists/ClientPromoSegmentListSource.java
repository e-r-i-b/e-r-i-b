package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeComparator;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author EgorovaA
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение промокодов клиента
 */
public class ClientPromoSegmentListSource implements EntityListSource
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ClientPromoCodeService service = new ClientPromoCodeService();

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getSegmentList().toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getSegmentList().toString());
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

	private EntityListBuilder getSegmentList() throws BusinessException
	{
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		if(PersonContext.isAvailable() && PermissionUtil.impliesService("CreatePromoAccountOpeningClaimService"))
		{
			List<ClientPromoCode> promoCodes = service.getActiveClientPromoCodes(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
			Collections.sort(promoCodes, new ClientPromoCodeComparator());
			for (ClientPromoCode promoCode : promoCodes)
			{
				builder.openEntityTag(String.valueOf(promoCode.getPromoCodeDeposit().getCodeS()));
				builder.appentField("text", promoCode.getName());
				builder.appentField("priority", String.valueOf(promoCode.getPromoCodeDeposit().getPrior()));
				builder.appentField("shortDescription", String.valueOf(promoCode.getPromoCodeDeposit().getNameS()));
				builder.appentField("fullDescription", String.valueOf(promoCode.getPromoCodeDeposit().getNameF()));
				builder.closeEntityTag();
			}
		}

		builder.closeEntityListTag();

		return builder;
	}

}
