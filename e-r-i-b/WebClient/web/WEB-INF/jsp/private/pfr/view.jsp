<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/private/pfr/statement">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="pfrBundle"/>
    <tiles:insert definition="printDoc" flush="false">
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function print(event)
                {
                    var url = "${phiz:calculateActionURL(pageContext,'/private/pfr/print.do')}";
                    var params = "?id=" + ${form.claimId};
                    var winpar = "fullscreen=0,location=0,menubar=1,status=0,toolbar=0,resizable=1" +
					             ", width=600" +
					             ", height=500" +
					             ", left=0" + ((screen.width) / 2 - 100) +
					             ", top=0" + ((screen.height) / 2 - 100);

                    openWindow(event, url + params, "PrintInformation", winpar);
                }
            </script>
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Выписка из пенсионного фонда"/>
                <tiles:put name="data" type="string">
                    <div id="pfrAbstract">
                        <div class="pfrHeader">
                            <div class="pfrInfo">
                                <h1>Пенсионный фонд</h1>
                                <br/>
                                <span class="pfrNumber">${form.SNILS}</span>
                                <span class="pfrDate bold">Дата запроса:&nbsp;<span class="bold">&nbsp;${phiz:formatDateWithStringMonth(form.claimDate)}</span></span>
                            </div>
                        </div>

                        ${form.html}
                        <div class="clear"></div>
                        <tiles:insert definition="roundBorder" flush="false">
                            <tiles:put name="color" value="hint"/>
                            <tiles:put name="data">
                                <div class="infoContainer">
                                    Для того чтобы получить детальную выписку, зайдите на сайт <a class="sbrfLink" href="http://www.pfrf.ru" target="_blank">http://www.pfrf.ru</a> или обратитесь в территориальный орган Пенсионного фонда РФ по месту жительства.
                                </div>
                                <div class="clear"></div>
                            </tiles:put>
                        </tiles:insert>

                        <div class="buttonsArea">
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.close"/>
                                <tiles:put name="commandHelpKey" value="button.close"/>
                                <tiles:put name="bundle" value="${bundle}"/>
                                <tiles:put name="viewType" value="buttonGrey"/>
                                <tiles:put name="onclick">window.close();</tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="clientButton" flush="false">
                                <tiles:put name="commandTextKey" value="button.print"/>
                                <tiles:put name="commandHelpKey" value="button.print.help"/>
                                <tiles:put name="bundle" value="${bundle}"/>
                                <tiles:put name="onclick">print(event);</tiles:put>
                            </tiles:insert>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>