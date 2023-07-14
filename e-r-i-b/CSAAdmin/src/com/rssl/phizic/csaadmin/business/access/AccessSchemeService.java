package com.rssl.phizic.csaadmin.business.access;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы со схемами прав
 */

public class AccessSchemeService extends SimpleService<AccessScheme>
{
	@Override
	protected Class<AccessScheme> getEntityClass()
	{
		return AccessScheme.class;
	}

	/**
	 * @param onlyVSPScheme true -- вытаскиваем только доступные сотрудникам ВСП схемы
	 * @param isCAScheme true -- вытаскиваем только доступные сотрудникам ЦА схемы
	 * @param categories искомые категории
	 * @return список схем прав
	 * @throws AdminException
	 */
	public List<AccessScheme> getList(boolean onlyVSPScheme, boolean isCAScheme, String[] categories) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AccessScheme.class);
		criteria.add(Expression.in("category", categories));
		if (onlyVSPScheme)
			criteria.add(Expression.or(Expression.eq("category","C"),Expression.eq("VSPEmployeeScheme", onlyVSPScheme)));
		if(!isCAScheme)
			criteria.add(Expression.or(Expression.eq("category","C"),Expression.eq("CAAdminScheme", isCAScheme)));
		/*
		Опорный элемент: ACCESSSCHEMES
		Предикаты доступа: -
		Кардинальность: количество прав доступа, удовлетворяющих условиям
		*/
		return find(criteria);
	}
}
