/**
 * 
 */
package com.ivoslabs.records.converters;

/**
 * Interface to be implemented in converter classes<br>
 * <br>
 * <b>Custom converter</b>
 * 
 * <pre>
 * <code>public class UserConverter implements FieldConverter&lt;User> {
 
    /&#42; Creates a UserConverter instance &#42;&#47;
    public UserConverter() {
	super();
    }
 
    /&#42;
     &#42; (non-Javadoc)
     &#42; 
     &#42; &#64;see com.ivoslabs.records.converters.FieldConverter#toString(java.lang.Object, java.lang.String[])
     &#42;&#47;
    public String toString(User object, String... args) {
	String str;
	Validate.notNull(args, "UserConverter requiere two arguments");
	Validate.isTrue(args.length == 2, "UserConverter requiere two arguments");
	Validate.isTrue(!args[0].isEmpty(), "UserConverter requiere two arguments");

	if (object != null) {
	    if (args[0].equals("usePrefix"){
	    	str = user.getId() + "-" + user.getNamePrefix() + user.getFirstName() + user.getLastName();
	    } else {
	    	str = user.getId() + "-" + user.getFirstName() + user.getLastName();
	    }
	} else {
	    str = "";
	}

	return str;
    }
 
    /&#42;
     &#42; (non-Javadoc)
     &#42; 
     &#42; &#64;see com.ivoslabs.records.converters.FieldConverter#toObject(java.lang.String, java.lang.String[])
     &#42;&#47;
    public User toObject(String string, String... args) {
	Validate.notNull(args, "UserConverter requiere two arguments");
	Validate.isTrue(args.length == 2, "UserConverter requiere two arguments");
	Validate.isTrue(!args[0].isEmpty(), "UserConverter requiere two arguments");
	
	int id = Integer.parseInt(string.split("-")[0]);
	return ProjectDaos.userDAO.findByid(id);
    }
    
  }</code>
 * </pre>
 * 
 * @param E Required type
 * @author www.ivoslabs.com
 *
 */
public interface FieldConverter<E extends Object> {

    /**
     * Convert a E instance to String using the received arguments
     * 
     * @param object Instance of E to convert
     * @param args   Arguments (Optionals)
     * @return The built String
     */
    String toString(E object, String... args) throws Exception;

    /**
     * Convert a String to E instance using the received arguments
     * 
     * @param string
     * @param args   Arguments (Optionals)
     * @return The built E
     */
    E toObject(String string, String... args) throws Exception;
}
