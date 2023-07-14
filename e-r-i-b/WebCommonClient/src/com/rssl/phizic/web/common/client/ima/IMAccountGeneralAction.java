package com.rssl.phizic.web.common.client.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Balovtsev
 * @created 19.11.2010
 * @ $Author$
 * @ $Revision$
 * 
 * Базовый класс для экшенов металлических счетов.
 * @see IMAccountInfoAction
 */
public abstract class IMAccountGeneralAction extends ViewActionBase
{
	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    protected static final String BACK_ERROR_IMACCOUNT_MESSAGE  = "Операция временно недоступна. Повторите попытку позже.";
    protected static final long MAX_COUNT = 10L;

    protected DateFormat getDateFormat()
	{
		return (DateFormat)dateFormat.clone();
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException
	{
		boolean isFullAbstractCreated = false;
		
		GetIMAccountAbstractOperation operation;
		if (checkAccess("GetIMAccountFullAbstractOperation"))
		{
			operation = createOperation("GetIMAccountFullAbstractOperation");
			isFullAbstractCreated = true;
		}
		else
		{
			operation = createOperation("GetIMAccountShortAbstractOperation");
		}
		operation.initialize(form.getId(), isFullAbstractCreated);
		return operation;
	}

	/**
	 * Перезаписывается по причине того, что необходимо установить на форме тип периода по-умолчанию(на данный момент он
	 * равен месяцу). 
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		frm.setFilter(IMAbstractFilter.IMABSTRACT_FILTER_TYPE_FIELD, getFilter(frm.getFilters()).getAbstractType().toString());
	}

	/**
	 * Возвращает набор, в котором для каждого счёта сформированна выписка.
	 * @param form форма
	 * @param operation операция используя которую получаем выписку
	 * @param source уже обработанные данные
	 * @return карта в которой ключ-счёт, значение-выписка
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected Map<IMAccountLink, IMAccountAbstract> getAbstract(EditFormBase form, GetIMAccountAbstractOperation operation, Map<String, Object> source) throws BusinessException, BusinessLogicException
	{
		IMAbstractFilter filter = null;
		if (source == null || source.isEmpty())
		{
			filter = getFilter( form.getFilters() );
		}
		else
		{
			filter = getFilter( source );
		}

		if(operation.isFullAbstractCreated())
		{
			Calendar begin = filter.getBeginAbstractDate();
			Calendar end   = filter.getEndAbstractDate();

			return createAbstractMap(operation, begin, end);
		}
		else
		{
			return operation.getIMAccountAbstractMap(MAX_COUNT);
		}
	}

	/**
	 *
	 * Должен возвращать фильтр формы
	 * 
	 * @param source данные для фильтра
	 * @return IMAbstractFilter
	 */
	protected abstract IMAbstractFilter getFilter(Map<String, Object> source);
	
	protected Map<IMAccountLink, IMAccountAbstract> createAbstractMap(GetIMAccountAbstractOperation operation, Calendar begin, Calendar end) throws BusinessException, BusinessLogicException
	{
		Map<IMAccountLink, IMAccountAbstract> abstractMap = new HashMap<IMAccountLink, IMAccountAbstract>();
		abstractMap.put(operation.getEntity(), operation.getAbstract(begin, end));
		return abstractMap;
	}
}
