package objet;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class ContactManager {

    private static class Person {
        int id;
        String firstname = "";
        String lastname = "";
        String street = "";
        String postalCode = "";
        String city = "";
        String email = "";
    }

    public static void main(String[] args) throws Exception {

        // Initialise une chaîne de caractère avec le chemin du fichier XML.
        // Attention : Les backslashes (\) du chemin Windows sont dédoublés
        // car dans une chaîne de caractères, le backslash est le caractère
        // d’échappement qui permet d’écrire des caractères spéciaux tels que
        // \r (carriage return), \n (line feed) ou \t (tab).

        // Crée un objet de type File avec le chemin du fichier XML.
        File xmlFile = new File("conf.xml");

        // Charge les données des personnes qui se trouve dans le fichier XML
        // dans un objet de type List de Person et affecte une référence à cet
        // objet à la variable personList.
        List<Person> personList = loadPersonDataFromXml(xmlFile);

        // Affiche le résultat dans la sortie standard (System.out).
        printPersonList(System.out, personList);
    }

    /**
     * Charge les données des personnes qui se trouvent dans le fichier XML dans un objet 
     * de type List de Person et renvoie l’objet.
     *
     * @param file le fichier XML à lire
     * @return l’objet person correspondant.
     */
    private static List<Person> loadPersonDataFromXml(File file) throws Exception {

        // Crée un tableau dynamique.
        List<Person> personList = new ArrayList<>();

        // Crée un objet Document qui représente les données du fichier XML
        // sous la forme d’une hiérarchie d’objets de type Node. Un objet de
        // type nœud peut représenter aussi bien un élément, qu’un nœud
        // de texte ou un attribut.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        // Recherche tous les elements <person>
        NodeList personNodeList = doc.getElementsByTagName("person");

        // Pour chaque élément XML de la liste
        for (int i = 0; i < personNodeList.getLength(); i = i + 1) {
            Node node = personNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
               
                // Récupère l'élément
                Element personElement = (Element)node;

                // Crée un nouvel objet de type Person
                Person person = new Person();

                person.id = Integer.parseInt(personElement.getAttribute("id"));
                person.firstname = personElement.getElementsByTagName("firstname").item(0).getTextContent();
                person.lastname = personElement.getElementsByTagName("lastname").item(0).getTextContent();
                person.postalCode = personElement.getElementsByTagName("postal-code").item(0).getTextContent();
                person.street = personElement.getElementsByTagName("street").item(0).getTextContent();
                person.city = personElement.getElementsByTagName("city").item(0).getTextContent();
                person.email = personElement.getElementsByTagName("email").item(0).getTextContent();
                
                // Ajoute la personne à la liste
                personList.add(person);
    
            }
        }

        // Renvoie la référence à l’objet personList
        return personList;
    }

    /**
     * Ecrit les élément d’une liste d'objet de type Person dans le flux de sortie
     * passé en paramètre.
     *
     * @param out le flux de sortie
     * @param personList la liste d'objet de type Person
     */
    private static void printPersonList(PrintStream out, List<Person> personList) {
        for (int i = 0; i < personList.size(); i = i + 1) {
            Person person = personList.get(i);
            printPerson(out, person);
        }
    }

    /**
     * Ecrit les données d’un objet de type Person dans le flux de sortie
     * passé en paramètre.
     *
     * @param out le flux de sortie
     * @param person l’objet de type Person
     */
    private static void printPerson(PrintStream out, Person person) {

            out.printf("(%d) %s %s, %s, %s %s\r\n",
                    person.id,
                    person.firstname,
                    person.lastname,
                    person.street,
                    person.postalCode,
                    person.city);
    }
}