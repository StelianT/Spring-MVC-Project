function likeQuote(elem) {
    var pictureId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/pictures/like/' + pictureId,
        success: addNewLike(pictureId)
    });
}

function addNewLike(pictureId) {
    var currentUsername = $('#loggedInUser').val();
    var currentUserId = $('#loggedInUserId').val();

    var aTag = document.createElement('a');
    aTag.setAttribute('href',"/user/" + currentUserId);

    var newDiv = document.createElement("div");
    var newContent = document.createTextNode(currentUsername);
    newDiv.appendChild(newContent);
    $(newDiv).addClass(currentUserId);
    $(newDiv).addClass("list-group-item");
    aTag.appendChild(newDiv);
    $('#' + pictureId).append(aTag);

    $('#id' + pictureId).removeClass("disabled").attr('isOnlyOneLike', 'true');

    $('button[value=' + pictureId + ']').text('Unlike').attr('onclick', 'unlike(this)');
}

function unlike(elem) {
    var pictureId = elem.value;
    $.ajax({
        type: 'POST',
        url: '/pictures/unlike/' + pictureId,
        success: removeLike(pictureId)
    });
}

function removeLike(pictureId) {
    var currentUserId = $('#loggedInUserId').val();
    $('#id' + pictureId + ' ' + '.' + currentUserId).remove();
    $('button[value=' + pictureId + ']').text('Like').attr('onclick', 'likeQuote(this)');

    var isOnlyOneLike = $('#id' + pictureId).attr('isOnlyOneLike');
    if (isOnlyOneLike === "true") {
        $('#id' + pictureId).addClass("disabled");
    }
}

function onLikedByHover(elem) {
    var pictureId = elem.getAttribute('value');
    document.getElementById(pictureId).style.position = 'absolute';
    document.getElementById(pictureId).style.zIndex = '1';
    document.getElementById(pictureId).style.display = 'block';
}

function onLikedByOut(elem) {
    var pictureId = elem.getAttribute('value');
    document.getElementById(pictureId).style.display = 'none';
}