<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${globalUrl}/commonSkin/images"/>
<tiles:importAttribute/>
 <%--блок с предложением на изменение лимита кредитной карты(на странице передложений банка)--%>
<div class="forLoanOfferBorder">
    <div class="loanOfferImg">
        <img src="${imagePath}/products/creditOffer64.png" onerror="onImgError(this)" border="0"/>
    </div>
    <div class="loanOfferContainer">
        <div class="loanOfferTitle">Ќовый лимит по кредитной карте</div>
        <div class="loanOfferDesc">
            „тобы ¬ы могли еще эффективнее управл€ть своими финансами, мы рассчитали новый кредитный лимит по ¬ашей карте!
        </div>
        <div>
            <tiles:insert definition="clientButton" flush="fase">
                <tiles:put name="commandTextKey" value="button.view"/>
                <tiles:put name="commandHelpKey" value="button.view"/>
                <tiles:put name="bundle" value="loanOfferBundle"/>
                <tiles:put name="action" value="/private/payments/payment.do?form=ChangeCreditLimitClaim"/>
            </tiles:insert>
        </div>
    </div>
</div>
<div class="clear"></div>
