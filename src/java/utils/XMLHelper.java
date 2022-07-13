/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author lehuuhieu
 */
public class XMLHelper implements Serializable {

    public static Document buildDOMFromFile(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dom = factory.newDocumentBuilder();
        Document document = dom.parse(xmlFile);

        return document;
    }

    public static XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        return xPath;
    }

    public static void transformerDOMtoFile(Node node, String xmlFile) throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Source src = new DOMSource(node);
        Result result = new StreamResult(xmlFile);

        transformer.transform(src, result);
    }

    public static void parseFileToSAX(String xmlFile, DefaultHandler handler) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser sax = factory.newSAXParser();
        sax.parse(xmlFile, handler);
    }

    public static Element createElement(Document doc, String elementName, String elementValue, Map<String, String> attributes) {
        if (doc != null) {
            Element element = doc.createElement(elementName);
            if (elementValue != null) {
                element.setTextContent(elementValue);
            }
            if (attributes != null) {
                if (!attributes.isEmpty()) {
                    for (Map.Entry<String, String> entry : attributes.entrySet()) {
                        element.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
            }
            return element;
        }
        return null;
    }

    public static XMLStreamReader parseFileToStAXCursor(InputStream is) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        return reader;
    }

    public static String getTextContent(String elementName, XMLStreamReader reader) throws XMLStreamException {
        String result = null;

        if (elementName == null) {
            return null;
        }

        if (elementName.trim().isEmpty()) {
            return null;
        }

        if (reader == null) {
            return null;
        }
        //reader has existed
        while (reader.hasNext()) {      
            int currentCursor = reader.getEventType();
            
            if(currentCursor == XMLStreamConstants.START_ELEMENT){
                String tagName = reader.getLocalName();
                
                if(tagName.equals(elementName)){
                    reader.next();//point value
                    result = reader.getText();
                    reader.nextTag();//point to end Element
                    break;
                }//target tag is pointed
            }//start tag is pointed
            
            reader.next();
        }//end reader does not point to end stream

        return result;
    }
}
