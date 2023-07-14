package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.test.MockHibernateExecutor;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import static org.apache.commons.lang.StringUtils.deleteWhitespace;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Egorovaa
 * Date: 11.11.2011
 * Time: 12:06:31
 */
public class MockProductService
{
	/**
	 * Получаем запись из таблицы GFL. Если объект не пустой, то по этим входным данным должны вернуться продукты
	 * @param bankAcctInqRq входные параметры запроса
	 * @return RequestGFL
	 * @throws BusinessException
	 */
	public RequestGFL getGFL(final BankAcctInqRq_Type bankAcctInqRq) throws BusinessException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			final PersonInfo_Type personInfo = bankAcctInqRq.getCustInfo().getPersonInfo();
			final CardAcctId_Type cardAcctId = bankAcctInqRq.getCardAcctId();
			final IdentityCard_Type identityCard = personInfo.getIdentityCard();

			final Calendar birthday = parseCalendar(formatter, personInfo.getBirthday());
			final String idSeries = deleteWhitespace(identityCard.getIdSeries());
			final Calendar issueDt = parseCalendar(formatter, identityCard.getIssueDt());
			final String cardNum = (cardAcctId != null) ? cardAcctId.getCardNum() : null;

			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<RequestGFL>()
			{
				public RequestGFL run(Session session) throws ParseException
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getGFL");
					query.setParameter("rbTbBrchId", bankAcctInqRq.getBankInfo().getRbTbBrchId());
					query.setParameter("birthday", birthday);
					query.setParameter("lastName", personInfo.getPersonName().getLastName());
					query.setParameter("firstName", personInfo.getPersonName().getFirstName());
					query.setParameter("middleName", personInfo.getPersonName().getMiddleName());
					query.setParameter("idType", identityCard.getIdType());
					query.setParameter("idNum", identityCard.getIdNum());
					query.setParameter("idSeries", idSeries);
					query.setParameter("issuedBy", identityCard.getIssuedBy());
					query.setParameter("issueDt", issueDt);
					query.setParameter("cardNum", cardNum);

					query.setMaxResults(1);
					return (RequestGFL) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	private static Calendar parseCalendar(SimpleDateFormat formatter, String dateAsString) throws ParseException
	{
		if (StringHelper.isEmpty(dateAsString))
			return null;
		return DateHelper.toCalendar(formatter.parse(dateAsString));
	}

	/**
	 * Получаем карты, используя id GFL-запроса (для GFL)
	 * @param id идентификатор GFL-запроса
	 * @return список карт, возвращаемых по такому запросу
	 * @throws BusinessException
	 */
	public List<MockCard> getCards(final Long id) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<List<MockCard>>()
			{
				public List<MockCard> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getCardsForGFL");
					query.setParameter("id", id);
					return (List<MockCard>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Получаем вклады, используя id GFL-запроса (для GFL)
	 * @param id идентификатор GFL-запроса
	 * @return список вкладов, возвращаемых по такому запросу
	 * @throws BusinessException
	 */
	public List<MockDeposit> getDeposits(final Long id) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<List<MockDeposit>>()
			{
				public List<MockDeposit> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getDepositsForGFL");
					query.setParameter("id", id);
					return (List<MockDeposit>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Получаем ОМС, используя id GFL-запроса (для GFL)
	 * @param id идентификатор GFL-запроса
	 * @return список ОМС, возвращаемых по такому запросу
	 * @throws BusinessException
	 */
	public List<MockIMAccount> getIMAccounts(final Long id) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<List<MockIMAccount>>()
			{
				public List<MockIMAccount> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getIMAccountsForGFL");
					query.setParameter("id", id);
					return (List<MockIMAccount>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Получаем кредиты, используя id GFL-запроса (для GFL)
	 * @param id идентификатор GFL-запроса
	 * @return список кредитов, возвращаемых по такому запросу
	 * @throws BusinessException
	 */
	public List<MockCredit> getCredits(final Long id) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<List<MockCredit>>()
			{
				public List<MockCredit> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getCreditsForGFL");
					query.setParameter("id", id);
					return (List<MockCredit>) query.list();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}


	/**
	 * Находим карту, используя входные данные (CRDWI)
	 * @param parameters входные параметры, которые приходят для CRDWI
	 * @param cardInfo информация о карте
	 * @return RequestCRDWI, содержащий данные о запросе (как показатель, что ответ по входным параметрам
	 * должен быть получен)	и карту (MockCard), из параметров которого будет сформирован ответ для CRDWI
	 * @throws BusinessException
	 */
	public RequestCRDWI getCardByCRDWI(final IFXRq_Type parameters, final CardInfo_Type cardInfo) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<RequestCRDWI>()
			{
				public RequestCRDWI run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getCardByCRDWI");
					query.setParameter("rbTbBrchId", parameters.getCardAcctDInqRq().getBankInfo().getRbTbBrchId());
					query.setParameter("systemId", cardInfo.getCardAcctId().getSystemId());
					query.setParameter("cardNum", cardInfo.getCardAcctId().getCardNum());
					if (cardInfo.getCardAcctId().getBankInfo() != null)
						query.setParameter("rbBrchId", cardInfo.getCardAcctId().getBankInfo().getRbBrchId());
					else
						query.setParameter("rbBrchId", null);
					query.setMaxResults(1);
					return (RequestCRDWI) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Используя входные параметры ACC_DI, получаем вклад
	 * @param parameters
	 * @param depAcctRec
	 * @return RequestACCDI, содержащий данные о запросе (как показатель, что ответ по входным параметрам
	 * должен быть получен)	и вклад (MockDeposit), из параметров которого будет сформирован ответ для ACC_DI
	 * @throws BusinessException
	 */
	public RequestACCDI getDepositByACCDI(final IFXRq_Type parameters, final DepAcctRec_Type depAcctRec) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<RequestACCDI>()
			{
				public RequestACCDI run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getDepositByACCDI");
					query.setParameter("rbTbBrchId", parameters.getAcctInfoRq().getBankInfo().getRbTbBrchId());
					query.setParameter("systemId", depAcctRec.getDepAcctId().getSystemId());
					query.setParameter("acctId", depAcctRec.getDepAcctId().getAcctId());
					if (depAcctRec.getDepAcctId().getBankInfo()!=null)
						query.setParameter("rbBrchId", depAcctRec.getDepAcctId().getBankInfo().getRbBrchId());
					else
						query.setParameter("rbBrchId", null);
					query.setMaxResults(1);
					return (RequestACCDI) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Находим ОМС, используя входные данные (IMA_IS)
	 * @param imsAcctId
	 * @param rbTbBrchId
	 * @return RequestIMAIS, содержащий данные о запросе (как показатель, что ответ по входным параметрам
	 * должен быть получен)	и ОМС (MockIMAccount), из параметров которого будет сформирован ответ для IMA_IS
	 * @throws BusinessException
	 */
	public RequestIMAIS getIMAccountIdByIMAIS(final ImsAcctId_Type imsAcctId, final String rbTbBrchId) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<RequestIMAIS>()
			{
				public RequestIMAIS run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getIMAccountByIMAIS");
					query.setParameter("rbTbBrchId", rbTbBrchId);
					query.setParameter("systemId", imsAcctId.getSystemId());
					query.setParameter("acctId", imsAcctId.getAcctId());
					if (imsAcctId.getBankInfo() != null)
						query.setParameter("rbBrchId", imsAcctId.getBankInfo().getRbBrchId());
					else
						query.setParameter("rbBrchId", null);
					query.setMaxResults(1);
					return (RequestIMAIS) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * Находим кредит, используя входные данные (LN_CI)
	 * @param parameters входные параметры запроса LN_CI
	 * @return RequestLNCI, содержащий данные о запросе (как показатель, что ответ по входным параметрам
	 * должен быть получен)	и кредит (MockCredit), из параметров которого будет сформирован ответ для LN_CI
	 * @throws BusinessException
	 */
	public RequestLNCI getCreditByLNCI(final LoanInfoRq_Type parameters) throws BusinessException
	{
		try
		{
			return MockHibernateExecutor.getInstance(false).execute(new HibernateAction<RequestLNCI>()
			{
				public RequestLNCI run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.test.esberibmock.MockProductService.getCreditByLNCI");
					query.setParameter("rbTbBrchId", parameters.getBankInfo().getRbTbBrchId());
					query.setParameter("systemId", parameters.getLoanAcctId().getSystemId());
					query.setParameter("acctId", parameters.getLoanAcctId().getAcctId());
					if (parameters.getLoanAcctId().getBankInfo() != null)
						query.setParameter("rbBrchId", parameters.getLoanAcctId().getBankInfo().getRbBrchId());
					else
						query.setParameter("rbBrchId", null);
					query.setMaxResults(1);
					return (RequestLNCI) query.uniqueResult();
				}
			});
		}
		catch (Exception ex)
		{
			throw new BusinessException(ex);
		}
	}
}
