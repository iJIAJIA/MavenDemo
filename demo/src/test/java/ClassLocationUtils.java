

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;


public class ClassLocationUtils {

    /**
     * find the location of the class come from
     * @param cls
     * @return
     */
    public static String where(final Class cls) {
        if (cls == null)throw new IllegalArgumentException("null input: cls");
        URL result = null;
        final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
        final ProtectionDomain pd = cls.getProtectionDomain();
        if (pd != null) {
            final CodeSource cs = pd.getCodeSource();
            if (cs != null) result = cs.getLocation();
            if (result != null) {
                if ("file".equals(result.getProtocol())) {
                    try {
                        if (result.toExternalForm().endsWith(".jar") ||
                                result.toExternalForm().endsWith(".zip"))
                            result = new URL("jar:".concat(result.toExternalForm())
                                    .concat("!/").concat(clsAsResource));
                        else if (new File(result.getFile()).isDirectory())
                            result = new URL(result, clsAsResource);
                    }
                    catch (MalformedURLException ignore) {}
                }
            }
        }
        if (result == null) {
            final ClassLoader clsLoader = cls.getClassLoader();
            result = clsLoader != null ?
                    clsLoader.getResource(clsAsResource) :
                    ClassLoader.getSystemResource(clsAsResource);
        }
        return result.toString();
    }
    
    public static String where(final String cls) {
    	try {
			Class<?> clazz = Class.forName(cls);
			return where(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

    
    public static void main(String[] args) {
    	String magicNameSAXParser = "org.apache.xerces.parsers.SAXParser";
    	String magicNameXMLReader = "org.xml.sax.XMLReader";
    	String magicNameSAXReader = "org.dom4j.io.SAXReader";
    	String magicNameXMLReaderFactory = "org.xml.sax.helpers.XMLReaderFactory";
//    	log.info("magicNameSAXParser from: " + ClassLocationUtils.where(magicNameSAXParser));
//    	log.info("magicNameXMLReader from: " + ClassLocationUtils.where(magicNameXMLReader));
//    	log.info("magicNameSAXReader from: " + ClassLocationUtils.where(magicNameSAXReader));
//    	log.info("magicNameXMLReaderFactory from: " + ClassLocationUtils.where(magicNameXMLReaderFactory));

	}
}