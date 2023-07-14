<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<a href="#" onclick="win.open('removeTargetWindow${target.id}');return false;">
    <bean:message key="label.remove.target.button" bundle="financesBundle"/>
</a>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="removeTargetWindow${target.id}"/>
    <tiles:put name="styleClass" value="confirmWidow"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2>
                <bean:message key="label.remove.target.title" bundle="financesBundle"/>
            </h2>
        </div>

        <div id="message" class="confirmWindowMessage">
            <c:set var="targetName"><span class="word-wrap"><c:out value="«${target.name}»"/></span><br /></c:set>
            <c:set var="targetComment"><span class="descriptionTarget word-wrap"><c:out value="${target.nameComment}"/></span></c:set>
            <bean:message key="label.remove.target.text" bundle="financesBundle" arg0="${targetName} ${targetComment}"/>
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close"/>
                <tiles:put name="commandHelpKey" value="button.close"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('removeTargetWindow${target.id}');"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.remove.confirm"/>
                <tiles:put name="commandHelpKey" value="button.remove.confirm"/>
                <tiles:put name="bundle" value="financesBundle"/>
                <tiles:put name="onclick" value="removeAccountTarget${target.id}();"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="removeTargetResult"/>
    <tiles:put name="styleClass" value="pop-upInformMessage"/>
    <tiles:put name="data">
        <div id="removeTargetResultMessageContainer"></div>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close.window"/>
                <tiles:put name="commandHelpKey" value="button.close.window"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('removeTargetResult');"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>

<c:choose>
    <c:when test="${not empty accountLink and accountLink.account.balance.asCents ne 0}">
        <c:set var="closeAccountUrl">${phiz:calculateActionURL(pageContext, '/private/payments/payment.do')}?form=AccountClosingPayment&fromResource=account:${accountLink.id}</c:set>
        <script type="text/javascript">
            function removeAccountTarget${target.id}()
            {
                window.location = '${closeAccountUrl}';
            }
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="removeTargetUrl">${phiz:calculateActionURL(pageContext, '/private/async/targets/remove.do')}?id=${target.id}</c:set>
        <c:set var="targetListUrl">${phiz:calculateActionURL(pageContext, '/private/finances/targets/targetsList.do')}</c:set>        
        <script type="text/javascript">
            function removeAccountTarget${target.id}()
            {
                ajaxQuery("operation=button.remove&id=${target.id}",'${removeTargetUrl}',
                        function(data)
                        {
                            win.close('removeTargetWindow${target.id}');
                            var state = $(data).find("#responceState").val();
                            if (state == "SUCCESS")
                            {
                                <c:choose>
                                    <c:when test="${isDetailInfoPage}">
                                        window.location = '${targetListUrl}';
                                    </c:when>
                                    <c:otherwise>
                                        window.location.reload();
                                    </c:otherwise>
                                </c:choose>
                                return;
                            }
                            $('#removeTargetResultMessageContainer').html(data);
                            if (state == "ERROR")
                                win.open('removeTargetResult');
                        }
                        );
            }
        </script>
    </c:otherwise>
</c:choose>

