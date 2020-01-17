package com.camelot.kuka.common.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * <p>
 * Description: [Iterable 的工具类]
 * </p>
 * Created on 2019/11/20
 * 
 * @author <a href="mailto: cuichunsong@camelotchina.com">崔春松 </a>
 * @version 1.0 Copyright (c) 2019 北京柯莱特科技有限公司
 */

public class Iterables {
    public static <E> void forEach(Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
