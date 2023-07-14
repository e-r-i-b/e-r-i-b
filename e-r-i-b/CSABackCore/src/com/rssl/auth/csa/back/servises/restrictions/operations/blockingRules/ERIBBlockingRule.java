package com.rssl.auth.csa.back.servises.restrictions.operations.blockingRules;

import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.pattern.PatternUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 * Сущность ограничение на вход
 */
public class ERIBBlockingRule extends ActiveRecord
{
	public static final String GLOBAL_BLOCKING_MESSAGE = "По техническим причинам вход в систему временно заблокирован. Пожалуйста, повторите попытку позже.";

	private static final int AUTO_PROLONGATION_INTERVAL = 20; //интервал автопролонгации в мин
	private static final String RESTRICTION_MESSAGE = "%s. Предоставление услуги будет возобновлено ориентировочно %s (время московское). Приносим извинения за предоставленные неудобства.";
	private static final String TODAY_DATE_EXPIRE_FORMAT = "%1$tH:%1$tM";
	private static final String COMMON_DATE_EXPIRE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM";

	private Long id;
	private String codes;           //перечисленные через запятую маски кодов
	private String eribMessage;     //сообщение ЕРИБ отображаемое пользователю в случае если блокировка активна (причина блокировки)
	private Calendar expired;       //ориентировочние время восстановления доступа
	private boolean applyToERIB;    //распространяется на ЕРИБ
	private boolean applyToMAPI;    //распространяется на мАПи
	private String mapiMessage;     //сообщение мАПи отображаемое пользователю в случае если блокировка активна (причина блокировки)
	private boolean applyToERMB;    //распространяется на ЕРМБ
	private String ermbMessage;     //сообщение ЕРМБ отображаемое пользователю в случае если блокировка активна (причина блокировки)
	private boolean applyToATM;     //распространяется на АТМ
	private String atmMessage;      //сообщение АТМ отображаемое пользователю в случае если блокировка активна (причина блокировки)
	private static final Cache CACHE = CacheProvider.getCache("blocking-rule-cache");      //кеш блокировок
	private static final String DELIMITER = "|";


	public boolean isApplyToATM()
	{
		return applyToATM;
	}

	public void setApplyToATM(boolean applyToATM)
	{
		this.applyToATM = applyToATM;
	}

	public String getAtmMessage()
	{
		return atmMessage;
	}

	public void setAtmMessage(String atmMessage)
	{
		this.atmMessage = atmMessage;
	}

	public boolean isApplyToERMB()
	{
		return applyToERMB;
	}

	public void setApplyToERMB(boolean applyToERMB)
	{
		this.applyToERMB = applyToERMB;
	}

	public String getErmbMessage()
	{
		return ermbMessage;
	}

	public void setErmbMessage(String ermbMessage)
	{
		this.ermbMessage = ermbMessage;
	}

	public boolean isApplyToMAPI()
	{
		return applyToMAPI;
	}

	public void setApplyToMAPI(boolean applyToMAPI)
	{
		this.applyToMAPI = applyToMAPI;
	}

	public String getMapiMessage()
	{
		return mapiMessage;
	}

	public void setMapiMessage(String mapiMessage)
	{
		this.mapiMessage = mapiMessage;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCodes()
	{
		return codes;
	}

	public void setCodes(String codes)
	{
		this.codes = codes;
	}

	public Calendar getExpired()
	{
		return expired;
	}

	public void setExpired(Calendar expired)
	{
		this.expired = expired;
	}

	public String getEribMessage()
	{
		return eribMessage;
	}

	public void setEribMessage(String eribMessage)
	{
		this.eribMessage = eribMessage;
	}

	public boolean isApplyToERIB()
	{
		return applyToERIB;
	}

	public void setApplyToERIB(boolean applyToERIB)
	{
		this.applyToERIB = applyToERIB;
	}

	/**
	 * Сгенерировать сообщение об ограничение по правилу:
	 * «<причина блокировки>. Предоставление услуги будет возобновлено ориентировочно в <дата и время восстановления доступа> (время московское). Приносим извинения за предоставленные неудобства.»
	 *
	 * (дата отображается, только если время возобновления доступа приходится на другой календарный день).
	 * Если к указанному моменту доступ не будет открыт, то время восстановления доступа должно автоматически пролонгироваться на 20 минут.
	 * @return сообщение
	 */
	private String generateMessage(String message)
	{
		return String.format(RESTRICTION_MESSAGE, message, getFormatDate());
	}

	private String getFormatDate()
	{
		Calendar expiredDate = (Calendar) expired.clone();
		Calendar currentDate = Calendar.getInstance();
		//Получаем разницу между текущи временем и восстановлением доступа в мс.
		long diff = DateHelper.diff(currentDate, expiredDate);
		if (diff > 0)
		{
			//Доступ не восстановили к назначенному времени. автоматически пролонгируем.
			int intervalInMilliseconds = AUTO_PROLONGATION_INTERVAL * DateHelper.MILLISECONDS_IN_MINUTE;
			int prolongationCount = (int) (diff + intervalInMilliseconds - 1) / intervalInMilliseconds;// получаем сколько полных интервалов прошло с округлением в большую сторону
			expiredDate.add(Calendar.MINUTE, prolongationCount * AUTO_PROLONGATION_INTERVAL); //добавляем эти интервалы к ожидаемому времени восстановления.
		}
		String dateFormat = (DateHelper.daysDiff(currentDate, expiredDate) == 0) ? TODAY_DATE_EXPIRE_FORMAT : COMMON_DATE_EXPIRE_FORMAT;
		return String.format(dateFormat, expiredDate);
	}

	/**
	 * @return сгенерированное сообщение для ЕРИБ
	 */
	public String generateEribMessage()
	{
		return generateMessage(eribMessage);
	}

	/**
	 * @return сгенерированное сообщение для ЕРМБ
	 */
	public String generateErmbMessage()
	{
		return generateMessage(StringHelper.isEmpty(ermbMessage) ? eribMessage : ermbMessage);
	}

	/**
	 * @return сгенерированное сообщение для мАпи
	 */
	public String generateMapiMessage()
	{
		return StringHelper.isEmpty(getMapiMessage()) ? getEribMessage() : getMapiMessage();
	}

	/**
	 * @return дата окончания блокировки
	 */
	public String getDate()
	{
		return getFormatDate();
	}

	/**
	 * @return сгенерированное сообщение для АТМ
	 */
	public String generateAtmMessage()
	{
		return generateMessage(StringHelper.isEmpty(atmMessage) ? eribMessage : atmMessage);
	}

	/**
	 * Cоответсвует ли переданный cbCode ограничению
	 * @param cbCode код подразделения
	 * @return да/нет
	 */
	public boolean matches(String cbCode)
	{
		if (cbCode == null)
		{
			throw new IllegalArgumentException("Параметр не может быть null");
		}
		Pattern pattern = PatternUtils.compileDepartmentsPatten(codes);
		return pattern.matcher(cbCode).matches();
	}

	/**
	 * @return Действут глобальная блокировка
	 * @throws Exception
	 */
	public static boolean isGlobalBlocking() throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				return Boolean.parseBoolean((String) session.getNamedQuery(ERIBBlockingRule.class.getName() + ".isGlobalBlocking").uniqueResult());
			}
		});
	}

	/**
	 * @return все (активные) ограничения
	 */
	public List<ERIBBlockingRule> findAll() throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<ERIBBlockingRule>>()
		{
			public List<ERIBBlockingRule> run(Session session) throws Exception
			{
				return (List<ERIBBlockingRule>) session.getNamedQuery(ERIBBlockingRule.this.getClass().getName() + ".findAllActive").list();
			}
		});
	}

	/**
	 * @return все (активные) ограничения из кеша
	 */
	public List<ERIBBlockingRule> findAllInCach() throws Exception
	{
		String localedKey = ERIBBlockingRule.this.getClass().getName()+DELIMITER + MultiLocaleContext.getLocaleId();
		Element element = CACHE.get(localedKey);
		if (element != null)
		{
			//noinspection unchecked
			return (List<ERIBBlockingRule>) element.getObjectValue();
		}

		List<ERIBBlockingRule> blockingRule = findAll();
		CACHE.put(new Element(localedKey, blockingRule));
		return blockingRule;
	}

	/**
	 * Найти соответсвующее cbCode правило активное блокировки
	 * @param cbCode код подразделения
	 * @return найденное, активное правило или null, если ограничения нет
	 */
	public ERIBBlockingRule findByCbCode(String cbCode) throws Exception
	{
		List<ERIBBlockingRule> rules = findAllInCach();
		for (ERIBBlockingRule rule : rules)
		{
			if (rule.matches(cbCode))
			{
				return rule;
			}
		}
		return null;
	}
}
