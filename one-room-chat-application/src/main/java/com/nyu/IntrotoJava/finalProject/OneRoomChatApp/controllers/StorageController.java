package com.nyu.IntrotoJava.finalProject.OneRoomChatApp.controllers;

import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.models.Users;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.StorageService;
import com.nyu.IntrotoJava.finalProject.OneRoomChatApp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private UsersService usersService;

    @GetMapping("/generate-presigned-url")
    public String getPresignedUrlToUploadFile(@RequestParam String filename, @RequestParam String contentType, @RequestParam String userId) {
        String bucketName = "springbootbucket-practice";
        Users user = usersService.findUserById(Long.parseLong(userId)).get();
        String objectKey = user.getUserId().toString() +"/" + filename; // Customize your object key
        return storageService.generatePresignedUrl(bucketName, objectKey, contentType);
    }
}
