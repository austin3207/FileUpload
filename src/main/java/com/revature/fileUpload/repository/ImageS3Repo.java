package com.revature.fileUpload.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.revature.fileUpload.model.ImageS3;

public interface ImageS3Repo extends JpaRepository<ImageS3, String>{

}
