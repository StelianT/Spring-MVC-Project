function showQuotes() {
    $('#picturesButton, #storiesButton, #moviesButton').removeClass('active');
    $('#quotesButton').addClass('active');
    $('#pictures, #stories, #movies').css('display', 'none');
    $('#quotes').css('display', 'block');
}

function showPictures() {
    $('#quotesButton, #storiesButton, #moviesButton').removeClass('active');
    $('#picturesButton').addClass('active');
    $('#quotes, #stories, #movies').css('display', 'none');
    $('#pictures').css('display', 'block');
}

function showStories() {
    $('#quotesButton, #picturesButton, #moviesButton').removeClass('active');
    $('#storiesButton').addClass('active');
    $('#quotes, #pictures, #movies').css('display', 'none');
    $('#stories').css('display', 'block');
}

function showMovies() {
    $('#quotesButton, #picturesButton, #storiesButton').removeClass('active');
    $('#moviesButton').addClass('active');
    $('#quotes, #pictures, #stories').css('display', 'none');
    $('#movies').css('display', 'block');
}