package com.iteamoa.mypage.entity;

import com.iteamoa.mypage.constant.DynamoDbEntityType;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@DynamoDbBean
public abstract class BaseEntity {
    private String pk;
    private String sk;
    private DynamoDbEntityType entityType;
    private LocalDateTime timestamp;
    private String creatorId;
    private Boolean userStatus;

    public BaseEntity() {}
    public BaseEntity(String pk, String sk, DynamoDbEntityType entityType, LocalDateTime timestamp, String creatorId) {
        this.pk = pk;
        this.sk = sk;
        this.entityType = entityType;
        this.timestamp = Objects.requireNonNullElseGet(timestamp, LocalDateTime::now);
        this.creatorId = creatorId;
        this.userStatus = true;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("Pk")
    public String getPk() {
        return pk;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("Sk")
    @DynamoDbSecondaryPartitionKey(indexNames = {"MostLikedFeed-index", "PostedFeed-index", "Application-index"})
    @DynamoDbSecondarySortKey(indexNames = {"SearchByCreator-index"})
    public String getSk() {
        return sk;
    }

    @DynamoDbAttribute("entityType")
    @DynamoDbSecondarySortKey(indexNames = "Like-index")
    public DynamoDbEntityType getEntityType(){
        return entityType;
    }

    @DynamoDbAttribute("timestamp")
    @DynamoDbSecondarySortKey(indexNames = "PostedFeed-index")
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    @DynamoDbAttribute("creatorId")
    @DynamoDbSecondaryPartitionKey(indexNames = {"SearchByCreator-index", "CreatorId-index"})
    public String getCreatorId(){
        return creatorId;
    }

    @DynamoDbAttribute("userStatus")
    @DynamoDbSecondarySortKey(indexNames = {"CreatorId-index"})
    public boolean getUserStatus(){
        return userStatus;
    }

}
