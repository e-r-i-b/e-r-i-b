<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="${id}"/>
    <tiles:put name="data">
        <input type="hidden" name="field(failureIMSICheck)" value="true"/>

        <div class="sendEmailTitle" style="padding-top:20px;">
            <h1>Обнаружена замена SIM-карты на телефоне клиента. Выслать новый пароль?</h1>
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.ignoreIMSICheck"/>
                <tiles:put name="commandHelpKey" value="button.ignoreIMSICheck"/>
                <tiles:put name="bundle" value="personsBundle"/>
            </tiles:insert>

            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.no"/>
                <tiles:put name="commandHelpKey" value="label.no"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="win.close('${id}');"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>