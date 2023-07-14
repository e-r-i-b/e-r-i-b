package com.rssl.phizic.operations.basket.accountingEntity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.*;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.operations.restrictions.AccountingEntityRestriction;
import com.rssl.phizic.common.types.basket.AccountingEntityType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.counter.CounterUtils;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;
import com.rssl.phizic.utils.counter.NameCounterAction;

import java.util.List;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * операция создания/редактирования объекта учета
 */
public class EditAccountingEntityOperation extends OperationBase<AccountingEntityRestriction> implements EditEntityOperation<AccountingEntity, AccountingEntityRestriction>
{
	protected static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	protected static final AccountingEntityService accountingEntityService = new AccountingEntityService();

	private AccountingEntity entity;

	/**
	 * Инициализация операции
	 * @param entityId идентификатор объекта учета
	 * @param type тип объекта учета
	 */
	public void initialize(Long entityId, AccountingEntityType type) throws BusinessException
	{
		if (entityId == null)
		{
			initializeNew(type);
		}
		else
		{
			entity = getById(entityId);

			if (entity == null)
			{
				throw new BusinessException("Объект учета с id " + entityId + " не найден.");
			}
		}
	}

	private void initializeNew(AccountingEntityType type) throws BusinessException
	{
		entity = createEntity(type);
		//устанавливаем логин клиента
		long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		entity.setLoginId(loginId);
		String defaultName = entity.getType().getDefaultName();
		//получаем все объекты учета данного клиента с типом создаваемого объекта учета
		List<AccountingEntity> entities = accountingEntityService.findLikeNameByLoginAndType(loginId, entity.getType(), defaultName);
		//получаем номер наименования, если оно уже задействовано
		DefaultNamingStrategy namingStrategy = new DefaultNamingStrategy();
		defaultName = namingStrategy.transform(defaultName);
		String number = CounterUtils.calcNumber(entities, defaultName, new NameCounterAction(namingStrategy)
		{
			@Override
			public String getName(Object o)
			{
				AccountingEntity accountingEntity = (AccountingEntity) o;
				return accountingEntity.getName();
			}
		});
		//устанавливаем наименование по умолчанию
		entity.setName(defaultName + number);
	}

	protected AccountingEntity createEntity(AccountingEntityType type) throws BusinessException
	{
		switch (type)
		{
			case CAR:
				return new CarAccountingEntity();
			case FLAT:
				return new FlatAccountingEntity();
			case HOUSE:
				return new HouseAccountingEntity();
			case GARAGE:
				return new GarageAccountingEntity();
			default:
				throw new BusinessException("Неизвестный тип объекта учета.");
		}
	}

	private AccountingEntity getById(Long id) throws BusinessException
	{
		return accountingEntityService.findById(id);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		accountingEntityService.addOrUpdate(entity);
	}

	public AccountingEntity getEntity() throws BusinessException, BusinessLogicException
	{
		return entity;
	}

	/**
	 * @return Необходимо ли разруппировать подписки (есть ли подписки, привязанные к данному объекту учета)?
	 * @throws BusinessException
	 */
	public boolean needUngroupSubscriptions() throws BusinessException
	{
		return entity.getId() != null && invoiceSubscriptionService.needUngroupSubscriptions(entity.getId());
	}
}
