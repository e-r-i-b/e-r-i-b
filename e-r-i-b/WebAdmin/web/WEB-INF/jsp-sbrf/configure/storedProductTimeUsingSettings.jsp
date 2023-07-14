<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags" %>

<html:form action="/productSettings/configure" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="configEdit"/>
        <tiles:put name="submenu"   type="string" value="StoredProductsTimeConfig"/>
	    <tiles:put name="pageName" type="string" value="Настройка времени актуальности данных"/>
	    <tiles:put name="pageDescription" type="string" value="На этой странице можно изменить период времени, в течение которого сохраненная информация
                    по продуктам клиентов будет использована в клиентском приложении."/>
        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="card.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по картам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="cardInfo.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Дополнительная информация по картам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="loan.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по кредитам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="loanInfo.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Дополнительная информация по кредитам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="account.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по вкладам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="accountInfo.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Дополнительная информация по вкладам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="imaccount.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по металлическим счетам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="depoaccount.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по счетам ДЕПО (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="long.offer.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по длительным поручениям (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="security.account.time2refresh.key"/>
                    <tiles:put name="fieldDescription">Информация по сберегательным сертификатам (указывается в минутах)</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="textSize" value="20"/>
                    <tiles:put name="textMaxLength" value="20"/>
                </tiles:insert>
            </table>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>