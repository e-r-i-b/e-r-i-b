<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<div class="enterBlock" 
     <c:if test="${not empty id}">id="${id}"</c:if>
     <c:if test="${empty display || !display}">style="display:none"</c:if>>

    <div class="workspace-box crystalTop">
        <c:if test="${not empty title}">
            <div class="crystalTopRTL r-top-left title-auth-block-show float"></div>
            <div class="crystalTopRTR r-top-right floatRight"></div>
            <div class="crystalTopRTC r-top-center ">
                ${title}
                <div class="clear"></div>
            </div>
        </c:if>
        <c:if test="${empty title}">
            <div class="whiteTopRT r-top title-auth-block-hide">
                <div class="whiteTopRTL r-top-left">
                    <div class="whiteTopRTR r-top-right">
                        <div class="whiteTopRTC r-top-center"></div>
                    </div>
                </div>
            </div>
        </c:if>
        
        <div class="crystalTopRCL r-center-left">
            <div class="crystalTopRCR r-center-right">
                <div class="crystalTopRC r-content">
                     <tiles:insert attribute="data"/>
                </div>
            </div>
        </div>
        <div class="crystalTopRBL r-bottom-left">
            <div class="crystalTopRBR r-bottom-right">
                <div class="crystalTopRBC r-bottom-center"></div>
            </div>
        </div>
    </div>
    <div id="blocking-message-restriction-block">
        <c:if test="${not empty blockingMessage}">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="yellowTop"/>
                <tiles:put name="data" type="string">
                    <c:out value="${blockingMessage}"/>
                </tiles:put>
            </tiles:insert>
        </c:if>
    </div>
</div>