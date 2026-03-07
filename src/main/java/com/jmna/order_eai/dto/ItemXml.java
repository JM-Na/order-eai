package com.jmna.order_eai.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 주문 정보 XML중 Item 값만을 담을 클래스
 */
@Data
public class ItemXml {

    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;

    @JacksonXmlProperty(localName = "ITEM_ID")
    private String itemId;

    @JacksonXmlProperty(localName = "ITEM_NAME")
    private String itemName;

    @JacksonXmlProperty(localName = "PRICE")
    private String price;

}
