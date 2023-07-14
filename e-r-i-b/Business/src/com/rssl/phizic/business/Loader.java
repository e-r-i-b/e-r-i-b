package com.rssl.phizic.business;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * ������� ����� ��� ������ �� xml ����� ����������� �������� � ��� �������������� � ������.
 *
 * User: Balovtsev
 * Date: 25.05.2011
 * Time: 17:44:29
 *
 * @see com.rssl.phizic.business.groups.GroupsLoader
 * @see com.rssl.phizic.business.skins.SkinsLoader
 */
public abstract class Loader
{
	protected String path;
    	
	protected Loader(String path)
	{
		this.path = path;
	}

	/**
	 *
	 * ��������� ������ �������� �� xml �����.
	 *
	 * @return List
	 * @throws BusinessException
	 */
	public abstract List<?> load() throws BusinessException;

	/**
	 *
	 * ������������� xml � ������
	 *
	 * @return Object
	 * @throws JAXBException
	 */
	protected Object unmarshall() throws JAXBException
	{
		return createUnmarshaller().unmarshal( readFile(new File(path), getFileName()) );
	}

	/**
	 * ������ ���������� ����� � ������� ����� ��������������� ������
	 * @return String
	 */
	protected abstract String getPackage();

	/**
	 * ��� ���������������� ����� xml
	 * @return String
	 */
	protected abstract String getFileName();

	private Unmarshaller createUnmarshaller() throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(getPackage(), getClass().getClassLoader());
		return context.createUnmarshaller();
	}
	
	private File readFile(File currentDir, final String fileName)
    {
        File[] files = currentDir.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.equals(fileName);
            }
        });
        return files.length != 0 ? files[0] : null;
    }
}
