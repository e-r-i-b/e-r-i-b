<%--
  User: IIvanova
  Date: 14.02.2006
  Time: 13:50:29
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.getRequest().setAttribute("mode","Options");%>
<% pageContext.getRequest().setAttribute("userMode","Cards");%>
<html:form action="/passwordcards/configure">
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
		<tiles:put name="submenu" type="string" value="Cards"/>
        
       <tiles:put name="pageTitle" type="string">
           ����� ������. ���������
       </tiles:put>
       <tiles:put name="pageDescription" value="����������� ������ ����� ��� ��������� ���� ������."/>
       <tiles:put name="data" type="string">
           <tiles:importAttribute/>
           <c:set var="globalImagePath" value="${globalUrl}/images"/>
           <c:set var="imagePath" value="${skinUrl}/images"/>
             <tr>
                <td class="Width120 LabelAll">�������� ����:</td>
                <td><html:text property="field(com.rssl.iccs.cards.generator.count)" size="4" maxlength="4"/> ���� �� <html:text property="field(com.rssl.iccs.cardpasswords.generator.count)" size="4" maxlength="4"/> ������</td>
             </tr>
			 <tr>
				<td align="right"><img src="${globalImagePath}/help1.gif" alt="" border="0" width="12px" height="12px">&nbsp;</td>
                <td class="pmntInfAreaSignature">
                    ���������� ���� ������, ����������� �� ���� ��������� ���������, � ���������� ������, ����������� �� �����.
                </td>
             </tr>
             <tr>
                <td class="Width120 LabelAll">����������� �������:</td>
                <td><html:select property="field(com.rssl.iccs.card-password-generator.allowed.chars)">
                      <html:option value="0123456789">������ �����</html:option>
                      <html:option value="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ">����� � ��������� �����</html:option>
                      <html:option value="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz">����� � �����</html:option>
                    </html:select>
                </td>
             </tr>
			 <tr>
				<td align="right"><img src="${globalImagePath}/help1.gif" alt="" border="0" width="12px" height="12px">&nbsp;</td>
                <td class="pmntInfAreaSignature">
                    �������, ������������ ��� �������� ������.
                </td>
             </tr>
             <tr>
                <td class="Width120 LabelAll">����� �����:</td>
                <td><html:text property="field(com.rssl.iccs.card-password-generator.password.length)" size="2" maxlength="2"/> ��������</td>
             </tr>
			 <tr>
				<td align="right"><img src="${globalImagePath}/help1.gif" alt="" border="0" width="12px" height="12px">&nbsp;</td>
                <td class="pmntInfAreaSignature">
                    ����� ����� �� �����.
                </td>
             </tr>
        </tiles:put>
		<tiles:put name="formAlign">
			 <tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="isDefault" value="true"/>
				<tiles:put name="postbackNavigation" value="true"/>
			</tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="onclick" value="resetForm(event)"/>
		    </tiles:insert>
		</tiles:put>
		<tiles:put name="formAlign" value="center"/>
	</tiles:insert>
</html:form>