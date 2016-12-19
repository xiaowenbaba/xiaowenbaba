package com.fdw.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MapValueToVo {

	public static <T> T setMapValueToVo(HashMap<String, Object> hashMap,
			Class<T> cls) throws Exception {
		T vo = cls.newInstance();
		// 拿到vo中的所有公共方法
		Method[] methods = cls.getMethods();

		for (Method m : methods) {
			String methodName = m.getName();// 拿到方法名称

			if (methodName.startsWith("set")) {
				// 只处理有一个参数的 set方法

				Class[] parameterTypes = m.getParameterTypes();// 参数列表类型数组
				if (parameterTypes != null && parameterTypes.length == 1) {
					Class paramType = parameterTypes[0];// 方法的第一个参数的数据类型

					// 要根据方法的参数类型，传入一个完全匹配的数据给方法
					// 从map中取出来的值，不一定与方法的参数类型匹配，那需要尽量将其转换成方法参数匹配的数据类型

					// 取出方法的set的后半段，转成小写，根据该值 从map中相应的数据
					methodName = methodName.substring(3).toLowerCase();
					//System.out.println("方法名:" + methodName + " 参数类型:"
							//+ paramType.getName());

					// 根据方法名不一定在map中取出相应值，mapValue则为空

					Object mapValue = hashMap.get(methodName);// "hiredate";
					if (mapValue != null) {
						// 拿到map中取出来的值的实际类型
						String valueType = mapValue.getClass().getName();
						//System.out.println("\t方法的参数类型:" + paramType.getName()
								//+ " 值的实际类型:" + valueType);
						// m.invoke(vo, mapValue);
						// 判断参数的数据类型是什么
						String paramTypeStr = paramType.getName();
						// 会对方法的参数类型是int或Integer类型的处理
						if (paramTypeStr.equals("int")
								|| paramTypeStr.equals("java.lang.Integer")) {
							if (mapValue instanceof Integer) {
								m.invoke(vo, mapValue);
							} else {
								String val = mapValue.toString();// 除int类型以外的其它都转换成字符串
								if (val.matches("[0-9]+")) {
									m.invoke(vo, Integer.parseInt(val));
								}
							}
						} else if (paramTypeStr.equals("float")
								|| paramTypeStr.equals("java.lang.Float")) {
							if (mapValue instanceof Float) {
								m.invoke(vo, mapValue);
							} else {
								// 123.0F
								String val = mapValue.toString();// 除int类型以外的其它都转换成字符串
								if (val.matches("[0-9]+([.][0-9]+)?(F|f)?")) {
									m.invoke(vo, Float.parseFloat(val));
								}
							}
						} else if (paramTypeStr.equals("double")
								|| paramTypeStr.equals("java.lang.Double")) {
							if (mapValue instanceof Double) {
								m.invoke(vo, mapValue);
							} else {
								// 123.0F
								String val = mapValue.toString();// 除int类型以外的其它都转换成字符串
								if (val.matches("[0-9]+([.][0-9]+)?(D|d)?")) {
									m.invoke(vo, Double.parseDouble(val));
								}
							}
						}// 8种数据类型判断
						else if (paramTypeStr.equals("java.sql.Date")) {
							if (mapValue instanceof java.sql.Date) {
								m.invoke(vo, mapValue);
							} else {
								String val = mapValue.toString();// 除int类型以外的其它都转换成字符串
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									java.util.Date utilDate = sdf.parse(val);
									java.sql.Date sqlDate = new java.sql.Date(
											utilDate.getTime());
									m.invoke(vo, sqlDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} else if (paramTypeStr.equals("java.util.Date")) {
							if (mapValue instanceof java.util.Date) {
								m.invoke(vo, mapValue);
							} else {
								String val = mapValue.toString();// 除int类型以外的其它都转换成字符串
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								try {
									java.util.Date utilDate = sdf.parse(val);
									m.invoke(vo, utilDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}else if (paramTypeStr.equals("java.lang.String")){
							m.invoke(vo, mapValue.toString());
						}
					}
				}

			}

		}
		return vo;

	}

}
