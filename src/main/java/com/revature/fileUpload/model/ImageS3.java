package com.revature.fileUpload.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ImageS3 {

	@Id
	private String userImageId;
	
	private String imageUrl;
	
	
	public String getUserImageId() {
		return userImageId;
	}

	public void setUserImageId(String userImageId) {
		this.userImageId = userImageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
