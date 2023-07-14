package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.business.payments.forms.utils.PaymentFormsLoader;

/**
 * @author Kidyaev
 * @ created 27.02.2006
 * @ $Author: dorzhinov $
 * @ $Revision: 47756 $
 */
public class PaymentFormLoaderTask extends SafeTaskBase
{
	private String globals;
	private String root;
	private String type;

	public void safeExecute() throws Exception
	{
		if ( root == null || root.length() <= 0 )
		{
			throw new Exception("�� ���������� �������� 'root'");
		}
		if ( globals == null || globals.length() <= 0)
		{
			throw new Exception("�� ���������� �������� 'globals'");
		}
		if ( type == null || type.length() <= 0)
		{
			throw new Exception("�� ���������� �������� 'type'");
		}

        log("Updating forms in path + " + root);
		//����  type=true �� ������ ��� ���� false �� ������ root
		//�������� load.type ������������ � payments.path ��� �������� ���������� ����
        PaymentFormsLoader paymentFormsLoader = new PaymentFormsLoader();
		if (type.equals("true"))
			paymentFormsLoader.saveFormsDataFromPath(globals, root);
		else if (type.equals("false"))
			paymentFormsLoader.saveFormsDataFromPath(null, root);
	}                                                      

	public void setRoot(String root)
	{
		this.root = root;
	}

	public void setGlobals(String globals)
	{
		this.globals = globals;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
