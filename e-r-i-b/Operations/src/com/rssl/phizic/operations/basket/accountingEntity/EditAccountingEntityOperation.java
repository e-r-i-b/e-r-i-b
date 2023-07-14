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
 * �������� ��������/�������������� ������� �����
 */
public class EditAccountingEntityOperation extends OperationBase<AccountingEntityRestriction> implements EditEntityOperation<AccountingEntity, AccountingEntityRestriction>
{
	protected static final InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	protected static final AccountingEntityService accountingEntityService = new AccountingEntityService();

	private AccountingEntity entity;

	/**
	 * ������������� ��������
	 * @param entityId ������������� ������� �����
	 * @param type ��� ������� �����
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
				throw new BusinessException("������ ����� � id " + entityId + " �� ������.");
			}
		}
	}

	private void initializeNew(AccountingEntityType type) throws BusinessException
	{
		entity = createEntity(type);
		//������������� ����� �������
		long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		entity.setLoginId(loginId);
		String defaultName = entity.getType().getDefaultName();
		//�������� ��� ������� ����� ������� ������� � ����� ������������ ������� �����
		List<AccountingEntity> entities = accountingEntityService.findLikeNameByLoginAndType(loginId, entity.getType(), defaultName);
		//�������� ����� ������������, ���� ��� ��� �������������
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
		//������������� ������������ �� ���������
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
				throw new BusinessException("����������� ��� ������� �����.");
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
	 * @return ���������� �� �������������� �������� (���� �� ��������, ����������� � ������� ������� �����)?
	 * @throws BusinessException
	 */
	public boolean needUngroupSubscriptions() throws BusinessException
	{
		return entity.getId() != null && invoiceSubscriptionService.needUngroupSubscriptions(entity.getId());
	}
}
