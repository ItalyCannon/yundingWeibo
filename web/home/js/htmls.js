function load_home1() {
    var one = document.getElementById("1");
    one.style.display = 'block';
    var two = document.getElementById("2");
    two.style.display = 'none';
    var two = document.getElementById("3");
    two.style.display = 'none';
    var two = document.getElementById("4");
    two.style.display = 'none';
    var css = document.getElementById("css1");
    css.href = "../home/css/style.css"
}

function load_home2() {
    window.location.href = "/collection/index.html";
}

function load_home3() {
    window.location.href = "/praise/index.html";
}

function load_home4() {
    window.location.href = "/commentReceive/index.html";
}

var allCollection;

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
            for (var i = 0; i < data.beanList.length; i++) {
                html +=
                    '<div class="part_1"' + ' id="' + data.beanList[i].weiboId + 'main' + ' "' + '>' +
                    '<img src="' + data.beanList[i].profilePicture + '" alt="" class="portrait">' +
                    '<div class="name">' +
                    '<p class="nickname">' + data.beanList[i].nickname + '</p>' +
                    '<p class="date">' + data.beanList[i].formatCreateTime + '</p>' +
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
                    '<div class="bottom_part bottom3" id="' + data.beanList[i].weiboId + 's' + '" onclick="showComment(' + data.beanList[i].weiboId + 's' + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe643;</i>' +
                    '<p class="bottom3_text">' + data.beanList[i].commentNum + '</p>' +
                    '</div>' +
                    '<div class="bottom_part bottom4" onclick="praise(' + data.beanList[i].weiboId + ')" style="cursor: pointer;">' +
                    '<i class="iconfont">&#xe60c;</i>' +
                    '<p class="bottom4_text" id="' + data.beanList[i].weiboId + 'p' + '">' + data.beanList[i].praiseNum + '</p>' +
                    '</div>' +
                    '</div>'

                    + '</div>';
                noApplicationRecord.innerHTML = html;
            }
        },
        async: false
    });
    PageCode++;
}

function baseInfo() {
    $.ajax({
        url: '/BasicUserInfoServlet',
        type: 'get',
        dataType: 'json',
        data: {},
        success: function (text) {
            user = eval(text);
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

//s是后缀，不是复数的意思
function showComment(weiboIds) {
    alert("111");
    // alert(weiboIds);
    // var commentDiv = $("#weiboIds");
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
            }
        },
        async: false
    });

    if (flag === 0) {
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

function showTopic() {

}

/*
 * 监听滚动条，本来不想用jQuery但是发现js里面监听滚动条的事件不好添加，这边就引用了Jquery的$(obj).scroll();这个方法了
 */
$(window).scroll(function () {
    //下面这句主要是获取网页的总高度，主要是考虑兼容性所以把Ie支持的documentElement也写了，这个方法至少支持IE8
    var htmlHeight = document.body.scrollHeight || document.documentElement.scrollHeight;
    var number = parseInt(htmlHeight);
    number -= 10;
    //clientHeight是网页在浏览器中的可视高度，
    var clientHeight = document.body.clientHeight || document.documentElement.clientHeight;
    //scrollTop是浏览器滚动条的top位置，
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    //通过判断滚动条的top位置与可视网页之和与整个网页的高度是否相等来决定是否加载内容；
    if (scrollTop + clientHeight >= number) {
        showWeibo();
    }
});


window.onload = function () {
    showWeibo();
    baseInfo();
};
