<%--
  Created by IntelliJ IDEA.
  User: saharnova
  Date: 29.09.14
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/MoneyBox.js"></script>


<html:form styleId="viewMoneyBoxFormId" action="/private/async/moneybox/view" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="moneybox" value="${form.link.value}"/>
    <script type="text/javascript">
        $(document).ready(function()
        {
            win.aditionalData('moneyBoxViewWinDiv', {closeCallback: function ()
            {
                var element = document.getElementById(moneyBoxWinId);
                var paymentId = ${moneybox.id};
                element.dataWasLoaded = false;
                var ajaxUrl = document.getElementById('hiddenAjaxUrl' + moneyBoxWinId);
                var params = '?id=' + paymentId;
                params += '&form=' + formEditName;
                ajaxUrl.value = editDraftClaimUrl + params;
                win.open(moneyBoxWinId);
                return true;
            }});

        });
    </script>
    <tiles:insert definition="resourceNotFoundAsync">
        <tiles:put name="pageTitle" type="string">Ошибка</tiles:put>
        <tiles:put name="needShowMessages" value="false"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orange" />
                <tiles:put name="data">
                    <div class="message">
                        <bean:message bundle="notFoundExceptionMessagesBundle" key="com.rssl.phizic.business.resources.external.AccountLink_For_MoneyBox"/>
                    </div>
                    <div class="clear"></div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>