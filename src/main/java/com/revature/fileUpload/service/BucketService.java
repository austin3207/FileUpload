package com.revature.fileUpload.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class BucketService {
	
	private AmazonS3 amazonS3;
	@Value("${amazon.s3.bucket-name}")
	private String bucketName;
	
	@Value("${amazon.s3.endpoint}")
	private String url;
	
	// The IAM access key.
    @Value("${amazon.s3.access-key}")
    private String accessKey;

    // The IAM secret key.
    @Value("${amazon.s3.secret-key}")
    private String secretKey;
    
	public AmazonS3 getClient() {
		// TODO Auto-generated method stub
		return amazonS3;
	}

	public String getBucketName() {
		// TODO Auto-generated method stub
		return bucketName;
	}

	public String getURL() {
		// TODO Auto-generated method stub
		return url;
	}
	@PostConstruct
	private void init() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
	}

}
