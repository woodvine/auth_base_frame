package com.platform.base.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @title :StringUtil
 * @description :字符串处理工具类
 * @update :2013-8-15 上午09:13:03
 * @author :wang_ll
 * @version :1.0.0
 * @since :2013-8-15
 */
public class StringUtil {
	/**
	 * 将首字母转换成大写字母返回
	 * 
	 * @param str
	 * @return
	 */
	public static String firstCharToUpper(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}

		String firstChar = str.substring(0, 1);
		return firstChar.toUpperCase().concat(str.substring(1));
	}

	/**
	 * 判断一个字符串表达式是否为空(null或"")
	 * 
	 * @param expression
	 * @return true:null或""<br/>
	 *         false:非空 <br/>
	 */
	public static boolean isEmpty(String expression) {
		return expression == null || "".equals(expression.trim());
	}

	/**
	 * 判断一个字符串表达式是否不为空
	 * 
	 * @param expression
	 * @return true:非null且非""<br/>
	 *         false:null或""
	 */
	public static boolean isNotEmpty(String expression) {
		return !(expression == null || "".equals(expression.trim()));
	}

	/**
	 * 判断一个字符串表达式是否为布尔类型
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isBoolean(String expression) {
		if (expression != null) {
			return expression.matches("false|true|False|True");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否为数值格式
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isNumeric(String expression) {
		if (expression != null) {
			return expression.matches("^[0-9]*$");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是double类型的数据
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isDouble(String expression) {
		if (expression != null) {
			return expression.matches("^([0-9])[0-9]*(\\.\\w*)?$");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是email格式
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isEmail(String expression) {
		if (expression != null) {
			return expression
					.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是IPV4地址格式
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isIPV4Address(String expression) {
		if (expression != null) {
			return expression
					.matches("((25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|0?[1-9]\\d|0?0?\\d)");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是中文
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isChinese(String expression) {
		if (expression != null) {
			return expression.matches("[\u4e00-\u9fa5]*");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否包含中文
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isContainChinese(String expression) {
		final String PATTERN_DS = "[\u4e00-\u9fa5]+";
		Pattern p = Pattern.compile(PATTERN_DS);
		Matcher m = p.matcher(expression);
		try {
			while (m.find()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是空白符号\t\s\n
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isWhiteSpace(String expression) {
		if (expression != null) {
			return expression.matches("\\s*");
		}
		return false;
	}

	/**
	 * 判断一个字符串表达式是否是URL的格式
	 * 
	 * @param expression
	 * @return
	 */
	public static boolean isURL(String expression) {
		if (expression != null) {
			return expression.matches("[a-zA-Z]+://\\S*");
		}
		return false;
	}

	/**
	 * 将字符串组织成Document对象
	 * 
	 * @param content
	 * @return
	 */
/*	public static Document stringToDocument(String content) {
		Document doc = new Document();
		if (content != null && content.length() > 0) {
			// 添加XML文档头
			String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			StringReader reader = new StringReader(header + content);
			InputSource is = new InputSource(reader);
			try {
				doc = (new SAXBuilder()).build(is);
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return doc;
	}*/

	/**
	 * 根据开始标识和结束标识截取字符串内容
	 * 
	 * @param content
	 *            原字符串,若为空则返回""
	 * @param beginFlag
	 *            开始标识
	 * @param endFlag
	 *            结束标识
	 * @comment 如果beginFlag为null,则返回空串<br/>
	 *          如果beginFlag为"",则返回从0到endFlag出现的index直接的字符串<br/>
	 *          如果endFlag为null或"",则截取beginFlag之后的字符串<br/>
	 * @return
	 */
	public static String truncate(String content, String beginFlag,
			String endFlag) {
		String truncate = "";
		if (content == null || "".equals(content)) {
			return truncate;
		}

		// 截取beginFlag和endFlag之间的内容
		if (beginFlag != null && content.contains(beginFlag)) {
			// 获取beginFlag在content中的索引
			int begin = content.indexOf(beginFlag) + beginFlag.length();
			// 获取endFlg在content中的索引
			int end = 0;
			if (endFlag == null || endFlag.equals("")) {
				end = content.length();
			} else {
				end = content.indexOf(endFlag, begin);
			}
			truncate = content.substring(begin, end);
		} else {
			System.out
					.println("beginFlag is null or content not contain beginFlag...");
			System.out.println("content:" + content);
			System.out.println("beginFlag:" + beginFlag);
			System.out.println("endFlag:" + endFlag);
		}
		return truncate;
	}

	/**
	 * 去除字符串中所有的html标签
	 * 
	 * @param sourceHtml
	 * @return 不含Html标签的纯文本信息
	 */
	public static String htmlToText(String sourceHtml) {

		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String htmlStr = sourceHtml;// 含html标签的字符串
			String regEx_title = "<[\\s]*?title[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?title[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>

			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";

			p_script = Pattern.compile(regEx_title, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤title标签

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);

			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr.trim();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 对字符串中的中文以给定的编码方式进行编码
	 * 
	 * @param source
	 * @param charset
	 * @return
	 */
	public static String encode(String source, String charset) {
		final String PATTERN_DS = "[\u4e00-\u9fa5]+";
		Pattern p = Pattern.compile(PATTERN_DS);
		Matcher m = p.matcher(source);
		StringBuffer b = new StringBuffer();
		try {
			while (m.find()) {
				m.appendReplacement(b,
						java.net.URLEncoder.encode(m.group(0), charset));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.appendTail(b);
		return b.toString();
	}

	/**
	 * 对字符串中的中文以给定的编码方式进行编码
	 * 
	 * @param source
	 * @param charset
	 * @return
	 */
	public static String decode(String source, String charset) {
		String result = "";
		try {
			result = java.net.URLDecoder.decode(source, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 抽取字符串中第一个连续的数值串
	 * 
	 * @param source
	 * @return
	 */
	public static String extractContinusDigits(String source) {
		if (isEmpty(source)) {
			return "";
		}

		final String decimalPattern = "\\d+";
		Pattern p = Pattern.compile(decimalPattern);
		Matcher m = p.matcher(source);

		if (m.find()) {
			return m.group(0);
		}

		return "";
	}

	/**
	 * 抽取源文件中的中文字符
	 * 
	 * @param sourceHtml
	 * @return
	 */
	public static String extractChineseText(String source) {
		if (isEmpty(source)) {
			return "";
		}

		final String PATTERN_DS = "[\u4e00-\u9fa5]+";
		Pattern p = Pattern.compile(PATTERN_DS);
		Matcher m = p.matcher(source);
		StringBuffer chinese = new StringBuffer();
		try {
			while (m.find()) {
				chinese.append(m.group(0));
				chinese.append(" ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chinese.toString();

	}

	/**
	 * 抽取JSON信息中包含中文字符的value
	 * 
	 * @param sourceHtml
	 * @return
	 */
	public static String extractTextInJson(String source) {
		// 正则表达式过滤所有""之间的文本
		String filter = "\"[^\"]*\"";
		Pattern p = Pattern.compile(filter);
		Matcher m = p.matcher(source);
		StringBuffer containChinese = new StringBuffer();
		String matcherStr = null;
		try {
			while (m.find()) {
				matcherStr = m.group(0);
				matcherStr = matcherStr.replace("\"", "");
				// 对双引号的内容进行判断：含有中文字符，则提取
				if (isContainChinese(matcherStr)) {
					containChinese.append(matcherStr);
					containChinese.append(" ");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return containChinese.toString();
	}

	public static void main(String[] args) {
		System.out.println(isEmail("victory-ddd+ddd.dodo@www.com.cn"));
		System.out.println(isIPV4Address("110.1.5.50"));
		String ddd = "http://name=1003?";
		System.out.println(truncate(ddd, null, null));
		System.out.println("upper:" + firstCharToUpper("h"));

		System.out.println(isBoolean(""));

		String t = extractContinusDigits("共30集");
		System.out.println(t);
	}
	
	public static String toJson(Object object){
		//将一个Java对象转换成JSON
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
			mapper.writeValue(writer, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        String json=writer.toString();
        return json;
	}
}
