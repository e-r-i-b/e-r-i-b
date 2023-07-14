package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.MultiInstanceDepartmentService;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.persons.dbserialize.CopyPersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:52:24
 */
public abstract class PersonOperationBase extends OperationBase<UserRestriction> implements PersonOperation
{
    protected static final MultiInstancePersonService personService = new MultiInstancePersonService();
	protected static final MultiInstanceDepartmentService MultiInstanceDepartmentService = new MultiInstanceDepartmentService();
	protected static final DepartmentService departmentService = new DepartmentService();
	protected static final GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);

    //TODO: personId - часть person
	private Long         personId;
    protected ActivePerson person;
	private Department personDepartment;
	PersonOperationMode mode = null;

    public Long getPersonId()
    {
        return personId;
    }

	protected void initializeViaPersonId(Long personId) throws BusinessException, BusinessLogicException
	{
		Person temp = personService.findById(personId, null);
		if (temp == null)
			throw new BusinessException("Клиент с заданным id не найден");

		this.person = (ActivePerson) temp;
	}

	/**
	 * В каком режиме сечас находится клиент
	 * todo static - временно, до доделки визарда, иначе пришлось бы дублировать
	 * @param personId
	 * @return
	 */
	public static PersonOperationMode getPersonMode(Long personId) throws BusinessException
	{
		return personService.getPersonMode(personId);
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
		if(Person.ACTIVE.equals( person.getStatus()))
		{
			if(!CopyPersonHelper.isCopyExists(person, MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME) )
			{
				CopyPersonHelper.copy(person,null,MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
			}
			mode = PersonOperationMode.shadow;

		}
		else mode = PersonOperationMode.direct;
	}

	/**
	 * скопировать изменение в основную копию и перевести клиента на работу в режиме оригинала
	 * todo по-хорошему, надо делать перехватчик операции на основе аннотаций,прозрачный для программиста, но решено делать временное решение
	 * @throws BusinessException
	 */
	public void switchToDirect() throws BusinessException
	{
		if(!ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())
				return;

		if(Person.ACTIVE.equals( person.getStatus()))
		{
			if(CopyPersonHelper.isCopyExists(person, MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME))
			{
				CopyPersonHelper.copy(person,MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME, null);
				CopyPersonHelper.deleteCopy(MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME, person);
			}
			mode = PersonOperationMode.direct;
		}
		else mode = PersonOperationMode.direct;
	}

	public void setPersonId(Long value) throws BusinessException, BusinessLogicException
	{
		this.person   = null;
		this.personId = null;

		if (value == null)
			return;

		ActivePerson temp = null;
		//если найдена копия, то работаем с ней
		if(ConfigFactory.getConfig(PersonCreateConfig.class).getRegisterChangesEnable())
		{
			temp = (ActivePerson) personService.findById(value,MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);

			if (temp == null)
			{
				temp = (ActivePerson) personService.findById(value,null);
				mode = PersonOperationMode.direct;
			}
			else
			{
				mode = PersonOperationMode.shadow;
			}
		}
		else
		{
			temp = (ActivePerson) personService.findById(value,null);
			mode = PersonOperationMode.direct;
		}

		if(temp == null)
			throw new BusinessException("Клиент id="+value+" не найден среди активных пользователей.");
		
		checkRestriction((UserRestriction) getRestriction(), temp);

		this.person   = temp;
		this.personId = value;

		if (temp != null)
		{
			personDepartment = MultiInstanceDepartmentService.findById(temp.getDepartmentId(),getInstanceName());
		}
	}

	/**
	 * режим в котором находится клиент в данный момент
	 * @return
	 */
	public PersonOperationMode getMode()
	{
		return mode;
	}

	public void setMode(PersonOperationMode mode)
	{
		this.mode = mode;
	}

	/**
	 * Получить имя экземпляра БД, из которого будут получены данные
	 * @return имя экземпляра
	 */
	public String getInstanceName()
	{
		if( (mode ==null) || PersonOperationMode.direct.equals(mode))
			return null;
		else
			return MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME;
	}

	/**
	 * Прверка ограничений
	 * @param restriction ограничение
	 * @param person пользователь
	 * @throws RestrictionViolationException если пользователь не проходит ограничения
	 */
	public static void checkRestriction(UserRestriction restriction,  ActivePerson person) throws RestrictionViolationException, BusinessException
	{
		if(!restriction.accept(person))
			throw new RestrictionViolationException(" Пользователь ID= " + person.getDepartmentId());
	}

	public ActivePerson getPerson()
    {
        return person;
    }

    protected void setPerson(ActivePerson person)
    {
        this.person = person;
    }

	public Department getPersonDepartment()
	{
		return personDepartment;
	}

	//Чтение настроек, конкретного гейта
	protected Boolean getNeedAgrementCancellation() throws BusinessException, BusinessLogicException
	{
		Department department = departmentService.findById(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId());
		try
		{
			return gateInfoService.isNeedChargeOff(department);
		}
		catch(GateException ex)
		{
			throw new BusinessException(ex);
		}
		catch(GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	/**
	 * @return список всех типов документов клиента
	 */
	public List<String> getDocumentTypes()
	{
		return PersonHelper.getDocumentStringTypes();
	}
}
