package com.rssl.phizic.config.ant;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.build.*;
import com.rssl.phizic.utils.AntUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.selectors.OrSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.rssl.phizic.config.build.BuildConfig.BUILD_CONFIG_XML;
import static com.rssl.phizic.utils.AntUtils.createFilenameSelector;

/**
 * @author Erkin
 * @ created 06.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сборка бизнес-модулей и веб-модулей по build-config.xml
 */
public class BuildModulesTask extends Task
{
	private static final String IGNORE_CLASSES = "**/*Test.class **/*TestCase.class **/*TestCaseBase.class";

	private File projectRoot;

	private File confDir;

	private File destDir;

	private File tempDir;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @param confDir - директория с build-config.xml
	 */
	public void setConfDir(File confDir)
	{
		this.confDir = confDir;
	}

	/**
	 * @param destDir - директория-результат
	 */
	public void setDestDir(File destDir)
	{
		this.destDir = destDir;
	}

	/**
	 * @param tempDir - директория для временных файлов
	 * Сюда будут собираться jar-библиотеки бизнес-модулей
	 */
	public void setTempDir(File tempDir)
	{
		this.tempDir = tempDir;
	}

	@Override
	public void init() throws BuildException
	{
		super.init();

		projectRoot = AntUtils.getProjectRoot(getProject());
	}

	private BuildConfig getBuildConfig() throws BuildException
	{
		File buildConfigXml = new File(confDir, BUILD_CONFIG_XML);
		if (!buildConfigXml.exists()) {
			log("Не найден " + buildConfigXml);
			return null;
		}
		return BuildConfigIO.readBuildConfigXml(buildConfigXml);
	}

	@Override
	public void execute() throws BuildException, ConfigurationException
	{
		// 1. Получаем build-config
		BuildConfig buildConfig = getBuildConfig();
		if (buildConfig == null)
			return;

		getProject().setUserProperty("debug-mode", String.valueOf(buildConfig.isDebugMode()));

		buildModules(buildConfig);
	}

	protected void buildModules(BuildConfig buildConfig)
	{
		// 1. Готовим jar-ы с бизнес-модулями
		for (BusinessModule businessModule : buildConfig.getBusinessModules())
			buildBusinessModule(businessModule);

		// 2. Готовим jar-ы с ejb-модулями
		for (EjbModule ejbModule : buildConfig.getEjbModules())
			buildEjbModule(ejbModule);

		// 3. Готовим war-ы с веб-модулями
		for (WebModule webModule : buildConfig.getWebModules())
			buildWebModule(webModule);
	}

	protected void buildBusinessModule(BusinessModule businessModule) throws BuildException
	{
		try
		{
			// Бизнес-модули собираем во временную директорию,
			// затем подкладываем в war
			File jar = new File(tempDir, businessModule.getJarName());

			Jar jarTask = new Jar();
			jarTask.setProject(getProject());
			jarTask.init();

			jarTask.setBasedir(getModuleBinDir(businessModule));
			jarTask.setExcludes(IGNORE_CLASSES);
			jarTask.setDestFile(jar);
			jarTask.execute();
		}
		catch (BuildException e)
		{
			throw new BuildException("Ошибка при сборке бизнес-модуля " + businessModule.getName(), e);
		}
	}

	@SuppressWarnings({"ReuseOfLocalVariable"})
	protected void buildEjbModule(EjbModule ejbModule)
	{
		// Ejb-модули сразу кладём в результирующую директорию (.ear)

		String jarName = ejbModule.getJarName();
		File jar = new File(destDir, jarName);
		File jarMetaInf = new File(jar, "META-INF");

		// 1. Классы -> корень
		FileSet classes = new FileSet();
		classes.setDir(getModuleBinDir(ejbModule));
		copyFiles(jar, classes);

		// 2. META-INF -> META-INF
		FileSet metaInf = new FileSet();
		metaInf.setDir(new File(getModuleBaseDir(ejbModule), "META-INF"));
		copyFiles(jarMetaInf, metaInf);

		// 3. Бизнес-модули -> корень
		// В связи в проблемами с областью видимости библиотек на WebSphere(BUG061731)
		// незапакованные библиотеки для ejb-модулей складываем в корень
		for (BusinessModule businessModule : ejbModule.getBusinessModules())
		{
			FileSet lib = new FileSet();
			lib.setDir(getModuleBinDir(businessModule));
			copyFiles(jar, lib);
		}

		// (5) Частные настройки модуля
		// Settings/configs/${конфигурация}/${приложение}/${модуль} -> корень
		File ejbModuleSettingsDir = new File(confDir, ejbModule.getSettingsPath());
		if (ejbModuleSettingsDir.exists())
		{
			FileSet settings = new FileSet();
			settings.setDir(ejbModuleSettingsDir);
			copyFiles(jar, settings);
		}

		// 6. Добавляем classpath с указанием пути к lib
		writeClasspath(jarMetaInf, buildClasspath(jarName+"/lib/", ejbModule.getBusinessModules()));
	}

	protected void buildWebModule(WebModule webModule)
	{
		// Веб-модули сразу кладём в результирующую директорию (.ear)

		File war = new File(destDir, webModule.getWarName());
		File warClasses = new File(war, "WEB-INF/classes");

		// 1. Экшены и формы веб-модуля -> WEB-INF/classes
		FileSet classes = new FileSet();
		classes.setDir(getModuleBinDir(webModule));
		copyFiles(warClasses, classes);

		// 2. WebCommon -> WEB-INF
		FileSet commonWebInf = new FileSet();
		commonWebInf.setDir(new File(projectRoot, "WebCommon/web/WEB-INF"));
		OrSelector or = new OrSelector();
		or.add(createFilenameSelector("tld/**/*.*"));
		or.add(createFilenameSelector("tiles/tiles-components.xml"));
		or.add(createFilenameSelector("tiles/tiles-module-templates.xml"));
		or.add(createFilenameSelector("jsp/common/**/*.*"));
		or.add(createFilenameSelector("skin/**/*.*"));
		commonWebInf.add(or);
		copyFiles(new File(war, "WEB-INF"), commonWebInf);

		// 3. WEB-INF -> WEB-INF
		FileSet webInf = new FileSet();
		webInf.setDir(getModuleWebInfDir(webModule));
		copyFiles(new File(war, "WEB-INF"), webInf);

		// 4. Бизнес-модули (уже приготовлены в виде jar-ов во временной директории) -> WEB-INF/lib
		FileSet lib = new FileSet();
		lib.setDir(tempDir);
		lib.add(createBusinessModulesSelector(webModule.getBusinessModules()));
		copyFiles(new File(war, "WEB-INF/lib"), lib);

		// (5) Частные настройки модуля
		// Settings/configs/${конфигурация}/${приложение}/${модуль} -> WEB-INF/classes
		File webModuleSettingsDir = new File(confDir, webModule.getSettingsPath());
		if (webModuleSettingsDir.exists())
		{
			FileSet settings = new FileSet();
			settings.setDir(webModuleSettingsDir);
			copyFiles(warClasses, settings);
		}
	}

	private File getModuleBaseDir(Module module)
	{
		return new File(projectRoot, module.getBase());
	}

	private File getModuleBinDir(Module module)
	{
		File base = getModuleBaseDir(module);
		return new File(base, "bin");
	}

	private File getModuleWebInfDir(WebModule module)
	{
		File base = getModuleBaseDir(module);
		return new File(base, "web/WEB-INF");
	}

	private void copyFiles(File todir, FileSet fileset) throws BuildException
	{
		Mkdir makeDirTask = new Mkdir();
		makeDirTask.setProject(getProject());
		makeDirTask.init();

		makeDirTask.setDir(todir);
		makeDirTask.execute();

		Copy copyTask = new Copy();
		copyTask.setProject(getProject());
		copyTask.init();

		copyTask.setTodir(todir);
		copyTask.addFileset(fileset);
		copyTask.execute();
	}

	/**
	 * Пишет classpath в METAINF.MF по указанному пути
	 * @param metaInfDir - директория с целевым METAINF.MF
	 * @param classpath - classpath
	 */
	private void writeClasspath(File metaInfDir, String classpath) throws BuildException
	{
		try
		{
			ManifestTask manifestTask = new ManifestTask();
			manifestTask.init();
			manifestTask.setFile(new File(metaInfDir, "MANIFEST.MF"));
			manifestTask.addConfiguredAttribute(new Manifest.Attribute("Class-Path", classpath));
			manifestTask.execute();
		}
		catch (ManifestException e)
		{
			throw new BuildException(e);
		}
	}
	
	private OrSelector createBusinessModulesSelector(Collection<BusinessModule> businessModules)
	{
		OrSelector or = new OrSelector();
		for (BusinessModule businessModule : businessModules)
			or.add(createFilenameSelector(businessModule.getJarName()));
		return or;
	}

	private String buildClasspath(String prefix, Collection<BusinessModule> businessModules)
	{
		List<String> classpaths = new ArrayList<String>(businessModules.size());
		for (BusinessModule module : businessModules)
			classpaths.add(prefix + module.getJarName());
		return StringUtils.join(classpaths, " ");
	}

	@Override
	public BuildModulesTask clone() throws CloneNotSupportedException
	{
		return (BuildModulesTask)super.clone();
	}
}
