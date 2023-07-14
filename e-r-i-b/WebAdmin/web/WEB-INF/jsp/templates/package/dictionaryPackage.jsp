<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/package/list"  onsubmit="return setEmptyAction(event);">
  <tiles:insert definition="personEdit">
    <tiles:put name="submenu" type="string" value="PackagesTemplates"/>
	<%--<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>--%>
    <tiles:put name="pageTitle" type="string" value="Пакеты шаблонов документов банка"/>

	<!--меню-->
    <tiles:put name="menu" type="string">
       <tiles:insert definition="commandButton" flush="false">
			<tiles:put name="commandKey" value="button.dict.choose"/>
			<tiles:put name="commandHelpKey" value="button.dict.choose.help"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="bundle" value="templatesBundle"/>
	        <tiles:put name="validationFunction">
                function()
                {
                    checkIfOneItem("selectedIds");
                    return checkSelection('selectedIds','Выберите шаблон');
                }
            </tiles:put>
           <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.dict.cancel"/>
			<tiles:put name="commandHelpKey" value="button.dict.cancel.help"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="bundle" value="templatesBundle"/>
			<tiles:put name="action" value="/persons/list.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
	</tiles:put>

	<!--данные-->
	<tiles:put name="data" type="string">		
	   <c:set var="form" value="${ShowDictionaryPackageForm}"/>

        <tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="documentsPack"/>
			<tiles:put name="text" value="Список пакета документов"/>
			<tiles:put name="head">
		        <td width="20px"> &nbsp;  </td>
				<td width="200px">Имя</td>
				<td>Описание</td>
		        <td width="80px" title="Дата обновления">Дата</td>
			</tiles:put>
			<tiles:put name="data">
             <c:forEach var="packagesList" items="${form.data}">
				<tr class="listLine0">
                    <td align=center class="ListItem">
						<html:radio property="selectedIds" value="${packagesList.id}" style="border:none"/>
					</td>
					<td class="ListItem">
						<span class="colortext">
						  <nobr>&nbsp;<bean:write name="packagesList" property="name"/>&nbsp;</nobr>
						</span>
					</td>
					<td class="ListItem" colspan="2"><span class="colortext"><bean:write name="packagesList" property="description"/>&nbsp;</span></td>
				</tr>
					<c:forEach var="template" items="${packagesList.templates}" varStatus="lineInfo">
						<tr class="listLine1">
							<td class="listItemBottom">&nbsp;</td>
							<td class="listItem" valign="top">
								<nobr>&nbsp;<bean:write name="template" property="name"/>&nbsp;</nobr>
							</td>
							<td class="listItem" nowrap="true">
								<bean:write name="template" property="description"/>&nbsp;
							</td>
							<td class="listItem" nowrap="true" align="center">
								&nbsp;<bean:write name="template" property="update.time" format="dd.MM.yyyy"/>&nbsp;
							</td>
						</tr>
					 </c:forEach>
		     </c:forEach>
	    </tiles:put>
		<tiles:put name="isEmpty" value="${empty form.data}"/>
		<tiles:put name="emptyMessage" value="Нет пакетов шаблонов."/>
	</tiles:insert>
	</tiles:put>
 </tiles:insert>
</html:form>