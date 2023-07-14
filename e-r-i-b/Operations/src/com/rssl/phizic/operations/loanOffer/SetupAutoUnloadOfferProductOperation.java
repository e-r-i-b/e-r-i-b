package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.virtualcards.unload.UnloadVirtualCardClaimOperation;
import com.rssl.phizic.utils.CronExpressionUtils;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gulov
 * @ created 28.06.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Настройка автоматической выгрузки заявок на кредит и кредитные карты
 */
public class SetupAutoUnloadOfferProductOperation extends AutoActionOperationBase
{
	private static final String HOURS = "Hours";

	public void initialize() throws BusinessException
	{
		//получаем шедулеры по именам, и заполняем данные в мап
		//создаем 5 шедулеров на каждый вид заявок
		scheduleMap = new HashMap<String, ScheduleData>(5);
		putExist(UnloadLoanProductOperation.class.getName());
		putExist(UnloadLoanOfferOperation.class.getName());
		putExist(UnloadCardProductOperation.class.getName());
		putExist(UnloadCardOfferOperation.class.getName());
		putExist(UnloadVirtualCardClaimOperation.class.getName());
	}

	public void initializeNew()
	{
		scheduleMap = new HashMap<String, ScheduleData>(5);

		putNew(UnloadLoanProductOperation.class.getName());
		putNew(UnloadLoanOfferOperation.class.getName());
		putNew(UnloadCardProductOperation.class.getName());
		putNew(UnloadCardOfferOperation.class.getName());
		putNew(UnloadVirtualCardClaimOperation.class.getName());
	}

	/**
	 * на основе заполненных инпутов на форме клиентом
	 * формируем соответствующие  Cron выражения и записываем их
	 * @param fields
	 * @throws BusinessException
	 */

	public void setData(Map<String, Object> fields) throws BusinessException, BusinessLogicException
	{
		setOneData(fields, UnloadLoanProductOperation.class.getName(), "loanProduct", "кредиты");
		setOneData(fields, UnloadLoanOfferOperation.class.getName(), "loanOffer", "предодобренные кредиты ");
		setOneData(fields, UnloadCardProductOperation.class.getName(), "loanCardProduct", "кредитные карты");
		setOneData(fields, UnloadCardOfferOperation.class.getName(), "loanCardOffer", "предодобренные кредитные карты");
		setOneData(fields, UnloadVirtualCardClaimOperation.class.getName(), "virtualCard", "виртуальные карты");
	}

	private void setOneData(Map<String, Object> fields, String key, String id, String name) throws BusinessException, BusinessLogicException
	{
		/*если Radio приходит null считаем что задание должно быть задизабленно*/
		String periodType = (String) fields.get(id + "Radio");
		if (!StringHelper.isEmpty(periodType))
		{
			if (periodType.equals(HOURS))
			{
				BigInteger hour = (BigInteger) fields.get(id + "Hour");
				scheduleMap.get(key).setSimple(true);
				scheduleMap.get(key).setHour(hour.toString());
			}
			else
			{  //не часы так дни.
				BigInteger day = (BigInteger) fields.get(id + "Day");
				String[] dayTime = fields.get(id + "DayTime").toString().split(" ");
				String time = dayTime[3];

				String expression = CronExpressionUtils.getDayTimeExp(time, day.toString());
				scheduleMap.get(key).setExpression(expression);
			}
			//каталог выгрузки
			String path = fields.get(id + "Folder").toString();
			if (pathTester(path))
				scheduleMap.get(key).setDir(path);
			else
				throw new BusinessLogicException("Путь к каталогу выгрузки Заявок на " + name + " указан неверно");
			scheduleMap.get(key).setEnabled(true);
		}
		else
			scheduleMap.get(key).setEnabled(false);
	}


	private void putNew(String operationName)
	{
		ScheduleData scheduleData = new ScheduleData();
		scheduleMap.put(operationName, scheduleData);
	}


	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return scheduleMap;
	}
}
