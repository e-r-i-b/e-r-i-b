package com.rssl.phizic.business.xslt.lists;

import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.ikfl.crediting.OfferTopUp;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionHelper;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.resources.external.ActiveLoanFilter;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.loans.LoanPrivateDetailsJMSRequestSender;
import com.rssl.phizicgate.esberibgate.types.loans.LoanImpl;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanDetailsRs;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Balovtsev
 * @since 04.02.2015.
 */
public class ExtendedLoanClaimLoanOffersListSource implements EntityListSource
{
	private static final Log          log          = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getEntityList().toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getEntityList().toString());
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

	private EntityList getEntityList() throws  BusinessException, BusinessLogicException
	{
		EntityList           entityList = new EntityList();
		PersonData           personData = PersonContext.getPersonDataProvider().getPersonData();
		List<LoanLink>       loans      = personData.getLoans(new ActiveLoanFilter());
		SortedSet<LoanOffer> loanOffers = new TreeSet<LoanOffer>(LoanClaimHelper.LOAN_OFFER_COMPARATOR);

		try
		{
			loanOffers.addAll(LoanOfferHelper.filterLoanOffersByEndDate(personData.getLoanOfferByPersonData(null, null)));

			for (LoanOffer loanOffer : loanOffers)
			{
				CreditProductCondition creditProductCondition = CreditProductConditionHelper.getCreditProductConditionByLoanOffer(loanOffer);

				String productTypeCode = loanOffer.getProductTypeCode();
				if (creditProductCondition != null)
					productTypeCode = creditProductCondition.getCreditProductType().getCode();

				if (productTypeCode != null)
				{
					Entity entity = new Entity("LOAN_OFFER", null);
					entity.addField(new Field("id", loanOffer.getOfferId().toString()));
					entity.addField(new Field("rate", String.valueOf(loanOffer.getPercentRate())));
					entity.addField(new Field("terbank", String.valueOf(loanOffer.getTerbank())));
					entity.addField(new Field("duration", String.valueOf(loanOffer.getDuration())));
					entity.addField(new Field("maxLimit", String.valueOf(loanOffer.getMaxLimit().getDecimal())));
					entity.addField(new Field("currency", String.valueOf(loanOffer.getMaxLimit().getCurrency().getCode())));
					entity.addField(new Field("productName", String.valueOf(loanOffer.getProductName())));

					for (OfferCondition condition : loanOffer.getConditions())
					{
						Entity entityCondition = new Entity("CONDITION", null);
						entityCondition.addField(new Field("offerId", String.valueOf(condition.getOfferId())));
						entityCondition.addField(new Field("amount", String.valueOf(condition.getAmount())));
						entityCondition.addField(new Field("period", String.valueOf(condition.getPeriod())));
						entityCondition.addField(new Field("rate", String.valueOf(condition.getRate())));
						entity.addEntity(entityCondition);
					}

					if (clientConfig.isJmsForLoanEnabled())
					{
						for (Entity topUpEntity : buildTopUpEntities(personData, loans, loanOffer))
						{
							entity.addEntity(topUpEntity);
						}
					}

					entityList.addEntity(entity);
				}
			}
		}
		catch (Exception e)
		{
			log.error("Возникла ошибка при построении справочника loan-claim-loan-offers.xml",e);
		}

		return entityList;
	}

	private Set<Entity> buildTopUpEntities(PersonData personData, List<LoanLink> loans, LoanOffer loanOffer) throws BusinessException, BusinessLogicException
	{
		Set<OfferTopUp> topUps   = loanOffer.getTopUps();
		Set<Entity>     entities = new HashSet<Entity>();

		if (CollectionUtils.isNotEmpty(loans) && CollectionUtils.isNotEmpty(topUps))
		{
			String logonCardDepartment = personData.getLogin().getLastLogonCardDepartment();
			LoanPrivateDetailsJMSRequestSender requestSender = new LoanPrivateDetailsJMSRequestSender(GateSingleton.getFactory());

			for (OfferTopUp topUp : topUps)
			{
				ListIterator<LoanLink> loanIterator = loans.listIterator();

				while (loanIterator.hasNext())
				{
					LoanLink link = loanIterator.next();
					Loan     loan = link.getLoan();

					if (topUp.getAgreementNumber().equals(loan.getAgreementNumber()))
					{
						GetPrivateLoanDetailsRs detailResponse = null;

						try
						{
							LoanImpl request = new LoanImpl();
							request.setId(loan.getId());
							request.setProdType(topUp.getIdContract());

							detailResponse = requestSender.send(request, logonCardDepartment);
						}
						catch (GateException e)
						{
							throw new BusinessException(e);
						}
						catch (GateLogicException e)
						{
							throw new BusinessLogicException(e);
						}


						Entity entity = new Entity("TOPUP", null);
						//Название кредита
						entity.addField(new Field("name", StringHelper.isEmpty(link.getName()) ? loan.getLoanType() : link.getName()));
						//Номер кредитного договора
						entity.addField(new Field("agreementNumber", loan.getAgreementNumber()));
						//Изначальная сумма кредита
						entity.addField(new Field("amount",   String.valueOf(loan.getLoanAmount().getDecimal())));
						entity.addField(new Field("currency", loan.getLoanAmount().getCurrency().getCode()));
						//Дата выдачи кредита
						entity.addField(new Field("termStart", DateHelper.formatDateWithMonthString(loan.getTermStart())));
						//Плановая дата погашения
						entity.addField(new Field("termEnd",   DateHelper.formatDateWithMonthString(loan.getTermEnd())));
						//Процентная ставка
						entity.addField(new Field("creditingRate", String.valueOf(detailResponse.getLoanRec().getCreditingRate().doubleValue())));
						//Сумма платежа для полного досрочного погашения
						entity.addField(new Field("repaymentAmount", String.valueOf(detailResponse.getLoanRec().getFullRepaymentAmount().doubleValue())));

						entities.add(entity);
						loanIterator.remove();
						break;
					}
				}
			}
		}

		return entities;
	}
}
