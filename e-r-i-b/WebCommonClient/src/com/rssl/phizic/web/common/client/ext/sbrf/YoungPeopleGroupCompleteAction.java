package com.rssl.phizic.web.common.client.ext.sbrf;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.util.SkinHelper;
import static com.rssl.phizic.web.util.SkinHelper.YOUNG_PEOPLE_GROUP;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 08.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Автоматическая привязка клиента к молодёжной группе
 * Экшен должен вызываться после установки PersonContext и определения продуктов клиента
 */
public class YoungPeopleGroupCompleteAction implements AthenticationCompleteAction
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Кол-во секунд в году
	 */
	private static final long MILLIS_IN_YEAR = 1000L*3600*24*365;

	/**
	 * Диапазон возрастов, подходящий для группы "Молодёжь": максимальное значение (лет)
	 */
	private static final int YOUNG_AGE_MAX = 30;

	private static final GroupService groupService = new GroupService();

	///////////////////////////////////////////////////////////////////////////

	public void execute(AuthenticationContext context) throws SecurityException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Person person = personData.getPerson();
		Login personLogin = person.getLogin();

		try
		{
			// 1. Проверяем наличие группы "Молодёжь"
			Group youngPeopleGroup = groupService.getGroupBySystemName(YOUNG_PEOPLE_GROUP);
			if (youngPeopleGroup == null) {
				log.warn("В системе отсутствует группа пользователей \"Молодёжь\" " +
						"(системное имя " + YOUNG_PEOPLE_GROUP + "). " +
						"Процедура автоматической привязки клиента к группе \"Молодёжь\" отключена.");
				return;
			}

			// 2. Пользователь в группе "Молодёжь" => ничего не делаем
			if (groupService.checkGroupContainsElement(youngPeopleGroup, personLogin))
				return;

			// 3. Пользователь "молодой"
			// => Привязываем к группе "Молодёжь"
			if (isPersonYoung(person))
			{
				String lastSkinUrl = SkinHelper.getSkinUrl();

				addPersonToYongPeopleGroup(context, youngPeopleGroup, person);

				if (youngPeopleGroup.getSkin() == null)
				{
					log.warn("Группе \"Молодежь\" (системное имя: " + YOUNG_PEOPLE_GROUP + ") не присвоен скин!");
					return;
				}
				String newSkinUrl = youngPeopleGroup.getSkin().getUrl();
				SkinHelper.setSkinUrl(newSkinUrl);
				// Определяем, нужно ли посылать сообщение об смене текущего скина
				if (!newSkinUrl.equals(lastSkinUrl))
					context.putMessage(SkinHelper.buildSkinChangedMessage());
			}
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}

	private boolean isPersonYoung(Person person)
	{
		Calendar now = Calendar.getInstance();
		Calendar birthday = person.getBirthDay();
		Long ageInMillis = DateHelper.diff(now, birthday);
		Long age = ageInMillis / MILLIS_IN_YEAR;
		return age <= YOUNG_AGE_MAX;
	}

	private void addPersonToYongPeopleGroup(AuthenticationContext context, Group group, Person person) throws BusinessException
	{
		groupService.addElementToGroup(group, person.getLogin());
		log.info("Привязали клиента к молодёжной группе. " +
				"LOGIN_ID=" + person.getLogin().getId());
	}
}
