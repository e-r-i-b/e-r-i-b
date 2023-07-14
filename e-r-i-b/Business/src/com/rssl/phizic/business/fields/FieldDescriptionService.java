package com.rssl.phizic.business.fields;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.List;

/**
 * @author osminin
 * @ created 08.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с полями ПУ
 */
public class FieldDescriptionService
{
	/**
	 * Получить краткую информацию о полях постащика услуг
	 * @param recipientId идентификатор поставщика услуг
	 * @return список полей
	 * @throws BusinessException
	 */
	public List<FieldDescriptionShortcut> getByRecipientId(final Long recipientId) throws BusinessException
	{
		if (recipientId == null)
		{
			throw new IllegalArgumentException("Идентификатор поставщика услуг не может быть null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FieldDescriptionShortcut>>()
			{
				public List<FieldDescriptionShortcut> run(Session session) throws Exception
				{
					return (List<FieldDescriptionShortcut>) session.getNamedQuery("com.rssl.phizic.business.fields.FieldDescriptionShortcut.getByRecipientId")
							.setParameter("recipient_id", recipientId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
