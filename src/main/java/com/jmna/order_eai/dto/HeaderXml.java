package com.jmna.order_eai.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 주문 정보 XML중 Header 값만을 담을 클래스
 */
@Data
public class HeaderXml {

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    @JacksonXmlProperty(localName = "NAME")
    private String name;

    @JacksonXmlProperty(localName = "ADDRESS")
    private String address;

    @JacksonXmlProperty(localName = "STATUS")
    private String status;

}
