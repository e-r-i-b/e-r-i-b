// this script is a local post-update hook script.

var objArgs,num;

objArgs = WScript.Arguments;
num = objArgs.length;
if (num != 5)
{
    WScript.Echo("Usage: [CScript | WScript] PostUpdate.js path/to/pathsfile depth revision error path/to/CWD ");
    WScript.Quit(1);
}

    var root = objArgs(4);
    var srcPath=root + "\\Settings\\configs\\global\\version.properties.template";
    var destFile=root + "\\Settings\\configs\\global\\version.properties";

//    WScript.Echo("srcPath:"+srcPath);
//    WScript.Echo("destFile:"+destFile);

    var objRegExp = new ActiveXObject("VBScript.RegExp");
    objRegExp.Pattern = "%CLIENT_REVISION%";
    objRegExp.Global = true;

	var fs = new ActiveXObject("Scripting.FileSystemObject");
	if (fs.FileExists(srcPath))
	{
		var a = fs.OpenTextFile(srcPath, 1, false);
        var b = fs.CreateTextFile(destFile, true, false);

		var i = 0;
		while (!a.AtEndOfStream)
		{
			var line = a.ReadLine();
            var res = objRegExp.Replace(line,objArgs(2)) ;
            b.WriteLine(res);
//            WScript.Echo("line:"+line +" ,res:"+res);
		}
		a.Close();
        b.Close();
	}

WScript.Quit(0);