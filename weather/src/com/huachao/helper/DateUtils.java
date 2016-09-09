package com.huachao.helper;
import java.util.List;
import com.huachao.bean.Model;
/**
 * 日期工具类
 *
 */
public class DateUtils {
     public static Model getModel(List<String> list){
    	 Model model=new Model();
    	 model.setName(list.get(0));
    	 model.setDate(list.get(3));
    	 model.setInfo(list.get(4));
    	 model.setT1(list.get(7)+" "+list.get(8)+" "+list.get(9));
    	 model.setT2(list.get(12)+" "+list.get(13)+" "+list.get(14));
    	 model.setT3(list.get(17)+" "+list.get(18)+" "+list.get(19));
    	 model.setT4(list.get(22)+" "+list.get(23)+" "+list.get(24));
    	 model.setT5(list.get(27)+" "+list.get(28)+" "+list.get(29));
		return model;
     }
}