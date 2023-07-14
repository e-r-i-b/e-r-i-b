<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>
<c:url var="paymentLink" value="/private/payments/payment.do"/>
<c:url var="servicePayment" value="/private/payments/servicesPayments.do"/>

<div class="titleItems">
    <h2>Переводы</h2>
    <c:if test="${phiz:isNeedToShowP2PPromo()}">
        <img src='${imagePath}/newItem.png' class='ap-newIcon'/>
    </c:if>
</div>
<div class="clear">&nbsp;</div>
<div class="translationsCategories">
   <div class="ap-transfers">
       <div class="paymentsTitles firstPaymentsTitles">
           <tiles:insert definition="paymentTemplate">
               <tiles:put name="title" value="<span>Перевод между своими картами</span>"/>
               <tiles:put name="link" value="${paymentLink}"/>
               <tiles:put name="image" value="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_InternalPayment48.jpg"/>
               <tiles:put name="className" value="categoryTitle paymentLinkWithImage"/>
               <tiles:put name="serviceName" value="CreateP2PAutoTransferClaim"/>
               <tiles:put name="showService" value="${true}"/>
               <tiles:put name="receiverType" value="several"/>
           </tiles:insert>
       </div>
       <div class="paymentsTitles">
           <tiles:insert definition="paymentTemplate">
               <tiles:put name="title" value="Перевод клиенту Сбербанка"/>
               <tiles:put name="link" value="${paymentLink}"/>
               <tiles:put name="image" value="${imagePathGlobal}/translationsPaymentIcons/iconPmntList_RurPaymentSber48.jpg"/>
               <tiles:put name="className" value="categoryTitle paymentLinkWithImage"/>
               <tiles:put name="serviceName" value="CreateP2PAutoTransferClaim"/>
               <tiles:put name="showService" value="${true}"/>
               <tiles:put name="receiverType" value="ph"/>
           </tiles:insert>
       </div>
   </div>
</div>