package com.iteamoa.mypage.repository;

import com.iteamoa.mypage.constant.DynamoDbEntityType;
import com.iteamoa.mypage.entity.ApplicationEntity;
import com.iteamoa.mypage.utils.KeyConverter;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ApplicationRepository {
    private final DynamoDbTable<ApplicationEntity> applicationTable;
    private final DynamoDbIndex<ApplicationEntity> applicationIndex;

    public ApplicationRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.applicationTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(ApplicationEntity.class));
        this.applicationIndex = applicationTable.index("Application-index");
    }

    public List<ApplicationEntity> findApplication(String feedId, String part) {
        String partitionValue = KeyConverter.toPk(DynamoDbEntityType.APPLICATION, feedId);
        QueryConditional queryConditional = part != null
                ? QueryConditional.keyEqualTo(k -> k.partitionValue(partitionValue).sortValue(part))
                : QueryConditional.keyEqualTo(k -> k.partitionValue(partitionValue));

        final SdkIterable<Page<ApplicationEntity>> pagedResult = applicationIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }

    public ApplicationEntity getApplication(String userId, String feedId){
        return applicationTable.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.USER, userId),
                KeyConverter.toPk(DynamoDbEntityType.APPLICATION, feedId)
        ));
    }

    public void updateApplication(ApplicationEntity applicationEntity) {
        applicationTable.updateItem(applicationEntity);
    }

}
