package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCodeService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Сервис для работы с промо - кодами для открытия вкладов
 *
 * @ author: Gololobov
 * @ created: 17.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositService extends SimpleService
{
    protected static final ClientPromoCodeService clientPromoCodesService = new ClientPromoCodeService();

	private static final String QUERY_PREFIX = PromoCodeDeposit.class.getName() + ".";
	private static final String PROMO_CODE_MESSAGES_IS_NOT_FOUND = "Не обнаружены сообщения об ошибках проверки промо-кодов";
	private static final String CODE_IS_EMPTY = "Не указан промо-код";
	private static final String CODE_ACCESSIBLE_ERROR_MESSAGE = "Ошибка при проверке введенного промо-кода: %s";

	private static final String REGEXP_CODE_LENGTH = "(?s).{%s,%s}";

	private static final Cache PROMO_CODES_CACHE;
	private static final Object LOCKER = new Object();
	private static final String PROMO_CODES_DEPOSIT_CACHE_KEY = "promoCodesDepositListKey";
	public static final String PROMO_CODES_DEPOSIT_CACHE = "PromoCodeDepositService.getPromoCodesDepositFromCache";

	static
	{
		PROMO_CODES_CACHE = CacheProvider.getCache(PROMO_CODES_DEPOSIT_CACHE);

		if (PROMO_CODES_DEPOSIT_CACHE == null)
			throw new RuntimeException("Не найден кеш справочника промо-кодов");
	}

	static void doClearCache(String key)
	{
		synchronized (LOCKER)
		{
			PROMO_CODES_CACHE.remove(key);
		}
	}

	/**
	 * Возвращает сущность "промо - код" из справочнка по коду. Поиск осуществляется по маске.
	 * @param code - "промо - код"
	 * @return
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public PromoCodeDeposit getPromoCodeDepositByCode(String code) throws BusinessLogicException, BusinessException
	{
		if (StringHelper.isEmpty(code))
			throw new BusinessLogicException(CODE_IS_EMPTY);

		PromoCodesDepositConfig promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
		RegexpFieldValidator regexpValidator = new RegexpFieldValidator();
		try
		{
			Map<String, PromoCodesMessage> promoCodesMessagesMap = promoCodesDepositConfig.getPromoCodesMessagesMap();
			if (CollectionUtils.isEmpty(promoCodesMessagesMap.keySet()))
				throw new BusinessLogicException(PROMO_CODE_MESSAGES_IS_NOT_FOUND);

			PromoCodesMessage promoCodesMessage02 = promoCodesMessagesMap.get("MSG02");
			PromoCodesMessage promoCodesMessage03 = promoCodesMessagesMap.get("MSG03");
			PromoCodesMessage promoCodesMessage04 = promoCodesMessagesMap.get("MSG04");
			PromoCodesMessage promoCodesMessage05 = promoCodesMessagesMap.get("MSG05");
			PromoCodesMessage promoCodesMessage06 = promoCodesMessagesMap.get("MSG06");

			//1. Определение минимально и максимально допустимого количества символов
			if (promoCodesDepositConfig.getMinCountSymbols() == 0 || promoCodesDepositConfig.getMaxCountSymbols() == 0) {
                log.error("Ошибка при получении настройки количества символов промо-кода");
				throw new WrongPromoCodeLogicException(promoCodesMessage02);
            }

			//2. Проверка, что значение введенного клиентом кода содержит только допустимые символы и имеет допустимую длину
			//2.1. Проверка длины введенного промо-кода
			regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP,
					String.format(REGEXP_CODE_LENGTH, promoCodesDepositConfig.getMinCountSymbols(), promoCodesDepositConfig.getMaxCountSymbols()));
			if (!regexpValidator.validate(code)) {
                StringBuilder err = new StringBuilder();
                err.append("Неверная длинна промокода ");
                err.append(code);
                err.append(". min: ");
                err.append(promoCodesDepositConfig.getMinCountSymbols());
                err.append(", max: ");
                err.append(promoCodesDepositConfig.getMaxCountSymbols());
                log.error(err.toString());
				throw new WrongPromoCodeLogicException(promoCodesMessage03);
            }
			//2.2. Проверка что значение введенного клиентом кода содержит только допустимые символы
			String accessibleSymbols = promoCodesDepositConfig.getAccessibleSymbolsForRegexpValidator();
			if (StringHelper.isNotEmpty(accessibleSymbols))
			{
				regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP, accessibleSymbols);
				if (!regexpValidator.validate(code)) {
                    StringBuilder err = new StringBuilder();
                    err.append("Промокод содержит недопустимые символы ");
                    err.append(code);
                    err.append(". pattern: ");
                    err.append(accessibleSymbols);
                    log.error(err.toString());
					throw new WrongPromoCodeLogicException(promoCodesMessage03);
                }
			}

			//3. Поиск записей в справочнике промо-кодов по маске промо-кода. Записи ищутся с учетом кеша и актуальности на текущую дату.
			List<PromoCodeDeposit> promoCodeDepositList = getPromoCodesDepositListActualByCurrentDate();
            PromoCodeDeposit promoCodeDeposit = null;
			for (PromoCodeDeposit promoCodeDepositItem : promoCodeDepositList)
			{
				//Проверка введенного промо-кода по маске из справочника промо-кодов
				if (checkMask(code, promoCodeDepositItem.getMask())) {
					promoCodeDeposit = promoCodeDepositItem;
                    break;
                }
			}

            if (promoCodeDeposit == null) {
                log.error("Промокод с подходящей маской не найден " + code);
                //3.1. Не нашлась запись с маской промо-кода
                throw new WrongPromoCodeLogicException(promoCodesMessage03);
            }

            /** проверка промокодов в разрезхе клиента **/
            Long loginId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
            List<ClientPromoCode> clientPromoCodes = clientPromoCodesService.getAllClientPromoCodes(loginId);
            List<ClientPromoCode> activeClientPromoCodes = clientPromoCodesService.getActiveClientPromoCodes(clientPromoCodes);

            if (clientPromoCodes == null)
                clientPromoCodes = new ArrayList<ClientPromoCode>();

            // вязанка codeG
			if (clientPromoCodesContainsPromoWithCodeG(clientPromoCodes, promoCodeDeposit.getCodeG()))
			{
				log.error("Промокод с таким кодом промоакции был введён ранее " + promoCodeDeposit.getCodeG());
                throw new WrongPromoCodeLogicException(promoCodesMessage04);
            }

            // Количество когда-либо введенных промо-кодов
            if (promoCodeDeposit.getHistCount() != null && promoCodeDeposit.getHistCount() > 0) {
                if (promoCodeDeposit.getHistCount() <= clientPromoCodes.size()) {
                    log.error("Превышенно число когда-либо введёных промокодов");
                    throw new WrongPromoCodeLogicException(promoCodesMessage06);
                }
            }

            // Количество одновременно действующих промо-кодов
            if (promoCodeDeposit.getActiveCount() != null && promoCodeDeposit.getActiveCount() > 0) {
                if (promoCodeDeposit.getActiveCount() <= activeClientPromoCodes.size()) {
                    log.error("Превышенно число одновременно действующих промокодов");
                    throw new WrongPromoCodeLogicException(promoCodesMessage05);
                }
            }

            return promoCodeDeposit;
		}
		catch (TemporalDocumentException e)
		{
			throw new BusinessLogicException(String.format(CODE_ACCESSIBLE_ERROR_MESSAGE, code), e);
		}
	}

	private boolean clientPromoCodesContainsPromoWithCodeG(List<ClientPromoCode> clientPromoCodes, Long codeG)
	{
		for (ClientPromoCode promo : clientPromoCodes)
			if (codeG.equals(promo.getPromoCodeDeposit().getCodeG()))
				return true;
		return false;
	}

	/**
	 * Загрузка справочника промо-кодов с учетом кеша
	 * @return справочник промо-кодов
	 * @throws BusinessException
	 */
	public List<PromoCodeDeposit> getPromoCodesDepositListActualByCurrentDate() throws BusinessException
	{
		//Ключ справочника промо-кодов актуального на текущую дату
		String promoCodesKey = PROMO_CODES_DEPOSIT_CACHE_KEY+"|"+ DateHelper.formatDateYYYYMMDD(Calendar.getInstance());
		List<PromoCodeDeposit> promoCodesDeposits = getPromoCodesDepositFromCache(promoCodesKey);
		if (CollectionUtils.isNotEmpty(promoCodesDeposits))
			return promoCodesDeposits;

		synchronized (LOCKER)
		{
			List<PromoCodeDeposit> promoCodesDepositList = getPromoCodesDepositFromCache(promoCodesKey);
			if (CollectionUtils.isNotEmpty(promoCodesDepositList))
				return promoCodesDepositList;

			return addToCache(promoCodesKey, getActualPromoCodesDepositList());
		}
	}

	/**
	 * Справочник промо-кодов из кеша
	 * @return
	 */
	private List<PromoCodeDeposit> getPromoCodesDepositFromCache(String key)
	{
		Element element  = PROMO_CODES_CACHE.get(key);
		if ( element != null )
		{
			return (List<PromoCodeDeposit>) element.getObjectValue();
		}
		return null;
	}

	private List<PromoCodeDeposit> addToCache(String key, List<PromoCodeDeposit> promoCodeDepositList)
	{
		if (CollectionUtils.isNotEmpty(promoCodeDepositList))
			PROMO_CODES_CACHE.put(new Element(key, promoCodeDepositList));
		return promoCodeDepositList;
	}

	/**
	 * Список актуальных на текущую дату промо-кодов
	 * @return
	 */
	public List<PromoCodeDeposit> getActualPromoCodesDepositList() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<PromoCodeDeposit>>()
			{
				public List<PromoCodeDeposit> run(Session session) throws Exception
				{
					Calendar currentDate = Calendar.getInstance();

					Query query = session.getNamedQuery(QUERY_PREFIX + "findActualByDate");
					query.setParameter("actualDate", currentDate);
					//noinspection unchecked
					return query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверка промо-кода на соответствие маске
	 * @param promoCode - промо-код
	 * @param promoCodeMask - маска промо-кода
	 * @return
	 */
	private boolean checkMask(String promoCode, String promoCodeMask)
	{
		if (StringHelper.isEmpty(promoCodeMask) || StringHelper.isEmpty(promoCode))
			return false;

		String regexpParametr = promoCodeMask.replace("%",".+");//обязательно наличие любого символа(.) повторяющегося не менее 1-го раза (+)
		regexpParametr = regexpParametr.replace("?",".{1}");//обязательно наличие любого символа(.) повторяющегося "1" раз({1})

		RegexpFieldValidator regexpValidator = new RegexpFieldValidator();
		regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP, regexpParametr.toUpperCase());

		try
		{
			return regexpValidator.validate(promoCode.toUpperCase());
		}
		catch (TemporalDocumentException e)
		{
			log.error("Ошибка при проверке промо-кода: " + promoCode, e);
			return false;
		}
	}

	/**
	 * Поиск промо-кода по уникальному идентификатору
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	public PromoCodeDeposit getPromoCodeDepositByUUID(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PromoCodeDeposit.class).add(Expression.eq("uuid", uuid));
		return super.findSingle(criteria);
	}
}
