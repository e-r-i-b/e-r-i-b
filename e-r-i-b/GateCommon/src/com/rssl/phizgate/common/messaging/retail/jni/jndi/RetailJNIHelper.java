package com.rssl.phizgate.common.messaging.retail.jni.jndi;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.naming.NamingHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizgate.common.messaging.retail.jni.pool.RetailJniPool;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � jndi ��� ��������� �������
 */
public class RetailJNIHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * ���� retail ����� jndi ��������.
	 * @return
	 */
	public static RetailJniPool lookupPoolFactory()
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			return (RetailJniPool)initialContext.lookup(RetailJNDINameBuilder.getRetailJniName());
		}
		catch(NamingException ex)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("�� ������� ����� ���� ��������� � rs-retail c jndi ������: ");
			builder.append( RetailJNDINameBuilder.getRetailJniName());
			builder.append( ". �������� �� �� ��� ������, ��. mbean ��� startServices.xml" );

			String errorMessage = builder.toString();
			RetailJNIHelper.log.error(errorMessage ,ex);
			throw new RuntimeException(errorMessage,ex);
		}
	}

	public static void putPoolFactory(RetailJniPool retailPoolFactory) throws GateException
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			NamingHelper.bind(initialContext, RetailJNDINameBuilder.getRetailJniName() , retailPoolFactory);
		}
		catch (Exception e)
		{
			String errorMessage = "�� ������� ������� jndi ������ ��� ����� ��������� � rs-retail";
			RetailJNIHelper.log.error(errorMessage, e);
			throw new GateException(errorMessage, e);
		}
	}

	public static boolean checkIfExist()
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			return (initialContext.lookup(RetailJNDINameBuilder.getRetailJniName()))!=null;
		}
		catch(NamingException ex)
		{
			return false;
		}
	}
	
}
