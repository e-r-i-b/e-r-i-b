package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR;
import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntitySubType;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductRate;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Roshka
 * @ created 10.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductService extends MultiInstanceDepositProductService
{
	private static final SimpleService service = new SimpleService();
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	/**
	 * Добавить продукт
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	public DepositProduct add(final DepositProduct product) throws BusinessException,
																   DublicateDepositProductNameException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();

			return trnExecutor.execute(new HibernateAction<DepositProduct>()
			{
				public DepositProduct run(Session session) throws Exception
				{
					session.save(product);
					session.flush();
					return product;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateDepositProductNameException();
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить продукт в БД
	 * @param product - продукт для обновления
	 * @return обновленный продукт
	 * @throws BusinessException
	 */
	public DepositProduct update(final DepositProduct product) throws BusinessException
	{
		return super.update(product,null);
	}

	/**
	 * Удалить депозитный продукт
	 * @param product продукт
	 */
	public void remove(DepositProduct product) throws BusinessException
	{
		super.remove(product,null);
	}

	/**
	 * @return Список всех депозитных продуктов
	 * @throws BusinessException
	 */
	public List<DepositProduct> getAllProducts() throws BusinessException
	{
		return super.getAllProducts(null);
	}

	/**
	 * Список доступных для открытия онлайн депозитных продуктов
	 * @param tb тербанк, для которого ищем доступные вклады
	 * @return список DepositProduct
	 */
	public List<DepositProduct> getOnlineAvailableList(final String tb) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DepositProduct>>()
			{
				public List<DepositProduct> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getOnlineAvailable");
					query.setParameter("terBank", tb);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает депозитный продукт по идентификатору
	 * @param id идентификатор
	 * @return депозитный продукт
	 * @throws BusinessException
	 */
	public DepositProduct findById (final Long id) throws BusinessException
	{
		return service.findById(DepositProduct.class, id);
	}

	/**
	 * Возвращает частичный депозитный продукт
	 * @param productId - номер вида вклада из внешнего справочника
	 * @return частичный депозитный продукт
	 * @throws BusinessException
	 */
	public DepositProductPart findPartByProductId(Long productId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProductPart.class).add(Expression.eq("productId",productId));
		return service.findSingle(criteria);
	}

	/**
	 * Найти деп. продукт по имени
	 * @param name имя продукта
	 * @return депозитный продукт - если найден, null - если нет.
	 * @throws BusinessException
	 */
	public DepositProduct findByName ( final String name ) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class).add(Expression.eq("name",name));
		return service.findSingle(criteria);
	}

	/**
	 * Найти деп. продукт по имени и id департамента
	 * @param name имя продукта, id департамента
	 * @return депозитный продукт - если найден, null - если нет.
	 * @throws BusinessException
	 */
	public DepositProduct findByNameAndDepartmentId ( final String name, final Long departmentId ) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(DepositProduct.class).add(Expression.eq("name", name)).add(Expression.eq("departmentId", departmentId));
		DepositProduct product = service.findSingle(criteria);

		return product;
	}

	/**
	 * Получить общее описание для ДП
	 * @return
	 * @throws BusinessException
	 */
	public DepositGlobal getGlobal() throws BusinessException
	{
		return super.getGlobal(null);
	}

	/**
	 * Получение депозитного продукта по номеру вида
	 * @param productId номер вида депозитного продукта
	 * @return DepositProduct
	 */
	public DepositProduct findByProductId(final Long productId) throws BusinessException
	{
		return super.findByProductId(productId, null);
	}

	/**
	 * Получить упрощенное описание вклада по виду вклада
	 * @param productId вид вклада
	 * @return упрощенное описание вклада
	 */
	public DepositProductShortCut findShortByProductId(final Long productId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<DepositProductShortCut>()
			{
				public DepositProductShortCut run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositProductShortCut.getByProductId");
					query.setParameter("productId", productId);
					return (DepositProductShortCut) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список записей справочника процентных ставок по виду и подвиду вклада
	 * @param type вид вклада
	 * @param subType подвид вклада
	 * @return список записей справочника процентных ставок
	 */
	public List<DepositsDCFTAR> findDepositsDCFTARByTypeAndSubType(final Long type, final Long subType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DepositsDCFTAR>>()
			{
				public List<DepositsDCFTAR> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR.findByTypeAndSubType");
					query.setLong("type", type);
					query.setLong("subType", subType);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить список номеров загруженных в систему видов вкладов.
	 * @return список номеров
	 */
	public List<Long> getAllDepositProductIds() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Long>>()
			{
				public List<Long> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.getAllDepositProductIds");
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Разрешить все виды вкладов для открытия онлайн
	 */
	public void allowAll() throws BusinessException
	{
		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.deposits.DepositProduct.allowAll");
			        query.executeUpdate();
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * Получить список вкладых продуктов по номерам видов вкладов
	 * @param depositTypeList - список типов вкладов
	 * @return - список вкладных продуктов
	 * @throws BusinessException
	 */
	public List<DepositProductEntity> getDepositEntities(final List<Long> depositTypeList) throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.getGroups");
			query.setParameterList("depositTypes", depositTypeList);
			query.setParameter("curDate", DateHelper.getCurrentDate());
			return query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить вкладной продукт по типу и коду группы
	 * @param depositType
	 * @param depositGroup
	 * @return вкладной продукт
	 * @throws BusinessException
	 */
	public DepositProductEntity findDepositEntity(final Long depositType, final Long depositGroup) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<DepositProductEntity>()
			{
				public DepositProductEntity run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.getGroup");
					query.setParameter("depositType", depositType);
					query.setParameter("depositGroup", depositGroup);
					query.setParameter("curDate", DateHelper.getCurrentDate());
					return (DepositProductEntity) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить ставки для вкладного продукта (по типу и списку подвидов вкладного продукта)
	 * @param type
	 * @param subTypes
	 * @return
	 * @throws BusinessException
	 */
	public List<DepositProductRate> findDepositEntityRates(final Long type, final List<Long> subTypes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DepositProductRate>>()
			{
				public List<DepositProductRate> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.findDepositEntityRates");
					query.setLong("type", type);
					query.setParameterList("subTypes", subTypes);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Boolean getCreditAllowed(final Long type, final List<Long> subTypes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsVALG.getCreditAllowed")
							.setParameter("type", type)
							.setParameterList("subTypes", subTypes)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Boolean getDebitAllowed(final Long type, final List<Long> subTypes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsVALG.getDebitAllowed")
							.setParameter("type", type)
							.setParameterList("subTypes", subTypes)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Boolean getInterestAllowed(final Long type, final List<Long> subTypes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsVALG.getInterestAllowed")
							.setParameter("type", type)
							.setParameterList("subTypes", subTypes)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить название вкладного продукта по названию минимального подвида (только для групп с кодом == 0)
	 * @param type - вид вклада
	 * @return название вкладного продукта
	 * @throws BusinessException
	 */
	public String getDepositEntityName(final Long type) throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.getName");
			query.setParameter("type", type);
			query.setMaxResults(1);
			return query.executeUnique();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти идентификатор шаблона договора по виду+подвиду вклада и типу
	 * @param depositType вид вклада
	 * @param depositSubType подвид вклада
	 * @param templateType тип шаблона
	 * @return идентификатор шаблона
	 */
	public Long findTemplateIdByType(final Long depositType, final Long depositSubType, final Long templateType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsContractTemplate.findTemplateIdByType");
					query.setLong("type", depositType);
					query.setLong("subtype", depositSubType);
					query.setCalendar("currentDate", Calendar.getInstance());
					query.setLong("templateType", templateType);
					//noinspection unchecked
					List<Long> resultList = query.list();

					if (CollectionUtils.isEmpty(resultList))
						return null;

					if (resultList.size() > 1)
					{
						LOG.error("Найдено несколько шаблонов договора для вклада вид: " + depositType + ", подвид: " + depositSubType);
						return null;
					}

					return resultList.get(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти идентификатор шаблона договора по типу и коду шаблона
	 * @param templateType тип шаблона
	 * @param templateCode код габлона
	 * @return идентификатор шаблона
	 */
	public Long findTemplateIdByTypeAndCode(final Long templateType, final String templateCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsContractTemplate.findTemplateIdByTypeAndCode");
					query.setCalendar("currentDate", Calendar.getInstance());
					query.setLong("templateType", templateType);
					query.setString("templateCode", templateCode);
					//noinspection unchecked
					List<Long> resultList = query.list();

					if (CollectionUtils.isEmpty(resultList))
						return null;

					if (resultList.size() > 1)
					{
						LOG.error("Найдено несколько шаблонов договора для вклада. Тип шаблона: " + templateType + ", код шаблона: " + templateCode);
						return null;
					}

					return resultList.get(0);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Найти название группы по коду группы
	 * @param groupCode  код группы
	 * @return название группы
	 */
	public String findDepositNameByGroupCode(final Long groupCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsGROUP.findNameByGroupCode");
					query.setParameter("groupCode", groupCode);
					query.setMaxResults(1);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить текст шаблона договора по идентификатору шаблона
	 * @param templateId идентификатор шаблона
	 * @return текст шаблона
	 */
	public String findTemplateByTemplateId(final Long templateId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsContractTemplate.findTemplateByTemplateId");
					query.setParameter("templateId", templateId);
					query.setMaxResults(1);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить текст шаблона договора по типу и коду шаблона
	 * @param templateType тип шаблона
	 * @param templateCode код шаблона
	 * @return текст шаблона
	 */
	public String findTemplateTextByTypeAndCode(final Long templateType, final String templateCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsContractTemplate.findTemplateTextByTypeAndCode");
					query.setCalendar("currentDate", Calendar.getInstance());
					query.setLong("templateType", templateType);
					query.setString("templateCode", templateCode);
					query.setMaxResults(1);
					return (String) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить детальную информацию по вкладному продукту
	 * @param type - тип вклада
	 * @param groupCode - код группы вклада
	 * @param subType - подвид вклада (обязателен только для 61 вида)
	 * @return поля из таблицы FIELD_TDOG, использующиеся на странице детальной информации
	 * @throws BusinessException
	 */
	public DepositsTDOG getDepositAdditionalInfo(final Long type, final Long groupCode, final Long subType) throws BusinessException
	{
		try
		{
			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG.getDepositAdditionalInfo");
			query.setParameter("type", type);
			query.setParameter("groupCode", groupCode);
			query.setParameter("subType", subType);
			query.setMaxResults(1);
			return query.executeUnique();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить валюту по виду и подвиду вклада
	 * @param type - вид вклада
	 * @param subType - подвид вклада
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getMinSubTypeCurrencies(final Long type, final Long subType) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsVAL.getMinSubTypeCurrencies");
					query.setParameter("type", type);
					query.setParameter("subType", subType);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить значения минимальны доп. взносов и валют для вкладного продукта
	 * @param type - вид вклада
	 * @param groupCode - код группы вклада
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, BigDecimal> getMinAdditionalFeeValues(final Long type, final Long groupCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Map<String, BigDecimal>>()
			{
				public Map<String, BigDecimal> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.getMinAdditionalFeeValues");
					query.setParameter("type", type);
					query.setParameter("groupCode", groupCode);
					List result =  query.list();

					Map<String, BigDecimal> values = new HashMap<String, BigDecimal>();
					if (!result.isEmpty())
					{
						for (Object row : result)
						{
							Object[] objects = (Object[]) row;
							values.put((String)objects[1], (BigDecimal)objects[0]);
						}
					}

					return values;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Определить, доступна ли капитализация для вклада
	 * @param type - вид вклада
	 * @return
	 * @throws BusinessException
	 */
	public Boolean getCapitalization(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB.getCapitalization")
							.setParameter("type", type)
							.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Есть ли у вклада неснижаемый остаток
	 * @param type - вид вклада
	 * @return признак наличия неснижаемого остатка
	 */
	public Boolean isWithMinimumBalance(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Integer minimumBalance = (Integer) session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVB.withMinimumBalance")
												.setParameter("type", type)
												.uniqueResult();
					return minimumBalance!= null && minimumBalance == 1;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение доп. информации о каждом подвиде вкладного продукта (для формы открытия вклада)
	 * @param type - тип вклада
	 * @param groupCode - код группы
	 * @return доп. информация о каждом подвиде вкладного продукта
	 * @throws BusinessException
	 */
	public List<DepositProductEntitySubType> getEntitySubTypesInfo(final Long type, final Long groupCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<DepositProductEntitySubType>>()
			{
				public List<DepositProductEntitySubType> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity.getEntitySubTypesInfo");
					query.setLong("type", type);
					query.setLong("groupCode", groupCode);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получение записей из таблицы зависимости вкладов от ТП
	 * @param type - вид вклада
	 * @return Map <подвид вклада, List<код тарифного плана>>
	 * @throws BusinessException
	 */
	public Map<Long, List<Long>> getTariffDependence(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Map<Long, List<Long>>>()
			{
				public Map<Long, List<Long>> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsQVBTP.getTariffDependence");
					query.setParameter("type", type);
					query.setParameter("current_date", DateHelper.getCurrentDate());
					List result =  query.list();

					Map<Long, List<Long>> resultMap = new HashMap<Long, List<Long>>();
					if (!result.isEmpty())
					{
						for (Object row : result)
						{
							Object[] objects = (Object[]) row;
							Long subType = (Long)objects[0];

							if (!resultMap.containsKey(subType))
								resultMap.put(subType, new ArrayList<Long>());
							resultMap.get(subType).add((Long)objects[1]);
						}
					}

					return resultMap;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить все валюты, доступные для вклада
	 * @param type - вид вклада
	 * @return список цифровых кодов валют
	 * @throws BusinessException
	 */
	public List<String> getDepositCurrencies(final Long type) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsVAL.getDepositCurrencies");
					query.setParameter("type", type);
					//noinspection unchecked
					return (List<String>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверяет, есть ли для открываемого вклада льготные ставки для ТП клиента
	 * @param type вид вклада
	 * @param subType подвид вклада
	 * @param tariffCode - ТП
	 * @return true, если найдены льготные ставки
	 */
	public boolean hasDepositTariffPlan(final Long type, final Long subType, final String tariffCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ext.sbrf.deposits.DepositsDCFTAR.hasDepositTariffPlan");
					query.setParameter("type", type);
					query.setParameter("subType", subType);
					query.setParameter("tariffCode", tariffCode);
					//noinspection unchecked
					return query.uniqueResult() != null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}