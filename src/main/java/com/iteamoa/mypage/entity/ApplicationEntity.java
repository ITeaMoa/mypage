package com.iteamoa.mypage.entity;

import com.iteamoa.mypage.constant.StatusType;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@Setter
@DynamoDbBean
public class ApplicationEntity extends BaseEntity{
    private String part;
    private StatusType status;
    private String feedType;

    public ApplicationEntity() {}

    @DynamoDbAttribute("part")
    @DynamoDbSecondarySortKey(indexNames = {"Application-index"})
    public String getPart(){
        return part;
    }

    @DynamoDbAttribute("status")
    public StatusType getStatus(){
        return status;
    }

    @DynamoDbAttribute("feedType")
    public String getFeedType(){
        return feedType;
    }

}
