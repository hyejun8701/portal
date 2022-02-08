package com.innocore.add.portal.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "system")
public class Info {

    @Id
    private String _id;
    private String os;
    private Integer cpu;
    private Integer ram;
    private Integer disk;
    private Integer network;

}
