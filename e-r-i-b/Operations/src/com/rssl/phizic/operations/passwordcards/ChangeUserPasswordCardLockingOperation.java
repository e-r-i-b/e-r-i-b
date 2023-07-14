package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardImpl;
import com.rssl.phizic.auth.passwordcards.CardPassword;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeContext;

/**
 * @author Roshka
 * @ created 14.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class ChangeUserPasswordCardLockingOperation extends PasswordCardOperationBase
{
	private final static DepartmentService departmentService = new DepartmentService();
	private final static PersonService personService = new PersonService();
	private final static SimpleService simpleService = new SimpleService();
	private String blockReason;
    private String password;
	private String checkPassword;
	private Long personId;

	public String getCheckPassword()
	{
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword)
	{
		this.checkPassword = checkPassword;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public ActivePerson getPerson() throws BusinessException
	{
		if(personId == null)
			throw new BusinessException("�� ���������� ������������");

		return (ActivePerson)personService.findById(personId);
	}

    /**
     * ���������� ������
     * @param password ������
     */
    public void setPassword(String password)
	{
		this.password = password;
	}

    /**
     * @return ������
     */
    public String getPassword()
	{
		return password;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	public String getBlockReason()
	{
		return blockReason;
	}

	/**
	 * ���������� ����� ������
	 * lockReason - ������� ����������
	 * @throws SecurityLogicException ���� ����� ������ "��������"
	 * @throws BusinessException
	 */
	@Transactional
	public void lock() throws BusinessException, SecurityLogicException
	{
		try
		{
			PasswordCardImpl card = (PasswordCardImpl) getCard();
			card.setBlockReason( getBlockReason() );
			card.setBlockType( PasswordCard.BLOCK_MANUAL);
			passwordCardService.lock( card );
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������� ����� ������
	 * @throws SecurityLogicException  ���� ����� ������ "���������"
	 * @throws UnlockManualPasswordCardException ���� ������������ �����, ��������������� � ������
	 * @throws BusinessException
	 * @throws InvalidPaswordCardValueException ���� �������� ������ �� �����
	 */
	@Transactional
	public void unlock() throws SecurityLogicException, UnlockManualPasswordCardException,
								BusinessException,InvalidPaswordCardValueException
	{
		unlockCheck();

		if(getPassword()==null)
				throw new BusinessException("�� ���������� ������ ��� �������������");

		try
		{			
			PasswordCardImpl card = (PasswordCardImpl) getCard();

			//��������� ������
			CardPassword cardPassword = passwordCardService.getCardPasswordByNumber(Integer.decode(checkPassword),card);

			CryptoService cryptoService = SecurityFactory.cryptoService();
			if(!cardPassword.getStringValue().equals(cryptoService.hash(password)))
			{
				 throw new InvalidPaswordCardValueException();
			}

			//�������� ������, ��� ��������������
			cardPassword.setUsed();
			simpleService.update(cardPassword);

			if (card.getValidPasswordsCount() ==0)
			{
				passwordCardService.markAsExhausted(card);
			}

			//������������ ������, �� � ������� �������������
			if(card.getBlockType().equals( PasswordCard.BLOCK_MANUAL) )
			{
				simpleService.update(card);
				throw new UnlockManualPasswordCardException();
			}


			card.setBlockReason(null);
			card.setBlockType( PasswordCard.BLOCK_NONE );			
			passwordCardService.unlock(card);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ������ �� �������������� ����� �� �����
	 * @throws BusinessException
	 */
	public Integer getUnusedPasswordNumber() throws BusinessException
	{
		try
		{
			unlockCheck();
			return passwordCardService.getNextUnusedCardPasswordNumber( getCard() );
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	private void unlockCheck() throws BusinessException
	{
		Department department = getCurrentDepartment();
		ActivePerson person = getPerson();

		if(! person.getDepartmentId().equals(department.getId()) )
			throw new BusinessException("������������ �� ����������� �������� �������������");

		if(getCard() == null)
			throw new BusinessException("�� ����������� ����� ��� �������������");

		if(!getCard().getLogin().equals( person.getLogin() ))
			throw new BusinessException("����� ������� �� ����������� ������������");
	}
	
	private Department getCurrentDepartment() throws BusinessException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	    Long departmentId = employeeData.getEmployee().getDepartmentId();
		return departmentService.findById( departmentId );
	}
}