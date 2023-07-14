<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/ermb/settings/subscribeFee" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="data" type="string">
            <tiles:put name="tilesDefinition" type="string" value="ermbSubscriptionFeeSettings"/>
            <tiles:put name="submenu" type="string" value="ErmbSubscribeFeeSettings"/>

            <c:set var="parameter" value="com.rssl.iccs.ermb.settings.subscribeFee"/>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.title"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.desc"/></tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppUnloadTime"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppUnloadTime"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="${parameter}.fppUnloadTime"/>
                                <tiles:put name="fieldType" value="textarea"/>
                                <tiles:put name="textMaxLength" value="60"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppProcNumber"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppProcNumber"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="${parameter}.fppProcTotalCount"/>
                                <tiles:put name="fieldType" value="radio"/>
                                <tiles:put name="selectItems" value="1@1|5@5|10@10|50@50|100@100"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppPath"/>
                        </tiles:put>
                        <tiles:put name="description">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.fppPath"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="${parameter}.fppPath"/>
                                <tiles:put name="textSize" value="40"/>
                                <tiles:put name="textMaxLength" value="100"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <%--BUG089151 нужно уточнять требование по этой настройке и либо сносить ее совсем, либо переделывать блокировку--%>

                    <%--<tiles:insert definition="simpleFormRow" flush="false">--%>
                        <%--<tiles:put name="title">--%>
                            <%--<bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.waitingDaysNumber"/>--%>
                        <%--</tiles:put>--%>
                        <%--<tiles:put name="description">--%>
                            <%--<bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.waitingDaysNumber.description"/>--%>
                        <%--</tiles:put>--%>
                        <%--<tiles:put name="isNecessary" value="true"/>--%>
                        <%--<tiles:put name="needMargin" value="true"/>--%>
                        <%--<tiles:put name="data">--%>
                            <%--<tiles:insert definition="propertyInput" flush="false">--%>
                                <%--<tiles:put name="fieldName" value="${parameter}.waitingDaysNumber"/>--%>
                                <%--<tiles:put name="textSize" value="40"/>--%>
                                <%--<tiles:put name="textMaxLength" value="100"/>--%>
                            <%--</tiles:insert>--%>
                        <%--</tiles:put>--%>
                    <%--</tiles:insert>--%>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="ermbBundle" key="ermb.settings.subscribeFee.maxTransactionCount"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <tiles:insert definition="propertyInput" flush="false">
                                <tiles:put name="fieldName" value="${parameter}.maxTransactionCount"/>
                                <tiles:put name="textSize" value="40"/>
                                <tiles:put name="textMaxLength" value="100"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false"
                          operation="SubscribeFeeSettingsEditOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false"
                          operation="SubscribeFeeSettingsEditOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="window.location.reload();"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>
