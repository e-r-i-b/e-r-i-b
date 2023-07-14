<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/crediting/editWaitingTime" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="data" type="string">
            <tiles:put name="tilesDefinition" type="string" value="OKBProviderIdEdit"/>
            <tiles:put name="submenu" type="string" value="WaitingTimeSetting"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="creditingBundle" key="crediting.watingTime.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="creditingBundle" key="crediting.watingTime.message"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="creditingBundle" key="crediting.watingTime"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="com.rssl.iccs.crediting.waitingTime"/>
                                <tiles:put name="textSize" value="32"/>
                                <tiles:put name="textMaxLength" value="32"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditWaitingTimeOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>