package com.jmna.order_eai.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JacksonXmlRootElement(localName = "Request")
public class RequestXml {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "HEADER")
    private List<HeaderXml> headers = new ArrayList<>();

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ITEM")
    private List<ItemXml> items = new ArrayList<>();

    public void setHeaders(HeaderXml header) {
        this.headers.add(header);
    }

    public void setItems(ItemXml item) {
        this.items.add(item);
    }

}

