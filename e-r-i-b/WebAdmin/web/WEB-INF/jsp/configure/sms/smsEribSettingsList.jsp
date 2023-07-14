<%@page contentType="text/html;charset=windows-1251" language="java"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>

<html:form action="/sms/erib/settings/list"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="configEdit">
        <tiles:importAttribute/>

        <c:set var="imagePath"       value="${skinUrl}/images"/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>
        <tiles:put name="pageTitle" type="string">Настройка SMS</tiles:put>
        <tiles:put name="submenu"   type="string" value="SmsSettingsErib"/>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/sms/erib/settings/edit')}";

                function doEdit()
                {
                    checkIfOneItem("selectedIds");

                    if (!checkOneSelection("selectedIds", "Пожалуйста, выберите одну запись"))
                    {
                        return;
                    }

                    var radioValue = getRadioValue( document.getElementsByName("selectedIds") );

                    window.location = addUrl + "?id=" + radioValue;
                }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id"    value="listEribSms"/>
                <tiles:put name="text"  value="Список SMS"/>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="SmsSettingsEditOperation">
                        <tiles:put name="commandTextKey" value="button.edit2"/>
                        <tiles:put name="commandHelpKey" value="button.edit2"/>
                        <tiles:put name="bundle"         value="commonBundle"/>
                        <tiles:put name="onclick"        value="doEdit()"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="item" property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                        <sl:collectionItem title="Название">
                            <c:out value="${item.description}"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="Приоритет">
                            <c:out value="${item.priority}"/>
                        </sl:collectionItem>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>
</html:form>