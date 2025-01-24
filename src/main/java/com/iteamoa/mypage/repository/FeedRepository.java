package com.iteamoa.mypage.repository;

import com.iteamoa.mypage.constant.DynamoDbEntityType;
import com.iteamoa.mypage.entity.FeedEntity;
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
public class FeedRepository {
    private final DynamoDbTable<FeedEntity> feedTable;
    private final DynamoDbIndex<FeedEntity> searchByCreatorIndex;

    public FeedRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.feedTable = enhancedClient.table("IM_MAIN_TB", TableSchema.fromBean(FeedEntity.class));
        this.searchByCreatorIndex = feedTable.index("SearchByCreator-index");
    }

    public List<FeedEntity> findFeedByCreatorIdAndSk(String creatorId, String sk) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k
                .partitionValue(KeyConverter.toPk(DynamoDbEntityType.USER, creatorId))
                .sortValue(KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, sk))
        );
        final SdkIterable<Page<FeedEntity>> pagedResult = searchByCreatorIndex.query(q -> q
                .queryConditional(queryConditional)
                .scanIndexForward(false)
                .attributesToProject());

        return pagedResult.stream()
                .flatMap(page -> page.items().stream())
                .collect(Collectors.toList());
    }

    public FeedEntity getFeed(String feedId, String feedType){
        return feedTable.getItem(KeyConverter.toKey(
                KeyConverter.toPk(DynamoDbEntityType.FEED, feedId),
                KeyConverter.toPk(DynamoDbEntityType.FEEDTYPE, feedType)
        ));
    }

    public void updateFeed(FeedEntity feedEntity){
        feedTable.updateItem(feedEntity);
    }

}
