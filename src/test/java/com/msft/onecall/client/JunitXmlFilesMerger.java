package com.msft.onecall.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class JunitXmlFilesMerger {

    public boolean checkIfNodeAlreadyPresentInFile(File file, Node nodeFromOtherFile) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);
        Element catalog = document.getDocumentElement();

        NodeList tests = catalog.getChildNodes();

        Element elementFromOtherFile = (Element) nodeFromOtherFile;
        for (int i = 0, ii = 0, n = tests.getLength(); i < n; i++) {
            Node child = tests.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element test = (Element) child;
            ii++;

            String id = test.getAttribute("name");
//            System.out.println("Value of id = " + id);

            if (elementFromOtherFile.getAttribute("name").equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void compareAndWriteToFile(File file1, File file2) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document1 = builder.parse(file1);
        Element xmlTree1 = document1.getDocumentElement();
        NodeList testSuite1 = xmlTree1.getChildNodes();

        Document document2 = builder.parse(file2);
        Element xmlTree2 = document2.getDocumentElement();

        for (int i = 0, ii = 0, n = testSuite1.getLength(); i < n; i++) {
            Node child = testSuite1.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE)
                continue;
            ii++;

            boolean nodeAlreadyPresent = checkIfNodeAlreadyPresentInFile(file2, child);
            if (!nodeAlreadyPresent) {
                Node firstNodeDoc = document2.importNode(child, true);
                xmlTree2.appendChild(firstNodeDoc);
            }
        }
        TransformerFactory tfact = TransformerFactory.newInstance();
        Transformer tform = tfact.newTransformer();
        tform.setOutputProperty(OutputKeys.INDENT, "yes");
        tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
        tform.transform(new DOMSource(document2), new StreamResult(new File("./src/test/resources/merge.xml")));
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        JunitXmlFilesMerger junitXmlFilesMerger = new JunitXmlFilesMerger();
//        junitMerger.compareAndWriteToFile(new File("C:\\Users\\harishrane\\Desktop\\test_merge\\JUnit_1.xml"),new File("C:\\Users\\harishrane\\Desktop\\test_merge\\JUnit_2.xml"));
        junitXmlFilesMerger.compareAndWriteToFile(new File(args[0]), new File(args[1]));
    }
}