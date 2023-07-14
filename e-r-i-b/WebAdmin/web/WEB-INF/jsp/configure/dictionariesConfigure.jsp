<%--
  User: osminin
  Date: 18.05.2009
  Time: 10:59:39
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<% pageContext.getRequest().setAttribute("mode","Options");%>
<% pageContext.getRequest().setAttribute("userMode","Dictionaries");%>
<html:form action="/dictionaries/configure" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" value="Dictionaties"/>
        <tiles:put name="pageTitle" type="string">
            Справочники. Настройки
        </tiles:put>
        <tiles:put name="menu" type="string">
	        <tiles:insert definition="commandButton" flush="false" operation="SetDictionariesConfigsOperation">
				<tiles:put name="commandKey"     value="button.cancel"/>
				<tiles:put name="commandHelpKey" value="button.cancel.help"/>
				<tiles:put name="bundle"  value="commonBundle"/>
				<tiles:put name="image"   value="clear.gif"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <script type="text/javascript">
                    function validateForm()
                    {
                        externalSystem = getElementValue('field(externalSystem)');
                        if (externalSystem == null || externalSystem == '')
                        {
                            alert("Выберите внешнюю систему");
                            return false;
                        }
                        return true;
                    }
                    function setExternalSystemInfo ( externalSystem)
                    {
                        setElement('field(externalSystem)',externalSystem["name"]);
                        setElement('field(externalSystemId)',externalSystem["id"]);
                    }
                </script>
                <tiles:put name="id" value=""/>
                <tiles:put name="name" value="Справочники. Настройки"/>
                <tiles:put name="description" value="Используйте форму для задания внешней системы справочникам"/>
                <tiles:put name="data">
                    <tr>
                        <td class="Width120 LabelAll">Внешняя система для загрузки справочников</td>
                        <td>
                            <html:text property="field(externalSystem)" readonly="true" size="30" maxlength="30"/>
                            <input type="button" class="buttWhite smButt" onclick="openAdaptersDictionary(setExternalSystemInfo);" value="..."/>
                            <html:hidden property="field(externalSystemId)"/>
                        </td>
                    </tr>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" operation="SetDictionariesConfigsOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="image"   value="save.gif"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                        <tiles:put name="validationFunction" value="validateForm()"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>