<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!--TODO grid-->
<html:form action="/templates/package/list"  onsubmit="return setEmptyAction(event);">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

  <tiles:insert definition="logMain">
	<tiles:put name="needSave" type="string" value="false"/>
	<tiles:put name="submenu" type="string" value="TemplatesPack"/>
	<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
    <tiles:put name="pageTitle" type="string">
	  Пакеты шаблонов документов банка 
  </tiles:put>
		<!--меню-->
    <tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey"     value="button.pack.add"/>
			<tiles:put name="commandHelpKey" value="button.pack.add.help"/>
			<tiles:put name="bundle"  value="templatesBundle"/>
			<tiles:put name="image"   value=""/>
			<tiles:put name="action"  value="/templates/package/edit.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

	<!--данные-->
	<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="templatesPackage"/>
		<tiles:put name="text" value="Список пакетов шаблонов документов банка"/>
        <tiles:put name="buttons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.pack.remove"/>
                <tiles:put name="commandHelpKey" value="button.pack.remove.help"/>
                <tiles:put name="bundle"  value="templatesBundle"/>
                <tiles:put name="validationFunction">
                    function()
                    {
                        checkIfOneItem("selectedIds");
                        return checkSelection('selectedIds', 'Выберите шаблон для удаления');
                    }
                </tiles:put>
			</tiles:insert>
        </tiles:put>
		<tiles:put name="head">
	        <td width="20px">
			    <input name="selectAll" type="checkbox" style="border:none" onclick="switchSelection('selectAll')"/>
			</td>
			<td width="200px">Имя</td>
			<td>Описание</td>
		    <td width="80px" title="Дата обновления">Дата</td>
		</tiles:put>
		<tiles:put name="data">
             <c:forEach var="packagesList" items="${form.data}">
				<tr class="listLine${packagesList.id % 2}">
                    <td align=center class="ListItem">   
						<html:multibox property="selectedIds" style="border:none">
							<bean:write name="packagesList" property="id"/>
						</html:multibox>
					</td>
					<td class="ListItem">
						<nobr>&nbsp;
						<phiz:link action="/templates/package/edit"
						           serviceId="PersonManagement">
                            <phiz:param name="id" value="${packagesList.id}"/>
		                       ${packagesList.name}
	                    </phiz:link>&nbsp;</nobr>
					</td>
					<td class="ListItem" colspan="2"><bean:write name="packagesList" property="description"/>&nbsp;</td>
				</tr>
					<c:forEach var="template" items="${packagesList.templates}" varStatus="lineInfo">
						<tr class="listLine${packagesList.id % 2}">
							<td class="listItem" colspan="2"  valign="top">
								<nobr>&nbsp;<bean:write name="template" property="name"/>&nbsp;</nobr>
							</td>
							<td class="listItem" nowrap="true">
								&nbsp;<bean:write name="template" property="description"/>&nbsp;
							</td>
							<td class="listItem" nowrap="true" align="center">
								&nbsp;<bean:write name="template" property="update.time" format="dd.MM.yyyy"/>&nbsp;
							</td>
						</tr>
					</c:forEach>
		     </c:forEach>
		    </tiles:put>
		    <%--<tiles:put name="isEmpty" value="${empty form.data}"/>--%>
		    <tiles:put name="emptyMessage" value="Не найдено ни одного пакета шаблонов!"/>
	</tiles:insert>
	</tiles:put>
 </tiles:insert>
</html:form>