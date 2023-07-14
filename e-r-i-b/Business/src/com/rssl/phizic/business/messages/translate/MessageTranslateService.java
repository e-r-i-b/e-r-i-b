package com.rssl.phizic.business.messages.translate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.logging.translateMessages.LogType;
import com.rssl.phizic.logging.translateMessages.MessageTranslate;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * C�������� ��������.
	 * ����� ����������� ��������, ���� �� ���  � ���� ������ � ������ �����
	 * � ���� ����, �� ������� ������ ������ � ����� �����
	 */
	public void save(final MessageTranslate obj, String instanceName) throws BusinessException
	{
        DetachedCriteria criteria = DetachedCriteria.forClass(MessageTranslate.class);
        criteria.add(org.hibernate.criterion.Expression.eq("code", obj.getCode()));
		MessageTranslate messageTranslate = simpleService.findSingle(criteria, instanceName);
		if (messageTranslate != null)
			simpleService.remove(messageTranslate, instanceName);
		else
			obj.setIsNew(true);

		simpleService.addOrUpdate(obj, instanceName);
	}

	/**
	 *  ����  ��������� ��������� �� ���������� ���� ��� ������ ��������
	 * @param text -  ��������� ������
	 * @param type -  ��� ��������� (������/�����)
	 * @return  - ��������� ��������� ��������
	 * @throws BusinessException
	 */
	public List<MessageTranslate> findByCodeOrTranslate(String text, TypeMessageTranslate type,String instanceName) throws BusinessException
	{
		if (StringHelper.isEmpty(text))
			return null;

		DetachedCriteria criteria = DetachedCriteria.forClass(MessageTranslate.class).add(
			Restrictions.or(
					Restrictions.ilike("code", text, MatchMode.START),
					Restrictions.ilike("translate", text, MatchMode.START)
			)
		).add( Restrictions.eq("type", type) );
		return simpleService.find(criteria, instanceName);
	}

	/**
	 * �������� ������ ��������-��������� ��� "������" ������
	 * @param searchString - ��������� ������, �� ���. ����
	 * @param type -  ��� ��������� (������/�����)
	 * @return ��������� ������ �����
	 * @throws BusinessException
	 */
	public List<String> getCodeOrTranslateList(String searchString, TypeMessageTranslate type, String instanceName) throws BusinessException
	{
		List<MessageTranslate> messageTranslates = findByCodeOrTranslate(searchString, type, instanceName);

		if (messageTranslates == null)
			return null;

		List<String> messages = new ArrayList<String>();
		for (MessageTranslate messageTranslate : messageTranslates)
		{
			// � ������ ������ ������� ��� ������������ ��������, ��� � �� ��������
			if (matchesLowerCaseStrings(messageTranslate.getCode(), searchString))
			{
				messages.add(messageTranslate.getCode());
			}
			else if (matchesLowerCaseStrings(messageTranslate.getTranslate(), searchString))
			{
				messages.add(messageTranslate.getTranslate());
			}
		}
		return messages;
	}

	/**
	 * �������, ���������� �� ��������� ������ � ��������� ��� ����� ��������
	 * @param mainString  - ������, � ������� ����
	 * @param subString   - ���������, ������� ����
	 * @return true - ������ ���������� � ���������, false - ������ �� ���������� � ���������
	 */
	private Boolean matchesLowerCaseStrings(String mainString, String subString)
	{
		if (mainString == null || subString == null)
			return false;

		return mainString.toLowerCase().matches("^" + subString.toLowerCase() + ".+");
	}

	public MessageTranslate findById(Long id, String instanceName) throws BusinessException
	{
		return simpleService.findById(MessageTranslate.class, id, instanceName);
	}


}
