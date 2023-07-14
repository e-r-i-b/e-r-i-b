package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.operations.ermb.person.EditErmbOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileOperationEntity;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.task.PersonTaskBase;

import java.util.Set;

/**
 * Таск для удаления номера телефона из профиля ЕРМБ, с подтверждением клиентом
 */
public class ErmbPhoneDeleteTask extends PersonTaskBase implements ConfirmableTask
{
	private String phoneToDelete;
	private static transient final OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	public void confirmGranted()
	{
		try
		{
			EditErmbOperation operation = operationFactory.create(EditErmbOperation.class);
			operation.initialize();
			ErmbProfileOperationEntity entity = (ErmbProfileOperationEntity) operation.getEntity();
			Set<String> phoneNumbers = entity.getPhonesNumber();
			phoneNumbers.remove(phoneToDelete);
			//Если удаляем главный номер, и он не последний в списке номеров клиента, нужно установить главным другой телефон.
			if (phoneToDelete.equals(entity.getMainPhoneNumber()))
			{
				if (phoneNumbers.size() > 0)
				{
					entity.setMainPhoneNumber(phoneNumbers.iterator().next());
				}
			}
			operation.save();
			getPersonSmsMessanger().sendSms(new MessageBuilder().buildSuccessErmbDeletePhoneMessage(), phoneToDelete);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	@MandatoryParameter
	public void setPhoneToDelete(String phoneToDelete)
	{
		this.phoneToDelete = phoneToDelete;
	}
}
