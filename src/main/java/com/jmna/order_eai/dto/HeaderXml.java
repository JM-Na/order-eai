package com.jmna.order_eai.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

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
