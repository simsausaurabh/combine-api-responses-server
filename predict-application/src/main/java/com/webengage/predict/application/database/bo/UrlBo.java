package com.webengage.predict.application.database.bo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity(name = "url")
@Table(name = "url")
public class UrlBo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String url1;

    private String url2;

    private String url3;
}
