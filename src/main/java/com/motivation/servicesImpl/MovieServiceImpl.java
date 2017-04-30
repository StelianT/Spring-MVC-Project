package com.motivation.servicesImpl;

import com.motivation.entities.Movie;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddMovieBindingModel;
import com.motivation.models.viewModels.MovieViewModel;
import com.motivation.repository.MovieRepository;
import com.motivation.services.MovieService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(AddMovieBindingModel addMovieBindingModel) {
        String movieTitle = addMovieBindingModel.getTitle();
        Movie movie = new Movie();
        try {
            retrieveMovieInfo(movieTitle, movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        movie.setAddedBy(addMovieBindingModel.getAddedBy());
        movie.setDescription(addMovieBindingModel.getDescription());
        this.movieRepository.save(movie);
    }

    @Override
    public List<MovieViewModel> findAllMovies() {
        List<MovieViewModel> models = new ArrayList<>();
        List<Movie> movies = this.movieRepository.findAll();
        for (Movie movie : movies) {
            MovieViewModel model = this.modelMapper.map(movie, MovieViewModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public List<MovieViewModel> findAllMoviesByUserId(Long userId) {
        List<MovieViewModel> models = new ArrayList<>();
        List<Movie> movies = this.movieRepository.findAllByAddedById(userId);
        for (Movie movie : movies) {
            MovieViewModel model = this.modelMapper.map(movie, MovieViewModel.class);
            models.add(model);
        }

        return models;
    }

    @Override
    public Page<MovieViewModel> findAllMovies(Pageable pageable) {
        Page<Movie> movies = this.movieRepository.findAll(pageable);
        List<MovieViewModel> movieViewModels = new ArrayList<>();
        for (Movie movie : movies) {
            MovieViewModel movieViewModel = this.modelMapper.map(movie, MovieViewModel.class);
            movieViewModels.add(movieViewModel);
        }

        Page<MovieViewModel> movieModels = new PageImpl<>(movieViewModels, pageable,
                movies.getTotalElements());
        return movieModels;
    }

    @Override
    public void like(User user, long movieId) {
        Movie movie = this.movieRepository.findOneById(movieId);
        movie.getLikedBy().add(user);
        this.movieRepository.saveAndFlush(movie);
    }

    @Override
    public void unlike(User user, long movieId) {
        Set<User> usersLikedMovie = this.movieRepository.findOneById(movieId).getLikedBy();
        for (User userInCollection : usersLikedMovie) {
            if (userInCollection.getUsername().equals(user.getUsername())) {
                usersLikedMovie.remove(userInCollection);
                this.movieRepository.saveAndFlush(this.movieRepository.findOneById(movieId));
                return;
            }
        }
    }

    private void retrieveMovieInfo(String movieTitle, Movie movie) throws Exception {

        String url = "http://www.omdbapi.com/?t=" + URLEncoder.encode(movieTitle, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");

        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String responseStr = response.toString();
        JSONObject jsonObject = new JSONObject(responseStr);
        String title = jsonObject.getString("Title");
        String posterLink = jsonObject.getString("Poster");
        int year = Integer.parseInt(jsonObject.getString("Year"));
        String genre = jsonObject.getString("Genre");

        movie.setTitle(title);
        movie.setPosterLink(posterLink);
        movie.setYear(year);
        movie.setGenre(genre);
    }
}
