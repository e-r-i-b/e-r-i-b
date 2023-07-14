<%--
  Created by IntelliJ IDEA.
  User: Kosyakova
  Date: 24.11.2006
  Time: 15:48:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.getRequest().setAttribute("mode","Options");%>
<% pageContext.getRequest().setAttribute("userMode","Dictionaries");%>
<html:form action="/dictionaries/configureContact">
    <tiles:insert definition="configEdit">
		<tiles:put name="submenu" type="string" value="DictionariesContact"/>
	   <tiles:put name="pageTitle" type="string">
           Справочники CONTACT. Настройки
       </tiles:put>

       <tiles:put name="data" type="string">
       <tiles:insert definition="paymentForm" flush="false">
			<tiles:put name="id" value=""/>
			<tiles:put name="name" value="Справочники CONTACT"/>
			<tiles:put name="description" value="Используйте данную форму для настройки справочников CONTACT."/>
			<tiles:put name="data">
             <tr>
                <td>Каталог для загрузки справочников CONTACT</td>
	         </tr>
			 <tr>
                <td><html:text property="field(contactDictionariesPath)" size="100"/></td>
             </tr>
			 <tr>
                <td class="pmntInfAreaSignature">
                  <img src="${initParam.resourcesRealPath}/images/help1.gif" alt="" border="0" width="11px" height="11px">
                   Каталог, из которого реплицируются справочники системы CONTACT.
                </td>
             </tr>
         </tiles:put>
		 <tiles:put name="buttons">
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
		<tiles:put name="alignTable" value="center"/>
	</tiles:insert>
    </tiles:put>
    </tiles:insert>
</html:form>