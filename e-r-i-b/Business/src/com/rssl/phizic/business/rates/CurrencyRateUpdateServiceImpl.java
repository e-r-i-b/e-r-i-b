package com.rssl.phizic.business.rates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.DynamicExchangeRate;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.currency.CurrencyRateUpdateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author gulov
 * @ created 21.09.2010
 * @ $Authors$
 * @ $Revision$
 */
public class CurrencyRateUpdateServiceImpl extends AbstractService implements CurrencyRateUpdateService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final BDCurrencyRateService bdCurrencyService = new BDCurrencyRateService();
	private static final DepartmentService departmentService = new DepartmentService();
	public CurrencyRateUpdateServiceImpl(GateFactory factory)
	{
		super(factory);
	}
	
	public void updateRate(final List<Pair<CurrencyRate, BigDecimal>> rates, final Office office, final String orderNum,
		final Calendar orderDate,  final Calendar startDate) throws GateException
	{
		try
		{
			// Коммит один на все курсы
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Department department = (office == null) ? null : departmentService.findByCode(office.getCode());

					List<Long> ids = new LinkedList<Long>();
					List<Rate> rateList = new LinkedList<Rate>();
					for (Pair<CurrencyRate, BigDecimal> rate: rates)
					{
						Rate rt = new Rate(rate.getFirst());
						rt.setDepartment(department);
						rt.setOrderDate(orderDate);
						rt.setOrderNumber(orderNum);
						rt.setCreationDate(Calendar.getInstance());
						rt.setEffDate(startDate);
						rt.setDynamicValue(rate.getSecond());
						rt.setTarifPlanCodeType(rate.getFirst().getTarifPlanCodeType());
						//Прежний курс
						Rate priorRate = bdCurrencyService.getRate(Calendar.getInstance(), rt.getFromCurrency(), rt.getToCurrency(),
								office, rt.getCurrencyRateType(), rt.getTarifPlanCodeType());
						//При обновлении/добавлении курса рассчитываем динамику изменения курса
						DynamicExchangeRate dynamicExchangeRate = CurrencyRateHelper.getDynamicExchangeRate(priorRate, rt);
						rt.setDynamicExchangeRate(dynamicExchangeRate);

						rateList.add(rt);

						if (priorRate != null && priorRate.getId() != null)
							ids.add(priorRate.getId());
					}

					bdCurrencyService.updateOldRates(startDate, ids);

					for (Rate rate : rateList)
					{
						bdCurrencyService.addOrUpdate(rate);
						session.flush();
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка при внесении нового курса в БД", e);
			throw new GateException(e);
		}
	}

	public boolean rateExistByOrderNumber(Map<String, Object> paramsMap) throws GateException
	{
		try
		{
			return bdCurrencyService.rateExistByOrderNumber(paramsMap);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при определении курсов с номером приказа - " + paramsMap.get("orderNumber"), e);
			throw new GateException(e);
		}
	}
}
