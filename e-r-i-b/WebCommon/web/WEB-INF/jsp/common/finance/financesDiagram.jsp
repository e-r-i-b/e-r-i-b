<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="contextName" value="${phiz:loginContextName()}"/>
<c:set var="showCreditCardAmountId" value="${diagramId}ShowCreditCardAmount"/>
<c:set var="showCents" value="${diagramId}ShowCents"/>

<input id="${showCreditCardAmountId}" type="checkbox" name="showCreditCardAmount" checked="true"  />
    учитывать кредитные средства
<br/>
<input id="${showCents}" type="checkbox" name="showCents"/>
    отображать копейки
<br/>

<c:set var="plotId" value="${diagramId}Plot"/>
<c:set var="legendId" value="${diagramId}Legend"/>
<c:set var="labelId" value="${diagramId}Label"/>
<c:set var="totalBalanceAmountId" value="${diagramId}TotalBalanceAmount"/>
<c:set var="titleBoxOtherPercentId" value="${diagramId}TitleBoxOtherPercent"/>
<c:set var="percentBoxOtherPercentId" value="${diagramId}PercentBoxOtherPercent"/>
<c:set var="otherProductListId" value="${diagramId}OtherProductList"/>
<c:set var="titleBoxId" value="${diagramId}TitleBox"/>
<c:set var="percentBoxId" value="${diagramId}PercentBox"/>
<c:set var="titleBoxTextId" value="${diagramId}TitleBoxText"/>
<c:set var="otherProductBlockId" value="${diagramId}OtherProductBlock"/>

<div id="${plotId}" style="width:350px; height:350px;float:left;overflow:visible;"></div>
<div id="${labelId}" class="financeLegendBlock" style="float:right; overflow:visible;">
    <div class="totalBalance">
        <div>Всего доступно:</div>
        <div id="${totalBalanceAmountId}" class="totalAmount"></div>
    </div>
    <div id="${legendId}" class="financeLegend">
    </div>
</div>