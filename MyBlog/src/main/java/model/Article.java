package model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Article {
    private Integer id;
    private Date createTime;
    private Date updateTime;
    private String title;
    private String content;
    private Integer readCount;
    private Integer state;
    private Integer userId;
}
