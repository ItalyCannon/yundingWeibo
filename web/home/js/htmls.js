function load_home1() {
    window.location.href = "/home"
}

function load_home2() {
    window.location.href = "/collection";
}

function load_home3() {
    window.location.href = "/praise";
}

function load_home4() {
    window.location.href = "/commentReceive";
}

function show_list() {
    window.location.href = "/list";
}

function loadDetail() {
    window.location.href = "/detail"
}

function load_attention() {
    window.location.href = "/attention"
}

function load_fans() {
    window.location.href = "/fans"
}

function load_space() {
    window.location.href = "/space"
}

var allCollection;
var allPraise;

var PageCode = 1;

function showWeibo() {
    var url = '/HomeWeiboServlet?pc=' + PageCode;
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            flushAllCollections();
            var data = eval(text);
            var html = noApplicationRecord.innerHTML;
            var messages = [];
            var ids = [];
            console.log(data);
            for (var i = 0; i < data.beanList.length; i++) {
                html +=
                    '<div class="part_1"' + ' id="' + data.beanList[i].weiboId + 'main' + '"' + '>' +
                    '<img src="' + data.beanList[i].profilePicture + '" alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data.beanList[i].nickname + '</p>' +
                    '<p class="date">' + new Date(data.beanList[i].createTime).toLocaleString() + '</p>' +
                    '</div>' +
                    '<p class="text">' + data.beanList[i].weiboContent + '<span>' + '' + '</span></p>' +
                    '<div id="imgs" class="imgs"' + imgHeight(data.beanList[i].photo) + '>' +
                    picture(data.beanList[i].photo) +
                    '</div>' +
                    '<div class="bottom">' +
                    '<div class="bottom_part bottom1" onclick="collection(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe665;</i>' +
                    '<p class="bottom1_text" id="' + data.beanList[i].weiboId + 'c' + '">' + collectionCondition(data.beanList[i].weiboId) + '</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom2" onclick="repost(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe64d;</i>' +
                    '<p class="bottom2_text">转发</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom3" id="' + data.beanList[i].weiboId + 's"' + ' onclick="showComment('
                    + data.beanList[i].weiboId + ')"' + ' style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe643;</i>' +
                    '<p class="bottom3_text">' + data.beanList[i].commentNum + '</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom4" onclick="praise(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont" id="' + data.beanList[i].weiboId + 'aaaa' + '">&#xe60c;</i>' +
                    '<p class="bottom4_text" id="' + data.beanList[i].weiboId + 'p' + '">' + data.beanList[i].praiseNum + '</p>' +
                    '</div>' +
                    '</div>' + '<div id="' + data.beanList[i].weiboId + 'cc' + '" style="display: none;">0</div>'
                    + '<div id="' + data.beanList[i].weiboId + 'ff' + '"  style="display:none;" class="comment"></div>'
                    + '</div>';

                //先把一条微博的轮廓搭起来，然后再往里加评论的部分
                noApplicationRecord.innerHTML = html;
                var message = '';
                message += '<div class="write">' +
                    '<img src="' + profile + '" alt="img" class="head_img">' +
                    '<textarea name="" class="text" id="' + data.beanList[i].weiboId + 'addcomment' + '">' + '' + '</textarea>' +
                    '<div class="option">' +
                    '<i class="iconfont expression">&#xe60c;</i>' +
                    '<i class="iconfont image">&#xe60c;</i>' +
                    '<div class="check"></div>' +
                    '<p class="word">同时转发到我的微博</p>' +
                    '<input type="submit" name="" value="发布" class="submit" onclick="addComment(' + data.beanList[i].weiboId + ')" style="cursor: pointer">' +
                    '</div>' +
                    '</div>';
                for (var j = 0; j < data.beanList[i].comments.length; ++j) {
                    message += '<div class="option_1">' +
                        '<img src="' + data.beanList[i].comments[j].profilePicture + '" alt="img" class="head_img">' +
                        '<div class="message">' +
                        '<p class="nickname"><span>' + data.beanList[i].comments[j].nickname + '：' + '</span>'
                        + data.beanList[i].comments[j].commentContent + '</p>' +
                        '<p class="date">' + data.beanList[i].comments[j].formatCommentTime + '</p>' +
                        '<p class="reply">回复</p>' +
                        '<p class="string"></p>' +
                        '<p class="like" style="cursor: pointer" onclick="likeComment(' + data.beanList[i].comments[j].commentId + ', '
                        + data.beanList[i].comments[j].commentPraise + ')" id="'
                        + data.beanList[i].comments[j].commentId + 'commentPraiseNum"><i class="iconfont">&#xe60c;</i> '
                        + data.beanList[i].comments[j].commentPraise + '</p>' +
                        '</div>' +
                        '</div>'
                }
                messages[i] = message;
                ids[i] = data.beanList[i].weiboId + 'ff';
            }
            for (var n = 0; n < data.beanList.length; ++n) {
                var weiboId = data.beanList[n].weiboId;
                for (var m = 0; m < allPraise.length; ++m) {
                    if (allPraise[m].weiboId == weiboId) {
                        document.getElementById(weiboId + "aaaa").style.color = "red";
                    }
                }
            }
            for (var k = 0; k < messages.length; ++k) {
                document.getElementById(ids[k]).innerHTML = messages[k];
            }

        },
        async: false
    });
    PageCode++;
}

var profile = '';

var userInfo;

function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
            userInfo = user;
            profile = user.profilePicture;
            $("#profilePicture").attr('src', user.profilePicture);
            $("#profile1").attr('src', user.profilePicture);
            $("#nicknameLeft").html(user.nickname);
            $("#subscribeNum").html(user.subscribeNum);
            $("#fansNum").html(user.fansNum);
            $("#weiboNum").html(user.weiboNum);
        },
        async: false
    });
}


function imgHeight(photo) {
    var pic = 'style="height: ';
    if (photo.length === 0) {
        return pic + '0"';
    }
    if (photo.length >= 1 && photo.length <= 3) {
        return pic + '136px"'
    }
    if (photo.length >= 4 && photo.length <= 7) {
        return pic + '280px"';
    }
    if (photo.length >= 8) {
        return pic + '420px"';
    }
}

function flushAllCollections() {
    $.ajax({
        url: '/CollectionServlet?type=show',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allCollection = eval(text);
        },
        async: false
    });
}

function picture(photo) {
    var img = '';
    if (photo.length === 0) {
        return img;
    }

    for (var i = 0; i < photo.length; ++i) {
        img +=
            '<img src="' + photo[i] + '" alt="img" class="middle_down_img middle_down_img' + (i + 1) + '">'
    }
    return img;
}

function repost(weiboId) {
    $.ajax({
        url: '/RepostServlet?type=add',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: {},
        async: false
    });
    alert("转发成功!");
}

function collectionCondition(weiboId) {
    for (var q = 0; q < allCollection.length; ++q) {
        if (allCollection[q].weiboId === weiboId) {
            return "已收藏";
        }
    }
    return "收藏";
}

function showComment(weiboId) {
    var cc = $("#" + weiboId + "cc");
    if (cc.html() == 0) {
        $("#" + weiboId + "ff").css("display", "block");
        cc.html(1);
    }
}

function praise(weiboId) {
    var flag = 0;
    $.ajax({
        url: '/PraiseServlet?type=weibo',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            if (text != null) {
                alert(text);
                flag++;
                return;
            }
        },
        async: false
    });

    if (flag === 0) {
        document.getElementById(weiboId + "aaaa").style.color = "red";
        var praiseNum = document.getElementById(weiboId + 'p');
        praiseNum.innerHTML = parseInt(praiseNum.innerHTML) + 1;
    }
}

function collection(weiboId) {
    var url;
    var collention = document.getElementById(weiboId + "c");
    if (collectionCondition(weiboId) === "收藏") {
        url = "/CollectionServlet?type=add";
        collention.innerHTML = "已收藏";
        allCollection.push({"weiboId": weiboId});
    } else {
        url = "/CollectionServlet?type=delete";
        collention.innerHTML = "收藏";
        for (var b = 0; b < allCollection.length; ++b) {
            if (allCollection[b].weiboId === weiboId) {
                allCollection.splice(b, 1);
                break;
            }
        }
    }
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data: {
            weibo: weiboId
        },
        success: function (text) {
            alert(text);
        },
        async: false
    })
}

$(function () {
    var totalHeight = 0; //定义一个总高度变量
    function ata() { //loa动态加载数据
        totalHeight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); //浏览器的高度加上滚动条的高度
        if ($(document).height() <= totalHeight) { //当文档的高度小于或者等于总的高度时，开始动态加载数据
            showWeibo();
        }
    }

    $(window).scroll(function () {
        ata();
    })
});


function getPraise() {
    $.ajax({
        url: '/MyPraiseServlet?c=aa',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            allPraise = eval(text);
        },
        async: false
    });
}

function addWeibo() {
    var weibo = {};
    weibo.photo = [];
    var weiboContent = $("#weiboContent").val();
    if (weiboContent == undefined || weiboContent == null || weiboContent == '') {
        alert("微博内容不能为空");
        return;
    }

    //这部分应该写到openBrowse()里，但修改成本太大了，就先写到这里了
    var formData = new FormData();
    var file = $('input[name=weiboFile]')[0].files[0];
    formData.append('photoForm', file);
    $.ajax({
        url: '/WeiboPhotoServlet',
        method: 'POST',
        data: formData,
        contentType: false, // 注意这里应设为false
        processData: false,
        cache: false,
        success: function (data) {
            weibo.photo[0] = data;
        },
        async: false
    });

    weibo.weiboContent = weiboContent;
    console.log(weibo);
    console.log(JSON.stringify(weibo));
    $.ajax({
        url: '/AddWeiboServlet',
        type: 'get',
        dataType: 'json',
        data: {
            weibo: JSON.stringify(weibo)
        },
        success: function (text) {
            alert("发布成功");
            window.location.href = "/home";
        },
        async: false
    });

}

function addComment(weiboId) {
    var comment = {commentContent: ''};
    var commentContent = $("#" + weiboId + "addcomment").val();
    if (commentContent == undefined || commentContent == null || commentContent == '') {
        alert("评论内容不能为空");
        return;
    }
    comment.commentContent = commentContent;
    var weibo = {weiboId: -1};
    weibo.weiboId = weiboId;
    $.ajax({
        url: '/AddCommentServlet',
        type: 'get',
        dataType: 'json',
        data: {
            comment: JSON.stringify(comment),
            weibo: JSON.stringify(weibo)
        },
        success: function (text) {
        },
        async: false
    });
    window.location.href = "/home";
}

function likeComment(commentId, praise) {
    var comment = {};
    comment.commentId = commentId;
    // console.log(comment);
    $.ajax({
        url: '/PraiseServlet?type=comment',
        type: 'get',
        dataType: 'json',
        data: {
            comment: JSON.stringify(comment)
        },
        success: function (text) {

        },
        async: false
    });
    praise++;
    var html = '<i class="iconfont">&#xe60c;</i> ' + praise;

    $("#" + commentId + "commentPraiseNum").html(html);
}

window.onload = function () {
    baseInfo();
    getPraise();
    showWeibo();
};

function openBrowse() {
    var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
    if (ie) {
        document.getElementById("file").click();
    } else {
        var a = document.createEvent("MouseEvents");//FF的处理
        a.initEvent("click", true, true);
        document.getElementById("file").dispatchEvent(a);
    }
}