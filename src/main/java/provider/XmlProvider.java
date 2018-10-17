package provider;

import data.Currency;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class XmlProvider implements Provider{

    public Set<Currency> getCurrencies(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try{
            builder = factory.newDocumentBuilder();
            document = builder.parse(new URL("https://www.nbp.pl/kursy/xml/LastA.xml").openStream());
        } catch (SAXException | ParserConfigurationException | IOException e){
            throw new RuntimeException(e);
        }
        NodeList list = document.getElementsByTagName("pozycja");
        return Stream.iterate(0, i-> i=i+1)
                .limit(list.getLength())
                .map(i -> {
                    String name = null;
                    Integer converter = null;
                    String code = null;
                    Double average = null;
                    Node item = list.item(i);
                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) item;
                        name = element.getElementsByTagName("nazwa_waluty").item(0).getTextContent();
                        converter = Integer.valueOf(element.getElementsByTagName("przelicznik").item(0).getTextContent());
                        code = element.getElementsByTagName("kod_waluty").item(0).getTextContent();
                        String avg = element.getElementsByTagName("kurs_sredni").item(0).getTextContent();
                        average = Double.valueOf(avg.replace(",", "."));
                    }
                    return new Currency(name, converter, code, average);
                    })
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
