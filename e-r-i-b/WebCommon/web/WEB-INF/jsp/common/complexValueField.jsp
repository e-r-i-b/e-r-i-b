<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<div id="${id}Indicator" style="width:${width}px" class="indicator-complexity">
    <c:if test="${notShowIndicator == ''}">
    <div class="state-indicator">
        <div class="left-gray-state">
            <div class="right-gray-state">
                <div class="gray-state margin-state">
                    <div class="fill-state"></div>
                </div>
            </div>
        </div>
    </div>
    </c:if>
    <div class="div-indicator">
    <i class="image-indicator"></i>
    <span class="message-indicator">
    </span>
    </div>
</div>
<div class="clear"></div>

