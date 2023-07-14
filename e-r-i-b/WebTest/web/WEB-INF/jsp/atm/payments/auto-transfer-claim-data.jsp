<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>

<tiles:put name="data">
    <c:if test="${not empty document}">
        <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <c:set var="receiver"       value="${document.receiver}"/>
                    <c:set var="autoSubDetails" value="${document.autoSubDetails}"/>

                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="receiverType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="receiverSubType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="receiverName"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="externalCardNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="externalPhoneNumber"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="receiver" beanProperty="toResource"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
                    </tiles:insert>
                    <c:if test="${receiver.receiverType.stringType.value == 'ph'}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="document" beanProperty="messageToReceiver"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="startDate"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="name"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="type"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.eventType"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.amount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="autoSubDetails" beanProperty="always.nextPayDate"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:if>
</tiles:put>
