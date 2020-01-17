package com.camelot.kuka.common.utils;

import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Description: [运行String模板并返回执行结果]
 * </p>
 * Created on 2019-08-20
 *
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松</a>
 * @version 1.0 Copyright (c) 2019 北京柯莱特科技有限公司
 */
public class RunString {
    private static JexlEngine jexlEngine = new Engine();
    public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
        JexlExpression expression = jexlEngine.createExpression(jexlExpression);
        JexlContext context = new MapContext();
        if (!map.isEmpty()) {
            map.forEach(context::set);
        }
        return expression.evaluate(context);
    }

    /**
     * demo
     * @return 执行结果
     */
    public boolean runString(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", 3);
        map.put("b", 1);
        String expression = "(a>0 and b>0 ) and (a>2 or b > 2)";
        Object o = RunString.executeExpression(expression, map);
        return (boolean)o;
    }

//    test demo
//    public static void main(String[] args) {
//        RunString runString = new RunString();
//        boolean result = runString.runString();
//        if(result){
//            System.out.printf("成功");
//        }else{
//            System.out.printf("失败");
//        }
//    }
}
