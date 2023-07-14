package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsLoader;
import com.rssl.phizic.utils.StringHelper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Sync;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.List;

/**
 * Копирование рнеобходимых в конфигурации скинов в Exploded.ear.
 * Вызывется при упаковке ресурсов (explode_web_resources).
 * Считывает необходимые скины из skins.xml и по systemName скина, копирует необходимые папки.
 *
 * @author egorova
 * @ created 27.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class SkinsSyncTask extends Task
{
	private String root;
	private String baseSourcePath;
	private String toDirPath;
	private String additionalPath;

	public void setRoot(String root)
	{
		this.root = root;
	}

	public void setBaseSourcePath(String baseSourcePath)
	{
		this.baseSourcePath = baseSourcePath;
	}

	public void setToDirPath(String toDirPath)
	{
		this.toDirPath = toDirPath;
	}

	public void setAdditionalPath(String additionalPath)
	{
		this.additionalPath = additionalPath;
	}

	public void execute()
	{

		if (root == null || root.length() == 0)
			throw new BuildException("Не установлен параметр 'root'");

		log("Loading skins descriptions from path + " + root + " ...");

		SkinsLoader skinsLoader = new SkinsLoader(root);
		List<Skin> skins = null;
		try
		{
			skins = skinsLoader.load();
		}
		catch (BusinessException e)
		{
			throw new BuildException(e);
		}
		if (skins.isEmpty())
			throw new BuildException("Не установлен скины приложения");
		for (Skin skin: skins)
		{
//исключаем глобальную настройку. Такой папки не должно быть!			
			if (skin.getSystemName().equals("global"))
				continue;

			Sync sync = initSync();

			File fileTo;
			
			if (StringHelper.isEmpty(additionalPath))
				fileTo = new File(toDirPath + "/" + skin.getSystemName());
			else
				fileTo = new File(toDirPath + "/" + skin.getSystemName()+"/"+additionalPath);
			log("Skins loading into "+fileTo.getPath());

			sync.setTodir(fileTo);

			File file = new File(baseSourcePath +"/" + skin.getSystemName());
			FileSet fileSet = new FileSet();
			fileSet.setDir(file);
			fileSet.setProject(sync.getProject());
			sync.addFileset(fileSet);

			sync.execute();
		}
	}

	/**
	 * Создание и инициализация Sync, который
	 * Synchronize a local target directory from the files defined in one or more filesets.
	 * Т.к. каждый раз необходим новый fileset, то и Sync создаем новый.
	 * @return Sync
	 */
	private Sync initSync()
	{
		Sync sync = new Sync();
		sync.setProject(new Project());
		sync.setTaskType("copy_config_skins");
		sync.setTaskName("copy_config_skins");
		sync.setOwningTarget(new Target());
		sync.init();
		return sync;
	}
}
