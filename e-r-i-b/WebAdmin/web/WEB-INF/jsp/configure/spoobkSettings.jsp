<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/spoobk/configure" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="configEdit">
        <tiles:put name="submenu" type="string" value="SPOOBKConfig"/>
        <tiles:put name="data" type="string">
            <tiles:importAttribute/>
            <c:set var="globalImagePath" value="${globalUrl}/images"/>
            <c:set var="imagePath" value="${skinUrl}/images"/>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Настройки СПООБК"/>
                <tiles:put name="description" value="Используйте форму для изменения настроек СПООБК."/>
                <tiles:put name="data">
                    <tr>
                        <td class="Width200 LabelAll">Имя таблицы в СПООБК:</td>
                        <td>&nbsp;<html:text property="field(com.rssl.phizic.web.configure.spoobk_table_name)" size="35" maxlength="30"/></td>
                    </tr>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" service="SPOOBKConfigureService">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false" service="SPOOBKConfigureService">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                   <tiles:insert definition="commandButton" flush="false">
                       <tiles:put name="commandKey" value="button.save"/>
                       <tiles:put name="commandHelpKey" value="button.save.help"/>
                       <tiles:put name="bundle" value="commonBundle"/>
                       <tiles:put name="isDefault" value="true"/>
                       <tiles:put name="postbackNavigation" value="true"/>
                   </tiles:insert>
                   <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.cancel"/>
                       <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                       <tiles:put name="bundle" value="commonBundle"/>
                       <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                   </tiles:insert>
               </tiles:put>

                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>