package com.revature.fileUpload.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.fileUpload.model.ImageS3;
import com.revature.fileUpload.repository.ImageS3Repo;
import com.revature.fileUpload.util.FileUtil;


@Service
public class S3BucketService extends BucketService{
	@Autowired
	private ImageS3Repo imageS3Repo;
	
	// Upload a List of Images to AWS S3.
    public List<ImageS3> insertImages(List<MultipartFile> images){
        List<ImageS3> amazonImages = new ArrayList<>();
        images.forEach(image -> {
			try {
				amazonImages.add(uploadImageToAmazon(image));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        return amazonImages;
    }

    // Upload image to AWS S3.
    @SuppressWarnings("unused")
	public ImageS3 uploadImageToAmazon(MultipartFile multipartFile) throws IOException{

        // Valid extensions array, like jpeg/jpg and png.
        List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");

        // Get extension of MultipartFile
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
//        if (!validExtensions.contains(extension)) {
//            // If file have a invalid extension, call an Exception.
////            log.warn(MessageUtil.getMessage("invalid.image.extesion"));
////            throw new InvalidImageExtensionException(validExtensions);
//        	
//        } else {

            // Upload file to Amazon.
            String url = uploadMultipartFile(multipartFile);

            // Save image information on MongoDB and return them.
            ImageS3 imageS3 = new ImageS3();
            imageS3.setImageUrl(url);

            return imageS3Repo.save(imageS3);
//        }

    }

    public void removeImageFromAmazon(ImageS3 imageS3) {
        String fileName = imageS3.getImageUrl().substring(imageS3.getImageUrl().lastIndexOf("/") + 1);
        getClient().deleteObject(new DeleteObjectRequest(getBucketName(), fileName));
        imageS3Repo.delete(imageS3);
    }

    // Make upload to Amazon.
    private String uploadMultipartFile(MultipartFile multipartFile) throws IOException{
        String fileUrl;

       // try {
            // Get the file from MultipartFile.
            File file = FileUtil.convertMultipartToFile(multipartFile);

            // Extract the file name.
            String fileName = FileUtil.generateFileName(multipartFile);

            // Upload file.
            uploadPublicFile(fileName, file);

            // Delete the file and get the File Url.
            file.delete();
            fileUrl = getURL().concat(fileName);
        //} catch (IOException e) {

            // If IOException on conversion or any file manipulation, call exception.
            //log.warn(MessageUtil.getMessage("multipart.to.file.convert.except"), e);
            //throw new FileConversionException();
        //}

        return fileUrl;
    }

    // Send image to AmazonS3, if have any problems here, the image fragments are removed from amazon.
    // Font: https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html#putObject%28com.amazonaws.services.s3.model.PutObjectRequest%29
    private void uploadPublicFile(String fileName, File file) {
        getClient().putObject(new PutObjectRequest(getBucketName(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
