package com.revature.fileUpload.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.fileUpload.model.ImageS3;
import com.revature.fileUpload.service.S3BucketService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	S3BucketService s3BucketService;
	
	@PostMapping("/uploadImage")
	public String uploadImage(MultipartFile multipartFile) {
		String url = "";
		try {
			ImageS3 image = s3BucketService.uploadImageToAmazon(multipartFile);
			url = image.getImageUrl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
//	@GetMapping("/GetImage")
//	public string getAllImages() {
//		
//		s3BucketService.getURL();
//	}
	
}
