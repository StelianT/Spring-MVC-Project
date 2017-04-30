function likeQuote(elem) {
    var quoteId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/quotes/like/' + quoteId,
        success: addNewLike(quoteId)
    });
}

function addNewLike(quoteId) {
    var currentUsername = $('#loggedInUser').val();
    var currentUserId = $('#loggedInUserId').val();

    var aTag = document.createElement('a');
    aTag.setAttribute('href',"/user/" + currentUserId);

    var isOnlyOneLike = !($('#' + quoteId).children.length > 0);

    var newDiv = document.createElement("div");
    var newContent = document.createTextNode(currentUsername);
    newDiv.appendChild(newContent);
    $(newDiv).addClass(currentUserId);
    $(newDiv).addClass("list-group-item");
    aTag.appendChild(newDiv);
    $('#' + quoteId).append(aTag);

    $('#id' + quoteId).removeClass("disabled");

    if (isOnlyOneLike) {
        $('#id' + quoteId).attr('isOnlyOneLike', 'true');
    } else {
        $('#id' + quoteId).attr('isOnlyOneLike', 'false');
    }

    $('button[value=' + quoteId + ']').text('Unlike').attr('onclick', 'unlike(this)');
}

function unlike(elem) {
    var quoteId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/quotes/unlike/' + quoteId,
        success: removeLike(quoteId)
    });
}

function removeLike(quoteId) {
    var currentUserId = $('#loggedInUserId').val();
    $('#id' + quoteId + ' ' + '.' + currentUserId).remove();
    $('button[value=' + quoteId + ']').text('Like').attr('onclick', 'likeQuote(this)');

    var isOnlyOneLike = $('#id' + quoteId).attr('isOnlyOneLike');
    if (isOnlyOneLike === "true") {
        $('#id' + quoteId).addClass("disabled");
    }
}

function onLikedByHover(elem) {
    var quoteId = elem.getAttribute('value');
    document.getElementById(quoteId).style.position = 'absolute';
    document.getElementById(quoteId).style.zIndex = '1';
    document.getElementById(quoteId).style.display = 'block';
}

function onLikedByOut(elem) {
    var quoteId = elem.getAttribute('value');
    document.getElementById(quoteId).style.display = 'none';
}