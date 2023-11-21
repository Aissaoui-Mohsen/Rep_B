package com.neolians.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {

	/***
	 * Load an Xml Doc from a file
	 *
	 * @param fXmlFile file to load
	 * @return the element
	 * @throws IOException  if file does not exist
	 * @throws SAXException if file is not well formatted
	 */
	public static Document loadDocument(File fXmlFile) throws SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			return dBuilder.parse(fXmlFile);
		} catch (final ParserConfigurationException ignored) {

		}
		return null;
	}

	public static Document loadDocument(InputStream fXmlFile) throws SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			return dBuilder.parse(fXmlFile);
		} catch (final ParserConfigurationException ignored) {

		}
		return null;
	}

	/***
	 * Load an Xml Doc from a file
	 *
	 * @param xmlString xmlstring to load
	 * @return the element
	 * @throws IOException  if file does not exist
	 * @throws SAXException if file is not well formatted
	 */
	public static Document loadDocument(String xmlString) throws SAXException, IOException {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			final InputStream stream = IOUtils.toInputStream(xmlString, "UTF-8");
			return dBuilder.parse(stream);
		} catch (final ParserConfigurationException ignored) {

		}
		return null;
	}

	/**
	 * return a node element from an Xpath expression
	 *
	 * @param xmlDoc Xml Document to parse
	 * @param xPath  xPath query
	 * @return Selected node
	 * @throws XPathExpressionException if invalid xPath query
	 */
	public static Node selectSingleNode(Document xmlDoc, String xPath) throws XPathExpressionException {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		return (Node) xpath.evaluate(xPath, xmlDoc, XPathConstants.NODE);
	}

	/**
	 * return a NodeList from an Xpath expresiion
	 *
	 * @param xmlDoc Xml Document to parse
	 * @param xPath  xPath query
	 * @return Selected nodeList
	 * @throws XPathExpressionException if invalid xPath query
	 */
	public static NodeList selectNodes(Document xmlDoc, String xPath) throws XPathExpressionException {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		return (NodeList) xpath.evaluate(xPath, xmlDoc, XPathConstants.NODESET);
	}

	/**
	 * return a NodeList from an Xpath expresiion
	 *
	 * @param xmlNode xmlNode to query
	 * @param xPath   xPath query
	 * @return Selected nodeList
	 * @throws XPathExpressionException if invalid xPath query
	 */
	public static NodeList selectNodes(Node xmlNode, String xPath) throws XPathExpressionException {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		return (NodeList) xpath.evaluate(xPath, xmlNode, XPathConstants.NODESET);
	}

	/***
	 * Create a root Element name
	 *
	 * @param nodeName node name of the element name
	 * @return the element
	 */
	public static Element createRootElement(String nodeName) {
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();

			final Document newDoc = builder.newDocument();
			return newDoc.createElement(nodeName);
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * Transform an Xml document to a String with a pretty indentation
	 *
	 * @param xmlDocument Xml document
	 * @return the String representation
	 * @throws TransformerException                 If an unrecoverable error occurs
	 *                                              during the course of the
	 *                                              transformation.
	 * @throws TransformerFactoryConfigurationError When it is not possible to
	 *                                              create a Transformer instance.
	 */
	public static String prettyString(Document xmlDocument) throws TransformerException {
		final Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		final Writer out = new StringWriter();
		final DOMSource source = new DOMSource(xmlDocument);
		tf.transform(source, new StreamResult(out));
		return out.toString();
	}

	/***
	 * Save an Xml document to a file
	 *
	 * @param xmlDocument Xml document
	 * @param file        result file
	 * @throws TransformerException If an unrecoverable error occurs during the
	 *                              course of the transformation.
	 */
	public static void toFile(Document xmlDocument, File file) throws TransformerException {
		final Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		final DOMSource source = new DOMSource(xmlDocument);
		final StreamResult result = new StreamResult(file);
		tf.transform(source, result);
		System.out.println("Saved Xml file: '" + file.getAbsolutePath() + "'");
	}
}