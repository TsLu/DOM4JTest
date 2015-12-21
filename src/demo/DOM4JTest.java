package demo;

import jdk.internal.org.xml.sax.Attributes;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 使用DOM4J方法解析xml文档
 * Created by lutingshu on 2015/12/21.
 */
public class DOM4JTest {
    private static ArrayList<Books>bookList = new ArrayList<Books>();

    public static void main(String[]args){

        //创建SAXReader的对象
        SAXReader reader = new SAXReader();
        try {
            //通过reader对象的read方法加载books.xml文档，获取document对象
            Document document = reader.read(new File("books.xml"));
            //通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            //通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            while (it.hasNext()){
                Books bookAdd = new Books();
                System.out.println("-------开始解析某一本书-----------");
                Element book = (Element) it.next();
                //获取book的属性名以及属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr: bookAttrs){
                    System.out.println("属性名： " + attr.getName() + "----属性值： " + attr.getValue());
                    if (attr.getName().equals("category")){
                        bookAdd.setCategory(attr.getValue());
                    }
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()){
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名： " + bookChild.getName() + "----节点值：" + bookChild.getStringValue());
                    if (bookChild.getName().equals("title")){
                        bookAdd.setTitle(bookChild.getStringValue());
                        List<Attribute> bookChildAttrs = bookChild.attributes();
                        for (Attribute attrChild: bookChildAttrs){
                            System.out.println("属性名： " + attrChild.getName() + "----属性值： " + attrChild.getValue());
                            bookAdd.setLanguage(attrChild.getValue());
                        }
                    }
                    else if (bookChild.getName().equals("author")){
                        bookAdd.setAuthor(bookChild.getStringValue());
                    }
                    else if (bookChild.getName().equals("year")){
                        bookAdd.setYear(bookChild.getStringValue());
                    }
                    else if (bookChild.getName().equals("price")){
                        bookAdd.setPrice(bookChild.getStringValue());
                    }


                }
                System.out.println("------遍历完某一本书---------");
                bookList.add(bookAdd);
                bookAdd = null;
            }

            System.out.println();

            System.out.println("队列中有" + bookList.size() + "本书\n" + "------开始遍历队列中的书本---------");

            Iterator listIt = bookList.iterator();
            for (Books bookitem : bookList){
               System.out.println("第" +( bookList.indexOf(bookitem) + 1) + "本书包含的属性为： ");
               System.out.println(bookitem.getCategory());
               System.out.println(bookitem.getLanguage());
               System.out.println(bookitem.getTitle());
               System.out.println(bookitem.getAuthor());
               System.out.println(bookitem.getYear());
               System.out.println(bookitem.getPrice());
            }

         }
         catch (DocumentException E){
            E.printStackTrace();
        }
    }
}
