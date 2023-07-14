<%--
  Created by IntelliJ IDEA.
  User: gladishev
  Date: 16.11.2007
  Time: 15:58:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% pageContext.getRequest().setAttribute("mode","Services");%>

<html:form action="/dictionaries/errors"  onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="serviceMain">
		<tiles:put name="submenu" type="string" value="ErrorMessages"/>
        <tiles:put name="pageTitle" type="string">
			Справочник ошибок
		</tiles:put>
          <!--меню-->
				<tiles:put name="menu" type="string">
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.doc.add"/>
						<tiles:put name="commandHelpKey" value="button.doc.add.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
						<tiles:put name="image"   value=""/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
					<tiles:insert definition="commandButton" flush="false">
						<tiles:put name="commandKey"     value="button.doc.delete"/>
						<tiles:put name="commandHelpKey" value="button.doc.delete.help"/>
						<tiles:put name="bundle"  value="templatesBundle"/>
						<tiles:put name="image"   value=""/>
                        <tiles:put name="viewType" value="blueBorder"/>
					</tiles:insert>
				</tiles:put>
		<!--данные-->
		<tiles:put name="data" type="string">
			<table cellpadding="0" cellspacing="0" class="MaxSize">
			<tr>
			<td height="100%">
			<table cellspacing="0" cellpadding="0" width="100%" class="userTab" id="tableTitle">
				<!-- заголовок списка -->
				<tr class="titleTable">
					<td width="20px">
						<html:checkbox property="selectAll" style="border:none" onclick="switchSelection('selectAll')"/>
					</td>
					<td width="200px">Имя</td>
					<td>Описание</td>
					<td width="100px" title="Дата обновления">Дата</td>
				</tr>
				<!-- строки списка -->
				<% int lineNumber = 0;%>
				<c:forEach items="${GetTemplatesDocumentsListForm.forms}" var="listElement">
					<% lineNumber++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td align=center class="ListItem" width="20px">
							<html:multibox property="selectedIds" style="border:none">
								<bean:write name="listElement" property="id"/>
							</html:multibox>
						</td>
						<td class="ListItem">
							<phiz:link action="/templates/documents/edit"
							            serviceId="PersonManagement">
                                       <phiz:param name="tempId" value="${listElement.id}"/>
			                       ${listElement.name}
 		                    </phiz:link>&nbsp;
						</td>
						<td class="ListItem"><bean:write name="listElement" property="description"/>&nbsp;</td>
						<td class="ListItem" align="center"><bean:write name="listElement" property="update.time" format="dd.MM.yyyy"/>&nbsp;</td>
					</tr>
				</c:forEach>
			</table>
			<% if ( lineNumber == 0 )
			{%>
			<table width="100%" cellpadding="4">
				<tr><td class="messageTab" align="center">Не найдено ни одного шаблона</td></tr>
			</table>
			<script>hideTitle();</script>
			<%}%>
			</td>
			</tr>
			</table>
		</tiles:put>
	</tiles:insert>
</html:form>