package com.rssl.phizic.ermb.auxiliary;

	import com.rssl.phizic.TestEjbMessage;
import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.UpdateProfilesRq;

import java.text.ParseException;
import java.util.List;
import java.util.Random;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 10.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class UpdateProfileProcessor extends MessageProcessorBase<TestEjbMessage>
{
	private final Random random = new Random();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль
	 */
	public UpdateProfileProcessor(Module module)
	{
		super(module);
	}

	public void doProcessMessage(TestEjbMessage xmlRequest)
	{
		UpdateProfilesRq request = xmlRequest.getUpdateProfilesRq();

		// 1. Парсим XML
		UpdateProfileXmlParser xmlParser = new UpdateProfileXmlParser();

		List<ErmbUpdateProfileInfo> updateProfileInfo = null;

		try
		{
			updateProfileInfo = xmlParser.parse(request);
		}
		catch (ParseException e)
        {
            log.error("Ошибка разбора xml-сообщения с информацией об измененных ЕРМБ профилях", e);
        }
        catch (JAXBException e)
        {
            log.error("Ошибка разбора xml-сообщения с информацией об измененных ЕРМБ профилях", e);
        }

		if (updateProfileInfo == null)
			return;

		// 2. Отсылаем сообщение с подтвержденными профилями
		int i = random.nextInt(10);
		if (2 >= i && i > 0)
			log.error("Не удалось отправить сообщение с подтверждением об изменении профилей");
		else
		{
			ConfirmProfileSender sender = new ConfirmProfileSender();
			sender.send(updateProfileInfo);
		}
	}

	public boolean writeToLog()
	{
		//Для UpdateProfile лог пишем в момент отправки сообщения.(см. ErmbOfficialInfoNotificationSender#sendNotification)
		return false;
	}
}
