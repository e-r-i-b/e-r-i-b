package com.rssl.phizic.business.dictionaries.pfp.channel;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * ������ ��� ������ � ��������
 * @author komarov
 * @ created 09.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChannelService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * ���� �������� �� ��������������
	 * @param id �������������
	 * @param instance ��� �������� ������ ��
	 * @return �����
	 * @throws BusinessException
	 */
	public Channel getChannelById(Long id, String instance) throws BusinessException
	{
		return service.findById(Channel.class, id, instance);
	}

	/**
	 * ���������/��������� �����
	 * @param channel �����
	 * @param instance ��� �������� ������ ��
	 * @return ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Channel addOrUpdate(final Channel channel, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(channel, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("����� � ����� ��������� ��� ����������.", e);
		}
	}

	/**
	 * ������� �����
	 * @param channel �����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final Channel channel, final String instance)  throws BusinessException, BusinessLogicException
	{
		channel.setDeleted(true);
		service.update(channel, instance);
	}

	/**
	 * ���������� ��� ������
	 * @return ������ �������
	 * @throws BusinessException
	 */
	public List<Channel> getChannels() throws BusinessException
	{
		return service.getAll(Channel.class);
	}

	/**
	 * ���������� ���������� ������
	 * @return ������ �������
	 * @throws BusinessException
	 */
	public List<Channel> getActiveChannels() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Channel.class);
		criteria.add(Expression.eq("deleted", false));
		return service.find(criteria);
	}

	/**
	 * ����� ������ �� ��������
	 * @param name �������� ������
	 * @return �����
	 * @throws BusinessException
	 */
	public Channel getByName(String name) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Channel.class);
		criteria.add(Expression.eq("name", name));
		criteria.add(Expression.eq("deleted", false));
		return service.findSingle(criteria);
	}
}
