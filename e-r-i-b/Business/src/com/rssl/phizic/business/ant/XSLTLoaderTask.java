package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.meta.XSLTBean;
import com.rssl.phizic.business.payments.forms.meta.XSLTService;
import com.rssl.phizic.utils.files.FileHelper;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.DirectoryScanner;
import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class XSLTLoaderTask extends SafeTaskBase
{
	private static final String DEFAULT_ENCODING = "UTF-8";

	private List<FileSet> filesets = new LinkedList<FileSet>();

	private String encoding;

	///////////////////////////////////////////////////////////////////////////

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public void addFileset(FileSet fileset)
	{
		this.filesets.add(fileset);
	}

	public void safeExecute()
	{
		if (filesets.isEmpty())
			throw new IllegalStateException("�� ������ fileset");

		if (StringHelper.isEmpty(encoding))
			encoding = DEFAULT_ENCODING;

		// 1. �������� 2 ����: "name->xslt" � "name->xsd"
		Map<String, File> xsltMap = new HashMap<String, File>();
		Map<String, File> xsdMap = new HashMap<String, File>();
		mapTemplateFiles(xsltMap, xsdMap);

		// 2. �� ����������� ����� ������ XSLT-���� � ���������/��������� �� � ����
		addOrUpdateXSLTBeans(xsltMap, xsdMap);
	}

	private void mapTemplateFiles(Map<String, File> xsltMap, Map<String, File> xsdMap)
	{
		for (FileSet fileset : filesets)
		{
			DirectoryScanner ds = fileset.getDirectoryScanner(getProject());
			File base = ds.getBasedir();
			// ���� � ������ ������������ ������� ���������� <base>
			String[] paths = ds.getIncludedFiles();
			for (String path : paths)
			{
				// 1. ���������� �����
				String ext = FileHelper.getFileExtension(path);
				if (ext == null)
					continue;

				// 2. ��� XSLT-����
				String name = path.substring(0, path.length() - ext.length() - 1);
				name = formatXSLTBeanName(name);

				// 3. XSLT, XSD � ���
				if (ext.equalsIgnoreCase("xslt") || ext.equalsIgnoreCase("xsl"))
					xsltMap.put(name, new File(base, path));
				else if (ext.equalsIgnoreCase("xsd"))
					xsdMap.put(name, new File(base, path));
			}
		}
	}

	private void addOrUpdateXSLTBeans(Map<String, File> xsltMap, Map<String, File> xsdMap)
	{
		XSLTService xsltService = new XSLTService();

		Set<String> nameset = new LinkedHashSet<String>(xsltMap.keySet());
		nameset.retainAll(xsdMap.keySet());
		int added = 0;
		int updated = 0;
		for (String name : nameset)
		{
			File xslt = xsltMap.remove(name).getAbsoluteFile();
			File xsd = xsdMap.remove(name).getAbsoluteFile();

			try
			{
				boolean isNew;
				XSLTBean xsltBean = xsltService.getBeanByName(name);
				if (xsltBean != null) {
					isNew = false;
					log("��������� XSLT-���: " + name, MSG_INFO);
				}
				else {
					isNew = true;
					xsltBean = new XSLTBean();
					log("��������� XSLT-���: " + name, MSG_INFO);
				}

				xsltBean.setXsltTemplate(readXml(xslt));
				xsltBean.setXsd(readXml(xsd));
				xsltBean.setXsltName(name);
				xsltService.addOrUpdate(xsltBean);

				if (isNew)
					added++;
				else updated++;
			}
			catch (BusinessException e)
			{
				log("������ ��� ����������/���������� XSLT-���� " + e.getMessage(), e, MSG_WARN);
			}
		}

		for (Map.Entry<String, File> entry : xsltMap.entrySet())
			log("XSLT-������ �������������� (�� ������� ���������� XSD-�����): " + entry.getValue().getPath(), MSG_WARN);

		for (Map.Entry<String, File> entry : xsdMap.entrySet())
			log("XSD-����� ��������������� (�� ������� ���������� XSLT-������): " + entry.getValue().getPath(), MSG_WARN);

		log("��������� XSLT-�����: " + added, MSG_INFO);
		log("��������� XSLT-�����: " + updated, MSG_INFO);
	}

	private String readXml(File file) throws BusinessException
	{
		try
		{
			return FileUtils.readFileToString(file, encoding);
		}
		catch (IOException e)
		{
			throw new BusinessException("������ ������ ����� " + file.getPath(), e);
		}
	}

	/**
	 * ����������� ���� � XSLT-���� � ��� ����
	 * @param pathname - (�������������) ���� � XSLT-����
	 * @return ��� XSLT-����
	 */
	private static String formatXSLTBeanName(String pathname)
	{
		String name = StringUtils.replaceChars(pathname, "\\/", ".");
		name = StringUtils.strip(name, ".");
		name = name.toLowerCase();
		return name;
	}
}
