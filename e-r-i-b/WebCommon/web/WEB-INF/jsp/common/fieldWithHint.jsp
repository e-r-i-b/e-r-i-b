<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute/>

<div class="form-row" id="${externalId}Row" onclick="payInput.onClick(this)">
    <div class="paymentLabel">
        <span class="paymentTextLabel">${fieldName}</span><c:if test="${required}"><span class="asterisk" id="asterisk_${externalId}">*</span></c:if>
    </div>
    <div class="paymentValue">
        <div class="paymentInputDiv" onkeyup="${onKeyUp}">
            ${data}
        </div>
        <div style="display: none" class="description">
            ${description}
            <c:if test="${not empty hint}">
                <a href="#" onclick="payInput.openDetailClick(this); return false;">
                <c:choose>
                    <c:when test="${empty description}">
                        Как заполнить это поле?
                    </c:when>
                    <c:otherwise>
                        Подробнее...
                    </c:otherwise>
                </c:choose>
                </a>
                <div class="detail" style="display: none">${hint}</div>
            </c:if>
        </div>
        <div style="display: none;" class="errorDiv"></div>
    </div>
     <div class="clear"></div>
 </div>
<script type="text/javascript">
    if (document.getElementsByName("${externalId}").length == 1)
    document.getElementsByName("${externalId}")[0].onfocus = function() {
            payInput.onFocus(this); }
</script>


