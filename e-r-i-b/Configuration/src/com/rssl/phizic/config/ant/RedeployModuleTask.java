package com.rssl.phizic.config.ant;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.build.BuildConfig;
import com.rssl.phizic.config.build.EjbModule;
import com.rssl.phizic.config.build.WebModule;
import com.rssl.phizic.config.build.BuildConfigIO;
import static com.rssl.phizic.config.build.BuildConfig.BUILD_CONFIG_XML;
import com.rssl.phizic.utils.AntUtils;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.JvmArgsBean;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.taskdefs.Touch;
import org.apache.tools.ant.taskdefs.Jar;

import java.io.File;

/**
 * @author Barinov
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��� �������� ������ (������ ��� OC4J):
 * 1. �������� ������
 * 2. ��������� ���� ����� web.xml (� ������)
 * 3. ���� ������� -updateConfig oc4j - ������ ���������� � ��� ������ ��������� �����������.
 */
public class RedeployModuleTask extends Task
{
	private static final String PROPERTY_OC4J_HOME = "PHIZIC_OC4J_HOME";

	private File confDir;

	private File tempDir;

	private String appName;

	private String moduleName;

	///////////////////////////////////////////////////////////////////////////

	private File oc4jAdminJar;

	/**
	 * ���������� ear, ��������, ${projectRoot}/Phiz.ear
	 */
	private File ear;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @param confDir - ���������� � ����������� ������������, ��������, ${projectRoot}Settings/configs/sbrf
	 */
	public void setConfDir(File confDir)
	{
		this.confDir = confDir;
	}

	/**
	 * @param appName - ��� ����������, ��������, PhizIC
	 */
	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	/**
	 * @param moduleName - ��� ������
	 */
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}

	/**
	 * @param tempDir - ���������� ��� ��������� ������
	 * ���� ����� ���������� jar-���������� ������-�������
	 */
	public void setTempDir(File tempDir)
	{
		this.tempDir = tempDir;
	}

	@Override
	public void init() throws BuildException
	{
		super.init();

	}

	public void execute() throws BuildException, ConfigurationException
	{
		File projectRoot = AntUtils.getProjectRoot(getProject());
		File appConfDir = AntUtils.getAppConfDir(confDir, appName);

		oc4jAdminJar = new File(getOC4JHome(), "j2ee/home/admin.jar");
		ear = new File(projectRoot, appName + ".ear");

		File buildConfigXml = new File(appConfDir, BUILD_CONFIG_XML);
		BuildConfig buildConfig = BuildConfigIO.readBuildConfigXml(buildConfigXml);
		if (buildConfig == null)
			throw new BuildException("�� ������� ��������� BuildConfig �� " + buildConfigXml);

		BuildSingleModuleTask buildTask = new BuildSingleModuleTask();
		buildTask.setProject(getProject());
		buildTask.setConfDir(appConfDir);
		buildTask.setDestDir(ear);
		buildTask.setTempDir(tempDir);
		buildTask.setModuleName(moduleName);
		buildTask.init();
		buildTask.execute();

		WebModule webModule = buildConfig.getWebModule(moduleName);
		if (webModule != null) {
			touchWebXml(webModule);
			oc4jUpdateConfig(webModule);
			return;
		}

		EjbModule ejbModule = buildConfig.getEjbModule(moduleName);
		if (ejbModule != null) {
			jarModule(ejbModule.getJarName());
			oc4jUpdateEJBModule(ejbModule);
			return;
		}

		throw new BuildException("�� ����, ��� ������������ ������ " + moduleName);
	}

	private void oc4jUpdateConfig(WebModule webModule) throws BuildException, ConfigurationException
	{
		OC4JConfig oc4jCfg = new OC4JConfig(confDir);

		String url = "ormi://" + oc4jCfg.getHost() + ":" + oc4jCfg.getPort();
		String args = String.format("%s %s %s -updateConfig",
				url, oc4jCfg.getAdminLogin(), oc4jCfg.getAdminPassword());

		Java java = new Java();
		java.setFork(true);
		java.setProject(getProject());
		java.setJvmargs(getJvmArgs());
		java.setJar(oc4jAdminJar);
		java.setArgs(args);
		java.init();

		log("���������� web-������ " + webModule.getName() + " �������� " + java.getCommandLine());
		java.execute();
	}

	private void oc4jUpdateEJBModule(EjbModule ejbModule) throws BuildException, ConfigurationException
	{
		// �� ������ ������ ������� -updateEJBModule ��������, �� ����������� ����������
		// � ���������� ��������������� ���� ear
		// TODO: (�����������) ������ ����������, ��������� OC4J ������������� ������ ejb-������. ����������� ����� �.

		OC4JConfig oc4jCfg = new OC4JConfig(confDir);

		String ejbJarName = ejbModule.getJarName();
		File jarFile = new File(tempDir, ejbJarName);
		String url = "ormi://" + oc4jCfg.getHost() + ":" + oc4jCfg.getPort();
		String args = String.format("%s %s %s -application %s -updateEJBModule %s -file %s",
				url, oc4jCfg.getAdminLogin(), oc4jCfg.getAdminPassword(),
				appName, ejbJarName, jarFile);

		Java java = new Java();
		java.setFork(true);
		java.setProject(getProject());
		java.setJvmargs(getJvmArgs());
		java.setJar(oc4jAdminJar);
		java.setArgs(args);
		java.init();

		log("���������� ejb-������ " + ejbModule.getName() + " �������� " + java.getCommandLine());
		java.execute();
	}

	private void jarModule(String moduleFilename)
	{
		Jar jarTask = new Jar();
		jarTask.setProject(getProject());
		jarTask.init();

		jarTask.setBasedir(new File(ear, moduleFilename));
		jarTask.setDestFile(new File(tempDir, moduleFilename));
		jarTask.execute();
	}

	private void touchWebXml(WebModule webModule) throws BuildException
	{
		Touch touchTask = new Touch();
		touchTask.setProject(getProject());
		touchTask.setFile(new File(ear, webModule.getWarName() + "/WEB-INF/web.xml"));
		touchTask.init();
		touchTask.execute();
	}

	private String getOC4JHome()
	{
		String home = getProject().getProperty(PROPERTY_OC4J_HOME);
		if (StringHelper.isEmpty(home))
			throw new BuildException("�� ���������� ���������� " + PROPERTY_OC4J_HOME);
		return home;
	}

	private String getJvmArgs()
	{
		return AntUtils.getJvmArgs(new JvmArgsBean());
	}

	@Override
	@SuppressWarnings({"CloneDoesntCallSuperClone"})
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}
}
