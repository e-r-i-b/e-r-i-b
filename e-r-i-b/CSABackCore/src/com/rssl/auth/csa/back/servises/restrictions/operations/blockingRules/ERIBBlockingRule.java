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
 * �������� ����������� �� ����
 */
public class ERIBBlockingRule extends ActiveRecord
{
	public static final String GLOBAL_BLOCKING_MESSAGE = "�� ����������� �������� ���� � ������� �������� ������������. ����������, ��������� ������� �����.";

	private static final int AUTO_PROLONGATION_INTERVAL = 20; //�������� ��������������� � ���
	private static final String RESTRICTION_MESSAGE = "%s. �������������� ������ ����� ������������ �������������� %s (����� ����������). �������� ��������� �� ��������������� ����������.";
	private static final String TODAY_DATE_EXPIRE_FORMAT = "%1$tH:%1$tM";
	private static final String COMMON_DATE_EXPIRE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM";

	private Long id;
	private String codes;           //������������� ����� ������� ����� �����
	private String eribMessage;     //��������� ���� ������������ ������������ � ������ ���� ���������� ������� (������� ����������)
	private Calendar expired;       //��������������� ����� �������������� �������
	private boolean applyToERIB;    //���������������� �� ����
	private boolean applyToMAPI;    //���������������� �� ����
	private String mapiMessage;     //��������� ���� ������������ ������������ � ������ ���� ���������� ������� (������� ����������)
	private boolean applyToERMB;    //���������������� �� ����
	private String ermbMessage;     //��������� ���� ������������ ������������ � ������ ���� ���������� ������� (������� ����������)
	private boolean applyToATM;     //���������������� �� ���
	private String atmMessage;      //��������� ��� ������������ ������������ � ������ ���� ���������� ������� (������� ����������)
	private static final Cache CACHE = CacheProvider.getCache("blocking-rule-cache");      //��� ����������
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
	 * ������������� ��������� �� ����������� �� �������:
	 * �<������� ����������>. �������������� ������ ����� ������������ �������������� � <���� � ����� �������������� �������> (����� ����������). �������� ��������� �� ��������������� ����������.�
	 *
	 * (���� ������������, ������ ���� ����� ������������� ������� ���������� �� ������ ����������� ����).
	 * ���� � ���������� ������� ������ �� ����� ������, �� ����� �������������� ������� ������ ������������� ���������������� �� 20 �����.
	 * @return ���������
	 */
	private String generateMessage(String message)
	{
		return String.format(RESTRICTION_MESSAGE, message, getFormatDate());
	}

	private String getFormatDate()
	{
		Calendar expiredDate = (Calendar) expired.clone();
		Calendar currentDate = Calendar.getInstance();
		//�������� ������� ����� ������ �������� � ��������������� ������� � ��.
		long diff = DateHelper.diff(currentDate, expiredDate);
		if (diff > 0)
		{
			//������ �� ������������ � ������������ �������. ������������� ������������.
			int intervalInMilliseconds = AUTO_PROLONGATION_INTERVAL * DateHelper.MILLISECONDS_IN_MINUTE;
			int prolongationCount = (int) (diff + intervalInMilliseconds - 1) / intervalInMilliseconds;// �������� ������� ������ ���������� ������ � ����������� � ������� �������
			expiredDate.add(Calendar.MINUTE, prolongationCount * AUTO_PROLONGATION_INTERVAL); //��������� ��� ��������� � ���������� ������� ��������������.
		}
		String dateFormat = (DateHelper.daysDiff(currentDate, expiredDate) == 0) ? TODAY_DATE_EXPIRE_FORMAT : COMMON_DATE_EXPIRE_FORMAT;
		return String.format(dateFormat, expiredDate);
	}

	/**
	 * @return ��������������� ��������� ��� ����
	 */
	public String generateEribMessage()
	{
		return generateMessage(eribMessage);
	}

	/**
	 * @return ��������������� ��������� ��� ����
	 */
	public String generateErmbMessage()
	{
		return generateMessage(StringHelper.isEmpty(ermbMessage) ? eribMessage : ermbMessage);
	}

	/**
	 * @return ��������������� ��������� ��� ����
	 */
	public String generateMapiMessage()
	{
		return StringHelper.isEmpty(getMapiMessage()) ? getEribMessage() : getMapiMessage();
	}

	/**
	 * @return ���� ��������� ����������
	 */
	public String getDate()
	{
		return getFormatDate();
	}

	/**
	 * @return ��������������� ��������� ��� ���
	 */
	public String generateAtmMessage()
	{
		return generateMessage(StringHelper.isEmpty(atmMessage) ? eribMessage : atmMessage);
	}

	/**
	 * C����������� �� ���������� cbCode �����������
	 * @param cbCode ��� �������������
	 * @return ��/���
	 */
	public boolean matches(String cbCode)
	{
		if (cbCode == null)
		{
			throw new IllegalArgumentException("�������� �� ����� ���� null");
		}
		Pattern pattern = PatternUtils.compileDepartmentsPatten(codes);
		return pattern.matcher(cbCode).matches();
	}

	/**
	 * @return �������� ���������� ����������
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
	 * @return ��� (��������) �����������
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
	 * @return ��� (��������) ����������� �� ����
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
	 * ����� �������������� cbCode ������� �������� ����������
	 * @param cbCode ��� �������������
	 * @return ���������, �������� ������� ��� null, ���� ����������� ���
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
