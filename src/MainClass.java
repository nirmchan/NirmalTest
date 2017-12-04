import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.Document;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class MainClass {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsURL = "http://t1c1m418.vci.att.com:10168/aff/DynamicProcessManagerSvc";
		URL url = new URL(wsURL);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:exec=\"http://amdocs.com/oss/aff/schema/executionPlanView\">\r\n" + 
				"   <soapenv:Header/>\r\n" + 
				"   <soapenv:Body>\r\n" + 
				"      <exec:getActivityRequest>\r\n" + 
				"         <exec:planID>2E9E56CB35E848F6A68AAC79C3093164</exec:planID>\r\n" + 
				"         <exec:activityID>AE9D219AF5FD4D149780432CB1366AD5</exec:activityID>\r\n" + 
				"      </exec:getActivityRequest>\r\n" + 
				"   </soapenv:Body>\r\n" + 
				"</soapenv:Envelope>";
		 
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
	/*	String SOAPAction =
		"http://t1c1m418.vci.att.com:10168/aff/DynamicProcessManagerSvc";*/
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length",
		String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		//httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		//Write the content of the request to the outputstream of the HTTP Connection.
		out.write(b);
		out.close();
		//Ready with sending the request.
		 
		//Read the response.
		InputStreamReader isr =
		new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		 
		//Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
		outputString = outputString + responseString;
		
		 
		//Write the SOAP message formatted to the console.
		String formattedSOAPResponse = prettyFormat(outputString,2);
		System.out.println(formattedSOAPResponse);
		}
		
	}
		public static String prettyFormat(String input, int indent) {
		    try {
		        Source xmlInput = new StreamSource(new StringReader(input));
		        StringWriter stringWriter = new StringWriter();
		        StreamResult xmlOutput = new StreamResult(stringWriter);
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        transformerFactory.setAttribute("indent-number", indent);
		        Transformer transformer = transformerFactory.newTransformer(); 
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.transform(xmlInput, xmlOutput);
		        return xmlOutput.getWriter().toString();
		    } catch (Exception e) {
		        throw new RuntimeException(e); // simple exception handling, please review it
		    }
		}

}
