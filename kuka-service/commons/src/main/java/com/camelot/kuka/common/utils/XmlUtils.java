package com.camelot.kuka.common.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/***
 * XML工具类
 */
public class XmlUtils {


    /***
     * 将javaBean转换成XML
     */
    public static String converToXml(Object obj, String encoding) {
        StringBuffer result = new StringBuffer();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> \n");
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,encoding);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj,writer);
            result.append(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
