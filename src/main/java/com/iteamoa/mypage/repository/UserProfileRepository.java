package com.iteamoa.mypage.repository;

import com.iteamoa.mypage.constant.DynamoDbEntityType;
import com.iteamoa.mypage.entity.UserProfileEntity;
import com.iteamoa.mypage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class UserProfileRepository {
    private final DynamoDbTable<UserProfileEntity> userProfileTable;

    public UserProfileRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.userProfileTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(UserProfileEntity.class));
    }

    public UserProfileEntity findByUserId(String userId) {
        return userProfileTable.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.USER, userId),
                KeyConverter.toPk(DynamoDbEntityType.PROFILE, "")
        ));
    }

}
