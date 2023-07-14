<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@page contentType="text/html;charset=windows-1251" language="java"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"   prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"   prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"  prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"  prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                         prefix="phiz"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"            prefix="c" %>

<html:form action="/sms/csa/settings/list"  onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="configEdit">
        <tiles:importAttribute/>

        <c:set var="imagePath"       value="${skinUrl}/images"/>
        <c:set var="globalImagePath" value="${globalUrl}/images"/>

        <tiles:put name="submenu"   type="string" value="SmsSettingsCSA"/>
        <tiles:put name="pageTitle" type="string">Настройка SMS</tiles:put>
        <tiles:put name="data" type="string">

            <script type="text/javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/sms/csa/settings/edit')}";

                function doEdit()
                {
                    checkIfOneItem("selectedIds");

                    if (!checkOneSelection("selectedIds", "Пожалуйста, выберите одну запись"))
                    {
                        return;
                    }

                    window.location = addUrl + "?id=" + getRadioValue(document.getElementsByName("selectedIds")) + '&field(csaTemplate)=true';
                }
            </script>

            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id"    value="listCSASms"/>
                <tiles:put name="text"  value="Список SMS"/>

                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="CSASmsSettingsEditOperation">
                        <tiles:put name="commandTextKey" value="button.edit"/>
                        <tiles:put name="commandHelpKey" value="button.edit"/>
                        <tiles:put name="bundle"         value="commonBundle"/>
                        <tiles:put name="onclick"        value="doEdit()"/>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="grid">
                    <sl:collection id="item"  property="data" model="list" selectType="checkbox" selectName="selectedIds" selectProperty="id">
                        <sl:collectionItem title="Название" property="description"/>
                    </sl:collection>
                </tiles:put>
            </tiles:insert>

        </tiles:put>
    </tiles:insert>
</html:form>