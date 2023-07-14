package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ArchiveClientService;

import java.text.SimpleDateFormat;

/**
 * @author Gulov
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Определитель 5 сегмента.
 * У клиента есть дополнительные карты.
 * Сегмент 5_1: Держатели дополнительных и основных карт, для которых выполняются следующие условия:
 * - в МБК имеется регистрация дополнительной карты (при этом дополнительная карта является единственной информационной картой в регистрации) ;
 * - основной клиент имеет признак ВИП/МВС
 * Сегмент 5_2: Держатели дополнительных и основных карт, для которых выполняются следующие условия:
 * - в МБК имеется регистрация дополнительной карты (при этом дополнительная карта является единственной информационной картой в регистрации);
 * - основной клиент не ВИП/МВС;
 * - основной клиент имеет в МБК/МБВ другие регистрации;
 * - дополнительный держатель имеет собственные регистрации в МБК/МБВ (на другой телефон)
 * Сегмент 5_3: Держатели дополнительных и основных карт, для которых выполняются следующие условия:
 * - в МБК имеется регистрация дополнительной карты (при этом дополнительная карта является единственной информационной картой в регистрации);
 * - основной клиент не ВИП/МВС;
 * - основной клиент имеет в МБК/МБВ другие регистрации;
 * - дополнительный держатель не имеет собственных регистрации в МБК/МБВ (на другой телефон)
 * Сегмент 5_4: Держатели дополнительных и основных карт, для которых выполняются следующие условия:
 * - в МБК имеется регистрация дополнительной карты (при этом дополнительная карта является единственной информационной картой в регистрации);
 * - основной клиент не ВИП/МВС;
 * - основной клиент не имеет в МБК/МБВ других регистрации;
 * Сегмент 5_5: Держатели основных карт, для которых выполняются следующие условия:
 * - в МБК имеется регистрация дополнительной карты (при этом дополнительная карта не является единственной
 * информационной картой в регистрации -  в регистрации есть другие основные продукты основного держателя);
 */
public class SegmentResolver5 implements Resolver
{
	private static final ArchiveClientService archiveService = new ArchiveClientService();

	public boolean evaluate(final Client client) throws BusinessException
	{
		if (!(!client.getPhones().isEmpty() && client.isAdditionalCards()))
			return false;
		// если клиент - султан (много доп карт на разных людей), то сегмент не заполняем, обрабатывается потом отдельно
		if (isSultan(client))
			return true;
		boolean isVip = isVipBaseClient(client);
		boolean hasSingleAdditionalCard = hasSingleAdditionalCard(client);
		if (!hasSingleAdditionalCard)
		{
			client.setSegment_5_5(true);
			return true;
		}
		client.setSegment_5_1(hasSingleAdditionalCard && isVip);
		if (client.getSegment_5_1())
			return true;
		boolean hasBaseClientRegistrations = hasBaseClientRegistrations(client);
		boolean hasAdditionalClientRegistration = hasAdditionalClientRegistration(client);
		client.setSegment_5_2(hasSingleAdditionalCard && !isVip && hasBaseClientRegistrations && hasAdditionalClientRegistration);
		if (client.getSegment_5_2())
			return true;
		client.setSegment_5_3(hasSingleAdditionalCard && !isVip && hasBaseClientRegistrations && !hasAdditionalClientRegistration);
		if (client.getSegment_5_3())
			return true;
		client.setSegment_5_4(hasSingleAdditionalCard && !isVip && !hasBaseClientRegistrations);
		if (client.getSegment_5_4())
			return true;
		return true;
	}

	private boolean isSultan(Client client)
	{
		for (Phone phone : client.getPhones())
			if (phone.isSultan())
				return true;
		return false;
	}

	/**
	 * В МБК есть регистрация дополнительной карты и она одна
	 * @param client клиент
	 * @return true - есть, false - нет
	 */
	private boolean hasSingleAdditionalCard(Client client)
	{
		for (Phone phone : client.getPhones())
			if (!phone.isOnlyAdditional())
				return false;
		return true;
	}

	/**
	 * Владелец дополнительной карты является VIP/MBC
	 * @param client клиент
	 * @return true - да, false - нет
	 */
	private boolean isVipBaseClient(Client client) throws BusinessException
	{
		for (Phone phone : client.getPhones())
			if (phone.getVipOrMbc())
				return true;
		return false;
	}

	/**
	 * Основной клиент имеет другие регистрации в МБК/МБВ
	 * @param client клиент
	 * @return true - да, false - нет
	 */
	private boolean hasBaseClientRegistrations(Client client) throws BusinessException
	{
		for (Phone phone : client.getPhones())
			if (archiveService.hasRegistrationByOtherPhone(client.getLastName(),
					client.getFirstName(), client.getMiddleName(), client.getDocument(),
					new SimpleDateFormat("dd.MM.yyyy").format(client.getBirthday().getTime()), phone.getPhoneNumber()))
				return true;
		return false;
	}

	/**
	 * Дополнительный держатель имеет собственные регистрации в МБК/МБВ на другие телефоны
	 * @param client клиент
	 * @return true - да, false - нет
	 */
	private boolean hasAdditionalClientRegistration(Client client) throws BusinessException
	{
		for (String[] strings : client.getAdditionalRegistration())
			if (archiveService.hasRegistrationByOtherPhone(strings[1], strings[2], strings[3], strings[4], strings[5], strings[0]))
				return true;
		return false;
	}
}
