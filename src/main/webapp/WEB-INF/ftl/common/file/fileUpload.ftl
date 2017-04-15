<!DOCTYPE html>
<html>
<head>
    <title>单文件上传分片上传上传</title>
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
            <button id="ctlBtn" class="btn btn-default">开始上传</button>
        </div>
    </div>
    <script type="text/javascript">
        $(function(){
            var $ = jQuery,
                    $list = $('#thelist'),
                    $btn = $('#ctlBtn'),
                    state = 'pending',
                    uploader,
                    decode;

            uploader = WebUploader.create({

                // swf文件路径
                swf: '/js/Uploader.swf',

                // 文件接收服务端。
                server: basePath + '/file/uploadPart',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#picker',

                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,

                chunked : true, //是否分片处理文件

                chunkSize : 1048576 //分片大小
            });

            // 当有文件添加进来的时候
            uploader.on("fileQueued", function(file) {
                $list.append( '<div id="' + file.id + '" class="item">' +
                        '<h4 class="info">' + file.name + '</h4>' +
                        '<p class="state">等待上传...</p>' +
                        '</div>' );

                var aa = uploader.md5File(file);
                aa.then(function(res){
                    console.log("md5值是->",res);
                    uploader.option("formData",{ //设置uploader的属性
                        md5 : res
                    });
                    decode = res;
                });

            });

            $btn.on( 'click', function() {
                if ( state === 'uploading' ) {
                    uploader.stop();//暂停上传文件
                } else {
                    uploader.upload();//开始上传文件
                }
            });

        });
    </script>

</body>
</html>

