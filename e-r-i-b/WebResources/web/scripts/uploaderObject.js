/*
 * Объект-загрузчик файла на сервер.
 * Передаваемые параметры:
 * file       - объект File (обязателен)
 * url        - строка, указывает куда загружать (обязателен)
 * fieldName  - имя поля, содержащего файл (как если задать атрибут name тегу input)
 * onprogress - функция обратного вызова, вызывается при обновлении данных
 *              о процессе загрузки, принимает один параметр: состояние загрузки (в процентах)
 * oncopmlete - функция обратного вызова, вызывается при завершении загрузки, принимает два параметра:
 *              uploaded - содержит true, в случае успеха и false, если возникли какие-либо ошибки;
 *              data - в случае успеха в него передается ответ сервера
 *              
 *              если в процессе загрузки возникли ошибки, то в свойство lastError объекта помещается
 *              объект ошибки, содержащий два поля: code и text
 */

var uploaderObject = function(params) {

    if(!params.file || !params.url) {
        return false;
    }

    this.xhr = new XMLHttpRequest();
    this.reader = new FileReader();

    this.progress = 0;
    this.uploaded = false;
    
    var self = this;    

    self.reader.onload = function() {
        self.xhr.upload.addEventListener("progress", function(e) {
            if (e.lengthComputable) {
                self.progress = (e.loaded * 100) / e.total;
                if(params.onprogress instanceof Function) {
                    params.onprogress.call(self, Math.round(self.progress));
                }
            }
        }, false);

        self.xhr.upload.addEventListener("load", function(){
            self.progress = 100;
            self.uploaded = true;
        }, false);

        self.xhr.onreadystatechange = function () {
            var callbackDefined = params.oncomplete instanceof Function;
            if (this.readyState == 4) {
                if(this.status == 200) {
                    if(!this.responseText) {
                        if(callbackDefined) {
                            params.oncomplete.call(self, false);
                        }
                    } else {
                        if(callbackDefined) {
                            params.oncomplete.call(self, true, this.responseText);
                        }
                    }
                    self.progress = 100;
                    self.uploaded = true;
                } else {
                    if(callbackDefined) {
                        params.oncomplete.call(self, false);
                    }
                }
            }
        };

        self.xhr.open("POST", params.url);

        var boundary = "userImage";
        self.xhr.setRequestHeader("Content-Type", "multipart/form-data;boundary="+boundary);
        self.xhr.setRequestHeader("Cache-Control", "no-cache");

        var body = "--" + boundary + "\r\n";
        body += "Content-Disposition: form-data; name=state;\r\n\r\n";
        body += params.state + "\r\n";
        body += "--" + boundary + "\r\n";
        body += "Content-Disposition: form-data; name="+(params.fieldName || 'file')+"; filename=" + params.file.name + "\r\n";
        body += "Content-Type: "+params.file.type+"\r\n\r\n";
        body += self.reader.result + "\r\n";
        body += "--" + boundary + "--";

        self.xhr.send(body);
    };

    self.reader.readAsDataURL(params.file);
};

/**
 * Загрузка файлов через IE.
 *
 * @param res
 * @constructor
 */
var IEUploader = function (res) {
    var self = this;

    self.name = "ieuploader" + Math.round(Math.random()*1000000);

    var ifrm = document.createElement('iframe');
    ifrm.setAttribute("name", self.name);
    ifrm.setAttribute("id", self.name);
    var html = '<body><form method="POST" action="' + res.url + '" enctype="multipart/form-data" encoding="multipart/form-data">'+
               '<input type="hidden" name="state" id="formState' + self.name + '" value="' + res.state + '">'+
               '<input type="file" name="' + res.fieldName + '" class="' + res.fieldName + '" '+
               'onchange="window.parent.'+ res.name +'.changeFileInput();" accept="' + res.acceptFile + '">'+
               '<input type="submit" id="submit' + self.name + '">'+
               '</form></body>';
    self.onComplete = res.oncomplete;
    self.onUpload = res.onupload;
    self.windowSerialId = res.windowSerialId;
    ifrm.style.position="absolute";ifrm.style.width=100;ifrm.style.height=100;ifrm.style.left=-10000;ifrm.src='about:blank';
    document.body.appendChild(ifrm);
    var ifrmDoc=ifrm.contentDocument?ifrm.contentDocument:(ifrm.contentWindow?ifrm.contentWindow.document:(window.frames[ifrm.name].document));
    if (ifrmDoc) {ifrmDoc.open();ifrmDoc.write(html);ifrmDoc.close();}

    this.getUploader = function ()
    {
        return $(window.frames[self.name].document.body);
    }

    this.changeFileInput = function ()
    {
        self.uploadFile(self.getUploader().find("input.avatarFile").val().replace( "C:\\fakepath\\", '' ));
    }

    var reloadPageInterval;
    this.uploadFile = function (file)
    {
        self.onUpload(file);
        self.getUploader().find("#formState" + self.name).val("upload");
        self.reloadPageInterval = setInterval(self.waitForResult, 100);
        self.getUploader().find("#submit" + self.name).click();
    }

    this.waitForResult = function ()
    {
        var data = self.getUploader().html();
        if (data.indexOf(self.name) < 0)
        {
            self.onComplete(true, data);
            clearInterval(self.reloadPageInterval);
        }
    }
};


function testExtendedFileUpload()
{
    return !IE() && (typeof FileReader != "undefined");
}
