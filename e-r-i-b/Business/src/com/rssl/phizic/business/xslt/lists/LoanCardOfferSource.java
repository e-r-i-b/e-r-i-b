package com.rssl.phizic.business.xslt.lists;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecordingService;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Moshenko
 * @ created 30.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardOfferSource implements EntityListSource
{
	private static final DepartmentsRecordingService departmentRecordingService = new DepartmentsRecordingService();


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
		String strId = params.get("offer");
		if(StringHelper.isEmpty(strId))
		{
			throw new BusinessException("Ќе задано предодобренное кредитное предложение");
		}

	    String isChangeLimit = params.get("changeLimit");
	    if (!StringHelper.isEmpty(isChangeLimit) && isChangeLimit.equals("true"))
	    {
		    OfferId offerId = OfferId.fromString(strId);
	        return buildChangeLimitCardOffer(offerId);
	    }

        return buildCardOffer(Long.valueOf(strId));
    }

	private EntityListBuilder buildCardOffer(long conditionId) throws BusinessException
	{
	    ConditionProductByOffer loanCardOfferWithInfo = PersonContext.getPersonDataProvider().getPersonData().findConditionProductByOffer(conditionId);

		if (loanCardOfferWithInfo.getOfferId() == null)
			throw new BusinessException("Ќе надено предожение по карте и соответствующий ему карточный продукт");

		if (loanCardOfferWithInfo.getOfferType() == LoanCardOfferType.changeLimit)
			throw new BusinessException("Ќайденное предложение неверного типа");

		EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();

		builder.openEntityTag(loanCardOfferWithInfo.getOfferId().toString());
	    builder.appentField("offerType", loanCardOfferWithInfo.getOfferType().getValue());
		builder.appentField("cardName", loanCardOfferWithInfo.getName());
		builder.appentField("amount", loanCardOfferWithInfo.getMaxLimit().getDecimal().toBigInteger().toString());
		builder.appentField("currency", loanCardOfferWithInfo.getMaxLimit().getCurrency().getCode());
		builder.appentField("conditionId", loanCardOfferWithInfo.getConditionId().toString());
		builder.appentField("interestRate", loanCardOfferWithInfo.getInterestRate().toString());
		builder.appentField("firstYearPaymentCurrency", loanCardOfferWithInfo.getFirstYearPayment().getCurrency().getCode());
		builder.appentField("nextYearPaymentCurrency", loanCardOfferWithInfo.getNextYearPayment().getCurrency().getCode());
		builder.appentField("firstYearPaymentDecimal", loanCardOfferWithInfo.getFirstYearPayment().getDecimal().toString());
		builder.appentField("nextYearPaymentDecimal", loanCardOfferWithInfo.getNextYearPayment().getDecimal().toString());

		builder.appentField("seriaAndNumber", loanCardOfferWithInfo.getSeriesAndNumber());
		builder.appentField("idWay", StringHelper.getNullIfNull(loanCardOfferWithInfo.getIdWay()));

		DepartmentsRecoding departmentsRecoding = departmentRecordingService.findDepartmentRecodingBySpoobk(StringHelper.getNullIfNull(loanCardOfferWithInfo.getTerbank()),
																											StringHelper.getNullIfNull(loanCardOfferWithInfo.getOsb()),
																											StringHelper.getNullIfNull(loanCardOfferWithInfo.getVsp()));

		if (departmentsRecoding != null)
		{
			builder.appentField("tb",  departmentsRecoding.getTbErib());
			builder.appentField("vsp", departmentsRecoding.getOfficeErib());
			builder.appentField("osb", departmentsRecoding.getOsbErib());
		}

		builder.closeEntityListTag();
        return builder;
	}

	private EntityListBuilder buildChangeLimitCardOffer(OfferId offerId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		LoanCardOffer loanCardOffer = personData.findLoanCardOfferById(offerId);

		if (loanCardOffer == null)
			throw new BusinessException("Ќе  найдено предложение по карте с id=" + offerId);

		if (loanCardOffer.getOfferType() != LoanCardOfferType.changeLimit)
			throw new BusinessException("Ќайденна€ за€вка не €вл€етс€ за€вкой на изменение лимита");

		EntityListBuilder builder = new EntityListBuilder();
	    builder.openEntityListTag();
		builder.openEntityTag(loanCardOffer.getOfferId().toString());
		
		builder.appentField("cardNumber",  MaskUtil.getCutCardNumber(CardsUtil.getClientCreditCard().getCard().getNumber()));
		builder.appentField("cardName", CardsUtil.getClientCreditCard().getName());
		builder.appentField("amount", loanCardOffer.getMaxLimit().getDecimal().toBigInteger().toString());
		builder.appentField("currency", loanCardOffer.getMaxLimit().getCurrency().getCode());
		builder.appentField("seriaAndNumber", loanCardOffer.getSeriesAndNumber());
		builder.appentField("idWay", StringHelper.getNullIfNull(loanCardOffer.getIdWay()));
		DepartmentsRecoding departmentsRecoding = departmentRecordingService.findDepartmentRecodingBySpoobk(
				loanCardOffer.getTerbank().toString(), loanCardOffer.getOsb().toString(),
				StringHelper.getNullIfNull(loanCardOffer.getVsp()));
		if (departmentsRecoding != null)
		{
			builder.appentField("tb", departmentsRecoding.getTbErib());
			builder.appentField("vsp", departmentsRecoding.getOfficeErib());
			builder.appentField("osb", departmentsRecoding.getOsbErib());
		}
		builder.appentField("offerType", loanCardOffer.getOfferType().getValue());
		
		builder.closeEntityTag();
		builder.closeEntityListTag();
        return builder;
	}
}
