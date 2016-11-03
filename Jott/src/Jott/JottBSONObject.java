package Jott;

import java.lang.Integer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

public class JottBSONObject implements BSONObject {

	HashMap<Integer, String> lines;
	
	public JottBSONObject() {
		lines = new HashMap<Integer, String>();
	}
	
	public boolean createNewLine(int linenum, String linestr) {
		if(lines.containsKey(linenum))
			return false;
		else
		{
			lines.put(new Integer(linenum), linestr);
			return true;
		}
	}
	
	@Override
	public boolean containsField(String arg0) {
		if(arg0.equals("linenum"))
			return true;
		else if(arg0.equals("linestr"))
			return true;
		else
			return false;
	}

	@Override
	public boolean containsKey(String arg0) {
		Integer arg0Int = new Integer(arg0);
		if(lines.containsKey(arg0Int))
			return true;
		else
			return false;
	}

	@Override
	public Object get(String arg0) {
		Integer arg0Int = new Integer(arg0);
		String linestr = lines.get(arg0Int);
		
		return linestr;
	}

	@Override
	public Set<String> keySet() {
		return (Set<String>) lines.values();	
	}

	@Override
	public Object put(String arg0, Object arg1) {
		Integer arg0Int = new Integer(arg0);
		if(lines.containsKey(arg0Int))
			lines.replace(arg0Int, (String) arg1);
		else
			lines.put(arg0Int, (String)arg1);
		
		return lines.get(arg0Int);
	}

	@Override
	public void putAll(BSONObject arg0) {
		lines = (HashMap<Integer, String>) arg0.toMap();
	}

	@Override
	public void putAll(Map arg0) {
		lines = (HashMap<Integer, String>) arg0;
	}

	@Override
	public Object removeField(String arg0) {
		
		Integer arg0Int = new Integer(arg0);
		
		if(lines.containsKey(arg0Int)) {
			return lines.remove(arg0Int);
		}
		else
			return null;
	}

	@Override
	public Map toMap() {
		HashMap map = new HashMap();
		Integer lineNum = new Integer(2);
		
		String lineNumStr = new String("linenum");
		map.put(lineNumStr, lineNum);
		
		
		return map;
	}

}
