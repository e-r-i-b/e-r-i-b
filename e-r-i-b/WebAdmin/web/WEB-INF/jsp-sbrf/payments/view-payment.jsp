<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/view">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:if test="${phiz:isInstance(frm.document, 'com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription') and frm.document.longOffer}">
        <jsp:include page="./subscriptions/support-payment-${frm.metadata.name}.jsp" flush="false" />
    </c:if>

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" type="string" value="${subMenu}"/>

		<tiles:put name="menu" type="string">
			<script type="text/javascript">
                function openMessageLogForDocument()
                {
                    openMessageLogList(getElementValue("operationUID"));
                }

				function selectPrintedDocuments(event)
				{
					var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
					             ", width=400" +
					             ", height=400" +
					             ", left=0" + ((screen.width) / 2 - 100) +
					             ", top=0" + ((screen.height) / 2 - 100);
					var pwin = openWindow(event, '${phiz:calculateActionURL(pageContext,'/claims/printDocumentsList.do')}?paymentsId=${frm.id}', "dialog", winpar);
					pwin.focus();
				}

                function confirmPrint(event)
                 {
                    var h = 600;
                    var w = 500;
                    var winpar = "fullscreen=0,location=0,menubar=0,status=0,toolbar=0,resizable=1" +
                              ", width=" + w + ", height=" + h +
                              ", left=" + (getClientWidth() - w) / 2 + ", top=" + (getClientHeight() - h) / 2;
                    var win = openWindow(null, "${phiz:calculateActionURL(pageContext, '/loans/claims/claim/print.do')}?id=${frm.id}", "dialog", winpar);
                    win.focus();
                 }
                function printStatement(event)
                {
                    <c:choose>
                        <c:when test="${isEditClaim}">
                            <c:set var="url" value="/private/payments/print-edit-claim.do"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="url" value="/private/payments/print.do"/>
                        </c:otherwise>
                    </c:choose>
                    openWindow(event, "${phiz:calculateActionURL(pageContext, url)}?id=${frm.id}");
                }
			</script>

            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/backToSubscriptionListButton.jsp" %>
		</tiles:put>

		<tiles:put name="data" type="string">
            <c:set var="document" value="${frm.document}"/>

            <input type="hidden" name="id" value="${document.id}"/>
            <input type="hidden" name="operationUID" value="${document.operationUID}"/>

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="audit_document"/>
                <tiles:put name="name" value="${title}"/>
                <tiles:put name="data">
                    ${frm.html}

                    <div class="form-row-addition">
                        <div class="paymentLabel">Ф.И.О. клиента:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${frm.owner.fullName}"/></b></div></div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row-addition">
                        <div class="paymentLabel">Идентификатор:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${frm.owner.id}"/></b></div></div>
                        <div class="clear"></div>
                    </div>
                    <div class="form-row-addition">
                        <div class="paymentLabel">Офис банка:</div>
                        <div class="paymentValue"><div class="paymentInputDiv"><b><c:out value="${frm.department.name}"/></b></div></div>
                        <div class="clear"></div>
                    </div>
                    <c:if test="${not empty frm.document.state.code and frm.document.state.code =='REFUSED'
                                and not empty frm.document.refusingReason
                                and (phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                                or phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">
                        <div class="form-row-addition">
                            <div class="paymentLabel">Статус:</div>
                            <div class="paymentValue">
                                <div class="paymentInputDiv">
                                    <b>
                                        <c:out value="${frm.document.refusingReason}"/>
                                    </b>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                    <c:if test="${phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.BusinessDocumentBase')}">
                        <div class="form-row-addition">
                            <div class="paymentLabel">Дата изменения статуса:</div>
                            <div class="paymentValue">
                                <div class="paymentInputDiv">
                                    <b>
                                        <bean:write name="document" property="changed" format="dd.MM.yyyy"/>
                                        <bean:write name="document" property="changed" format="HH:mm:ss"/>
                                    </b>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </c:if>
                </tiles:put>
					<tiles:put name="buttons">
                        <tiles:insert definition="clientButton" service="${serviceName}" flush="false">
                            <tiles:put name="commandTextKey"    value="button.print"/>
                            <tiles:put name="commandHelpKey"    value="button.print.help"/>
                            <tiles:put name="bundle"            value="autopaymentsBundle"/>
                            <tiles:put name="onclick"           value="printStatement(event);"/>
                        </tiles:insert>

					</tiles:put>
					<tiles:put name="alignTable" value="center"/>
				</tiles:insert>
				<script>
					if (document.getElementById("headerClaim") && document.getElementById("bodyClaim"))
                    {
						var width = document.getElementById("bodyClaim").offsetWidth - 36;
						document.getElementById("headerClaim").style[width] = width;
					}
				</script>
			</tiles:put>
	</tiles:insert>

</html:form>

