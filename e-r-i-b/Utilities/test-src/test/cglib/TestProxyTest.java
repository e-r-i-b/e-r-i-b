package test.cglib;

import junit.framework.TestCase;
import net.sf.cglib.proxy.Factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 15.02.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1122 $
 * @noinspection MagicNumber
 */
public class TestProxyTest extends TestCase
{

	public void manualTestInterceptOperationMethod () throws IllegalAccessException, InstantiationException
	{
		Class factory = TestProxy.createFactory(TestOperation.class);
		TestOperation testOperation = (TestOperation)factory.newInstance();
		((Factory)testOperation).setCallbacks(TestProxy.CALLBACKS);

		testOperation.doSomething();
	}

	public void manualTestTestProxy() throws IllegalAccessException, InstantiationException
	{
		List arr = (ArrayList) TestProxy.newInstance(ArrayList.class);
		List arr1 = new ArrayList();

		// ����� ������������������ ������ ������
		func1(arr);
		// ������ ����� ���� �� ������
		func1(arr1);
		// ����� ���������������� ������ ������
		func2(arr);
		// ������ ����� ���� �� ������
		func2(arr1);
		// �������� ���������� ������� ��������
		func3_0(); // too sloooooooooow - � 100 ��� ��������� 3_3
		// �������� ����������� ������� ����� new
		func3_1();
		// �������� ����������� ������� ����� newInstance
		func3_2();
		// �������� ���������� ������� � �������������� Factory
		func3_3();
	}

	private void func1 (List arr)
	{
		Date start;
		Date end;

		arr.add(this);
		start = new Date();
		for(int i=0; i < 10000000; i++)
		{
			arr.get(0);
		}
		end = new Date();

		System.out.println("func1 elapsed :" + (end.getTime() - start.getTime()));
	}

	private void func2 (List arr)
	{
		Date start;
		Date end;

		start = new Date();
		for (int i = 0; i < 10000000; i++)
		{
			arr.clear();
		}
		end = new Date();

		System.out.println("func2 elapsed :" + (end.getTime() - start.getTime()));
	}

	private void func3_0 ()
	{
		List arr = null;
		Date start;
		Date end;

		start = new Date();
		for (int i = 0; i < 100; i++)
		{
			arr = (ArrayList) TestProxy.newInstance(ArrayList.class);
		}
		end = new Date();

		System.out.println("func3_0 elapsed :" + (end.getTime() - start.getTime()));
		arr.toString();
	}

	private void func3_1 ()
	{
		List arr = null;
		Date start;
		Date end;

		start = new Date();
		for (int i = 0; i < 100000; i++)
		{
			arr = new ArrayList();
		}
		end = new Date();

		System.out.println("func3_1 elapsed :" + (end.getTime() - start.getTime()));
		arr.toString();
	}

	private void func3_2 () throws IllegalAccessException, InstantiationException
	{
		List arr = null;
		Date start;
		Date end;

		start = new Date();
		for (int i = 0; i < 100000; i++)
		{
			arr = (List) ArrayList.class.newInstance();
		}
		end = new Date();

		System.out.println("func3_2 elapsed :" + (end.getTime() - start.getTime()));
		arr.toString();
	}

	private List func3_3 () throws IllegalAccessException, InstantiationException
	{
		List arr = null;
		Date start;
		Date end;

		Class factory = TestProxy.createFactory(ArrayList.class);

		start = new Date();
		for (int i = 0; i < 100000; i++)
		{
			arr = (List) factory.newInstance();
			((Factory)arr).setCallbacks(TestProxy.CALLBACKS);
		}
		end = new Date();

		System.out.println("func3_3 elapsed :" + (end.getTime() - start.getTime()));

		return arr;
	}
}
