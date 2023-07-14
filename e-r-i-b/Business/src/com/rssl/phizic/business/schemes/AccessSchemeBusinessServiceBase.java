package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.schemes.AccessScheme;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 07.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис работы со схемами прав сотрудников
 */

public abstract class AccessSchemeBusinessServiceBase extends AbstractService implements com.rssl.phizic.gate.schemes.AccessSchemeService
{
	protected static final SimpleService service = new SimpleService();
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();

	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которого происходит создание сервиса
	 */
	protected AccessSchemeBusinessServiceBase(GateFactory factory)
	{
		super(factory);
	}

	public List<AccessScheme> getList(String[] categories) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SharedAccessScheme.class);
			criteria.add(Expression.in("category", categories));
			boolean vspEmployee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isVSPEmployee();
			if (vspEmployee)
				criteria.add(Expression.or(Expression.eq("category","C"),Expression.eq("VSPEmployeeScheme", vspEmployee)));
			/*
			Опорный элемент: ACCESSSCHEMES
			Предикаты доступа: -
			Кардинальность: количество прав доступа, удовлетворяющих условиям
			*/
			return service.find(criteria);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public AccessScheme save(AccessScheme schema) throws GateException
	{
		try
		{
			return accessSchemeService.save((com.rssl.phizic.business.schemes.AccessScheme) schema);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void delete(final AccessScheme schema) throws GateException, GateLogicException
	{
		try
		{
			accessSchemeService.remove((com.rssl.phizic.business.schemes.AccessScheme) schema);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

}
