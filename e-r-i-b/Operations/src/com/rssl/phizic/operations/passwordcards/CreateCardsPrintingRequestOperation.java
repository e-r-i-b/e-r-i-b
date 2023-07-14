package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.xml.XmlHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Roshka
 * @ created 23.12.2005
 * @ $Author: Evgrafov $
 * @ $Revision: 3384 $
 */
public class CreateCardsPrintingRequestOperation extends OperationBase
{
    private static final PasswordCardService passwordCardService = new PasswordCardService();
    private static final DepartmentService departmentService = new DepartmentService();


    private int cardsCount;
    private int keysCount;
	private Department department;

	/**
	 * инициализация
	 * @param departmentId код отделения банка
	 * @param cardsCount
	 * @param keysCount
	 */
    public void initialize(int cardsCount, int keysCount, long departmentId) throws BusinessException, BusinessLogicException
	{
		this.cardsCount = cardsCount;
		this.keysCount = keysCount;

		department = departmentService.findById(departmentId);

		if(department == null)
		{
			throw new BusinessException("Выбранное отделение не найдено среди подключенных к системе");
		}
    }

	/**
	 * @return сколько карт создавать
	 */
    public int getCardsCount()
    {
        return cardsCount;
    }

	/**
	 * @param cardsCount сколько карт создавать
	 */
    public void setCardsCount(int cardsCount)
    {
        this.cardsCount = cardsCount;
    }

	/**
 	 * @return количество ключей в карте
	 */
    public int getKeysCount()
    {
        return keysCount;
    }

	/**
	 * @param keysCount количество ключей в карте
	 */
    public void setKeysCount(int keysCount)
    {
        this.keysCount = keysCount;
    }

	public Long getLastRequestNumber(){
		return passwordCardService.getLastRequestNumber();
	}

	@Transactional
	public Document createRequest() throws ParserConfigurationException, BusinessException, SecurityDbException
	{
		final Document document = XmlHelper.getDocumentBuilder().newDocument();
	    final PasswordCard[] cards = passwordCardService.create( getCardsCount(), getKeysCount());
		final Element root = document.createElement("request");

		document.appendChild(root);
	    XmlHelper.appendSimpleElement(root, "number", Long.toString(getLastRequestNumber()));
		XmlHelper.appendSimpleElement(root, "regionBank", Long.toString(getDepartment().getId()));
		XmlHelper.appendSimpleElement(root, "branch", "0");
		XmlHelper.appendSimpleElement(root, "office", "0");
		XmlHelper.appendSimpleElement(root, "keyCount", Integer.toString( getKeysCount()));

		final Element cardsElement = document.createElement("cards");
		root.appendChild( cardsElement);

		for (PasswordCard card: cards)
		{
			XmlHelper.appendSimpleElement(cardsElement, "card", card.getNumber());
		}

		return document;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
