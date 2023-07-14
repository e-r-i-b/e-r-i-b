package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.persons.dbserialize.CopyPersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.person.Person;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

/**
 * Операция назначения/удаления схемы прав доступа и индивидуальных прав пользователя
 * User: Roshka
 * Date: 04.10.2005
 * Time: 12:30:12
 */
//todo отнаследовать от PersonOperationBase
public class AssignPersonAccessOperation extends AssignAccessOperationBase<UserRestriction>
{
	private static final MultiInstancePersonService personService = new MultiInstancePersonService();
	private static final String PERSON_SHADOW_INSTANCE_NAME = "Shadow";
	private ActivePerson person;
	private PersonOperationMode mode = null;

	/**
	 * Инициализация (оптимизирована для единичного изменения прав)
	 * @param loginSource получение логина
	 * @param accessType тип доступа
	 */
	public void initialize(PersonLoginSource loginSource, AccessType accessType) throws BusinessException
	{
		this.initialize(loginSource, accessType, CategoryAssignAccessHelper.createClient());
	}

	/**
	 * Инициализация (оптимизирована для массового изменения прав)
	 * @param loginSource получение логина
	 * @param accessHelper получение настроек (чтоб не считывать для каждого список услуг и прочее)
	 * @param accessType тип доступа
	 */
	public void initialize(PersonLoginSource loginSource, AccessType accessType, AssignAccessHelper accessHelper) throws BusinessException
	{
		ActivePerson temp = loginSource.getPerson();
		PersonOperationBase.checkRestriction(getRestriction(), temp);

		//если найдена копия, то работаем с ней
		if(ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())
		{
			ActivePerson temp1 = (ActivePerson) personService.findById(temp.getId(),PERSON_SHADOW_INSTANCE_NAME);

			if (temp1 == null)
			{
				mode = PersonOperationMode.direct;
			}
			else
			{
				mode = PersonOperationMode.shadow;
				temp = temp1;
			}
		}
		else
		{
			mode = PersonOperationMode.direct;
		}

		super.initialize(loginSource, accessType, Arrays.asList(accessHelper));

		person = temp;
	}

	/**
	 * Получить имя экземпляра БД, из которого будут получены данные
	 * @return имя экземпляра
	 */
	protected String getInstanceName()
	{
		if (!ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())

			return null;
		if( (mode ==null) || PersonOperationMode.direct.equals(mode))
			return null;
		else
			return PERSON_SHADOW_INSTANCE_NAME;
	}

	/**
	 * перевести клиента на работу в режиме копиии, предполагается, что инициализация уже была выполнена
	 * todo по хорошему, надо делать перехватчик операции на основе аннотаций,прозрачный для программиста, но решено делать временное решение
	 * @throws BusinessException
	 */
	public void switchToShadow() throws BusinessException
	{
		if(!ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())
				return;
		//сохраняем только активного клиента, в других случаях рассинхронизация не нужна
		if(Person.ACTIVE.equals(person.getStatus()))
		{
			if(!CopyPersonHelper.isCopyExists(person, PERSON_SHADOW_INSTANCE_NAME))
			{
				CopyPersonHelper.copy(person,null,PERSON_SHADOW_INSTANCE_NAME);
			}
			mode = PersonOperationMode.shadow;
		}
		else mode = PersonOperationMode.direct;
	}

	/**
	 * скопировать изменение в основную копию и перевести клиента на работу в режиме оригинала
	 * todo по хорошему, надо делать перехватчик операции на основе анотаций,прозрачный для программиста, но решено делать временное решение
	 * @throws BusinessException
	 */
	public void switchToDirect() throws BusinessException
	{
		if(ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())
			return;

		if(Person.ACTIVE.equals(person.getStatus()))
		{
			if(CopyPersonHelper.isCopyExists(person, PERSON_SHADOW_INSTANCE_NAME))
			{
				CopyPersonHelper.copy(person,PERSON_SHADOW_INSTANCE_NAME, null);
				CopyPersonHelper.deleteCopy(PERSON_SHADOW_INSTANCE_NAME, person);
			}
			mode = PersonOperationMode.direct;
		}
		else mode = PersonOperationMode.direct;
	}

	protected void afterAssign() throws BusinessException, BusinessLogicException
	{
		// скорректировать права представителей
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					List<ActivePerson> empoweredPersons = personService.getEmpoweredPersons(person, getInstanceName());

					for (ActivePerson empoweredPerson : empoweredPersons)
					{
						EmpoweredAccessModifier accessModifier = new EmpoweredAccessModifier(empoweredPerson, person,getAccessType(), getInstanceName());
						accessModifier.change(accessModifier.getCurrentServices(),accessModifier.isCurrentAccessAllowed() ,accessModifier.getProperties() );
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public ActivePerson getPerson()
	{
		return person;
	}
}
