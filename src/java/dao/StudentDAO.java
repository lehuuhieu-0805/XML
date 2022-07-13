/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.StudentDTO;
import handler.StudentHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.XMLHelper;

/**
 *
 * @author lehuuhieu
 */
public class StudentDAO implements Serializable {

    private List<StudentDTO> students;

    public List<StudentDTO> getStudents() {
        return students;
    }

    public StudentDTO checkLogin(String username, String password, Document document) throws XPathExpressionException {
        StudentDTO dto = null;
        //1. Access DOM TREE
        if (document != null) {
            //2. Create Xpath Expression
            String expression = "//student[@id='"
                    + username
                    + "' and normalize-space(password)='"
                    + password
                    + "' and not (normalize-space(status)='dropout')]";

            //3. Create Xpath Object
            XPath xPath = XMLHelper.getXPath();
            //4. Evaluate Xpath Expression
            Node student = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
            //5. Process result
            if (student != null) {
                expression = "lastname";
                String lastName = (String) xPath.evaluate(expression, student, XPathConstants.STRING);
                expression = "middlename";
                String middleName = (String) xPath.evaluate(expression, student, XPathConstants.STRING);
                expression = "firstname";
                String firstName = (String) xPath.evaluate(expression, student, XPathConstants.STRING);
                String fullName = lastName.trim() + " "
                        + middleName.trim() + " "
                        + firstName.trim();
                dto = new StudentDTO(username, null, fullName, null, null, null);
            }

        }; //DOM TRE has existed

        return dto;
    }

    public void findStudentbyAdress(String searchValue, Document document) throws XPathExpressionException {
        if (document != null) {
            String expression = "//student[contains(address, '"
                    + searchValue
                    + "')]";
            XPath xpath = XMLHelper.getXPath();
            NodeList studentList = (NodeList) xpath.evaluate(
                    expression, document, XPathConstants.NODESET);
            for (int i = 0; i < studentList.getLength(); i++) {
                Node student = studentList.item(i);

                expression = "@id";
                String id = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "@class";
                String sClass = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "lastname";
                String lastName = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "middlename";
                String middleName = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "firstname";
                String firstName = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "address";
                String address = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "sex";
                String sex = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                expression = "status";
                String status = (String) xpath.evaluate(expression, student, XPathConstants.STRING);

                String fullName = lastName.trim() + " " + middleName.trim() + " " + firstName.trim();

                StudentDTO dto = new StudentDTO(
                        id, sClass, fullName, address.trim(), sex.trim(), status.trim());
                if (this.students == null) {
                    this.students = new ArrayList<>();
                }
                //student has existed
                this.students.add(dto);
            }//end raversing student list
        } //end DOM TREE has existed
    }

    public boolean deleteStudent(String id, Document document, String xmlFile) throws XPathExpressionException, TransformerException {
        boolean result = false;
        if (document != null) {
            //1. find student
            String expression = "//student[@id='"
                    + id
                    + "']";
            XPath xPath = XMLHelper.getXPath();
            Node student = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
            //2. find parent
            if (student != null) {
                Node students = student.getParentNode();
                if (students != null) {
                    //3. remove child
                    students.removeChild(student);
                    //4. transformer
                    XMLHelper.transformerDOMtoFile(document, xmlFile);
                    result = true;
                }
            }// end student has existed
        }//end DOM TREE has existed
        return result;
    }

    public boolean updateStudent(String id, String sClass, String address, String status, Document document, String xmlFile) throws XPathExpressionException, TransformerException {
        boolean result = false;
        if (document != null) {
            String expression = "//student[@id='"
                    + id
                    + "']";
            XPath xPath = XMLHelper.getXPath();
            Node student = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
            if (student != null) {
                NamedNodeMap attrs = student.getAttributes();
                attrs.getNamedItem("class").setNodeValue(sClass);

                expression = "//student[@id='"
                        + id
                        + "']/status";

                Node updateNode = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
                if (updateNode != null) {
                    updateNode.setTextContent(status);
                }

                expression = "//student[@id='"
                        + id
                        + "']/address";

                updateNode = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
                if (updateNode != null) {
                    updateNode.setTextContent(address);
                }

                XMLHelper.transformerDOMtoFile(document, xmlFile);
                result = true;
            }
        }
        return result;
    }

    public String checkLoginwithSAX(String username, String password, String xmlFile) throws ParserConfigurationException, SAXException, IOException {

        String result = null;

        //1. Create deafault handler object
        StudentHandler handler = new StudentHandler(username, password);
        //2. Register handler objec to parse
        XMLHelper.parseFileToSAX(xmlFile, handler);
        //3. Process result
        if (handler.isFound()) {
            result = handler.getFullname();
        }

        return result;
    }

    public boolean createStudent(String id, String sClass, String lastName, String middleName, String firstName, String male, String address, String password, String status, Document doc, String xmlFile) throws TransformerException {

        if (doc != null) {
            Map<String, String> studentAttrs = new HashMap<String, String>();
            studentAttrs.put("id", id);
            studentAttrs.put("class", sClass);

            Element studentNode = XMLHelper.createElement(doc, "student", null, studentAttrs);
            Element lastnameNode = XMLHelper.createElement(doc, "lastname", lastName, null);
            Element middlenameNode = XMLHelper.createElement(doc, "middlename", middleName, null);
            Element firstnameNode = XMLHelper.createElement(doc, "firstname", firstName, null);
            Element maleNode = XMLHelper.createElement(doc, "male", male, null);
            Element addressNode = XMLHelper.createElement(doc, "address", address, null);
            Element passwordNode = XMLHelper.createElement(doc, "password", password, null);
            Element statusNode = XMLHelper.createElement(doc, "status", status, null);

            studentNode.appendChild(lastnameNode);
            studentNode.appendChild(middlenameNode);
            studentNode.appendChild(firstnameNode);
            studentNode.appendChild(maleNode);
            studentNode.appendChild(addressNode);
            studentNode.appendChild(passwordNode);
            studentNode.appendChild(statusNode);

            NodeList students = doc.getElementsByTagName("students");
            students.item(0).appendChild(studentNode);

            XMLHelper.transformerDOMtoFile(doc, xmlFile);

            return true;
        }

        return false;
    }

    public StudentDTO checkloginwithStAXCursor(String username, String password, String xmlFile) throws IOException, XMLStreamException {
        StudentDTO result = null;
        InputStream is = null;
        XMLStreamReader reader = null;
        try {
            is = new FileInputStream(xmlFile);
            //1. Create StAX Cursor Object
            reader = XMLHelper.parseFileToStAXCursor(is);
            //2. Using Cursor to traverse Stream
            String fullname = "";
            while(reader.hasNext()){
                int currentCursor = reader.next();
                
                if(currentCursor == XMLStreamConstants.START_ELEMENT){
                    String tagName = reader.getLocalName();
                    
                    if(tagName.equals("student")){
                        String id = reader.getAttributeValue("", "id");
                        
                        if(id.equals(username)){
                            String lastname = XMLHelper.getTextContent("lastname", reader);
                            String middlename = XMLHelper.getTextContent("middlename", reader);
                            String firstname = XMLHelper.getTextContent("firstname", reader);
                            fullname = lastname.trim() + " " + middlename.trim() + " " + firstname.trim();
                            
                            String pass = XMLHelper.getTextContent("password", reader);
                            if(pass.trim().equals(password)){
                                String status = XMLHelper.getTextContent("status", reader);
                                
                                if(!status.trim().equals("dropout")){
                                    result = new StudentDTO(username, null, fullname, null, null, null);
                                    break;
                                }
                            }
                            
                        }
                    }//student tag is pointed
                }//end start element
            }//end cursor does not point to end stream
            //3. Process result
        } finally {
            if(reader != null){
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }

        return result;
    }
}
