package com.motivation.controllers;

import com.motivation.dao.FileUploadDAO;
import com.motivation.entities.Picture;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.bindingModels.AddQuoteBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.services.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/pictures")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("")
    public String getPictureHomePage(Model model, @PageableDefault(size = 6) Pageable pageable) {

        //List<PictureViewModel> pictures = this.pictureService.findAllPictures();
        Page<PictureViewModel> pictures = this.pictureService.findAllPictures(pageable);
        User currentUser = getCurrentUser();

        for (PictureViewModel picture : pictures) {
            Set<User> likedBy = picture.getLikedBy();
            boolean isLikedByCurrentUser = false;
            for (User user : likedBy) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    isLikedByCurrentUser = true;
                }
            }
            picture.setLikedByCurrentUser(isLikedByCurrentUser);
        }

        model.addAttribute("pictures", pictures);

        return "pictures";
    }

    @GetMapping("/add")
    public String getAddPicturePage(@ModelAttribute AddPictureBindingModel addPictureBindingModel) {
        return "pictures-add";
    }

    @PostMapping("/add")
    public String addPicture(@ModelAttribute AddPictureBindingModel addPictureBindingModel) throws IOException {

        addPictureBindingModel.setAddedBy(getCurrentUser());
        this.pictureService.save(addPictureBindingModel);

//        MultipartFile fileUpload = addPictureBindingModel.getPicture();
//        String name = addPictureBindingModel.getTitle();
//
//        if (fileUpload != null && fileUpload.getSize() > 0) {
//
//                System.out.println("Saving file: " + fileUpload.getOriginalFilename());
//
//                Picture uploadFile = new Picture();
//                uploadFile.setFileName(name);
//                uploadFile.setData(fileUpload.getBytes());
//                fileUploadDAO.save(uploadFile);
//        }

        return "redirect:/pictures";
    }

    @PostMapping("/like/{pictureId}")
    public ResponseEntity likeQuote(@PathVariable long pictureId) {
        this.pictureService.like(getCurrentUser(), pictureId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/unlike/{pictureId}")
    public ResponseEntity unlikeQuote(@PathVariable long pictureId) {
        this.pictureService.unlike(getCurrentUser(), pictureId);

        return new ResponseEntity(HttpStatus.OK);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() :  null;

        return (User) myUser;
    }
}
