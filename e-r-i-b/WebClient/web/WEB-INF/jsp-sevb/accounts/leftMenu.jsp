<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">
   document.imgPath="${imagePath}/";
</script>
<br/>
<span class="infoTitle backTransparent">Счета и карты</span>
<br/>
<table cellspacing="2" cellpadding="4" class="linkTab backTransparent" width="80%">
	
<c:if test="${phiz:impliesOperation('GetAccountAbstractOperation','Abstract')}">
	<tr>
		<td>
			<phiz:menuLink action="/private/accounts/abstract"param="list=accounts" serviceId="Abstract" id="f1">
			 Выписка по счетам<br>
			</phiz:menuLink>
		</td>
	</tr>
</c:if>
<c:if test="${phiz:impliesOperation('GetAccountAbstractExtendedOperation','PrintAccountAbstract')}">
	<tr>
		<td>
			<phiz:menuLink action="/private/accounts/abstract" param="list=all&copying=true"  serviceId="PrintAccountAbstract"  id="f2">
			 Справка о состоянии вклада<br>
			</phiz:menuLink>

		</td>
	</tr>
</c:if>
<c:if test="${phiz:impliesOperation('GetCardAbstractOperation','Abstract') and phiz:userHasCards()}">
	<tr>
	   <td>
		   <phiz:menuLink action="/private/accounts/abstract" param="list=cards" serviceId="Abstract" id="f3">
		   Выписка по картам<br>
		   </phiz:menuLink>
	   </td>
	</tr>
</c:if>
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment" param="form=LossPassbookApplication" serviceId="LossPassbookApplication" id="f4">
        Блокировка счета<br>
        </phiz:menuLink>
    </td>
</tr>
<c:if test="${phiz:userHasCards()}">
<tr>
    <td>
        <phiz:menuLink action="/private/payments/payment"param="form=BlockingCardClaim" serviceId="BlockingCardClaim"id="f5">
        Блокировка карты<br>
        </phiz:menuLink>
    </td>
</tr>
</c:if>    
</table>
