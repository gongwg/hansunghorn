package answer_control_Control.TV;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlGene {
 /**
  * @param args
  */
XmlGene()
{
  // TODO Auto-generated method stub
  Document doc = new Document();  
 
  Element root = new Element("Topic");
  
  Element pack = new Element("Answer");
  
  Element pack_name = new Element("ask");
  
  root.addContent(pack);//root element �� ���� element �� �����
  pack.addContent(pack_name); //package element �� ������ package-name �����
  
  root.setText("Topic");
  pack_name.setText("com.ysci.theme.aaa");
  //package-name element �� value ���� text �� �־� �ֱ�
  
  doc.setRootElement(root);
  
  try {                                                             
     FileOutputStream out = new FileOutputStream("d:\\test.xml"); 
     //xml ������ ������ ���� ��ο� ���� �̸� ������ �ֱ�
      XMLOutputter serializer = new XMLOutputter();                 
                                                                   
     Format f = serializer.getFormat();                            
     f.setEncoding("UTF-8");
      //encoding Ÿ���� UTF-8 �� ����
      f.setIndent(" ");                                             
     f.setLineSeparator("\r\n");                                   
     f.setTextMode(Format.TextMode.TRIM);                          
     serializer.setFormat(f);                                      
                                                                   
     serializer.output(doc, out);                                  
     out.flush();                                                  
     out.close();    
     
     //String ���� xml ���
     // XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("UTF-8")) ;
     // System.out.println(outputter.outputString(doc));
  } catch (IOException e) {                                         
     System.err.println(e);                                        
 }                                                                 
 }
}
