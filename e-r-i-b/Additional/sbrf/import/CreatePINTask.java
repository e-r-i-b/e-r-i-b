package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.pin.DuplicatePINException;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.security.crypto.CryptoProviderService;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SimpleSecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ext.sbrf.person.AgrementNumberCreatorSBRF;
import com.rssl.phizic.config.ResourcePropertyReader;
import com.rssl.phizic.config.ConfigFactory;

import java.util.List;
import java.util.Iterator;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Roshka
 * @ created 12.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class CreatePINTask extends SafeTaskBase
{
	private static DepartmentService departmentService = new DepartmentService();
	private String departmentId;
	private String number = "5";
	private Department department;
	private boolean isAll = false;

	public boolean isAll()
	{
		return isAll;
	}

	public void setAll(boolean all)
	{
		isAll = all;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public void safeExecute() throws Exception
	{
        SecurityConfig securityConfig = new SimpleSecurityConfig("PhizIA", new ResourcePropertyReader("iccs.properties"));
        securityConfig.refresh();
        ConfigFactory.getInstance().registerConfig(SecurityConfig.class, securityConfig);


		System.out.println("departmentId=" + (departmentId==null?"null":departmentId));
		System.out.println("isAll=" + isAll);
		System.out.println("number=" + (number==null?"null":number));
		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

        if( !isAll || departmentId == null || departmentId.length() == 0 )
			throw new BusinessLogicException("”кажите id департмента.");

		if( number == null || number.length() == 0 )
			throw new BusinessLogicException("”кажите количество пин-конвертов.");

		//криптопровайдер
		CryptoProviderService cryptoProviderService = new CryptoProviderService();
		cryptoProviderService.start();

		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

		if(!isAll)
		{
			department = departmentService.findById(Long.parseLong(departmentId));
			createPin(department);
		}
		else
		{
			List<Department> departments  = departmentService.getAll();
			for (Department department1 : departments)
			{
				createPin(department1);
			}
		}

		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

		cryptoProviderService.stop();

	}

	private void createPin(Department department) throws BusinessException, ParserConfigurationException, DuplicatePINException
	{//cerate pin
		CreatePINRequestOperation operation = new CreatePINRequestOperation();
		operation.setCount(Integer.parseInt(number));
		operation.setDepartment(department.getId());
		operation.createRequest();
		List<PINEnvelope> pins = operation.getPins();

		CryptoService cryptoService = SecurityFactory.cryptoService();
		String hash123 = cryptoService.hash("123");

		for (PINEnvelope pinEnvelope : pins)
		{
			setHash(pinEnvelope, hash123,department);
			System.out.println("PIN: " + pinEnvelope.getUserId() + " password: 123");
		}
	}

	private void setHash(PINEnvelope envelope, String hash, Department department) throws DuplicatePINException
	{
		try
		{
			PINService pinService = new PINService();

			envelope.setState(PINEnvelope.STATE_UPLOADED);
			envelope.setValue(hash);
			AgrementNumberCreatorSBRF creator = new AgrementNumberCreatorSBRF();
			creator.init(department);
			envelope.setAgreementNumberTest(creator.getNextAgreementNumber());
			envelope.setClientIdTest(new RandomGUID().getStringValue());

			pinService.update(envelope);
		}
		catch(BusinessException ex)
		{
			throw new RuntimeException("ќшибка при создании номер договора",ex);
		}

	 }

}
