<!DOCTYPE html>
<html>
<head>
    <title>单文件普通上传</title>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link rel="icon" href="/static/icon/favicon.ico" type="image/x-icon">
    <link type="text/css" href="/static/js/webuploader-0.1.5/webuploader.css" rel="stylesheet"/>
    <link type="text/css" href="/static/js/bootstrap-3.3.0/dist/css/bootstrap.css" rel="stylesheet"/>

    <script type="text/javascript" src="/static/js/jQuery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/static/js/common/common.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap-3.3.0/dist/js/bootstrap.js"></script>
    <script type="text/javascript" src="/static/js/webuploader-0.1.5/webuploader.js"></script>

</head>
<body>

    <div id="uploader" class="wu-example">
        <!--用来存放文件信息-->
        <div id="thelist" class="uploader-list"></div>
        <div class="btns">
            <div id="picker">选择文件</div>
            <button id="ctlBtn" class="btn btn-default" disabled>开始上传</button>
        </div>
    </div>
    <script type="text/javascript">
        $(function(){
            var $ = jQuery,
                    $list = $('#thelist'),
                    $btn = $('#ctlBtn'),
                    state = 'pending',
                    uploader,
                    md5Encrypt;

            uploader = WebUploader.create({

                // swf文件路径
                swf: '/static/js/webuploader-0.1.5/Uploader.swf',

                // 文件接收服务端。
                server: basePath + '/file/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {
                    id : "#picker",
                    multiple : false //是否开启选择多个文件能力
                },

                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,

                chunked : false, //是否分片处理文件

                compress: null,//图片不压缩

                fileNumLimit : 1,
            });

            // 当有文件添加进来的时候
            uploader.on("fileQueued", function(file) {
                $list.append( '<div id="' + file.id + '" class="item">' +
                        '<h4 class="info">' + file.name + '</h4>' +
                        '<p class="state">等待上传...</p>' +
                        '</div>' );

                var aa = uploader.md5File(file);
                aa.then(function(res){
                    $btn.removeAttr("disabled");
                    console.log("md5值是->",res);
                    console.log(file);
                    uploader.option("formData",{ //设置uploader的属性
                        md5Encrypt : res
                    });
                    md5Encrypt = res;
                });

            });


            $btn.on( 'click', function() {
                uploader.upload();//开始上传文件
            });

            uploader.on("uploadFinished", function () {//文件上传结束触发
                var errArr = uploader.getFiles('error');
                console.log("文件上传结束", errArr);
            });

            uploader.on("uploadError", function (file, reason) {//文件上传出错
                console.log("文件上传出错", reason);
            });

            uploader.on("uploadSuccess", function (file, response) {//文件上传成功
                console.log("文件上传成功",file);
                console.log("文件上传成功",response);
            });

            uploader.on("uploadComplete", function (file) {//文件上传结束触发
                console.log("文件上传结束！", file);
            });

            uploader.on('error', function(handler) {
                console.log("捕捉的异常信息",handler);
                if ("Q_EXCEED_NUM_LIMIT" == handler) {
                    alert("只能选择一个文件！");
                }
            });
        });
    </script>

</body>
</html>

