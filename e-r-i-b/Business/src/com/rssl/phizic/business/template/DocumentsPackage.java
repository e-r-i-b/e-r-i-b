package com.rssl.phizic.business.template;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 11.03.2007
 * Time: 16:32:27
 * To change this template use File | Settings | File Templates.
 */
public class DocumentsPackage
{
    private Long id;
	private PackageTemplate packageTemplate;
	private DocTemplate template;

		/**
		 * @return логин пользователя, пренадлежащего группе
		 */
		PackageTemplate getPackage()
		{
			return packageTemplate;
		}

		void setPackage(PackageTemplate packageTemplate)
		{
			this.packageTemplate = packageTemplate;
		}

		Long getId()
		{
			return id;
		}

		void setId(Long id)
		{
			this.id = id;
		}

		/**
		 * @return группа, которой пренадлежит элемент
		 */
		DocTemplate getTemplate()
		{
			return template;
		}

		void setTemplate(DocTemplate template)
		{
			this.template = template;
		}

}
