import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
	
	private Map<String, String> valueMap = new HashMap();
	private String template;

	public Template(String string) {
		this.template = string;
	}

	public void set(String string, String string2) {
		this.valueMap.put(string, string2);		
	}

	public String evaluate() {
		template = replaceVariables();
		checkForMissingValues(template);
		return template;
	}
	
	private String replaceVariables() {
	    String result = template;
	    for (Entry<String, String> entry : valueMap.entrySet()) {
	        String regex = "\\$\\{" + entry.getKey() + "\\}";
	        result = result.replaceAll(regex, entry.getValue());
	    }
	     return result;
	}
	
	private void checkForMissingValues(String result) {
		Matcher m = Pattern.compile("\\$\\{.+\\}").matcher(result);
        if (m.find()) {
            throw new MissingValueException("No value for " + m.group());
        }
	}

}
