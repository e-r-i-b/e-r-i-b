<%@ page import="com.rssl.phizic.web.util.PersonSettingsUtil" %>
<%@ page import="com.rssl.phizic.business.persons.PersonHelper" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-34229649-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

 <%-- Маркировка DMP-ЕРИБ Pixel для закрытой части--%>
<c:if test="${needPixelMetric == 'true'}">
  <c:set var="url" value="<%= PersonSettingsUtil.getUrlMetricPixel() %>"/>
  <c:set var="clientId" value="<%= PersonHelper.getCryptoClientId() %>"/>
  <c:if test="${url != null && clientId != null}">
      var img = document.createElement('img');
      img.width = 1;
      img.height = 1;
      img.style.display = 'none';
      img.src = "https://${url}/img/p.png?pid=${clientId}&r=" + Math.random();
      document.body.insertBefore(img, document.body.firstChild);
  </c:if>
</c:if>

<!-- Yandex.Metrika counter -->
(function (d, w, c) {
    (w[c] = w[c] || []).push(function() {
        try {
            w.yaCounter16643608 = new Ya.Metrika({id:16643608, enableAll: true});
        } catch(e) { }
    });

    var n = d.getElementsByTagName("script")[0],
        s = d.createElement("script"),
        f = function () { n.parentNode.insertBefore(s, n); };
    s.type = "text/javascript";
    s.async = true;
    s.src = (d.location.protocol == "https:" ? "https:" : "http:") + "//mc.yandex.ru/metrika/watch.js";

    if (w.opera == "[object Opera]") {
        d.addEventListener("DOMContentLoaded", f);
    } else { f(); }
})(document, window, "yandex_metrika_callbacks");
</script>
<noscript><div><img src="//mc.yandex.ru/watch/16643608" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
<!-- /Yandex.Metrika counter -->