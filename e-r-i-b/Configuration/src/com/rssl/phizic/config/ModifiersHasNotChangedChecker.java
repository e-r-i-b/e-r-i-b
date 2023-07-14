package com.rssl.phizic.config;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * �������� �� ��������� �������������.
 *
 * ����� ��������� � ������ ����, �� ������ ����������, ��� �������.
 *
 * @author bogdanov
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ModifiersHasNotChangedChecker extends Task
{
	private static final PrintWriter out = new PrintWriter(System.out, true);

	public void execute() throws BuildException
	{
		ClassLoader oldClassLoader = null;
		try
		{
			oldClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			safeExecute();
		}
		finally
		{
			if (oldClassLoader != null)
				Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	private void safeExecute() throws BuildException
	{
		check();
	}

	public static class ClassInfo
	{
		private final Class clazz;
		private final Map<String, Integer> methodToModifiers = new HashMap<String, Integer>();
		private final boolean publicConstructors;

		ClassInfo(Class clazz, boolean publicConstructors)
		{
			this.clazz = clazz;
			this.publicConstructors = publicConstructors;
		}

		void addMethod(String name, int modfiers)
		{
			methodToModifiers.put(name, modfiers);
		}

		/**
		 * �������� ���������� �������������.
		 */
		public void check()
		{
			out.println("�������� ��������� ������� � " + clazz.getName());
			if (!publicConstructors)
			{
				for (Constructor constr : clazz.getConstructors())
					if (Modifier.isPublic(constr.getModifiers()))
						throw new RuntimeException("� ������ "+ clazz.getName() + " �� ����� ���� ��������� �������������: " + constr.toGenericString());
			}

			Set<String> unusedMethods = new HashSet<String>(methodToModifiers.keySet());
			for (Method method : clazz.getDeclaredMethods())
			{
				if (!methodToModifiers.containsKey(method.getName()))
					continue;

				unusedMethods.remove(method.getName());
				if (method.getModifiers() != methodToModifiers.get(method.getName()))
					throw new RuntimeException("��� ������ " + clazz.getName() + "#" + method.getName() + " �������� ������������. ������ ���� ����������� ������ ������������ " + Modifier.toString(methodToModifiers.get(method.getName())));
			}

			if (!unusedMethods.isEmpty())
				throw new RuntimeException("�� ������ " + clazz.getName() + " ������� ������, ������� ������ �������: " + Arrays.toString(unusedMethods.toArray()));
		}
	}

	private static final List<ClassInfo> classInfos = new ArrayList<ClassInfo>();

	//������ �������, ��� ������� ������������� ����������� ������ ������������ ������������� � �������.
	static
	{
		ClassInfo info = new ClassInfo(Config.class, true);
		info.addMethod("init", 0);
		info.addMethod("doRefresh", Modifier.ABSTRACT | Modifier.PROTECTED);
		info.addMethod("needRefresh", 0);
		classInfos.add(info);

		info = new ClassInfo(ConfigFactory.class, false);
		info.addMethod("getConfigInner", Modifier.PRIVATE);
		info.addMethod("loadConfig", Modifier.PRIVATE);
		classInfos.add(info);

		info = new ClassInfo(PropertyReader.class, false);
		info.addMethod("refreshIfNecessary", Modifier.PROTECTED | Modifier.FINAL);
		info.addMethod("doRefresh", Modifier.PROTECTED | Modifier.ABSTRACT);
		classInfos.add(info);

		info = new ClassInfo(DbPropertyReader.class, false);
		info.addMethod("sendRefresh", Modifier.SYNCHRONIZED);
		info.addMethod("doRefresh", Modifier.PROTECTED);
		info.addMethod("setUpdatePeriod", 0);
		classInfos.add(info);

		info = new ClassInfo(ResourcePropertyReader.class, false);
		info.addMethod("doRefresh", Modifier.PROTECTED);
		classInfos.add(info);
	}

	/**
	 * �������� ��������� ������������� � ������� ������ ������������.
	 */
	public void check()
	{
		out.println("����� ��������� ������������� ConfigFactory");
	    try
	    {
		    for (ClassInfo info : classInfos)
		        info.check();
	    }
	    catch (RuntimeException e)
	    {
		    out.print("������ �� ����� ���������� " + this.getClass().getName() + ": ");
		    e.printStackTrace(out);
		    throw e;
	    }

		out.println("����������� ��������� � ������������� ������� ConfigFactory ���");
	}
}
