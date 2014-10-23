package com.torychow.bat.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class CommonUtils {

	public static JsonConfig getConfigByStr(JsonConfig config,
			final String[] str) {
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 死循环监听
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object arg0, String arg1, Object arg2) {
				Boolean isJson = false;
				for (int i = str.length - 1; i >= 0; i--) {
					if (str[i].equals(arg1)) {
						isJson = true;
						break;
					}
				}
				return !isJson;
			};
		});
		return config;
	}
	

	public static String getNowTime(String... format) {
		SimpleDateFormat sdf = null;
		if (format.length == 0)
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else if (format.length == 1)
			sdf = new SimpleDateFormat(format[0]);
		else
			throw new IllegalArgumentException("illegal argument");
		return sdf.format(new Date());
	}

	public static long getStringIpToLong(String ip) {
		String[] ips = ip.split("[.]");
		long num = 16777216L * Long.parseLong(ips[0]) + 65536L
				* Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2])
				+ Long.parseLong(ips[3]);
		return num;
	}

	public static String getLongIpToString(long ipLong) {

		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}

	private static final ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	// 今天
	public static String getThisDate() {
		Date today = new Date();
		return tl.get().format(today);
	}

	// 前一天
	public static String getLastDate() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return tl.get().format(c.getTime());
	}

	// 前一天
	public static String getLastDate(String date) {
		Calendar c = Calendar.getInstance();
		Date d = null;
		try {
			d = tl.get().parse(date);
		} catch (ParseException e) {
			return null;
		}
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return tl.get().format(c.getTime());
	}

	// 上月第一天
	public static String getLastMonthBegin() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, 1);
		return tl.get().format(c.getTime());
	}

	// 上月最后一天
	public static String getLastMonthEnd() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		return tl.get().format(c.getTime());
	}

	// 本月第一天
	public static String getThisMonthBegin() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.set(Calendar.DATE, 1);
		return tl.get().format(c.getTime());
	}

	// 本月最后一天
	public static String getThisMonthEnd() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		return tl.get().format(c.getTime());
	}

	// 一周前
	public static String get7DaysBefore() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, -7);
		return tl.get().format(c.getTime());
	}

	// 3天前
	public static String get5DaysBefore() {
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, -5);
		return tl.get().format(c.getTime());
	}

	public static void main(String[] args) {
		// System.out.println(getThisDate());
		// System.out.println(getLastDate());
		// System.out.println(getLastMonthBegin());
		// System.out.println(getLastMonthEnd());
		// System.out.println(getThisMonthBegin());
		// System.out.println(getThisMonthEnd());
		// System.out.println(get7DaysBefore());
		// System.out.println(get3DaysBefore());

		// System.out.println(getFloatFormat(1.23456f, 1));
		// System.out.println(getFloatFormat(1.23856, 2));

		System.out.println(getDatesByScope("2013-11-01", "2013-11-30",
				"yyyy-MM-dd"));
	}

	public static String getFloatFormat(double f, int retain) {
		String format = "##0.";
		for (int i = 0; i < retain; i++) {
			format += "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(f);
	}

	public static String getFluxStr(long l) {
		DecimalFormat df = new DecimalFormat("##0.0");
		if (l >= 1024 * 1024 * 1024)
			return df.format(l * 1.0 / 1024 / 1024 / 1024) + "GB";
		else if (l < 1024 * 1024 * 1024 && l >= 1024 * 1024)
			return df.format(l * 1.0 / 1024 / 1024) + "MB";
		else if (l < 1024 * 1024 && l >= 1024)
			return df.format(l * 1.0 / 1024) + "KB";
		else
			return l + "B";
	}

	public static String getTimeStr(long l) {
		if (l >= 3600 * 24)
			return l / 3600 / 24 + "天" + l / 3600 % 24 + "时";
		else if (l < 3600 * 24 && l >= 3600)
			return l / 3600 % 24 + "时" + l / 60 % 60 + "分";
		else if (l < 3600 && l >= 60)
			return l / 60 % 60 + "分";
		else if (l > 0)
			return l + "秒";
		else
			return "0秒";
	}

	public static List<String> getDatesByScope(String startDate,
			String endDate, String format) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat(format);
		List<String> dates = new ArrayList<String>();

		Date sd = null;
		Date ed = null;
		try {
			sd = sdf1.parse(startDate);
			ed = sdf1.parse(endDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("illegal date format");
		}
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(sd);
		end.setTime(ed);
		for (; start.before(end); start.add(Calendar.DAY_OF_MONTH, 1))
			dates.add(sdf2.format(start.getTime()));
		dates.add(sdf2.format(end.getTime()));
		return dates;
	}

	private static final ThreadLocal<SimpleDateFormat> tl2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static int getMinByTimes(String t1, String t2) throws ParseException {
		SimpleDateFormat sdf = tl2.get();
		Date d1 = sdf.parse(t1);
		Date d2 = sdf.parse(t2);
		return (int) ((d2.getTime() - d1.getTime()) / 1000 / 60);
	}

	public static boolean hasValue(String number) {
		if (!NumberUtils.isNumber(number))
			return false;
		return Float.valueOf(number) > 0f;
	}

	public static float roundFloat(float num, int dot) {
		int minus = 1;
		for (int i = 0; i < dot; i++) {
			minus *= 10;
		}
		return (float) (Math.round(num * minus)) / minus;
	}

	public static int getCurrentPage(int nTotalRecord, int nCurrentPage,int nPageSize) {
		int pageCount=(int) Math.ceil((nTotalRecord*1.0)/nPageSize);
		if (pageCount <= 0 ||nCurrentPage < 1)
			nCurrentPage = 1;
		else if (nCurrentPage > pageCount)
			nCurrentPage = pageCount;
		return nCurrentPage;
	}
	
	public static String getRandomNum() {
		String uuid = UUID.randomUUID().toString();
		String num = uuid.substring(uuid.length() - 6, uuid.length());
		return num.toUpperCase();
	}
	
	public static String getLabelIndex() {
		StringBuffer result = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sUUID = UUID.randomUUID().toString();
		Calendar date = Calendar.getInstance();
		String dayTime = sdf.format(date.getTime()) + date.getTimeInMillis();
		result.append(dayTime.substring(0, dayTime.length() - 4));
		result.append(sUUID.substring(sUUID.length() - 4));
		return result.toString();
	}

	public static String getLabelIndex(String parentIndex) {
		String labelIndex = parentIndex + "." + getLabelIndex();
		return labelIndex;
	}

	
	public static List<String> getLabelsNotStartWith(List<String> labelIndex) {
		List<String> listResult = new ArrayList<String>();
		Set<String> copyList = new HashSet<String>();
		copyList.addAll(labelIndex);
		for(String s :labelIndex){
			for(String str :labelIndex){
				if(str.startsWith(s)&&!str.equals(s)){
					copyList.remove(str);
				}
			}
		}
		listResult.addAll(copyList);
		return listResult;
	}
	
	public static String getIdsStr(String sIds){
		if(StringUtils.isNotBlank(sIds)){
			String [] ids=sIds.split(",");
			StringBuffer stringBuffer = new StringBuffer(); 
			for(int i=ids.length-1;i>=0;i--){
				stringBuffer.append("'"+ids[i]+"'");
				if(i!=0)stringBuffer.append(",");
			}
			return stringBuffer.toString();
		}
		return null;
	}

	public static String getIdsStr(Object[] ids) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = ids.length - 1; i >= 0; i--) {
			stringBuffer.append("'" + ids[i] + "'");
			if (i != 0)
				stringBuffer.append(",");
		}
		return stringBuffer.toString();
	}
	
	public static Float getNumberFormat(Float result,String format){
		DecimalFormat df = new DecimalFormat(format);
		return Float.parseFloat(df.format(result));
	}
	
	public static String getWhereJson(String whereJson,
			Map<String, Object> whereMap) {
		Map<String, Object> map4Json = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(whereJson)){
			map4Json = JsonUtil.getMap4Json(whereJson);
		}
		map4Json.putAll(whereMap);
		whereJson = JsonUtil.getJsonString4JavaPOJO(map4Json);
		return whereJson;
	}
	
	/**
	 * @description 大括号定界符替换 (有问题)
	 * @author zhoutong
	 * @method replaceAll
	 * @return String
	 * @date 2014-7-10 上午10:12:37
	 */
	@Deprecated
	public static String replaceAll(String original,String replacement){
		String reg = "\\{[\\x00-\\xff]*\\}"; 
		Pattern ptt = Pattern.compile(reg, Pattern.CASE_INSENSITIVE); 
		Matcher m_other = ptt.matcher(original); 
		return m_other.replaceAll(replacement);
	}
}
