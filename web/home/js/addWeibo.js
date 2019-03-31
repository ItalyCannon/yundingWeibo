var imgNum = 0;

function addWeibo() {
    var weibo = {};
    weibo.photo = [];
    var weiboContent = $("#weiboContent").val();
    if (weiboContent == undefined || weiboContent == null || weiboContent == '') {
        alert("微博内容不能为空");
        return;
    }

    var formData = new FormData();
    for (var j = 0; j < $('input[name=weiboFile]').length; j++) {
        for (var i = 0; i < $('input[name=weiboFile]')[j].files.length; i++) {
            var file = $('input[name=weiboFile]')[0].files[i];
            formData.append('photoForm', file);
        }
    }

    $.ajax({
        url: '/WeiboPhotoServlet',
        method: 'POST',
        data: formData,
        contentType: false, // 注意这里应设为false
        processData: false,
        cache: false,
        success: function (data) {
            weibo.photo.push(data);
        },
        async: false
    });

    weibo.weiboContent = weiboContent;
    // console.log(weibo);
    // console.log(JSON.stringify(weibo));
    $.ajax({
        url: '/AddWeiboServlet',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: JSON.stringify(weibo)
        },
        success: function (text) {
            // alert("发布成功");
            window.location.href = "/home";
        },
        async: false
    });

}

function openBrowse() {
    var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
    var inputButton = document.getElementById("file");
    if (ie) {
        inputButton.click();
    } else {
        var a = document.createEvent("MouseEvents");//FF的处理
        a.initEvent("click", true, true);
        inputButton.dispatchEvent(a);
    }
    inputButton.setAttribute("id", "file1");

    $("#photoForm").append('<input type="file" id="file" name="weiboFile" onchange="reads(this)">');
    imgNum++;
}

function reads(obj) {
    var file = obj.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (ev) {
        var imgBackView = $("#backimg" + imgNum);
        imgBackView.attr("src", ev.target.result);
        imgBackView.click(function () {
            showBigPhoto(ev.target.result)
        });
        imgBackView.removeAttr("style");
    }
}

function cancelShowBigPhoto() {
    $("#background").attr("style", "display:none;");
    $("#showBigPhoto").attr("style", "display:none;");
}

function showBigPhoto(src) {
    $("#background").attr("style", "display:block;");
    $("#showBigPhoto").attr("style", "display:block;");
    $("#showBigPhoto").attr("src", src);
}
