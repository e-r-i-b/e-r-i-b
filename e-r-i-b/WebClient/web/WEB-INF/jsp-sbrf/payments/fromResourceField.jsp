<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<div class="fullWidth">
    <div id="resourceSelectRow" class="form-row">
       <div class="paymentLabel">
         <span class="paymentTextLabel" id="accountLabel"></span><span id="asterisk_" class="asterisk">*</span>:
       </div>
       <div class="paymentValue">
          <div class="paymentInputDiv">
              <html:select name="form" property="fromResource" styleId="fromResource" value="${form.fromResource}">
                <html:option value="">Выберите счет/карту списания</html:option>
                <c:forEach items="${form.chargeOffResources}" var="resource" >
                        <html:option value="${resource.code}" >
                        <c:choose>
                            <c:when test="${resource['class'].name == 'com.rssl.phizic.business.resources.external.CardLink'}">
                                <c:out value="${phiz:getCutCardNumber(resource.number)} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${resource.number} [${resource.name}] ${resource.rest.decimal} ${phiz:getCurrencySign(resource.currency.code)}"/>
                            </c:otherwise>
                        </c:choose>
                        </html:option>
                </c:forEach>
            </html:select>
            <div class="description" style="display: none;"></div>
            <div class="errorDiv" style="display: none;"></div>
          </div>
       </div>
       <div class="clear"></div>
    </div>
</div>
