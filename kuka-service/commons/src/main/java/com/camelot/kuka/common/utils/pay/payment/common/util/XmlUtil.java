package com.camelot.kuka.common.utils.pay.payment.common.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil
{

    public XmlUtil()
    {
    }

    public static Schema createSchema(File file)
        throws Exception
    {
        SchemaFactory schemafactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        return schemafactory.newSchema(file);
    }

    public static void validateViaDOM(Document document, Schema schema)
        throws Exception
    {
        DOMSource domsource = new DOMSource(document);
        Validator validator = schema.newValidator();
        validator.validate(domsource);
    }

    public static void validateViaDOM(String s, Schema schema)
        throws Exception
    {
        DOMSource domsource = new DOMSource(createDocument(s));
        Validator validator = schema.newValidator();
        validator.validate(domsource);
    }

    public static void validateViaSAX(String s, Schema schema)
        throws Exception
    {
        SAXSource saxsource = new SAXSource(new InputSource(new InputStreamReader(new ByteArrayInputStream(s.getBytes("UTF-8")))));
        Validator validator = schema.newValidator();
        validator.validate(saxsource);
    }

    public static void validateViaStreamSource(String s, Schema schema)
        throws Exception
    {
        StreamSource streamsource = new StreamSource(new ByteArrayInputStream(s.getBytes("UTF-8")));
        Validator validator = schema.newValidator();
        validator.validate(streamsource);
    }

    public static Document createDocument(String s)
        throws Exception
    {
        DocumentBuilder documentbuilder = createDocumentBuilder();
        return documentbuilder.parse(new ByteArrayInputStream(s.getBytes("UTF-8")));
    }

    public static Document createDocument(String s, String s1)
        throws Exception
    {
        DocumentBuilder documentbuilder = createDocumentBuilder();
        return documentbuilder.parse(new ByteArrayInputStream(s.getBytes(s1)));
    }

    public static Document createDocument(File file)
        throws Exception
    {
        DocumentBuilder documentbuilder = createDocumentBuilder();
        return documentbuilder.parse(file);
    }

    public static DocumentBuilder createDocumentBuilder()
        throws Exception
    {
        DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
        return documentbuilderfactory.newDocumentBuilder();
    }

    public static String getNodeText(Document document, String s)
        throws Exception
    {
        return getNodeText(document, s, 0);
    }

    public static String getNodeText(Document document, String s, int i)
        throws Exception
    {
        NodeList nodelist = document.getElementsByTagName(s);
        if(nodelist == null || i >= nodelist.getLength())
            return null;
        else
            return nodelist.item(i).getTextContent();
    }

    public static String getChildNodeText(Node node, String s)
    {
        NodeList nodelist = node.getChildNodes();
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
        {
            Node node1 = nodelist.item(j);
            if(node1.getNodeType() == 1 && node1.getNodeName() == s)
                return node1.getTextContent();
        }

        return null;
    }

    public static List getNodeList(Document document, String s)
        throws Exception
    {
        NodeList nodelist = document.getElementsByTagName(s);
        ArrayList arraylist = new ArrayList();
        if(nodelist == null)
            return null;
        for(int i = 0; i < nodelist.getLength(); i++)
            arraylist.add(nodelist.item(i).getTextContent());

        return arraylist;
    }

    public static String getNodeAttributeValue(Document document, String s, String s1)
        throws Exception
    {
        NodeList nodelist = document.getElementsByTagName(s);
        if(nodelist != null && nodelist.getLength() > 0)
        {
            Element element = (Element)nodelist.item(0);
            return element.getAttribute(s1);
        } else
        {
            return null;
        }
    }

    public static String getNodeText(Node node, String s)
        throws Exception
    {
        NodeList nodelist = node.getChildNodes();
        int i = nodelist.getLength();
        for(int j = 0; j < i; j++)
        {
            Node node1 = nodelist.item(j);
            if(node1.getNodeType() == 1 && node1.getNodeName().equals(s))
                return node1.getTextContent();
        }

        return null;
    }

    public static String createPrettyFormat(Document document)
        throws Exception
    {
        return createPrettyFormat(document, "UTF-8");
    }

    public static String createPrettyFormat(Document document, String s)
        throws Exception
    {
        DOMSource domsource = new DOMSource(document);
        TransformerFactory transformerfactory = TransformerFactory.newInstance();
        Transformer transformer = transformerfactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("encoding", s);
        StringWriter stringwriter = new StringWriter();
        transformer.transform(domsource, new StreamResult(stringwriter));
        return stringwriter.toString();
    }

    public static String createCompactFormat(Document document)
        throws Exception
    {
        return createCompactFormat(document, "UTF-8");
    }

    public static String createCompactFormat(Document document, String s)
        throws Exception
    {
        DOMSource domsource = new DOMSource(document);
        TransformerFactory transformerfactory = TransformerFactory.newInstance();
        Transformer transformer = transformerfactory.newTransformer();
        transformer.setOutputProperty("encoding", s);
        StringWriter stringwriter = new StringWriter();
        transformer.transform(domsource, new StreamResult(stringwriter));
        return stringwriter.toString();
    }
}
