package com.rssl.phizic.business.ermb.profile.events;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 09.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� �������� ����������� ������ �� ���������� �������� �������
 */
public class ErmbResourseParamsEvent extends ErmbResourseEvent
{
	private Map<Class, List<ErmbProductLink>> oldResourses = new HashMap<Class, List<ErmbProductLink>>();
	private Map<Class, List<ErmbProductLink>> newResourses = new HashMap<Class, List<ErmbProductLink>>();

	public ErmbResourseParamsEvent(Long personId) throws BusinessException
	{
		super(personId);
	}

	public ErmbResourseParamsEvent(ErmbProfileImpl profile)
	{
		super(profile); 
	}

	public void setOldData(List<CardLink> cardLinks, List<AccountLink> accountLinks, List<LoanLink> loanLinks) throws BusinessException
	{
		oldResourses.put(CardLink.class, copyResourse(cardLinks));
		oldResourses.put(AccountLink.class, copyResourse(accountLinks));
		oldResourses.put(LoanLink.class, copyResourse(loanLinks));
	}

	public void setNewData(List<CardLink> cardLinks, List<AccountLink> accountLinks, List<LoanLink> loanLinks) throws BusinessException
	{
		newResourses.put(CardLink.class, copyResourse(cardLinks));
		newResourses.put(AccountLink.class, copyResourse(accountLinks));
		newResourses.put(LoanLink.class, copyResourse(loanLinks));
	}

	/**
	 * ����������� ������ ������ (������������� �����������)
	 * @param list - ������ ������ ��� �����������
	 * @return ������ �����
	 * @throws BusinessException
	 */
	private List<ErmbProductLink> copyResourse(List<? extends ErmbProductLink> list) throws BusinessException
	{
		if (list == null)
			return new ArrayList<ErmbProductLink>();
		BeanUtilsBean utils = new BeanUtilsBean();
		List<ErmbProductLink> linkList = new ArrayList<ErmbProductLink>(list.size());
		for (ErmbProductLink link : list)
		{
			try
			{
				ErmbProductLink copy = (ErmbProductLink) utils.cloneBean(link);
				linkList.add(copy);
			}
			catch (IllegalAccessException e)
			{
				throw new BusinessException(e);
			}
			catch (InstantiationException e)
			{
				throw new BusinessException(e);
			}
			catch (InvocationTargetException e)
			{
				throw new BusinessException(e);
			}
			catch (NoSuchMethodException e)
			{
				throw new BusinessException(e);
			}
		}
		return linkList;
	}


	public Map<Class, List<ErmbProductLink>> getOldResourses()
	{
		return oldResourses;
	}

	public Map<Class, List<ErmbProductLink>> getNewResourses()
	{
		return newResourses;
	}

}
