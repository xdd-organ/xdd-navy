<!DOCTYPE html>
<html>
<head>
    <title>多文件普通上传</title>
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
                swf: '/js/Uploader.swf',

                // 文件接收服务端。
                server: basePath + '/file/upload',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {
                    id : "#picker",
                    multiple : true //是否开启选择多个文件能力
                },

                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,

                chunked : false, //是否分片处理文件

                compress: null,//图片不压缩

                fileNumLimit : 3, //允许上传文件个数

                chunkRetry : 3 //重试次数
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
                if ( state === 'uploading' ) {
                    uploader.stop(true);//暂停上传文件
                } else {
                    uploader.upload();//开始上传文件
                }
            });

            uploader.on( 'all', function( type ) {
                if ( type === 'startUpload' ) {
                    state = 'uploading';
                } else if ( type === 'stopUpload' ) {
                    state = 'paused';
                } else if ( type === 'uploadFinished' ) {
                    state = 'done';
                }

                if ( state === 'uploading' ) {
                    $btn.text('暂停上传');
                } else {
                    $btn.text('开始上传');
                }
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

            uploader.on("uploadProgress", function (file, percentage) {//带上传进度
                console.log("上传进度！", percentage);
            });

            uploader.on('error', function(handler) {
                console.log("捕捉的异常信息",handler);
                if ("Q_EXCEED_NUM_LIMIT" == handler) {
                    alert("上传文件数大于允许最大数！");
                }
            });




        });
    </script>

</body>
</html>

