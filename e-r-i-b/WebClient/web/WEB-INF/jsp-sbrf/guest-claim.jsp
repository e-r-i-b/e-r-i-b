<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".gp-claim:last").addClass("lastClaims");
    });
</script>

<div class="gp-claim">
    <div class="gp-claimIcon floatLeft gp-${type}"></div>
    <div class="gp-claimNameInfo floatLeft">
        <div class="gp-name gp-ref">
            ${name}
        </div>
        <div class="gp-info">
            ${info}
        </div>
    </div>
    <div class="gp-date floatLeft">
    </div>
    <c:if test="${state != ''}">
        <c:choose>
            <c:when test="${isGuest}">
                <div class="gp-g-state floatRight">
                    ${state}
                </div>
            </c:when>
            <c:otherwise>
                <div class="gp-state floatRight">
                    ${state}
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="gp-date floatRight">
        ${date}
    </div>
    <div class="clear"></div>
</div>