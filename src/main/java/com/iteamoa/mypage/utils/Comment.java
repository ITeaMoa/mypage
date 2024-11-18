package com.iteamoa.mypage.utils;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDateTime;

@Setter
@DynamoDbBean
public class Comment {
    private String userId;
    private String comment;
    private LocalDateTime timestamp;
    private String name = "none";

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("UserID")
    public String getUserId() {
        return userId;
    }

    @DynamoDbAttribute("Comment")
    public String getComment() {
        return comment;
    }

    @DynamoDbConvertedBy(LocalDateTimeConverter.class)
    @DynamoDbAttribute("Timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    //localDateTime객체를 다이나모 디비에서 문자열로 저장하고 다시 변환할수있게 하는 클래스
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime> {

        @Override
        public AttributeValue transformFrom(LocalDateTime input) {
            if (input == null) {
                return null;
            }
            return AttributeValue.builder().s(input.toString()).build(); //localdatetime을 문자열로 변환
        }

        @Override
        public LocalDateTime transformTo(AttributeValue attributeValue) {
            if (attributeValue == null || attributeValue.nul() != null && attributeValue.nul()) {
                return null;
            }
            return LocalDateTime.parse(attributeValue.s()); //문자열을 LocalDateTime으로 변환
        }

        @Override
        public EnhancedType<LocalDateTime> type() {
            return EnhancedType.of(LocalDateTime.class); // 변환할 타입을 정의
        }

        @Override
        public AttributeValueType attributeValueType() {
            return AttributeValueType.S; // DynamoDB에서 문자열로 처리
        }
    }
}