<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/templates/package/edit" onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="templatesDoc">
		<tiles:put name="submenu" type="string" value="TemplatesPack"/>
		<tiles:put type="string" name="messagesBundle" value="templatesBundle"/>
        <tiles:put name="pageTitle" type="string">
			����� ��������
		</tiles:put>


		<!--����-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.pack.list"/>
				<tiles:put name="commandHelpKey"    value="button.pack.list.help"/>
				<tiles:put name="bundle"  value="templatesBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/templates/package/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--������-->                                                                                          
		<tiles:put name="data" type="string">
			<html:hidden property="id"/>
			<tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="certList"/>
                <tiles:put name="text" value="����� ����������"/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="templatesBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="settingsBeforeInf">
                <table cellpadding="0" cellspacing="0">
                <tr>
					<td class="Width120 LabelAll">������������<span class="asterisk">*</span></td>
                    <td><html:text property="field(name)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">��������</td>
					<td><html:text property="field(description)" size="100" maxlength="256" styleClass="contactInput"/>&nbsp;</td>
				</tr>
                </table>
                </tiles:put>
                <tiles:put name="data">

                <c:set var="templates" value="${EditTemplatePackageForm.templates}"/>
				<!-- ��������� ������ -->
				<tr class="tblInfHeader">
					<td width="20px">
						<html:checkbox property="selectAll" style="border:none" onclick="switchSelection('selectAll')"/>
					</td>
					<td width="200px">������������</td>
					<td>��������</td>
					<td width="100px" title="���� ����������">����</td>
				</tr>
				<!-- ������ ������ -->
				<% int lineNumber = 0;%>
				<c:forEach items="${templates}" var="listElement">
					<% lineNumber++;%>
					<tr class="ListLine<%=lineNumber%2%>">
						<td align=center class="ListItem" width="20px">
							<html:multibox property="selectedIds" style="border:none" >
								<bean:write name="listElement" property="id"/>
							</html:multibox>
						</td>
						<td class="ListItem"><bean:write name="listElement" property="name"/>&nbsp;</td>
						<td class="ListItem"><bean:write name="listElement" property="description"/>&nbsp;</td>
						<td class="ListItem" align="center"><bean:write name="listElement" property="update.time" format="dd.MM.yyyy"/>&nbsp;</td>
					</tr>
				</c:forEach>			
                </tiles:put>
                <tiles:put name="isEmpty" value="${empty templates}"/>
                <tiles:put name="emptyMessage" value="��� �������� ����������"/>
            </tiles:insert>
	</tiles:put>
  </tiles:insert>
</html:form>