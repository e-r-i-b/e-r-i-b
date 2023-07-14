<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 29.04.2009
  Time: 15:23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/systemSettings/configure"  onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="configEdit">
    
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:put name="submenu" type="string" value="SysSettings"/>
	<tiles:put name="pageTitle" type="string">
        Параметры системы. Настройки
	</tiles:put>

      <tiles:put name="data" type="string">
	   <tiles:insert definition="paymentForm" flush="false">
		<tiles:put name="id" value=""/>
		<tiles:put name="name" value=" Параметры системы. Настройки."/>
		<tiles:put name="description" value="Используйте форму для изменения настроек системы."/>
		<tiles:put name="data">
             <tr>
                <td class="Width200 LabelAll">Уровень иерархии подразделений:</td>
                <td>&nbsp;<html:text property="field(departmentAllowedLevel)" size="5" maxlength="10"/>
                </td>
	         </tr>
			 <tr>
				<td align="right"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px">&nbsp;</td>
                <td class="pmntInfAreaSignature">
                    Уровень иерархии подразделений
                </td>
             </tr>
             <tr>
                 <td class="Width200 LabelAll">Срок хранения заявок:</td>
                 <td>&nbsp;<html:text property="field(claimWorkingLife)" size="5" maxlength="2"/></td>
             </tr>
             <tr>
				 <td align="right"><img src="${imagePath}/info.gif" alt="" border="0" width="12px" height="12px">&nbsp;</td>
                 <td class="pmntInfAreaSignature">
                    По истечении указанного количества дней после отправки заявки в банк она будет автоматически удалена из системы
                 </td>
             </tr>
            </tiles:put>
				<tiles:put name="buttons">
					<tiles:insert definition="commandButton" flush="false" service="AdminSystemSettingsManagement">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
					    <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" service="AdminSystemSettingsManagement">
                        <tiles:put name="commandTextKey"     value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                    </tiles:insert>
			</tiles:put>
			<tiles:put name="alignTable" value="center"/>
		 </tiles:insert>
        </tiles:put>

</tiles:insert>

</html:form>