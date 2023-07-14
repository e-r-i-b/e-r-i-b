<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 03.05.2009
  Time: 15:02:31
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/dictionaries/editExternalSystem" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="externalSystemsMain">
    <tiles:put name="mainmenu" value="ExternalSystems"/>
    <tiles:put name="submenu" value="ExternalSystems" type="string"/>
    <tiles:put name="pageTitle" value="Редактирование внешней системы" type="string"/>

    <tiles:put name="menu" type="string">
      <tiles:insert definition="clientButton" flush="false" operation="EditExternalSystemOperation">
         <tiles:put name="commandTextKey" value="button.cancel"/>
         <tiles:put name="commandHelpKey" value="button.cancel.help"/>
         <tiles:put name="bundle"         value="dictionariesBundle"/>
         <tiles:put name="action"         value="/private/externalSystems.do"/>
          <tiles:put name="viewType" value="blueBorder"/>
      </tiles:insert>
   </tiles:put>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="name" value="Редактирование данных внешней системы"/>
            <tiles:put name="description" value="Используйте данную форму для редактирования данных внешней системы"/>
            <tiles:put name="data">
                <tr>
                    <td class="Width120 LabelAll">
                        Наименование
                    </td>
                    <td><html:text property="field(name)" size="30"/></td>
                </tr>
                <tr>
                    <td class="Width120 LabelAll">
                        Адрес шлюза
                    </td>
                    <td><html:text property="field(url)" size="30"/></td>
                </tr>
                <tr>
                    <td class="Width120 LabelAll">
                        Идентификатор
                    </td>
                    <td><html:text property="field(uid)" size="30" disabled="true"/></td>
                </tr>
                <tr>
                    <td class="Width120 LabelAll">
                        <nobr>Номер агентской карты "Город"</nobr>
                    </td>
                    <td><html:text property="field(numberGorod)" size="30"/></td>
                </tr>
                <tr>
                    <td class="Width120 LabelAll">
                        Пароль карты
                    </td>
                    <td><html:text property="field(passwordGorod)" size="30"/></td>
                </tr>
            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false" operation="EditExternalSystemOperation">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"         value="dictionariesBundle"/>
                    <tiles:put name="isDefault"        value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </tiles:put>
    
</tiles:insert>

</html:form>