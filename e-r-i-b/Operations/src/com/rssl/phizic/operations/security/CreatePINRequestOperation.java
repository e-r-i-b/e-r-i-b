package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.pin.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Roshka
 * @ created 17.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class CreatePINRequestOperation extends OperationBase
{
	static final String PIN_CREATION_ERROR_MESSAGE = "Ошибка генерации PIN";
	private static final int USER_ID_LENGTH = 8;
	private static final int TRY_ITERATIONS = 10;
	private static final int DEFAULT_PINS_COUNT = 10;

	private static final PINService pinService = new PINService();
    private static final DepartmentService departmentService = new DepartmentService();

	private PINEnvelopeCreator pinEnvelopeCreator;
	private PINValueGenerator pinValueGenerator;

	private Integer count = DEFAULT_PINS_COUNT;
	private Department department = null;
	private List<PINEnvelope> pins;

	public CreatePINRequestOperation()
	{
		pinEnvelopeCreator = new PINEnvelopeCreator();
		pinValueGenerator  = new PINValueGenerator();
	}

	public void setDepartment(Long departmentId) throws BusinessException
	{
		department = departmentService.findById(departmentId);

		if(department == null)
			throw new BusinessException("Выбранное отделение не найдено среди подключенных к системе");

	}

	public PINEnvelopeCreator getPinEnvelopeCreator()
	{
		return pinEnvelopeCreator;
	}

	public void setPinEnvelopeCreator(PINEnvelopeCreator pinEnvelopeCreator)
	{
		this.pinEnvelopeCreator = pinEnvelopeCreator;
	}

	public PINValueGenerator getPinValueGenerator()
	{
		return pinValueGenerator;
	}

	public void setPinValueGenerator(PINValueGenerator pinValueGenerator)
	{
		this.pinValueGenerator = pinValueGenerator;
	}

	public Department getDepartment()
	{
		return  department;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public List<PINEnvelope> getPins()
	{
		return pins;
	}

	public String getRequestNumber()
	{
		return pins.size() > 0 ? pins.get(0).getRequestNumber().toString() : "не найден";
	}

	/**
	 * Создание запроса на печать pin-конвертов.
	 * @return
	 * @throws ParserConfigurationException
	 * @throws BusinessException
	 */
	@Transactional
	public Document createRequest() throws ParserConfigurationException, BusinessException
	{
		long requestNumber = pinService.getLastRequestNumber() + 1;

		List<PINEnvelope> pins = createPINs( requestNumber );

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

		Document document = documentBuilder.newDocument();

		Element root = document.createElement("request");
		document.appendChild(root);
		//todo сейчас формат xml не совпадает с реально передаваемыми данными, необходимо разделить на две
		XmlHelper.appendSimpleElement(root, "number", pins.get(0).getRequestNumber().toString());
		XmlHelper.appendSimpleElement(root, "regionBank", department.getId().toString());
		XmlHelper.appendSimpleElement(root, "branch", "0");
		XmlHelper.appendSimpleElement(root, "office", "0");

		Element loginsElement = document.createElement("logins");
		root.appendChild(loginsElement);

		for (PINEnvelope pin : pins)
			XmlHelper.appendSimpleElement(loginsElement, "login", pin.getUserId());

		return document;
	}

	private List<PINEnvelope> createPINs(long requestNumber) throws BusinessException
	{
		if(department == null)
			throw new BusinessException("Для генерации ПИН-конвертов необходимо установить отделение");

		pins = new ArrayList<PINEnvelope>();
		for (int i = 0; i < count; i++)
			createPIN( TRY_ITERATIONS, requestNumber, department.getId());

		return pins;
	}

	void createPIN( int tryIterations, long requestNumber, long departmentId ) throws BusinessException
	{
		try
		{
			String userId = pinValueGenerator.newUserId(USER_ID_LENGTH);
			pins.add(pinEnvelopeCreator.create(userId, requestNumber, departmentId));
		}
		catch (DuplicatePINException e)
		{
			if( tryIterations == 0 )
				createPIN( --tryIterations, requestNumber, departmentId);
			else
				throw new BusinessException(PIN_CREATION_ERROR_MESSAGE, e);
		}
	}
}