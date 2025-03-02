package com.iteamoa.mypage.repository;

import com.iteamoa.mypage.constant.DynamoDbEntityType;
import com.iteamoa.mypage.entity.LikeEntity;
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
public class LikeRepository {
    private final DynamoDbTable<LikeEntity> likeTable;

    public LikeRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        this.likeTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(LikeEntity.class));
    }

    public List<LikeEntity> queryLikeFeed(String pk){
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.USER, pk))  // PK 조건 설정
                .sortValue(DynamoDbEntityType.LIKE.getType())
        );

        final DynamoDbIndex<LikeEntity> likeFeedIndex = likeTable.index("Like-index");
        final SdkIterable<Page<LikeEntity>> pagedResult = likeFeedIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }

}
