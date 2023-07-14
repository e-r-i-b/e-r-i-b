package com.rssl.phizic.config.ant;

import com.rssl.phizic.utils.AntUtils;
import static com.rssl.phizic.utils.AntUtils.createFilenameSelector;
import com.rssl.phizic.utils.JvmArgsBean;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;
import java.util.LinkedList;

/**
 * @author Erkin
 * @ created 05.02.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��� ������ js-��������, ���������� � �������������� ���������� Dojo.
 * ���� ������� ������ �� �������� � dojo.build-profile.js.
 *
 * �� �����:
 * - todir - ���������� ����������� ������
 * - dojo.build-profile.js - �������� �������, ����
 *
 * �� ������:
 * - ����� js-������� dojo, dijit, dojox, widget � �.�. � ���� ���� �������� js-������ � ��������������� ������
 * - ����� js-������� dojo, dijit, dojox, widget � �.�. � ���� ���������������� ���� � ��� �� ������
 *
 * ���������:
 * 1. ���������������� ���� �������� ������ �� js-������, ������� ������������ � �������
 * (�������������� �� ������������).
 * 2. ����������� ��������:
 * - ������ js-������ ���� �������� ��������, ������������ � �.�.
 * - ���������� ����� js-������� � ���� js-����
 * 3. �� ����� ������������ ������ ���������������� ����;
 * ��������� ������������ "��� ������������" (���� ����������� ������� ����������������� ����)
 */
public class BuildDojoTask extends Task
{
	private File projectRoot;

	private File todir;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @param todir - ���������� ����������� ������
	 */
	public void setTodir(File todir)
	{
		this.todir = todir;
	}

	@Override
	public void init() throws BuildException
	{
		super.init();

		projectRoot = AntUtils.getProjectRoot(getProject());
	}

	@Override
	public void execute() throws BuildException
	{
		if (todir == null)
			throw new BuildException("�� ����� �������� todir");

		Java java = new Java();
		java.setFork(true);
		java.setDir(getWorkingDir());
		java.setProject(getProject());
		java.createJvmarg().setLine(getJvmArgs());
		java.createClasspath().add(getClassPath());
		java.setClassname("org.mozilla.javascript.tools.shell.Main");
		java.createArg().setLine(getArgs());
		java.init();

		log("������ dojo-�������� �������� " + java.getCommandLine());
		java.execute();
	}

	private File getWorkingDir()
	{
		return new File(projectRoot, "WebResources/web/scripts/util/buildscripts");
	}

	private String getJvmArgs()
	{
		return AntUtils.getJvmArgs(new JvmArgsBean());
	}

	private Path getClassPath()
	{
		Path classpath = new Path(getProject());

		FileSet fs = new FileSet();
		fs.setDir(new File(projectRoot, "AntBuilds"));
		fs.add(createFilenameSelector("lib/shrinksafe/*.jar"));

		classpath.add(fs);
		return classpath;
	}

	private String getArgs()
	{
		List<String> args = new LinkedList<String>();
		args.add("../../dojo/dojo.js");
		args.add("baseUrl=../../dojo");
		args.add("load=build");
		args.add("action=release");
		args.add("profile=dojo.build-profile.js");
		args.add("releaseDir=" + makePath(todir));
//		args.add("--help");
//		args.add("--check-args");

		return StringUtils.join(args, " ");
	}

	private static String makePath(File target)
	{
		return target.getPath().replace('\\', '/');
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
