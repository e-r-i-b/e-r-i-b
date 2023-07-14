var UrlEncodeDecode = {

	// публичная функция для кодирования URL
	encode : function (string) {
		return escape(this._utf8_encode(string));
	},

	// публичная функция для декодирования URL
	decode : function (string) {
		return this._utf8_decode(unescape(string));
	},

	// приватная функция для кодирования URL
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	// приватная функция для декодирования URL
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
        var c;
        var c1;
        var c2;
        var c3;
		c = c1 = c2 = 0;

		while ( i < utftext.length ) {

			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}

		}

		return string;
	}

};
// Данное извращение актуально только для ИЕ6
function IFrameRequest()
{
    if (window.IFrameRequestCounter == undefined)
        window.IFrameRequestCounter = 0;
    this.readyState = 0;
    this.status = 0;
    this.responseText = "";
    window.IFrameRequestCounter++;
    this.req_id = window.IFrameRequestCounter;
    /**
     * метод для импортирвания данных типа param=value&param2=value2.. на форму
      * @param postData данные
     * @param frm форма
     */
    this.preparePostData = function (postData, frm)
    {
        postData = UrlEncodeDecode.decode(postData);
        var items = postData.split('&');
        for (var i=0; i<items.length; i++)
        {
            var item = items[i].split('=',2);
            frm.innerHTML += "<input type='hidden' name='"+item[0]+"' value='"+item[1]+"' />";
        }
    }
};

IFrameRequest.prototype = {
    open: function(protocol, url, async)
    {
        this.protocol = protocol;
        this.url = url;
    },

    onreadystatechange: function()
    {
    },

    send: function(postBody)
    {
        if (postBody == null) postBody = "";
        
        var CONTEINER_ID_NAME = "ieIFrameRequest";
        var IE_ADDRES_LENGTH_LIMIT = 2000;
        var self = this;
        var urlWithParams = this.url;
        if (postBody.length!=0)
        {
            if (this.url.indexOf("?") >= 0)
                urlWithParams += "&";
            else
               urlWithParams += "?";
            urlWithParams += postBody;
        }
        // Кривоватое ограничение на пост.
        // Гетом данный метод хорошо передает русскоязычный текст, а вот пост все ломает, однако на гет в ИЕ есть ограничение, так что используем гет а если не влезает то используем пост.
        var isPost = this.protocol.toUpperCase() == 'POST' && postBody!= null && postBody != '' && urlWithParams.length>IE_ADDRES_LENGTH_LIMIT;
        // необходимо для того чтоби избежать предупреджение о не безопастном контенте
        var  src = "javascript:;";
        if (!isPost)
            src = urlWithParams;

        try
        {
            var frameId = 'req' + this.req_id;

            var requestConteiner = document.getElementById(CONTEINER_ID_NAME);
            if (requestConteiner == null)
            {
                var conteiner = document.createElement("div");
                conteiner.id = CONTEINER_ID_NAME;
                conteiner.style.position = "absolute";
                conteiner.style.height = "1px";
                conteiner.style.weigh = "1px";
                conteiner.style.top = "-20px";
                document.body.appendChild(conteiner);
                requestConteiner = document.getElementById(CONTEINER_ID_NAME);
            }
            // если пост запрос то создаем форму и постим в iframe
            if (isPost)
            {
                requestConteiner.innerHTML += "<iframe name='" + frameId + "' id='" + frameId + "' style='width: 0px; height: 0px; border: 0px; display:none;' src='" + src + "'></iframe>";;
                // создаем форму
                var frm = document.frames[frameId].document.createElement('form');
                frm.id = 'reqPostForm' + this.req_id;
                document.frames[frameId].document.appendChild(frm);
                frm = document.frames[frameId].document.getElementById('reqPostForm' + this.req_id);
                // устанавливаем необходимые параметры в форму
                frm.method = 'post';
                frm.action = this.url;
                frm.target = frameId;
                frm.style.position = "absolute";
                // складируем элементы на форму
                this.preparePostData(postBody, frm);
                // сабмитем форму
                frm.submit();
            }
            // GET - фрейм создаем с помощью createElement, через   innerHTML в IE6 глючит
            else
            {
                var IFrameDoc = document.createElement('iframe');
                IFrameDoc.setAttribute('id', frameId);
                IFrameDoc.setAttribute('name', frameId);
                IFrameDoc.setAttribute('src', src);
                IFrameDoc.style.width = "0";
                IFrameDoc.style.height = "0";
                IFrameDoc.style.border = "0";
                requestConteiner.appendChild(IFrameDoc);
            }
        }
        catch(e)
        {
            return false;
        }

        this.readyState = 1;
        this.onreadystatechange();

        setTimeout(function()
        {
            self.IFht(2);
        }, 2);

    },

    overrideMimeType: function()
    {
    },

    getResponseHeader: function (name)
    {
        return '';
    },

    setRequestHeader: function (name, data)
    {
    },

    IFht: function (d)
    {
        var self = this;
        var el = document.getElementById('req' + self.req_id);
        if (el.readyState == 'complete')
        {
            self.responseText = document.frames['req' + self.req_id].document.body.innerHTML.replace(/[\n\r]+/ig, "");
            el.parentNode.removeChild(el);
            self.status = 200;
            self.readyState = 4;
            self.onreadystatechange();
        }
        else
        {
            d *= 1.5;
            setTimeout(function()
            {
                self.IFht(d);
            }, d);
        }
    }
};
