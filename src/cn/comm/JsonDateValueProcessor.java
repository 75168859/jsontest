package cn.comm;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json 日期处理器
 * @author liuhuan
 *
 */
public class JsonDateValueProcessor implements JsonValueProcessor{

	private String format = "yyyy-MM-dd";
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig config) {
		return process(value,config);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig config) {
		
		return process(value,config);
	}

	
	public Object process(Object value, JsonConfig jsonConfig){
		if(value instanceof Date) {
			return new SimpleDateFormat(format).format((Date)value);
		}
		return value == null ? null : value.toString();
	}
	
}
