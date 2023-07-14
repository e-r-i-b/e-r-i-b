<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:importAttribute/>


<tiles:insert page="fields-table.jsp" flush="false">
   <tiles:put name="data">
       <c:set var="receiver" value="${document.receiver}"/>
       <c:set var="autoSubDetails" value="${document.autoSubDetails}"/>
       <c:set var="always" value="${document.autoSubDetails.always}"/>
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
       <c:if test="${not empty receiver.toResource}">
           <tiles:insert page="field.jsp" flush="false">
               <tiles:put name="field" beanName="receiver" beanProperty="toResource"/>
           </tiles:insert>
       </c:if>
       <c:if test="${not empty receiver.externalPhoneNumber}">
           <tiles:insert page="field.jsp" flush="false">
               <tiles:put name="field" beanName="receiver" beanProperty="externalPhoneNumber"/>
           </tiles:insert>
       </c:if>
       <c:if test="${not empty receiver.externalCardNumber}">
           <tiles:insert page="field.jsp" flush="false">
               <tiles:put name="field" beanName="receiver" beanProperty="externalCardNumber"/>
           </tiles:insert>
       </c:if>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="document" beanProperty="fromResource"/>
       </tiles:insert>
       <c:if test="${receiver.receiverType.stringType.value == 'ph' and not empty document.messageToReceiver}">
           <tiles:insert page="field.jsp" flush="false">
               <tiles:put name="field" beanName="document" beanProperty="messageToReceiver"/>
           </tiles:insert>
       </c:if>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="autoSubDetails" beanProperty="longOfferStartDate"/>
       </tiles:insert>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="autoSubDetails" beanProperty="autoSubName"/>
       </tiles:insert>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="autoSubDetails" beanProperty="autoSubType"/>
       </tiles:insert>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="always" beanProperty="longOfferEventType"/>
       </tiles:insert>
       <c:if test="${not empty always.nextPayDate and not empty always.nextPayDate.dateType.value}">
           <tiles:insert page="field.jsp" flush="false">
               <tiles:put name="field" beanName="always" beanProperty="nextPayDate"/>
           </tiles:insert>
       </c:if>
       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="always" beanProperty="sellAmount"/>
       </tiles:insert>

       <tiles:insert page="field.jsp" flush="false">
           <tiles:put name="field" beanName="always" beanProperty="commission"/>
       </tiles:insert>
   </tiles:put>
</tiles:insert>


