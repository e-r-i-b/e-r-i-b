package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.deposits.DepositEntityService;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntitySubType;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductRate;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.BooleanUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Сорс для получения описания вкладного продукта из сущности, построенной на справочниках ЦАС НСИ
 * @author EgorovaA
 * @ created 22.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntitySource implements EntityListSource
{
	private static final DepositEntityService depositEntityService = new DepositEntityService();
	private static final ClientPromoCodeService clientPromoCodeService = new ClientPromoCodeService();
	private static final DepositProductService depositService = new DepositProductService();

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getDepositEntityDescription(params).toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getDepositEntityDescription(params).toString());
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

	private XmlEntityBuilder getDepositEntityDescription(Map<String, String> params) throws BusinessException
	{
		DecimalFormat decimalFormat = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.US));

		Long depositType = Long.valueOf(params.get("depositType"));
		Long depositGroup = Long.valueOf(params.get("depositGroup"));
		Long group = -22L == depositGroup ? 0L : depositGroup;

		List<PromoCodeDeposit> actualClientPromoCodes = new ArrayList<PromoCodeDeposit>();
		ClientPromoCode clientPromoCode = clientPromoCodeService.getPromoCodeBySegment(params.get("segment"));
		if (clientPromoCode != null)
			actualClientPromoCodes.add(clientPromoCode.getPromoCodeDeposit());

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		String clientTariffPlan = StringHelper.getZeroIfEmptyOrNull(personData.getPerson().getTarifPlanCodeType());
		Department clientTB = personData.getTb();

		DepositProductEntity entity = depositEntityService.getDepositProductEntity(depositType, group, actualClientPromoCodes, TariffPlanHelper.getDictionaryTariffCodes(), PersonHelper.isPensioner(), Long.valueOf(clientTariffPlan), false, clientTB.getRegion());

		List<DepositProductEntitySubType> entitySubTypes = depositService.getEntitySubTypesInfo(depositType, group);
		Map<Long, String> subTypeInfo = buildSubTypeInfo(entitySubTypes);
		String name = entity.getGroupName();
		String groupInfo = buildGroupInfo(entity);

		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("product");
		builder.openEntityTag("data");
		builder.openEntityTag("options");

		Map<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> rates = entity.getDepositRates();

		for (String currency : rates.keySet())
		{
			Map<BigDecimal, Map<String, List<DepositProductRate>>> balanceRates = rates.get(currency);
			for (BigDecimal balance : balanceRates.keySet())
			{
				Map<String, List<DepositProductRate>> periodRates = balanceRates.get(balance);
				for (List<DepositProductRate> periodRate : periodRates.values())
				{
					for (DepositProductRate rate : periodRate)
					{
						builder.openEntityTag("element");

						builder.append(subTypeInfo.get(rate.getDepositSubType()));

						if (rate.getPeriod() != null)
						{
							builder.createEntityTag("period", rate.getPeriod());
							builder.createEntityTag("periodInDays", rate.getPeriodBegin().toString() + "-" + rate.getPeriodEnd().toString());
						}
						builder.createEntityTag("currencyCode", currency);
						builder.createEntityTag("minBalance", decimalFormat.format(balance));
						builder.createEntityTag("interestRate", decimalFormat.format(rate.getBaseRate()));
						builder.createEntityTag("interestDateBegin", dateFormat.format(rate.getDateBegin().getTime()));
						builder.createEntityTag("subTypeName", name);
						builder.createEntityTag("tariffPlanCode", rate.getClientCategory().toString());
						builder.createEntityTag("segmentCode", rate.getSegment().toString());
						builder.createEntityTag("availToOpen", "true");

						// добавить информацию о группе
						builder.append(groupInfo);

						builder.closeEntityTag("element");
					}
				}
			}
		}
		builder.closeEntityTag("options");
		builder.closeEntityTag("data");
		builder.closeEntityTag("product");

		return builder;
	}

	private String buildGroupInfo(DepositProductEntity entity) throws BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag("group");

		// название и код группы
		builder.createEntityTag("groupCode", entity.getGroupCode().toString());
		builder.createEntityTag("groupName", entity.getGroupName());

		// параметры группы
		builder.openEntityTag("groupParams");
		builder.createEntityTag("pensionRate", String.valueOf(BooleanUtils.isNotFalse(entity.getAllowPensionRates())));
		builder.createEntityTag("promoCodeSupported", String.valueOf(BooleanUtils.isTrue(entity.getPromoCodeSupported())));
		builder.closeEntityTag("groupParams");

		builder.closeEntityTag("group");

		return builder.toString();
	}

	private Map<Long, String> buildSubTypeInfo(List<DepositProductEntitySubType> entitySubTypes) throws BusinessException
	{
		Map<Long, String> subTypeInfo = new HashMap<Long, String>();
		DecimalFormat decimalFormat = new DecimalFormat("########0.00", new DecimalFormatSymbols(Locale.US));

		for (DepositProductEntitySubType entitySubType : entitySubTypes)
		{
			XmlEntityBuilder builder = new XmlEntityBuilder();

			builder.createEntityTag("id", entitySubType.getId().toString());
			builder.createEntityTag("subTypeName", entitySubType.getName());
			builder.createEntityTag("dateBegin", dateFormat.format(entitySubType.getDateBegin().getTime()));
			builder.createEntityTag("dateEnd", dateFormat.format(entitySubType.getDateEnd().getTime()));
			BigDecimal minAdditionalFee = entitySubType.getMinAdditionalFee() == null ? new BigDecimal(0) : entitySubType.getMinAdditionalFee();
			builder.createEntityTag("minAdditionalFee", decimalFormat.format(minAdditionalFee));

			subTypeInfo.put(entitySubType.getId(), builder.toString());
		}

		return subTypeInfo;
	}
}
